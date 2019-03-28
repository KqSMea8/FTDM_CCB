package com.sunyard.sunfintech.core.util;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @author heroy
 * @version 2018/1/2
 */
public class KeyValueUtil implements Serializable {
    private String key;
    private Object value;

    public KeyValueUtil(){
        super();
    }

    public KeyValueUtil(String key, Object value){
        super();
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KeyValueUtil that = (KeyValueUtil) o;

        if (!key.equals(that.key)) return false;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}
