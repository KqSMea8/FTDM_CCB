package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.AccOpenconfig;
import com.sunyard.sunfintech.dao.entity.AccOpenconfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccOpenconfigMapper {
    /**
     *
     * @mbggenerated 2017-06-07
     */
    int countByExample(AccOpenconfigExample example);

    /**
     *
     * @mbggenerated 2017-06-07
     */
    int deleteByExample(AccOpenconfigExample example);

    /**
     *
     * @mbggenerated 2017-06-07
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-07
     */
    int insert(AccOpenconfig record);

    /**
     *
     * @mbggenerated 2017-06-07
     */
    int insertSelective(AccOpenconfig record);

    /**
     *
     * @mbggenerated 2017-06-07
     */
    List<AccOpenconfig> selectByExample(AccOpenconfigExample example);

    /**
     *
     * @mbggenerated 2017-06-07
     */
    AccOpenconfig selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-07
     */
    int updateByExampleSelective(@Param("record") AccOpenconfig record, @Param("example") AccOpenconfigExample example);

    /**
     *
     * @mbggenerated 2017-06-07
     */
    int updateByExample(@Param("record") AccOpenconfig record, @Param("example") AccOpenconfigExample example);

    /**
     *
     * @mbggenerated 2017-06-07
     */
    int updateByPrimaryKeySelective(AccOpenconfig record);

    /**
     *
     * @mbggenerated 2017-06-07
     */
    int updateByPrimaryKey(AccOpenconfig record);
}