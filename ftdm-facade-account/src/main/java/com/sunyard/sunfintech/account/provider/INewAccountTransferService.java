//package com.sunyard.sunfintech.account.provider;
//
//import com.sunyard.sunfintech.core.exception.BusinessException;
//import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
//
//import java.util.List;
//
///**
// * Created by terry on 2018/1/9.
// */
//public interface INewAccountTransferService {
//
//    /**
//     * 转账
//     * @param eaccAccountamtlist
//     * @return
//     * @throws BusinessException
//     */
//    public Integer transfer(EaccAccountamtlist eaccAccountamtlist)throws BusinessException;
//
//    /**
//     * 批量转账
//     * @param eaccAccountamtlist
//     * @return
//     * @throws BusinessException
//     */
//    public Integer batchTransfer(List<EaccAccountamtlist> eaccAccountamtlist)throws BusinessException;
//
//    /**
//     * 单笔加款加款
//     * @author wml
//     * @param eaccAccountamtlist
//     * @return
//     * @throws BusinessException
//     */
//    public boolean singleCharge(EaccAccountamtlist eaccAccountamtlist)throws BusinessException;
//
//    /**
//     * 批量加款加款
//     * @author wml
//     * @param eaccAccountamtlistList
//     * @return
//     * @throws BusinessException
//     */
//    public void batchCharge(List<EaccAccountamtlist> eaccAccountamtlistList)throws BusinessException;
//}
