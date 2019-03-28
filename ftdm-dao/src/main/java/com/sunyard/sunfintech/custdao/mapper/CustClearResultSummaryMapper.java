package com.sunyard.sunfintech.custdao.mapper;

import com.sunyard.sunfintech.dao.entity.ClearResultSummary;
import com.sunyard.sunfintech.dao.entity.ClearResultSummaryExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CustClearResultSummaryMapper {
    /**
     *
     * @mbggenerated 2017-06-19
     */
    int countByExample(ClearResultSummaryExample example);

    /**
     *
     * @mbggenerated 2017-06-19
     */
    int deleteByExample(ClearResultSummaryExample example);

    /**
     *
     * @mbggenerated 2017-06-19
     */
    int deleteByPrimaryKey(Integer pid);

    /**
     *
     * @mbggenerated 2017-06-19
     */
    int insert(ClearResultSummary record);

    /**
     *
     * @mbggenerated 2017-06-19
     */
    int insertSelective(ClearResultSummary record);

    /**
     *
     * @mbggenerated 2017-06-19
     */
    List<ClearResultSummary> selectByExample(ClearResultSummaryExample example);

    /**
     *
     * @mbggenerated 2017-06-19
     */
    ClearResultSummary selectByPrimaryKey(Integer pid);

    /**
     *
     * @mbggenerated 2017-06-19
     */
    int updateByExampleSelective(@Param("record") ClearResultSummary record, @Param("example") ClearResultSummaryExample example);

    /**
     *
     * @mbggenerated 2017-06-19
     */
    int updateByExample(@Param("record") ClearResultSummary record, @Param("example") ClearResultSummaryExample example);

    /**
     *
     * @mbggenerated 2017-06-19
     */
    int updateByPrimaryKeySelective(ClearResultSummary record);

    /**
     *
     * @mbggenerated 2017-06-19
     */
    int updateByPrimaryKey(ClearResultSummary record);

    int addClearResultSummary(Map<String, Object> map);

    List<Map<String, Object>> selectPlatSummary(Map<String, Object> map);

    /**
     * 更新recharge_count_plat，recharge_sum_plat，withdrawals_count_plat，withdrawals_sum_plat,balance_sum,balance_bank
     * @param list
     * @return
     */
    int updateResultSumBatch(List<Map<String,Object>> list);
}