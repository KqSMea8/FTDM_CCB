package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ProdShareList;
import com.sunyard.sunfintech.dao.entity.ProdShareListExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ProdShareListMapper {
    /**
     *
     * @mbggenerated 2017-06-01
     */
    int countByExample(ProdShareListExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByExample(ProdShareListExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insert(ProdShareList record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insertSelective(ProdShareList record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<ProdShareList> selectByExample(ProdShareListExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    ProdShareList selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleSelective(@Param("record") ProdShareList record, @Param("example") ProdShareListExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExample(@Param("record") ProdShareList record, @Param("example") ProdShareListExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeySelective(ProdShareList record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKey(ProdShareList record);

    List<Map<String,Object>> getInvestmentDetail(@Param("plat_no") String plat_no, @Param("prod_id") String prod_id);

}