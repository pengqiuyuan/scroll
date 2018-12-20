package com.ruoyi.project.monitor.job.task;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.common.utils.RedisUtil;
import com.ruoyi.project.monitor.scroll.domain.Scroll;
import com.ruoyi.project.monitor.scroll.service.IScrollService;
import com.ruoyi.project.monitor.weiboDel.domain.WeiboDel;
import com.ruoyi.project.monitor.weiboDel.service.IWeiboDelService;
import com.ruoyi.project.monitor.weixinDel.service.IWeixinDelService;
import com.ruoyi.project.monitor.weixinScroll.domain.WeixinScroll;
import com.ruoyi.project.monitor.weixinScroll.service.IWeixinScrollService;


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
	private IScrollService weiboScrollService;
	
	@Autowired
	private IWeixinScrollService weixinScrollService;
	
	@Autowired
	private IWeiboDelService weiboDelService;
	
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
		Scroll scroll = weiboScrollService.selectScrollById(scrollId.intValue());
		if(scroll==null || !scroll.getStatus().equals("0")){
			return;
		}
    	scroll.setStatus("1");
    	weiboScrollService.updateScroll(scroll);
    	System.out.println("获取 incrscroll 开始压缩任务：" + scrollId);
    	try {
    		System.out.println("sleep 10秒 进行中....");
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
		scroll.setStatus("2");
		weiboScrollService.updateScroll(scroll);
    }
    
    
    public void ryNoParamsByWeiboScroll()
    {
    	Scroll scro = new Scroll();
    	scro.setScrollGroup("压缩");
    	scro.setStatus("0");
    	List<Scroll> scrolls = weiboScrollService.selectScrollList(scro);
    	if(scrolls.size() == 0){
    		return;
    	}else{
    		weiboScrollService.scroll();
    	}
    }
    
    public void ryNoParamsByWeixinScroll()
    {
    	WeixinScroll scro = new WeixinScroll();
    	scro.setScrollGroup("压缩");
    	scro.setStatus("0");
    	List<WeixinScroll> scrolls = weixinScrollService.selectWeixinScrollList(scro);
    	if(scrolls.size() == 0){
    		return;
    	}else{
    		weixinScrollService.scroll();
    	}
    }
    
    public void ryNoParamsByWeiboIndex()
    {
    	WeiboDel del = new WeiboDel();
    	del.setScrollGroup("索引");
    	del.setStatus("0");
    	List<WeiboDel> dels = weiboDelService.selectWeiboDelList(del);
    	if(dels.size() == 0){
    		return;
    	}else{
        	String index = "weibo_articles_and_weiboers";
        	String type   = "weibo_articles_and_weiboer";
    		weiboDelService.index(index,type);
    	}
    }
}
