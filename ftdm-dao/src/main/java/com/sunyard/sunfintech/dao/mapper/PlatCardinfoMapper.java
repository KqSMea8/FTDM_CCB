package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.PlatCardinfo;
import com.sunyard.sunfintech.dao.entity.PlatCardinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlatCardinfoMapper {
    /**
     *
     * @mbggenerated 2017-06-01
     */
    int countByExample(PlatCardinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByExample(PlatCardinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insert(PlatCardinfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insertSelective(PlatCardinfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<PlatCardinfo> selectByExample(PlatCardinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    PlatCardinfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleSelective(@Param("record") PlatCardinfo record, @Param("example") PlatCardinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExample(@Param("record") PlatCardinfo record, @Param("example") PlatCardinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeySelective(PlatCardinfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKey(PlatCardinfo record);
}