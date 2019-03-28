package com.sunyard.sunfintech.account.provider;


import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.AccountSubjectInfo;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;

import java.util.List;
import java.util.Map;

/**
 * 账户基本操作开户
 * @author heroy
 * @version 2017/4/12
 */
public interface IAccountOprationService {
    public AccountSubjectInfo register(AccountSubjectInfo accountSubjectInfo) throws BusinessException;

    /**
     * 反向转账
     * @param eaccAccountamtlists
     * @return
     * @throws BusinessException
     */
    public boolean backTransfer(List<EaccAccountamtlist> eaccAccountamtlists) throws BusinessException;

    /**
     * 执行转账记账
     * @param expenseAccount
     * @return
     * @throws BusinessException
     */
    public Map<String,Object> doTransfer(EaccAccountamtlist expenseAccount) throws BusinessException;

    /**
     * 执行转账记账
     * @param platcust
     * @return
     * @throws BusinessException
     */
    public void deleteAccountSubjectInfoByPlatcust(String platcust) throws BusinessException;

    /**
     * 更新账户
     * @param accountSubjectInfoParams
     * @param where_platcust
     * @param where_plat_no
     * @param where_subject
     * @param where_sub_subject
     * @return
     * @throws BusinessException
     */
    public Integer updateAccountSubjectInfo(AccountSubjectInfo accountSubjectInfoParams,String where_platcust, String where_plat_no, String where_subject, String where_sub_subject)throws BusinessException;

    int batchUpdateAccountStatus(List<AccountSubjectInfo> subjectInfoList,String enable_status)  throws BusinessException;
}
