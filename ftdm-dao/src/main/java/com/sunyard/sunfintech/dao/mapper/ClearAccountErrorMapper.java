package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ClearAccountError;

public interface ClearAccountErrorMapper {
    /**
     *
     * @mbggenerated 2017-11-01
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2017-11-01
     */
    int insert(ClearAccountError record);

    /**
     *
     * @mbggenerated 2017-11-01
     */
    int insertSelective(ClearAccountError record);

    /**
     *
     * @mbggenerated 2017-11-01
     */
    ClearAccountError selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2017-11-01
     */
    int updateByPrimaryKeySelective(ClearAccountError record);

    /**
     *
     * @mbggenerated 2017-11-01
     */
    int updateByPrimaryKey(ClearAccountError record);
}