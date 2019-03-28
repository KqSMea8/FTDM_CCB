package com.sunyard.sunfintech.billcheck.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.sunyard.sunfintech.billcheck.provider.IBillCheckService;
import com.sunyard.sunfintech.billcheck.provider.IReconciliationService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.dic.ClearStatus;
import com.sunyard.sunfintech.core.dic.IsSelfBank;
import com.sunyard.sunfintech.core.dic.OrderStatus;
import com.sunyard.sunfintech.core.dic.PayStatus;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.threads.SingleThreadPool;
import com.sunyard.sunfintech.core.util.*;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.PlatPaycodeMapper;
import com.sunyard.sunfintech.dao.mapper.PlatPlatinfoMapper;
import com.sunyard.sunfintech.dao.mapper.RwRechargeMapper;
import com.sunyard.sunfintech.dao.mapper.RwWithdrawMapper;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.thirdparty.provider.IAdapterService;
import com.sunyard.sunfintech.user.provider.IAccountSearchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by wkq on 2017/7/4.
 */
@Service(interfaceClass = IReconciliationService.class)
@CacheConfig(cacheNames = "reconciliation")
@org.springframework.stereotype.Service
public class ReconciliationService implements IReconciliationService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private PlatPaycodeMapper platPaycodeMapper;

    @Autowired
    private RwRechargeMapper rwRechargeMapper;

    @Autowired
    private RwWithdrawMapper rwWithdrawMapper;

    @Autowired
    private IBillCheckService billCheckService;

    @Autowired
    private PlatPlatinfoMapper platPlatinfoMapper;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private IAccountSearchService accountSearchService;

    @Autowired
    private IAdapterService adapterService;

    public String getCheckFilePath() {
        String path = "";
        //windows下
        if ("\\".equals(System.getProperty("file.separator"))) {

            path = UrlPropertiesUtil.getVal("windows_path");
        }
        //linux下
        if ("/".equals(System.getProperty("file.separator"))) {
            path = UrlPropertiesUtil.getVal("linux_path");
        }
        return path;
    }

    @Override
    public BaseResponse getAccountFile(ClearResult clearResult) {
        //查询是否有重复充值
//        List<EaccAccountamtlist> repeatRecharge = custRwRechargeMapper.selectRepeatRecharge(clear_date);
//        for(EaccAccountamtlist rwRecharge:repeatRecharge) {
//            clearCheckError.setClear_date(clear_date);
//            clearCheckError.setPlat_no(rwRecharge.getPlat_no());
//            clearCheckError.setClear_code("repeatRecharge");
//            clearCheckError.setSerial_id(rwRecharge.getTrans_serial());
//            clearCheckError.setStatus(ClearStatus.UNCHECK.getCode());
//            clearCheckError.setPay_amt(rwRecharge.getAmt());
//            clearCheckError.setClear_name("检查是否重复充值");
//            clearCheckError.setRemark("该笔充值流水重复，记账多次");
//            clearCheckError.setTrans_amt(rwRecharge.getAmt());//我方金额
//            //如果表中不存在该数据则插入
//            checkErrorExample.clear();
//            criteriaCheckError = checkErrorExample.createCriteria();
//            criteriaCheckError.andClear_dateEqualTo(clear_date);
//            criteriaCheckError.andPlat_noEqualTo(clearCheckError.getPlat_no());
//            criteriaCheckError.andSerial_idEqualTo(clearCheckError.getSerial_id());
//            criteriaCheckError.andPay_codeEqualTo(clearCheckError.getPay_code());
//            criteriaCheckError.andPay_amtEqualTo(clearCheckError.getPay_amt());
//            List<ClearCheckError> clearCheckErrors = clearCheckErrorMapper.selectByExample(checkErrorExample);
//            if (clearCheckErrors.size() == 0) {
//                clearCheckErrorMapper.insert(clearCheckError);
//            }
//            response.setRecode(BusinessMsg.FAIL);
//            response.setRemsg("对账失败");
//        }

        String mallNo = accountSearchService.queryMallNoByPlatNo(clearResult.getPlat_no());
        logger.info("==========对账开始==========");
        BaseResponse response = new BaseResponse();
        response.setRecode(BusinessMsg.SUCCESS);
        response.setRemsg("对账成功");
        //内部充值笔数
        int inRechargeNum = 0;
        //外部充值笔数
        int outRechargeNum = 0;
        //内部充值金额
        BigDecimal inRechargeAmt = BigDecimal.ZERO;
        //外部充值金额
        BigDecimal outRechargeAmt = BigDecimal.ZERO;
        //内部提现笔数
        int inWithdrawNum = 0;
        //外部提现笔数
        int outWithdrawNum = 0;
        //内部提现金额
        BigDecimal inWithdrawAmt = BigDecimal.ZERO;
        //外部提现金额
        BigDecimal outWithdrawAmt = BigDecimal.ZERO;

        //如果是集团，就不对，直接返回对账成功
        PlatPlatinfoExample examplePlatPlatinfo = new PlatPlatinfoExample();
        PlatPlatinfoExample.Criteria criteriaPlatPlatinfo = examplePlatPlatinfo.createCriteria();
        criteriaPlatPlatinfo.andPlat_noEqualTo(clearResult.getPlat_no());
        List<PlatPlatinfo> platPlatinfos = platPlatinfoMapper.selectByExample(examplePlatPlatinfo);
        String clear_date = DateUtils.formatDateToStr(clearResult.getClear_date(), DateUtils.DEF_FORMAT_STRING_DATE);
        if (platPlatinfos.size() == 0) {
            //更新清算结果记录
            logger.info("==========更新清算结果记录");
            clearResult.setRecharge_sum_outside(outRechargeAmt);//外部充值总金额
            clearResult.setRecharge_count_outside(outRechargeNum);//外部充值总笔数
            clearResult.setWithdrawals_sum_outside(outWithdrawAmt);//外部提现总金额
            clearResult.setWithdrawals_count_outside(outWithdrawNum);//外部提现总笔数
            clearResult.setClear_status(ClearStatus.CHECKED.getCode());
            billCheckService.updateClearResultById(clearResult);
        } else {
            String savePath = getCheckFilePath();
            PlatPaycodeExample platPaycodeExample = new PlatPaycodeExample();
            PlatPaycodeExample.Criteria criteria1PlatPaycode = platPaycodeExample.createCriteria();
            criteria1PlatPaycode.andPlat_noEqualTo(clearResult.getPlat_no());
            criteria1PlatPaycode.andPay_codeEqualTo(clearResult.getPay_code());
            criteria1PlatPaycode.andEnabledEqualTo(Constants.ENABLED);
            List<PlatPaycode> platPaycodes = platPaycodeMapper.selectByExample(platPaycodeExample);
            if (platPaycodes == null || platPaycodes.size() != 1) {
                response.setRecode(BusinessMsg.FAIL);
                response.setRemsg("查询不到plat_paycode或者有多条plat_paycode");
                logger.info("==========查询不到plat_paycode或者有多条plat_paycode");
                return response;
            }
            PlatPaycode platPaycode = platPaycodes.get(0);
            String partner_id = platPaycode.getPayment_plat_no();
            String pay_code = platPaycode.getChannel_id();
            Map<String, Object> dealMap = new HashMap<>();
            dealMap.put("partner_id", platPaycode.getPayment_plat_no());
            dealMap.put("paycheck_date", clear_date.replace("-", ""));
            dealMap.put("pay_code", platPaycode.getChannel_id());
            dealMap.put("precheck_flag", "0");
            dealMap.put("cert_sign", UrlPropertiesUtil.getVal("cert_sign"));//签名
            //CCB需要的字段
            dealMap.put("partner_serial_no", SeqUtil.getSeqNum());
            dealMap.put("channelId", platPaycode.getChannel_id());

            String ip = sysParameterService.querySysParameter(mallNo, URLConfigUtil.EPAY_SERVER_KEY);
            String baseUrl = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.YUNPAY_CHECK_FILE_URL);
            dealMap.put("host", ip);
            dealMap.put("url", baseUrl);
            logger.info("==========开始下载对账文件");
            List<String> lines;
            try {
                String key = adapterService.getAndSaveCheckFile(dealMap,
                        savePath + clear_date
                                + File.separator
                                + clearResult.getPlat_no()
                                + File.separator
                                + clearResult.getPlat_no() + "_"
                                + clearResult.getPay_code()
                                + "_" + clear_date, clear_date);
                lines = new ArrayList<>();
                Long size = CacheUtil.getCache().size(key);
                for (int i = 0; i < size; i++) {
                    lines.add((String) CacheUtil.getCache().rightPop(key));
                }
                CacheUtil.getCache().del(key);
            } catch (Exception e) {
                logger.info("==========" + e.getMessage());
                response.setRecode(BusinessMsg.FAIL);
                response.setRemsg(e.getMessage());
                return response;
            }
            // [0:平台号，1：日期，2：总笔数，3：总金额]
            String[] collect = lines.get(0).split("\\|");
            /**
             * 存储对账每日汇总数据
             */
            logger.info("==========储存对账每日汇总数据");
            HashMap<String, Object> totalMap = new HashMap<>();
            totalMap.put("clear_date", clear_date);
            totalMap.put("plat_no", clearResult.getPlat_no());
            totalMap.put("payment_plat_no", platPaycode.getPayment_plat_no());
            totalMap.put("get_time", new Date());
            totalMap.put("e_int", collect[2]);// 商户成功笔数
            totalMap.put("e_mat", collect[3]);// 商户成功金额
            totalMap.put("status", "0");// 获取对账文件
            //如果添加过，就不重复添加
            ClearCheckinfoEmx emx = new ClearCheckinfoEmx();
            emx.setClear_date(clearResult.getClear_date());
            emx.setPlat_no(partner_id);
            emx.setPayment_plat_no(platPaycode.getPayment_plat_no());
            List<ClearCheckinfoEmx> clearCheckinfoEmxes = billCheckService.queryClearCheckinfoEmx(emx);
            if (clearCheckinfoEmxes.size() == 0) {
                billCheckService.addClearInfoMx(totalMap);
            }

            // 单笔充值明细
            List<HashMap<String, Object>> emxlist = new ArrayList<>();
            for (int i = 1; i < lines.size(); i++) {
                // [0:平台号,1:日期,2:时间日期,3:订单号,4:platcust,5:金额,6:?,7:?,8：付款渠道,9:?]
                String[] mxlist = lines.get(i).split("\\|");
                HashMap<String, Object> map = new HashMap<>();
                map.put("clear_date", mxlist[1]);
                map.put("payment_plat_no", mxlist[0]);
                map.put("trans_date", mxlist[2]);
                map.put("serial_no", mxlist[3]);
                map.put("order_no", mxlist[4]);
                map.put("amt", mxlist[5]);
                map.put("charge", mxlist[6]);
                map.put("order_type", mxlist[7]);
                map.put("payment_pay_code", mxlist[8]);
                map.put("tripartite_num", mxlist[9]);
                map.put("plat_no", clearResult.getPlat_no());
                map.put("pay_code", pay_code);
                logger.info("==========我方paycode" + pay_code);
                logger.info("==========对方paycode" + mxlist[8]);
                if (pay_code.equals(mxlist[8])) {
                    emxlist.add(map);
                }
            }
            logger.info("==========删除当前对账日期数据");
            logger.info("==========pay_code:" + pay_code + "=====" + "clear_date:" + clear_date + "=====" + "plat_no" + clearResult.getPlat_no());
            billCheckService.deleteClearInfoByDate(clear_date, pay_code, clearResult.getPlat_no());
            /**
             * 存储对账每日明细数据
             */
            logger.info("==========储存对账每日明细数据");
            if (emxlist.size() > 0) {
                //每5000笔记录插入一次
                int size = 5000;//插入一次大小
                int count = (int) Math.ceil(emxlist.size() / (float) size);//总共插入次数
                for (int i = 0; i < count; i++) {
                    int firstIndex = i * size;//截取开始位置
                    int lastIndex;//截取结束位置
                    if (i == count - 1) {
                        lastIndex = emxlist.size();
                    } else {
                        lastIndex = (i + 1) * size;
                    }
                    List<HashMap<String, Object>> cutList = emxlist.subList(firstIndex, lastIndex);
                    billCheckService.addClearInfoLIst(cutList);
                }
            }
            /**
             * 与支付服务完成对账
             */
            logger.info("==========开始对账");
            //差错表数据准备
            ClearCheckError clearCheckError = new ClearCheckError();

            logger.info("==========对账充值");
            //查询外部充值对账清单
            ClearCheckinfoEmxlist clearCheckinfoEmxlist = new ClearCheckinfoEmxlist();
            clearCheckinfoEmxlist.setClear_date(clear_date);//对账日期
            clearCheckinfoEmxlist.setPlat_no(clearResult.getPlat_no());//平台号
            clearCheckinfoEmxlist.setPay_code(pay_code);//支付编号
            List<ClearCheckinfoEmxlist> checkinfoEmxLists = billCheckService.queryEmxListCharge(clearCheckinfoEmxlist);
            //查询内部充值对账清单
            RwRechargeExample rechargeExample = new RwRechargeExample();
            RwRechargeExample.Criteria rechargeCriteria = rechargeExample.createCriteria();
            rechargeCriteria.andPlat_noEqualTo(clearResult.getPlat_no());
            rechargeCriteria.andStatusEqualTo(OrderStatus.SUCCESS.getCode());
            rechargeCriteria.andPayment_dateEqualTo(clear_date);
            rechargeCriteria.andPay_codeEqualTo(clearResult.getPay_code());
            rechargeCriteria.andSelf_bank_flagNotEqualTo(IsSelfBank.YES.getCode());
            List<RwRecharge> rwRecharges = rwRechargeMapper.selectByExample(rechargeExample);
            Map<String, Object> rechargeMap = new HashMap<>();
            rwRecharges.forEach(recharge -> rechargeMap.put(recharge.getTrans_serial(), recharge));
            logger.info("==========对方为基础");
            for(ClearCheckinfoEmxlist checkinfo : checkinfoEmxLists){
                outRechargeNum++;
                outRechargeAmt = outRechargeAmt.add(checkinfo.getAmt());
                RwRecharge recharge = (RwRecharge) rechargeMap.get(checkinfo.getSerial_no());
                if(recharge == null){
                    logger.info("【对账】存管找不到该流水的充值，流水号：" + checkinfo.getSerial_no());
                    clearCheckError.setClear_date(clear_date);
                    clearCheckError.setPlat_no(checkinfo.getPlat_no());
                    clearCheckError.setClear_code("rechargeCheck");
                    clearCheckError.setSerial_id(checkinfo.getSerial_no());
                    clearCheckError.setOrder_no(checkinfo.getOrder_no());
                    clearCheckError.setStatus(ClearStatus.UNCHECK.getCode());
                    clearCheckError.setPay_code(checkinfo.getPay_code());
                    clearCheckError.setPay_amt(checkinfo.getAmt());
                    clearCheckError.setClear_name("充值对账");
                    clearCheckError.setRemark("存管无订单，支付有不明充值");
                    //如果表中不存在该数据则插入
                    insertClearCheckError(clearCheckError);
                    response.setRecode(BusinessMsg.FAIL);
                    response.setRemsg("对账失败");
                }else {
                    logger.info("【对账】我方找到该流水的充值，流水号：" + checkinfo.getSerial_no());
                    //如果金额不相同
                    if (0 != recharge.getTrans_amt().compareTo(checkinfo.getAmt())) {
                        logger.info("==========金额不一致");
                        clearCheckError.setClear_date(checkinfo.getClear_date());
                        clearCheckError.setPlat_no(checkinfo.getPlat_no());
                        clearCheckError.setClear_code("rechargeCheck");
                        clearCheckError.setSerial_id(checkinfo.getSerial_no());
                        clearCheckError.setOrder_no(checkinfo.getOrder_no());
                        clearCheckError.setStatus(ClearStatus.UNCHECK.getCode());
                        clearCheckError.setPay_code(checkinfo.getPay_code());
                        clearCheckError.setClear_name("充值对账");
                        clearCheckError.setRemark("充值金额不一致");
                        clearCheckError.setTrans_amt(recharge.getTrans_amt());//我方金额
                        clearCheckError.setPay_amt(checkinfo.getAmt());//对方金额
                        //如果表中不存在该数据则插入
                        insertClearCheckError(clearCheckError);
                        response.setRecode(BusinessMsg.FAIL);
                        response.setRemsg("对账失败");
                    }
                }
            }

            logger.info("==========我方为基础");
            Map<String, Object> rechargeCheckinfo = new HashMap<>();
            checkinfoEmxLists.forEach(checkinfo -> rechargeCheckinfo.put(checkinfo.getSerial_no(), checkinfo));
            for(RwRecharge recharge : rwRecharges) {
                inRechargeNum++;
                inRechargeAmt = inRechargeAmt.add(recharge.getTrans_amt());
                ClearCheckinfoEmxlist checkinfo = (ClearCheckinfoEmxlist) rechargeCheckinfo.get(recharge.getTrans_serial());
                if (checkinfo == null) {
                    logger.info("【对账】支付找不到该流水，流水号：" + recharge.getTrans_serial());
                    clearCheckError.setClear_date(clear_date);
                    clearCheckError.setPlat_no(recharge.getPlat_no());
                    clearCheckError.setClear_code("rechargeCheck");
                    clearCheckError.setSerial_id(recharge.getTrans_serial());
                    clearCheckError.setOrder_no(recharge.getOrder_no());
                    clearCheckError.setStatus(ClearStatus.UNCHECK.getCode());
                    clearCheckError.setPay_code(recharge.getPay_code());
                    clearCheckError.setTrans_amt(recharge.getTrans_amt());//存管金额
                    clearCheckError.setClear_name("充值对账");
                    clearCheckError.setRemark("支付无订单，存管有不明充值");
                    //如果表中不存在该数据则插入
                    insertClearCheckError(clearCheckError);
                    response.setRecode(BusinessMsg.FAIL);
                    response.setRemsg("对账失败");
                } else {
                    logger.info("【对账】支付找到该流水的充值，流水号：" + checkinfo.getSerial_no());
                    if (0 != recharge.getTrans_amt().compareTo(checkinfo.getAmt())) {
                        logger.info("【对账】金额不一致");
                        clearCheckError.setClear_date(clear_date);
                        clearCheckError.setPlat_no(recharge.getPlat_no());
                        clearCheckError.setClear_code("rechargeCheck");
                        clearCheckError.setSerial_id(recharge.getTrans_serial());
                        clearCheckError.setStatus(ClearStatus.UNCHECK.getCode());
                        clearCheckError.setPay_code(recharge.getPay_code());
                        clearCheckError.setTrans_amt(recharge.getTrans_amt());
                        clearCheckError.setPay_amt(checkinfo.getAmt());
                        clearCheckError.setClear_name("充值对账");
                        clearCheckError.setRemark("充值金额不一致");
                        //如果表中不存在该数据则插入
                        insertClearCheckError(clearCheckError);
                        response.setRecode(BusinessMsg.FAIL);
                        response.setRemsg("对账失败");
                    }
                }
            }

            logger.info("==========对账提现");
            //查询外部提现对账清单
            checkinfoEmxLists = billCheckService.queryEmxListWithdraw(clearCheckinfoEmxlist);
            //查询所有提现记录
            RwWithdrawExample withdrawExample = new RwWithdrawExample();
            RwWithdrawExample.Criteria withdrawCriteria = withdrawExample.createCriteria();
            withdrawCriteria.andPlat_noEqualTo(clearResult.getPlat_no());
            withdrawCriteria.andPayment_dateEqualTo(clear_date);
            withdrawCriteria.andPay_codeEqualTo(clearResult.getPay_code());
            withdrawCriteria.andPay_statusEqualTo(PayStatus.SUCCESS.getCode());
            List<RwWithdraw> rwWithdraws = rwWithdrawMapper.selectByExample(withdrawExample);

            logger.info("==========对方为基础");
            Map<String, Object> withdrawMap = new HashMap<>();
            rwWithdraws.forEach(withdraw -> withdrawMap.put(withdraw.getTrans_serial(), withdraw));
            for(ClearCheckinfoEmxlist checkinfo : checkinfoEmxLists){
                outWithdrawNum++;//外部提现笔数
                outWithdrawAmt = outWithdrawAmt.add(checkinfo.getAmt());//外部提现金额
                RwWithdraw withdraw = (RwWithdraw) withdrawMap.get(checkinfo.getSerial_no());
                if(withdraw == null){
                    logger.info("【对账】存管找不到该笔提现，流水号：" + checkinfo.getSerial_no());
                    clearCheckError.setClear_date(clear_date);
                    clearCheckError.setPlat_no(checkinfo.getPlat_no());
                    clearCheckError.setClear_code("withdrawCheck");
                    clearCheckError.setSerial_id(checkinfo.getSerial_no());
                    clearCheckError.setOrder_no(checkinfo.getOrder_no());
                    clearCheckError.setStatus(ClearStatus.UNCHECK.getCode());
                    clearCheckError.setPay_code(checkinfo.getPay_code());
                    clearCheckError.setPay_amt(checkinfo.getAmt());
                    clearCheckError.setClear_name("提现对账");
                    clearCheckError.setRemark("存管无订单，支付有不明提现");
                    //如果表中不存在该数据则插入
                    insertClearCheckError(clearCheckError);
                    response.setRecode(BusinessMsg.FAIL);
                    response.setRemsg("对账失败");
                }else {
                    if(0 != checkinfo.getAmt().compareTo(withdraw.getOut_amt())){
                        logger.info("【对账】金额不一致");
                        clearCheckError.setClear_date(checkinfo.getClear_date());
                        clearCheckError.setPlat_no(checkinfo.getPlat_no());
                        clearCheckError.setClear_code("withdrawCheck");
                        clearCheckError.setSerial_id(checkinfo.getSerial_no());
                        clearCheckError.setOrder_no(checkinfo.getOrder_no());
                        clearCheckError.setStatus(ClearStatus.UNCHECK.getCode());
                        clearCheckError.setPay_code(checkinfo.getPay_code());
                        clearCheckError.setClear_name("提现对账");
                        clearCheckError.setRemark("提现金额不一致");
                        clearCheckError.setTrans_amt(withdraw.getOut_amt());//我方金额
                        clearCheckError.setPay_amt(checkinfo.getAmt());//对方金额
                        //如果表中不存在该数据则插入
                        insertClearCheckError(clearCheckError);
                        response.setRecode(BusinessMsg.FAIL);
                        response.setRemsg("对账失败");
                    }
                }
            }

            logger.info("==========我方为基础");
            Map<String, Object> withdrawCheckinfoMap = new HashMap<>();
            checkinfoEmxLists.forEach(checkinfo -> withdrawCheckinfoMap.put(checkinfo.getSerial_no(), checkinfo));
            for(RwWithdraw withdraw : rwWithdraws){
                inWithdrawNum++;
                inWithdrawAmt = inWithdrawAmt.add(withdraw.getOut_amt());
                ClearCheckinfoEmxlist checkinfo = (ClearCheckinfoEmxlist) withdrawCheckinfoMap.get(withdraw.getTrans_serial());
                if(checkinfo == null){
                    logger.info("【对账】支付找不到该笔提现，流水号" + withdraw.getTrans_serial());
                    clearCheckError.setClear_date(clear_date);
                    clearCheckError.setPlat_no(withdraw.getPlat_no());
                    clearCheckError.setClear_code("withdrawCheck");
                    clearCheckError.setSerial_id(withdraw.getTrans_serial());
                    clearCheckError.setStatus(ClearStatus.UNCHECK.getCode());
                    clearCheckError.setPay_code(withdraw.getPay_code());
                    clearCheckError.setTrans_amt(withdraw.getOut_amt());
                    clearCheckError.setClear_name("提现对账");
                    clearCheckError.setRemark("支付无订单，存管有不明提现");
                    //如果表中不存在该数据则插入
                    insertClearCheckError(clearCheckError);
                    response.setRecode(BusinessMsg.FAIL);
                    response.setRemsg("对账失败");
                }else {
                    if(0 != withdraw.getOut_amt().compareTo(checkinfo.getAmt())){
                        logger.info("【对账】金额不一致");
                        logger.info("==========提现对账有差错");
                        clearCheckError.setClear_date(checkinfo.getClear_date());
                        clearCheckError.setPlat_no(checkinfo.getPlat_no());
                        clearCheckError.setClear_code("withdrawCheck");
                        clearCheckError.setSerial_id(checkinfo.getSerial_no());
                        clearCheckError.setOrder_no(checkinfo.getOrder_no());
                        clearCheckError.setStatus(ClearStatus.UNCHECK.getCode());
                        clearCheckError.setPay_code(checkinfo.getPay_code());
                        clearCheckError.setClear_name("我方提现对账");
                        clearCheckError.setRemark("提现对账有差错");
                        clearCheckError.setTrans_amt(withdraw.getOut_amt());//我方金额
                        clearCheckError.setPay_amt(checkinfo.getAmt());//对方金额
                        //如果表中不存在该数据则插入
                        insertClearCheckError(clearCheckError);
                        response.setRecode(BusinessMsg.FAIL);
                        response.setRemsg("对账失败");
                    }

                }
            }

            logger.info("==========内部充值笔数" + inRechargeNum);
            logger.info("==========内部充值金额" + inRechargeAmt);
            logger.info("==========内部提现笔数" + inWithdrawNum);
            logger.info("==========内部提现金额" + inWithdrawAmt);

            logger.info("==========外部充值笔数" + outRechargeNum);
            logger.info("==========外部充值金额" + outRechargeAmt);
            logger.info("==========外部提现笔数" + outWithdrawNum);
            logger.info("==========外部提现金额" + outWithdrawAmt);

            //更新清算结果记录
            logger.info("==========更新清算结果记录");
            clearResult.setRecharge_sum_inside(inRechargeAmt);//内部充值总金额
            clearResult.setRecharge_count_inside(inRechargeNum);//内部充值总笔数
            clearResult.setWithdrawals_sum_inside(inWithdrawAmt);//内部提现总金额
            clearResult.setWithdrawals_count_inside(inWithdrawNum);//内部提现总笔数

            clearResult.setRecharge_sum_outside(outRechargeAmt);//外部充值总金额
            clearResult.setRecharge_count_outside(outRechargeNum);//外部充值总笔数
            clearResult.setWithdrawals_sum_outside(outWithdrawAmt);//外部提现总金额
            clearResult.setWithdrawals_count_outside(outWithdrawNum);//外部提现总笔数

            if (inRechargeAmt.compareTo(outRechargeAmt) != 0 || inWithdrawAmt.compareTo(outWithdrawAmt) != 0
                    || inRechargeNum != outRechargeNum || inWithdrawNum != outWithdrawNum) {
                //内外部充值提现金额或笔数不等
                response.setRecode(BusinessMsg.FAIL);
                response.setRemsg("对账失败");
            }

            if (BusinessMsg.SUCCESS.equals(response.getRecode())) {
                clearResult.setClear_status(ClearStatus.CHECKED.getCode());
            } else {
                clearResult.setClear_status(ClearStatus.CHECK_ERROR.getCode());
            }
            clearResult.setClear_recordtime(DateUtils.format(new Date(), DateUtils.DEF_FORMAT_NODATE));
            billCheckService.updateClearResultById(clearResult);
        }
        //根据集团号查询该集团下所有的平台
        PlatPlatinfoExample platExample = new PlatPlatinfoExample();
        PlatPlatinfoExample.Criteria platCriteria = platExample.createCriteria();
        platCriteria.andMall_noEqualTo(mallNo);
        List<PlatPlatinfo> platPlatinfoList = platPlatinfoMapper.selectByExample(platExample);
        List<String> platNos = new ArrayList<>();
        platNos.add(mallNo);
        platPlatinfoList.forEach(platPlatinfo -> platNos.add(platPlatinfo.getPlat_no()));
        //判断是否全部对账成功
        List<ClearResult> clearResults = billCheckService.selectUnCheckedClearResult(clearResult, platNos);
        if (clearResults.size() == 0) {
            logger.info("【对账】该平台所有数据都已对账成功，生成出入金对账文件");
            SingleThreadPool.getThreadPool().execute(() ->
                    billCheckService.makePayCodeClearCT(clearResult.getPlat_no(), clear_date));
            //如果是集团对账需要发送对账完成通知给中间平台
            if (platPlatinfoList.size() > 1) {
                SingleThreadPool.getThreadPool().execute(() ->
                        billCheckService.billCheckNotify(mallNo, clear_date));
            }
        }
        return response;
    }

    /**
     * 插入对账差错表
     * @param clearCheckError
     * @param clearCheckError
     */
    private void insertClearCheckError(ClearCheckError clearCheckError){
        List<ClearCheckError> clearCheckErrors = billCheckService.selectClearCheckError(clearCheckError);
        if (clearCheckErrors.size() == 0) {
            billCheckService.insertClearError(clearCheckError);
        }
    }
}