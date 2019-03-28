package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ClearTransSerialError;
import com.sunyard.sunfintech.dao.entity.ClearTransSerialErrorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClearTransSerialErrorMapper {
    /**
     *
     * @mbggenerated 2018-01-23
     */
    int countByExample(ClearTransSerialErrorExample example);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    int deleteByExample(ClearTransSerialErrorExample example);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    int insert(ClearTransSerialError record);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    int insertSelective(ClearTransSerialError record);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    List<ClearTransSerialError> selectByExample(ClearTransSerialErrorExample example);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    ClearTransSerialError selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    int updateByExampleSelective(@Param("record") ClearTransSerialError record, @Param("example") ClearTransSerialErrorExample example);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    int updateByExample(@Param("record") ClearTransSerialError record, @Param("example") ClearTransSerialErrorExample example);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    int updateByPrimaryKeySelective(ClearTransSerialError record);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    int updateByPrimaryKey(ClearTransSerialError record);
}