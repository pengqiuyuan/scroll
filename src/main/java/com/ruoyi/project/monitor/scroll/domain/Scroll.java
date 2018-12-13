package com.ruoyi.project.monitor.scroll.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 索引任务表 sys_scroll
 * 
 * @author ruoyi
 * @date 2018-12-12
 */
public class Scroll extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 任务ID */
	private Integer scrollId;
	/** 任务开始时间（包含） */
	private String startDate;
	/** 任务截止时间（不包含） */
	private String endDate;
	/** 任务组名（压缩、索引） */
	private String scrollGroup;
	private String url;
	/** 状态（0未开始 1进行中 2已完成） */
	private String status;
	/** 创建者 */
	private String createBy;
	/** 创建时间 */
	private Date createTime;
	/** 更新者 */
	private String updateBy;
	/** 更新时间 */
	private Date updateTime;
	/** 备注信息 */
	private String remark;

	public void setScrollId(Integer scrollId) 
	{
		this.scrollId = scrollId;
	}

	public Integer getScrollId() 
	{
		return scrollId;
	}
	public void setStartDate(String startDate) 
	{
		this.startDate = startDate;
	}

	public String getStartDate() 
	{
		return startDate;
	}
	public void setEndDate(String endDate) 
	{
		this.endDate = endDate;
	}

	public String getEndDate() 
	{
		return endDate;
	}
	public void setScrollGroup(String scrollGroup) 
	{
		this.scrollGroup = scrollGroup;
	}

	public String getScrollGroup() 
	{
		return scrollGroup;
	}
	public void setStatus(String status) 
	{
		this.status = status;
	}

	public String getStatus() 
	{
		return status;
	}
	public void setCreateBy(String createBy) 
	{
		this.createBy = createBy;
	}

	public String getCreateBy() 
	{
		return createBy;
	}
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	public Date getCreateTime() 
	{
		return createTime;
	}
	public void setUpdateBy(String updateBy) 
	{
		this.updateBy = updateBy;
	}

	public String getUpdateBy() 
	{
		return updateBy;
	}
	public void setUpdateTime(Date updateTime) 
	{
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() 
	{
		return updateTime;
	}
	public void setRemark(String remark) 
	{
		this.remark = remark;
	}

	public String getRemark() 
	{
		return remark;
	}

    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("scrollId", getScrollId())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("scrollGroup", getScrollGroup())
            .append("scrollGroup", getScrollGroup())
            .append("status", getStatus())
            .append("url", getUrl())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
