package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ClearYuAccount;
import com.sunyard.sunfintech.dao.entity.ClearYuAccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClearYuAccountMapper {
    /**
     *
     * @mbggenerated 2018-02-03
     */
    int countByExample(ClearYuAccountExample example);

    /**
     *
     * @mbggenerated 2018-02-03
     */
    int deleteByExample(ClearYuAccountExample example);

    /**
     *
     * @mbggenerated 2018-02-03
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-02-03
     */
    int insert(ClearYuAccount record);

    /**
     *
     * @mbggenerated 2018-02-03
     */
    int insertSelective(ClearYuAccount record);

    /**
     *
     * @mbggenerated 2018-02-03
     */
    List<ClearYuAccount> selectByExample(ClearYuAccountExample example);

    /**
     *
     * @mbggenerated 2018-02-03
     */
    ClearYuAccount selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-02-03
     */
    int updateByExampleSelective(@Param("record") ClearYuAccount record, @Param("example") ClearYuAccountExample example);

    /**
     *
     * @mbggenerated 2018-02-03
     */
    int updateByExample(@Param("record") ClearYuAccount record, @Param("example") ClearYuAccountExample example);

    /**
     *
     * @mbggenerated 2018-02-03
     */
    int updateByPrimaryKeySelective(ClearYuAccount record);

    /**
     *
     * @mbggenerated 2018-02-03
     */
    int updateByPrimaryKey(ClearYuAccount record);
}