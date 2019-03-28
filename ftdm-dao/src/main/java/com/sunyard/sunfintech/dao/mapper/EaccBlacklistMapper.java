package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.EaccBlacklist;
import com.sunyard.sunfintech.dao.entity.EaccBlacklistExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EaccBlacklistMapper {
    /**
     *
     * @mbggenerated 2017-06-01
     */
    int countByExample(EaccBlacklistExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByExample(EaccBlacklistExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insert(EaccBlacklist record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insertSelective(EaccBlacklist record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<EaccBlacklist> selectByExample(EaccBlacklistExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    EaccBlacklist selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleSelective(@Param("record") EaccBlacklist record, @Param("example") EaccBlacklistExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExample(@Param("record") EaccBlacklist record, @Param("example") EaccBlacklistExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeySelective(EaccBlacklist record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKey(EaccBlacklist record);
}