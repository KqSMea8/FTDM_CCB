package com.sunyard.sunfintech.pub.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.EaccTransTransreq;
import com.sunyard.sunfintech.dao.entity.EaccTransTransreqWithBLOBs;

import java.util.List;

/**
 * Created by terry on 2017/7/16.
 */
public interface IEaccTransTransreqService {

    /**
     * 添加单条流水
     * @param eaccTransTransreq
     * @return
     * @throws BusinessException
     */
    public boolean addFlow(EaccTransTransreqWithBLOBs eaccTransTransreq)throws BusinessException;

    /**
     * 添加批量流水
     * @param eaccTransTransreqList
     * @return
     * @throws BusinessException
     */
    public boolean addBatchFlow(List<EaccTransTransreqWithBLOBs> eaccTransTransreqList)throws BusinessException;

    /**
     * 根据订单流水号查询流水
     * @param trans_serial
     * @return
     * @throws BusinessException
     */
    public EaccTransTransreqWithBLOBs queryFlowByTransSerial(String trans_serial)throws BusinessException;

    /**
     * 根据交易流水号查询流水
     * @param parent_trans_serial
     * @return
     * @throws BusinessException
     */
    public List<EaccTransTransreqWithBLOBs> queryFlowByParentTransSerial(String parent_trans_serial)throws BusinessException;

    /**
     * 根据主键更新流水
     * @param eaccTransTransreq
     * @return
     * @throws BusinessException
     */
    public boolean updateFlowByPrimaryKey(EaccTransTransreqWithBLOBs eaccTransTransreq)throws BusinessException;

    /**
     * 批量根据主键更新流水
     * @param eaccTransTransreq
     * @return
     * @throws BusinessException
     */
    public boolean updateBatchFlowByPrimaryKey(List<EaccTransTransreqWithBLOBs> eaccTransTransreq)throws BusinessException;

    public boolean updateFlowByTransSerial(String trans_serial,EaccTransTransreqWithBLOBs eaccTransTransreq)throws BusinessException;
}
