package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ComParam;
import com.sunyard.sunfintech.dao.entity.ComParamExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ComParamMapper {
    /**
     *
     * @mbggenerated 2017-10-27
     */
    int countByExample(ComParamExample example);

    /**
     *
     * @mbggenerated 2017-10-27
     */
    int deleteByExample(ComParamExample example);

    /**
     *
     * @mbggenerated 2017-10-27
     */
    int insert(ComParam record);

    /**
     *
     * @mbggenerated 2017-10-27
     */
    int insertSelective(ComParam record);

    /**
     *
     * @mbggenerated 2017-10-27
     */
    List<ComParam> selectByExample(ComParamExample example);

    /**
     *
     * @mbggenerated 2017-10-27
     */
    int updateByExampleSelective(@Param("record") ComParam record, @Param("example") ComParamExample example);

    /**
     *
     * @mbggenerated 2017-10-27
     */
    int updateByExample(@Param("record") ComParam record, @Param("example") ComParamExample example);
}