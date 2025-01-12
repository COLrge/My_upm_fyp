package com.ruoyi.visa.service.impl;

import java.util.List;
import java.util.Arrays;
import java.util.Date;

import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import eu.bitwalker.useragentutils.Application;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.visa.mapper.VisaApplicationMapper;
import com.ruoyi.visa.domain.VisaApplication;
import com.ruoyi.visa.service.IVisaApplicationService;

import com.ruoyi.common.utils.SecurityUtils;

/**
 * Visa Application FormService业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-30
 */
@Service
public class VisaApplicationServiceImpl implements IVisaApplicationService 
{
    @Autowired
    private VisaApplicationMapper visaApplicationMapper;


    
    /**
     * 查询Visa Application Form
     * 
     * @param id Visa Application Form主键
     * @return Visa Application Form
     */
    @Override
    public VisaApplication selectVisaApplicationById(Long id)
    {
        return visaApplicationMapper.selectVisaApplicationById(id);
    }

    /**
     * 查询Visa Application Form列表
     * 
     * @param visaApplication Visa Application Form
     * @return Visa Application Form
     */
    @Override
    public List<VisaApplication> selectVisaApplicationList(VisaApplication visaApplication)
    {
        // 获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getUser();
        // 判断用户的角色
        List<SysRole> roles = user.getRoles();
        // 遍历角色
        for (SysRole role : roles) {
            if (role.getRoleKey().equals("admin")) {
                break;
            }
            if (role.getRoleKey().equals("common")) {
                visaApplication.setApplicantId(user.getUserId());
                break;
            }
            if (role.getRoleKey().equals("reviewer")) {
                visaApplication.setReviewerId(user.getUserId());
                break;
            }
        }
        List<VisaApplication> list = visaApplicationMapper.selectVisaApplicationList(visaApplication);
        return list;
    }

    /**
     * 新增Visa Application Form
     * 
     * @param visaApplication Visa Application Form
     * @return 结果
     */
    @Override
    public int insertVisaApplication(VisaApplication visaApplication)
    {
        // 获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        visaApplication.setApplicantId(loginUser.getUserId());
        return visaApplicationMapper.insertVisaApplication(visaApplication);
    }

    /**
     * 修改Visa Application Form
     * 
     * @param visaApplication Visa Application Form
     * @return 结果
     */
    @Override
    public int updateVisaApplication(VisaApplication visaApplication)
    {
        // 重置状态
        visaApplication.setStatus("UNDER_REVIEW");
        return visaApplicationMapper.updateVisaApplication(visaApplication);
    }

    /**
     * 批量删除Visa Application Form
     * 
     * @param ids 需要删除的Visa Application Form主键
     * @return 结果
     */
    @Override
    public int deleteVisaApplicationByIds(Long[] ids)
    {
        return visaApplicationMapper.deleteVisaApplicationByIds(ids);
    }

    /**
     * 删除Visa Application Form信息
     * 
     * @param id Visa Application Form主键
     * @return 结果
     */
    @Override
    public int deleteVisaApplicationById(Long id)
    {
        return visaApplicationMapper.deleteVisaApplicationById(id);
    }

    /**
     * 更新签证申请审核状态
     */
    @Override
    public int updateVisaApplicationStatus(Long id, String status, String remarks, Long reviewerId) {
        if (!Arrays.asList("SUBMITTED", "UNDER_REVIEW", "APPROVED", "REJECTED")
                .contains(status)) {
            throw new RuntimeException("无效的状态");
        }
        
        VisaApplication visa = new VisaApplication();
        visa.setId(id);
        visa.setStatus(status);
        visa.setRemarks(remarks);
        visa.setUpdatedAt(new Date());
        
        return visaApplicationMapper.updateVisaApplication(visa);
    }

    /**
     * 分配审核人员
     */
    @Override
    public int assignReviewer(Long id, Long reviewerId) {
        VisaApplication visa = new VisaApplication();
        visa.setId(id);
        visa.setReviewerId(reviewerId);
        visa.setStatus("UNDER_REVIEW");
        visa.setUpdatedAt(new Date());
        
        return visaApplicationMapper.updateVisaApplication(visa);
    }

    /**
     * 检查用户是否有进行中的申请
     */
    @Override
    public boolean hasActiveApplication(Long applicantId) {
        VisaApplication query = new VisaApplication();
        query.setApplicantId(applicantId);
        List<VisaApplication> activeApplications = visaApplicationMapper.selectActiveApplications(query);
        return !activeApplications.isEmpty();
    }

    @Override
    public SysUser getReviewerInfo() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser reViewer = new SysUser();
        List<VisaApplication> applicationList = visaApplicationMapper.selectUnderReviewByUserId(loginUser.getUserId());
        if(applicationList!=null&&applicationList.size()>0){
            VisaApplication visaApplication = applicationList.get(0);
            reViewer = visaApplicationMapper.selectReviewerByApplicationId(visaApplication.getId());
        }
        return reViewer;
    }
}
