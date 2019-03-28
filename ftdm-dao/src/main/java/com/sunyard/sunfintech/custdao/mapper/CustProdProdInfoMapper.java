package com.sunyard.sunfintech.custdao.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by terry on 2017/8/15.
 */
public interface CustProdProdInfoMapper {

    int addProdLimit(@Param("params")Map<String,Object> params);

    int subtractProdLimit(@Param("params")Map<String,Object> params);
}
