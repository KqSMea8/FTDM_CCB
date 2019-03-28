package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ProdBorrowerRealrepay;
import com.sunyard.sunfintech.dao.entity.ProdBorrowerRealrepayExample;
import com.sunyard.sunfintech.dao.entity.ProdBorrowerRealrepayKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProdBorrowerRealrepayMapper {
    /**
     *
     * @mbggenerated 2017-06-01
     */
    int countByExample(ProdBorrowerRealrepayExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByExample(ProdBorrowerRealrepayExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByPrimaryKey(ProdBorrowerRealrepayKey key);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insert(ProdBorrowerRealrepay record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insertSelective(ProdBorrowerRealrepay record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<ProdBorrowerRealrepay> selectByExample(ProdBorrowerRealrepayExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    ProdBorrowerRealrepay selectByPrimaryKey(ProdBorrowerRealrepayKey key);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleSelective(@Param("record") ProdBorrowerRealrepay record, @Param("example") ProdBorrowerRealrepayExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExample(@Param("record") ProdBorrowerRealrepay record, @Param("example") ProdBorrowerRealrepayExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeySelective(ProdBorrowerRealrepay record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKey(ProdBorrowerRealrepay record);
}