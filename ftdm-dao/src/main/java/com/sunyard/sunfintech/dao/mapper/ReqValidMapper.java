package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ReqValid;
import com.sunyard.sunfintech.dao.entity.ReqValidExample;
import com.sunyard.sunfintech.dao.entity.ReqValidWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReqValidMapper {
    /**
     *
     * @mbggenerated 2017-06-01
     */
    int countByExample(ReqValidExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByExample(ReqValidExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insert(ReqValidWithBLOBs record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insertSelective(ReqValidWithBLOBs record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<ReqValidWithBLOBs> selectByExampleWithBLOBs(ReqValidExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<ReqValid> selectByExample(ReqValidExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    ReqValidWithBLOBs selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleSelective(@Param("record") ReqValidWithBLOBs record, @Param("example") ReqValidExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleWithBLOBs(@Param("record") ReqValidWithBLOBs record, @Param("example") ReqValidExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExample(@Param("record") ReqValid record, @Param("example") ReqValidExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeySelective(ReqValidWithBLOBs record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeyWithBLOBs(ReqValidWithBLOBs record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKey(ReqValid record);
}