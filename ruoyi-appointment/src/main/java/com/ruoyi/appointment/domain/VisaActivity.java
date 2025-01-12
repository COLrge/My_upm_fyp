package com.ruoyi.appointment.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Activity list对象 visa_activity
 * 
 * @author zeyu
 * @date 2025-01-05
 */
public class VisaActivity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Primary key ID */
    private Long id;

    /** activity's name */
    @Excel(name = "activity's name")
    private String activityName;

    /** Content of the activity */
    @Excel(name = "Content of the activity")
    private String activityContent;

    /** Open time */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "Open time", width = 30, dateFormat = "yyyy-MM-dd HH:mm")
    private Date openTime;

    /** Illustration image */
    @Excel(name = "Illustration image")
    private String activityImage;

    /** Close time */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "Close time", width = 30, dateFormat = "yyyy-MM-dd HH:mm")
    private Date closeTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setActivityName(String activityName) 
    {
        this.activityName = activityName;
    }

    public String getActivityName() 
    {
        return activityName;
    }
    public void setActivityContent(String activityContent) 
    {
        this.activityContent = activityContent;
    }

    public String getActivityContent() 
    {
        return activityContent;
    }
    public void setOpenTime(Date openTime) 
    {
        this.openTime = openTime;
    }

    public Date getOpenTime() 
    {
        return openTime;
    }
    public void setActivityImage(String activityImage) 
    {
        this.activityImage = activityImage;
    }

    public String getActivityImage() 
    {
        return activityImage;
    }
    public void setCloseTime(Date closeTime) 
    {
        this.closeTime = closeTime;
    }

    public Date getCloseTime() 
    {
        return closeTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("activityName", getActivityName())
            .append("activityContent", getActivityContent())
            .append("openTime", getOpenTime())
            .append("activityImage", getActivityImage())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("closeTime", getCloseTime())
            .toString();
    }
}
