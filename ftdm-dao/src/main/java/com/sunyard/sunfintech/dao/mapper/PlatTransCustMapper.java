package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.PlatTransCust;
import com.sunyard.sunfintech.dao.entity.PlatTransCustExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlatTransCustMapper {
    /**
     *
     * @mbggenerated 2018-01-09
     */
    int countByExample(PlatTransCustExample example);

    /**
     *
     * @mbggenerated 2018-01-09
     */
    int deleteByExample(PlatTransCustExample example);

    /**
     *
     * @mbggenerated 2018-01-09
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-01-09
     */
    int insert(PlatTransCust record);

    /**
     *
     * @mbggenerated 2018-01-09
     */
    int insertSelective(PlatTransCust record);

    /**
     *
     * @mbggenerated 2018-01-09
     */
    List<PlatTransCust> selectByExample(PlatTransCustExample example);

    /**
     *
     * @mbggenerated 2018-01-09
     */
    PlatTransCust selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-01-09
     */
    int updateByExampleSelective(@Param("record") PlatTransCust record, @Param("example") PlatTransCustExample example);

    /**
     *
     * @mbggenerated 2018-01-09
     */
    int updateByExample(@Param("record") PlatTransCust record, @Param("example") PlatTransCustExample example);

    /**
     *
     * @mbggenerated 2018-01-09
     */
    int updateByPrimaryKeySelective(PlatTransCust record);

    /**
     *
     * @mbggenerated 2018-01-09
     */
    int updateByPrimaryKey(PlatTransCust record);
}