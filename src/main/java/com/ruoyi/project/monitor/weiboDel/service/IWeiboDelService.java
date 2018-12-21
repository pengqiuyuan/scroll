package com.ruoyi.project.monitor.weiboDel.service;

import com.ruoyi.project.monitor.weiboDel.domain.WeiboDel;

import java.io.IOException;
import java.util.List;

import org.apache.http.ParseException;

/**
 * 微博数据删除 服务层
 * 
 * @author ruoyi
 * @date 2018-12-18
 */
public interface IWeiboDelService 
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
     * 删除微博数据删除信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteWeiboDelByIds(String ids);
	
	public void weiboIndex(String index, String type);
	
	public void weixinIndex(String index, String type);
	
	public void toutiaoIndex(String index, String type);
}
