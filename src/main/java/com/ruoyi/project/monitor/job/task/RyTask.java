package com.ruoyi.project.monitor.job.task;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.common.utils.RedisUtil;
import com.ruoyi.project.monitor.job.service.weibo.WeiboScrollService;
import com.ruoyi.project.monitor.scroll.domain.Scroll;
import com.ruoyi.project.monitor.scroll.service.IScrollService;


/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask
{
	public static final SimpleDateFormat s =   new SimpleDateFormat("yyyyMMddHHmmssSSS" ); 
	
	@Autowired
	private IScrollService scrollService;
	
	@Autowired
	private WeiboScrollService weiboScrollService;
    	
    public void ryParams(String params)
    {
    	System.out.println("执行有参方法：" + params);
    }
    
    public void ryTests(){
    	System.out.println("incrscroll: "+RedisUtil.INSTANCE.sincr("incrscroll"));
    	try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 模拟测试
     */
    public void scrollTest(){
		Long scrollId = RedisUtil.INSTANCE.sincr("incrscroll");
		Scroll scroll = scrollService.selectScrollById(scrollId.intValue());
		if(scroll==null || !scroll.getStatus().equals("0")){
			return;
		}
    	scroll.setStatus("1");
    	scrollService.updateScroll(scroll);
    	System.out.println("获取 incrscroll 开始压缩任务：" + scrollId);
    	try {
    		System.out.println("sleep 10秒 进行中....");
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
		scroll.setStatus("2");
    	scrollService.updateScroll(scroll);
    }
    
    
    public void ryNoParamsByWeiboScroll()
    {
    	Scroll scro = new Scroll();
    	scro.setScrollGroup("压缩");
    	scro.setStatus("0");
    	List<Scroll> scrolls = scrollService.selectScrollList(scro);
    	if(scrolls.size() == 0){
    		return;
    	}else{
    		weiboScrollService.scroll();
    	}
    }
}
