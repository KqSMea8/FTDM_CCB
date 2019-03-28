package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ProdCompensationInfo;
import com.sunyard.sunfintech.dao.entity.ProdCompensationInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProdCompensationInfoMapper {
    /**
     *
     * @mbggenerated 2017-06-27
     */
    int countByExample(ProdCompensationInfoExample example);

    /**
     *
     * @mbggenerated 2017-06-27
     */
    int deleteByExample(ProdCompensationInfoExample example);

    /**
     *
     * @mbggenerated 2017-06-27
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-27
     */
    int insert(ProdCompensationInfo record);

    /**
     *
     * @mbggenerated 2017-06-27
     */
    int insertSelective(ProdCompensationInfo record);

    /**
     *
     * @mbggenerated 2017-06-27
     */
    List<ProdCompensationInfo> selectByExample(ProdCompensationInfoExample example);

    /**
     *
     * @mbggenerated 2017-06-27
     */
    ProdCompensationInfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-27
     */
    int updateByExampleSelective(@Param("record") ProdCompensationInfo record, @Param("example") ProdCompensationInfoExample example);

    /**
     *
     * @mbggenerated 2017-06-27
     */
    int updateByExample(@Param("record") ProdCompensationInfo record, @Param("example") ProdCompensationInfoExample example);

    /**
     *
     * @mbggenerated 2017-06-27
     */
    int updateByPrimaryKeySelective(ProdCompensationInfo record);

    /**
     *
     * @mbggenerated 2017-06-27
     */
    int updateByPrimaryKey(ProdCompensationInfo record);
}