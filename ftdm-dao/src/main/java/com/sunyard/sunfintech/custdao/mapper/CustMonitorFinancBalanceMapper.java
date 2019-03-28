package com.sunyard.sunfintech.custdao.mapper;

import com.sunyard.sunfintech.dao.entity.MonitorFinancBalance;

import java.util.List;
import java.util.Map;

public interface CustMonitorFinancBalanceMapper {
    /**
     * 查询融资人剩余待还份额
     * @param map
     * @return
     */
    List<MonitorFinancBalance> getMonitorFinancBalance(Map<String, Object> map);

    /**
     * 插入融资人剩余待还份额
     * @param monitorFinancBalanceList
     */
    void insertMonitorFinancBalance(List<MonitorFinancBalance> monitorFinancBalanceList);
}