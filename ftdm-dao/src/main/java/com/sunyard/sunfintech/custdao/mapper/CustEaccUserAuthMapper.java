package com.sunyard.sunfintech.custdao.mapper;


import com.sunyard.sunfintech.dao.entity.EaccUserauth;

/**
 * Created by terry on 2018/1/21.
 */
public interface CustEaccUserAuthMapper {

    public Integer insertEaccUserAuthIgnore(EaccUserauth eaccUserauth);

    public Integer insertEaccUserAuthReplace(EaccUserauth eaccUserauth);
}
