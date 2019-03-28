package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.EaccFinancinfo;
import com.sunyard.sunfintech.dao.entity.EaccFinancinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EaccFinancinfoMapper {
    /**
     *
     * @mbggenerated 2017-09-15
     */
    int countByExample(EaccFinancinfoExample example);

    /**
     *
     * @mbggenerated 2017-09-15
     */
    int deleteByExample(EaccFinancinfoExample example);

    /**
     *
     * @mbggenerated 2017-09-15
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-09-15
     */
    int insert(EaccFinancinfo record);

    /**
     *
     * @mbggenerated 2017-09-15
     */
    int insertSelective(EaccFinancinfo record);

    /**
     *
     * @mbggenerated 2017-09-15
     */
    List<EaccFinancinfo> selectByExample(EaccFinancinfoExample example);

    /**
     *
     * @mbggenerated 2017-09-15
     */
    EaccFinancinfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-09-15
     */
    int updateByExampleSelective(@Param("record") EaccFinancinfo record, @Param("example") EaccFinancinfoExample example);

    /**
     *
     * @mbggenerated 2017-09-15
     */
    int updateByExample(@Param("record") EaccFinancinfo record, @Param("example") EaccFinancinfoExample example);

    /**
     *
     * @mbggenerated 2017-09-15
     */
    int updateByPrimaryKeySelective(EaccFinancinfo record);

    /**
     *
     * @mbggenerated 2017-09-15
     */
    int updateByPrimaryKey(EaccFinancinfo record);
}