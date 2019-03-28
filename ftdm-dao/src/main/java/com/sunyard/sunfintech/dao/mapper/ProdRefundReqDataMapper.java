package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ProdRefundReqData;
import com.sunyard.sunfintech.dao.entity.ProdRefundReqDataExample;
import com.sunyard.sunfintech.dao.entity.ProdRefundReqDataKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProdRefundReqDataMapper {
    /**
     *
     * @mbggenerated 2018-07-23
     */
    int countByExample(ProdRefundReqDataExample example);

    /**
     *
     * @mbggenerated 2018-07-23
     */
    int deleteByExample(ProdRefundReqDataExample example);

    /**
     *
     * @mbggenerated 2018-07-23
     */
    int deleteByPrimaryKey(ProdRefundReqDataKey key);

    /**
     *
     * @mbggenerated 2018-07-23
     */
    int insert(ProdRefundReqData record);

    /**
     *
     * @mbggenerated 2018-07-23
     */
    int insertSelective(ProdRefundReqData record);

    /**
     *
     * @mbggenerated 2018-07-23
     */
    List<ProdRefundReqData> selectByExample(ProdRefundReqDataExample example);

    /**
     *
     * @mbggenerated 2018-07-23
     */
    ProdRefundReqData selectByPrimaryKey(ProdRefundReqDataKey key);

    /**
     *
     * @mbggenerated 2018-07-23
     */
    int updateByExampleSelective(@Param("record") ProdRefundReqData record, @Param("example") ProdRefundReqDataExample example);

    /**
     *
     * @mbggenerated 2018-07-23
     */
    int updateByExample(@Param("record") ProdRefundReqData record, @Param("example") ProdRefundReqDataExample example);

    /**
     *
     * @mbggenerated 2018-07-23
     */
    int updateByPrimaryKeySelective(ProdRefundReqData record);

    /**
     *
     * @mbggenerated 2018-07-23
     */
    int updateByPrimaryKey(ProdRefundReqData record);
}