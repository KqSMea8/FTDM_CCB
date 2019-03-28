package com.sunyard.sunfintech.custdao.mapper;

import com.sunyard.sunfintech.dao.entity.PlatCardinfo;
import com.sunyard.sunfintech.dao.entity.PlatCardinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CustPlatCardinfoMapper {
	/**
	 * @param plat_no
	 * @param card_type
	 * @return
	 */
	PlatCardinfo getByUnionKey(@Param("plat_no")String plat_no,@Param("card_type")String card_type);

}