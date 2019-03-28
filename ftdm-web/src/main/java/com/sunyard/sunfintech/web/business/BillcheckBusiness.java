package com.sunyard.sunfintech.web.business;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.billcheck.model.bo.SortingRequest;
import com.sunyard.sunfintech.billcheck.model.bo.SortingResponse;
import com.sunyard.sunfintech.billcheck.provider.IBillCheckService;
import com.sunyard.sunfintech.billcheck.provider.IClearService;
import com.sunyard.sunfintech.core.base.BaseMessage;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.dic.SortingType;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.core.util.URLConfigUtil;
import com.sunyard.sunfintech.dao.entity.ClearResult;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.user.provider.IAccountSearchService;
import com.sunyard.sunfintech.web.threads.SingleThreadPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.sunyard.sunfintech.core.Constants.REDISKEY_SYS_PARAMETER;

/**
 * Created by dingjy on 2017/6/12.
 */
@Service("billcheckBusiness")
public class BillcheckBusiness {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private IBillCheckService billcheckService;

    @Autowired
    private IClearService clearService;

    @Resource(name = "notifyBusiness")
    private NotifyBusiness notifyBusiness;

    //    @Autowired
//    private ISettlementStatusSearchService settlementStatusSearchService;
//
    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private IAccountSearchService accountSearchService;

//    @Autowired
//    private JobService jobService;
//
//    private static final String REDISKEY_SYS_PARAMETER = Constants.CACHE_NAMESPACE + "billcheck:";
//
//    // public void  accountFund(AccountFundRequest accountFundRequest){
//    //billcheckService.accountFund(accountFundRequest);
//    //}
//
//    /**
//     * 查询清算状态
//     *
//     * @param searchSettlementStatusRequest
//     * @return
//     */
//    public SearchSettlementStatusResponse getSettlementstatus(SearchSettlementStatusRequest searchSettlementStatusRequest) {
//        SearchSettlementStatusResponse searchSettlementStatusResponse = new SearchSettlementStatusResponse();
//        //获取前一天对账时间
//        Date yesterday = DateUtils.getYesterday();
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("clear_date", DateUtils.formatDateToStr(yesterday, "yyyy-MM-dd"));
//        params.put("plat_no", searchSettlementStatusRequest.getPlat_no());
//        params.put("mall_no", searchSettlementStatusRequest.getMall_no());
//
//        try {
//            logger.info("开始查询清算状态，请求参数为：" + params.toString());
//            //SearchSettlementStatusResponseData searchSettlementStatusResponseData = new SearchSettlementStatusResponseData();
//            //searchSettlementStatusResponseData=billcheckService.getClearstatus(params);
//            //添加一下data为空的情况判断  还是在service层处理好了
//            //logger.info("查询清算状态完毕，返回数据为："+searchSettlementStatusResponseData.toString());
//
//            searchSettlementStatusResponse.setRecode(BusinessMsg.SUCCESS);
//            searchSettlementStatusResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
//            //searchSettlementStatusResponse.setSearchSettlementStatusResponseData(searchSettlementStatusResponseData);
//            searchSettlementStatusResponse.setSign("sunyardcaj");
//        } catch (BusinessException e) {
//            logger.info("查询清算状态失败，错误信息为：" + e);
//
//            searchSettlementStatusResponse.setRecode(e.getBaseResponse().getRecode());
//            searchSettlementStatusResponse.setRemsg(e.getBaseResponse().getRemsg());
//        }
//        searchSettlementStatusResponse.setOrder_no(searchSettlementStatusRequest.getOrder_no());
//        return searchSettlementStatusResponse;
//    }
//

    /**
     * 请求清分文件
     *
     * @param sortingRequest
     * @author Lid
     */
    public BaseMessage requestSortingFile(final SortingRequest sortingRequest) throws BusinessException {
        BaseResponse baseResponse = null;
//        BaseHttpResponse baseHttpResponse = null;
        BaseMessage baseMessage = new BaseMessage();
        SortingResponse sortingResponse = new SortingResponse();
        logger.info("===================================清算开始获取锁==================================");
        if (SortingType.START.getCode().equals(CacheUtil.getCache().getAndNotSetAlive(REDISKEY_SYS_PARAMETER + sortingRequest.getMall_no()))) {
            baseMessage.setRecode(BusinessMsg.FAIL);
            baseMessage.setRemsg("正在清算，不能重复清算！");
            return baseMessage;
        }
        String mall_no = accountSearchService.queryNoCacheMallNoByPlatNo(sortingRequest.getMall_no());
        if(StringUtils.isEmpty(mall_no)){
            logger.info("【清算】传进来的是集团号");
            mall_no = sortingRequest.getMall_no();
        }else {
            logger.info("【清算】传进来的是平台号");
        }
        List<String> platNos = accountSearchService.queryNoCachePlatNoByMallNo(mall_no);
        if(platNos.size() == 0){
            logger.info("【清算】该集团下查询不出平台，mall_no:" + mall_no);
            baseMessage.setRecode(BusinessMsg.FAIL);
            baseMessage.setRemsg("该集团下查询不出平台！");
            return baseMessage;
        }
        //检查对账文件是否生成
        List<ClearResult> clearResults = billcheckService.getClearResult(platNos.get(0), sortingRequest.getClear_date().replace("-",""));
        if (clearResults.size() == 0) {
            baseMessage.setRecode(BusinessMsg.FAIL);
            baseMessage.setRemsg("存管对账文件暂未生成！");
            return baseMessage;
        }
        try {
            logger.info("===================================开始确认账单全部对账完成==================================");
            baseResponse = billcheckService.checkClearResult(sortingRequest);
            if (BusinessMsg.FAIL.equals(baseResponse.getRecode())) {
                baseMessage.setRecode(baseResponse.getRecode());
                baseMessage.setRemsg(baseResponse.getRemsg());
                return baseMessage;
            }
        } catch (BusinessException e) {
            logger.info("===================================该集团账单未对账完成==================================");
            baseMessage.setRecode(e.getErrorCode());
            baseMessage.setRemsg(e.getErrorMsg());
            return baseMessage;
        }
        String plat_server = sysParameterService.querySysParameter(mall_no, URLConfigUtil.PLAT_SERVER);
        String plat_sorting_url = sysParameterService.querySysParameter(mall_no, URLConfigUtil.PLAT_SORTING_URL);
        String notify_url = plat_server + "/" + plat_sorting_url;
        logger.info("===================================行内请求参数【mall_no：" + mall_no + "】==================================");
        logger.info("【通知清算】查询该平台是否需要发送通知");
        String needNotify = sysParameterService.querySysParameter(mall_no, URLConfigUtil.CLEAR_NOTIFY);
        if ("1".equals(needNotify)) {
            logger.info("【通知清算】该平台需要通知");
            //通知平台
            logger.info("===================================把通知对象转成json==================================");
            sortingResponse.setMall_no(mall_no);
            sortingResponse.setClear_date(sortingRequest.getClear_date());
            sortingResponse.setType(SortingType.START.getCode());
            String json = JSON.toJSONString(sortingResponse);
            logger.info("===================================开始通知平台要开始清算==================================");
            Boolean flag = notifyBusiness.nofity(mall_no,notify_url, json);
            if (!flag) {
                logger.info("通知清算失败");
                baseMessage.setRecode(BusinessMsg.FAIL);
                baseMessage.setRemsg("通知平台失败");
                return baseMessage;
            }
        }
        try {
            logger.info("【通知清算】添加清算锁");
            CacheUtil.getCache().set(REDISKEY_SYS_PARAMETER + mall_no, SortingType.START.getCode(), 30 * 60);
            //异步对账，同步返回成功
            SingleThreadPool.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    // 调用清算系统生成清分文件
                    if(platNos.size() == 1){
                        //平台清算,传入平台号
                        sortingRequest.setMall_no(platNos.get(0));
                    }
                    billcheckService.requestSortingFile(sortingRequest);
                    logger.info("===================================调用清算系统生成清分文件结束==================================");
                }
            });
            baseMessage.setRecode(BusinessMsg.SUCCESS);
            baseMessage.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        } catch (BusinessException e) {
            logger.error("========================清算异常删除清算锁===========处理失败：" + e);
            baseMessage.setRecode(e.getErrorCode());
            baseMessage.setRemsg(e.getErrorMsg());
            CacheUtil.getCache().del(REDISKEY_SYS_PARAMETER + mall_no);
        } catch (Exception e) {
            logger.error("========================清算异常，删除清算锁===========处理失败：", e);
            CacheUtil.getCache().del(REDISKEY_SYS_PARAMETER + mall_no);
        }
        return baseMessage;
    }

    /**
     * 清算所有金额
     *
     * @author daijh
     */
    public BaseMessage clearAll(String plat_no) throws BusinessException {
        BaseResponse baseResponse = clearService.clearAll(plat_no);
        return baseResponse;
    }

        /**
         * 清分结果通知接口
         *
         * @param sortingRequest
         * @author Lid
         */
    public BaseMessage sortingResultNotify(SortingRequest sortingRequest) throws BusinessException {
//        String notify_url = sysParameterService.querySysParameter(sortingRequest.getMall_no(), "sorting_notify_url");
//        String notify_url = PropertiesUtil.getString("notify_url");
        BaseMessage baseMessage = new BaseMessage();
        SortingResponse sortingResponse = new SortingResponse();
        try {
            logger.info("===================================更新标识：2结束清算==================================");
            String plat_server = sysParameterService.querySysParameter(sortingRequest.getMall_no(), URLConfigUtil.PLAT_SERVER);
            String plat_sorting_url = sysParameterService.querySysParameter(sortingRequest.getMall_no(), URLConfigUtil.PLAT_SORTING_URL);
            String notify_url = plat_server + "/" + plat_sorting_url;
            CacheUtil.getCache().set(REDISKEY_SYS_PARAMETER + sortingRequest.getMall_no(), SortingType.END.getCode(), 15 * 60);
            logger.info("===================================行内请求参数【mall_no：" + sortingRequest.getMall_no() + "】==================================");
            //logger.info("===================================收银台请求参数【mer_no："+sortingRequest.getMer_no()+"】==================================");
            logger.info("===================================行内请求参数【clear_date：" + "】==================================");
            logger.info("===================================开始调用清算系统在途转现金==================================");

            logger.info("===================================调用清算系统在途转现金结束==================================");
            //通知平台
            logger.info("===================================把通知对象转成json==================================");
            sortingResponse.setMall_no(sortingRequest.getMall_no());
            //sortingResponse.setMer_no(sortingRequest.getMer_no());
            sortingResponse.setClear_date(sortingRequest.getClear_date());
            sortingResponse.setType(SortingType.END.getCode());
            String json = JSON.toJSONString(sortingResponse);
            logger.info("===================================开始调用清算系统在途转现金==================================");
            notifyBusiness.nofity(sortingRequest.getMall_no(),notify_url, json);
            baseMessage.setRecode(BusinessMsg.SUCCESS);
            baseMessage.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        } catch (BusinessException e) {
            logger.error("===================================处理失败：" + e.getErrorMsg() + "==================================");
            baseMessage.setRecode(e.getErrorCode());
            baseMessage.setRemsg(e.getErrorMsg());
        }

        return baseMessage;
    }

//    public SortingSearchResponse searchSettlementStatus(SettlementStatusSearchRequest settlementStatusSearchRequest) throws BusinessException {
//        logger.info("===================================【清算结果状态查询】====================strat==============");
//        SortingSearchResponse sortingSearchResponse = new SortingSearchResponse();
//        SortingSearchDataResponse sortingSearchDataResponse = new SortingSearchDataResponse();
//        try {
//            sortingSearchResponse = settlementStatusSearchService.searchSettlementStatus(settlementStatusSearchRequest);
//        } catch (BusinessException e) {
//            sortingSearchResponse.setRecode(e.getErrorCode());
//            sortingSearchResponse.setRemsg(e.getErrorMsg());
//        }
//        logger.info("===================================【清算结果状态查询】====================end==============");
//        return sortingSearchResponse;
//    }
}
