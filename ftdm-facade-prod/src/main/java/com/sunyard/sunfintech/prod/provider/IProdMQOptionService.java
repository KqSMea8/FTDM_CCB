package com.sunyard.sunfintech.prod.provider;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.EaccUserinfo;
import com.sunyard.sunfintech.dao.entity.ProdProductinfo;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.prod.model.bo.BatchCustRepay;
import com.sunyard.sunfintech.prod.model.bo.BatchRepayRequestDetail;
import com.sunyard.sunfintech.prod.model.bo.ProdInvestData;
import com.sunyard.sunfintech.prod.model.bo.ProdTransferDebtRequestBo;
import com.sunyard.sunfintech.prod.model.mq.ProdRefundData;
import com.sunyard.sunfintech.prod.model.mq.ProdSingleRefundData;

import java.util.List;

/**
 * Created by terry on 2017/7/28.
 */
public interface IProdMQOptionService {

    public void addProdRepay(BaseRequest baseRequest, BatchCustRepay batchCustRepay, String notifyURL)throws BusinessException;

    public void addBatchRepayAsyn(BaseRequest baseRequest, BatchRepayRequestDetail batchRepayRequestDetail, String notifyURL)throws BusinessException;

    public void addInvest(BaseRequest baseRequest,ProdInvestData prodInvestData,String notifyURL)throws BusinessException;

    public void addInvests(BaseRequest baseRequest,List<ProdInvestData >  prodInvestDatas,String notifyURL,boolean authValid)throws BusinessException;

    public void addTransProd(List<ProdTransferDebtRequestBo> prodTransferDebtRequestBo,String notifyURL) throws BusinessException;

    public void addRefund(List<BatchCustRepay> batchCustRepayList, ProdProductinfo prodProductinfo)throws BusinessException;

    public void addSingleRefund(BatchCustRepay batchCustRepay, ProdProductinfo prodProductinfo, EaccUserinfo eaccUserinfo)throws BusinessException;

    public void doInvest(BaseRequest baseRequest, ProdInvestData prodInvestData,String notifyURL,boolean authValid)throws BusinessException;

    //借款人还款申请执行
    public void doBatchRepayAsyn(BaseRequest baseRequest, BatchRepayRequestDetail batchRepayRequestDetail,String notifyURL)throws BusinessException;

    public void doProdRepay(BaseRequest baseRequest, BatchCustRepay batchCustRepay,String notifyURL)throws BusinessException;

    public void doTransProd(List<ProdTransferDebtRequestBo> prodTransferDebtRequestBoList,String notifyURL)throws BusinessException;

    public void doRefund(ProdRefundData prodRefundData)throws  BusinessException;

    public void doSingleRefund(ProdSingleRefundData prodSingleRefundData)throws  BusinessException;

    public void finishTransProd(TransTransreq transTransreq)throws BusinessException;

    public void finishProdRefund(TransTransreq transTransreq)throws BusinessException;
    public void investAsyncCallBack(TransTransreq transTransreq)throws BusinessException;

}
