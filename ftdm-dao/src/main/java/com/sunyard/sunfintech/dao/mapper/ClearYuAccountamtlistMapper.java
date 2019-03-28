package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ClearYuAccountamtlist;
import com.sunyard.sunfintech.dao.entity.ClearYuAccountamtlistExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClearYuAccountamtlistMapper {
    /**
     *
     * @mbggenerated 2018-02-04
     */
    int countByExample(ClearYuAccountamtlistExample example);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int deleteByExample(ClearYuAccountamtlistExample example);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int insert(ClearYuAccountamtlist record);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int insertSelective(ClearYuAccountamtlist record);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    List<ClearYuAccountamtlist> selectByExample(ClearYuAccountamtlistExample example);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    ClearYuAccountamtlist selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int updateByExampleSelective(@Param("record") ClearYuAccountamtlist record, @Param("example") ClearYuAccountamtlistExample example);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int updateByExample(@Param("record") ClearYuAccountamtlist record, @Param("example") ClearYuAccountamtlistExample example);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int updateByPrimaryKeySelective(ClearYuAccountamtlist record);

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int updateByPrimaryKey(ClearYuAccountamtlist record);
}