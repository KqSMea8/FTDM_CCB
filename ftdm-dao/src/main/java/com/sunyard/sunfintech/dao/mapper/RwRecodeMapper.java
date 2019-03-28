package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.RwRecode;
import com.sunyard.sunfintech.dao.entity.RwRecodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RwRecodeMapper {
    /**
     *
     * @mbggenerated 2017-06-01
     */
    int countByExample(RwRecodeExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByExample(RwRecodeExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insert(RwRecode record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insertSelective(RwRecode record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<RwRecode> selectByExample(RwRecodeExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    RwRecode selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleSelective(@Param("record") RwRecode record, @Param("example") RwRecodeExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExample(@Param("record") RwRecode record, @Param("example") RwRecodeExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeySelective(RwRecode record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKey(RwRecode record);
}