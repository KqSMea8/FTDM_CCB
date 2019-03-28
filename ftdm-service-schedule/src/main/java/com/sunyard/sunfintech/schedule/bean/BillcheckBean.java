package com.sunyard.sunfintech.schedule.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunyard.sunfintech.billcheck.provider.IBillCheckService;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.dic.CardType;
import com.sunyard.sunfintech.core.dic.ClearStatus;
import com.sunyard.sunfintech.core.dic.LiquidationStatus;
import com.sunyard.sunfintech.core.dic.PlatAccType;
import com.sunyard.sunfintech.core.threads.SingleThreadPool;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.prod.provider.IProdFinancService;
import com.sunyard.sunfintech.pub.provider.IRedisConnectionService;
import com.sunyard.sunfintech.pub.provider.ISendMsgService;
import com.sunyard.sunfintech.schedule.service.ScheduleService;
import com.sunyard.sunfintech.user.model.bo.CompanyAccBalanceData;
import com.sunyard.sunfintech.user.model.bo.CompanyAccountDetailData;
import com.sunyard.sunfintech.user.provider.IAccountSearchService;
import com.sunyard.sunfintech.user.provider.IPlatCacheService;
import com.sunyard.sunfintech.user.provider.IPlatTransService;
import com.sunyard.sunfintech.user.provider.IUserSearchService;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * 凌晨跑对账服务
 *
 * @author KouKi
 */
@Component
public class BillcheckBean implements IScheduleTaskDealSingle<Object> {

    protected final Logger logger = LogManager.getLogger(this.getClass());
    private final String BILLCHECK_KEY = "billcheckBean:clearCount:";

    @Value("${deploy.environment}")
    private String deployEnvironment;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private ISendMsgService sendMsgService;
    @Autowired
    private IPlatCacheService platCacheService;
    @Autowired
    private IPlatTransService platTransService;
    @Autowired
    private IBillCheckService billCheckService;
    @Autowired
    private IProdFinancService prodFinancService;
    @Autowired
    private IRedisConnectionService redisConnectionService;
    @Autowired
    private IUserSearchService userSearchService;
    @Autowired
    private IAccountSearchService accountSearchService;

    @Override
    public boolean execute(Object task, String ownSign) throws Exception {
        return true;
    }

    @Override
    public List<Object> selectTasks(String taskParameter, String ownSign, int taskItemNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum, int pageNum) throws Exception {
        Date date = DateUtils.beforeDate(new Date(), 1);
        String strDate = DateUtils.formatDateToStr(date, DateUtils.DEF_FORMAT_NOTIME);
        Object hasCleared = redisConnectionService.get(BILLCHECK_KEY + strDate);
        if(hasCleared != null){
            logger.info("【billcheckBean】该数据已经跑过一次");
            return null;
        }
        redisConnectionService.set(BILLCHECK_KEY + strDate, "1");
        redisConnectionService.expire(BILLCHECK_KEY + strDate, 24 * 60 * 60);
        logger.info("【billcheckBean】生成融资人待还余额数据");
        List<PlatPlatinfo> platPlatinfoList = platCacheService.queryAllPlatInfo();
        //生成融资人待还余额数据
        SingleThreadPool.getThreadPool().execute(() ->
                prodFinancService.makeProdFinancBalance(strDate, platPlatinfoList));
        logger.info("【billcheckBean】充值提现对账");
//        //充值提现对账
//        SingleThreadPool.getThreadPool().execute(() ->
//                checkCTList(date));
        logger.info("【billcheckBean】日切统计");
        //日切统计
        SingleThreadPool.getThreadPool().execute(() ->
                makeClearResult(date, platPlatinfoList));
        logger.info("【billcheckBean】生成平台充值提现出入金流水");
        //生成平台充值提现出入金流水
        SingleThreadPool.getThreadPool().execute(() ->
                platPlatinfoList.forEach(platPlatinfo ->
                        platTransService.makePlatClearCT(strDate, platPlatinfo.getPlat_no())));
        //生成用户余额对账文件
        SingleThreadPool.getThreadPool().execute(() ->
                platPlatinfoList.forEach(platPlatinfo ->
                        billCheckService.makeAccountBalance(platPlatinfo.getPlat_no())));
        //总分对账
        SingleThreadPool.getThreadPool().execute(()->
                platPlatinfoList.forEach(platPlatinfo ->
                        makeAllCheck(platPlatinfo.getPlat_no())));
        //生成资金流水对账文件
        if("CCB".equals(deployEnvironment)){
            SingleThreadPool.getThreadPool().execute(() ->
                    platPlatinfoList.forEach(platPlatinfo ->
                            billCheckService.makeAccountTrans(platPlatinfo.getPlat_no(),strDate)));
        }
        return null;
    }

    /**
     * 总分对账
     * @param plat_no
     */
    private void makeAllCheck(String plat_no) {
        //总账余额
        String today_amt = "";
        //分账余额
        String subAmt = "";
        String msg = "平";
        try {
            String mall_no = accountSearchService.queryMallNoByPlatNo(plat_no);
            //查询实际余额
            CompanyAccBalanceData data = new CompanyAccBalanceData();
            data.setMall_no(mall_no);
            data.setMer_no(plat_no);
            data.setOrder_no(SeqUtil.getSeqNum());
            data.setCard_type(PlatAccType.PLATTRUST.getCode());
            CompanyAccountDetailData response = userSearchService.queryAccountByPublic(data);
            //总账户今日余额
            today_amt = response.getToday_amt();
            //分账户总余额
            subAmt = scheduleService.querySubAmt(plat_no);
            if(new BigDecimal(today_amt).compareTo(new BigDecimal(subAmt)) != 0){
                msg = "不平";
            }
        }catch (Exception e){
            logger.info("银行卡余额查询异常", e);
        }
        scheduleService.updateCheckAll(plat_no, today_amt, subAmt, msg);
    }

    /**
     * 生成clear_result数据
     */
    private void makeClearResult(Date clearDate, List<PlatPlatinfo> platPlatinfoList) {
        try {
            platPlatinfoList.forEach(platPlatinfo -> {
                List<PlatPaycode> platPaycodeList = platCacheService.queryTransactionCode(platPlatinfo.getPlat_no());
                platPaycodeList.forEach(platPaycode -> {
                    ClearResult clearResult = new ClearResult();
                    clearResult.setPlat_no(platPaycode.getPlat_no());
                    clearResult.setClear_date(clearDate);
                    clearResult.setPay_code(platPaycode.getPay_code());
                    clearResult.setClear_status(ClearStatus.UNCHECK.getCode());
                    clearResult.setLiquidation(LiquidationStatus.UNCHECK.getCode());
                    scheduleService.insertClearResult(clearResult);
                });
            });
        } catch (Exception e) {
            logger.info("【生成clear_result】捕获到异常", e);
        }
    }

    /**
     * 充值提现对账
     */
    public void checkCTList(Date clearDate) {
        logger.info("【充值提现对账流水】开始查询流水");
        Boolean isError = false;
        String strDate = DateUtils.format(clearDate, DateUtils.DEF_FORMAT_NOTIME);
        List<String> trans_codes = new ArrayList<>();
        trans_codes.add(TransConsts.QUICK_CONFIRM_CODE);
        trans_codes.add(TransConsts.GATEWAY_RECHARGE_CODE);
        trans_codes.add(TransConsts.COLLECTION_CODE);
        //充值流水
        List<EaccAccountamtlist> rechargeList = scheduleService.queryCTListByAccountDate(clearDate, trans_codes);
        trans_codes.clear();
        trans_codes.add(TransConsts.BATCH_WITHDRAW_CODE);
        trans_codes.add(TransConsts.WITHDRAW_AFFIRM_CODE);
        //提现流水
        List<EaccAccountamtlist> withdrawList = scheduleService.queryCTListByAccountDate(clearDate, trans_codes);
        //充值记录
        List<RwRecharge> recharges = scheduleService.querySuccessRecharge(strDate);
        //提现记录
        List<RwWithdraw> withdraws = scheduleService.querySuccessRwWithdraw(strDate);
        logger.info("【充值提现对账流水】流水查询结束，开始对账");
        logger.info("【充值提现对账流水】充值对账");
        /*
        充值提现对账存在六种情况，
                订单表有、资金表无，
                资金表有、订单表无，
                订单表重复（忽略，有唯一索引），
                充值流水表重复，
                正常,
                资金不等（忽略）
         */
        Map<String,Boolean> rechargeMap = new HashMap<>();
        for (EaccAccountamtlist eaccAccountamtlist : rechargeList) {
            Boolean isExsits = rechargeMap.get(eaccAccountamtlist.getTrans_serial());
           if (isExsits != null) {//已存在，重复记账
                isError = true;
                insertClearCheckError("{内部对账-充值}资金流水重复记账",eaccAccountamtlist.getTrans_serial(),eaccAccountamtlist.getOrder_no()
                    ,strDate,"C");
            } else {
                rechargeMap.put(eaccAccountamtlist.getTrans_serial(), false);
            }
        }
        for (RwRecharge recharge : recharges) {
            Boolean  isExsits = rechargeMap.get(recharge.getTrans_serial());
            if(isExsits = null){
                isError = true;
                List<EaccAccountamtlist> eaccAccountamtlists = scheduleService.queryAmtListByTransSerial(recharge.getTrans_serial());
                if(eaccAccountamtlists.size() == 0){
                    logger.info("【内部对账】没有资金流水");
                    insertClearCheckError("{内部对账-充值}无资金流水",recharge.getTrans_serial(),recharge.getOrder_no()
                            ,strDate,"C");
                }else {
                    logger.info("【内部对账】有资金流水，修改account_date");
                    eaccAccountamtlists.forEach(eaccAccountamtlist -> {
                        EaccAccountamtlist amtlist = new EaccAccountamtlist();
                        amtlist.setId(eaccAccountamtlist.getId());
                        amtlist.setAccount_date(DateUtils.parseDate(recharge.getPayment_date(), DateUtils.DEF_FORMAT_NOTIME));
                        scheduleService.updateAccountDate(eaccAccountamtlist);
                    });
                }
            }else if(!isExsits){
                isError = true;
                insertClearCheckError("{内部对账-充值}订单重复",recharge.getTrans_serial(),recharge.getOrder_no()
                        ,strDate,"C");
            }else {
                rechargeMap.put(recharge.getTrans_serial(),true);//设置为已对账
            }
        }
        for(Map.Entry<String,Boolean> entry:rechargeMap.entrySet()){
            if(!entry.getValue()){
                isError = true;
                insertClearCheckError("{内部对账-充值}资金表有订单，订单表无记录",entry.getKey(),""
                        ,strDate,"C");
            }
        }
        logger.info("【充值提现对账流水】提现对账");
        Map<String,Boolean> withdrawsMap = new HashMap<>();
        for (EaccAccountamtlist eaccAccountamtlist : withdrawList) {
            Boolean isExsits = withdrawsMap.get(eaccAccountamtlist.getTrans_serial());
            if (isExsits != null) {//已存在，重复记账
                isError = true;
                insertClearCheckError("{内部对账-提现}资金流水重复记账",eaccAccountamtlist.getTrans_serial(),eaccAccountamtlist.getOrder_no()
                        ,strDate,"T");
            } else {
                withdrawsMap.put(eaccAccountamtlist.getTrans_serial(), false);
            }
        }
        for (RwWithdraw withdraw : withdraws) {
            Boolean  isExsits = withdrawsMap.get(withdraw.getTrans_serial());
            if(isExsits = null){
                isError = true;
                List<EaccAccountamtlist> eaccAccountamtlists = scheduleService.queryAmtListByTransSerial(withdraw.getTrans_serial());
                if(eaccAccountamtlists.size() == 0){
                    insertClearCheckError("{内部对账-提现}有提现订单无资金流水",withdraw.getTrans_serial(),withdraw.getOrder_no()
                            ,strDate,"T");
                }else {
                    logger.info("【内部对账】有流水记录，修改account_date");
                    eaccAccountamtlists.forEach(eaccAccountamtlist -> {
                        EaccAccountamtlist amtlist = new EaccAccountamtlist();
                        amtlist.setId(eaccAccountamtlist.getId());
                        amtlist.setAccount_date(DateUtils.parseDate(withdraw.getPayment_date(), DateUtils.DEF_FORMAT_NOTIME));
                        scheduleService.updateAccountDate(eaccAccountamtlist);
                    });
                }
            }else if(!isExsits){
                isError = true;
                insertClearCheckError("{内部对账-提现}订单重复",withdraw.getTrans_serial(),withdraw.getOrder_no()
                        ,strDate,"T");
            }else {
                withdrawsMap.put(withdraw.getTrans_serial(),true);//设置为已对账
            }
        }
        for(Map.Entry<String,Boolean> entry:withdrawsMap.entrySet()){
            if(!entry.getValue()){
                isError = true;
                insertClearCheckError("{内部对账-提现}资金表有订单，订单表无记录",entry.getKey(),""
                        ,strDate,"T");
            }
        }
        if (isError) {
            //给管理员发短信
            StringBuilder sb = new StringBuilder();
            if("BOB".equals(deployEnvironment)){
                sb.append("【北京银行】");
            }
            sb.append("【凌晨对账失败】请关注clear_check_error表中的错误数据");
            sendMsgService.sendErrorToAdmin(sb.toString(), null);
        }
    }

    private void insertClearCheckError(String remark,String trans_serial,String order_no,String clear_date,String type){
        logger.info("【充值提现对账流水】"+remark+"，trans_serial:" + trans_serial);
        ClearCheckError clearCheckError = new ClearCheckError();
        clearCheckError.setSerial_id(trans_serial);
        clearCheckError.setClear_date(clear_date);
        clearCheckError.setOrder_no(order_no);
        clearCheckError.setRemark(remark);
        clearCheckError.setStatus("0");
        clearCheckError.setClear_code(type);
        scheduleService.insertClearCheckError(clearCheckError);
    }

    @Override
    public Comparator<Object> getComparator() {
        return new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }

            public boolean equals(Object obj) {
                return this == obj;
            }
        };
    }

}
