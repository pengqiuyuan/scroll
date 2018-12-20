package com.ruoyi.project;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.ESNewRestClientHelper;
import com.ruoyi.project.monitor.weiboDel.domain.WeiboDel;
import com.ruoyi.project.monitor.weiboDel.service.IWeiboDelService;

public class TtestReIndex extends Tester {
	
	@Autowired
	private IWeiboDelService weiboDelService;
	
	@Test
	@Rollback(false)
	public void test() throws Exception {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		List<String> dates = days("2017-01-11 00:00:00", "2018-12-30 00:00:00");
		System.out.println(dates.size());
		System.out.println(JSON.toJSONString(dates));
		for (String string : dates) {
			LocalDateTime start = LocalDateTime.parse(string);
			LocalDateTime end = start.plusHours(1);
			System.out.println(df.format(start) +"  "  + df.format(end));
		    
			WeiboDel scro = new WeiboDel();
	    	scro.setScrollGroup("索引");
	    	scro.setStatus("0");
	    	scro.setStartDate(df.format(start));
	    	scro.setEndDate(df.format(end));
	    	weiboDelService.insertWeiboDel(scro);
		}
	}
	
	/**
	 * 获取两个日期之间所有的日期
	 * @param date1
	 * @param date2
	 * @return
	 */
	public synchronized static ArrayList<String> days(String date1, String date2) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime start = LocalDateTime.parse(date1,df);
		LocalDateTime end = LocalDateTime.parse(date2,df);
		ArrayList<String> totalDates = new ArrayList<>();
		while (!start.isAfter(end) && !start.equals(end)) {
		    totalDates.add(start.toString());
		    start = start.plusHours(1);
		}
		return totalDates;
	}
	

	//@Test
	public void test1() throws Exception {
    	String index = "weibo_articles_and_weiboers";
    	String type   = "weibo_articles_and_weiboer";
    	String startDate = "2017-01-02 00:00:00";
    	String endDate   = "2017-01-03 00:00:00";
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
