package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ReqLog;
import com.sunyard.sunfintech.dao.entity.ReqLogExample;
import com.sunyard.sunfintech.dao.entity.ReqLogWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReqLogMapper {
    /**
     *
     * @mbggenerated 2018-01-18
     */
    int countByExample(ReqLogExample example);

    /**
     *
     * @mbggenerated 2018-01-18
     */
    int deleteByExample(ReqLogExample example);

    /**
     *
     * @mbggenerated 2018-01-18
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-01-18
     */
    int insert(ReqLogWithBLOBs record);

    /**
     *
     * @mbggenerated 2018-01-18
     */
    int insertSelective(ReqLogWithBLOBs record);

    /**
     *
     * @mbggenerated 2018-01-18
     */
    List<ReqLogWithBLOBs> selectByExampleWithBLOBs(ReqLogExample example);

    /**
     *
     * @mbggenerated 2018-01-18
     */
    List<ReqLog> selectByExample(ReqLogExample example);

    /**
     *
     * @mbggenerated 2018-01-18
     */
    ReqLogWithBLOBs selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-01-18
     */
    int updateByExampleSelective(@Param("record") ReqLogWithBLOBs record, @Param("example") ReqLogExample example);

    /**
     *
     * @mbggenerated 2018-01-18
     */
    int updateByExampleWithBLOBs(@Param("record") ReqLogWithBLOBs record, @Param("example") ReqLogExample example);

    /**
     *
     * @mbggenerated 2018-01-18
     */
    int updateByExample(@Param("record") ReqLog record, @Param("example") ReqLogExample example);

    /**
     *
     * @mbggenerated 2018-01-18
     */
    int updateByPrimaryKeySelective(ReqLogWithBLOBs record);

    /**
     *
     * @mbggenerated 2018-01-18
     */
    int updateByPrimaryKeyWithBLOBs(ReqLogWithBLOBs record);

    /**
     *
     * @mbggenerated 2018-01-18
     */
    int updateByPrimaryKey(ReqLog record);
}