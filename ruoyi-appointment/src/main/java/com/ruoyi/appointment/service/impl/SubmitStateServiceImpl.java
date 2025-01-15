package com.ruoyi.appointment.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.appointment.mapper.SubmitStateMapper;
import com.ruoyi.appointment.domain.SubmitState;
import com.ruoyi.appointment.service.ISubmitStateService;

/**
 * File Submission Status TableService业务层处理
 * 
 * @author zeyu
 * @date 2025-01-16
 */
@Service
public class SubmitStateServiceImpl implements ISubmitStateService 
{
    @Autowired
    private SubmitStateMapper submitStateMapper;

    /**
     * 查询File Submission Status Table
     * 
     * @param id File Submission Status Table主键
     * @return File Submission Status Table
     */
    @Override
    public SubmitState selectSubmitStateById(Long id)
    {
        return submitStateMapper.selectSubmitStateById(id);
    }

    /**
     * 查询File Submission Status Table列表
     * 
     * @param submitState File Submission Status Table
     * @return File Submission Status Table
     */
    @Override
    public List<SubmitState> selectSubmitStateList(SubmitState submitState)
    {
        return submitStateMapper.selectSubmitStateList(submitState);
    }

    /**
     * 新增File Submission Status Table
     * 
     * @param submitState File Submission Status Table
     * @return 结果
     */
    @Override
    public int insertSubmitState(SubmitState submitState)
    {
        submitState.setCreateTime(DateUtils.getNowDate());
        return submitStateMapper.insertSubmitState(submitState);
    }

    /**
     * 修改File Submission Status Table
     * 
     * @param submitState File Submission Status Table
     * @return 结果
     */
    @Override
    public int updateSubmitState(SubmitState submitState)
    {
        submitState.setUpdateTime(DateUtils.getNowDate());
        return submitStateMapper.updateSubmitState(submitState);
    }

    /**
     * 批量删除File Submission Status Table
     * 
     * @param ids 需要删除的File Submission Status Table主键
     * @return 结果
     */
    @Override
    public int deleteSubmitStateByIds(Long[] ids)
    {
        return submitStateMapper.deleteSubmitStateByIds(ids);
    }

    /**
     * 删除File Submission Status Table信息
     * 
     * @param id File Submission Status Table主键
     * @return 结果
     */
    @Override
    public int deleteSubmitStateById(Long id)
    {
        return submitStateMapper.deleteSubmitStateById(id);
    }
}
