package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ClearCheckinfoEmx;
import com.sunyard.sunfintech.dao.entity.ClearCheckinfoEmxExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClearCheckinfoEmxMapper {
    /**
     *
     * @mbggenerated 2017-07-04
     */
    int countByExample(ClearCheckinfoEmxExample example);

    /**
     *
     * @mbggenerated 2017-07-04
     */
    int deleteByExample(ClearCheckinfoEmxExample example);

    /**
     *
     * @mbggenerated 2017-07-04
     */
    int insert(ClearCheckinfoEmx record);

    /**
     *
     * @mbggenerated 2017-07-04
     */
    int insertSelective(ClearCheckinfoEmx record);

    /**
     *
     * @mbggenerated 2017-07-04
     */
    List<ClearCheckinfoEmx> selectByExample(ClearCheckinfoEmxExample example);

    /**
     *
     * @mbggenerated 2017-07-04
     */
    int updateByExampleSelective(@Param("record") ClearCheckinfoEmx record, @Param("example") ClearCheckinfoEmxExample example);

    /**
     *
     * @mbggenerated 2017-07-04
     */
    int updateByExample(@Param("record") ClearCheckinfoEmx record, @Param("example") ClearCheckinfoEmxExample example);
}