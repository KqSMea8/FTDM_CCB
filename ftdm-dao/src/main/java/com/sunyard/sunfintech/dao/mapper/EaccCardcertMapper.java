package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.EaccCardcert;
import com.sunyard.sunfintech.dao.entity.EaccCardcertExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EaccCardcertMapper {
    /**
     *
     * @mbggenerated 2017-11-21
     */
    int countByExample(EaccCardcertExample example);

    /**
     *
     * @mbggenerated 2017-11-21
     */
    int deleteByExample(EaccCardcertExample example);

    /**
     *
     * @mbggenerated 2017-11-21
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-11-21
     */
    int insert(EaccCardcert record);

    /**
     *
     * @mbggenerated 2017-11-21
     */
    int insertSelective(EaccCardcert record);

    /**
     *
     * @mbggenerated 2017-11-21
     */
    List<EaccCardcert> selectByExample(EaccCardcertExample example);

    /**
     *
     * @mbggenerated 2017-11-21
     */
    EaccCardcert selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-11-21
     */
    int updateByExampleSelective(@Param("record") EaccCardcert record, @Param("example") EaccCardcertExample example);

    /**
     *
     * @mbggenerated 2017-11-21
     */
    int updateByExample(@Param("record") EaccCardcert record, @Param("example") EaccCardcertExample example);

    /**
     *
     * @mbggenerated 2017-11-21
     */
    int updateByPrimaryKeySelective(EaccCardcert record);

    /**
     *
     * @mbggenerated 2017-11-21
     */
    int updateByPrimaryKey(EaccCardcert record);
}