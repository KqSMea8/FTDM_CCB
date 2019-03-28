package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.BatchRepayDetail;
import com.sunyard.sunfintech.dao.entity.BatchRepayDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BatchRepayDetailMapper {
    /**
     *
     * @mbggenerated 2018-09-20
     */
    int countByExample(BatchRepayDetailExample example);

    /**
     *
     * @mbggenerated 2018-09-20
     */
    int deleteByExample(BatchRepayDetailExample example);

    /**
     *
     * @mbggenerated 2018-09-20
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-09-20
     */
    int insert(BatchRepayDetail record);

    /**
     *
     * @mbggenerated 2018-09-20
     */
    int insertSelective(BatchRepayDetail record);

    /**
     *
     * @mbggenerated 2018-09-20
     */
    List<BatchRepayDetail> selectByExample(BatchRepayDetailExample example);

    /**
     *
     * @mbggenerated 2018-09-20
     */
    BatchRepayDetail selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-09-20
     */
    int updateByExampleSelective(@Param("record") BatchRepayDetail record, @Param("example") BatchRepayDetailExample example);

    /**
     *
     * @mbggenerated 2018-09-20
     */
    int updateByExample(@Param("record") BatchRepayDetail record, @Param("example") BatchRepayDetailExample example);

    /**
     *
     * @mbggenerated 2018-09-20
     */
    int updateByPrimaryKeySelective(BatchRepayDetail record);

    /**
     *
     * @mbggenerated 2018-09-20
     */
    int updateByPrimaryKey(BatchRepayDetail record);
}