package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.PlatPlatinfo;
import com.sunyard.sunfintech.dao.entity.PlatPlatinfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlatPlatinfoMapper {
    /**
     *
     * @mbggenerated 2017-06-01
     */
    int countByExample(PlatPlatinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByExample(PlatPlatinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insert(PlatPlatinfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insertSelective(PlatPlatinfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<PlatPlatinfo> selectByExampleWithBLOBs(PlatPlatinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<PlatPlatinfo> selectByExample(PlatPlatinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    PlatPlatinfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleSelective(@Param("record") PlatPlatinfo record, @Param("example") PlatPlatinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleWithBLOBs(@Param("record") PlatPlatinfo record, @Param("example") PlatPlatinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExample(@Param("record") PlatPlatinfo record, @Param("example") PlatPlatinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeySelective(PlatPlatinfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeyWithBLOBs(PlatPlatinfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKey(PlatPlatinfo record);
}