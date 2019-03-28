package com.sunyard.sunfintech.prod.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.prod.model.bo.ProdEstablishOrAbort;
import com.sunyard.sunfintech.prod.model.bo.ProdEstablishOrAbortRequest;
import com.sunyard.sunfintech.prod.model.bo.RepayPlanListObject;

import java.util.List;

/**
 * 标的相关
 * @author heroy
 * @version 2017/4/13
 */
public interface IProductOptionExtService {

    /**
     * 添加标的信息表
     * @param prodProductinfo
     * @return return
     * @throws BusinessException 抛出的异常
     */
    public void publish(ProdProductinfo prodProductinfo,EaccFinancinfo eaccFinancinfo,List<ProdCompensationList> prodCompensationLists, String order_no) throws BusinessException;

    void doEstablish(ProdEstablishOrAbortRequest prodEstablishOrAbort, List<ProdShareList> prodShareLists, List<RepayPlanListObject> repayPlanListObjectList, EaccAccountamtlist eaccAccountamtlist, String prod_type) throws BusinessException;

    void doAbortInvestment(List<EaccAccountamtlist> rollBackList, List<ProdShareList> prodShareLists, ProdShareList prodShareList,ProdProductinfo prodProductInfo) throws BusinessException;
}
