package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ClearCheckinfoEmxlist;
import com.sunyard.sunfintech.dao.entity.ClearCheckinfoEmxlistExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClearCheckinfoEmxlistMapper {
    /**
     *
     * @mbggenerated 2017-07-04
     */
    int countByExample(ClearCheckinfoEmxlistExample example);

    /**
     *
     * @mbggenerated 2017-07-04
     */
    int deleteByExample(ClearCheckinfoEmxlistExample example);

    /**
     *
     * @mbggenerated 2017-07-04
     */
    int insert(ClearCheckinfoEmxlist record);

    /**
     *
     * @mbggenerated 2017-07-04
     */
    int insertSelective(ClearCheckinfoEmxlist record);

    /**
     *
     * @mbggenerated 2017-07-04
     */
    List<ClearCheckinfoEmxlist> selectByExample(ClearCheckinfoEmxlistExample example);

    /**
     *
     * @mbggenerated 2017-07-04
     */
    int updateByExampleSelective(@Param("record") ClearCheckinfoEmxlist record, @Param("example") ClearCheckinfoEmxlistExample example);

    /**
     *
     * @mbggenerated 2017-07-04
     */
    int updateByExample(@Param("record") ClearCheckinfoEmxlist record, @Param("example") ClearCheckinfoEmxlistExample example);
}