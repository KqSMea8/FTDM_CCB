package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ShareholderInfo;
import com.sunyard.sunfintech.dao.entity.ShareholderInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShareholderInfoMapper {
    /**
     *
     * @mbggenerated 2017-06-01
     */
    int countByExample(ShareholderInfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByExample(ShareholderInfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insert(ShareholderInfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insertSelective(ShareholderInfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<ShareholderInfo> selectByExample(ShareholderInfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    ShareholderInfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleSelective(@Param("record") ShareholderInfo record, @Param("example") ShareholderInfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExample(@Param("record") ShareholderInfo record, @Param("example") ShareholderInfoExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeySelective(ShareholderInfo record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKey(ShareholderInfo record);
}