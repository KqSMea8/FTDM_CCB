package com.sunyard.sunfintech.custdao.mapper;

import com.sunyard.sunfintech.dao.entity.RwRecharge;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.dao.entity.TransTransreqExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CustTransTransreqMapper {
    List<TransTransreq> queryProcessingPayfee(Map<String, Object> map);
    List<TransTransreq> queryProdProcessing(Map<String, Object> map);

    /**
     * 查询处理中的订单
     * @mbggenerated 2017-04-20
     */
    List<TransTransreq> queryProcessOrders(@Param("params") Map<String, Object> queryPlatAccountParams);
}