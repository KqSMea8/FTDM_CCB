package com.sunyard.sunfintech.custdao.mapper;

import com.sunyard.sunfintech.dao.entity.PlatTransCust;

public interface CustPlatTransCustMapper {
    /**
     * 扣除转账份额
     *
     * @param platTransCust
     * @return
     */
    int substractBalance(PlatTransCust platTransCust);
}