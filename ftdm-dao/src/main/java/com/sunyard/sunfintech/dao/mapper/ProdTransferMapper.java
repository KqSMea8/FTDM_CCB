package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ProdTransfer;
import com.sunyard.sunfintech.dao.entity.ProdTransferExample;
import com.sunyard.sunfintech.dao.entity.ProdTransferKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProdTransferMapper {
    /**
     *
     * @mbggenerated 2017-06-01
     */
    int countByExample(ProdTransferExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByExample(ProdTransferExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByPrimaryKey(ProdTransferKey key);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insert(ProdTransfer record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insertSelective(ProdTransfer record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<ProdTransfer> selectByExample(ProdTransferExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    ProdTransfer selectByPrimaryKey(ProdTransferKey key);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleSelective(@Param("record") ProdTransfer record, @Param("example") ProdTransferExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExample(@Param("record") ProdTransfer record, @Param("example") ProdTransferExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeySelective(ProdTransfer record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKey(ProdTransfer record);
}