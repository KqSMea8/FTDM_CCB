package com.sunyard.sunfintech.core.annotation;

/**
 * ALL 全部需要验证
 * NONE 全部不需要验签
 * REQUEST 请求需要验签
 * RESPONSE 返回需要验签
 * @author heroy
 * @version 2017/5/12
 */
public enum SignType {
    ALL,
    NONE,
    REQUEST,
    RESPONSE
}
