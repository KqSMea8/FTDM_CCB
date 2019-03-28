package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.AccountInfo;
import com.sunyard.sunfintech.dao.entity.AccountInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccountInfoMapper {
    /**
     *
     * @mbggenerated 2017-06-01
     */
    int countByExample(AccountInfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByExample(AccountInfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insert(AccountInfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insertSelective(AccountInfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<AccountInfo> selectByExample(AccountInfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    AccountInfo selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleSelective(@Param("record") AccountInfo record, @Param("example") AccountInfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExample(@Param("record") AccountInfo record, @Param("example") AccountInfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeySelective(AccountInfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKey(AccountInfo record);
}