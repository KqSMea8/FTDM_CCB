package com.sunyard.sunfintech.custdao.mapper;

import com.sunyard.sunfintech.dao.entity.PlatPlatinfo;
import com.sunyard.sunfintech.dao.entity.PlatPlatinfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustPlatPlatinfoMapper {

    List<String> getPlatNoByMallNo(@Param("mall_no") String mall_no);

    String getMallNoByPlatNo(@Param("plat_no") String plat_no);

}