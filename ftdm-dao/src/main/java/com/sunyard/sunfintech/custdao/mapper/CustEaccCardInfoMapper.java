package com.sunyard.sunfintech.custdao.mapper;


import com.sunyard.sunfintech.dao.entity.EaccCardinfo;

import java.util.List;

/**
 * Created by terry on 2018/1/21.
 */
public interface CustEaccCardInfoMapper {

    public Integer insertEaccCardInfoIgnore(EaccCardinfo eaccCardinfo);

    public Integer insertEaccCardInfoReplace(EaccCardinfo eaccCardinfo);

    public int updateEaccCardinfoListByIds(EaccCardinfo eaccCardinfoList);
}
