package com.sunyard.sunfintech.custdao.mapper;

import org.apache.ibatis.annotations.Param;

import com.sunyard.sunfintech.dao.entity.PlatPaycode;

public interface CustPlatPaycodeMapper {

	PlatPaycode getByUnionKey(@Param("plat_no")String plat_no,@Param("pay_code")String pay_code);
}