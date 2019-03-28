//package com.sunyard.sunfintech.account.provider;
//
//import com.sunyard.sunfintech.core.exception.BusinessException;
//import com.sunyard.sunfintech.dao.entity.AccountSubjectInfo;
//import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
//
//import java.util.List;
//
///**
// * Created by terry on 2018/1/4.
// */
//public interface INewAccountTransferExecuteService {
//
//    /**
//     * 转账，事务
//     * @param eaccAccountamtlist
//     * @return
//     * @throws BusinessException
//     */
//    public Integer transfer(EaccAccountamtlist eaccAccountamtlist)throws BusinessException;
//
//    /**
//     * 批量转账，事务
//     * @param eaccAccountamtlist
//     * @return
//     * @throws BusinessException
//     */
//    public Integer batchTransfer(List<EaccAccountamtlist> eaccAccountamtlist)throws BusinessException;
//
//    /**
//     * 执行转账，非事务
//     * @param eaccAccountamtlist
//     * @return
//     * @throws BusinessException
//     */
//    public Boolean doTransfer(EaccAccountamtlist eaccAccountamtlist, List<AccountSubjectInfo> accountSubjectInfoList)throws BusinessException;
//
//    /**
//     * 加款
//     * @param eaccAccountamtlist
//     * @return
//     * @throws BusinessException
//     */
//    public Boolean charge(EaccAccountamtlist eaccAccountamtlist, Long accountId)throws BusinessException;
//
//    /**
//     * 扣款
//     * @param eaccAccountamtlist
//     * @return
//     * @throws BusinessException
//     */
//    public Boolean subtract(EaccAccountamtlist eaccAccountamtlist, Long accountId)throws BusinessException;
//}
