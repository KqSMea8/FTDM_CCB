package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ClearCheckinfo;
import com.sunyard.sunfintech.dao.entity.ClearCheckinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClearCheckinfoMapper {
    /**
     *
     * @mbggenerated 2017-07-04
     */
    int countByExample(ClearCheckinfoExample example);

    /**
     *
     * @mbggenerated 2017-07-04
     */
    int deleteByExample(ClearCheckinfoExample example);

    /**
     *
     * @mbggenerated 2017-07-04
     */
    int insert(ClearCheckinfo record);

    /**
     *
     * @mbggenerated 2017-07-04
     */
    int insertSelective(ClearCheckinfo record);

    /**
     *
     * @mbggenerated 2017-07-04
     */
    List<ClearCheckinfo> selectByExample(ClearCheckinfoExample example);

    /**
     *
     * @mbggenerated 2017-07-04
     */
    int updateByExampleSelective(@Param("record") ClearCheckinfo record, @Param("example") ClearCheckinfoExample example);

    /**
     *
     * @mbggenerated 2017-07-04
     */
    int updateByExample(@Param("record") ClearCheckinfo record, @Param("example") ClearCheckinfoExample example);
}