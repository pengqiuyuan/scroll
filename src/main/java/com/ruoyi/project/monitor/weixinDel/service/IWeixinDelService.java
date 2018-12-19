package com.ruoyi.project.monitor.weixinDel.service;

import com.ruoyi.project.monitor.weixinDel.domain.WeixinDel;
import java.util.List;

/**
 * 微信数据删除 服务层
 * 
 * @author ruoyi
 * @date 2018-12-18
 */
public interface IWeixinDelService 
{
	/**
     * 查询微信数据删除信息
     * 
     * @param scrollId 微信数据删除ID
     * @return 微信数据删除信息
     */
	public WeixinDel selectWeixinDelById(Integer scrollId);
	
	/**
     * 查询微信数据删除列表
     * 
     * @param weixinDel 微信数据删除信息
     * @return 微信数据删除集合
     */
	public List<WeixinDel> selectWeixinDelList(WeixinDel weixinDel);
	
	/**
     * 新增微信数据删除
     * 
     * @param weixinDel 微信数据删除信息
     * @return 结果
     */
	public int insertWeixinDel(WeixinDel weixinDel);
	
	/**
     * 修改微信数据删除
     * 
     * @param weixinDel 微信数据删除信息
     * @return 结果
     */
	public int updateWeixinDel(WeixinDel weixinDel);
		
	/**
     * 删除微信数据删除信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteWeixinDelByIds(String ids);
	
}
