package com.sunyard.sunfintech.custdao.mapper;

import com.sunyard.sunfintech.dao.entity.EaccUserinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustEaccUserinfoMapper {
	
	EaccUserinfo getByUnionKey(@Param("plat_no")String plat_no,@Param("platcust")String platcust);

	public int updateEaccUserInfoStatusById(EaccUserinfo eaccUserinfo);

	int replace(EaccUserinfo record);

	List<EaccUserinfo> userInFo(@Param("name")String name, @Param("mall_no")String mall_no, @Param("id_code")String id_code);

}