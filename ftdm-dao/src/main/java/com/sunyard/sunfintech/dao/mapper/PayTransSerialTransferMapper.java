package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.PayTransSerialTransfer;
import com.sunyard.sunfintech.dao.entity.PayTransSerialTransferExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PayTransSerialTransferMapper {
    /**
     *
     * @mbggenerated 2018-07-18
     */
    int countByExample(PayTransSerialTransferExample example);

    /**
     *
     * @mbggenerated 2018-07-18
     */
    int deleteByExample(PayTransSerialTransferExample example);

    /**
     *
     * @mbggenerated 2018-07-18
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-07-18
     */
    int insert(PayTransSerialTransfer record);

    /**
     *
     * @mbggenerated 2018-07-18
     */
    int insertSelective(PayTransSerialTransfer record);

    /**
     *
     * @mbggenerated 2018-07-18
     */
    List<PayTransSerialTransfer> selectByExample(PayTransSerialTransferExample example);

    /**
     *
     * @mbggenerated 2018-07-18
     */
    PayTransSerialTransfer selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-07-18
     */
    int updateByExampleSelective(@Param("record") PayTransSerialTransfer record, @Param("example") PayTransSerialTransferExample example);

    /**
     *
     * @mbggenerated 2018-07-18
     */
    int updateByExample(@Param("record") PayTransSerialTransfer record, @Param("example") PayTransSerialTransferExample example);

    /**
     *
     * @mbggenerated 2018-07-18
     */
    int updateByPrimaryKeySelective(PayTransSerialTransfer record);

    /**
     *
     * @mbggenerated 2018-07-18
     */
    int updateByPrimaryKey(PayTransSerialTransfer record);
}