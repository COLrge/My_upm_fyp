package com.ruoyi.appointment.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.appointment.domain.VisaActivity;
import com.ruoyi.appointment.service.IVisaActivityService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * Activity listController
 * 
 * @author zeyu
 * @date 2025-01-05
 */
@RestController
@RequestMapping("/activity_check/activity")
public class VisaActivityController extends BaseController
{
    @Autowired
    private IVisaActivityService visaActivityService;

    /**
     * 查询Activity list列表
     */
    @PreAuthorize("@ss.hasPermi('activity_check:activity:list')")
    @GetMapping("/list")
    public TableDataInfo list(VisaActivity visaActivity)
    {
        startPage();
        List<VisaActivity> list = visaActivityService.selectVisaActivityList(visaActivity);
        return getDataTable(list);
    }

    /**
     * 导出Activity list列表
     */
    @PreAuthorize("@ss.hasPermi('activity_check:activity:export')")
    @Log(title = "Activity list", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VisaActivity visaActivity)
    {
        List<VisaActivity> list = visaActivityService.selectVisaActivityList(visaActivity);
        ExcelUtil<VisaActivity> util = new ExcelUtil<VisaActivity>(VisaActivity.class);
        util.exportExcel(response, list, "Activity list数据");
    }

    /**
     * 获取Activity list详细信息
     */
    @PreAuthorize("@ss.hasPermi('activity_check:activity:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(visaActivityService.selectVisaActivityById(id));
    }

    /**
     * 新增Activity list
     */
    @PreAuthorize("@ss.hasPermi('activity_check:activity:add')")
    @Log(title = "Activity list", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VisaActivity visaActivity)
    {
        return toAjax(visaActivityService.insertVisaActivity(visaActivity));
    }

    /**
     * 修改Activity list
     */
    @PreAuthorize("@ss.hasPermi('activity_check:activity:edit')")
    @Log(title = "Activity list", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VisaActivity visaActivity)
    {
        return toAjax(visaActivityService.updateVisaActivity(visaActivity));
    }

    /**
     * 删除Activity list
     */
    @PreAuthorize("@ss.hasPermi('activity_check:activity:remove')")
    @Log(title = "Activity list", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(visaActivityService.deleteVisaActivityByIds(ids));
    }
}
