package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ProdCompensationRepay;
import com.sunyard.sunfintech.dao.entity.ProdCompensationRepayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProdCompensationRepayMapper {
    /**
     *
     * @mbggenerated 2017-06-27
     */
    int countByExample(ProdCompensationRepayExample example);

    /**
     *
     * @mbggenerated 2017-06-27
     */
    int deleteByExample(ProdCompensationRepayExample example);

    /**
     *
     * @mbggenerated 2017-06-27
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-27
     */
    int insert(ProdCompensationRepay record);

    /**
     *
     * @mbggenerated 2017-06-27
     */
    int insertSelective(ProdCompensationRepay record);

    /**
     *
     * @mbggenerated 2017-06-27
     */
    List<ProdCompensationRepay> selectByExample(ProdCompensationRepayExample example);

    /**
     *
     * @mbggenerated 2017-06-27
     */
    ProdCompensationRepay selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-27
     */
    int updateByExampleSelective(@Param("record") ProdCompensationRepay record, @Param("example") ProdCompensationRepayExample example);

    /**
     *
     * @mbggenerated 2017-06-27
     */
    int updateByExample(@Param("record") ProdCompensationRepay record, @Param("example") ProdCompensationRepayExample example);

    /**
     *
     * @mbggenerated 2017-06-27
     */
    int updateByPrimaryKeySelective(ProdCompensationRepay record);

    /**
     *
     * @mbggenerated 2017-06-27
     */
    int updateByPrimaryKey(ProdCompensationRepay record);
}