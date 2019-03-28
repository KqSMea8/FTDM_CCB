package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.OrderStatusChangeInfo;
import com.sunyard.sunfintech.dao.entity.OrderStatusChangeInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderStatusChangeInfoMapper {
    /**
     *
     * @mbggenerated 2017-08-14
     */
    int countByExample(OrderStatusChangeInfoExample example);

    /**
     *
     * @mbggenerated 2017-08-14
     */
    int deleteByExample(OrderStatusChangeInfoExample example);

    /**
     *
     * @mbggenerated 2017-08-14
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-08-14
     */
    int insert(OrderStatusChangeInfo record);

    /**
     *
     * @mbggenerated 2017-08-14
     */
    int insertSelective(OrderStatusChangeInfo record);

    /**
     *
     * @mbggenerated 2017-08-14
     */
    List<OrderStatusChangeInfo> selectByExample(OrderStatusChangeInfoExample example);

    /**
     *
     * @mbggenerated 2017-08-14
     */
    OrderStatusChangeInfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-08-14
     */
    int updateByExampleSelective(@Param("record") OrderStatusChangeInfo record, @Param("example") OrderStatusChangeInfoExample example);

    /**
     *
     * @mbggenerated 2017-08-14
     */
    int updateByExample(@Param("record") OrderStatusChangeInfo record, @Param("example") OrderStatusChangeInfoExample example);

    /**
     *
     * @mbggenerated 2017-08-14
     */
    int updateByPrimaryKeySelective(OrderStatusChangeInfo record);

    /**
     *
     * @mbggenerated 2017-08-14
     */
    int updateByPrimaryKey(OrderStatusChangeInfo record);
}