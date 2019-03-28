package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ClearAccountamtlist;
import com.sunyard.sunfintech.dao.entity.ClearAccountamtlistExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClearAccountamtlistMapper {
    /**
     *
     * @mbggenerated 2018-01-26
     */
    int countByExample(ClearAccountamtlistExample example);

    /**
     *
     * @mbggenerated 2018-01-26
     */
    int deleteByExample(ClearAccountamtlistExample example);

    /**
     *
     * @mbggenerated 2018-01-26
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-01-26
     */
    int insert(ClearAccountamtlist record);

    /**
     *
     * @mbggenerated 2018-01-26
     */
    int insertSelective(ClearAccountamtlist record);

    /**
     *
     * @mbggenerated 2018-01-26
     */
    List<ClearAccountamtlist> selectByExample(ClearAccountamtlistExample example);

    /**
     *
     * @mbggenerated 2018-01-26
     */
    ClearAccountamtlist selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-01-26
     */
    int updateByExampleSelective(@Param("record") ClearAccountamtlist record, @Param("example") ClearAccountamtlistExample example);

    /**
     *
     * @mbggenerated 2018-01-26
     */
    int updateByExample(@Param("record") ClearAccountamtlist record, @Param("example") ClearAccountamtlistExample example);

    /**
     *
     * @mbggenerated 2018-01-26
     */
    int updateByPrimaryKeySelective(ClearAccountamtlist record);

    /**
     *
     * @mbggenerated 2018-01-26
     */
    int updateByPrimaryKey(ClearAccountamtlist record);
}