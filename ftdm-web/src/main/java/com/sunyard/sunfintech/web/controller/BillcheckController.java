package com.sunyard.sunfintech.web.controller;

import com.sunyard.sunfintech.billcheck.model.bo.SortingRequest;
import com.sunyard.sunfintech.core.annotation.Log;
import com.sunyard.sunfintech.core.base.BaseController;
import com.sunyard.sunfintech.core.base.BaseMessage;
import com.sunyard.sunfintech.web.business.BillcheckBusiness;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对账对外服务接口
 * Created by wubin on 2017/5/5.
 */
@RestController
@RequestMapping("/billchecking")
public class BillcheckController extends BaseController {
    @Resource(name="billcheckBusiness")
    private BillcheckBusiness billcheckBusiness;
//    /**
//     * 对账文件支付渠道资金进出数据
//     * @author wubin
//     * @param httpServletRequest request请求参数
//     * @param reconciliationRequest 对账文件支付渠道资金进出数据请求参数
//     */
//    @RequestMapping("/channel_fund")
//    public void channelFund(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,ReconciliationRequest reconciliationRequest){
//
//        validate(reconciliationRequest);
////
////        FileUtil.download("channelFund.txt",null,httpServletResponse);
//
//        try{
////            URL url= new URL("ftp://fengjr:123456@72.160.0.210:21/wb/1111.txt");
//            String netUrl = "fengjr:123456@72.160.0.210:21";
//            URL url= new URL("ftp://"+netUrl+"/wb/"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"testBillCheck.txt");
//            PrintWriter pw=new PrintWriter(url.openConnection().getOutputStream());
//            for(int i=0;i<3;i++){
//                pw.println("testWb"+i);
//            }
//            pw.flush();
//            pw.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 对账文件存管账户余额数据
//     * @author wubin
//     * @param httpServletRequest request请求参数
//     * @param reconciliationRequest 对账文件支付渠道资金进出数据请求参数
//     */
//    @RequestMapping("/depository_balance")
//    public void depositorybalance(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,ReconciliationRequest reconciliationRequest){
//
//        validate(reconciliationRequest);
//
//        FileUtil.download("depositorybalance.txt",null,httpServletResponse);
//    }
//
//    /**
//     * 对账文件电子账户资金进出数据
//     * @author wubin
//     * @param httpServletRequest request请求参数
//     * @param accountFundRequest 对账文件支付渠道资金进出数据请求参数
//     */
//    @RequestMapping("/eaccount_fund")
//    public void eaccountFund(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,AccountFundRequest accountFundRequest){
//
//        validate(accountFundRequest);
//        String filename=accountFundRequest.getPaycheck_date()+"eaccountFund.txt";
//        String filePath="d:/"+accountFundRequest.getPaycheck_date()+"eaccountFund.txt";
//        //billcheckBusiness.accountFund(accountFundRequest);
//        FileUtil.download(filename,filePath,httpServletResponse);
//    }
//
//    /**
//     * 对账文件电子账户余额数据
//     * @author wubin
//     * @param httpServletRequest request请求参数
//     * @param reconciliationRequest 对账文件支付渠道资金进出数据请求参数
//     */
//    @RequestMapping("/eaccount_balance")
//    public void eaccountBalance(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse, ReconciliationRequest reconciliationRequest){
//
//        validate(reconciliationRequest);
//
//        FileUtil.download("eaccountBalance.txt",null,httpServletResponse);
//    }
//
//    /**
//     * 清算状态查询
//     * @author wubin
//     * @param httpServletRequest request请求参数
//     * @param settlementStatusSearchRequest 对账文件支付渠道资金进出数据请求参数
//     */
//    @RequestMapping("/search_settlement_status")
//    public SortingSearchResponse searchSettlementStatus(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SettlementStatusSearchRequest settlementStatusSearchRequest){
//        validate(settlementStatusSearchRequest);
//        SortingSearchResponse sortingSearchResponse = new SortingSearchResponse();
//        sortingSearchResponse = billcheckBusiness.searchSettlementStatus(settlementStatusSearchRequest);
//        return sortingSearchResponse;
//    }
//
//    /**
//     * 清算数据下载
//     * @author wubin
//     * @param httpServletRequest request请求参数
//     * @param downloadSettlementDataRequest 对账文件支付渠道资金进出数据请求参数
//     */
//    @RequestMapping("/download_settlement_data")
//    public void downloadSettlementData(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,DownloadSettlementDataRequest downloadSettlementDataRequest){
//
//        validate(downloadSettlementDataRequest);
//
//        FileUtil.download("downloadSettlementData.txt",null,httpServletResponse);
//    }
//

    /**
     * 请求清分文件
     *
     * @param httpServletRequest
     * @param sortingRequest
     * @author Lid
     */
    @RequestMapping("/request_sortingfile")
    @Log(method = "requestSortingFile")
    public BaseMessage requestSortingFile(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SortingRequest sortingRequest) {
        validate(sortingRequest);
        BaseMessage baseMessage = billcheckBusiness.requestSortingFile(sortingRequest);
        return baseMessage;
    }

    /**
     * 清算所有在途账户
     * @param httpServletRequest
     * @author daijh
     */
    @RequestMapping("/clearAll")
    public BaseMessage clearAll(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        BaseMessage baseMessage = billcheckBusiness.clearAll(httpServletRequest.getParameter("plat_no"));
        return baseMessage;
    }


    /**
     * 清分结果通知接口
     * @author Lid
     * @param httpServletRequest
     * @param sortingRequest
     */
    @RequestMapping("/sorting_result_rotify")
    @Log(method = "sortingResultNotify")
    public BaseMessage sortingResultNotify(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SortingRequest sortingRequest){
        validate(sortingRequest);
        BaseMessage baseMessage = billcheckBusiness.sortingResultNotify(sortingRequest);
        return baseMessage;
    }
}
