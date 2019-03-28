package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ClearListCheckResult;
import com.sunyard.sunfintech.dao.entity.ClearListCheckResultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClearListCheckResultMapper {
    /**
     *
     * @mbggenerated 2018-06-21
     */
    int countByExample(ClearListCheckResultExample example);

    /**
     *
     * @mbggenerated 2018-06-21
     */
    int deleteByExample(ClearListCheckResultExample example);

    /**
     *
     * @mbggenerated 2018-06-21
     */
    int insert(ClearListCheckResult record);

    /**
     *
     * @mbggenerated 2018-06-21
     */
    int insertSelective(ClearListCheckResult record);

    /**
     *
     * @mbggenerated 2018-06-21
     */
    List<ClearListCheckResult> selectByExample(ClearListCheckResultExample example);

    /**
     *
     * @mbggenerated 2018-06-21
     */
    int updateByExampleSelective(@Param("record") ClearListCheckResult record, @Param("example") ClearListCheckResultExample example);

    /**
     *
     * @mbggenerated 2018-06-21
     */
    int updateByExample(@Param("record") ClearListCheckResult record, @Param("example") ClearListCheckResultExample example);
}