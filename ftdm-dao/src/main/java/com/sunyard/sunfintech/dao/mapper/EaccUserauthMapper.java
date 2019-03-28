package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.EaccUserauth;
import com.sunyard.sunfintech.dao.entity.EaccUserauthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EaccUserauthMapper {
    /**
     *
     * @mbggenerated 2018-01-10
     */
    int countByExample(EaccUserauthExample example);

    /**
     *
     * @mbggenerated 2018-01-10
     */
    int deleteByExample(EaccUserauthExample example);

    /**
     *
     * @mbggenerated 2018-01-10
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-01-10
     */
    int insert(EaccUserauth record);

    /**
     *
     * @mbggenerated 2018-01-10
     */
    int insertSelective(EaccUserauth record);

    /**
     *
     * @mbggenerated 2018-01-10
     */
    List<EaccUserauth> selectByExample(EaccUserauthExample example);

    /**
     *
     * @mbggenerated 2018-01-10
     */
    EaccUserauth selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-01-10
     */
    int updateByExampleSelective(@Param("record") EaccUserauth record, @Param("example") EaccUserauthExample example);

    /**
     *
     * @mbggenerated 2018-01-10
     */
    int updateByExample(@Param("record") EaccUserauth record, @Param("example") EaccUserauthExample example);

    /**
     *
     * @mbggenerated 2018-01-10
     */
    int updateByPrimaryKeySelective(EaccUserauth record);

    /**
     *
     * @mbggenerated 2018-01-10
     */
    int updateByPrimaryKey(EaccUserauth record);
}