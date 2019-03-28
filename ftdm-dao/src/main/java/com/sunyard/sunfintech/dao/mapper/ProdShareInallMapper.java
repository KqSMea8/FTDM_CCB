package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ProdShareInall;
import com.sunyard.sunfintech.dao.entity.ProdShareInallExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProdShareInallMapper {
    /**
     *
     * @mbggenerated 2017-06-01
     */
    int countByExample(ProdShareInallExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByExample(ProdShareInallExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insert(ProdShareInall record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insertSelective(ProdShareInall record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<ProdShareInall> selectByExample(ProdShareInallExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    ProdShareInall selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleSelective(@Param("record") ProdShareInall record, @Param("example") ProdShareInallExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExample(@Param("record") ProdShareInall record, @Param("example") ProdShareInallExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeySelective(ProdShareInall record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKey(ProdShareInall record);
}