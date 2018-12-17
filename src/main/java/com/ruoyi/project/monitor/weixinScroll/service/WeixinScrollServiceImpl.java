package com.ruoyi.project.monitor.weixinScroll.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.monitor.weixinScroll.mapper.WeixinScrollMapper;
import com.ruoyi.project.monitor.weixinScroll.domain.WeixinScroll;
import com.ruoyi.project.monitor.weixinScroll.service.IWeixinScrollService;
import com.ruoyi.common.support.Convert;
import com.ruoyi.common.utils.security.ShiroUtils;

/**
 * 索引任务 服务层实现
 * 
 * @author ruoyi
 * @date 2018-12-17
 */
@Service
public class WeixinScrollServiceImpl implements IWeixinScrollService 
{
	@Autowired
	private WeixinScrollMapper weixinScrollMapper;

	/**
     * 查询索引任务信息
     * 
     * @param scrollId 索引任务ID
     * @return 索引任务信息
     */
    @Override
	public WeixinScroll selectWeixinScrollById(Integer scrollId)
	{
	    return weixinScrollMapper.selectWeixinScrollById(scrollId);
	}
	
	/**
     * 查询索引任务列表
     * 
     * @param weixinScroll 索引任务信息
     * @return 索引任务集合
     */
	@Override
	public List<WeixinScroll> selectWeixinScrollList(WeixinScroll weixinScroll)
	{
	    return weixinScrollMapper.selectWeixinScrollList(weixinScroll);
	}
	
    /**
     * 新增索引任务
     * 
     * @param weixinScroll 索引任务信息
     * @return 结果
     */
	@Override
	public int insertWeixinScroll(WeixinScroll weixinScroll)
	{
		try {
			weixinScroll.setCreateBy(ShiroUtils.getLoginName());
		} catch (Exception e) {
		}
	    return weixinScrollMapper.insertWeixinScroll(weixinScroll);
	}
	
	/**
     * 修改索引任务
     * 
     * @param weixinScroll 索引任务信息
     * @return 结果
     */
	@Override
	public int updateWeixinScroll(WeixinScroll weixinScroll)
	{
		try {
			weixinScroll.setUpdateBy(ShiroUtils.getLoginName());
		} catch (Exception e) {
		}
	    return weixinScrollMapper.updateWeixinScroll(weixinScroll);
	}

	/**
     * 删除索引任务对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteWeixinScrollByIds(String ids)
	{
		return weixinScrollMapper.deleteWeixinScrollByIds(Convert.toStrArray(ids));
	}
	
}
