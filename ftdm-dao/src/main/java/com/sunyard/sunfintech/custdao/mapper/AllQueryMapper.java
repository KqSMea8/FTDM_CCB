package com.sunyard.sunfintech.custdao.mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by terry on 2018/4/10.
 */
public interface AllQueryMapper {

    List<Map<String,Object>> dbQuery(String sql);
}
