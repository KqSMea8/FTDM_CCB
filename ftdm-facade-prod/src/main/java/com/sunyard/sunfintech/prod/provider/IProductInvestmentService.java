package com.sunyard.sunfintech.prod.provider;

import com.sunyard.sunfintech.core.base.BaseModel;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.ProdProductinfo;
import com.sunyard.sunfintech.dao.entity.ProdShareInall;
import com.sunyard.sunfintech.dao.entity.ProdShareList;
import com.sunyard.sunfintech.dao.entity.ProdTransferRecord;
import com.sunyard.sunfintech.prod.model.bo.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by terry on 2017/4/28.
 */
public interface IProductInvestmentService {

    public boolean prodTrans(ProdShareInall prodShareInallBuyer, ProdShareInall prodShareInallSaler, BigDecimal changeVol) throws BusinessException;

    /**
     * 标的转让份额更新回滚
     * @param prodShareInallBuyer
     * @param prodShareInallSaler
     * @param changeVol
     * @return
     * @throws BusinessException
     */
    public boolean backProdTrans(ProdShareInall prodShareInallBuyer, ProdShareInall prodShareInallSaler, BigDecimal changeVol) throws BusinessException;

    public boolean addShareList(ProdShareList prodShareList);

    public boolean addTransferRecord(ProdTransferRecord transferRecord) throws BusinessException;

    /**
     * 标的转让回滚转让数据
     * @param trans_serial
     * @return
     * @throws BusinessException
     */
    public boolean delProdTransDate(String trans_serial) throws BusinessException;

    /**
     * 标的转让
     * @param prodTransferDebtRequestBo
     * @return
     * @throws BusinessException
     */
    public boolean assignment(ProdTransferDebtRequestBo prodTransferDebtRequestBo)throws BusinessException;

    /**
     * 标的转让（批量）
     * @param prodBatchTransferRequestBo
     * @return
     * @throws BusinessException
     */
    public ProdBatchTransferReturnData batchAssignment(ProdBatchTransferRequestBo prodBatchTransferRequestBo)throws BusinessException;

    /**
     * 异步标的转让（批量）
     * @param prodBatchTransferRequestBo
     * @return
     * @throws BusinessException
     */
    public boolean batchAssignmentNoSync(ProdBatchTransferAsynRequestBo prodBatchTransferRequestBo)throws BusinessException;

    /**
     * 批量投标
     * @param baseRequest
     * @param prodProductInfo
     * @param prodInvestDataList
     * @return
     * @throws BusinessException
     */
    public ProdInvestReturnData batchInvest(BaseRequest baseRequest, ProdProductinfo prodProductInfo, List<ProdInvestDataTb> prodInvestDataList)throws BusinessException;
    public  ProdInvestReturnData invest(BaseRequest baseRequest, ProdProductinfo prodProductInfo, ProdInvestDataTb prodInvestDataList) throws BusinessException ;
    public boolean saveInvestmentInfo(BaseRequest baseRequest,  ProdShareList shareList  ,ProdProductinfo productInfo, BigDecimal realVol) throws BusinessException;

        /**
         * 异步批量投标
         * @param prodInvestNoSynResquest
         * @return
         * @throws BusinessException
         */
    public BaseResponse batchInvestNoSync(ProdInvestNoSynResquest prodInvestNoSynResquest,boolean isNeedValidAuth)throws BusinessException;

    /**
     * 验密债转申请
     * @param request
     * @return
     * @throws BusinessException
     */
    boolean nonAuthProdTransferApply(ProdTransferNonAuthRequest request)throws BusinessException;

    /**
     * 投标申请
     * @param prodInvestApplyRequest
     * @return
     * @throws BusinessException
     */
    public boolean applyInvest(ProdInvestApplyRequest prodInvestApplyRequest)throws BusinessException;

    /**
     * 异步批量确认投标
     * @param prodInvestNoSynResquest
     * @return
     * @throws BusinessException
     */
    public BaseResponse batchInvestConfirmAsync(ProdInvestNoSynResquest prodInvestNoSynResquest)throws BusinessException;

}
