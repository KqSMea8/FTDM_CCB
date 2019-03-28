package com.sunyard.sunfintech.custdao.mapper;

import com.sunyard.sunfintech.dao.entity.EaccTransTransreq;
import com.sunyard.sunfintech.dao.entity.EaccTransTransreqExample;
import com.sunyard.sunfintech.dao.entity.EaccTransTransreqWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustEaccTransTransreqMapper {

    /**
     *
     * @mbggenerated 2018-02-04
     */
    int insertReplace(EaccTransTransreqWithBLOBs record);
}