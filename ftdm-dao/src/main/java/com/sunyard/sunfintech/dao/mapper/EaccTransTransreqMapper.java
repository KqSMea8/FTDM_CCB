package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.EaccTransTransreq;
import com.sunyard.sunfintech.dao.entity.EaccTransTransreqExample;
import com.sunyard.sunfintech.dao.entity.EaccTransTransreqWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EaccTransTransreqMapper {
    /**
     *
     * @mbggenerated 2018-02-04
     */
    int countByExample(EaccTransTransreqExample example);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int deleteByExample(EaccTransTransreqExample example);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int insert(EaccTransTransreqWithBLOBs record);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int insertSelective(EaccTransTransreqWithBLOBs record);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    List<EaccTransTransreqWithBLOBs> selectByExampleWithBLOBs(EaccTransTransreqExample example);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    List<EaccTransTransreq> selectByExample(EaccTransTransreqExample example);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    EaccTransTransreqWithBLOBs selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int updateByExampleSelective(@Param("record") EaccTransTransreqWithBLOBs record, @Param("example") EaccTransTransreqExample example);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int updateByExampleWithBLOBs(@Param("record") EaccTransTransreqWithBLOBs record, @Param("example") EaccTransTransreqExample example);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int updateByExample(@Param("record") EaccTransTransreq record, @Param("example") EaccTransTransreqExample example);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int updateByPrimaryKeySelective(EaccTransTransreqWithBLOBs record);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int updateByPrimaryKeyWithBLOBs(EaccTransTransreqWithBLOBs record);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int updateByPrimaryKey(EaccTransTransreq record);
}