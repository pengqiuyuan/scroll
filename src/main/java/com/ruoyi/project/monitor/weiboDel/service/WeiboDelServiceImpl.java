package com.ruoyi.project.monitor.weiboDel.service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.monitor.weiboDel.mapper.WeiboDelMapper;
import com.ruoyi.project.monitor.weiboDel.domain.WeiboDel;
import com.ruoyi.project.monitor.weiboDel.service.IWeiboDelService;
import com.ruoyi.project.monitor.weixinScroll.domain.WeixinScroll;
import com.ruoyi.common.support.Convert;
import com.ruoyi.common.utils.ESNewRestClientHelper;
import com.ruoyi.common.utils.ESRestClientHelper;
import com.ruoyi.common.utils.RedisUtil;
import com.ruoyi.common.utils.security.ShiroUtils;

/**
 * 微博数据删除 服务层实现
 * 
 * @author ruoyi
 * @date 2018-12-18
 */
@Service
public class WeiboDelServiceImpl implements IWeiboDelService 
{
	@Autowired
	private WeiboDelMapper weiboDelMapper;

	/**
     * 查询微博数据删除信息
     * 
     * @param scrollId 微博数据删除ID
     * @return 微博数据删除信息
     */
    @Override
	public WeiboDel selectWeiboDelById(Integer scrollId)
	{
	    return weiboDelMapper.selectWeiboDelById(scrollId);
	}
	
	/**
     * 查询微博数据删除列表
     * 
     * @param weiboDel 微博数据删除信息
     * @return 微博数据删除集合
     */
	@Override
	public List<WeiboDel> selectWeiboDelList(WeiboDel weiboDel)
	{
	    return weiboDelMapper.selectWeiboDelList(weiboDel);
	}
	
    /**
     * 新增微博数据删除
     * 
     * @param weiboDel 微博数据删除信息
     * @return 结果
     */
	@Override
	public int insertWeiboDel(WeiboDel weiboDel)
	{
		try {
			weiboDel.setCreateBy(ShiroUtils.getLoginName());
		} catch (Exception e) {
		}
	    return weiboDelMapper.insertWeiboDel(weiboDel);
	}
	
	/**
     * 修改微博数据删除
     * 
     * @param weiboDel 微博数据删除信息
     * @return 结果
     */
	@Override
	public int updateWeiboDel(WeiboDel weiboDel)
	{
		try {
			weiboDel.setUpdateBy(ShiroUtils.getLoginName());
		} catch (Exception e) {
		}
	    return weiboDelMapper.updateWeiboDel(weiboDel);
	}

	/**
     * 删除微博数据删除对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteWeiboDelByIds(String ids)
	{
		return weiboDelMapper.deleteWeiboDelByIds(Convert.toStrArray(ids));
	}

	@Override
	public void index(String index, String type) {
		Long scrollId = RedisUtil.INSTANCE.sincr("incr_weibo_index");
		WeiboDel scroll = selectWeiboDelById(scrollId.intValue());
		if(scroll==null || !scroll.getStatus().equals("0")){
			return;
		}
    	scroll.setStatus("1");
    	updateWeiboDel(scroll);
    	System.out.println("获取 incr_weibo_index 开始索引任务：" + scrollId);
    	try {
    		getWeiboArticlesIndex(index,type,scroll.getStartDate(), scroll.getEndDate());
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		scroll.setStatus("2");
		updateWeiboDel(scroll);
	}
	
    public void getWeiboArticlesIndex(String index, String type,String startDate, String endDate) throws ParseException, IOException {
        HttpEntity entity = new NStringEntity(""
                + "{"
                + 		"\"source\": {"
                + 			"\"remote\": {"
                + 					"\"host\": \"http://10.28.51.165:9222\","
                + 					"\"username\": \"idatage\","
                + 					"\"password\": \"abc@123456\""
                + 			"},"
                + 			"\"index\": \""+index+"\","
                + 			"\"type\": \""+type+"\","
                + 			"\"query\": {"
                + 				"\"bool\": {"
                + 					"\"filter\": [{"
                +			 			"\"range\": {"
                + 							"\"published_at\": {"
                + 								"\"gte\": \""+startDate+"\","
                + 								"\"lte\": \""+endDate+"\","
                +                       		"\"format\": \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis\","
                + 								"\"time_zone\": \"Asia/Shanghai\""
                + 							"}"
                + 						"}"
                + 					"}]"
                + 				"}"
                + 			"},"
                +   		"\"size\": 10000"
                + 		"},"
                + 		"\"dest\": {"
                + 			"\"index\": \""+index+"\","
                + 			"\"type\": \""+type+"\""
                + 		"}"
                + "}", ContentType.APPLICATION_JSON);
        System.out.println(EntityUtils.toString(entity));
        //新集群发送请求 ESNewRestClientHelper
        RestClient restClient = ESNewRestClientHelper.getInstance().getClient();
		Response indexResponse = restClient.performRequest(
		        "POST",
		        "/_reindex",
		        Collections.singletonMap("pretty", "true"),
		        entity);
		System.out.println(startDate + " " + endDate + "  "+EntityUtils.toString(indexResponse.getEntity()));
    }
	
}
