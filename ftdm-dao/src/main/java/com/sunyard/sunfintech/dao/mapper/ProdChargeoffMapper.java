package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ProdChargeoff;
import com.sunyard.sunfintech.dao.entity.ProdChargeoffExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProdChargeoffMapper {
    /**
     *
     * @mbggenerated 2017-06-01
     */
    int countByExample(ProdChargeoffExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByExample(ProdChargeoffExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insert(ProdChargeoff record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insertSelective(ProdChargeoff record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<ProdChargeoff> selectByExample(ProdChargeoffExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    ProdChargeoff selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleSelective(@Param("record") ProdChargeoff record, @Param("example") ProdChargeoffExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExample(@Param("record") ProdChargeoff record, @Param("example") ProdChargeoffExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeySelective(ProdChargeoff record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKey(ProdChargeoff record);
}