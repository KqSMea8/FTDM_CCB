package com.sunyard.sunfintech.pub.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;

import java.util.List;
import java.util.Map;

/**
 * Created by terry on 2017/8/28.
 */
public interface IRedisManager {

    public Map<String,Object> getKeyList(String key)throws BusinessException;

    public List<String> searchList(String key, String value)throws BusinessException;

    public List<String> searchSet(String key, String value)throws BusinessException;
}
