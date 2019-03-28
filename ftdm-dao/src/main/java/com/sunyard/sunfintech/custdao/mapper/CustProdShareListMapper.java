package com.sunyard.sunfintech.custdao.mapper;

import com.sunyard.sunfintech.custdao.entity.CustProdShareList;
import com.sunyard.sunfintech.dao.entity.ProdShareList;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by terry on 2017/6/1.
 */
public interface CustProdShareListMapper {

    List<ProdShareList> queryProdShareSumList(@Param("params")Map<String, Object> prodShareListParam);

    int insertMore(List<ProdShareList> prodShareListAddList);

    List<CustProdShareList> selectCustProdShareList(@Param("params")Map<String, Object> prodShareListParam);

    String getRemainAmtByPlacust(@Param("plat_no")String mer_no, @Param("platcust")String platcust);

    List<ProdShareList>selectQueryProdlist(@Param("trans_serial")String trans_serial);
}
