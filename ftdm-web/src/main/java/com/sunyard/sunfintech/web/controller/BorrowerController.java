package com.sunyard.sunfintech.web.controller;

import com.sunyard.sunfintech.core.annotation.CheckPassword;
import com.sunyard.sunfintech.core.annotation.Log;
import com.sunyard.sunfintech.core.annotation.Sign;
import com.sunyard.sunfintech.core.base.BaseController;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.user.model.bo.*;
import com.sunyard.sunfintech.user.modelold.bo.BatchRepayAsynRequestOld;
import com.sunyard.sunfintech.user.modelold.bo.BatchRepayRequestOld;
import com.sunyard.sunfintech.user.modelold.bo.CompensateRepayRequestOld;
import com.sunyard.sunfintech.user.modelold.bo.SubstituteRepayRequestOld;
import com.sunyard.sunfintech.web.business.UserBusiness;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 借款人还款对外服务接口
 * Created by wubin on 2017/5/3.
 */
@RestController
@RequestMapping("/borrower")
public class BorrowerController extends BaseController {

	@Resource(name = "userBusiness")
    private UserBusiness userBusiness;

    /**
     * 借款人还款申请  异步接口
     * @author pengzi
     * @param httpServletRequest request请求参数
     * @param batchRepayRequest 借款人批量还款请求参数
     * @return BatchRepayResponse 借款人批量还款响应参数
     */
    @RequestMapping("/apply_auth_repay")
    @Sign
    @Log(method = "batchRepayAsyn",batchDataName = "data")
    public BatchInvestNoSynResponse batchRepayAsyn(HttpServletRequest httpServletRequest, BatchRepayAsynRequest batchRepayRequest) {
        logger.info("【借款人还款申请】-->开始-->order_no:"+batchRepayRequest.getOrder_no());
        validate(batchRepayRequest);
        long start = System.currentTimeMillis();
        BatchInvestNoSynResponse batchRepayResponse = userBusiness.batchRepayAsyn(batchRepayRequest);
        long end = System.currentTimeMillis();
        logger.info("【借款人还款申请】-->结束-->耗时:"+(end-start)+"-->order_no:"+batchRepayRequest.getOrder_no());
        return batchRepayResponse;
    }

    /**
     * 代偿还款
     * @author PengZY
     * @param httpServletRequest request请求参数
     * @param substituteRepayRequest 标的代偿（委托）还款请求参数
     * @return BatchRepayResponse 标的代偿（委托）还款响应参数
     */
    @RequestMapping("/substitute_repay")
    @Sign(checkPassword = CheckPassword.YES, platcust = "compensation_platcust")
    @Log(method = "substituteRepay")
    public SubstituteRepayResponse substituteRepay(HttpServletRequest httpServletRequest, SubstituteRepayRequest substituteRepayRequest){
        logger.info("【代偿还款】-->开始-->order_no:"+substituteRepayRequest.getOrder_no());
        long start = System.currentTimeMillis();
        validate(substituteRepayRequest);
        SubstituteRepayResponse substituteRepayResponse = userBusiness.substituteRepay(substituteRepayRequest);
        long end = System.currentTimeMillis();
        logger.info("【代偿还款】-->结束-->order_no:"+substituteRepayRequest.getOrder_no()+"-->耗时:"+(end-start));
        return substituteRepayResponse;
    }

    /**
     *  授权代偿还款
     * @author PengZY
     * @param httpServletRequest request请求参数
     * @param substituteRepayRequest 标的代偿（委托）还款请求参数
     * @return BatchRepayResponse 标的代偿（委托）还款响应参数
     */
    @RequestMapping("/auth_substitute_repay")
    @Sign
    @Log(method = "authSubstituteRepay")
    public SubstituteRepayResponse authSubstituteRepay(HttpServletRequest httpServletRequest, SubstituteRepayRequest substituteRepayRequest){
        logger.info("【授权代偿还款】-->开始-->order_no:"+substituteRepayRequest.getOrder_no());
        long start = System.currentTimeMillis();
        validate(substituteRepayRequest);
        SubstituteRepayResponse substituteRepayResponse = userBusiness.authSubstituteRepay(substituteRepayRequest);
        long end = System.currentTimeMillis();
        logger.info("【授权代偿还款】-->开始-->order_no:"+substituteRepayRequest.getOrder_no()+"-->耗时:"+(end-start));
        return substituteRepayResponse;
    }

    /**
     * 借款人还款代偿
     * @author PengZY
     * @param httpServletRequest request请求参数
     * @param compensateRepayRequest 借款人还款代偿还款 请求参数
     * @return CompensateRepayResponse 借款人还款代偿 还款响应参数
     */
    @RequestMapping("/compensate_repay")
    @Sign
    @Log(method = "compensate_repay")
    public CompensateRepayResponse compensate_repay(HttpServletRequest httpServletRequest, CompensateRepayRequest compensateRepayRequest){
        logger.info("【借款人还款代偿】-->开始-->order_no:"+compensateRepayRequest.getOrder_no());
        long start = System.currentTimeMillis();
        validate(compensateRepayRequest);
        CompensateRepayResponse compensateRepayResponse  = userBusiness.compensateRepay(compensateRepayRequest);
        long end = System.currentTimeMillis();
        logger.info("【借款人还款代偿】-->结束-->order_no:"+compensateRepayRequest.getOrder_no()+"-->耗时:"+(end-start));
        return compensateRepayResponse;
    }

    @RequestMapping("/sub_account")
    @Sign
    @Log(method = "sub_account")
    public BorrowerSubAccountResponse sub_account(HttpServletRequest httpServletRequest, BorrowerSubAccountRequest borrowerSubAccountRequest)throws BusinessException{
        BorrowerSubAccountResponse borrowerSubAccountResponse=new BorrowerSubAccountResponse();
        validate(borrowerSubAccountRequest);

        borrowerSubAccountResponse=userBusiness.sub_account(borrowerSubAccountRequest);
        borrowerSubAccountResponse.setOrder_no(borrowerSubAccountRequest.getOrder_no());
       return borrowerSubAccountResponse;
    }

    /**
     * 借款人批量还款  同步老接口
     * @param httpServletRequest request请求参数
     * @param batchRepayRequestOld 借款人批量还款请求参数
     * @return BatchRepayResponse 借款人批量还款响应参数
     */
    @RequestMapping("/batch_repay_old")
    @Sign
    @Log(method = "batchRepayOld",batchDataName = "data")
    public BatchRepayResponse batchRepayOld(HttpServletRequest httpServletRequest, BatchRepayRequestOld batchRepayRequestOld){
        validate(batchRepayRequestOld);

        BatchRepayResponse batchRepayResponse=userBusiness.batchRepayOld(batchRepayRequestOld);
        batchRepayResponse.setOrder_no(batchRepayRequestOld.getOrder_no());
        return batchRepayResponse;
    }

    /**
     * 借款人批量还款  异步老接口
     * @author wubin
     * @param httpServletRequest request请求参数
     * @param batchRepayRequest 借款人批量还款请求参数
     * @return BatchRepayResponse 借款人批量还款响应参数
     */
    @RequestMapping("/batch_repay_asyn_old")
    @Sign
    @Log(method = "batchRepayAsynOld",batchDataName = "data")
    public BatchInvestNoSynResponse batchRepayAsynOld(HttpServletRequest httpServletRequest, BatchRepayAsynRequestOld batchRepayRequest) {
        logger.info("【借款人批量还款old】-->请求参数："+batchRepayRequest.toString());
        BatchInvestNoSynResponse batchRepayResponse = new BatchInvestNoSynResponse();
        validate(batchRepayRequest);
        try {
            batchRepayResponse=userBusiness.batchRepayAsynOld(batchRepayRequest);

        } catch (BusinessException e) {
            batchRepayResponse.setRecode(e.getBaseResponse().getRecode());
            batchRepayResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        batchRepayResponse.setOrder_no(batchRepayRequest.getOrder_no());
        return batchRepayResponse;
    }

    /**
     * 标的代偿（委托）还款
     * @author PengZY
     * @param httpServletRequest request请求参数
     * @param substituteRepayRequest 标的代偿（委托）还款请求参数
     * @return BatchRepayResponse 标的代偿（委托）还款响应参数
     */
    @RequestMapping("/substitute_repay_old")
    @Sign
    @Log(method = "substituteRepayOld")
    public SubstituteRepayResponse substituteRepayOld(HttpServletRequest httpServletRequest, SubstituteRepayRequestOld substituteRepayRequest){

        logger.info("【标的代偿（委托）还款】开始，order_no:"+substituteRepayRequest.getOrder_no());
        long start = System.currentTimeMillis();
        SubstituteRepayResponse substituteRepayResponse = new SubstituteRepayResponse();
        validate(substituteRepayRequest);

        substituteRepayResponse = userBusiness.substituteRepayOld(substituteRepayRequest);
        substituteRepayResponse.setOrder_no(substituteRepayRequest.getOrder_no());
        long end = System.currentTimeMillis();
        logger.info("【标的代偿（委托）还款】结束,order_no:"+substituteRepayRequest.getOrder_no()+"耗时："+(end-start));
        return substituteRepayResponse;
    }

    /**
     * 借款人还款代偿（委托）  老接口
     * @author PengZY
     * @param httpServletRequest request请求参数
     * @param compensateRepayRequest 标的代偿（委托）还款请求参数
     * @return CompensateRepayResponse 标的代偿（委托）还款响应参数
     */
    @RequestMapping("/compensate_repay_old")
    @Sign
    @Log(method = "compensate_repay")
    public CompensateRepayResponse compensate_repay(HttpServletRequest httpServletRequest, CompensateRepayRequestOld compensateRepayRequest)throws BusinessException{

        CompensateRepayResponse compensateRepayResponse = new CompensateRepayResponse();
        validate(compensateRepayRequest);

        compensateRepayResponse = userBusiness.compensateRepayOld(compensateRepayRequest);
        compensateRepayResponse.setOrder_no(compensateRepayRequest.getOrder_no());
        return compensateRepayResponse;
    }


}
