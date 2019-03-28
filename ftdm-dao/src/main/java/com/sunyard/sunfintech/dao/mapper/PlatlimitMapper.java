package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.Platlimit;
import com.sunyard.sunfintech.dao.entity.PlatlimitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlatlimitMapper {
    /**
     *
     * @mbggenerated 2017-10-11
     */
    int countByExample(PlatlimitExample example);

    /**
     *
     * @mbggenerated 2017-10-11
     */
    int deleteByExample(PlatlimitExample example);

    /**
     *
     * @mbggenerated 2017-10-11
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-10-11
     */
    int insert(Platlimit record);

    /**
     *
     * @mbggenerated 2017-10-11
     */
    int insertSelective(Platlimit record);

    /**
     *
     * @mbggenerated 2017-10-11
     */
    List<Platlimit> selectByExample(PlatlimitExample example);

    /**
     *
     * @mbggenerated 2017-10-11
     */
    Platlimit selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-10-11
     */
    int updateByExampleSelective(@Param("record") Platlimit record, @Param("example") PlatlimitExample example);

    /**
     *
     * @mbggenerated 2017-10-11
     */
    int updateByExample(@Param("record") Platlimit record, @Param("example") PlatlimitExample example);

    /**
     *
     * @mbggenerated 2017-10-11
     */
    int updateByPrimaryKeySelective(Platlimit record);

    /**
     *
     * @mbggenerated 2017-10-11
     */
    int updateByPrimaryKey(Platlimit record);

    int updateLimitAmt(Platlimit platlimit);
}