package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.EaccClearErrorInfo;
import com.sunyard.sunfintech.dao.entity.EaccClearErrorInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EaccClearErrorInfoMapper {
    /**
     *
     * @mbggenerated 2017-08-09
     */
    int countByExample(EaccClearErrorInfoExample example);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    int deleteByExample(EaccClearErrorInfoExample example);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    int insert(EaccClearErrorInfo record);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    int insertSelective(EaccClearErrorInfo record);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    List<EaccClearErrorInfo> selectByExample(EaccClearErrorInfoExample example);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    EaccClearErrorInfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    int updateByExampleSelective(@Param("record") EaccClearErrorInfo record, @Param("example") EaccClearErrorInfoExample example);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    int updateByExample(@Param("record") EaccClearErrorInfo record, @Param("example") EaccClearErrorInfoExample example);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    int updateByPrimaryKeySelective(EaccClearErrorInfo record);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    int updateByPrimaryKey(EaccClearErrorInfo record);
}