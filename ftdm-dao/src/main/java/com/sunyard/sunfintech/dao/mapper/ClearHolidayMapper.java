package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ClearHoliday;
import com.sunyard.sunfintech.dao.entity.ClearHolidayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClearHolidayMapper {
    /**
     *
     * @mbggenerated 2018-06-29
     */
    int countByExample(ClearHolidayExample example);

    /**
     *
     * @mbggenerated 2018-06-29
     */
    int deleteByExample(ClearHolidayExample example);

    /**
     *
     * @mbggenerated 2018-06-29
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-06-29
     */
    int insert(ClearHoliday record);

    /**
     *
     * @mbggenerated 2018-06-29
     */
    int insertSelective(ClearHoliday record);

    /**
     *
     * @mbggenerated 2018-06-29
     */
    List<ClearHoliday> selectByExample(ClearHolidayExample example);

    /**
     *
     * @mbggenerated 2018-06-29
     */
    ClearHoliday selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-06-29
     */
    int updateByExampleSelective(@Param("record") ClearHoliday record, @Param("example") ClearHolidayExample example);

    /**
     *
     * @mbggenerated 2018-06-29
     */
    int updateByExample(@Param("record") ClearHoliday record, @Param("example") ClearHolidayExample example);

    /**
     *
     * @mbggenerated 2018-06-29
     */
    int updateByPrimaryKeySelective(ClearHoliday record);

    /**
     *
     * @mbggenerated 2018-06-29
     */
    int updateByPrimaryKey(ClearHoliday record);
}