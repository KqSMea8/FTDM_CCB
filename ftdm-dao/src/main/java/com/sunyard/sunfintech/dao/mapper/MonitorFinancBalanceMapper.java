package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.MonitorFinancBalance;
import com.sunyard.sunfintech.dao.entity.MonitorFinancBalanceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MonitorFinancBalanceMapper {
    /**
     *
     * @mbggenerated 2018-02-03
     */
    int countByExample(MonitorFinancBalanceExample example);

    /**
     *
     * @mbggenerated 2018-02-03
     */
    int deleteByExample(MonitorFinancBalanceExample example);

    /**
     *
     * @mbggenerated 2018-02-03
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-02-03
     */
    int insert(MonitorFinancBalance record);

    /**
     *
     * @mbggenerated 2018-02-03
     */
    int insertSelective(MonitorFinancBalance record);

    /**
     *
     * @mbggenerated 2018-02-03
     */
    List<MonitorFinancBalance> selectByExample(MonitorFinancBalanceExample example);

    /**
     *
     * @mbggenerated 2018-02-03
     */
    MonitorFinancBalance selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-02-03
     */
    int updateByExampleSelective(@Param("record") MonitorFinancBalance record, @Param("example") MonitorFinancBalanceExample example);

    /**
     *
     * @mbggenerated 2018-02-03
     */
    int updateByExample(@Param("record") MonitorFinancBalance record, @Param("example") MonitorFinancBalanceExample example);

    /**
     *
     * @mbggenerated 2018-02-03
     */
    int updateByPrimaryKeySelective(MonitorFinancBalance record);

    /**
     *
     * @mbggenerated 2018-02-03
     */
    int updateByPrimaryKey(MonitorFinancBalance record);
}