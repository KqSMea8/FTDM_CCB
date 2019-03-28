package com.sunyard.sunfintech.custdao.mapper;

import com.sunyard.sunfintech.dao.entity.RwRecharge;
import com.sunyard.sunfintech.dao.entity.RwWithdraw;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by KouKi on 2017/6/9.
 */
public interface CustRwWithdrawMapper {

    /**
     * 查询所有提现
     *
     * @param map
     * @return
     */
    List<RwWithdraw> selectAllList(Map<String, Object> map);

    /**
     * 查询单条提现  只查询成功的20或处理中的24
     */
    RwWithdraw selectTransSerial(String trans_serial);

    /**
     * 查询单条提现  只查询成功的20或处理中的24
     */
    RwWithdraw selectTransSerialAndInitPay(String trans_serial);

    /**
     * 根据原申请订单号查询提现信息
     */
    List<RwWithdraw> selectByOriginOrderNo(String origin_order_no);

    /*
     * 查询处理中10天前到一个小时之前的数据
     */
    List<RwWithdraw> selectSolved(@Param("withdrawNum") Integer withdrawNum);

    /*
     * 查询处理待处理中的状态
     */
    List<RwWithdraw> selectWaitPay(@Param("withdrawNum") Integer withdrawNum);

    /*
     * 修改提现状态为处理中丢往代付
     */
    int updateByIdAndPayStatusByInit(RwWithdraw rwWithdraw);

    /*
     * 修改提现状态
     */
    int updateByIdAndPayStatusByProcessing(RwWithdraw rwWithdraw);

    /*
     * 修改提现状态
     */
    int updateByIdAndPayStatusByProcessingCCB(RwWithdraw rwWithdraw);

    /*
     * 只修提现申请
     */
    int updateByPrimaryKeySelective(RwWithdraw rwWithdraw);

    /*
     * 根据ID和老状态更新提现订单状态
     */
    int updateStatusByIdAndStatus(@Param("params") Map<String, Object> params);

    /**
     * 查询需要退款的提现
     * @param paycodelist
     * @param count
     * @return
     */
    List<RwWithdraw> getRefund(@Param("paycodelist") List<String> paycodelist, @Param("count") int count);

    /**
     * CCB-查询待处理的提现
     * @param withdrawNum
     * @return
     */
    List<RwWithdraw> selectWaitSend(@Param("withdrawNum") int withdrawNum);

    /**
     * 查询处理中的订单
     * @mbggenerated 2017-04-20
     */
    List<RwWithdraw> queryRwWithdrawByStatus(@Param("params") Map<String, Object> queryParams);
}
