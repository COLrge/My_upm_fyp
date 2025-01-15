package com.ruoyi.appointment.mapper;

import java.util.List;
import com.ruoyi.appointment.domain.SubmitState;

/**
 * File Submission Status TableMapper接口
 * 
 * @author zeyu
 * @date 2025-01-16
 */
public interface SubmitStateMapper 
{
    /**
     * 查询File Submission Status Table
     * 
     * @param id File Submission Status Table主键
     * @return File Submission Status Table
     */
    public SubmitState selectSubmitStateById(Long id);

    /**
     * 查询File Submission Status Table列表
     * 
     * @param submitState File Submission Status Table
     * @return File Submission Status Table集合
     */
    public List<SubmitState> selectSubmitStateList(SubmitState submitState);

    /**
     * 新增File Submission Status Table
     * 
     * @param submitState File Submission Status Table
     * @return 结果
     */
    public int insertSubmitState(SubmitState submitState);

    /**
     * 修改File Submission Status Table
     * 
     * @param submitState File Submission Status Table
     * @return 结果
     */
    public int updateSubmitState(SubmitState submitState);

    /**
     * 删除File Submission Status Table
     * 
     * @param id File Submission Status Table主键
     * @return 结果
     */
    public int deleteSubmitStateById(Long id);

    /**
     * 批量删除File Submission Status Table
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSubmitStateByIds(Long[] ids);
}
