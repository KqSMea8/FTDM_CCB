package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.TransChangeCard;
import com.sunyard.sunfintech.dao.entity.TransChangeCardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TransChangeCardMapper {
    /**
     *
     * @mbggenerated 2017-09-26
     */
    int countByExample(TransChangeCardExample example);

    /**
     *
     * @mbggenerated 2017-09-26
     */
    int deleteByExample(TransChangeCardExample example);

    /**
     *
     * @mbggenerated 2017-09-26
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-09-26
     */
    int insert(TransChangeCard record);

    /**
     *
     * @mbggenerated 2017-09-26
     */
    int insertSelective(TransChangeCard record);

    /**
     *
     * @mbggenerated 2017-09-26
     */
    List<TransChangeCard> selectByExample(TransChangeCardExample example);

    /**
     *
     * @mbggenerated 2017-09-26
     */
    TransChangeCard selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-09-26
     */
    int updateByExampleSelective(@Param("record") TransChangeCard record, @Param("example") TransChangeCardExample example);

    /**
     *
     * @mbggenerated 2017-09-26
     */
    int updateByExample(@Param("record") TransChangeCard record, @Param("example") TransChangeCardExample example);

    /**
     *
     * @mbggenerated 2017-09-26
     */
    int updateByPrimaryKeySelective(TransChangeCard record);

    /**
     *
     * @mbggenerated 2017-09-26
     */
    int updateByPrimaryKey(TransChangeCard record);
}