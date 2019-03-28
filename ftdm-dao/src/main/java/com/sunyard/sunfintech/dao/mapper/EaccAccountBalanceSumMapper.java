package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.EaccAccountBalanceSum;
import com.sunyard.sunfintech.dao.entity.EaccAccountBalanceSumExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EaccAccountBalanceSumMapper {
    /**
     *
     * @mbggenerated 2017-08-15
     */
    int countByExample(EaccAccountBalanceSumExample example);

    /**
     *
     * @mbggenerated 2017-08-15
     */
    int deleteByExample(EaccAccountBalanceSumExample example);

    /**
     *
     * @mbggenerated 2017-08-15
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-08-15
     */
    int insert(EaccAccountBalanceSum record);

    /**
     *
     * @mbggenerated 2017-08-15
     */
    int insertSelective(EaccAccountBalanceSum record);

    /**
     *
     * @mbggenerated 2017-08-15
     */
    List<EaccAccountBalanceSum> selectByExample(EaccAccountBalanceSumExample example);

    /**
     *
     * @mbggenerated 2017-08-15
     */
    EaccAccountBalanceSum selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-08-15
     */
    int updateByExampleSelective(@Param("record") EaccAccountBalanceSum record, @Param("example") EaccAccountBalanceSumExample example);

    /**
     *
     * @mbggenerated 2017-08-15
     */
    int updateByExample(@Param("record") EaccAccountBalanceSum record, @Param("example") EaccAccountBalanceSumExample example);

    /**
     *
     * @mbggenerated 2017-08-15
     */
    int updateByPrimaryKeySelective(EaccAccountBalanceSum record);

    /**
     *
     * @mbggenerated 2017-08-15
     */
    int updateByPrimaryKey(EaccAccountBalanceSum record);
}