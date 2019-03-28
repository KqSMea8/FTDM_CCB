package com.sunyard.sunfintech.pub.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.EaccUserauth;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by terry on 2018/2/14.
 */
public interface IAuthCheckService {

    /**
     * 检查授权信息
     * @param plat_no 平台号，必填参数
     * @param mall_no 集团号，必填参数
     * @param platcust 用户客户号，必填参数
     * @param authType 授权类型，必填参数。参数值详见AuthType(com.sunyard.sunfintech.core.dic.AuthType)枚举
     * @param authAmt 授权金额，必填参数
     * @return true：授权校验通过。false：授权校验不通过。
     * @throws BusinessException 参数错误或数据库数据异常时会抛出异常。
     */
    public boolean checkAuth(String plat_no, String mall_no, String platcust, String authType, BigDecimal authAmt)throws BusinessException;

    List<EaccUserauth> queryAuthInfo(String mall_no,String mer_no, String platcust);

    List<EaccUserauth> queryAuthInfoAllStatus(String mall_no, String mer_no, String platcust);
}
