package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ClearListCheckConf;
import com.sunyard.sunfintech.dao.entity.ClearListCheckConfExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClearListCheckConfMapper {
    /**
     *
     * @mbggenerated 2018-06-21
     */
    int countByExample(ClearListCheckConfExample example);

    /**
     *
     * @mbggenerated 2018-06-21
     */
    int deleteByExample(ClearListCheckConfExample example);

    /**
     *
     * @mbggenerated 2018-06-21
     */
    int insert(ClearListCheckConf record);

    /**
     *
     * @mbggenerated 2018-06-21
     */
    int insertSelective(ClearListCheckConf record);

    /**
     *
     * @mbggenerated 2018-06-21
     */
    List<ClearListCheckConf> selectByExample(ClearListCheckConfExample example);

    /**
     *
     * @mbggenerated 2018-06-21
     */
    int updateByExampleSelective(@Param("record") ClearListCheckConf record, @Param("example") ClearListCheckConfExample example);

    /**
     *
     * @mbggenerated 2018-06-21
     */
    int updateByExample(@Param("record") ClearListCheckConf record, @Param("example") ClearListCheckConfExample example);
}