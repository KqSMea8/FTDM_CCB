package com.sunyard.sunfintech.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.entity.TransTransreqExample.Criteria;
import com.sunyard.sunfintech.dao.mapper.PlatCardinfoMapper;
import com.sunyard.sunfintech.dao.mapper.RwRechargeMapper;
import com.sunyard.sunfintech.dao.mapper.RwRecodeMapper;
import com.sunyard.sunfintech.dao.mapper.TransTransreqMapper;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import com.sunyard.sunfintech.user.model.bo.*;
import com.sunyard.sunfintech.user.provider.IPlatCacheService;
import com.sunyard.sunfintech.user.provider.IThirdpartySearchService;
import com.sunyard.sunfintech.user.provider.IUserAccountService;
import com.sunyard.sunfintech.user.provider.IUserTransService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.List;

/**
 * Created by dingjy on 2017/5/23.
 */

@Service(interfaceClass = IThirdpartySearchService.class)
@CacheConfig(cacheNames = "thirdpartySearchService")
@org.springframework.stereotype.Service
public class ThirdpartySearchService  implements IThirdpartySearchService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * 业务流水表mapper
     */
    @Autowired
    private TransTransreqMapper transReqMapper;
    /**
     * 充值订单
     */
    @Autowired
    private RwRechargeMapper rwRechargeMapper;
    /**
     * 退票补单
     */
    @Autowired
    private RwRecodeMapper rwRecodeMapper;

    @Autowired
    private IAccountQueryService accountQueryService;

    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private IPlatCacheService platCacheService;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private ITransReqService transReqService;

    @Autowired
    private IUserTransService userTransService;

    @Autowired
    private PlatCardinfoMapper platCardinfoMapper;
    /**
     * 订单状态查询
     *
     * @param orderStatusRequire
     * @return TransTransReq
     * @throws BusinessException
     */
    public TransTransreq QueryOrderStatus(OrderStatusRequire orderStatusRequire) throws BusinessException {

        /**
         * 订单状态查询步骤
         * 1、查redis
         * 2、再查库
         * 3、redis和数据库中状态不一致，进行补单
         */
        logger.info("search:---------------订单状态查询，order_no："+orderStatusRequire.getQuery_order_no());
        String keys=Constants.TRANSREQ_ORDER_KEY+orderStatusRequire.getQuery_order_no();
        Object transreqObj=CacheUtil.getCache().get(keys);

        //查询业务流水表
        TransTransreqExample example = new TransTransreqExample();
        Criteria criteria = example.createCriteria();
        criteria.andPlat_noEqualTo(orderStatusRequire.getMer_no());
        criteria.andOrder_noEqualTo(orderStatusRequire.getQuery_order_no());
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<TransTransreq> transTransReqs = transReqMapper.selectByExample(example);
        if (transTransReqs.size() > 1) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "数据库有重复的订单");
            throw new BusinessException(baseResponse);
        }
        if(transreqObj==null ){
            if(transTransReqs.size()==0){
                return null;
            }else{
                return transTransReqs.get(0);
            }
        }else{
            TransTransreq transTransreqRedis= (TransTransreq) transreqObj;
            if(transTransReqs.size()==0){
                //TODO:根据业务检查补单状态
                //补单
                transReqService.addFlow(transTransreqRedis);
            }else{
                TransTransreq transTransreqDB=transTransReqs.get(0);
                if(!transTransreqRedis.getStatus().equals(transTransreqDB.getStatus())){
                    //TODO:根据业务检查补单状态
                    //补单
                    transReqService.addFlow(transTransreqRedis);
                }
            }
        }
        return transTransReqs.get(0);
    }



    /**
     * 退票补单查询
     *
     * @return RwRecode
     */
    public RwRecode QueryRrfund(RefundRequest refundRequest) throws BusinessException {

        logger.info("search:---------------退票补单查询");

        //查询
        RwRecodeExample example = new RwRecodeExample();
        RwRecodeExample.Criteria criteria = example.createCriteria();
        criteria.andPlat_noEqualTo(refundRequest.getMer_no());
        criteria.andOrder_noEqualTo(refundRequest.getQuery_order_no());
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<RwRecode> rwRecodes = rwRecodeMapper.selectByExample(example);
        if (rwRecodes.size() > 1) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "数据库有重复退票补单数据");
            throw new BusinessException(baseResponse);
        }

        if (null == rwRecodes || rwRecodes.size() == 0) {
            return null;
        }
        return rwRecodes.get(0);
    }


	private PlatCardinfo queryPlatCardinfo(String mall_no, String plat_no, String card_type) {
		PlatCardinfoExample platCardinfoExample = new PlatCardinfoExample();
		PlatCardinfoExample.Criteria criteria = platCardinfoExample.createCriteria();
		criteria.andEnabledEqualTo(Constants.ENABLED);
		criteria.andMall_noEqualTo(mall_no);
		criteria.andPlat_noEqualTo(plat_no);
		criteria.andCard_typeEqualTo(card_type);

		List<PlatCardinfo> platCardinfo = platCardinfoMapper.selectByExample(platCardinfoExample);
		if (platCardinfo.size() > 1) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
			baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "--数据库有重复的账户");
			throw new BusinessException(baseResponse);
		} else if (platCardinfo.size() <= 0) {
			return null;
		}
		return platCardinfo.get(0);
	}
    /**
     * 平台账户余额查询
     *
     * @param companyAccountBalanceData
     */
    public QueryPlatBalanceRes QueryCompanyAccountBalance(CompanyAccountBalanceData companyAccountBalanceData) throws BusinessException {

        logger.info("search:---------------平台账户余额查询");

        //获取卡号
        PlatCardinfo platCardinfo = queryPlatCardinfo(companyAccountBalanceData.getMall_no(),
                companyAccountBalanceData.getMer_no(), companyAccountBalanceData.getCard_type());
        //
        if (null == platCardinfo) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "存管系统没有该卡信息");
            throw new BusinessException(baseResponse);
        }
        if (null == platCardinfo.getCard_no()) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "存管系统有该卡信息，但是卡号为空");
            throw new BusinessException(baseResponse);
        }
        //TODO 杨磊
        /*
        String card_no = platCardinfo.getCard_no();
        BaseBankPayRequest<String> baseBankPayRequest = new BaseBankPayRequest<>();
        baseBankPayRequest.setTran_type("queryBalance");
        List list = new ArrayList();
        list.add(card_no);
        baseBankPayRequest.setThird_batch_no(SeqUtil.getSeqNum());//流水号
        baseBankPayRequest.setTrandata(list);//trandata里面是一个JSON格式的list字符串  card_no卡号
        baseBankPayRequest.setThird_no(companyAccountBalanceData.getMer_no());//平台号
        //调用第三方服务
        PayResponse<BankPayBalanceReturnData> payResponse = bankPayService.queryBalance(baseBankPayRequest);

        PlatBalanceData platBalanceData = new PlatBalanceData();
        QueryPlatBalanceRes queryPlatBalanceRes = new QueryPlatBalanceRes();

        if (payResponse.getRecode().equals(BusinessMsg.SUCCESS)) {
            BigDecimal realBalance = payResponse.getData().getReal_time_balance();
            platBalanceData.setAmt(realBalance.toString());
        }
        queryPlatBalanceRes.setData(platBalanceData);
        queryPlatBalanceRes.setRecode(payResponse.getRecode());
        queryPlatBalanceRes.setRemsg(payResponse.getRemsg());
        queryPlatBalanceRes.setOrder_no(companyAccountBalanceData.getOrder_no());
        queryPlatBalanceRes.setSign(companyAccountBalanceData.getSign());
        return queryPlatBalanceRes;
    }

    public AccountingDetailHxResponse queryAccountingDetail(BaseRequest baseRequest) throws BusinessException {
        logger.info("search:---------------客户账务往来明细查询");
        AccountingDetailHxResponse accountingDetailResponse = new AccountingDetailHxResponse();
        BaseResponse baseResponse = new BaseResponse();
        AccountingDetailHxRequest accountingDetailHxRequest = (AccountingDetailHxRequest) baseRequest;
        BaseBankPayRequest<BankPayRecordData> baseBankPayRequest = new BaseBankPayRequest<BankPayRecordData>();
        String seq = SeqUtil.getSeqNum();

        try {
            String payNoJson = platCacheService.queryCardInfoToCache(accountingDetailHxRequest.getMer_no(), accountingDetailHxRequest.getAccount_type());
            JSONObject payNoJsonObj = JSON.parseObject(payNoJson);

            List<BankPayRecordData> bankPayRecordDataList = new ArrayList<BankPayRecordData>();
            BankPayRecordData bankPayRecordData = new BankPayRecordData();
            bankPayRecordData.setAcct_no(payNoJsonObj.get("card_no").toString());
            bankPayRecordData.setFlag(accountingDetailHxRequest.getDate_flag());
            bankPayRecordData.setFrom_date(accountingDetailHxRequest.getDate_from());
            bankPayRecordData.setTo_date(accountingDetailHxRequest.getDate_to());
            bankPayRecordData.setType(accountingDetailHxRequest.getIo_flag());
            bankPayRecordDataList.add(bankPayRecordData);

            baseBankPayRequest.setTrandata(bankPayRecordDataList);
            baseBankPayRequest.setThird_batch_no(seq);
            baseBankPayRequest.setThird_no(accountingDetailHxRequest.getOrder_no());
            baseBankPayRequest.setTran_type("query");
            logger.info("======准备客户账务往来明细查询数据: " + JSON.toJSON(baseBankPayRequest));
            PayResponse<List<BankPayRecordReturnData>> payResponse = bankPayService.queryRecords(baseBankPayRequest);
            logger.info("======客户账务往来明细查询返回数据: " + JSON.toJSON(payResponse));
            if (payResponse.getData() == null || payResponse.getData().size() == 0) {
                baseResponse.setRecode(BusinessMsg.UNKNOW_REMOTE_STATUS);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_REMOTE_STATUS));
                throw new BusinessException(baseResponse);
            }
            if (payResponse.getRecode().equals(BusinessMsg.SUCCESS)) {
                List<BankPayRecordReturnData> bankPayRecordReturnDatas = payResponse.getData();
                if (bankPayRecordReturnDatas != null && bankPayRecordReturnDatas.size() > 0) {
                    List<AccountingDetailResponseDetail> accountingDetailResponseDetails = new ArrayList<AccountingDetailResponseDetail>();
                    for (BankPayRecordReturnData bankPayRecordReturnData : bankPayRecordReturnDatas) {
                        AccountingDetailResponseDetail accountingDetailResponseDetail = new AccountingDetailResponseDetail();
                        accountingDetailResponseDetail.setOppo_acct(bankPayRecordReturnData.getOppo_acct());
                        accountingDetailResponseDetail.setOppo_branch_id(bankPayRecordReturnData.getOppo_branch_id());
                        accountingDetailResponseDetail.setOppo_name(bankPayRecordReturnData.getOppo_name());
                        accountingDetailResponseDetail.setTran_amt(new BigDecimal(bankPayRecordReturnData.getTran_amt()));
                        accountingDetailResponseDetail.setTran_flag(bankPayRecordReturnData.getTrans_flag());
                        accountingDetailResponseDetail.setSeq_no(seq);
                        accountingDetailResponseDetails.add(accountingDetailResponseDetail);
                    }
                    accountingDetailResponse.setData_detail(accountingDetailResponseDetails);
                }
            }
        } catch (Exception e) {
            logger.error(e);
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                throw new BusinessException(baseResponse);
            } else {
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
        }
        return accountingDetailResponse;
        */
        return null;
    }


    public AccountingDetailHxNoteResponse queryAccountingDetailNote(AccountingDetailHxNoteRequest accountingDetailHxNoteRequest) throws BusinessException {
        logger.info("【客户账务往来明细查询（带附言）】==========");
        AccountingDetailHxNoteResponse accountingDetailHxNoteResponse = new AccountingDetailHxNoteResponse();
        BaseResponse baseResponse = new BaseResponse();
        String seq = SeqUtil.getSeqNum();

        //TODO 杨磊
        /*
        try {

            String payNoJson = platCacheService.queryCardInfoToCache(accountingDetailHxNoteRequest.getMer_no(), accountingDetailHxNoteRequest.getAccount_type());
            JSONObject payNoJsonObj = JSON.parseObject(payNoJson);

            Map<String, Object> params = new HashMap<>();
            params.put("partner_id", accountingDetailHxNoteRequest.getMall_no());
            params.put("io_flag", accountingDetailHxNoteRequest.getIo_flag());
            params.put("date_flag", accountingDetailHxNoteRequest.getDate_flag());
            params.put("date_from", accountingDetailHxNoteRequest.getDate_from());
            params.put("date_to", accountingDetailHxNoteRequest.getDate_to());
            params.put("acct_no", payNoJsonObj.get("card_no").toString());
            params.put("currency_code", "CNY");

            logger.info("======准备客户账务往来明细查询数据: " + JSON.toJSON(params));
            EpayBankFourInOneQueryResponse payResponse = fundService.epayBankFourInOneQuery(params, accountingDetailHxNoteRequest.getMall_no());
            logger.info("======客户账务往来明细查询返回数据: " + JSON.toJSON(payResponse));
            if (payResponse.getData() == null || payResponse.getData().size() == 0) {
                baseResponse.setRecode(BusinessMsg.UNKNOW_REMOTE_STATUS);
                baseResponse.setRemsg("查询失败：" + payResponse.getError_info());
                throw new BusinessException(baseResponse);
            }
            List<EpayBankFourInOneQueryDetail> epayBankFourInOneQueryDetailList = payResponse.getData().get(0).getAccount_details();
            accountingDetailHxNoteResponse.setData_detail(epayBankFourInOneQueryDetailList);
        } catch (Exception e) {
            logger.error(e);
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                throw new BusinessException(baseResponse);
            } else {
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
        }
        */
        return accountingDetailHxNoteResponse;
    }

    /* TODO 杨磊
    public RealEaccountBalanceResponseDetail queryRealEaccountBalance(RealEaccountBalance realEaccountBalance) throws BusinessException {

        RealEaccountBalanceResponseDetail realEaccountBalanceResponseDetail = new RealEaccountBalanceResponseDetail();

        try {

            //检查用户信息是否存在
            logger.info("检查用户信息是否存在");
            EaccUserinfo eaccUserInfo = userAccountService.queryEaccUserInfo(realEaccountBalance.getMall_no(), realEaccountBalance.getPlatcust());
            if (eaccUserInfo == null) {
                logger.info("用户账号不存在" + "用户账户号：" + realEaccountBalance.getPlatcust());
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",用户信息不存在");
                throw new BusinessException(baseResponse);
            }
            logger.info("用户信息存在");

            Map<String, Object> map = new HashMap<>();
            map.put("partner_id", realEaccountBalance.getMall_no());
            map.put("partner_serial_no", SeqUtil.getSeqNum());
            map.put("bank_acct_no", eaccUserInfo.getEaccount());
            map.put("cert_sign", "sunyard");

            logger.info("【向E支付发送真实电子账户余额查询请求】从redis中获取云融惠付接口URL");
            logger.info("【向E支付发送真实电子账户余额查询请求】===获取第三方服务ip====");
            String Base = sysParameterService.querySysParameter(realEaccountBalance.getMall_no(), "epay_server");
            logger.info("【向E支付发送真实电子账户余额查询请求】===获取第三方服务请求===");
            String URL = "eis/yunpay/cashier_balance_query";

            BaseHttpResponse baseHttpResponse = fundService.doPost(map, Base + "/" + URL, null);
            YunPayBaseErrorResponse errorResponse = JSONObject.parseObject(baseHttpResponse.getEntityString(), YunPayBaseErrorResponse.class);
            if (errorResponse.getError_code() != null) {
                logger.info("【向E支付发送提现请求】返回异常" + errorResponse.toString("YunPayBaseErrorResponse"));
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(errorResponse.getError_code());
                baseResponse.setRemsg("E支付返回：" + errorResponse.getError_info());
                throw new BusinessException(baseResponse);
            }

            CashierBalanceQueryReturn cashierBalanceQueryReturn = JSONObject.parseObject(baseHttpResponse.getEntityString(), CashierBalanceQueryReturn.class);
            if (cashierBalanceQueryReturn.getData() == null || cashierBalanceQueryReturn.getData().size() == 0) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(cashierBalanceQueryReturn.getError_code());
                baseResponse.setRemsg("E支付返回：" + cashierBalanceQueryReturn.getError_info());
                throw new BusinessException(baseResponse);
            }

            logger.info(cashierBalanceQueryReturn.getData().get(0).toString());

            realEaccountBalanceResponseDetail.setClient_name(cashierBalanceQueryReturn.getData().get(0).getClient_name());
            realEaccountBalanceResponseDetail.setEaccount(eaccUserInfo.getEaccount());
            realEaccountBalanceResponseDetail.setOpen_bank(cashierBalanceQueryReturn.getData().get(0).getOpen_bank());

            List<SubAcctBulkPackage> subAcctBulkPackage = cashierBalanceQueryReturn.getData().get(0).getSubAcctBulkPackages();
            realEaccountBalanceResponseDetail.setSubAcctBulkPackages(subAcctBulkPackage);


        } catch (Exception e) {

            logger.error("真实电子账户", e);

            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.FAIL);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL));
            throw new BusinessException(baseResponse);

        }
        return realEaccountBalanceResponseDetail;
    }
    */
}
