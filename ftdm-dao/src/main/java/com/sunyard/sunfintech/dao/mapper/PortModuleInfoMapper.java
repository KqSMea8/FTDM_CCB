package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.PortModuleInfo;
import com.sunyard.sunfintech.dao.entity.PortModuleInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PortModuleInfoMapper {
    /**
     *
     * @mbggenerated 2018-01-29
     */
    int countByExample(PortModuleInfoExample example);

    /**
     *
     * @mbggenerated 2018-01-29
     */
    int deleteByExample(PortModuleInfoExample example);

    /**
     *
     * @mbggenerated 2018-01-29
     */
    int deleteByPrimaryKey(Long pid);

    /**
     *
     * @mbggenerated 2018-01-29
     */
    int insert(PortModuleInfo record);

    /**
     *
     * @mbggenerated 2018-01-29
     */
    int insertSelective(PortModuleInfo record);

    /**
     *
     * @mbggenerated 2018-01-29
     */
    List<PortModuleInfo> selectByExample(PortModuleInfoExample example);

    /**
     *
     * @mbggenerated 2018-01-29
     */
    PortModuleInfo selectByPrimaryKey(Long pid);

    /**
     *
     * @mbggenerated 2018-01-29
     */
    int updateByExampleSelective(@Param("record") PortModuleInfo record, @Param("example") PortModuleInfoExample example);

    /**
     *
     * @mbggenerated 2018-01-29
     */
    int updateByExample(@Param("record") PortModuleInfo record, @Param("example") PortModuleInfoExample example);

    /**
     *
     * @mbggenerated 2018-01-29
     */
    int updateByPrimaryKeySelective(PortModuleInfo record);

    /**
     *
     * @mbggenerated 2018-01-29
     */
    int updateByPrimaryKey(PortModuleInfo record);
}