package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.PayEaccountBind;
import com.sunyard.sunfintech.dao.entity.PayEaccountBindExample;
import com.sunyard.sunfintech.dao.entity.PayEaccountBindKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PayEaccountBindMapper {
    /**
     *
     * @mbggenerated 2018-07-18
     */
    int countByExample(PayEaccountBindExample example);

    /**
     *
     * @mbggenerated 2018-07-18
     */
    int deleteByExample(PayEaccountBindExample example);

    /**
     *
     * @mbggenerated 2018-07-18
     */
    int deleteByPrimaryKey(PayEaccountBindKey key);

    /**
     *
     * @mbggenerated 2018-07-18
     */
    int insert(PayEaccountBind record);

    /**
     *
     * @mbggenerated 2018-07-18
     */
    int insertSelective(PayEaccountBind record);

    /**
     *
     * @mbggenerated 2018-07-18
     */
    List<PayEaccountBind> selectByExample(PayEaccountBindExample example);

    /**
     *
     * @mbggenerated 2018-07-18
     */
    PayEaccountBind selectByPrimaryKey(PayEaccountBindKey key);

    /**
     *
     * @mbggenerated 2018-07-18
     */
    int updateByExampleSelective(@Param("record") PayEaccountBind record, @Param("example") PayEaccountBindExample example);

    /**
     *
     * @mbggenerated 2018-07-18
     */
    int updateByExample(@Param("record") PayEaccountBind record, @Param("example") PayEaccountBindExample example);

    /**
     *
     * @mbggenerated 2018-07-18
     */
    int updateByPrimaryKeySelective(PayEaccountBind record);

    /**
     *
     * @mbggenerated 2018-07-18
     */
    int updateByPrimaryKey(PayEaccountBind record);
}