package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.BillCheckNotifyRetry;
import com.sunyard.sunfintech.dao.entity.BillCheckNotifyRetryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BillCheckNotifyRetryMapper {
    /**
     *
     * @mbggenerated 2018-02-04
     */
    int countByExample(BillCheckNotifyRetryExample example);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int deleteByExample(BillCheckNotifyRetryExample example);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int insert(BillCheckNotifyRetry record);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int insertSelective(BillCheckNotifyRetry record);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    List<BillCheckNotifyRetry> selectByExample(BillCheckNotifyRetryExample example);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    BillCheckNotifyRetry selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int updateByExampleSelective(@Param("record") BillCheckNotifyRetry record, @Param("example") BillCheckNotifyRetryExample example);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int updateByExample(@Param("record") BillCheckNotifyRetry record, @Param("example") BillCheckNotifyRetryExample example);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int updateByPrimaryKeySelective(BillCheckNotifyRetry record);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int updateByPrimaryKey(BillCheckNotifyRetry record);
}