package com.ruoyi.appointment.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.appointment.mapper.VisaExpiryMapper;
import com.ruoyi.appointment.domain.VisaExpiry;
import com.ruoyi.appointment.service.IVisaExpiryService;
import java.util.Date;

/**
 * store_expirydayService业务层处理
 * 
 * @author zeyu
 * @date 2025-01-21
 */
@Service
public class VisaExpiryServiceImpl implements IVisaExpiryService 
{
    /**
     * 根据用户ID查询签证过期时间
     *
     * @param userId 用户ID
     * @return 签证过期时间
     */
    @Override
    public Date selectVisaExpiryDateByUserId(Long userId)
    {
        return visaExpiryMapper.selectVisaExpiryDateByUserId(userId);
    }

    @Autowired
    private VisaExpiryMapper visaExpiryMapper;

    /**
     * 查询store_expiryday
     * 
     * @param id store_expiryday主键
     * @return store_expiryday
     */
    @Override
    public VisaExpiry selectVisaExpiryById(Long id)
    {
        return visaExpiryMapper.selectVisaExpiryById(id);
    }

    /**
     * 查询store_expiryday列表
     * 
     * @param visaExpiry store_expiryday
     * @return store_expiryday
     */
    @Override
    public List<VisaExpiry> selectVisaExpiryList(VisaExpiry visaExpiry)
    {
        return visaExpiryMapper.selectVisaExpiryList(visaExpiry);
    }

    /**
     * 新增store_expiryday
     * 
     * @param visaExpiry store_expiryday
     * @return 结果
     */
    @Override
    public int insertVisaExpiry(VisaExpiry visaExpiry)
    {
        visaExpiry.setCreateTime(DateUtils.getNowDate());
        return visaExpiryMapper.insertVisaExpiry(visaExpiry);
    }

    /**
     * 修改store_expiryday
     * 
     * @param visaExpiry store_expiryday
     * @return 结果
     */
    @Override
    public int updateVisaExpiry(VisaExpiry visaExpiry)
    {
        visaExpiry.setUpdateTime(DateUtils.getNowDate());
        return visaExpiryMapper.updateVisaExpiry(visaExpiry);
    }

    /**
     * 批量删除store_expiryday
     * 
     * @param ids 需要删除的store_expiryday主键
     * @return 结果
     */
    @Override
    public int deleteVisaExpiryByIds(Long[] ids)
    {
        return visaExpiryMapper.deleteVisaExpiryByIds(ids);
    }

    /**
     * 删除store_expiryday信息
     * 
     * @param id store_expiryday主键
     * @return 结果
     */
    @Override
    public int deleteVisaExpiryById(Long id)
    {
        return visaExpiryMapper.deleteVisaExpiryById(id);
    }
}
