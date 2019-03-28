package com.sunyard.sunfintech.core.support.security;

import java.util.Map;

/**
 * @author heroy
 * @version 2018/1/16
 */
public interface AuthorizationAdapter {
    public Map<String, Object> checkPassword(Map<String, Object> params);

}
