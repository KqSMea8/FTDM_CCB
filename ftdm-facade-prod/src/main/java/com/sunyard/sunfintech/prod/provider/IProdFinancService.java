package com.sunyard.sunfintech.prod.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.MonitorFinancBalance;
import com.sunyard.sunfintech.dao.entity.PlatPlatinfo;

import java.util.List;

/**
 * 融资人待还余额统计
 */
public interface IProdFinancService {
    /**
     * 统计融资人单笔还款余额
     *
     * @param monitorDate
     * @return
     */
    void makeProdFinancBalance(String monitorDate, List<PlatPlatinfo> platPlatinfoList) throws BusinessException;
}
