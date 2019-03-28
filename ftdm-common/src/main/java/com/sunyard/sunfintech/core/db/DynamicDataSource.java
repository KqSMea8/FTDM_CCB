package com.sunyard.sunfintech.core.db;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态选择数据源
 * Created by heroy on 2017/4/3.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    public static Map<String, List<String>> METHODTYPE = new HashMap<String, List<String>>();

    /**
     * 取得当前使用哪个数据源
     * @return 数据源
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DbContextHolder.getDataSource();
    }

    // 设置方法名前缀对应的数据源
    public void setMethodType(Map<String, String> map) {
        for (String key : map.keySet()) {
            List<String> v = new ArrayList<String>();
            String[] types = map.get(key).split(",");
            for (String type : types) {
                if (StringUtils.isNotBlank(type)) {
                    v.add(type);
                }
            }
            METHODTYPE.put(key, v);
        }
    }
}