package com.ruoyi.project.monitor.weixinDel.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.monitor.weixinDel.mapper.WeixinDelMapper;
import com.ruoyi.project.monitor.weixinDel.domain.WeixinDel;
import com.ruoyi.project.monitor.weixinDel.service.IWeixinDelService;
import com.ruoyi.common.support.Convert;
import com.ruoyi.common.utils.security.ShiroUtils;

/**
 * 微信数据删除 服务层实现
 * 
 * @author ruoyi
 * @date 2018-12-18
 */
@Service
public class WeixinDelServiceImpl implements IWeixinDelService 
{
	@Autowired
	private WeixinDelMapper weixinDelMapper;

	/**
     * 查询微信数据删除信息
     * 
     * @param scrollId 微信数据删除ID
     * @return 微信数据删除信息
     */
    @Override
	public WeixinDel selectWeixinDelById(Integer scrollId)
	{
	    return weixinDelMapper.selectWeixinDelById(scrollId);
	}
	
	/**
     * 查询微信数据删除列表
     * 
     * @param weixinDel 微信数据删除信息
     * @return 微信数据删除集合
     */
	@Override
	public List<WeixinDel> selectWeixinDelList(WeixinDel weixinDel)
	{
	    return weixinDelMapper.selectWeixinDelList(weixinDel);
	}
	
    /**
     * 新增微信数据删除
     * 
     * @param weixinDel 微信数据删除信息
     * @return 结果
     */
	@Override
	public int insertWeixinDel(WeixinDel weixinDel)
	{
		try {
			weixinDel.setCreateBy(ShiroUtils.getLoginName());
		} catch (Exception e) {
		}
	    return weixinDelMapper.insertWeixinDel(weixinDel);
	}
	
	/**
     * 修改微信数据删除
     * 
     * @param weixinDel 微信数据删除信息
     * @return 结果
     */
	@Override
	public int updateWeixinDel(WeixinDel weixinDel)
	{
		try {
			weixinDel.setUpdateBy(ShiroUtils.getLoginName());
		} catch (Exception e) {
		}
	    return weixinDelMapper.updateWeixinDel(weixinDel);
	}

	/**
     * 删除微信数据删除对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteWeixinDelByIds(String ids)
	{
		return weixinDelMapper.deleteWeixinDelByIds(Convert.toStrArray(ids));
	}
	
}
