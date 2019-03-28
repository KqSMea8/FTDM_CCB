package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.MessageLog;
import com.sunyard.sunfintech.dao.entity.MessageLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessageLogMapper {
    /**
     *
     * @mbggenerated 2017-06-23
     */
    int countByExample(MessageLogExample example);

    /**
     *
     * @mbggenerated 2017-06-23
     */
    int deleteByExample(MessageLogExample example);

    /**
     *
     * @mbggenerated 2017-06-23
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2017-06-23
     */
    int insert(MessageLog record);

    /**
     *
     * @mbggenerated 2017-06-23
     */
    int insertSelective(MessageLog record);

    /**
     *
     * @mbggenerated 2017-06-23
     */
    List<MessageLog> selectByExample(MessageLogExample example);

    /**
     *
     * @mbggenerated 2017-06-23
     */
    MessageLog selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2017-06-23
     */
    int updateByExampleSelective(@Param("record") MessageLog record, @Param("example") MessageLogExample example);

    /**
     *
     * @mbggenerated 2017-06-23
     */
    int updateByExample(@Param("record") MessageLog record, @Param("example") MessageLogExample example);

    /**
     *
     * @mbggenerated 2017-06-23
     */
    int updateByPrimaryKeySelective(MessageLog record);

    /**
     *
     * @mbggenerated 2017-06-23
     */
    int updateByPrimaryKey(MessageLog record);
}