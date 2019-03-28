package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ClearResultSummary;
import com.sunyard.sunfintech.dao.entity.ClearResultSummaryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClearResultSummaryMapper {
    /**
     *
     * @mbggenerated 2017-06-19
     */
    int countByExample(ClearResultSummaryExample example);

    /**
     *
     * @mbggenerated 2017-06-19
     */
    int deleteByExample(ClearResultSummaryExample example);

    /**
     *
     * @mbggenerated 2017-06-19
     */
    int deleteByPrimaryKey(Integer pid);

    /**
     *
     * @mbggenerated 2017-06-19
     */
    int insert(ClearResultSummary record);

    /**
     *
     * @mbggenerated 2017-06-19
     */
    int insertSelective(ClearResultSummary record);

    /**
     *
     * @mbggenerated 2017-06-19
     */
    List<ClearResultSummary> selectByExample(ClearResultSummaryExample example);

    /**
     *
     * @mbggenerated 2017-06-19
     */
    ClearResultSummary selectByPrimaryKey(Integer pid);

    /**
     *
     * @mbggenerated 2017-06-19
     */
    int updateByExampleSelective(@Param("record") ClearResultSummary record, @Param("example") ClearResultSummaryExample example);

    /**
     *
     * @mbggenerated 2017-06-19
     */
    int updateByExample(@Param("record") ClearResultSummary record, @Param("example") ClearResultSummaryExample example);

    /**
     *
     * @mbggenerated 2017-06-19
     */
    int updateByPrimaryKeySelective(ClearResultSummary record);

    /**
     *
     * @mbggenerated 2017-06-19
     */
    int updateByPrimaryKey(ClearResultSummary record);
}