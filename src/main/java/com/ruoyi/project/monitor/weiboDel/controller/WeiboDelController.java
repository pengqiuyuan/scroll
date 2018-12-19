package com.ruoyi.project.monitor.weiboDel.controller;

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
import com.ruoyi.project.monitor.weiboDel.domain.WeiboDel;
import com.ruoyi.project.monitor.weiboDel.service.IWeiboDelService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 微博数据删除 信息操作处理
 * 
 * @author ruoyi
 * @date 2018-12-18
 */
@Controller
@RequestMapping("/monitor/weiboDel")
public class WeiboDelController extends BaseController
{
    private String prefix = "monitor/weiboDel";
	
	@Autowired
	private IWeiboDelService weiboDelService;
	
	@RequiresPermissions("monitor:weiboDel:view")
	@GetMapping()
	public String weiboDel()
	{
	    return prefix + "/weiboDel";
	}
	
	/**
	 * 查询微博数据删除列表
	 */
	@RequiresPermissions("monitor:weiboDel:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(WeiboDel weiboDel)
	{
		startPage();
        List<WeiboDel> list = weiboDelService.selectWeiboDelList(weiboDel);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出微博数据删除列表
	 */
	@RequiresPermissions("monitor:weiboDel:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(WeiboDel weiboDel)
    {
    	List<WeiboDel> list = weiboDelService.selectWeiboDelList(weiboDel);
        ExcelUtil<WeiboDel> util = new ExcelUtil<WeiboDel>(WeiboDel.class);
        return util.exportExcel(list, "weiboDel");
    }
	
	/**
	 * 新增微博数据删除
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存微博数据删除
	 */
	@RequiresPermissions("monitor:weiboDel:add")
	@Log(title = "微博数据删除", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(WeiboDel weiboDel)
	{		
		return toAjax(weiboDelService.insertWeiboDel(weiboDel));
	}

	/**
	 * 修改微博数据删除
	 */
	@GetMapping("/edit/{scrollId}")
	public String edit(@PathVariable("scrollId") Integer scrollId, ModelMap mmap)
	{
		WeiboDel weiboDel = weiboDelService.selectWeiboDelById(scrollId);
		mmap.put("weiboDel", weiboDel);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存微博数据删除
	 */
	@RequiresPermissions("monitor:weiboDel:edit")
	@Log(title = "微博数据删除", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(WeiboDel weiboDel)
	{		
		return toAjax(weiboDelService.updateWeiboDel(weiboDel));
	}
	
	/**
	 * 删除微博数据删除
	 */
	@RequiresPermissions("monitor:weiboDel:remove")
	@Log(title = "微博数据删除", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(weiboDelService.deleteWeiboDelByIds(ids));
	}
	
}
