package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.EaccCardinfo;
import com.sunyard.sunfintech.dao.entity.EaccCardinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EaccCardinfoMapper {
    /**
     *
     * @mbggenerated 2017-06-01
     */
    int countByExample(EaccCardinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByExample(EaccCardinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insert(EaccCardinfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insertSelective(EaccCardinfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<EaccCardinfo> selectByExample(EaccCardinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    EaccCardinfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleSelective(@Param("record") EaccCardinfo record, @Param("example") EaccCardinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExample(@Param("record") EaccCardinfo record, @Param("example") EaccCardinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeySelective(EaccCardinfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKey(EaccCardinfo record);
}