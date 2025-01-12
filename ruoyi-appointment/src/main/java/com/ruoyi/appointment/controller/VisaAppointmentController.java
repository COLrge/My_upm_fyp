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
import com.ruoyi.appointment.domain.VisaAppointment;
import com.ruoyi.appointment.service.IVisaAppointmentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * Appointment listController
 * 
 * @author zeyu
 * @date 2025-01-07
 */
@RestController
@RequestMapping("/appointment_record/appointment_record")
public class VisaAppointmentController extends BaseController
{
    @Autowired
    private IVisaAppointmentService visaAppointmentService;

    /**
     * 查询Appointment list列表
     */
    @PreAuthorize("@ss.hasPermi('appointment_record:appointment_record:list')")
    @GetMapping("/list")
    public TableDataInfo list(VisaAppointment visaAppointment)
    {
        startPage();
        List<VisaAppointment> list = visaAppointmentService.selectVisaAppointmentList(visaAppointment);
        return getDataTable(list);
    }

    /**
     * 导出Appointment list列表
     */
    @PreAuthorize("@ss.hasPermi('appointment_record:appointment_record:export')")
    @Log(title = "Appointment list", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VisaAppointment visaAppointment)
    {
        List<VisaAppointment> list = visaAppointmentService.selectVisaAppointmentList(visaAppointment);
        ExcelUtil<VisaAppointment> util = new ExcelUtil<VisaAppointment>(VisaAppointment.class);
        util.exportExcel(response, list, "Appointment list数据");
    }

    /**
     * 获取Appointment list详细信息
     */
    @PreAuthorize("@ss.hasPermi('appointment_record:appointment_record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(visaAppointmentService.selectVisaAppointmentById(id));
    }

    /**
     * 新增Appointment list
     */
    @PreAuthorize("@ss.hasPermi('appointment_record:appointment_record:add')")
    @Log(title = "Appointment list", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VisaAppointment visaAppointment)
    {
        return toAjax(visaAppointmentService.insertVisaAppointment(visaAppointment));
    }

    /**
     * 修改Appointment list
     */
    @PreAuthorize("@ss.hasPermi('appointment_record:appointment_record:edit')")
    @Log(title = "Appointment list", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VisaAppointment visaAppointment)
    {
        return toAjax(visaAppointmentService.updateVisaAppointment(visaAppointment));
    }

    /**
     * 删除Appointment list
     */
    @PreAuthorize("@ss.hasPermi('appointment_record:appointment_record:remove')")
    @Log(title = "Appointment list", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(visaAppointmentService.deleteVisaAppointmentByIds(ids));
    }
}
