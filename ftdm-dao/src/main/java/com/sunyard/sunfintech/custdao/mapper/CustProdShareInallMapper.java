package com.sunyard.sunfintech.custdao.mapper;

import com.sunyard.sunfintech.core.base.BaseMapper;
import com.sunyard.sunfintech.dao.entity.ProdShareInall;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CustProdShareInallMapper {

    int insertMore(List<ProdShareInall> prodShareInalls);

    int addInallVol(@Param("params")Map<String,Object> params);

    int subtractInallVol(@Param("params")Map<String,Object> params);
}
