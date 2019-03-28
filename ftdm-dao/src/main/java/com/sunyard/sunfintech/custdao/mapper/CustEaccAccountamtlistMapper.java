package com.sunyard.sunfintech.custdao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CustEaccAccountamtlistMapper extends BaseMapper<EaccAccountamtlist>{

    /**
     *
     * @param record
     * @return
     */
    ArrayList<String> selectAccountFund(EaccAccountamtlist record);

    /**
     * 查询汇总
     * @param map
     * @return
     */
    Map<String,Object> selectTolEacclist(Map<String,Object> map);

    Map<String,Object> selectTollist(Map<String,Object> map);

    Map<String,Object> selectTolPayCodeCtlist(Map<String,Object> map);

    Map<String,Object> selectTolPlatCtlist(Map<String,Object> map);

    List<Map<String,Object>> selectDetailPlatCtlist(Map<String,Object> map);

    List<EaccAccountamtlist>selectQueryEaccAccoun(@Param("trans_serial") String trans_serial);

    /**
     * 查询明细
     * @param map
     * @return
     */
    List<Map<String,Object>> selectDetailEacclist(Map<String,Object> map);

    List<Map<String,Object>> selectDetaillist(Map<String,Object> map);

    List<Map<String,Object>> selectDetailPayCodeCtlist(Map<String,Object> map);

    /**
     * 更新资金记录为已清算
     * @param allSwitch
     */
    void batchUpdateAllSwitch(List<Long> allSwitch);

    Map<String,Object> selectLimitByDay();

    Map<String,Object> selectLimitByMonth();

    /**
     * 更新资金记录为已部分清算
     * @param
     */
    int updatePartSwitch(EaccAccountamtlist eacc);

    /**
     * 查询待清算充值记录
     * @param plat_no 平台号
     * @param clear_date 清算日期
     * @param maxId 最大id
     * @param minId 最小id
     * @return
     */
    List<EaccAccountamtlist> getChargeList(@Param("plat_no")String plat_no,@Param("clear_date")Date clear_date,@Param("minId")long minId,@Param("maxId")long maxId);

    /**
     * 查询待清算资金流水
     * @param plat_no 平台号
     * @param maxId 最大id
     * @param minId 最小id
     * @return
     */
    List<EaccAccountamtlist> getTransferList(@Param("plat_no")String plat_no,@Param("minId")long minId,@Param("maxId")long maxId);

    /**
     * 集团（多平台）
     * @param plat_nos
     * @param clear_date
     * @return
     */
    List<EaccAccountamtlist> getMallChargeList(@Param("list")List<String> plat_nos,@Param("clear_date")Date clear_date);

    /**
     * 集团（多平台）
     * @param plat_nos
     * @param clear_date
     * @return
     */
    List<EaccAccountamtlist> getMallTransferList(@Param("list")List<String> plat_nos,@Param("clear_date")Date clear_date);

    /**
     * 集团（多平台,对账日期后的所有充值）
     * @param plat_nos
     * @param clear_date
     * @return
     */
    List<EaccAccountamtlist> getMallAllChargeList(@Param("list")List<String> plat_nos,@Param("clear_date")Date clear_date);

    /**
     * 获取清算记录
     * @param plat_no
     * @param clear_date
     * @param pay_code
     * @return
     */
    List<EaccAccountamtlist> getClearRecord(@Param("plat_no")String plat_no, @Param("clear_date")String clear_date, @Param("pay_code")String pay_code);


    Map<String,Long> getChargeScope(@Param("plat_no")String plat_no, @Param("clear_date")Date clear_date);

    Long getTransMaxId();

}