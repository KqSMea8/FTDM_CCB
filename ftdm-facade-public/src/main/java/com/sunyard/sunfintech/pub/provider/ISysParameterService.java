package com.sunyard.sunfintech.pub.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.SysParameter;

/**
 * 系统参数操作
 * @author heroy
 * @version 2017/5/25
 */
public interface ISysParameterService {
    /**
     * 获取系统参数值
     * @param sysParameter 系统参数数据库信息
     * @return 参数信息
     * @throws BusinessException 业务异常
     */
    public String querySysParameter(SysParameter sysParameter) throws BusinessException;

    /**
     * 获取数据库中系统参数值
     * @param mall_no 集团号
     * @param mer_no 平台号
     * @param key 参数KEY
     * @return 参数信息
     * @throws BusinessException 业务异常
     */
    public SysParameter getSysParameter(String mall_no, String mer_no, String key) throws BusinessException;

    /**
     * 获取系统参数值【平台参数】
     * @param mall_no 集团号
     * @param mer_no 平台号
     * @param key 参数KEY
     * @return 参数值
     * @throws BusinessException
     */
    public String querySysParameter(String mall_no, String mer_no, String key) throws BusinessException;

    /**
     * 获取系统参数值【集团参数】
     * @param mall_no 集团号
     * @param key 参数KEY
     * @return 参数值
     * @throws BusinessException
     */
    public String querySysParameter(String mall_no, String key) throws BusinessException;


    /**
     * 更新系统参数值
     * @param sysParameter 系统参数数据库信息
     * @return true 成功
     * @throws BusinessException 业务异常
     */
    public boolean updateSysParameter(SysParameter sysParameter) throws BusinessException;

    /**
     * 插入系统参数
     * @param sysParameter 系统参数数据库信息
     * @return true 成功
     * @throws BusinessException 业务异常
     */
    public boolean insertSysParameter(SysParameter sysParameter) throws BusinessException;

    /**
     * 验签
     * @param plain 加密原文
     * @param sData 密文
     * @return true 验签成功  false 验签失败
     * @throws BusinessException 业务异常
     */
    public boolean attestation(String plain, String sData) throws BusinessException;
}
