package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ProdCompensationList;
import com.sunyard.sunfintech.dao.entity.ProdCompensationListExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ProdCompensationListMapper {
    /**
     *
     * @mbggenerated 2017-06-01
     */
    int countByExample(ProdCompensationListExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByExample(ProdCompensationListExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insert(ProdCompensationList record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insertSelective(ProdCompensationList record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<ProdCompensationList> selectByExample(ProdCompensationListExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    ProdCompensationList selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleSelective(@Param("record") ProdCompensationList record, @Param("example") ProdCompensationListExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExample(@Param("record") ProdCompensationList record, @Param("example") ProdCompensationListExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeySelective(ProdCompensationList record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKey(ProdCompensationList record);

    List<Map<String,Object>> getProdCompensationListByProdId(@Param("plat_no")String plat_no, @Param("prod_id")String prod_id);
}