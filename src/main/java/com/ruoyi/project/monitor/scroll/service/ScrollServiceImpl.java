package com.ruoyi.project.monitor.scroll.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.monitor.scroll.mapper.ScrollMapper;
import com.ruoyi.project.monitor.scroll.domain.Scroll;
import com.ruoyi.project.monitor.scroll.service.IScrollService;
import com.ruoyi.common.support.Convert;
import com.ruoyi.common.utils.security.ShiroUtils;

/**
 * 索引任务 服务层实现
 * 
 * @author ruoyi
 * @date 2018-12-12
 */
@Service
public class ScrollServiceImpl implements IScrollService 
{
	@Autowired
	private ScrollMapper scrollMapper;

	/**
     * 查询索引任务信息
     * 
     * @param scrollId 索引任务ID
     * @return 索引任务信息
     */
    @Override
	public Scroll selectScrollById(Integer scrollId)
	{
	    return scrollMapper.selectScrollById(scrollId);
	}
	
	/**
     * 查询索引任务列表
     * 
     * @param scroll 索引任务信息
     * @return 索引任务集合
     */
	@Override
	public List<Scroll> selectScrollList(Scroll scroll)
	{
	    return scrollMapper.selectScrollList(scroll);
	}
	
    /**
     * 新增索引任务
     * 
     * @param scroll 索引任务信息
     * @return 结果
     */
	@Override
	public int insertScroll(Scroll scroll)
	{
		try {
			scroll.setCreateBy(ShiroUtils.getLoginName());
		} catch (Exception e) {
		}
	    return scrollMapper.insertScroll(scroll);
	}
	
	/**
     * 修改索引任务
     * 
     * @param scroll 索引任务信息
     * @return 结果
     */
	@Override
	public int updateScroll(Scroll scroll)
	{
		try {
			scroll.setUpdateBy(ShiroUtils.getLoginName());
		} catch (Exception e) {
		}
	    return scrollMapper.updateScroll(scroll);
	}

	/**
     * 删除索引任务对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteScrollByIds(String ids)
	{
		return scrollMapper.deleteScrollByIds(Convert.toStrArray(ids));
	}
	
}
