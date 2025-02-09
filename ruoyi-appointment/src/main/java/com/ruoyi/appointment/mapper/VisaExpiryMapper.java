package com.ruoyi.appointment.mapper;

import java.util.List;
import com.ruoyi.appointment.domain.VisaExpiry;

/**
 * store_expirydayMapper接口
 * 
 * @author zeyu
 * @date 2025-01-21
 */
public interface VisaExpiryMapper 
{
    /**
     * 查询store_expiryday
     * 
     * @param id store_expiryday主键
     * @return store_expiryday
     */
    public VisaExpiry selectVisaExpiryById(Long id);

    /**
     * 查询store_expiryday列表
     * 
     * @param visaExpiry store_expiryday
     * @return store_expiryday集合
     */
    public List<VisaExpiry> selectVisaExpiryList(VisaExpiry visaExpiry);

    /**
     * 新增store_expiryday
     * 
     * @param visaExpiry store_expiryday
     * @return 结果
     */
    public int insertVisaExpiry(VisaExpiry visaExpiry);

    /**
     * 修改store_expiryday
     * 
     * @param visaExpiry store_expiryday
     * @return 结果
     */
    public int updateVisaExpiry(VisaExpiry visaExpiry);

    /**
     * 删除store_expiryday
     * 
     * @param id store_expiryday主键
     * @return 结果
     */
    public int deleteVisaExpiryById(Long id);

    /**
     * 批量删除store_expiryday
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVisaExpiryByIds(Long[] ids);
}
