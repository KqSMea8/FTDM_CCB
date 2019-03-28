package com.sunyard.sunfintech.prod.provider;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.dic.ProdState;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.dao.entity.ProdProductinfo;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.prod.model.bo.*;

/**
 * Created by terry on 2017/7/9.
 */
public interface IProdInvestmentExtService {

    public boolean invest(BaseRequest baseRequest, ProdProductinfo productInfo, ProdInvestDataTb prodInvestData) throws BusinessException;

    public boolean investForNoSyn(BaseRequest baseRequest, ProdInvestData prodInvestData) throws BusinessException;

    public boolean transProd(ProdTransferDebtRequestBo prodTransferDebtRequestBo, TransTransreq transTransreq) throws BusinessException;

    public boolean prodRepayForNoSyn(BaseRequest baseRequest, BatchCustRepay batchCustRepay) throws BusinessException;

}
