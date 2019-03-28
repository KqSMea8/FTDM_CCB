package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.RwRecharge;
import com.sunyard.sunfintech.dao.entity.RwRechargeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RwRechargeMapper {
    /**
     *
     * @mbggenerated 2018-01-23
     */
    int countByExample(RwRechargeExample example);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    int deleteByExample(RwRechargeExample example);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    int insert(RwRecharge record);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    int insertSelective(RwRecharge record);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    List<RwRecharge> selectByExample(RwRechargeExample example);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    RwRecharge selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    int updateByExampleSelective(@Param("record") RwRecharge record, @Param("example") RwRechargeExample example);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    int updateByExample(@Param("record") RwRecharge record, @Param("example") RwRechargeExample example);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    int updateByPrimaryKeySelective(RwRecharge record);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    int updateByPrimaryKey(RwRecharge record);
}