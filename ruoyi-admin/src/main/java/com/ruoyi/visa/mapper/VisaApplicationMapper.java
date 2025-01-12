package com.ruoyi.visa.mapper;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.visa.domain.VisaApplication;
import org.apache.ibatis.annotations.Mapper;

/**
 * Visa Application FormMapper接口
 * 
 * @author ruoyi
 * @date 2024-11-30
 */
@Mapper
public interface VisaApplicationMapper 
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
     * 查询用户进行中的申请
     */
    public List<VisaApplication> selectActiveApplications(VisaApplication visaApplication);
    /**
     * 查询用户是否存在正在审核中的申请
     *
     * @param userId 用户ID
     * @return 审核中的申请列表
     */
    public List<VisaApplication> selectUnderReviewByUserId(Long userId);
    /**
     * 查询签证申请的审核员信息
     * @param id 申请ID
     * @return 审核员信息
     */
    public SysUser selectReviewerByApplicationId(Long id);

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
     * 删除Visa Application Form
     * 
     * @param id Visa Application Form主键
     * @return 结果
     */
    public int deleteVisaApplicationById(Long id);

    /**
     * 批量删除Visa Application Form
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVisaApplicationByIds(Long[] ids);
}
