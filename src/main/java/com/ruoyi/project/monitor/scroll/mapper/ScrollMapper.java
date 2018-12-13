package com.ruoyi.project.monitor.scroll.mapper;

import com.ruoyi.project.monitor.scroll.domain.Scroll;
import java.util.List;	

/**
 * 索引任务 数据层
 * 
 * @author ruoyi
 * @date 2018-12-12
 */
public interface ScrollMapper 
{
	/**
     * 查询索引任务信息
     * 
     * @param scrollId 索引任务ID
     * @return 索引任务信息
     */
	public Scroll selectScrollById(Integer scrollId);
	
	/**
     * 查询索引任务列表
     * 
     * @param scroll 索引任务信息
     * @return 索引任务集合
     */
	public List<Scroll> selectScrollList(Scroll scroll);
	
	/**
     * 新增索引任务
     * 
     * @param scroll 索引任务信息
     * @return 结果
     */
	public int insertScroll(Scroll scroll);
	
	/**
     * 修改索引任务
     * 
     * @param scroll 索引任务信息
     * @return 结果
     */
	public int updateScroll(Scroll scroll);
	
	/**
     * 删除索引任务
     * 
     * @param scrollId 索引任务ID
     * @return 结果
     */
	public int deleteScrollById(Integer scrollId);
	
	/**
     * 批量删除索引任务
     * 
     * @param scrollIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteScrollByIds(String[] scrollIds);
	
}