package com.sunyard.sunfintech.custdao.mapper;

import com.sunyard.sunfintech.dao.entity.AccountInfo;

import java.util.List;

public interface CustAccountInfoMapper{
    /**
     *
     * @mbggenerated 2017-04-20
     */
    Integer insert(AccountInfo record);

    /**
     *
     * @mbggenerated 2017-04-20
     */
    AccountInfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-04-20
     */
    List<AccountInfo> selectAll();

    /**
     *
     * @mbggenerated 2017-04-20
     */
    int updateByPrimaryKey(AccountInfo record);
}