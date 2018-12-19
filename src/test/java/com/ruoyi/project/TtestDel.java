package com.ruoyi.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.ESRestClientHelper;
import com.ruoyi.project.monitor.weiboDel.domain.WeiboDel;
import com.ruoyi.project.monitor.weiboDel.service.IWeiboDelService;
import com.ruoyi.project.monitor.weixinDel.domain.WeixinDel;
import com.ruoyi.project.monitor.weixinDel.service.IWeixinDelService;

public class TtestDel extends Tester {

	@Autowired
	private IWeiboDelService weiboDelService;
	
	@Autowired
	private IWeixinDelService weixinDelService;
	
	//@Test
	@Rollback(false)
	public void test() throws Exception {
		List<String> dates = days("2014-01-01", "2014-03-01");
		System.out.println(dates.size());
		System.out.println(JSON.toJSONString(dates));
		for (String string : dates) {
			LocalDate start = LocalDate.parse(string);
			LocalDate end = start.plusDays(1);
			System.out.println(start.toString() +"  "  + end.toString());
		    
			WeiboDel scro = new WeiboDel();
	    	scro.setScrollGroup("删除");
	    	scro.setStatus("0");
	    	scro.setStartDate(start.toString());
	    	scro.setEndDate(end.toString());
	    	weiboDelService.insertWeiboDel(scro);
		}
	}
	
	
	//@Test
	@Rollback(false)
	public void testWeixin() throws Exception {
		List<String> dates = days("2014-01-01", "2017-01-01");
		System.out.println(dates.size());
		System.out.println(JSON.toJSONString(dates));
		for (String string : dates) {
			LocalDate start = LocalDate.parse(string);
			LocalDate end = start.plusDays(1);
			System.out.println(start.toString() +"  "  + end.toString());
		    
			WeixinDel scro = new WeixinDel();
	    	scro.setScrollGroup("删除");
	    	scro.setStatus("0");
	    	scro.setStartDate(start.toString());
	    	scro.setEndDate(end.toString());
	    	weixinDelService.insertWeixinDel(scro);
		}
	}
	
	/**
	 * 获取两个日期之间所有的日期
	 * @param date1
	 * @param date2
	 * @return
	 */
	public synchronized static ArrayList<String> days(String date1, String date2) {
		LocalDate start = LocalDate.parse(date1);
		LocalDate end = LocalDate.parse(date2);
		ArrayList<String> totalDates = new ArrayList<>();
		while (!start.isAfter(end)) {
		    totalDates.add(start.toString());
		    start = start.plusDays(1);
		}
		return totalDates;
	}
	
	@Test
	public void test1() throws Exception {
    	String startDate = "2014-01-11 00:00:00";
    	String endDate   = "2014-01-13 00:00:00";
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
                + 	"}"
                + "}", ContentType.APPLICATION_JSON);
        System.out.println(EntityUtils.toString(entity));
        RestClient restClient = ESRestClientHelper.getInstance().getClient();
		Response indexResponse = restClient.performRequest(
		        "POST",
		        "/weibo_articles_and_weiboers/weibo_articles_and_weiboer/_delete_by_query?conflicts=proceed&scroll_size=10000&refresh&slices=20",
		        Collections.singletonMap("pretty", "true"),
		        entity);
		System.out.println(startDate + " " + endDate + "  "+EntityUtils.toString(indexResponse.getEntity()));
    	
	}
    
}
