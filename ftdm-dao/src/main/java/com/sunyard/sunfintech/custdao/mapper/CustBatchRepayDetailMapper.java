package com.sunyard.sunfintech.custdao.mapper;

import com.sunyard.sunfintech.dao.entity.BatchRepayDetail;

import java.util.List;

/**
 * Created by terry on 2018/9/20.
 */
public interface CustBatchRepayDetailMapper {

    public int batchInsert(List<BatchRepayDetail> batchRepayDetailList);
}
