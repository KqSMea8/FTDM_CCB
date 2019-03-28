package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ClearResult;
import com.sunyard.sunfintech.dao.entity.ClearResultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClearResultMapper {
    /**
     *
     * @mbggenerated 2017-06-14
     */
    int countByExample(ClearResultExample example);

    /**
     *
     * @mbggenerated 2017-06-14
     */
    int deleteByExample(ClearResultExample example);

    /**
     *
     * @mbggenerated 2017-06-14
     */
    int deleteByPrimaryKey(Integer pid);

    /**
     *
     * @mbggenerated 2017-06-14
     */
    int insert(ClearResult record);

    /**
     *
     * @mbggenerated 2017-06-14
     */
    int insertSelective(ClearResult record);

    /**
     *
     * @mbggenerated 2017-06-14
     */
    List<ClearResult> selectByExample(ClearResultExample example);

    /**
     *
     * @mbggenerated 2017-06-14
     */
    ClearResult selectByPrimaryKey(Integer pid);

    /**
     *
     * @mbggenerated 2017-06-14
     */
    int updateByExampleSelective(@Param("record") ClearResult record, @Param("example") ClearResultExample example);

    /**
     *
     * @mbggenerated 2017-06-14
     */
    int updateByExample(@Param("record") ClearResult record, @Param("example") ClearResultExample example);

    /**
     *
     * @mbggenerated 2017-06-14
     */
    int updateByPrimaryKeySelective(ClearResult record);

    /**
     *
     * @mbggenerated 2017-06-14
     */
    int updateByPrimaryKey(ClearResult record);
}