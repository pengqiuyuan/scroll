package com.ruoyi.project.monitor.weixinDel.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 微信数据删除表 sys_weixin_del
 * 
 * @author ruoyi
 * @date 2018-12-18
 */
public class WeixinDel extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 任务ID */
	private Integer scrollId;
	/** 任务开始时间（包含） */
	private String startDate;
	/** 任务截止时间（不包含） */
	private String endDate;
	/** 任务名称 */
	private String scrollGroupName;
	/** 任务组名（删除） */
	private String scrollGroup;
	/** 状态（0未开始 1进行中 2完成） */
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
	public void setScrollGroupName(String scrollGroupName) 
	{
		this.scrollGroupName = scrollGroupName;
	}

	public String getScrollGroupName() 
	{
		return scrollGroupName;
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

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("scrollId", getScrollId())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("scrollGroupName", getScrollGroupName())
            .append("scrollGroup", getScrollGroup())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
