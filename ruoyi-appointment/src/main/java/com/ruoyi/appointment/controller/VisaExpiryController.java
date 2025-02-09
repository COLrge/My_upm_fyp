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
import com.ruoyi.appointment.domain.VisaExpiry;
import com.ruoyi.appointment.service.IVisaExpiryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * store_expirydayController
 * 
 * @author zeyu
 * @date 2025-01-21
 */
@RestController
@RequestMapping("/expiryday/expiry")
public class VisaExpiryController extends BaseController
{
    @Autowired
    private IVisaExpiryService visaExpiryService;

    /**
     * 查询store_expiryday列表
     */
    @PreAuthorize("@ss.hasPermi('expiryday:expiry:list')")
    @GetMapping("/list")
    public TableDataInfo list(VisaExpiry visaExpiry)
    {
        startPage();
        List<VisaExpiry> list = visaExpiryService.selectVisaExpiryList(visaExpiry);
        return getDataTable(list);
    }

    /**
     * 导出store_expiryday列表
     */
    @PreAuthorize("@ss.hasPermi('expiryday:expiry:export')")
    @Log(title = "store_expiryday", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VisaExpiry visaExpiry)
    {
        List<VisaExpiry> list = visaExpiryService.selectVisaExpiryList(visaExpiry);
        ExcelUtil<VisaExpiry> util = new ExcelUtil<VisaExpiry>(VisaExpiry.class);
        util.exportExcel(response, list, "store_expiryday数据");
    }

    /**
     * 获取store_expiryday详细信息
     */
    @PreAuthorize("@ss.hasPermi('expiryday:expiry:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(visaExpiryService.selectVisaExpiryById(id));
    }

    /**
     * 新增store_expiryday
     */
    @PreAuthorize("@ss.hasPermi('expiryday:expiry:add')")
    @Log(title = "store_expiryday", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VisaExpiry visaExpiry)
    {
        return toAjax(visaExpiryService.insertVisaExpiry(visaExpiry));
    }

    /**
     * 修改store_expiryday
     */
    @PreAuthorize("@ss.hasPermi('expiryday:expiry:edit')")
    @Log(title = "store_expiryday", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VisaExpiry visaExpiry)
    {
        return toAjax(visaExpiryService.updateVisaExpiry(visaExpiry));
    }

    /**
     * 删除store_expiryday
     */
    @PreAuthorize("@ss.hasPermi('expiryday:expiry:remove')")
    @Log(title = "store_expiryday", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(visaExpiryService.deleteVisaExpiryByIds(ids));
    }
}
