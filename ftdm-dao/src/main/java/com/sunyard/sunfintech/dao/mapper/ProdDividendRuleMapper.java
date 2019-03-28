package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.ProdDividendRule;
import com.sunyard.sunfintech.dao.entity.ProdDividendRuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProdDividendRuleMapper {
    /**
     *
     * @mbggenerated 2017-06-01
     */
    int countByExample(ProdDividendRuleExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByExample(ProdDividendRuleExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insert(ProdDividendRule record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insertSelective(ProdDividendRule record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<ProdDividendRule> selectByExample(ProdDividendRuleExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    ProdDividendRule selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleSelective(@Param("record") ProdDividendRule record, @Param("example") ProdDividendRuleExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExample(@Param("record") ProdDividendRule record, @Param("example") ProdDividendRuleExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeySelective(ProdDividendRule record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKey(ProdDividendRule record);
}