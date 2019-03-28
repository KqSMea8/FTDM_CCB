package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.UnRegisterUserInfo;
import com.sunyard.sunfintech.dao.entity.UnRegisterUserInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UnRegisterUserInfoMapper {
    /**
     *
     * @mbggenerated 2018-05-17
     */
    int countByExample(UnRegisterUserInfoExample example);

    /**
     *
     * @mbggenerated 2018-05-17
     */
    int deleteByExample(UnRegisterUserInfoExample example);

    /**
     *
     * @mbggenerated 2018-05-17
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-05-17
     */
    int insert(UnRegisterUserInfo record);

    /**
     *
     * @mbggenerated 2018-05-17
     */
    int insertSelective(UnRegisterUserInfo record);

    /**
     *
     * @mbggenerated 2018-05-17
     */
    List<UnRegisterUserInfo> selectByExample(UnRegisterUserInfoExample example);

    /**
     *
     * @mbggenerated 2018-05-17
     */
    UnRegisterUserInfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-05-17
     */
    int updateByExampleSelective(@Param("record") UnRegisterUserInfo record, @Param("example") UnRegisterUserInfoExample example);

    /**
     *
     * @mbggenerated 2018-05-17
     */
    int updateByExample(@Param("record") UnRegisterUserInfo record, @Param("example") UnRegisterUserInfoExample example);

    /**
     *
     * @mbggenerated 2018-05-17
     */
    int updateByPrimaryKeySelective(UnRegisterUserInfo record);

    /**
     *
     * @mbggenerated 2018-05-17
     */
    int updateByPrimaryKey(UnRegisterUserInfo record);
}