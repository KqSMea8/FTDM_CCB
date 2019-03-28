package com.sunyard.sunfintech.custdao.mapper;

import com.sunyard.sunfintech.custdao.entity.AccOpenconfig;
import com.sunyard.sunfintech.dao.entity.AccountInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CustOpenconfigMapper {
    /**
     * 根据集团编号查询平台编号
     */
     List <String>  selectPlatNoByMallNo (@Param("params") Map<String, Object> params);

}