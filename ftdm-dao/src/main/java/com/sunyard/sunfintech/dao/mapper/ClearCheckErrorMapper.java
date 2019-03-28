package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ClearCheckError;
import com.sunyard.sunfintech.dao.entity.ClearCheckErrorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClearCheckErrorMapper {
    /**
     *
     * @mbggenerated 2017-07-04
     */
    int countByExample(ClearCheckErrorExample example);

    /**
     *
     * @mbggenerated 2017-07-04
     */
    int deleteByExample(ClearCheckErrorExample example);

    /**
     *
     * @mbggenerated 2017-07-04
     */
    int insert(ClearCheckError record);

    /**
     *
     * @mbggenerated 2017-07-04
     */
    int insertSelective(ClearCheckError record);

    /**
     *
     * @mbggenerated 2017-07-04
     */
    List<ClearCheckError> selectByExample(ClearCheckErrorExample example);

    /**
     *
     * @mbggenerated 2017-07-04
     */
    int updateByExampleSelective(@Param("record") ClearCheckError record, @Param("example") ClearCheckErrorExample example);

    /**
     *
     * @mbggenerated 2017-07-04
     */
    int updateByExample(@Param("record") ClearCheckError record, @Param("example") ClearCheckErrorExample example);
}