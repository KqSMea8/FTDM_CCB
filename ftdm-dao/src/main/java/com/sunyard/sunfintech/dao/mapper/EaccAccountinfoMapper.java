package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.EaccAccountinfo;
import com.sunyard.sunfintech.dao.entity.EaccAccountinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EaccAccountinfoMapper {
    /**
     *
     * @mbggenerated 2017-06-01
     */
    int countByExample(EaccAccountinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByExample(EaccAccountinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insert(EaccAccountinfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insertSelective(EaccAccountinfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<EaccAccountinfo> selectByExample(EaccAccountinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    EaccAccountinfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleSelective(@Param("record") EaccAccountinfo record, @Param("example") EaccAccountinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExample(@Param("record") EaccAccountinfo record, @Param("example") EaccAccountinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeySelective(EaccAccountinfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKey(EaccAccountinfo record);
}