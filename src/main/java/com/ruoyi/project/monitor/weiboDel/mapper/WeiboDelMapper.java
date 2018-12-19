package com.ruoyi.project.monitor.weiboDel.mapper;

import com.ruoyi.project.monitor.weiboDel.domain.WeiboDel;
import java.util.List;	

/**
 * 微博数据删除 数据层
 * 
 * @author ruoyi
 * @date 2018-12-18
 */
public interface WeiboDelMapper 
{
	/**
     * 查询微博数据删除信息
     * 
     * @param scrollId 微博数据删除ID
     * @return 微博数据删除信息
     */
	public WeiboDel selectWeiboDelById(Integer scrollId);
	
	/**
     * 查询微博数据删除列表
     * 
     * @param weiboDel 微博数据删除信息
     * @return 微博数据删除集合
     */
	public List<WeiboDel> selectWeiboDelList(WeiboDel weiboDel);
	
	/**
     * 新增微博数据删除
     * 
     * @param weiboDel 微博数据删除信息
     * @return 结果
     */
	public int insertWeiboDel(WeiboDel weiboDel);
	
	/**
     * 修改微博数据删除
     * 
     * @param weiboDel 微博数据删除信息
     * @return 结果
     */
	public int updateWeiboDel(WeiboDel weiboDel);
	
	/**
     * 删除微博数据删除
     * 
     * @param scrollId 微博数据删除ID
     * @return 结果
     */
	public int deleteWeiboDelById(Integer scrollId);
	
	/**
     * 批量删除微博数据删除
     * 
     * @param scrollIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteWeiboDelByIds(String[] scrollIds);
	
}