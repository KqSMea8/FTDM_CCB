package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.PayfeeInfo;
import com.sunyard.sunfintech.dao.entity.PayfeeInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PayfeeInfoMapper {
    /**
     *
     * @mbggenerated 2018-03-19
     */
    int countByExample(PayfeeInfoExample example);

    /**
     *
     * @mbggenerated 2018-03-19
     */
    int deleteByExample(PayfeeInfoExample example);

    /**
     *
     * @mbggenerated 2018-03-19
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-03-19
     */
    int insert(PayfeeInfo record);

    /**
     *
     * @mbggenerated 2018-03-19
     */
    int insertSelective(PayfeeInfo record);

    /**
     *
     * @mbggenerated 2018-03-19
     */
    List<PayfeeInfo> selectByExample(PayfeeInfoExample example);

    /**
     *
     * @mbggenerated 2018-03-19
     */
    PayfeeInfo selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-03-19
     */
    int updateByExampleSelective(@Param("record") PayfeeInfo record, @Param("example") PayfeeInfoExample example);

    /**
     *
     * @mbggenerated 2018-03-19
     */
    int updateByExample(@Param("record") PayfeeInfo record, @Param("example") PayfeeInfoExample example);

    /**
     *
     * @mbggenerated 2018-03-19
     */
    int updateByPrimaryKeySelective(PayfeeInfo record);

    /**
     *
     * @mbggenerated 2018-03-19
     */
    int updateByPrimaryKey(PayfeeInfo record);
}