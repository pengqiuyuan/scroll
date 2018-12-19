package com.ruoyi.project.monitor.weixinDel.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.monitor.weixinDel.domain.WeixinDel;
import com.ruoyi.project.monitor.weixinDel.service.IWeixinDelService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 微信数据删除 信息操作处理
 * 
 * @author ruoyi
 * @date 2018-12-18
 */
@Controller
@RequestMapping("/monitor/weixinDel")
public class WeixinDelController extends BaseController
{
    private String prefix = "monitor/weixinDel";
	
	@Autowired
	private IWeixinDelService weixinDelService;
	
	@RequiresPermissions("monitor:weixinDel:view")
	@GetMapping()
	public String weixinDel()
	{
	    return prefix + "/weixinDel";
	}
	
	/**
	 * 查询微信数据删除列表
	 */
	@RequiresPermissions("monitor:weixinDel:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(WeixinDel weixinDel)
	{
		startPage();
        List<WeixinDel> list = weixinDelService.selectWeixinDelList(weixinDel);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出微信数据删除列表
	 */
	@RequiresPermissions("monitor:weixinDel:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(WeixinDel weixinDel)
    {
    	List<WeixinDel> list = weixinDelService.selectWeixinDelList(weixinDel);
        ExcelUtil<WeixinDel> util = new ExcelUtil<WeixinDel>(WeixinDel.class);
        return util.exportExcel(list, "weixinDel");
    }
	
	/**
	 * 新增微信数据删除
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存微信数据删除
	 */
	@RequiresPermissions("monitor:weixinDel:add")
	@Log(title = "微信数据删除", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(WeixinDel weixinDel)
	{		
		return toAjax(weixinDelService.insertWeixinDel(weixinDel));
	}

	/**
	 * 修改微信数据删除
	 */
	@GetMapping("/edit/{scrollId}")
	public String edit(@PathVariable("scrollId") Integer scrollId, ModelMap mmap)
	{
		WeixinDel weixinDel = weixinDelService.selectWeixinDelById(scrollId);
		mmap.put("weixinDel", weixinDel);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存微信数据删除
	 */
	@RequiresPermissions("monitor:weixinDel:edit")
	@Log(title = "微信数据删除", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(WeixinDel weixinDel)
	{		
		return toAjax(weixinDelService.updateWeixinDel(weixinDel));
	}
	
	/**
	 * 删除微信数据删除
	 */
	@RequiresPermissions("monitor:weixinDel:remove")
	@Log(title = "微信数据删除", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(weixinDelService.deleteWeixinDelByIds(ids));
	}
	
}
