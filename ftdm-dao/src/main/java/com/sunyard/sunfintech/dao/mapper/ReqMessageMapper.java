package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ReqMessage;
import com.sunyard.sunfintech.dao.entity.ReqMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReqMessageMapper {
    /**
     *
     * @mbggenerated 2017-06-01
     */
    int countByExample(ReqMessageExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByExample(ReqMessageExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insert(ReqMessage record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insertSelective(ReqMessage record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<ReqMessage> selectByExampleWithBLOBs(ReqMessageExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<ReqMessage> selectByExample(ReqMessageExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    ReqMessage selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleSelective(@Param("record") ReqMessage record, @Param("example") ReqMessageExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleWithBLOBs(@Param("record") ReqMessage record, @Param("example") ReqMessageExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExample(@Param("record") ReqMessage record, @Param("example") ReqMessageExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeySelective(ReqMessage record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeyWithBLOBs(ReqMessage record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKey(ReqMessage record);
}