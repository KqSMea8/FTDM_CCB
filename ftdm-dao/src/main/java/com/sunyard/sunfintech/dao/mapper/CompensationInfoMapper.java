package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.CompensationInfo;
import com.sunyard.sunfintech.dao.entity.CompensationInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CompensationInfoMapper {
    /**
     *
     * @mbggenerated 2017-06-01
     */
    int countByExample(CompensationInfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByExample(CompensationInfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insert(CompensationInfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insertSelective(CompensationInfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<CompensationInfo> selectByExample(CompensationInfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    CompensationInfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleSelective(@Param("record") CompensationInfo record, @Param("example") CompensationInfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExample(@Param("record") CompensationInfo record, @Param("example") CompensationInfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeySelective(CompensationInfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKey(CompensationInfo record);
}