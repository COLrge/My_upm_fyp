package com.ruoyi.appointment.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * File Submission Status Table对象 submit_state
 * 
 * @author zeyu
 * @date 2025-01-16
 */
public class SubmitState extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Primary Key */
    private Long id;

    /** File Name */
    @Excel(name = "File Name")
    private String fileName;

    /** Submission Status */
    @Excel(name = "Submission Status")
    private String submitStatus;

    /** User ID */
    @Excel(name = "User ID")
    private Long userId;

    /** User Name */
    @Excel(name = "User Name")
    private String userName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }
    public void setSubmitStatus(String submitStatus) 
    {
        this.submitStatus = submitStatus;
    }

    public String getSubmitStatus() 
    {
        return submitStatus;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("fileName", getFileName())
            .append("submitStatus", getSubmitStatus())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
