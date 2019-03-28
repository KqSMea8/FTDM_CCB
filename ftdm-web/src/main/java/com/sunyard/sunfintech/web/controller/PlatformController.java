package com.sunyard.sunfintech.web.controller;

import com.sunyard.sunfintech.core.annotation.Log;
import com.sunyard.sunfintech.core.annotation.Sign;
import com.sunyard.sunfintech.core.base.BaseController;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.FileUtil;
import com.sunyard.sunfintech.user.model.bo.*;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.user.model.bo.ChargeResponse;
import com.sunyard.sunfintech.user.model.bo.TransferToPersonRequest;
import com.sunyard.sunfintech.web.business.PlatformBusiness;
import com.sunyard.sunfintech.web.model.vo.platform.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 平台对外服务接口
 * Created by wubin on 2017/5/5.
 */
@RestController
@RequestMapping("/platform")
public class PlatformController extends BaseController{


    @Resource(name = "platformBusiness")
    private PlatformBusiness platformBusiness;

    /**
     * 资金冻结解冻
     * @author pzy
     * @param httpServletRequest request请求参数
     * @param freezeFundRequest 资金冻结解冻请求参数
     * @return FreezeFundResponse 资金冻结解冻响应参数
     */
    @RequestMapping("/freeze_fund")
    @Sign
    @Log(method = "freezeFund")
    public FreezeFundResponse freezeFund(HttpServletRequest httpServletRequest, FreezeFundRequest freezeFundRequest){
        logger.info("【资金冻结解冻】-->web开始-->order_no:"+freezeFundRequest.getOrder_no());
        long start = System.currentTimeMillis();
        validate(freezeFundRequest);
        FreezeFundResponse freezeFundResponse = platformBusiness.freezeFund(freezeFundRequest);
        long end = System.currentTimeMillis();
        logger.info("【资金冻结解冻】-->web开始-->耗时:"+(end-start)+"毫秒-->order_no:" + freezeFundRequest.getOrder_no());
        return freezeFundResponse;
    }


    /**
     * 平台转帐个人
     * @author pzy
     * @param httpServletRequest request请求参数
     * @param transferToPersonRequest 平台转帐个人请求参数
     */
    @RequestMapping("/transfer_to_person")
    @Sign
    @Log(method = "transferToPerson")
    public BaseResponse transferToPerson(HttpServletRequest httpServletRequest,TransferToPersonRequest transferToPersonRequest){
        BaseResponse baseResponse = new BaseResponse();
        validate(transferToPersonRequest);
        try {
            baseResponse = platformBusiness.transferToPerson(transferToPersonRequest);
        }catch (BusinessException e){
            baseResponse.setRecode(e.getErrorCode());
            baseResponse.setRemsg(e.getErrorMsg());
        }
        baseResponse.setOrder_no(transferToPersonRequest.getOrder_no());
        baseResponse.setTrans_date(DateUtils.getyyyyMMddDate());
        return baseResponse;
    }

    /**
     * （批量）平台转帐个人
     * @author pzy
     * @param httpServletRequest request请求参数
     * @param batchTransferToPersonRequest 平台转帐个人请求参数
     */
    @RequestMapping("/batch_transfer_to_person")
    @Sign
    @Log(method = "batchTransferToPerson",batchDataName = "data")
    public BatchTransferToPersonResponse batchTransferToPerson(HttpServletRequest httpServletRequest, BatchTransferToPersonRequest batchTransferToPersonRequest){
        validate(batchTransferToPersonRequest);
        BatchTransferToPersonResponse batchTransferToPersonResponse = new BatchTransferToPersonResponse();
        try {
            Long start = System.currentTimeMillis();
            batchTransferToPersonResponse = platformBusiness.batchTransferToPerson(batchTransferToPersonRequest);
            Long end = System.currentTimeMillis();
            logger.info("【批量平台转个人】同步返回耗时：" + String.valueOf(end - start));
        }catch (BusinessException e){
            batchTransferToPersonResponse.setRecode(e.getErrorCode());
            batchTransferToPersonResponse.setRemsg(e.getErrorMsg());
        }
        batchTransferToPersonResponse.setOrder_no(batchTransferToPersonRequest.getOrder_no());
        batchTransferToPersonResponse.setTrans_date(DateUtils.getyyyyMMddDate());
        return batchTransferToPersonResponse;
    }


    /**
     * 平台转帐个人撤销
     * @author wuml
     * @param httpServletRequest request请求参数
     * @param transferToPersonBackRequest 平台转帐个人撤销请求参数
     */
    @RequestMapping("/rollback_plat2person")
    @Sign
    @Log(method = "transferToPersonBack")
    public BaseResponse transferToPersonBack(HttpServletRequest httpServletRequest, TransferToPersonBackRequest transferToPersonBackRequest){
       BaseResponse baseResponse=new BaseResponse();
       validate(transferToPersonBackRequest);
       try {
           baseResponse = platformBusiness.rollback_plat2person(transferToPersonBackRequest);
       }catch (BusinessException e){
           baseResponse.setRecode(e.getErrorCode());
           baseResponse.setRemsg(e.getErrorMsg());
       }
        baseResponse.setOrder_no(transferToPersonBackRequest.getOrder_no());
        baseResponse.setTrans_date(DateUtils.getyyyyMMddDate());
        return baseResponse;
    }

    /**
     * 个人转平台
     * @param httpServletRequest
     * @param transferToPersonRequest
     * @return
     */
/*    @RequestMapping("/person_to_transfer")
    @Sign
    public BaseResponse transferFromPerson(HttpServletRequest httpServletRequest, TransferToPersonRequest transferToPersonRequest){
        validate(transferToPersonRequest);
        BaseResponse baseResponse = platformBusiness.transferFromPerson(transferToPersonRequest);
        baseResponse.setOrder_no(transferToPersonRequest.getOrder_no());
        return baseResponse;
    }*/

    /**
     * 平台提现
     * @author pzy
     * @param httpServletRequest request请求参数
     * @param withdrawRequest 平台提现请求参数
     * @return WithdrawResponse 平台提现响应参数
     */
    @RequestMapping("/withdraw")
    @Sign
    @Log(method = "withdraw")
    public WithdrawResponse withdraw(HttpServletRequest httpServletRequest, WithdrawRequest withdrawRequest){
        logger.info("【平台提现】-->web开始-->order_no:"+withdrawRequest.getOrder_no());
        long start = System.currentTimeMillis();
        validate(withdrawRequest);
        WithdrawResponse withdrawResponse = platformBusiness.withdraw(withdrawRequest);
        long end = System.currentTimeMillis();
        logger.info("【平台提现】-->web开始-->耗时:"+(end-start)+"毫秒-->order_no:"+withdrawRequest.getOrder_no());
        return withdrawResponse;
    }


    /**
     * 平台不同账户转账
     * @author pzy
     * @param httpServletRequest request请求参数
     * @param transferToPlatformRequest 平台不同账户转账请求参数
     * @return BaseResponse 平台不同账户转账响应参数
     */
   @RequestMapping("/transfer_to_platform")
   @Sign
   @Log(method = "transferToPlatform")
    public TransferToPlatformResponse transferToPlatform(HttpServletRequest httpServletRequest, TransferToPlatformRequest transferToPlatformRequest){
        TransferToPlatformResponse transferToPlatformResponse = new TransferToPlatformResponse();
        validate(transferToPlatformRequest);

        transferToPlatformResponse = platformBusiness.transferToPlatform(transferToPlatformRequest);
        transferToPlatformResponse.setOrder_no(transferToPlatformRequest.getOrder_no());
        return transferToPlatformResponse;
    }

    /**
     * 平台充值
     * @author wubin
     * @param httpServletRequest request请求参数
     * @param chargeRequest 平台充值请求参数
     * @return  平台充值响应参数
     */
    @RequestMapping("/charge")
    @Sign
    @Log(method = "charge")
    public ChargeResponse charge(HttpServletRequest httpServletRequest, ChargeRequest chargeRequest){
        validate(chargeRequest);
        ChargeResponse chargeResponse = platformBusiness.charge(chargeRequest);
        chargeResponse.setOrder_no(chargeRequest.getOrder_no());
        chargeResponse.setTrans_date(DateUtils.getyyyyMMddDate());
        return chargeResponse;
    }

    /**
     * 平台非存管账户出金
     * @author wubin
     * @param httpServletRequest request请求参数
     * @param withdrawNoDepositoryRequest 平台非存管账户出金请求参数
     * @return WithdrawNoDepositoryResponse 平台非存管账户出金响应参数
     */
    @RequestMapping("/withdraw_no_depository")
    @Log(method = "withdrawNoDepository")
    public WithdrawNoDepositoryResponse withdrawNoDepository(HttpServletRequest httpServletRequest, WithdrawNoDepositoryRequest withdrawNoDepositoryRequest){

        validate(withdrawNoDepositoryRequest);
        WithdrawNoDepositoryResponse withdrawNoDepositoryResponse = new WithdrawNoDepositoryResponse();
        withdrawNoDepositoryResponse.setSign("1");
        withdrawNoDepositoryResponse.setRecode("10000");
        withdrawNoDepositoryResponse.setProcess_date("Thu May 04 15:13:04 CST 2017");
        withdrawNoDepositoryResponse.setRemsg("成功");

        WithdrawNoDepository withdrawNoDepository = new WithdrawNoDepository();
        withdrawNoDepositoryResponse.setWithdrawNoDepository(withdrawNoDepository);
        return withdrawNoDepositoryResponse;
    }

    /**
     * 自有资金账户手续费扣款查询
     * @author wubin
     * @param httpServletRequest request请求参数
     * @param substitutePayFeeRequest 自有资金账户手续费扣款查询请求参数
     * @return SubstitutePayFeeResponse 自有资金账户手续费扣款查询响应参数
     */
    @RequestMapping("/substitute_pay_fee")
    @Sign
    @Log(method = "substitutePayFee")
    public SubstitutePayFeeResponse substitutePayFee(HttpServletRequest httpServletRequest, SubstitutePayFeeRequest substitutePayFeeRequest){

        validate(substitutePayFeeRequest);
        SubstitutePayFeeResponse substitutePayFeeResponse = new SubstitutePayFeeResponse();
        try{
            List<SubstitutePayFeeData> substitutePayFeeDataList=platformBusiness.substitutePayFee(substitutePayFeeRequest);
            substitutePayFeeResponse.setSubstitutePayFeeData(substitutePayFeeDataList);
            substitutePayFeeResponse.setRecode(BusinessMsg.SUCCESS);
            substitutePayFeeResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        }catch (BusinessException e){
            substitutePayFeeResponse.setRecode(e.getBaseResponse().getRecode());
            substitutePayFeeResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        substitutePayFeeResponse.setOrder_no(substitutePayFeeRequest.getOrder_no());
        return substitutePayFeeResponse;
    }

    /**
     * 账户流水单据下载
     * @author wubin
     * @param httpServletRequest request请求参数
     * @param downloadAccountListRequest 账户流水单据下载请求参数
     */
    @RequestMapping("/download_account_list")
    @Log(method = "downloadAccountList")
    public void downloadAccountList(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,DownloadAccountListRequest downloadAccountListRequest){

        validate(downloadAccountListRequest);
        try{
            String filePath=platformBusiness.downAccountList(downloadAccountListRequest,httpServletRequest);
            String fileName="【账户流水单据】"+downloadAccountListRequest.getStart_date()+"-"+downloadAccountListRequest.getEnd_date()+".pdf";
            FileUtil.download(fileName,filePath,httpServletResponse);
        }catch (BusinessException e){
            logger.error("【账户流水单据下载】："+e.getBaseResponse().getRemsg());
        }
    }

}
