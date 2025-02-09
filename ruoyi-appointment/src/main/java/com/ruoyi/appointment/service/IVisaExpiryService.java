package com.ruoyi.appointment.service;

import java.util.List;
import com.ruoyi.appointment.domain.VisaExpiry;

/**
 * store_expirydayService接口
 * 
 * @author zeyu
 * @date 2025-01-21
 */
public interface IVisaExpiryService 
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
     * 批量删除store_expiryday
     * 
     * @param ids 需要删除的store_expiryday主键集合
     * @return 结果
     */
    public int deleteVisaExpiryByIds(Long[] ids);

    /**
     * 删除store_expiryday信息
     * 
     * @param id store_expiryday主键
     * @return 结果
     */
    public int deleteVisaExpiryById(Long id);
}
