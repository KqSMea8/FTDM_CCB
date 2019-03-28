package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlistExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EaccAccountamtlistMapper {
    /**
     *
     * @mbggenerated 2018-01-06
     */
    int countByExample(EaccAccountamtlistExample example);

    /**
     *
     * @mbggenerated 2018-01-06
     */
    int deleteByExample(EaccAccountamtlistExample example);

    /**
     *
     * @mbggenerated 2018-01-06
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-01-06
     */
    int insert(EaccAccountamtlist record);

    /**
     *
     * @mbggenerated 2018-01-06
     */
    int insertSelective(EaccAccountamtlist record);

    /**
     *
     * @mbggenerated 2018-01-06
     */
    List<EaccAccountamtlist> selectByExample(EaccAccountamtlistExample example);

    /**
     *
     * @mbggenerated 2018-01-06
     */
    EaccAccountamtlist selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-01-06
     */
    int updateByExampleSelective(@Param("record") EaccAccountamtlist record, @Param("example") EaccAccountamtlistExample example);

    /**
     *
     * @mbggenerated 2018-01-06
     */
    int updateByExample(@Param("record") EaccAccountamtlist record, @Param("example") EaccAccountamtlistExample example);

    /**
     *
     * @mbggenerated 2018-01-06
     */
    int updateByPrimaryKeySelective(EaccAccountamtlist record);

    /**
     *
     * @mbggenerated 2018-01-06
     */
    int updateByPrimaryKey(EaccAccountamtlist record);
}