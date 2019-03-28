package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.RwWithdraw;
import com.sunyard.sunfintech.dao.entity.RwWithdrawExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RwWithdrawMapper {
    /**
     *
     * @mbggenerated 2018-01-29
     */
    int countByExample(RwWithdrawExample example);

    /**
     *
     * @mbggenerated 2018-01-29
     */
    int deleteByExample(RwWithdrawExample example);

    /**
     *
     * @mbggenerated 2018-01-29
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-01-29
     */
    int insert(RwWithdraw record);

    /**
     *
     * @mbggenerated 2018-01-29
     */
    int insertSelective(RwWithdraw record);

    /**
     *
     * @mbggenerated 2018-01-29
     */
    List<RwWithdraw> selectByExample(RwWithdrawExample example);

    /**
     *
     * @mbggenerated 2018-01-29
     */
    RwWithdraw selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-01-29
     */
    int updateByExampleSelective(@Param("record") RwWithdraw record, @Param("example") RwWithdrawExample example);

    /**
     *
     * @mbggenerated 2018-01-29
     */
    int updateByExample(@Param("record") RwWithdraw record, @Param("example") RwWithdrawExample example);

    /**
     *
     * @mbggenerated 2018-01-29
     */
    int updateByPrimaryKeySelective(RwWithdraw record);

    /**
     *
     * @mbggenerated 2018-01-29
     */
    int updateByPrimaryKey(RwWithdraw record);
}