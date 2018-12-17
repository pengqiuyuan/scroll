package com.ruoyi.project.monitor.weixinScroll.controller;

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
import com.ruoyi.project.monitor.weixinScroll.domain.WeixinScroll;
import com.ruoyi.project.monitor.weixinScroll.service.IWeixinScrollService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 索引任务 信息操作处理
 * 
 * @author ruoyi
 * @date 2018-12-17
 */
@Controller
@RequestMapping("/monitor/weixinScroll")
public class WeixinScrollController extends BaseController
{
    private String prefix = "monitor/weixinScroll";
	
	@Autowired
	private IWeixinScrollService weixinScrollService;
	
	@RequiresPermissions("monitor:weixinScroll:view")
	@GetMapping()
	public String weixinScroll()
	{
	    return prefix + "/weixinScroll";
	}
	
	/**
	 * 查询索引任务列表
	 */
	@RequiresPermissions("monitor:weixinScroll:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(WeixinScroll weixinScroll)
	{
		startPage();
        List<WeixinScroll> list = weixinScrollService.selectWeixinScrollList(weixinScroll);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出索引任务列表
	 */
	@RequiresPermissions("monitor:weixinScroll:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(WeixinScroll weixinScroll)
    {
    	List<WeixinScroll> list = weixinScrollService.selectWeixinScrollList(weixinScroll);
        ExcelUtil<WeixinScroll> util = new ExcelUtil<WeixinScroll>(WeixinScroll.class);
        return util.exportExcel(list, "weixinScroll");
    }
	
	/**
	 * 新增索引任务
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存索引任务
	 */
	@RequiresPermissions("monitor:weixinScroll:add")
	@Log(title = "索引任务", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(WeixinScroll weixinScroll)
	{		
		return toAjax(weixinScrollService.insertWeixinScroll(weixinScroll));
	}

	/**
	 * 修改索引任务
	 */
	@GetMapping("/edit/{scrollId}")
	public String edit(@PathVariable("scrollId") Integer scrollId, ModelMap mmap)
	{
		WeixinScroll weixinScroll = weixinScrollService.selectWeixinScrollById(scrollId);
		mmap.put("weixinScroll", weixinScroll);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存索引任务
	 */
	@RequiresPermissions("monitor:weixinScroll:edit")
	@Log(title = "索引任务", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(WeixinScroll weixinScroll)
	{		
		return toAjax(weixinScrollService.updateWeixinScroll(weixinScroll));
	}
	
	/**
	 * 删除索引任务
	 */
	@RequiresPermissions("monitor:weixinScroll:remove")
	@Log(title = "索引任务", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(weixinScrollService.deleteWeixinScrollByIds(ids));
	}
	
}
