package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.EaccClearCheckInfo;
import com.sunyard.sunfintech.dao.entity.EaccClearCheckInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EaccClearCheckInfoMapper {
    /**
     *
     * @mbggenerated 2017-08-09
     */
    int countByExample(EaccClearCheckInfoExample example);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    int deleteByExample(EaccClearCheckInfoExample example);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    int insert(EaccClearCheckInfo record);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    int insertSelective(EaccClearCheckInfo record);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    List<EaccClearCheckInfo> selectByExample(EaccClearCheckInfoExample example);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    EaccClearCheckInfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    int updateByExampleSelective(@Param("record") EaccClearCheckInfo record, @Param("example") EaccClearCheckInfoExample example);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    int updateByExample(@Param("record") EaccClearCheckInfo record, @Param("example") EaccClearCheckInfoExample example);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    int updateByPrimaryKeySelective(EaccClearCheckInfo record);

    /**
     *
     * @mbggenerated 2017-08-09
     */
    int updateByPrimaryKey(EaccClearCheckInfo record);
}