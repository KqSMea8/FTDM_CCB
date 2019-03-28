package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.QuartzUnionpay;
import com.sunyard.sunfintech.dao.entity.QuartzUnionpayExample;
import com.sunyard.sunfintech.dao.entity.QuartzUnionpayKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuartzUnionpayMapper {
    /**
     *
     * @mbggenerated 2017-07-07
     */
    int countByExample(QuartzUnionpayExample example);

    /**
     *
     * @mbggenerated 2017-07-07
     */
    int deleteByExample(QuartzUnionpayExample example);

    /**
     *
     * @mbggenerated 2017-07-07
     */
    int deleteByPrimaryKey(QuartzUnionpayKey key);

    /**
     *
     * @mbggenerated 2017-07-07
     */
    int insert(QuartzUnionpay record);

    /**
     *
     * @mbggenerated 2017-07-07
     */
    int insertSelective(QuartzUnionpay record);

    /**
     *
     * @mbggenerated 2017-07-07
     */
    List<QuartzUnionpay> selectByExample(QuartzUnionpayExample example);

    /**
     *
     * @mbggenerated 2017-07-07
     */
    QuartzUnionpay selectByPrimaryKey(QuartzUnionpayKey key);

    /**
     *
     * @mbggenerated 2017-07-07
     */
    int updateByExampleSelective(@Param("record") QuartzUnionpay record, @Param("example") QuartzUnionpayExample example);

    /**
     *
     * @mbggenerated 2017-07-07
     */
    int updateByExample(@Param("record") QuartzUnionpay record, @Param("example") QuartzUnionpayExample example);

    /**
     *
     * @mbggenerated 2017-07-07
     */
    int updateByPrimaryKeySelective(QuartzUnionpay record);

    /**
     *
     * @mbggenerated 2017-07-07
     */
    int updateByPrimaryKey(QuartzUnionpay record);
}