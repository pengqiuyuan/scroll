package com.ruoyi.project;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.alibaba.fastjson.JSON;
import com.ruoyi.project.monitor.scroll.domain.Scroll;
import com.ruoyi.project.monitor.scroll.service.IScrollService;
import com.ruoyi.project.monitor.weixinScroll.domain.WeixinScroll;
import com.ruoyi.project.monitor.weixinScroll.service.IWeixinScrollService;

public class Ttest extends Tester {

	private final Logger logger = LoggerFactory.getLogger(Ttest.class);
	
	@Autowired
	private IScrollService scrollService;
	
	@Autowired
	private IWeixinScrollService weixinScrollService;
	
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
		    
	    	Scroll scro = new Scroll();
	    	scro.setScrollGroup("压缩");
	    	scro.setStatus("0");
	    	scro.setStartDate(start.toString());
	    	scro.setEndDate(end.toString());
	    	scrollService.insertScroll(scro);
		}
	}
	
	
	@Test
	@Rollback(false)
	public void testWeixin() throws Exception {
		List<String> dates = days("2014-01-01", "2017-01-01");
		System.out.println(dates.size());
		System.out.println(JSON.toJSONString(dates));
		for (String string : dates) {
			LocalDate start = LocalDate.parse(string);
			LocalDate end = start.plusDays(1);
			System.out.println(start.toString() +"  "  + end.toString());
		    
			WeixinScroll scro = new WeixinScroll();
	    	scro.setScrollGroup("压缩");
	    	scro.setStatus("0");
	    	scro.setStartDate(start.toString());
	    	scro.setEndDate(end.toString());
	    	weixinScrollService.insertWeixinScroll(scro);
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
    
}
