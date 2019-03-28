package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ProdProductinfo;
import com.sunyard.sunfintech.dao.entity.ProdProductinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProdProductinfoMapper {
    /**
     *
     * @mbggenerated 2018-02-27
     */
    int countByExample(ProdProductinfoExample example);

    /**
     *
     * @mbggenerated 2018-02-27
     */
    int deleteByExample(ProdProductinfoExample example);

    /**
     *
     * @mbggenerated 2018-02-27
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-02-27
     */
    int insert(ProdProductinfo record);

    /**
     *
     * @mbggenerated 2018-02-27
     */
    int insertSelective(ProdProductinfo record);

    /**
     *
     * @mbggenerated 2018-02-27
     */
    List<ProdProductinfo> selectByExample(ProdProductinfoExample example);

    /**
     *
     * @mbggenerated 2018-02-27
     */
    ProdProductinfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-02-27
     */
    int updateByExampleSelective(@Param("record") ProdProductinfo record, @Param("example") ProdProductinfoExample example);

    /**
     *
     * @mbggenerated 2018-02-27
     */
    int updateByExample(@Param("record") ProdProductinfo record, @Param("example") ProdProductinfoExample example);

    /**
     *
     * @mbggenerated 2018-02-27
     */
    int updateByPrimaryKeySelective(ProdProductinfo record);

    /**
     *
     * @mbggenerated 2018-02-27
     */
    int updateByPrimaryKey(ProdProductinfo record);
}