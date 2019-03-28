package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ProdProductinfoExt;
import com.sunyard.sunfintech.dao.entity.ProdProductinfoExtExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProdProductinfoExtMapper {
    /**
     *
     * @mbggenerated 2017-05-31
     */
    int countByExample(ProdProductinfoExtExample example);

    /**
     *
     * @mbggenerated 2017-05-31
     */
    int deleteByExample(ProdProductinfoExtExample example);

    /**
     *
     * @mbggenerated 2017-05-31
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-05-31
     */
    int insert(ProdProductinfoExt record);

    /**
     *
     * @mbggenerated 2017-05-31
     */
    int insertSelective(ProdProductinfoExt record);

    /**
     *
     * @mbggenerated 2017-05-31
     */
    List<ProdProductinfoExt> selectByExample(ProdProductinfoExtExample example);

    /**
     *
     * @mbggenerated 2017-05-31
     */
    ProdProductinfoExt selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-05-31
     */
    int updateByExampleSelective(@Param("record") ProdProductinfoExt record, @Param("example") ProdProductinfoExtExample example);

    /**
     *
     * @mbggenerated 2017-05-31
     */
    int updateByExample(@Param("record") ProdProductinfoExt record, @Param("example") ProdProductinfoExtExample example);

    /**
     *
     * @mbggenerated 2017-05-31
     */
    int updateByPrimaryKeySelective(ProdProductinfoExt record);

    /**
     *
     * @mbggenerated 2017-05-31
     */
    int updateByPrimaryKey(ProdProductinfoExt record);
}