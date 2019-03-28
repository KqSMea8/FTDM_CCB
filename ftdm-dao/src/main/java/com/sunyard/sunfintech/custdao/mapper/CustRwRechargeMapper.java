package com.sunyard.sunfintech.custdao.mapper;

import com.sunyard.sunfintech.dao.entity.RwRecharge;

import java.util.List;
import java.util.Map;

/**
 * Created by dingjy on 2017/6/9.
 */
public interface CustRwRechargeMapper {

    /**
     * 批量插入RwRecharge；
     */
    int insertBatch(List<RwRecharge> rwRecharge);

    /**
     * 根据主键选择性批量更新RwRecharge；
     */
    int updateBatchByPrimaryKeySelective(List<RwRecharge> rwRecharge);

    List<RwRecharge> selectPayOffOnRecharge(Map<String, Object> map);

    /**
     * 查询所有非本行卡充值
     *
     * @param map
     * @return
     */
    List<RwRecharge> selectNotSelfBank(Map<String, Object> map);

    /**
     * 根据交易流水选择性更新status不为1的RwRecharge；
     */
    int updateByTransSerialSelective(RwRecharge rwRecharge);

    /**
     * @return
     */
    List<RwRecharge> queryProcessingRecharge(Map<String, Object> map);
}
