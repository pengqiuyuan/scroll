package com.ruoyi.project;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSClient;
import com.ruoyi.common.utils.OSSClientUtil;
import com.ruoyi.project.monitor.scroll.domain.Scroll;
import com.ruoyi.project.monitor.scroll.service.IScrollService;

public class TtestOss extends Tester {

	private final Logger logger = LoggerFactory.getLogger(TtestOss.class);
	
	@Autowired
	private IScrollService scrollService;
	
	@Test
	@Rollback(false)
	public void test() throws Exception {
		OSSClient ossClient = OSSClientUtil.getInstance().getClient();
    	List<Scroll> scrolls = scrollService.selectScrollList(new Scroll());
    	for (Scroll scroll : scrolls) {
    		System.out.println(scroll.getStartDate()+"_"+scroll.getEndDate());
        	String fileName = scroll.getStartDate()+"_"+scroll.getEndDate();
        	String filePath = "/Volumes/HD/"+fileName;
            String zipFilePath = filePath+".zip";
            
    		System.out.println(fileName +" 上传文件开始 " + LocalDateTime.now());
    		ossClient.putObject("narnia", fileName, new File(zipFilePath));
    		System.out.println(fileName +" 上传文件完成 " + LocalDateTime.now());
    		Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
    		URL url = ossClient.generatePresignedUrl("narnia", fileName, expiration);
    		System.out.println(url);
    		
    		scroll.setStatus("2");
    		scroll.setUrl(url.toString());
        	scrollService.updateScroll(scroll);
		}

	}
    
}
