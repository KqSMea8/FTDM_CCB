package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.EaccClearSubjectInfo;
import com.sunyard.sunfintech.dao.entity.EaccClearSubjectInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EaccClearSubjectInfoMapper {
    /**
     *
     * @mbggenerated 2017-08-09
     */
    int countByExample(EaccClearSubjectInfoExample example);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    int deleteByExample(EaccClearSubjectInfoExample example);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    int insert(EaccClearSubjectInfo record);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    int insertSelective(EaccClearSubjectInfo record);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    List<EaccClearSubjectInfo> selectByExample(EaccClearSubjectInfoExample example);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    EaccClearSubjectInfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    int updateByExampleSelective(@Param("record") EaccClearSubjectInfo record, @Param("example") EaccClearSubjectInfoExample example);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    int updateByExample(@Param("record") EaccClearSubjectInfo record, @Param("example") EaccClearSubjectInfoExample example);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    int updateByPrimaryKeySelective(EaccClearSubjectInfo record);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    int updateByPrimaryKey(EaccClearSubjectInfo record);
}