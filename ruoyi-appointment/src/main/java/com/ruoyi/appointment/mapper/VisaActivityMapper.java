package com.ruoyi.appointment.mapper;

import java.util.List;
import com.ruoyi.appointment.domain.VisaActivity;

/**
 * Activity listMapper接口
 * 
 * @author zeyu
 * @date 2025-01-05
 */
public interface VisaActivityMapper 
{
    /**
     * 查询Activity list
     * 
     * @param id Activity list主键
     * @return Activity list
     */
    public VisaActivity selectVisaActivityById(Long id);

    /**
     * 查询Activity list列表
     * 
     * @param visaActivity Activity list
     * @return Activity list集合
     */
    public List<VisaActivity> selectVisaActivityList(VisaActivity visaActivity);

    /**
     * 新增Activity list
     * 
     * @param visaActivity Activity list
     * @return 结果
     */
    public int insertVisaActivity(VisaActivity visaActivity);

    /**
     * 修改Activity list
     * 
     * @param visaActivity Activity list
     * @return 结果
     */
    public int updateVisaActivity(VisaActivity visaActivity);

    /**
     * 删除Activity list
     * 
     * @param id Activity list主键
     * @return 结果
     */
    public int deleteVisaActivityById(Long id);

    /**
     * 批量删除Activity list
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVisaActivityByIds(Long[] ids);
}
