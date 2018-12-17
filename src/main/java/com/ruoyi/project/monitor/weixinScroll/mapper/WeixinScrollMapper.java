package com.ruoyi.project.monitor.weixinScroll.mapper;

import com.ruoyi.project.monitor.weixinScroll.domain.WeixinScroll;
import java.util.List;	

/**
 * 索引任务 数据层
 * 
 * @author ruoyi
 * @date 2018-12-17
 */
public interface WeixinScrollMapper 
{
	/**
     * 查询索引任务信息
     * 
     * @param scrollId 索引任务ID
     * @return 索引任务信息
     */
	public WeixinScroll selectWeixinScrollById(Integer scrollId);
	
	/**
     * 查询索引任务列表
     * 
     * @param weixinScroll 索引任务信息
     * @return 索引任务集合
     */
	public List<WeixinScroll> selectWeixinScrollList(WeixinScroll weixinScroll);
	
	/**
     * 新增索引任务
     * 
     * @param weixinScroll 索引任务信息
     * @return 结果
     */
	public int insertWeixinScroll(WeixinScroll weixinScroll);
	
	/**
     * 修改索引任务
     * 
     * @param weixinScroll 索引任务信息
     * @return 结果
     */
	public int updateWeixinScroll(WeixinScroll weixinScroll);
	
	/**
     * 删除索引任务
     * 
     * @param scrollId 索引任务ID
     * @return 结果
     */
	public int deleteWeixinScrollById(Integer scrollId);
	
	/**
     * 批量删除索引任务
     * 
     * @param scrollIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteWeixinScrollByIds(String[] scrollIds);
	
}