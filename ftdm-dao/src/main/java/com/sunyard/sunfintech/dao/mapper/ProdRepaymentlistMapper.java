package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ProdRepaymentlist;
import com.sunyard.sunfintech.dao.entity.ProdRepaymentlistExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProdRepaymentlistMapper {
    /**
     *
     * @mbggenerated 2017-06-01
     */
    int countByExample(ProdRepaymentlistExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByExample(ProdRepaymentlistExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insert(ProdRepaymentlist record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insertSelective(ProdRepaymentlist record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<ProdRepaymentlist> selectByExample(ProdRepaymentlistExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleSelective(@Param("record") ProdRepaymentlist record, @Param("example") ProdRepaymentlistExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExample(@Param("record") ProdRepaymentlist record, @Param("example") ProdRepaymentlistExample example);
}