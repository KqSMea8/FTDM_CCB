package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.MessageSwithes;
import com.sunyard.sunfintech.dao.entity.MessageSwithesExample;
import com.sunyard.sunfintech.dao.entity.MessageSwithesKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessageSwithesMapper {
    /**
     *
     * @mbggenerated 2017-06-23
     */
    int countByExample(MessageSwithesExample example);

    /**
     *
     * @mbggenerated 2017-06-23
     */
    int deleteByExample(MessageSwithesExample example);

    /**
     *
     * @mbggenerated 2017-06-23
     */
    int deleteByPrimaryKey(MessageSwithesKey key);

    /**
     *
     * @mbggenerated 2017-06-23
     */
    int insert(MessageSwithes record);

    /**
     *
     * @mbggenerated 2017-06-23
     */
    int insertSelective(MessageSwithes record);

    /**
     *
     * @mbggenerated 2017-06-23
     */
    List<MessageSwithes> selectByExample(MessageSwithesExample example);

    /**
     *
     * @mbggenerated 2017-06-23
     */
    MessageSwithes selectByPrimaryKey(MessageSwithesKey key);

    /**
     *
     * @mbggenerated 2017-06-23
     */
    int updateByExampleSelective(@Param("record") MessageSwithes record, @Param("example") MessageSwithesExample example);

    /**
     *
     * @mbggenerated 2017-06-23
     */
    int updateByExample(@Param("record") MessageSwithes record, @Param("example") MessageSwithesExample example);

    /**
     *
     * @mbggenerated 2017-06-23
     */
    int updateByPrimaryKeySelective(MessageSwithes record);

    /**
     *
     * @mbggenerated 2017-06-23
     */
    int updateByPrimaryKey(MessageSwithes record);
}