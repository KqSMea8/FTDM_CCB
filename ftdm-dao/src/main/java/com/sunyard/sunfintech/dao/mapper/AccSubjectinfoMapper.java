package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.AccSubjectinfo;
import com.sunyard.sunfintech.dao.entity.AccSubjectinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccSubjectinfoMapper {
    /**
     *
     * @mbggenerated 2017-06-01
     */
    int countByExample(AccSubjectinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByExample(AccSubjectinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insert(AccSubjectinfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insertSelective(AccSubjectinfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<AccSubjectinfo> selectByExample(AccSubjectinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    AccSubjectinfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleSelective(@Param("record") AccSubjectinfo record, @Param("example") AccSubjectinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExample(@Param("record") AccSubjectinfo record, @Param("example") AccSubjectinfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeySelective(AccSubjectinfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKey(AccSubjectinfo record);
}