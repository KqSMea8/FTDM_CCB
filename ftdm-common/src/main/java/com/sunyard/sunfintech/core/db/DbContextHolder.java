package com.sunyard.sunfintech.core.db;

/**
 * Created by heroy on 2017/4/3.
 */
public class DbContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    /**
     * 设置数据源
     * @param datasource 数据源
     */
    public static void setDataSource(String datasource) {
        contextHolder.set(datasource);
    }

    /**
     * 取得当前数据源
     * @return
     */
    public static String getDataSource() {
        return contextHolder.get();
    }

    /**
     * 清除上下文数据
     */
    public static void clear() {
        contextHolder.remove();
    }
}
