package com.ruoyi.project.monitor.job.service.weibo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.aliyun.oss.OSSClient;
import com.google.common.collect.Maps;
import com.ruoyi.common.utils.ESRestClientHelper;
import com.ruoyi.common.utils.FileUtil;
import com.ruoyi.common.utils.OSSClientUtil;
import com.ruoyi.common.utils.RedisUtil;
import com.ruoyi.common.utils.ZipUtils;
import com.ruoyi.project.monitor.scroll.domain.Scroll;
import com.ruoyi.project.monitor.scroll.service.IScrollService;
import com.ruoyi.project.system.user.service.IUserService;

@Service
public class WeiboScrollService
{
	@Autowired
	private IScrollService scrollService;
	
    @Autowired
    private IUserService userService;
    
    @Value("${ruoyi.filePath}")
	public String fileP;
    
    public void scroll(){
		Long scrollId = RedisUtil.INSTANCE.sincr("incr_weibo_scroll");
		Scroll scroll = scrollService.selectScrollById(scrollId.intValue());
		if(scroll==null || !scroll.getStatus().equals("0")){
			return;
		}
    	scroll.setStatus("1");
    	scrollService.updateScroll(scroll);
    	System.out.println("获取 incrscroll 开始压缩任务：" + scrollId);
    	
    	OSSClient ossClient = OSSClientUtil.getInstance().getClient();
    	String fileName = scroll.getStartDate()+"_"+scroll.getEndDate();
    	String filePath = fileP + fileName;
    	try {
    		System.out.println("执行无参方法");
			getWeiboArticlesSorted(scroll.getStartDate()+" 00:00:00", scroll.getEndDate()+" 00:00:00",filePath);
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
    	
        String sourceFolder = filePath;
        String zipFilePath = filePath+".zip";
        try {
			ZipUtils.zip(sourceFolder, zipFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
		System.out.println(fileName +" 上传文件开始");
		ossClient.putObject("narnia", fileName, new File(zipFilePath));
		System.out.println(fileName +" 上传文件完成");
		Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
		URL url = ossClient.generatePresignedUrl("narnia", fileName, expiration);
		System.out.println(url);
		
		//删除本地文件
        System.out.println(sourceFolder+"  " + zipFilePath +" 删除原始、压缩文件");
		FileUtil.delete(sourceFolder);
		FileUtil.delete(zipFilePath);
		
		scroll.setStatus("2");
		scroll.setUrl(url.toString());
    	scrollService.updateScroll(scroll);
    }
    
    /**
     * 带排序的微博原文导出
     * @param startDate
     * @param endDate
     * @param keywords
     * @param fields
     * @param limit
     * @param sortField
     * @param ascOrDesc "ASC,DESC"
     * @return
     * @throws IOException 
     * @throws ParseException 
     */
    public void getWeiboArticlesSorted(String startDate, String endDate,String filePath) throws ParseException, IOException {
        String scrollSize = "10000";
        HttpEntity entity = new NStringEntity(""
                + "{"
                + 	"\"query\": {"
                + 		"\"bool\": {"
                + 			"\"filter\": [{"
                +			 	"\"range\": {"
                + 					"\"published_at\": {"
                + 						"\"gte\": \""+startDate+"\","
                + 						"\"lte\": \""+endDate+"\","
                +                       "\"format\": \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis\","
                + 						"\"time_zone\": \"Asia/Shanghai\""
                + 					"}"
                + 				"}"
                + 			"}]"
                + 		"}"
                + 	"},"
                + 	"\"size\": "+scrollSize+","
                +    "\"sort\" : ["
                +       "{\"published_at\" : {\"order\" : \"asc\"}}"
                +    "]"
                + "}", ContentType.APPLICATION_JSON);
        System.out.println(EntityUtils.toString(entity));
        try {
            this.GetScrollList("weibo_articles_and_weiboers","weibo_articles_and_weiboer", entity, scrollSize,filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
    /**
     * 获取scroll结果
     *
     * @param index
     * @param type
     * @param entity
     * @param scrollSize
     * @return
     * @throws IOException
     */
    public void GetScrollList(String index, String type, HttpEntity entity, String scrollSize, String filePath) throws IOException {
    	File fout = new File(filePath);
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    	
        Map<String, String> map = GetScrollId(index, type, entity, scrollSize);
        String scrollId = map.get("scrollId");
        String total = map.get("total");
        scrollSize = map.get("scrollSize");
        String scrollList = map.get("scrollList");
        List<Object> sList = JSON.parseArray(scrollList);

		for (Object object : sList) {
			bw.write(object.toString());
			bw.newLine();
		}
		bw.flush();
        System.out.println("【" + type + " 更新导出】scroll总条数：" + total + " scroll_id 第1批，此批大小：" + sList.size());
        RestClient restClient = ESRestClientHelper.getInstance().getClient();
        HttpEntity entity2 = new NStringEntity("{\"scroll\":\"1800s\",\"scroll_id\":\"" + scrollId + "\"}", ContentType.APPLICATION_JSON);

        for (int i = 1; i <= (Long.valueOf(total) / Long.valueOf(scrollSize)); i++) {
        	//防止定时任务执行时间过长 DataSource 被连接池自动关闭
        	userService.selectUserById(1L);
            Response indexResponse = restClient.performRequest(
                    "POST",
                    "/_search/scroll",
                    Collections.<String, String>emptyMap(),
                    entity2);
            String json = EntityUtils.toString(indexResponse.getEntity());
            Map<String, Object> userMap = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
            });
            String list = JSON.parseObject(userMap.get("hits").toString()).getString("hits");
            List<Object> lists = JSON.parseArray(list);
            
    		for (Object object : lists) {
    			bw.write(object.toString());
    			bw.newLine();
    		}
    		bw.flush();
            System.out.println("【" + type + " 更新导出】scroll总条数：" + total + " scroll_id 第" + (i + 1) + "批，此批大小：" + lists.size());
        }
        bw.close();
        
        //clear scroll
        Response clearScrollResponse = restClient.performRequest(
                "DELETE",
                "/_search/scroll/" + scrollId,
                Collections.<String, String>emptyMap());
        System.out.println("清除 scrollId " + EntityUtils.toString(clearScrollResponse.getEntity()));
    }

    /**
     * scroll查询辅助方法，注意scrollSize和entity中的size应该相等
     *
     * @param index
     * @param type
     * @param entity
     * @param scrollSize
     * @return
     * @throws IOException
     */

    private Map<String, String> GetScrollId(String index, String type, HttpEntity entity, String scrollSize) throws IOException {
        RestClient restClient = ESRestClientHelper.getInstance().getClient();
        Response indexResponse = restClient.performRequest(
                "GET",
                String.format("/%s/%s/_search?scroll=1800s", index, type),
                Collections.singletonMap("pretty", "true"),
                entity);
        String json = EntityUtils.toString(indexResponse.getEntity());
        String scrollId = JSON.parseObject(json).get("_scroll_id").toString();
        Map<String, Object> userMap = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
        });
        String total = JSON.parseObject(userMap.get("hits").toString()).getString("total");
        String scrollList = JSON.parseObject(userMap.get("hits").toString()).getString("hits");
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("scrollId", scrollId);
        map.put("total", total);
        map.put("scrollSize", scrollSize);
        map.put("scrollList", scrollList);
        return map;
    }
}
