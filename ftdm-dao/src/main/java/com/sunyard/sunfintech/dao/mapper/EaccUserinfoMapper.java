package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.EaccUserinfo;
import com.sunyard.sunfintech.dao.entity.EaccUserinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EaccUserinfoMapper {
    /**
     *
     * @mbggenerated 2018-02-24
     */
    int countByExample(EaccUserinfoExample example);

    /**
     *
     * @mbggenerated 2018-02-24
     */
    int deleteByExample(EaccUserinfoExample example);

    /**
     *
     * @mbggenerated 2018-02-24
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-02-24
     */
    int insert(EaccUserinfo record);

    /**
     *
     * @mbggenerated 2018-02-24
     */
    int insertSelective(EaccUserinfo record);

    /**
     *
     * @mbggenerated 2018-02-24
     */
    List<EaccUserinfo> selectByExample(EaccUserinfoExample example);

    /**
     *
     * @mbggenerated 2018-02-24
     */
    EaccUserinfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-02-24
     */
    int updateByExampleSelective(@Param("record") EaccUserinfo record, @Param("example") EaccUserinfoExample example);

    /**
     *
     * @mbggenerated 2018-02-24
     */
    int updateByExample(@Param("record") EaccUserinfo record, @Param("example") EaccUserinfoExample example);

    /**
     *
     * @mbggenerated 2018-02-24
     */
    int updateByPrimaryKeySelective(EaccUserinfo record);

    /**
     *
     * @mbggenerated 2018-02-24
     */
    int updateByPrimaryKey(EaccUserinfo record);
}