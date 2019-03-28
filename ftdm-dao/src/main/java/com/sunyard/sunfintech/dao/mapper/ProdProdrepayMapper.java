package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ProdProdrepay;
import com.sunyard.sunfintech.dao.entity.ProdProdrepayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProdProdrepayMapper {
    /**
     *
     * @mbggenerated 2017-06-01
     */
    int countByExample(ProdProdrepayExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByExample(ProdProdrepayExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insert(ProdProdrepay record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insertSelective(ProdProdrepay record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<ProdProdrepay> selectByExample(ProdProdrepayExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    ProdProdrepay selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleSelective(@Param("record") ProdProdrepay record, @Param("example") ProdProdrepayExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExample(@Param("record") ProdProdrepay record, @Param("example") ProdProdrepayExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeySelective(ProdProdrepay record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKey(ProdProdrepay record);
}