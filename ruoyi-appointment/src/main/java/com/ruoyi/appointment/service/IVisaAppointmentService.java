package com.ruoyi.appointment.service;

import java.util.List;
import com.ruoyi.appointment.domain.VisaAppointment;

/**
 * Appointment listService接口
 * 
 * @author zeyu
 * @date 2025-01-07
 */
public interface IVisaAppointmentService 
{
    /**
     * 查询Appointment list
     * 
     * @param id Appointment list主键
     * @return Appointment list
     */
    public VisaAppointment selectVisaAppointmentById(Long id);

    /**
     * 查询Appointment list列表
     * 
     * @param visaAppointment Appointment list
     * @return Appointment list集合
     */
    public List<VisaAppointment> selectVisaAppointmentList(VisaAppointment visaAppointment);

    /**
     * 新增Appointment list
     * 
     * @param visaAppointment Appointment list
     * @return 结果
     */
    public int insertVisaAppointment(VisaAppointment visaAppointment);

    /**
     * 修改Appointment list
     * 
     * @param visaAppointment Appointment list
     * @return 结果
     */
    public int updateVisaAppointment(VisaAppointment visaAppointment);

    /**
     * 批量删除Appointment list
     * 
     * @param ids 需要删除的Appointment list主键集合
     * @return 结果
     */
    public int deleteVisaAppointmentByIds(Long[] ids);

    /**
     * 删除Appointment list信息
     * 
     * @param id Appointment list主键
     * @return 结果
     */
    public int deleteVisaAppointmentById(Long id);
}
