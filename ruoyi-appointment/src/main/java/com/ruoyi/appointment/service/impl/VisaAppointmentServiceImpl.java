package com.ruoyi.appointment.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.appointment.mapper.VisaAppointmentMapper;
import com.ruoyi.appointment.domain.VisaAppointment;
import com.ruoyi.appointment.service.IVisaAppointmentService;

/**
 * Appointment listService业务层处理
 * 
 * @author zeyu
 * @date 2025-01-07
 */
@Service
public class VisaAppointmentServiceImpl implements IVisaAppointmentService 
{
    @Autowired
    private VisaAppointmentMapper visaAppointmentMapper;

    /**
     * 查询Appointment list
     * 
     * @param id Appointment list主键
     * @return Appointment list
     */
    @Override
    public VisaAppointment selectVisaAppointmentById(Long id)
    {
        return visaAppointmentMapper.selectVisaAppointmentById(id);
    }

    /**
     * 查询Appointment list列表
     * 
     * @param visaAppointment Appointment list
     * @return Appointment list
     */
    @Override
    public List<VisaAppointment> selectVisaAppointmentList(VisaAppointment visaAppointment)
    {
        return visaAppointmentMapper.selectVisaAppointmentList(visaAppointment);
    }

    /**
     * 新增Appointment list
     * 
     * @param visaAppointment Appointment list
     * @return 结果
     */
    @Override
    public int insertVisaAppointment(VisaAppointment visaAppointment)
    {
        visaAppointment.setCreateTime(DateUtils.getNowDate());
        return visaAppointmentMapper.insertVisaAppointment(visaAppointment);
    }

    /**
     * 修改Appointment list
     * 
     * @param visaAppointment Appointment list
     * @return 结果
     */
    @Override
    public int updateVisaAppointment(VisaAppointment visaAppointment)
    {
        visaAppointment.setUpdateTime(DateUtils.getNowDate());
        return visaAppointmentMapper.updateVisaAppointment(visaAppointment);
    }

    /**
     * 批量删除Appointment list
     * 
     * @param ids 需要删除的Appointment list主键
     * @return 结果
     */
    @Override
    public int deleteVisaAppointmentByIds(Long[] ids)
    {
        return visaAppointmentMapper.deleteVisaAppointmentByIds(ids);
    }

    /**
     * 删除Appointment list信息
     * 
     * @param id Appointment list主键
     * @return 结果
     */
    @Override
    public int deleteVisaAppointmentById(Long id)
    {
        return visaAppointmentMapper.deleteVisaAppointmentById(id);
    }
}
