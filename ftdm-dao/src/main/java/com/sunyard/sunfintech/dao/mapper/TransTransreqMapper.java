package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.dao.entity.TransTransreqExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TransTransreqMapper {
    /**
     *
     * @mbggenerated 2018-01-23
     */
    int countByExample(TransTransreqExample example);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    int deleteByExample(TransTransreqExample example);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    int insert(TransTransreq record);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    int insertSelective(TransTransreq record);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    List<TransTransreq> selectByExample(TransTransreqExample example);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    TransTransreq selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    int updateByExampleSelective(@Param("record") TransTransreq record, @Param("example") TransTransreqExample example);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    int updateByExample(@Param("record") TransTransreq record, @Param("example") TransTransreqExample example);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    int updateByPrimaryKeySelective(TransTransreq record);

    /**
     *
     * @mbggenerated 2018-01-23
     */
    int updateByPrimaryKey(TransTransreq record);
}