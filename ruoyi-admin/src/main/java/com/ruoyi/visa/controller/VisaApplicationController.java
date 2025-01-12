package com.ruoyi.visa.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.visa.domain.VisaApplication;
import com.ruoyi.visa.service.IVisaApplicationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import java.util.Date;

/**
 * Visa Application FormController
 * 
 * @author ruoyi
 * @date 2024-11-30
 */
@RestController
@RequestMapping("/system/application")
public class VisaApplicationController extends BaseController
{
    @Autowired
    private IVisaApplicationService visaApplicationService;


    
    /**
     * 查询Visa Application Form列表
     */
    @PreAuthorize("@ss.hasPermi('system:application:list')")
    @GetMapping("/list")
    public TableDataInfo list(VisaApplication visaApplication)
    {
        startPage();
        List<VisaApplication> list = visaApplicationService.selectVisaApplicationList(visaApplication);
        return getDataTable(list);
    }

    /**
     * 导出Visa Application Form列表
     */
    @PreAuthorize("@ss.hasPermi('system:application:export')")
    @Log(title = "Visa Application Form", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VisaApplication visaApplication)
    {
        List<VisaApplication> list = visaApplicationService.selectVisaApplicationList(visaApplication);
        ExcelUtil<VisaApplication> util = new ExcelUtil<VisaApplication>(VisaApplication.class);
        util.exportExcel(response, list, "Visa Application Form数据");
    }

    /**
     * 获取Visa Application Form详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:application:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(visaApplicationService.selectVisaApplicationById(id));
    }

    /**
     * 新增Visa Application Form
     */
    @PreAuthorize("@ss.hasPermi('system:application:add')")
    @PostMapping
    public AjaxResult add(@RequestBody VisaApplication visaApplication) {
        // 获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        visaApplication.setApplicantId(loginUser.getUserId());
        // 检查是否存在进行中的申请
        if (visaApplicationService.hasActiveApplication(loginUser.getUserId())) {
            return AjaxResult.error("已存在正在处理中的签证申请");
        }
        
        visaApplication.setStatus("SUBMITTED");
        visaApplication.setCreatedAt(new Date());
        return toAjax(visaApplicationService.insertVisaApplication(visaApplication));
    }

    /**
     * 修改Visa Application Form
     */
    @PreAuthorize("@ss.hasPermi('system:application:edit')")
    @Log(title = "Visa Application Form", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VisaApplication visaApplication)
    {
        return toAjax(visaApplicationService.updateVisaApplication(visaApplication));
    }

    /**
     * 删除Visa Application Form
     */
    @PreAuthorize("@ss.hasPermi('system:application:remove')")
    @Log(title = "Visa Application Form", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(visaApplicationService.deleteVisaApplicationByIds(ids));
    }

    /**
     * 审核签证申请
     */
    @PreAuthorize("@ss.hasPermi('system:application:review')")
    @PutMapping("/review/{id}")
    public AjaxResult review(@PathVariable("id") Long id, 
                            @RequestParam String status,
                            @RequestParam(required = false) String remarks) {
        return toAjax(visaApplicationService.updateVisaApplicationStatus(id, status, remarks, 
                      getUserId()));
    }

    /**
     * 分配审核人员
     */
    @PreAuthorize("@ss.hasPermi('system:application:assign')")  
    @PutMapping("/assign/{id}")
    public AjaxResult assignReviewer(@PathVariable("id") Long id,
                                   @RequestParam Long reviewerId) {
        return toAjax(visaApplicationService.assignReviewer(id, reviewerId));
    }

    /**
     * 获取签证申请的审核员信息
     */
//    @PreAuthorize("@ss.hasPermi('system:application:query')")
    @GetMapping("/getMySelfReviewer")
    public AjaxResult getReviewerInfo() {
        return success(visaApplicationService.getReviewerInfo());
    }
}
