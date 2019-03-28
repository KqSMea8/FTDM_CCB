package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.AccountSubjectInfo;
import com.sunyard.sunfintech.dao.entity.AccountSubjectInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountSubjectInfoMapper {
    /**
     *
     * @mbggenerated 2018-02-09
     */
    int countByExample(AccountSubjectInfoExample example);

    /**
     *
     * @mbggenerated 2018-02-09
     */
    int deleteByExample(AccountSubjectInfoExample example);

    /**
     *
     * @mbggenerated 2018-02-09
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-02-09
     */
    int insert(AccountSubjectInfo record);

    /**
     *
     * @mbggenerated 2018-02-09
     */
    int insertSelective(AccountSubjectInfo record);

    /**
     *
     * @mbggenerated 2018-02-09
     */
    List<AccountSubjectInfo> selectByExample(AccountSubjectInfoExample example);

    /**
     *
     * @mbggenerated 2018-02-09
     */
    AccountSubjectInfo selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-02-09
     */
    int updateByExampleSelective(@Param("record") AccountSubjectInfo record, @Param("example") AccountSubjectInfoExample example);

    /**
     *
     * @mbggenerated 2018-02-09
     */
    int updateByExample(@Param("record") AccountSubjectInfo record, @Param("example") AccountSubjectInfoExample example);

    /**
     *
     * @mbggenerated 2018-02-09
     */
    int updateByPrimaryKeySelective(AccountSubjectInfo record);

    /**
     *
     * @mbggenerated 2018-02-09
     */
    int updateByPrimaryKey(AccountSubjectInfo record);
}