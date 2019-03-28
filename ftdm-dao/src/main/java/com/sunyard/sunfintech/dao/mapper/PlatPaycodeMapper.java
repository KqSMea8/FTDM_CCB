package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.PlatPaycode;
import com.sunyard.sunfintech.dao.entity.PlatPaycodeExample;
import com.sunyard.sunfintech.dao.entity.PlatPaycodeKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlatPaycodeMapper {
    /**
     *
     * @mbggenerated 2018-07-02
     */
    int countByExample(PlatPaycodeExample example);

    /**
     *
     * @mbggenerated 2018-07-02
     */
    int deleteByExample(PlatPaycodeExample example);

    /**
     *
     * @mbggenerated 2018-07-02
     */
    int deleteByPrimaryKey(PlatPaycodeKey key);

    /**
     *
     * @mbggenerated 2018-07-02
     */
    int insert(PlatPaycode record);

    /**
     *
     * @mbggenerated 2018-07-02
     */
    int insertSelective(PlatPaycode record);

    /**
     *
     * @mbggenerated 2018-07-02
     */
    List<PlatPaycode> selectByExample(PlatPaycodeExample example);

    /**
     *
     * @mbggenerated 2018-07-02
     */
    PlatPaycode selectByPrimaryKey(PlatPaycodeKey key);

    /**
     *
     * @mbggenerated 2018-07-02
     */
    int updateByExampleSelective(@Param("record") PlatPaycode record, @Param("example") PlatPaycodeExample example);

    /**
     *
     * @mbggenerated 2018-07-02
     */
    int updateByExample(@Param("record") PlatPaycode record, @Param("example") PlatPaycodeExample example);

    /**
     *
     * @mbggenerated 2018-07-02
     */
    int updateByPrimaryKeySelective(PlatPaycode record);

    /**
     *
     * @mbggenerated 2018-07-02
     */
    int updateByPrimaryKey(PlatPaycode record);
}