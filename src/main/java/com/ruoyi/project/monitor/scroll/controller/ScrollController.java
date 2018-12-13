package com.ruoyi.project.monitor.scroll.controller;

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
import com.ruoyi.project.monitor.scroll.domain.Scroll;
import com.ruoyi.project.monitor.scroll.service.IScrollService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 索引任务 信息操作处理
 * 
 * @author ruoyi
 * @date 2018-12-12
 */
@Controller
@RequestMapping("/monitor/scroll")
public class ScrollController extends BaseController
{
    private String prefix = "monitor/scroll";
	
	@Autowired
	private IScrollService scrollService;
	
	@RequiresPermissions("monitor:scroll:view")
	@GetMapping()
	public String scroll()
	{
	    return prefix + "/scroll";
	}
	
	/**
	 * 查询索引任务列表
	 */
	@RequiresPermissions("monitor:scroll:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Scroll scroll)
	{
		startPage();
        List<Scroll> list = scrollService.selectScrollList(scroll);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出索引任务列表
	 */
	@RequiresPermissions("monitor:scroll:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Scroll scroll)
    {
    	List<Scroll> list = scrollService.selectScrollList(scroll);
        ExcelUtil<Scroll> util = new ExcelUtil<Scroll>(Scroll.class);
        return util.exportExcel(list, "scroll");
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
	@RequiresPermissions("monitor:scroll:add")
	@Log(title = "索引任务", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Scroll scroll)
	{		
		return toAjax(scrollService.insertScroll(scroll));
	}

	/**
	 * 修改索引任务
	 */
	@GetMapping("/edit/{scrollId}")
	public String edit(@PathVariable("scrollId") Integer scrollId, ModelMap mmap)
	{
		Scroll scroll = scrollService.selectScrollById(scrollId);
		mmap.put("scroll", scroll);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存索引任务
	 */
	@RequiresPermissions("monitor:scroll:edit")
	@Log(title = "索引任务", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Scroll scroll)
	{		
		return toAjax(scrollService.updateScroll(scroll));
	}
	
	/**
	 * 删除索引任务
	 */
	@RequiresPermissions("monitor:scroll:remove")
	@Log(title = "索引任务", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(scrollService.deleteScrollByIds(ids));
	}
	
}
