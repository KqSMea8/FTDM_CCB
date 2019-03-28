package com.sunyard.sunfintech.prod.provider;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import com.sunyard.sunfintech.dao.entity.ProdProductinfo;
import com.sunyard.sunfintech.dao.entity.ProdShareList;
import com.sunyard.sunfintech.dao.entity.ProdTransferRecord;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by terry on 2017/9/12.
 */
public interface IProdTransferService {

    /**
     * 债转转账
     * @param prodShareListOut
     * @param prodShareListIn
     * @param transferRecord
     * @param eaccAccountamtlist
     * @return
     * @throws BusinessException
     */
    public boolean prodTransTransfer(BaseRequest baseRequest,ProdShareList prodShareListOut, ProdShareList prodShareListIn, ProdTransferRecord transferRecord, List<EaccAccountamtlist> eaccAccountamtlist,boolean transType)throws BusinessException;

    /**
     * 投资转账
     * @param shareList
     * @param batchEaccAccountamtList
     * @param baseRequest
     * @return
     * @throws BusinessException
     */
  //  public boolean prodInvestTransfer(ProdShareList shareList, List<EaccAccountamtlist> batchEaccAccountamtList, BaseRequest baseRequest)throws BusinessException;
    public  void saveInvestAsyncData(BaseRequest baseRequest, ProdShareList shareList  , List<EaccAccountamtlist> batchEaccAccountamtList, ProdProductinfo productInfo, BigDecimal realVol)throws BusinessException;
}
