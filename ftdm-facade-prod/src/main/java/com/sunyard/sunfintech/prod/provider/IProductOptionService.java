package com.sunyard.sunfintech.prod.provider;

import java.util.List;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.prod.model.bo.*;

/**
 * 标的相关
 * @author heroy
 * @version 2017/4/13
 */
public interface IProductOptionService {


    /**
     * 借款人募集申请

     * @param prodPublishRequest
     * @return ProdPublishResponse
     * @throws BusinessException 抛出的异常
     * @author pengzy
     */
    public ProdPublishResponse publish(ProdPublishRequest prodPublishRequest) throws BusinessException;
    /**
     * 标的发布-老接口
     * @param prodProductinfo 标的信息
     * @return return
     * @throws BusinessException 抛出的异常
     */
    public ProdPublishResponseData publishOld(BaseRequest baseRequest, ProdProductinfo prodProductinfo, List<EaccFinancinfoDetail> eaccFinancinfo, List<ProdCompensationList> compensationlist) throws BusinessException;
    /**
     * 标的成废
     * @param prodEstablishOrAbortRequest
     * @throws BusinessException
     */
    public ProdEstablishOrAbortResponse establishOrAbort(ProdEstablishOrAbortRequest prodEstablishOrAbortRequest) throws BusinessException;

  /**
   * 标的出账信息修改
   * @param baseRequest
   * @param eaccFinancinfo
   * @return
   * @user:dingjy
   * @time:2017年5月15日 上午10:27:28
   */
    public BaseResponse updateExpenditureInfo(BaseRequest baseRequest,EaccFinancinfo eaccFinancinfo) throws BusinessException;

    /**
     * 标的撤资
     * @param prodAbortInvestmentRequest
     * @throws BusinessException
     */
    public ProdAbortInvestmentResponse abortInvestment(ProdAbortInvestmentRequest prodAbortInvestmentRequest) throws BusinessException;

    ProdEstablishOrAbortResponse establishOrAbortOld(ProdEstablishOrAbortRequestOld prodEstablishOrAbortRequest);

    public ProdTruncationResponse endProd(ProdTruncationResquest prodTruncationResquest) throws BusinessException;


}
