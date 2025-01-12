package com.ruoyi.visa.service;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.visa.domain.VisaApplication;

/**
 * Visa Application FormService接口
 * 
 * @author ruoyi
 * @date 2024-11-30
 */
public interface IVisaApplicationService 
{
    /**
     * 查询Visa Application Form
     * 
     * @param id Visa Application Form主键
     * @return Visa Application Form
     */
    public VisaApplication selectVisaApplicationById(Long id);

    /**
     * 查询Visa Application Form列表
     * 
     * @param visaApplication Visa Application Form
     * @return Visa Application Form集合
     */
    public List<VisaApplication> selectVisaApplicationList(VisaApplication visaApplication);

    /**
     * 新增Visa Application Form
     * 
     * @param visaApplication Visa Application Form
     * @return 结果
     */
    public int insertVisaApplication(VisaApplication visaApplication);

    /**
     * 修改Visa Application Form
     * 
     * @param visaApplication Visa Application Form
     * @return 结果
     */
    public int updateVisaApplication(VisaApplication visaApplication);

    /**
     * 批量删除Visa Application Form
     * 
     * @param ids 需要删除的Visa Application Form主键集合
     * @return 结果
     */
    public int deleteVisaApplicationByIds(Long[] ids);

    /**
     * 删除Visa Application Form信息
     * 
     * @param id Visa Application Form主键
     * @return 结果
     */
    public int deleteVisaApplicationById(Long id);

    public int updateVisaApplicationStatus(Long id, String status, String remarks, Long reviewerId);
    public int assignReviewer(Long id, Long reviewerId);
    public boolean hasActiveApplication(Long applicantId);
    public SysUser getReviewerInfo();
}
