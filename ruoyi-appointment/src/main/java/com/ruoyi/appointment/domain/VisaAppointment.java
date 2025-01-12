package com.ruoyi.appointment.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Appointment list对象 visa_appointment
 *
 * @author zeyu
 * @date 2025-01-07
 */
public class VisaAppointment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Primary key ID */
    private Long id;

    /** ID of the related activity */
    @Excel(name = "ID of the related activity")
    private Long activityId;

    /** Appointment name */
    @Excel(name = "Appointment name")
    private String appointmentName;

    /** User ID */
    @Excel(name = "User ID")
    private Long userId;

    /** User name */
    @Excel(name = "User name")
    private String userName;

    /** Appointment time */
    @Excel(name = "Appointment time", width = 30)
    private String appointmentTime;

    /** Role id */
    private Long roleId;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setActivityId(Long activityId)
    {
        this.activityId = activityId;
    }

    public Long getActivityId()
    {
        return activityId;
    }
    public void setAppointmentName(String appointmentName)
    {
        this.appointmentName = appointmentName;
    }

    public String getAppointmentName()
    {
        return appointmentName;
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
    public void setAppointmentTime(String appointmentTime)
    {
        this.appointmentTime = appointmentTime;
    }

    public String getAppointmentTime()
    {
        return appointmentTime;
    }
    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    public Long getRoleId()
    {
        return roleId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("activityId", getActivityId())
                .append("appointmentName", getAppointmentName())
                .append("userId", getUserId())
                .append("userName", getUserName())
                .append("appointmentTime", getAppointmentTime())
                .append("roleId", getRoleId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}

