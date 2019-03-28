package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.RwWithdrawThird;
import com.sunyard.sunfintech.dao.entity.RwWithdrawThirdExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RwWithdrawThirdMapper {
    /**
     *
     * @mbggenerated 2018-05-16
     */
    int countByExample(RwWithdrawThirdExample example);

    /**
     *
     * @mbggenerated 2018-05-16
     */
    int deleteByExample(RwWithdrawThirdExample example);

    /**
     *
     * @mbggenerated 2018-05-16
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-05-16
     */
    int insert(RwWithdrawThird record);

    /**
     *
     * @mbggenerated 2018-05-16
     */
    int insertSelective(RwWithdrawThird record);

    /**
     *
     * @mbggenerated 2018-05-16
     */
    List<RwWithdrawThird> selectByExample(RwWithdrawThirdExample example);

    /**
     *
     * @mbggenerated 2018-05-16
     */
    RwWithdrawThird selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-05-16
     */
    int updateByExampleSelective(@Param("record") RwWithdrawThird record, @Param("example") RwWithdrawThirdExample example);

    /**
     *
     * @mbggenerated 2018-05-16
     */
    int updateByExample(@Param("record") RwWithdrawThird record, @Param("example") RwWithdrawThirdExample example);

    /**
     *
     * @mbggenerated 2018-05-16
     */
    int updateByPrimaryKeySelective(RwWithdrawThird record);

    /**
     *
     * @mbggenerated 2018-05-16
     */
    int updateByPrimaryKey(RwWithdrawThird record);
}