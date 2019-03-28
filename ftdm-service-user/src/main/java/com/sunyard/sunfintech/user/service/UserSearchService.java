package com.sunyard.sunfintech.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.*;
import com.sunyard.sunfintech.custdao.mapper.CustRwWithdrawMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.RwRechargeMapper;
import com.sunyard.sunfintech.dao.mapper.RwRecodeMapper;
import com.sunyard.sunfintech.dao.mapper.RwWithdrawMapper;
import com.sunyard.sunfintech.dao.mapper.TransTransreqMapper;
import com.sunyard.sunfintech.pub.model.NotifyData;
import com.sunyard.sunfintech.pub.provider.INotifyService;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.thirdparty.model.WithholdErrorResponse;
import com.sunyard.sunfintech.thirdparty.model.WithholdRequest;
import com.sunyard.sunfintech.thirdparty.model.WithholdResponse;
import com.sunyard.sunfintech.thirdparty.model.WithholdResponseDetail;
import com.sunyard.sunfintech.thirdparty.provider.IAdapterService;
import com.sunyard.sunfintech.user.model.bo.*;
import com.sunyard.sunfintech.user.modelold.bo.AccountDetailTran;
import com.sunyard.sunfintech.user.modelold.bo.AccountingDetailRequest;
import com.sunyard.sunfintech.user.provider.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

/**
 * @author heroy
 * @version 2018/1/26
 */
@Service(interfaceClass = IUserSearchService.class)
public class UserSearchService extends BaseServiceSimple implements IUserSearchService {
    @Autowired
    private TransTransreqMapper transReqMapper;

    @Autowired
    private RwRecodeMapper rwRecodeMapper;

    @Autowired
    private IUserTransService userTransService;

    @Autowired
    private RwRechargeMapper rwRechargeMapper;

    @Autowired
    private IPlatCacheService platCacheService;

    @Autowired
    private ISysParameterService sysParameterService;



   /* @Autowired
    private IAdapterService adapterService;*/

    @Autowired
    private TransTransreqMapper transTransreqMapper;

    @Value("${deploy.environment}")
    private String deployEnvironment;

    @Value("${service.name}")
    private String serviceName;

    @Autowired
    private INotifyService notifyService;

    @Autowired
    private CustRwWithdrawMapper custRwWithdrawMapper;

    @Autowired
    private RwWithdrawMapper rwWithdrawMapper;

    @Autowired
    private IAccountSearchService accountSearchService;

    @Autowired
    private IAdapterService adapterService;

    @Autowired
    private IRechargeOptionService rechargeOptionService;



    /**
     * @author RaoYL
     * @param orderStatusRequest
     * @return TransTransreq
     * @throws BusinessException
     */
    @Override
    public TransTransreq queryOrderStatus(OrderStatusRequest orderStatusRequest) throws BusinessException {
        logger.info(String.format("【订单状态查询】查询订单号:%s",orderStatusRequest.getQuery_order_no()));

        //查询业务流水表
        TransTransreqExample example = new TransTransreqExample();
        TransTransreqExample.Criteria criteria = example.createCriteria();
        criteria.andPlat_noEqualTo(orderStatusRequest.getMer_no());
        criteria.andOrder_noEqualTo(orderStatusRequest.getQuery_order_no());
        List<String> serviceNameList=new ArrayList<>();
        serviceNameList.add("user");
        serviceNameList.add("prod");
        criteria.andService_nameIn(serviceNameList);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<TransTransreq> transTransReqs = transReqMapper.selectByExample(example);
        if(transTransReqs.size() > 1){
            logger.info(String.format("【订单状态查询】数据库有重复的订单|查询订单号:%s",orderStatusRequest.getQuery_order_no()));
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "数据库有重复的订单");
            throw new BusinessException(baseResponse);
        }
        if(transTransReqs.size()==0){
            logger.info(String.format("【订单状态查询】在数据库中查不到订单数据，查redis|查询订单号:%s",orderStatusRequest.getQuery_order_no()));
            String key=Constants.TRANSREQ_ORDER_KEY+orderStatusRequest.getQuery_order_no();
            Object objectKey= CacheUtil.getCache().get(key);
            if(objectKey!=null){
                return JSON.parseObject(String.valueOf(objectKey),TransTransreq.class);
            }else{
                logger.info(String.format("【订单状态查询】订单不存在|查询订单号:%s",orderStatusRequest.getQuery_order_no()));
            }
            return null;
        }
        return transTransReqs.get(0);
    }
    /**
     * 退票补单查询
     *
     * @return RwRecode
     */
    @Override
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

    /**
     * 代扣订单查询
     */
    public WithholdResponseDetail queryWithhold(WithholdRequest withholdRequest)throws BusinessException{
        logger.info("批量代扣查询开始",withholdRequest.getOrder_no());
        WithholdResponseDetail withholdResponseDetail=new WithholdResponseDetail();
        TransTransreqExample transTransreqExample = new TransTransreqExample();
        List<WithholdResponse>withholdResponseList=new ArrayList<>();
        TransTransreqExample.Criteria criteria = transTransreqExample.createCriteria();
        criteria.andOrder_noEqualTo(withholdRequest.getBatch_order_no());
        List<TransTransreq>transTransreqs=transTransreqMapper.selectByExample(transTransreqExample);
        if (transTransreqs.size()==0){
            throw  new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
        }
        try {
            PlatPaycode platPayCode = accountSearchService.queryPlatPayCode(withholdRequest.getMer_no(), withholdRequest.getPay_code());

            String host = sysParameterService.querySysParameter(withholdRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY);
            String url = sysParameterService.querySysParameter(withholdRequest.getMall_no(), URLConfigUtil.BATCH_PAY_QUERY_NEW);

            Map<String,Object>map=new HashMap<>();
            map.put("partner_id",platPayCode.getPayment_plat_no());
            map.put("partner_serial_no", SeqUtil.getSeqNum());
            map.put("partner_trans_date", DateUtils.getyyyyMMddDate());
            map.put("partner_trans_time",DateUtils.getHHmmssTime());
            map.put("original_serial_no",withholdRequest.getBatch_order_no());
            map.put("url",url);
            map.put("host",host);
            String mall_no=withholdRequest.getMall_no();
            logger.info("【 批量代扣】三方请求参数："+ JSON.toJSONString(map));
            Map<String,Object> res=adapterService.batchPayQueryTo(map);
            List<JSONObject> list = (List) res.get("data");
            WithholdResponse withholdResponse=new WithholdResponse();
            for (JSONObject jo:list){
                withholdResponse.setOccur_balance(jo.getBigDecimal("occur_balance"));
                withholdResponse.setDetail_no(jo.getString("detail_no"));
                withholdResponse.setClient_name(jo.getString("client_name"));
                withholdResponse.setStatus(jo.getString("order_status"));
                withholdResponse.setTotal_num(jo.getString("total_num"));
                withholdResponse.setSummary(jo.getString("summary"));
                withholdResponse.setTotal_balance(jo.getString("total_balance"));
                withholdResponse.setCard_no(jo.getString("card_no"));
                String trans_serial=withholdResponse.getDetail_no();
                RwRecharge rwRecharge=new RwRecharge();
                rwRecharge=accountSearchService.queryRwRechargeBySerial(trans_serial,null,null);
                String apply_order_no=rwRecharge.getOrder_no();
                if("7".equals(withholdResponse.getStatus())){
                    withholdResponse.setFail_cause(jo.getString("remsg"));
                    withholdResponse.setStatus("2");
                    rwRecharge.setStatus("2");
                }else if("3".equals(withholdResponse.getStatus())){
                    withholdResponse.setStatus("1");
                    rwRecharge.setStatus("1");
                }
                //调陈瑞的转账
                try {
                    rechargeOptionService.doRecharge(rwRecharge,mall_no,apply_order_no, "批量代扣查询");
                }catch (Exception e){
                    logger.error("【批量代扣查询】==============异常：%s",e);
                    if (e instanceof BusinessException){
                        throw e;
                    }else {
                        logger.error("【批量代扣查询】==============转账异常：%s",e);
                        throw new BusinessException(BusinessMsg.UNKNOW_ERROE,BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE)+ "转账异常");
                    }

                }
                withholdResponseList.add(withholdResponse);
            }
            withholdResponseDetail.setRecode(BusinessMsg.SUCCESS);
            withholdResponseDetail.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        }catch (Exception e){
            logger.error("未知异常", e);
            throw new BusinessException(BusinessMsg.UNKNOW_ERROE,BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE));
        }
        if(withholdRequest.getNotify_url()!=null) {
            NotifyData notifyData = new NotifyData();
            notifyData.setNotifyUrl(withholdRequest.getNotify_url());
            notifyData.setMall_no(withholdRequest.getMall_no());
            withholdResponseDetail.setData_detail(withholdResponseList);
            withholdResponseDetail.setOrder_no(withholdRequest.getOrder_no());
            withholdResponseDetail.setRecode(BusinessMsg.SUCCESS);
            withholdResponseDetail.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            notifyData.setNotifyContent(JSON.toJSONString(withholdResponseDetail));
            notifyService.addNotify(notifyData);
            logger.info("【代扣查询】-->丢入MQ成功-->order_no:" + withholdRequest.getBatch_order_no());
        }
        return  withholdResponseDetail;
    }

    /**
     * 充值订单状态查询
     * @author RaoYL
     * @param chargeStatus
     * @return RwRecharge
     * @throws BusinessException
     */
    public RwRecharge queryChargeStatus(ChargeStatus chargeStatus) throws BusinessException {

        logger.info("【充值订单状态查询】请求第三方支付参数：" + JSON.toJSONString(chargeStatus, GlobalConfig.serializerFeature));
        //=================添加充值处理标志==========
        RwRecharge rwRecharge;
        //查询
        RwRechargeExample example = new RwRechargeExample();
        RwRechargeExample.Criteria criteria = example.createCriteria();
        criteria.andPlat_noEqualTo(chargeStatus.getMer_no());
        criteria.andOrder_noEqualTo(chargeStatus.getOriginal_serial_no());
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<RwRecharge> rwRecharges = rwRechargeMapper.selectByExample(example);
        if (rwRecharges.size() > 1) {
            logger.info("【充值订单状态查询】==========数据库有重复充值订单："+chargeStatus.getOriginal_serial_no());
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "数据库有重复充值订单");
            throw new BusinessException(baseResponse);
        }

        if (null == rwRecharges || rwRecharges.size() == 0) {
            return null;
        }
        rwRecharge = rwRecharges.get(0);

        if(rwRecharge.getTrans_amt().compareTo(chargeStatus.getOccur_balance())!=0){
            logger.info("【充值订单状态查询】==========金额错误,原充值订单号："+chargeStatus.getOriginal_serial_no());
            throw new BusinessException(BusinessMsg.MONEY_ERROR,BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR)+":查询金额不符");
        }

        try {
            //如果充值订单状态不是终态。则调用云融惠付查询订单状态。
            if (!OrderStatus.SUCCESS.getCode().equals(rwRecharge.getStatus()) && !OrderStatus.FAIL.getCode().equals(rwRecharge.getStatus())) {
                logger.info("【充值订单状态查询】==========开始调用queryEpayStatus查询充值订单，请求参数：" + rwRecharge.getTrans_serial() + "充值订单金额：" + chargeStatus.getOccur_balance());
                rwRecharge=userTransService.queryEpayStatus(rwRecharge);
            }
            logger.info("【充值订单状态查询】==========支付返回最终订单状态：" + rwRecharge.getStatus());
            return rwRecharge;
        } catch (Exception e) {
            logger.error("充值订单状态查询异常：" + e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg("充值订单状态查询异常");
            }
            throw new BusinessException(baseResponse);
        }
    }

    @Override
    public CompanyAccountBalanceResponseDetail queryCompanyAccountBalance(CompanyAccountBalanceRequest companyAccountBalanceRequest) throws BusinessException {
        logger.info("【平台对公账户余额查询】order_no:"+companyAccountBalanceRequest.getOrder_no());
        BaseResponse baseResponse = new BaseResponse();
        Map<String,Object> realParams = new HashMap<String,Object>();
        CompanyAccountBalanceResponseDetail dataDetail = new CompanyAccountBalanceResponseDetail();
        try{
            //从redis缓存中查询平台对公卡信息
            String platCardInfo = platCacheService.queryCardInfoToCache(companyAccountBalanceRequest.getMer_no(),companyAccountBalanceRequest.getCard_type());
            JSONObject platCardInfoJson = JSONObject.parseObject(platCardInfo);
            logger.info("从redis中查询出来的平台对公卡信息："+platCardInfoJson.toString());
            //请求支付查询平台对公卡信息
            String host = sysParameterService.querySysParameter(companyAccountBalanceRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY);
            String url = sysParameterService.querySysParameter(companyAccountBalanceRequest.getMall_no(), URLConfigUtil.EPAY_QUERY_BALANCE);
            if("BOB".equals(deployEnvironment)) {
                realParams.put("partner_id", "90000009");//商户ID
                realParams.put("partner_serial_no", SeqUtil.getSeqNum());//流水号
                realParams.put("client_property", "1");
                realParams.put("bank_acct_no", platCardInfoJson.get("card_no").toString());
            }
            if("CCB".equals(deployEnvironment)){
                JSONArray trandata=new JSONArray();
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("card_no",platCardInfoJson.get("card_no").toString());
                trandata.add(map);
                logger.info("平台实体账户卡号："+platCardInfoJson.get("card_no").toString()+"map:"+map+"trandata:"+trandata);
                realParams.put("partner_id","cg001");//固定值
                realParams.put("channelId","01050000");//固定值
//                realParams.put("third_no",companyAccountBalanceRequest.getMer_no());//平台号
                realParams.put("tran_type","queryBalance");
                realParams.put("third_batch_no",SeqUtil.getSeqNum());//流水号
                realParams.put("trandata",trandata);//trandata里面是一个JSON格式的list字符串  card_no卡号
            }
            realParams.put("host",host);
            realParams.put("url",url);
            logger.info("【平台对公账户查询】order_no:"+companyAccountBalanceRequest.getOrder_no()+"开始请求支付，参数："+JSON.toJSON(realParams));
            Map<String,Object> resultMap = adapterService.queryAccountOfCompany(realParams);
            if("BOB".equals(deployEnvironment)) {
                if(null == resultMap || null == resultMap.get("order_status") || ".".equals(resultMap.get("order_status"))){
                    logger.info("【平台对公账户查询】order_no:"+companyAccountBalanceRequest.getOrder_no()+"第三方支付返回结果为空");
                    baseResponse.setRecode(BusinessMsg.UNKNOW_REMOTE_STATUS);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_REMOTE_STATUS));
                    throw new BusinessException(baseResponse);
                }
            }
            if("CCB".equals(deployEnvironment)){
                if(null == resultMap ){
                    logger.info("【平台对公账户查询】order_no:"+companyAccountBalanceRequest.getOrder_no()+"第三方支付返回结果为空");
                    baseResponse.setRecode(BusinessMsg.UNKNOW_REMOTE_STATUS);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_REMOTE_STATUS));
                    throw new BusinessException(baseResponse);
                }
            }
            if(!OrderStatus.SUCCESS.getCode().equals(resultMap.get("order_status"))){
                logger.info("【平台对公账户查询】order_no:"+companyAccountBalanceRequest.getOrder_no()+"第三方支付返回错误信息");
                baseResponse.setRecode(resultMap.get("recode").toString());
                baseResponse.setRemsg(resultMap.get("remsg").toString());
                throw new BusinessException(baseResponse);
            }
            logger.info("平台对公账户查询支付返回结果："+resultMap);
            if("CCB".equals(deployEnvironment)){
                dataDetail.setOpen_bank(resultMap.get("open_bank").toString());
                dataDetail.setAcct_name_ch(resultMap.get("acct_name_ch").toString());
                dataDetail.setReal_time_balance(resultMap.get("real_time_balance").toString());
                dataDetail.setToday_amt(resultMap.get("today_amt").toString());
                dataDetail.setAcct_status(resultMap.get("acct_status").toString());
            }
            if("BOB".equals(deployEnvironment)){
                dataDetail.setOpen_bank(resultMap.get("open_bank").toString());
                dataDetail.setAcct_name_ch(resultMap.get("client_name").toString());
                dataDetail.setReal_time_balance(resultMap.get("real_time_balance").toString());
                dataDetail.setToday_amt(resultMap.get("today_balance").toString());
                dataDetail.setYesterday_amt(resultMap.get("yesterday_balance").toString());
            }
            logger.info("【平台对公账户查询】order_no:"+companyAccountBalanceRequest.getOrder_no()+"查询结束");
        }catch (Exception ex){
            logger.info("【平台对公账户查询】order_no:"+companyAccountBalanceRequest.getOrder_no()+"查询开始异常");
            logger.error(ex);
            if(ex instanceof BusinessException){
                baseResponse = ((BusinessException) ex).getBaseResponse();
                throw new BusinessException(baseResponse);
            }else {
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION,BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
        }
        return dataDetail;
    }
    //
    @Override
    public CompanyAccountDetailData queryAccountByPublic(CompanyAccBalanceData companyAccBalanceData)throws BusinessException{
        logger.info("***************【"+companyAccBalanceData.getOrder_no()+"】平台对公账户查询开始...*****************");
        BaseResponse baseResponse = new BaseResponse();
        Map<String,Object> eMap = new HashMap<String,Object>();
        CompanyAccountDetailData companyAccountDetailData = new CompanyAccountDetailData();
        try{
            String payNoJson = platCacheService.queryCardInfoToCache(companyAccBalanceData.getMer_no(), companyAccBalanceData.getCard_type());
            JSONObject payNoJsonObj = JSON.parseObject(payNoJson);
            String host = sysParameterService.querySysParameter(companyAccBalanceData.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY);
            String url = sysParameterService.querySysParameter(companyAccBalanceData.getMall_no(), URLConfigUtil.EPAY_QUERY_BALANCE);
            eMap.put("partner_id","90000009");
            eMap.put("partner_serial_no", SeqUtil.getSeqNum());
            eMap.put("client_property","1");
            eMap.put("bank_acct_no",payNoJsonObj.get("card_no").toString());
            eMap.put("host",host);
            eMap.put("url",url);
            logger.info("***【"+companyAccBalanceData.getOrder_no()+"】平台对公账户查询开始***: "+JSON.toJSON(eMap));
            Map<String,Object> responseMap = adapterService.queryAccountOfCompany(eMap);
            logger.info("***【"+companyAccBalanceData.getOrder_no()+"】平台对公账户查询开始***: "+JSON.toJSON(responseMap));
            if(null == responseMap || null == responseMap.get("order_status") || ".".equals(responseMap.get("order_status"))){
                logger.info("【平台对公账户查询】order_no:"+companyAccBalanceData.getOrder_no()+"第三方支付返回结果为空");
                baseResponse.setRecode(BusinessMsg.UNKNOW_REMOTE_STATUS);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_REMOTE_STATUS));
                throw new BusinessException(baseResponse);
            }
            if(!OrderStatus.SUCCESS.getCode().equals(responseMap.get("order_status"))){
                logger.info("【平台对公账户查询】order_no:"+companyAccBalanceData.getOrder_no()+"第三方支付返回错误信息");
                baseResponse.setRecode(responseMap.get("recode").toString());
                baseResponse.setRemsg(responseMap.get("remsg").toString());
                throw new BusinessException(baseResponse);
            }
            //CashierBalanceQueryReturnDetail cashierBalanceQueryReturnDetail = cashierBalanceQueryReturn.getData().get(0);
            companyAccountDetailData.setOpen_bank(responseMap.get("open_bank").toString());
            companyAccountDetailData.setAcct_name_ch(responseMap.get("client_name").toString());
            companyAccountDetailData.setReal_time_balance(responseMap.get("real_time_balance").toString());
            companyAccountDetailData.setToday_amt(responseMap.get("today_balance").toString());
            companyAccountDetailData.setYesterday_amt(responseMap.get("yesterday_balance").toString());
            logger.info("***************【"+companyAccBalanceData.getOrder_no()+"】平台对公账户查询结束*****************");
        }catch (Exception e){
            logger.error("***************【"+companyAccBalanceData.getOrder_no()+"】平台对公账户查询开始异常*****************");
            logger.error(e);
            if(e instanceof BusinessException){
                baseResponse = ((BusinessException) e).getBaseResponse();
                throw new BusinessException(baseResponse);
            }else{
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION,BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
        }
        return companyAccountDetailData;
    }
    @Override
    public AccountingDetailHxNoteResponse queryAccountingDetailNote(AccountingDetailHxNoteRequest accountingDetailHxNoteRequest) throws BusinessException {
        logger.info("【平台真实账务往来明细查询（带附言）】==========开始查询");
        AccountingDetailHxNoteResponse accountingDetailHxNoteResponse = new AccountingDetailHxNoteResponse();
        BaseResponse baseResponse = new BaseResponse();
        String seq = SeqUtil.getSeqNum();

        try {
            String payNoJson = platCacheService.queryCardInfoToCache(accountingDetailHxNoteRequest.getMer_no(), accountingDetailHxNoteRequest.getAccount_type());
            JSONObject payNoJsonObj = JSON.parseObject(payNoJson);
            String host = sysParameterService.querySysParameter(accountingDetailHxNoteRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY);
            String url = sysParameterService.querySysParameter(accountingDetailHxNoteRequest.getMall_no(), URLConfigUtil.EPAY_BANK_FOUR_IN_ONE_QUERY);
            Map<String, Object> params = new HashMap<>();
            params.put("partner_id", accountingDetailHxNoteRequest.getMall_no());
            params.put("io_flag", accountingDetailHxNoteRequest.getIo_flag());
            params.put("date_flag", accountingDetailHxNoteRequest.getDate_flag());
            params.put("date_from", accountingDetailHxNoteRequest.getDate_from());
            params.put("date_to", accountingDetailHxNoteRequest.getDate_to());
            params.put("acct_no", payNoJsonObj.get("card_no").toString());
            params.put("currency_code", "CNY");
            params.put("host",host);
            params.put("url",url);

            logger.info("【平台真实账务往来明细查询(带附言)】order_no: "+accountingDetailHxNoteRequest.getOrder_no()+"开始请求支付，参数：" + JSON.toJSON(params));
            Map<String,Object> resultMap = adapterService.epayBankFourInOneQuery(params);
            if(null == resultMap || null == resultMap.get("order_status") || ".".equals(resultMap.get("order_status"))){
                logger.info("【平台真实账务往来明细查询(带附言)】order_no:"+accountingDetailHxNoteRequest.getOrder_no()+"第三方支付返回结果为空");
                baseResponse.setRecode(BusinessMsg.UNKNOW_REMOTE_STATUS);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_REMOTE_STATUS));
                throw new BusinessException(baseResponse);
            }
            if(!OrderStatus.SUCCESS.getCode().equals(resultMap.get("order_status"))){
                logger.info("【平台真实账务往来明细查询(带附言)】order_no:"+accountingDetailHxNoteRequest.getOrder_no()+"第三方支付返回错误信息");
                baseResponse.setRecode(resultMap.get("recode").toString());
                baseResponse.setRemsg(resultMap.get("remsg").toString());
                throw new BusinessException(baseResponse);
            }
            List<EpayBankFourInOneQueryDetail> epayBankFourInOneQueryDetailList = (List)resultMap.get("account_details");
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
        return accountingDetailHxNoteResponse;
    }

    @Override
    public List<AccountDetailTran> queryAccountDetailOld(AccountingDetailRequest accountingDetailBo) throws BusinessException {
        logger.info("【平台真实账务往来明细查询】order_no:"+accountingDetailBo.getOrder_no());
        BaseResponse baseResponse = new BaseResponse();
        List<AccountDetailTran> array_tran_list = new ArrayList<AccountDetailTran>();
        Map<String,Object> eMap = new HashMap<String,Object>();

        try{
            String payNoJson = platCacheService.queryCardInfoToCache(accountingDetailBo.getMer_no(), accountingDetailBo.getAccount_type());
            JSONObject payNoJsonObj = JSON.parseObject(payNoJson);

            String host = sysParameterService.querySysParameter(accountingDetailBo.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY);
            String url = sysParameterService.querySysParameter(accountingDetailBo.getMall_no(), URLConfigUtil.EPAY_QUERY_ACCDETAIL);
            eMap.put("partner_id","90000009");
            eMap.put("partner_serial_no", SeqUtil.getSeqNum());//合作商流水号为空，入账结果不确定
            eMap.put("date_from",accountingDetailBo.getDate_from());
            eMap.put("date_to",accountingDetailBo.getDate_to());
            eMap.put("acct_no",payNoJsonObj.get("card_no").toString());
            eMap.put("flag_dc",accountingDetailBo.getIo_flag());//借贷标志【D：借 C:贷 A:全部】
            eMap.put("host",host);
            eMap.put("url",url);
            logger.info(String.format("【平台真实账务往来明细查询】准备客户账务往来明细数据查询|order_no:%s|params:%s",accountingDetailBo.getOrder_no(),JSON.toJSON(eMap)));
            Map<String,Object> resultMap = adapterService.queryListOfCompanyTransfer(eMap);
            logger.info(String.format("【平台真实账务往来明细查询】准备客户账务往来明细数据查询|order_no:%s|resultMap:%s",accountingDetailBo.getOrder_no(),JSON.toJSON(resultMap)));

            if(!OrderStatus.SUCCESS.getCode().equals(resultMap.get("order_status"))){
                logger.info("【平台真实账务往来明细查询】order_no:"+accountingDetailBo.getOrder_no()+"第三方支付返回错误信息");
                baseResponse.setRecode(resultMap.get("recode").toString());
                baseResponse.setRemsg(resultMap.get("remsg").toString());
                throw new BusinessException(baseResponse);
            }
            Object listObj=resultMap.get("array_tran_list");
            if(null == listObj){
                logger.info("【平台真实账务往来明细查询】第三方支付返回结果为空|order_no:"+accountingDetailBo.getOrder_no());
                baseResponse.setRecode(BusinessMsg.UNKNOW_REMOTE_STATUS);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_REMOTE_STATUS));
                throw new BusinessException(baseResponse);
            }
            array_tran_list= (List<AccountDetailTran>) listObj;
            logger.info("***************【"+accountingDetailBo.getOrder_no()+"】平台真实账务往来明细查询结束*****************");
        }catch (Exception e){
            logger.error("***************【"+accountingDetailBo.getOrder_no()+"】平台真实账务往来明细查询异常*****************");
            logger.error(e);
            if(e instanceof BusinessException){
                baseResponse = ((BusinessException) e).getBaseResponse();
                throw new BusinessException(baseResponse);
            }else{
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
        }
        return array_tran_list;
    }

    /**
     * @version 20180307
     * @author RaoYL
     * 客户账务往来明细查询--调公司支付(晋商银行调用)
     */
    @Override
    public List<PlatAccountDetailResponseList> queryAccountDetail(PlatAccountDetailRequest accountingDetailRequest) throws BusinessException {
        logger.info("【平台真实账务往来明细查询】order_no:"+accountingDetailRequest.getOrder_no());
        BaseResponse baseResponse = new BaseResponse();
        List<PlatAccountDetailResponseList> array_tran_list = new ArrayList<PlatAccountDetailResponseList>();
        Map<String,Object> realParams = new HashMap<String,Object>();

        try{
            String platCardJson = platCacheService.queryCardInfoToCache(accountingDetailRequest.getMer_no(), accountingDetailRequest.getAccount_type());
            JSONObject platCardJsonObj = JSON.parseObject(platCardJson);
            logger.info("从redis缓存中查出的平台卡信息："+platCardJsonObj);
            String host = sysParameterService.querySysParameter(accountingDetailRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY);
            String url = sysParameterService.querySysParameter(accountingDetailRequest.getMall_no(), URLConfigUtil.EPAY_QUERY_ACCDETAIL);
            realParams.put("partner_id","cg001");//固定值
            realParams.put("channelId","01050000");//固定值
            realParams.put("third_batch_no", SeqUtil.getSeqNum());//合作商流水号为空，入账结果不确定
            realParams.put("tran_type","query");
            realParams.put("host",host);
            realParams.put("url",url);
            JSONArray trandata = new JSONArray();
            Map<String,Object> eMap = new HashMap<String, Object>();
            eMap.put("type",accountingDetailRequest.getIo_flag());//借贷标志【D：借 C:贷 A:全部】
            eMap.put("acct_no",platCardJsonObj.get("card_no").toString());//卡号
            eMap.put("flag",accountingDetailRequest.getDate_flag());//日期标志
            eMap.put("from_date",accountingDetailRequest.getDate_from());
            eMap.put("to_date",accountingDetailRequest.getDate_to());
            trandata.add(eMap);
            logger.info("CCB平台真实账务往来查询【trandata】"+trandata);
            realParams.put("trandata",trandata);
            logger.info(String.format("【平台真实账务往来明细查询】准备客户账务往来明细数据查询|order_no:%s|params:%s",accountingDetailRequest.getOrder_no(),JSON.toJSON(realParams)));
            Map<String,Object> resultMap = adapterService.queryListOfCompanyTransfer(realParams);
            logger.info(String.format("【平台真实账务往来明细查询】准备客户账务往来明细数据查询|order_no:%s|resultMap:%s",accountingDetailRequest.getOrder_no(),JSON.toJSON(resultMap)));

            if(!OrderStatus.SUCCESS.getCode().equals(resultMap.get("order_status"))){
                logger.info("【平台真实账务往来明细查询】order_no:"+accountingDetailRequest.getOrder_no()+"第三方支付返回错误信息");
                baseResponse.setRecode(resultMap.get("recode").toString());
                baseResponse.setRemsg(resultMap.get("remsg").toString());
                throw new BusinessException(baseResponse);
            }
            Object listObj=resultMap.get("array_tran_list");
            if(null == listObj){
                logger.info("【平台真实账务往来明细查询】第三方支付返回结果为空|order_no:"+accountingDetailRequest.getOrder_no());
                baseResponse.setRecode(BusinessMsg.UNKNOW_REMOTE_STATUS);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_REMOTE_STATUS));
                throw new BusinessException(baseResponse);
            }
            array_tran_list= (List<PlatAccountDetailResponseList>) listObj;
            logger.info("***************【"+accountingDetailRequest.getOrder_no()+"】平台真实账务往来明细查询结束*****************");
        }catch (Exception e){
            logger.error("***************【"+accountingDetailRequest.getOrder_no()+"】平台真实账务往来明细查询异常*****************");
            logger.error(e);
            if(e instanceof BusinessException){
                baseResponse = ((BusinessException) e).getBaseResponse();
                throw new BusinessException(baseResponse);
            }else{
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
        }
        return array_tran_list;
    }
}
