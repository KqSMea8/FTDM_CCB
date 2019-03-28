package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.SunbobTaskUnionpay;
import com.sunyard.sunfintech.dao.entity.SunbobTaskUnionpayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SunbobTaskUnionpayMapper {
    /**
     *
     * @mbggenerated 2017-06-01
     */
    int countByExample(SunbobTaskUnionpayExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByExample(SunbobTaskUnionpayExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insert(SunbobTaskUnionpay record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insertSelective(SunbobTaskUnionpay record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<SunbobTaskUnionpay> selectByExample(SunbobTaskUnionpayExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    SunbobTaskUnionpay selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleSelective(@Param("record") SunbobTaskUnionpay record, @Param("example") SunbobTaskUnionpayExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExample(@Param("record") SunbobTaskUnionpay record, @Param("example") SunbobTaskUnionpayExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeySelective(SunbobTaskUnionpay record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKey(SunbobTaskUnionpay record);
}