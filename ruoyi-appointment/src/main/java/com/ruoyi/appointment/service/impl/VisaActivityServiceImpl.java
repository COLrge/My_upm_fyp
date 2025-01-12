package com.ruoyi.appointment.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.appointment.mapper.VisaActivityMapper;
import com.ruoyi.appointment.domain.VisaActivity;
import com.ruoyi.appointment.service.IVisaActivityService;

/**
 * Activity listService业务层处理
 * 
 * @author zeyu
 * @date 2025-01-05
 */
@Service
public class VisaActivityServiceImpl implements IVisaActivityService 
{
    @Autowired
    private VisaActivityMapper visaActivityMapper;

    /**
     * 查询Activity list
     * 
     * @param id Activity list主键
     * @return Activity list
     */
    @Override
    public VisaActivity selectVisaActivityById(Long id)
    {
        return visaActivityMapper.selectVisaActivityById(id);
    }

    /**
     * 查询Activity list列表
     * 
     * @param visaActivity Activity list
     * @return Activity list
     */
    @Override
    public List<VisaActivity> selectVisaActivityList(VisaActivity visaActivity)
    {
        return visaActivityMapper.selectVisaActivityList(visaActivity);
    }

    /**
     * 新增Activity list
     * 
     * @param visaActivity Activity list
     * @return 结果
     */
    @Override
    public int insertVisaActivity(VisaActivity visaActivity)
    {
        visaActivity.setCreateTime(DateUtils.getNowDate());
        return visaActivityMapper.insertVisaActivity(visaActivity);
    }

    /**
     * 修改Activity list
     * 
     * @param visaActivity Activity list
     * @return 结果
     */
    @Override
    public int updateVisaActivity(VisaActivity visaActivity)
    {
        visaActivity.setUpdateTime(DateUtils.getNowDate());
        return visaActivityMapper.updateVisaActivity(visaActivity);
    }

    /**
     * 批量删除Activity list
     * 
     * @param ids 需要删除的Activity list主键
     * @return 结果
     */
    @Override
    public int deleteVisaActivityByIds(Long[] ids)
    {
        return visaActivityMapper.deleteVisaActivityByIds(ids);
    }

    /**
     * 删除Activity list信息
     * 
     * @param id Activity list主键
     * @return 结果
     */
    @Override
    public int deleteVisaActivityById(Long id)
    {
        return visaActivityMapper.deleteVisaActivityById(id);
    }
}
