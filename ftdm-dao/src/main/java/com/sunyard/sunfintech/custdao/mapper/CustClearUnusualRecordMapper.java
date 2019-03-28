package com.sunyard.sunfintech.custdao.mapper;

import com.sunyard.sunfintech.dao.entity.ClearUnusualRecord;
import com.sunyard.sunfintech.dao.entity.ClearUnusualRecordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustClearUnusualRecordMapper {
    /**
     *
     * @mbggenerated 2017-06-17
     */
    int countByExample(ClearUnusualRecordExample example);

    /**
     *
     * @mbggenerated 2017-06-17
     */
    int deleteByExample(ClearUnusualRecordExample example);

    /**
     *
     * @mbggenerated 2017-06-17
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-17
     */
    int insert(ClearUnusualRecord record);

    /**
     *
     * @mbggenerated 2017-06-17
     */
    int insertSelective(ClearUnusualRecord record);

    /**
     *
     * @mbggenerated 2017-06-17
     */
    List<ClearUnusualRecord> selectByExample(ClearUnusualRecordExample example);

    /**
     *
     * @mbggenerated 2017-06-17
     */
    ClearUnusualRecord selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-17
     */
    int updateByExampleSelective(@Param("record") ClearUnusualRecord record, @Param("example") ClearUnusualRecordExample example);

    /**
     *
     * @mbggenerated 2017-06-17
     */
    int updateByExample(@Param("record") ClearUnusualRecord record, @Param("example") ClearUnusualRecordExample example);

    /**
     *
     * @mbggenerated 2017-06-17
     */
    int updateByPrimaryKeySelective(ClearUnusualRecord record);

    /**
     *
     * @mbggenerated 2017-06-17
     */
    int updateByPrimaryKey(ClearUnusualRecord record);

    int insertMore(List<ClearUnusualRecord> list);
}