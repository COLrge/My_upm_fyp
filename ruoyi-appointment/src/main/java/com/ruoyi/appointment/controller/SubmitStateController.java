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
import com.ruoyi.appointment.domain.SubmitState;
import com.ruoyi.appointment.service.ISubmitStateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * File Submission Status TableController
 * 
 * @author zeyu
 * @date 2025-01-16
 */
@RestController
@RequestMapping("/file_submit/file_submit")
public class SubmitStateController extends BaseController
{
    @Autowired
    private ISubmitStateService submitStateService;

    /**
     * 查询File Submission Status Table列表
     */
    @PreAuthorize("@ss.hasPermi('file_submit:file_submit:list')")
    @GetMapping("/list")
    public TableDataInfo list(SubmitState submitState)
    {
        startPage();
        List<SubmitState> list = submitStateService.selectSubmitStateList(submitState);
        return getDataTable(list);
    }

    /**
     * 导出File Submission Status Table列表
     */
    @PreAuthorize("@ss.hasPermi('file_submit:file_submit:export')")
    @Log(title = "File Submission Status Table", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SubmitState submitState)
    {
        List<SubmitState> list = submitStateService.selectSubmitStateList(submitState);
        ExcelUtil<SubmitState> util = new ExcelUtil<SubmitState>(SubmitState.class);
        util.exportExcel(response, list, "File Submission Status Table数据");
    }

    /**
     * 获取File Submission Status Table详细信息
     */
    @PreAuthorize("@ss.hasPermi('file_submit:file_submit:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(submitStateService.selectSubmitStateById(id));
    }

    /**
     * 新增File Submission Status Table
     */
    @PreAuthorize("@ss.hasPermi('file_submit:file_submit:add')")
    @Log(title = "File Submission Status Table", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SubmitState submitState)
    {
        return toAjax(submitStateService.insertSubmitState(submitState));
    }

    /**
     * 修改File Submission Status Table
     */
    @PreAuthorize("@ss.hasPermi('file_submit:file_submit:edit')")
    @Log(title = "File Submission Status Table", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SubmitState submitState)
    {
        return toAjax(submitStateService.updateSubmitState(submitState));
    }

    /**
     * 删除File Submission Status Table
     */
    @PreAuthorize("@ss.hasPermi('file_submit:file_submit:remove')")
    @Log(title = "File Submission Status Table", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(submitStateService.deleteSubmitStateByIds(ids));
    }
}
