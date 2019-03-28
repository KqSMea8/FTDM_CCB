package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ProdTransferRecord;
import com.sunyard.sunfintech.dao.entity.ProdTransferRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProdTransferRecordMapper {
    /**
     *
     * @mbggenerated 2017-06-01
     */
    int countByExample(ProdTransferRecordExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByExample(ProdTransferRecordExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insert(ProdTransferRecord record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insertSelective(ProdTransferRecord record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<ProdTransferRecord> selectByExample(ProdTransferRecordExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    ProdTransferRecord selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleSelective(@Param("record") ProdTransferRecord record, @Param("example") ProdTransferRecordExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExample(@Param("record") ProdTransferRecord record, @Param("example") ProdTransferRecordExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeySelective(ProdTransferRecord record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKey(ProdTransferRecord record);
}