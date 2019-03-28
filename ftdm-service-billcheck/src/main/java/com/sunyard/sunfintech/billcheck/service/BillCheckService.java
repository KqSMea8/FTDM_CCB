package com.sunyard.sunfintech.billcheck.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunyard.sunfintech.billcheck.model.bo.SortingRequest;
import com.sunyard.sunfintech.billcheck.provider.IBillCheckService;
import com.sunyard.sunfintech.billcheck.provider.IClearService;
import com.sunyard.sunfintech.billcheck.service.inner.CTCheckService;
import com.sunyard.sunfintech.billcheck.utils.BillCheckPropertiesUtil;
import com.sunyard.sunfintech.billcheck.utils.SFTPUtils;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseHttpResponse;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.HttpClientUtils;
import com.sunyard.sunfintech.core.util.URLConfigUtil;
import com.sunyard.sunfintech.custdao.mapper.*;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.*;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;

import java.beans.Encoder;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wubin on 2017/6/17.
 */
@Service(interfaceClass = IBillCheckService.class)
@CacheConfig(cacheNames = "billCheckService")
@org.springframework.stereotype.Service
public class BillCheckService extends BaseServiceSimple implements IBillCheckService {

    @Value("${deploy.environment}")
    private String environment;

    @Autowired
    private RwWithdrawMapper rwWithdrawMapper;

    @Autowired
    private CustClearUnusualRecordMapper clearUnusualRecordMapper;

    @Autowired
    private CustAccountSubjectInfoMapper accountSubjectInfoMapper;

    @Autowired
    private AccountSubjectInfoMapper accSubjectInfoMapper;

    @Autowired
    private CustEaccAccountamtlistMapper custEaccAccountamtlistMapper;

    @Autowired
    private EaccAccountamtlistMapper eaccAccountamtlistMapper;

    @Autowired
    private CustClearResultMapper custClearResultMapper;

    @Autowired
    private CustClearResultSummaryMapper clearResultSummeryMapper;

    @Autowired
    private PlatPaycodeMapper platPaycodeMapper;

    @Autowired
    private IClearService clearService;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private CustPlatPlatinfoMapper custPlatPlatinfoMapper;

    @Autowired
    private EaccClearCheckInfoMapper eaccClearCheckInfoMapper;

    @Autowired
    private CustEaccClearCheckInfoMapper custEaccClearCheckInfoMapper;

    @Autowired
    private CustEaccClearErrorInfoMapper custEaccClearErrorInfoMapper;

    @Autowired
    private EaccClearSubjectInfoMapper eaccClearSubjectInfoMapper;

    @Autowired
    private EaccUserinfoMapper eaccUserinfoMapper;

    @Autowired
    private EaccAccountBalanceSumMapper eaccAccountBalanceSumMapper;

    @Autowired
    private ClearTransSerialErrorMapper clearTransSerialErrorMapper;

    @Autowired
    private ClearResultMapper clearResultMapper;

    @Autowired
    private ClearCheckinfoEmxMapper clearCheckinfoEmxMapper;

    @Autowired
    private ClearCheckinfoEmxlistMapper clearCheckinfoEmxlistMapper;

    @Autowired
    private ClearCheckErrorMapper clearCheckErrorMapper;

    @Autowired
    private BillCheckNotifyRetryMapper billCheckNotifyRetryMapper;

    @Autowired
    private CTCheckService ctCheckService;

    @Autowired
    private RwRechargeMapper rwRechargeMapper;

    private static final String REDISKEY_SYS_PARAMETER = Constants.CACHE_NAMESPACE + "billcheck:";

    /**
     * 生成支付渠道资金进出数据
     * BOB-FENGJR-ZS-C-20170509|20150415|6|180000.00|
     * BOB-FENGJR-ZS-C-20170509|20150415|121515|10045425103559643|30000.00|C|072|101
     * 一-汇总：平台编号|对账日期|笔数|总金额
     * 明细：平台编号|交易日期|交易时间|订单号|金额|C-0，T-1|支付编号|流水号
     *
     * @param plat_no
     * @throws BusinessException
     */
    @Override
    public Boolean makePayCodeClearCT(String plat_no, String clear_date) throws BusinessException {
        logger.info("====================对账开始：start 生成充值提现出入金流水====================");
        //查询充值
        RwRechargeExample rechargeExample = new RwRechargeExample();
        RwRechargeExample.Criteria rechargeExampleCriteria = rechargeExample.createCriteria();
        rechargeExampleCriteria.andPlat_noEqualTo(plat_no);
        rechargeExampleCriteria.andPayment_dateEqualTo(clear_date);
        rechargeExampleCriteria.andStatusEqualTo(OrderStatus.SUCCESS.getCode());
        rechargeExampleCriteria.andPay_codeNotEqualTo(TransConsts.PLAT_CHARGE_CODE);
        List<RwRecharge> rechargeList = rwRechargeMapper.selectByExample(rechargeExample);
        //查询提现
        RwWithdrawExample withdrawExample = new RwWithdrawExample();
        RwWithdrawExample.Criteria withdrawCriteria = withdrawExample.createCriteria();
        withdrawCriteria.andPlat_noEqualTo(plat_no);
        withdrawCriteria.andPayment_dateEqualTo(clear_date);
        withdrawCriteria.andPay_statusEqualTo(PayStatus.SUCCESS.getCode());
        List<String> payCode = new ArrayList<>();
        payCode.add(TransConsts.PLAT_WITHDRAW_CODE);
        payCode.add(TransConsts.WITHDRAW_TO_PLAT_CODE);
        withdrawCriteria.andPay_codeNotIn(payCode);
        List<RwWithdraw> withdrawList = rwWithdrawMapper.selectByExample(withdrawExample);
        //详细数据
        StringBuilder sbList = new StringBuilder();
        BigDecimal tol = BigDecimal.ZERO;
        for(RwRecharge recharge : rechargeList){
            tol = tol.add(recharge.getTrans_amt());
            sbList.append(plat_no).append("|").append(recharge.getTrans_date()).append("|").append(recharge.getTrans_time())
                    .append("|").append(recharge.getOrder_no()).append("|").append(recharge.getTrans_amt())
                    .append("|").append("C").append("|").append(recharge.getPay_code()).append("|").append("\n");
        }
        for(RwWithdraw withdraw : withdrawList){
            tol = tol.add(withdraw.getOut_amt());
            sbList.append(plat_no).append("|").append(withdraw.getTrans_date()).append("|").append(withdraw.getTrans_time())
                    .append("|").append(withdraw.getOrder_no()).append("|").append(withdraw.getOut_amt())
                    .append("|").append("T").append("|").append(withdraw.getPay_code()).append("|").append("\n");
        }
        StringBuilder sbAll = new StringBuilder();
        sbAll.append(plat_no).append("|").append(clear_date).append("|")
                .append(rechargeList.size() + withdrawList.size()).append("|")
                .append(tol).append("\n")
                .append(sbList).append("ENDFLAG");

//        Map<String, Object> map = new HashMap<>();
//        map.put("plat_no", plat_no);
//        map.put("clear_date", clear_date);
//        Map<String, Object> tolMap = custEaccAccountamtlistMapper.selectTolPayCodeCtlist(map);
//        //查询明细
//        List<Map<String, Object>> mapList = custEaccAccountamtlistMapper.selectDetailPayCodeCtlist(map);
//        mapList.add(0, tolMap);//汇总放到最前面
//        StringBuffer sb = new StringBuffer();
//        if (mapList.size() > 1) {
//            for (int i = 0; i < mapList.size(); i++) {
//                sb.append(mapList.get(i).get("accTransPayCodelistInfo").toString()).append("\n");
//            }
//            sb.append("ENDFLAG");
//        }

        try {
            String fileDirName = "/home/fund/ftdm/billcheck/uploadFiles/" + "PAY_CHANNEL_DATA/" + plat_no;
            String fileName = "PAY_CHANNEL_DATA_" + clear_date + ".txt";
            File file = new File(fileDirName, fileName);
            if (file.exists()) { // 判断文件或文件夹是否存在
                System.out.println(file.getName() + " 已经存在");
            } else {
                try {
                    file.getParentFile().mkdirs();
                    file.createNewFile(); // 创建文件或者文件夹
                    System.out.println(file.getName() + " 创建成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String plat_ftp_name = sysParameterService.querySysParameter(plat_no, URLConfigUtil.PLAT_FTP_NAME);
            String ftp_path = "/home/ftp/" + plat_ftp_name + "/" + plat_no;

            FileUtils.writeStringToFile(file, sbAll.toString(), "utf-8", false);
            logger.info("====================本地文件写成功====================");

            String addr = BillCheckPropertiesUtil.getVal("addr");
            String port = BillCheckPropertiesUtil.getVal("port");
            String username = BillCheckPropertiesUtil.getVal("username");
            String password = BillCheckPropertiesUtil.getVal("password");
            if (!"CCB".equals(environment)) {
                com.sunyard.sunfintech.core.util.FtpUtils f = new com.sunyard.sunfintech.core.util.FtpUtils(addr, 21, username, password);
                if (f.open()) {
                    logger.info("====================连接FTP成功！！！====================");
                    f.changeToParentDir();
                    f.upload(fileDirName + "/" + fileName, fileName, ftp_path);
                    f.close();
                    logger.info("====================FTP文件写成功====================");
                } else {
                    logger.info("====================连接FTP失败！！！====================");
                }
                logger.info("====================对账：end 生成账户余额对账文件====================");
                return true;
            } else {
                SFTPUtils ftp = new SFTPUtils(addr, port, username, password);
                logger.info(String.format("ip【%s】port【%s】username【%s】password【%s】", addr, port, username, password));
                if (ftp.connect()) {
                    logger.info("====================连接SFTP成功！！！====================");
                    if (ftp.uploadFile(ftp_path + "/", fileName, fileDirName + "/", fileName)) {
                        logger.info("====================SFTP文件写成功！！！====================");
                    } else {
                        logger.info("====================SFTP文件写失败功！！！====================");
                    }
                    //关闭连接
                    ftp.disconnect();
                    return true;
                } else {
                    logger.info("====================连接SFTP失败！！！====================");
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error("写入ftp文件失败：" + e);
            return false;
        }
    }

    /**
     * 获取未确认的提现订单;处理中，提现时间前一天，年月日，存到未确认订单表
     *
     * @param clear_date
     * @param plat_no
     * @throws BusinessException
     */
    @Override
    public Boolean getNoConfim(String clear_date, String plat_no) throws BusinessException {
        logger.info("====================对账开始：start 获取未确认的提现订单====================");
        //获取未确认的提现订单
        Date cDate = DateUtils.beforeDate(DateUtils.parseDate(clear_date), 1);
        String clearPerferDate = new SimpleDateFormat("yyyyMMdd").format(cDate);
        logger.info("前一天时间：" + clearPerferDate);
        RwWithdrawExample rwWithdrawExample = new RwWithdrawExample();
        RwWithdrawExample.Criteria criteria = rwWithdrawExample.createCriteria();
        criteria.andPlat_noEqualTo(plat_no);
        criteria.andPay_statusEqualTo(PayStatus.PAYING.getCode());
        criteria.andFinish_timeEqualTo(clearPerferDate);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<RwWithdraw> rwWithdrawList = rwWithdrawMapper.selectByExample(rwWithdrawExample);
        if (rwWithdrawList != null && rwWithdrawList.size() > 0) {
            //存入待确认订单表
            ClearUnusualRecord clearUnusualRecord = null;
            List<ClearUnusualRecord> clearUnusualRecordList = new ArrayList<ClearUnusualRecord>();
            for (RwWithdraw rwWithdraw : rwWithdrawList) {
                clearUnusualRecord = new ClearUnusualRecord();
                clearUnusualRecord.setPlat_no(rwWithdraw.getPlat_no());
                clearUnusualRecord.setTrans_serial(rwWithdraw.getTrans_serial());
                clearUnusualRecord.setAcct_state(rwWithdraw.getAcct_state());
                clearUnusualRecord.setClear_date(clearPerferDate);
                clearUnusualRecord.setPay_code(rwWithdraw.getPay_code());
                clearUnusualRecord.setAmt(rwWithdraw.getOut_amt());
                clearUnusualRecord.setCreate_by(rwWithdraw.getPlat_no());
                clearUnusualRecord.setCreate_time(new Date());
                clearUnusualRecord.setEnabled(Constants.ENABLED);
                clearUnusualRecordList.add(clearUnusualRecord);
            }
            clearUnusualRecordMapper.insertMore(clearUnusualRecordList);
        }
        logger.info("====================对账：end 获取未确认的提现订单====================");
        return true;
    }

    /**
     * 生成账户余额对账文件;
     * 一-汇总：平台编号|对账日期|总金额|可用|冻结
     * 明细：平台编号|科目|子科目|总金额|可用金额|冻结金额，平台编号,非04
     *
     * @param plat_no
     * @throws BusinessException
     */
    @Override
    public Boolean makeAccountBalance(String plat_no) throws BusinessException {
        logger.info("====================对账开始：start 生成账户余额对账文件====================");
        Date cDate = DateUtils.beforeDate(new Date(), 1);
        String clearPerferDate = new SimpleDateFormat("yyyyMMdd").format(cDate);
        //查询汇总
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("plat_no", plat_no);
        map.put("clear_date", clearPerferDate);
        Map<String, Object> tolMap = accountSubjectInfoMapper.selectTolAccountSubject(map);
        //查询明细
        List<Map<String, Object>> mapList = accountSubjectInfoMapper.selectDetailAccountSubject(map);
        mapList.add(0, tolMap);//汇总放到最前面
        try {
            String fileDirName = "/home/fund/ftdm/billcheck/uploadFiles/" + "FTDM_BALANCE/" + plat_no;
            String fileName = "FTDM_BALANCE_" + clearPerferDate + ".txt";
            File file = new File(fileDirName, fileName);
            if (file.exists()) { // 判断文件或文件夹是否存在
                System.out.println(file.getName() + " 已经存在");
            } else {
                try {
                    file.getParentFile().mkdirs();
                    file.createNewFile(); // 创建文件或者文件夹
                    System.out.println(file.getName() + " 创建成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            PrintWriter pw = new PrintWriter(new FileWriter(file), true);
            String sign = "testSign1234";
            if (mapList.size() > 1) {
                for (int i = 0; i < mapList.size(); i++) {
                    pw.println(mapList.get(i).get("accBalanceBillCheckInfo").toString());
                }
//                    pw.println(sign);
            }
            pw.println("ENDFLAG");
            pw.flush();
            pw.close();
            logger.info("====================本地文件写成功====================");
            String plat_ftp_name = sysParameterService.querySysParameter(plat_no, URLConfigUtil.PLAT_FTP_NAME);
            String addr = BillCheckPropertiesUtil.getVal("addr");
            String port = BillCheckPropertiesUtil.getVal("port");
            String username = BillCheckPropertiesUtil.getVal("username");
            String password = BillCheckPropertiesUtil.getVal("password");
            String ftp_path = "/home/ftp/" + plat_ftp_name + "/" + plat_no;
            logger.info("环境变量：" + environment);
            if (!"CCB".equals(environment)) {
                com.sunyard.sunfintech.core.util.FtpUtils f = new com.sunyard.sunfintech.core.util.FtpUtils(addr, 21, username, password);
                if (f.open()) {
                    logger.info("====================连接FTP成功！！！====================");
//                f.upload(fileDirName+"/"+fileName,"FTDM_BALANCE_"+clearPerferDate+".txt",plat_no);
                    f.changeToParentDir();
                    f.upload(fileDirName + "/" + fileName, fileName, ftp_path);
                    f.close();
                    logger.info("====================FTP文件写成功====================");
                } else {
                    logger.info("====================连接FTP失败！！！====================");
                }
                logger.info("====================对账：end 生成账户余额对账文件====================");
                return true;
            } else {
                SFTPUtils ftp = new SFTPUtils(addr, port, username, password);
                logger.info(String.format("ip【%s】port【%s】username【%s】password【%s】", addr, port, username, password));
                if (ftp.connect()) {
                    logger.info("====================连接SFTP成功！！！====================");
                    if (ftp.uploadFile(ftp_path + "/", fileName, fileDirName + "/", fileName)) {
                        logger.info("====================SFTP文件写成功！！！====================");
                    } else {
                        logger.info("====================SFTP文件写失败功！！！====================");
                    }
                    //关闭连接
                    ftp.disconnect();
                    return true;
                } else {
                    logger.info("====================连接SFTP失败！！！====================");
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error("写入ftp文件失败：" + e);
            return false;
        }
    }

    /**
     * 生成电子账户对账文件,04
     *
     * @param plat_no
     * @throws BusinessException
     */
    @Override
    public Boolean makeElcAccountBalance(String plat_no) throws BusinessException {
        logger.info("====================对账开始：start 生成电子账户对账文件====================");
        Date cDate = DateUtils.beforeDate(new Date(), 1);
        String clearPerferDate = new SimpleDateFormat("yyyyMMdd").format(cDate);
        boolean flag = CacheUtil.getCache().setnx("elc_balance_" + plat_no + clearPerferDate, "elc_balance_" + plat_no + clearPerferDate);
        if (flag) {//缓存不存在该键，设置值
            //设置order_no键的有效时间为24小时，单位为秒
            CacheUtil.getCache().expire("elc_balance_" + plat_no + clearPerferDate, 24 * 60 * 60);
        } else {//存在该键，说明已经生成过了文件
            return true;
        }
        //查询汇总
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("plat_no", plat_no);
        map.put("clear_date", clearPerferDate);
        Map<String, Object> tolMap = accountSubjectInfoMapper.selectTolAccountSubjectByElc(map);
        //查询明细
        List<Map<String, Object>> mapList = accountSubjectInfoMapper.selectDetailAccountSubjectByElc(map);
        mapList.add(0, tolMap);//汇总放到最前面
//        try{
//            String fileDirName = "/home/fund/ftdm/billcheck/uploadFiles/"+plat_no+"/"+clearPerferDate;
//            String fileName = "EZH_"+plat_no+".txt";
//            File file = new File(fileDirName,fileName);
//            if(file.exists()) { // 判断文件或文件夹是否存在
//                System.out.println(file.getName() + " 已经存在");
//            } else {
//                try {
//                    file.getParentFile().mkdirs();
//                    file.createNewFile(); // 创建文件或者文件夹
//                    System.out.println(file.getName() + " 创建成功");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            PrintWriter pw = new PrintWriter(new FileWriter(file),true);
//            String sign = "";
//            for (int i=0;i<mapList.size();i++){
//                pw.println(mapList.get(i).get("accBalanceBillCheckInfo").toString());
//            }
//            pw.println(sign);
//            pw.flush();
//            pw.close();
//            FtpUtils ftpUtils = new FtpUtils();
//            String ftpPath = "/"+plat_no+"/EZH_"+clearPerferDate;
//            String addr = BillCheckPropertiesUtil.getVal("addr");
//            String username = BillCheckPropertiesUtil.getVal("username");
//            String password = BillCheckPropertiesUtil.getVal("password");
//            ftpUtils.connect(ftpPath, addr, 21, username, password);
//            ftpUtils.upload(file);
//            logger.info("====================对账：end 生成电子账户对账文件====================");
//            return true;
//        }catch (Exception e){
//            logger.error("写入ftp文件失败："+e);
//            return false;
//        }

        try {
            String plat_ftp_name = sysParameterService.querySysParameter(plat_no, URLConfigUtil.PLAT_FTP_NAME);
            String fileDirName = "/home/fund/ftdm/billcheck/uploadFiles/" + "EACCOUNT_BALANCE/" + plat_no;
            String fileName = "EACCOUNT_BALANCE_" + clearPerferDate + ".txt";
            String ftp_path = "/home/ftp/" + plat_ftp_name + "/" + plat_no;
            File file = new File(fileDirName, fileName);
            if (file.exists()) { // 判断文件或文件夹是否存在
                System.out.println(file.getName() + " 已经存在");
            } else {
                try {
                    file.getParentFile().mkdirs();
                    file.createNewFile(); // 创建文件或者文件夹
                    System.out.println(file.getName() + " 创建成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String addr = BillCheckPropertiesUtil.getVal("addr");
            String username = BillCheckPropertiesUtil.getVal("username");
            String password = BillCheckPropertiesUtil.getVal("password");
            com.sunyard.sunfintech.core.util.FtpUtils f = new com.sunyard.sunfintech.core.util.FtpUtils(addr, 21, username, password);
            if (f.open()) {
                logger.info("====================连接FTP成功！！！====================");
                PrintWriter pw = new PrintWriter(new FileWriter(file), true);
                String sign = "testSign1234";
                if (mapList.size() > 1) {
                    for (int i = 0; i < mapList.size(); i++) {
                        pw.println(mapList.get(i).get("accBalanceBillCheckInfo").toString());
                    }
//                    pw.println(sign);
                }
                pw.println("ENDFLAG");
                logger.info("====================本地文件写成功====================");
//                f.upload(fileDirName+"/"+fileName,"EACCOUNT_BALANCE_"+clearPerferDate+".txt",plat_no);
                f.changeToParentDir();
                f.upload(fileDirName + "/" + fileName, "EACCOUNT_BALANCE_" + clearPerferDate + ".txt", ftp_path);
                f.close();
                logger.info("====================FTP文件写成功====================");
            } else {
                logger.info("====================连接FTP失败！！！====================");
            }
            logger.info("====================对账：end 生成电子账户对账文件====================");
            return true;
        } catch (Exception e) {
            logger.error("写入ftp文件失败：" + e);
            return false;
        }
    }

    /**
     * 日终统计 createClearList
     *
     * @throws BusinessException
     */
    @Override
    public Boolean countClearList(String clear_date, String plat_no) throws BusinessException {
        logger.info("====================对账开始：start 日终统计====================");
        Date cDate = DateUtils.beforeDate(DateUtils.parseDate(clear_date), 1);
        String clearPerferDate = new SimpleDateFormat("yyyyMMdd").format(cDate);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("clear_date", clearPerferDate);
        param.put("plat_no", plat_no);
        //记录清算结果明细表
        List<ClearResult> clearResults = custClearResultMapper.selectClearResult(param);
        if (clearResults != null && clearResults.size() > 0) {
            for (ClearResult clearResult : clearResults) {
                clearResult.setClear_date(cDate);
                custClearResultMapper.insertResult(clearResult);
            }
        }
        //记录清算结果汇总表
        clearResultSummeryMapper.addClearResultSummary(param);
        //查询平台充值提现，并更新汇总表
        List<Map<String, Object>> platSumList = clearResultSummeryMapper.selectPlatSummary(param);
        if (platSumList != null) {
            clearResultSummeryMapper.updateResultSumBatch(platSumList);
        }
        //查询系统可用现金，并更新汇总表
        List<Map<String, Object>> platBalanceList = custClearResultMapper.selectPlatBalance(param);
        if (platBalanceList != null) {
            clearResultSummeryMapper.updateResultSumBatch(platBalanceList);
        }
        //查询银行账户余额，并更新汇总表
        List<Map<String, Object>> bankBalanceList = custClearResultMapper.selectBankBalance(param);
        if (bankBalanceList != null && bankBalanceList.size() > 0) {
            clearResultSummeryMapper.updateResultSumBatch(bankBalanceList);
        }
        logger.info("====================对账：end 日终统计====================");
        return true;
    }

    @Override
    public Boolean countEaccAccountBalance(String mall_no) throws BusinessException {
        Date cDate = DateUtils.beforeDate(new Date(), 1);
        String clearPerferDate = new SimpleDateFormat("yyyyMMdd").format(cDate);
        boolean flag = CacheUtil.getCache().setnx("elc_count_" + mall_no + clearPerferDate, "elc_count_" + mall_no + clearPerferDate);
        if (flag) {//缓存不存在该键，设置值
            CacheUtil.getCache().expire("elc_count_" + mall_no + clearPerferDate, 24 * 60 * 60);
        } else {//存在该键，说明已经生成
            return true;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mall_no", mall_no);
        Map<String, Object> elcBalanceSumMap = accountSubjectInfoMapper.selectTolElcBalance(map);
        if (elcBalanceSumMap != null) {
            EaccAccountBalanceSum eaccAccountBalanceSum = new EaccAccountBalanceSum();
            eaccAccountBalanceSum.setCount_date(clearPerferDate);
            eaccAccountBalanceSum.setOwn_balance(String.valueOf(elcBalanceSumMap.get("t_balance")));
            eaccAccountBalanceSum.setCreate_time(new Date());
            eaccAccountBalanceSumMapper.insert(eaccAccountBalanceSum);
        }
        return true;
    }

    /**
     * 生成账户出入金流水--资金流水表，platcust
     * 平台|时间|笔数
     * 平台|日期|时间|platcust|..金额|C-0，T-1
     *
     * @throws BusinessException
     */
    @Override
    public Boolean makeElcAccountTrans(String mall_no, String sub_subject, String clear_date) throws BusinessException {
        boolean flag = CacheUtil.getCache().setnx("acc_trans_" + mall_no + clear_date, "acc_trans_" + mall_no + clear_date);
        if (flag) {//缓存不存在该键，设置值
            //设置order_no键的有效时间为24小时，单位为秒
            CacheUtil.getCache().expire("acc_trans_" + mall_no + clear_date, 24 * 60 * 60);
        } else {//存在该键，说明已经生成过了文件
            return true;
        }
        logger.info("====================对账开始：start 生成电子账户出入金流水====================");
        BaseResponse baseResponse = new BaseResponse();
        String errorMsg = "";
        if (!Ssubject.EACCOUNT.getCode().equals(sub_subject)) {
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            errorMsg = String.format("传入的账户类型【%s】不是电子账户，请确认！", sub_subject);
            baseResponse.setRemsg(errorMsg);
            throw new BusinessException(baseResponse);
        }
        Date cDate = DateUtils.beforeDate(DateUtils.parseDate(clear_date), 1);
        String clearPerferDate = new SimpleDateFormat("yyyyMMdd").format(cDate);
        //查询汇总
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mall_no", mall_no);
        map.put("clear_date", clearPerferDate);
        map.put("sub_subject", sub_subject);
        Map<String, Object> tolMap = custEaccAccountamtlistMapper.selectTolEacclist(map);
        //查询明细
        List<Map<String, Object>> mapList = custEaccAccountamtlistMapper.selectDetailEacclist(map);
        mapList.add(0, tolMap);//汇总放到最前面

        try {
            String plat_ftp_name = sysParameterService.querySysParameter(mall_no, URLConfigUtil.PLAT_FTP_NAME);
            String fileDirName = "/home/fund/ftdm/billcheck/uploadFiles/" + "ACCOUNT_DATA/" + mall_no;
            String fileName = "ACCOUNT_DATA_" + clearPerferDate + ".txt";
            String ftp_path = "/home/ftp/" + plat_ftp_name + "/" + mall_no;
            File file = new File(fileDirName, fileName);
            if (file.exists()) { // 判断文件或文件夹是否存在
                System.out.println(file.getName() + " 已经存在");
            } else {
                try {
                    file.getParentFile().mkdirs();
                    file.createNewFile(); // 创建文件或者文件夹
                    System.out.println(file.getName() + " 创建成功");
                } catch (Exception e) {
                    logger.error("创建文件失败" + e);
                    e.printStackTrace();
                }
            }

            String addr = BillCheckPropertiesUtil.getVal("addr");
            String username = BillCheckPropertiesUtil.getVal("username");
            String password = BillCheckPropertiesUtil.getVal("password");
            com.sunyard.sunfintech.core.util.FtpUtils f = new com.sunyard.sunfintech.core.util.FtpUtils(addr, 21, username, password);
            if (f.open()) {
                logger.info("====================连接FTP成功！！！====================");
                PrintWriter pw = new PrintWriter(new FileWriter(file), true);
                String sign = "testSign1234";
                if (mapList.size() > 1) {
                    for (int i = 0; i < mapList.size(); i++) {
                        pw.println(mapList.get(i).get("accTransEacclistInfo").toString());
                    }
//                    pw.println(sign);
                }
                pw.println("ENDFLAG");
                logger.info("====================本地文件写成功====================");
                f.changeToParentDir();
                f.upload(fileDirName + "/" + fileName, "EACCOUNT_DATA_" + clearPerferDate + ".txt", ftp_path);
                f.close();
                logger.info("====================FTP文件写成功====================");
            } else {
                logger.info("====================连接FTP失败！！！====================");
            }
            logger.info("====================对账：end 生成电子账户出入金流水====================");
            return true;
        } catch (Exception e) {
            logger.error("写入ftp文件失败：" + e);
            return false;
        }
    }

    /**
     * 生成电子账户出入金流水--资金流水表 04，platcust
     * 平台|时间|笔数
     * 平台|日期|时间|platcust|..金额|C-0，T-1
     *
     * @throws BusinessException
     */
    @Override
    public Boolean makeAccountTrans(String mall_no, String clear_date) throws BusinessException {
//        boolean flag = CacheUtil.getCache().setnx("acc_trans_" + mall_no + clear_date, "acc_trans_" + mall_no + clear_date);
//        if (flag) {//缓存不存在该键，设置值
//            //设置order_no键的有效时间为24小时，单位为秒
//            CacheUtil.getCache().expire("acc_trans_" + mall_no + clear_date, 24 * 60 * 60);
//        } else {//存在该键，说明已经生成过了文件
//            return true;
//        }
        logger.info("====================对账开始：start 生成账户流水====================");
        BaseResponse baseResponse = new BaseResponse();
        String errorMsg = "";
//        Date cDate = DateUtils.beforeDate(DateUtils.parseDate(clear_date), 1);
//        String clearPerferDate = new SimpleDateFormat("yyyyMMdd").format(cDate);
        //查询汇总
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mall_no", mall_no);
        map.put("clear_date", clear_date);
        Map<String, Object> tolMap = custEaccAccountamtlistMapper.selectTollist(map);
        //查询明细
        List<Map<String, Object>> mapList = custEaccAccountamtlistMapper.selectDetaillist(map);
        mapList.add(0, tolMap);//汇总放到最前面

        try {
            String plat_ftp_name = sysParameterService.querySysParameter(mall_no, URLConfigUtil.PLAT_FTP_NAME);
            String fileDirName = "/home/fund/ftdm/billcheck/uploadFiles/" + "EACCOUNT_DATA/" + mall_no;
            String fileName = "EACCOUNT_DATA_" + clear_date + ".txt";
            String ftp_path = "/home/ftp/" + plat_ftp_name + "/" + mall_no;
            File file = new File(fileDirName, fileName);
            if (file.exists()) { // 判断文件或文件夹是否存在
                System.out.println(file.getName() + " 已经存在");
            } else {
                try {
                    file.getParentFile().mkdirs();
                    file.createNewFile(); // 创建文件或者文件夹
                    System.out.println(file.getName() + " 创建成功");
                } catch (Exception e) {
                    logger.error("创建文件失败" + e);
                    e.printStackTrace();
                }
            }
            StringBuilder sb=new StringBuilder("");
            if (mapList.size() > 1) {
                for (int i = 0; i < mapList.size(); i++) {
                    sb.append(mapList.get(i).get("accTransEacclistInfo").toString()).append("\n");
                }
            }
            sb.append("ENDFLAG");
            FileUtils.writeStringToFile(file, sb.toString(), "utf-8", false);
            logger.info("文件写入成功！");

            String addr = BillCheckPropertiesUtil.getVal("addr");
            String username = BillCheckPropertiesUtil.getVal("username");
            String password = BillCheckPropertiesUtil.getVal("password");
            String port = BillCheckPropertiesUtil.getVal("port");
            SFTPUtils ftp = new SFTPUtils(addr, port, username, password);
            logger.info(String.format("ip【%s】port【%s】username【%s】password【%s】", addr, port, username, password));
            if (ftp.connect()) {
                logger.info("====================连接SFTP成功！！！====================");
                if (ftp.uploadFile(ftp_path + "/", fileName, fileDirName + "/", fileName)) {
                    logger.info("====================SFTP文件写成功！！！====================");
                } else {
                    logger.info("====================SFTP文件写失败功！！！====================");
                }
                //关闭连接
                ftp.disconnect();
                return true;
            } else {
                logger.info("====================连接SFTP失败！！！====================");
                return false;
            }
        } catch (Exception e) {
            logger.error("写入ftp文件失败：" + e);
            return false;
        }
    }

    //获得前一天日期
    public static String getSpecifiedDayBefore(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }

    @Override
    public BaseResponse checkClearResult(SortingRequest sortingRequest) throws BusinessException {
        BaseResponse response = new BaseResponse();
        //查询是否属于集团
        List<String> plat_nos = custPlatPlatinfoMapper.getPlatNoByMallNo(sortingRequest.getMall_no());
        if (plat_nos.size() > 1) {
            for (String plat_no : plat_nos) {
                //清算记录
                ClearResultExample example = new ClearResultExample();
                ClearResultExample.Criteria criteria = example.createCriteria();
                criteria.andPlat_noEqualTo(plat_no);
                criteria.andClear_dateEqualTo(DateUtils.parseDate(sortingRequest.getClear_date()));
                List<ClearResult> clearResults = custClearResultMapper.selectByExample(example);
                for (ClearResult clearResult : clearResults) {
                    BaseResponse baseResponse = isClearable(clearResult, response);
                    if (BusinessMsg.FAIL.equals(baseResponse.getRecode())) throw new BusinessException(baseResponse);
                }
            }
        } else {
            //清算记录
            ClearResultExample example = new ClearResultExample();
            ClearResultExample.Criteria criteria = example.createCriteria();
            criteria.andPlat_noEqualTo(sortingRequest.getMall_no());
            criteria.andClear_dateEqualTo(DateUtils.parseDate(sortingRequest.getClear_date()));
            List<ClearResult> clearResults = custClearResultMapper.selectByExample(example);
            for (ClearResult clearResult : clearResults) {
                BaseResponse baseResponse = isClearable(clearResult, response);
//                if(BusinessMsg.FAIL.equals(baseResponse.getRecode())) return  baseResponse;
                if (BusinessMsg.FAIL.equals(baseResponse.getRecode())) throw new BusinessException(baseResponse);
                ;
            }
        }

        return response;
    }

    /**
     * 判断当前状态是否可以清算
     *
     * @param clearResult
     * @param response
     * @return
     */
    private BaseResponse isClearable(ClearResult clearResult, BaseResponse response) {
        if (clearResult == null) {
            response.setRecode(BusinessMsg.FAIL);
            response.setRemsg("没找到对账记录（clear_reuslt中无记录）");
            return response;
        }
        if (!LiquidationStatus.UNCHECK.getCode().equals(clearResult.getLiquidation())) {
            response.setRecode(BusinessMsg.FAIL);
            response.setRemsg("清算状态为【liquidation:" + clearResult.getLiquidation() + "】,不能重复清算");
            return response;
        }
        if (ClearStatus.UNCHECK.getCode().equals(clearResult.getClear_status())) {
            response.setRecode(BusinessMsg.FAIL);
            response.setRemsg("未对账，清算前请先对账");
            return response;
        } else if (ClearStatus.CHECK_ERROR.getCode().equals(clearResult.getClear_status())) {
            response.setRecode(BusinessMsg.FAIL);
            response.setRemsg("对账异常，请处理完异常账单再清算");
            return response;
        }
        return response;
    }

    @Override
    public BaseResponse requestSortingFile(final SortingRequest sortingRequest) throws BusinessException {
        BaseResponse baseResponse;
        logger.info("=================================清分==调用清算进行生成文件================================");
        PlatPaycodeExample example = new PlatPaycodeExample();
        PlatPaycodeExample.Criteria criteria = example.createCriteria();
        criteria.andIs_transactionEqualTo(Constants.ENABLED);
        criteria.andPlat_noEqualTo(sortingRequest.getMall_no());
        List<PlatPaycode> list = platPaycodeMapper.selectByExample(example);
        CacheUtil.getCache().del(REDISKEY_SYS_PARAMETER + sortingRequest.getMall_no() + sortingRequest.getClear_date());
        CacheUtil.getCache().increment(REDISKEY_SYS_PARAMETER + sortingRequest.getMall_no() + sortingRequest.getClear_date(), list.size());
        baseResponse = clearService.doClear(sortingRequest.getMall_no(), sortingRequest.getClear_date());
        if (baseResponse == null) {
            logger.info("=================================baseResponse返回null================================");
        } else {
            logger.info("=================================清分==调用清算进行生成文件=清算返回信息==" + baseResponse.getRecode() + "====" + baseResponse.getRemsg() + "=========================");
        }

        if (BusinessMsg.FAIL.equals(baseResponse.getRecode())) {
            logger.info("==================清算异常==============");
            throw new BusinessException(baseResponse);
        }
        return baseResponse;
    }

//    public void getReadyFile(String mall_no,String clear_date){
//        try {
//            logger.info("====================本地结束文件写成功====================");
//            String addr = BillCheckPropertiesUtil.getVal("addr");
//            String username = BillCheckPropertiesUtil.getVal("username");
//            String password = BillCheckPropertiesUtil.getVal("password");
//            com.sunyard.sunfintech.core.util.FtpUtils f = new com.sunyard.sunfintech.core.util.FtpUtils(addr, 21, username, password);
//            if (f.open()) {
//                String fileName_ready = "/home/fund/ftdm/clearResult/uploadFiles/" + mall_no + "/" + clear_date + "/"+ "ready";
//                logger.info("====================连接FTP成功！！！====================");
////                PrintWriter pw=new PrintWriter(fileName_ready);
////                pw.close();
//                File file = new File(fileName_ready);
//                FileUtils.writeStringToFile(file, "", "utf-8", false);
//                logger.info("====================本地结束文件写成功====================");
//                logger.info("====================结束文件====================");
//                f.upload(fileName_ready, "ready", mall_no + "/" + clear_date);
//                logger.info("====================FTP文件写成功====================");
//            } else {
//                logger.info("====================连接FTP失败！！！====================");
//            }
//            logger.info("====================清算：end 生成清算结束文件====================");
//        } catch (IOException e) {
//            logger.error("生成清算结束文件异常", e);
//        }
//        //        }
//
//        logger.info("=================================清分==生成清分文件=======end=========================");
//    }

    public Boolean checkBankByElc(String clearPerferDate) throws BusinessException {
//        boolean flag = CacheUtil.getCache().setnx("bank_billcheck_"+clearPerferDate,"bank_billcheck_"+clearPerferDate);
//        if(flag){//缓存不存在该键，设置值
//            //设置order_no键的有效时间为24小时，单位为秒
//            CacheUtil.getCache().expire("bank_billcheck_"+clearPerferDate,24*60*60);
//        }else{//存在该键，说明已经生成过了文件
//            return true;
//        }
        logger.info("************开始下载行方对账文件************");
        String addr = BillCheckPropertiesUtil.getVal("addr_Bank");
        String username = BillCheckPropertiesUtil.getVal("username_Bank");
        String password = BillCheckPropertiesUtil.getVal("password_Bank");
        String subFileName = BillCheckPropertiesUtil.getVal("sub_file_name");
        String ftpDirPath = BillCheckPropertiesUtil.getVal("ftpDirPath") + clearPerferDate + "/";
        String fileDirName = "/home/fund/ftdm/billcheck/bankClearFiles/";
        String fileName = subFileName + clearPerferDate + ".txt";
        File file = new File(fileDirName, fileName);
        if (file.exists()) { // 判断文件或文件夹是否存在
            logger.info("====================" + file.getName() + "已经存在====================");
        } else {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile(); // 创建文件或者文件夹
                logger.info("====================" + file.getName() + "创建成功====================");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        com.sunyard.sunfintech.core.util.FtpUtils f = new com.sunyard.sunfintech.core.util.FtpUtils(addr, 21, username, password);
        if (f.open()) {
            logger.info("====================连接行方FTP成功！！！====================");
            f.get(ftpDirPath + fileName, fileDirName + fileName);
            logger.info("====================行方FTP文件下载成功====================");
        } else {
            logger.info("====================连接行方FTP失败！！！====================");
        }
        // [0:电子账号，1：当日余额，2：当天是否动账,Y/N]

        BigDecimal bankBalanceSum = BigDecimal.ZERO;
        List<EaccClearCheckInfo> eaccClearCheckInfos = readTxtFile(fileDirName + fileName, clearPerferDate, bankBalanceSum);
        List<EaccClearCheckInfo> subEaccClearCheckInfos = new ArrayList<EaccClearCheckInfo>();
        int pointsDataLimit = 200;//限制条数
//        if (eaccClearCheckInfos.size() > 0) {
//            int part = eaccClearCheckInfos.size()/pointsDataLimit;//分批数
//            logger.info("共有 ： "+eaccClearCheckInfos.size()+"条，！"+" 分为 ："+part+"批");
//            for (int i = 0; i < part; i++) {
//                subEaccClearCheckInfos = eaccClearCheckInfos.subList(0,pointsDataLimit);
//                eaccClearCheckInfos.subList(0,pointsDataLimit).clear();
//                custEaccClearCheckInfoMapper.insertMore(subEaccClearCheckInfos);
//            }
//            if(!eaccClearCheckInfos.isEmpty()){
//                custEaccClearCheckInfoMapper.insertMore(subEaccClearCheckInfos);//插入最后剩下的数据
//            }
//        }else{
//            logger.info("从行内下载电子账户文件内容为空");
//            return false;
//        }
//        logger.info("***********储存行方电子账户每日数据成功***********");
        int y = 1;
        if (eaccClearCheckInfos.size() > 0) {
            for (int i = 1; i < eaccClearCheckInfos.size(); i++) {
                if (i == 1) {
                    subEaccClearCheckInfos.add(eaccClearCheckInfos.get(i - 1));
                }
                subEaccClearCheckInfos.add(eaccClearCheckInfos.get(i));
                y++;
                if ((i + 1) / (pointsDataLimit) == 0) {
                    custEaccClearCheckInfoMapper.insertMore(subEaccClearCheckInfos);
                    subEaccClearCheckInfos.clear();
                }
                if (i == eaccClearCheckInfos.size() - 1) {
                    logger.info("最后有剩余...");
                    custEaccClearCheckInfoMapper.insertMore(subEaccClearCheckInfos);
                }
            }
        } else {
            logger.info("从行内下载电子账户文件内容为空");
            return false;
        }
        logger.info("***********储存行方电子账户每日数据成功***********" + y + "...");

        //删除前一个星期值
        Date toDate = DateUtils.beforeDate(new Date(), 7);
        String toClearPerferDate = new SimpleDateFormat("yyyyMMdd").format(toDate);
        EaccClearCheckInfoExample ecExample = new EaccClearCheckInfoExample();
        EaccClearCheckInfoExample.Criteria ecCheckinfoIa = ecExample.createCriteria();
        ecCheckinfoIa.andClear_dateEqualTo(toClearPerferDate);
        eaccClearCheckInfoMapper.deleteByExample(ecExample);

        //更新行方每日数据
        EaccAccountBalanceSum eaccAccountBalanceSum = new EaccAccountBalanceSum();
        EaccAccountBalanceSumExample eaccAccountBalanceSumExample = new EaccAccountBalanceSumExample();
        EaccAccountBalanceSumExample.Criteria criteriaSum = eaccAccountBalanceSumExample.createCriteria();
        criteriaSum.andCount_dateEqualTo(clearPerferDate);
        eaccAccountBalanceSum.setOther_balance(String.valueOf(bankBalanceSum));
        eaccAccountBalanceSumMapper.updateByExampleSelective(eaccAccountBalanceSum, eaccAccountBalanceSumExample);
        logger.info("***********更新行方电子账户每日汇总数据成功***********");

        //查询出昨日所有动账的电子账户
        EaccClearCheckInfoExample eaccClearCheckInfoExample = new EaccClearCheckInfoExample();
        EaccClearCheckInfoExample.Criteria c = eaccClearCheckInfoExample.createCriteria();
        c.andIs_activeEqualTo("Y");
        c.andClear_dateEqualTo(clearPerferDate);
        List<EaccClearCheckInfo> eaccClearCheckInfoList = eaccClearCheckInfoMapper.selectByExample(eaccClearCheckInfoExample);

        //查询出昨日所有动账的电子账户
        EaccClearSubjectInfoExample eaccClearSubjectInfoExample = new EaccClearSubjectInfoExample();
        EaccClearSubjectInfoExample.Criteria esCriteria = eaccClearSubjectInfoExample.createCriteria();
        esCriteria.andClear_dateEqualTo(clearPerferDate);
        List<EaccClearSubjectInfo> eaccClearSubjectInfos = eaccClearSubjectInfoMapper.selectByExample(eaccClearSubjectInfoExample);

        EaccClearErrorInfo eaccClearErrorInfo = null;
        List<EaccClearErrorInfo> eaccClearErrorInfos = new ArrayList<EaccClearErrorInfo>();
        int i;
        for (EaccClearCheckInfo eaccClearCheckInfo : eaccClearCheckInfoList) {
            i = 0;
            for (EaccClearSubjectInfo eaccClearSubjectInfo : eaccClearSubjectInfos) {
                if (eaccClearCheckInfo.getEaccount().equals(eaccClearSubjectInfo.getEaccount())) {
                    if (new BigDecimal(eaccClearCheckInfo.getBalance()).compareTo(new BigDecimal(eaccClearSubjectInfo.getT_balance())) != 0) {
                        eaccClearErrorInfo = new EaccClearErrorInfo();
                        eaccClearErrorInfo.setClear_date(clearPerferDate);
                        eaccClearErrorInfo.setEaccount(eaccClearCheckInfo.getEaccount());
                        eaccClearErrorInfo.setOwn_Amt(eaccClearSubjectInfo.getT_balance());//我方电子账户余额
                        eaccClearErrorInfo.setBank_Amt(eaccClearCheckInfo.getBalance());//行方电子账户余额
                        eaccClearErrorInfo.setError_msg("我方电子账户与行方电子账户余额不一致");
                        eaccClearErrorInfo.setCreate_time(new Date());
                        eaccClearErrorInfos.add(eaccClearErrorInfo);
                    }
                    break;
                }
                i++;
            }
            if (i == eaccClearSubjectInfos.size()) {
                EaccUserinfoExample eaccUserinfoExample = new EaccUserinfoExample();
                EaccUserinfoExample.Criteria ca = eaccUserinfoExample.createCriteria();
                ca.andEaccountEqualTo(eaccClearCheckInfo.getEaccount());
                ca.andEnabledEqualTo(Constants.ENABLED);
                List<EaccUserinfo> eaccUserinfos = eaccUserinfoMapper.selectByExample(eaccUserinfoExample);
                if (eaccUserinfos != null && eaccUserinfos.size() > 0) {
                    AccountSubjectInfoExample accountSubjectInfoExample = new AccountSubjectInfoExample();
                    AccountSubjectInfoExample.Criteria acia = accountSubjectInfoExample.createCriteria();
                    acia.andSubjectEqualTo(Tsubject.CASH.getCode());
                    acia.andPlatcustEqualTo(eaccUserinfos.get(0).getMallcust());
                    acia.andSub_subjectEqualTo(Ssubject.EACCOUNT.getCode());
                    acia.andEnabledEqualTo(Constants.ENABLED);
                    List<AccountSubjectInfo> accountSubjectInfos = accSubjectInfoMapper.selectByExample(accountSubjectInfoExample);
                    if (accountSubjectInfos != null && accountSubjectInfos.size() > 0) {
                        eaccClearErrorInfo = new EaccClearErrorInfo();
                        eaccClearErrorInfo.setClear_date(clearPerferDate);
                        eaccClearErrorInfo.setBank_Amt(eaccClearCheckInfo.getBalance());
                        eaccClearErrorInfo.setOwn_Amt(String.valueOf(accountSubjectInfos.get(0).getT_balance()));
                        eaccClearErrorInfo.setEaccount(eaccClearCheckInfo.getEaccount());
                        eaccClearErrorInfo.setError_msg("行方有该电子账户变动，我方无该电子账户变动");
                        eaccClearErrorInfo.setCreate_time(new Date());
                        eaccClearErrorInfos.add(eaccClearErrorInfo);
                    }
                } else {
                    eaccClearErrorInfo = new EaccClearErrorInfo();
                    eaccClearErrorInfo.setClear_date(clearPerferDate);
                    eaccClearErrorInfo.setBank_Amt(eaccClearCheckInfo.getBalance());
                    eaccClearErrorInfo.setEaccount(eaccClearCheckInfo.getEaccount());
                    eaccClearErrorInfo.setError_msg("行方有该电子账户，我方无该电子账户");
                    eaccClearErrorInfo.setCreate_time(new Date());
                    eaccClearErrorInfos.add(eaccClearErrorInfo);
                }
            }
        }

        for (EaccClearSubjectInfo eaccClearSubjectInfo : eaccClearSubjectInfos) {
            i = 0;
            for (EaccClearCheckInfo eaccClearCheckInfo : eaccClearCheckInfoList) {
                if (eaccClearSubjectInfo.getEaccount().equals(eaccClearCheckInfo.getEaccount())) {
                    break;
                }
                i++;
            }
            if (i == eaccClearCheckInfoList.size()) {//内循环遍历结束
                EaccClearCheckInfoExample eClearCheckInfoExample = new EaccClearCheckInfoExample();
                EaccClearCheckInfoExample.Criteria cc = eClearCheckInfoExample.createCriteria();
                cc.andClear_dateEqualTo(clearPerferDate);
                cc.andEaccountEqualTo(eaccClearSubjectInfo.getEaccount());
                List<EaccClearCheckInfo> elearCheckInfoList = eaccClearCheckInfoMapper.selectByExample(eClearCheckInfoExample);
                if (elearCheckInfoList != null && elearCheckInfoList.size() > 0) {
                    eaccClearErrorInfo = new EaccClearErrorInfo();
                    eaccClearErrorInfo.setClear_date(clearPerferDate);
                    eaccClearErrorInfo.setEaccount(eaccClearSubjectInfo.getEaccount());
                    eaccClearErrorInfo.setOwn_Amt(eaccClearSubjectInfo.getT_balance());
                    eaccClearErrorInfo.setBank_Amt(elearCheckInfoList.get(0).getBalance());
                    eaccClearErrorInfo.setError_msg("我方有该电子账户变动，行方无该电子账户变动");
                    eaccClearErrorInfo.setCreate_time(new Date());
                    eaccClearErrorInfos.add(eaccClearErrorInfo);
                } else {
                    eaccClearErrorInfo = new EaccClearErrorInfo();
                    eaccClearErrorInfo.setClear_date(clearPerferDate);
                    eaccClearErrorInfo.setEaccount(eaccClearSubjectInfo.getEaccount());
                    eaccClearErrorInfo.setOwn_Amt(eaccClearSubjectInfo.getT_balance());
                    eaccClearErrorInfo.setError_msg("我方有该电子账户，行方无该电子账户");
                    eaccClearErrorInfo.setCreate_time(new Date());
                    eaccClearErrorInfos.add(eaccClearErrorInfo);
                }
            }
        }

        if (eaccClearErrorInfos.size() > 0) {
            logger.info("***********批量插入差错表***********");
            custEaccClearErrorInfoMapper.insertMore(eaccClearErrorInfos);
        }

        return true;
    }

    @Override
    public List<ClearResult> getClearResult(String mall_no, String clear_date) {
        ClearResultExample example = new ClearResultExample();
        ClearResultExample.Criteria criteria = example.createCriteria();
        criteria.andPlat_noEqualTo(mall_no);
        criteria.andClear_dateEqualTo(DateUtils.parseDate(clear_date));
        List<ClearResult> clearResults = custClearResultMapper.selectByExample(example);
        return clearResults;
    }

    @Override
    public void updateClearResultById(ClearResult clearResult) {
        try {
            clearResultMapper.updateByPrimaryKeySelective(clearResult);
        } catch (Exception e) {
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION);
        }
    }

    @Override
    public List<ClearCheckinfoEmx> queryClearCheckinfoEmx(ClearCheckinfoEmx emx) {
        List<ClearCheckinfoEmx> clearCheckinfoEmxList;
        try {
            ClearCheckinfoEmxExample checkinfoEmxExample = new ClearCheckinfoEmxExample();
            ClearCheckinfoEmxExample.Criteria criteriaCheckinfo = checkinfoEmxExample.createCriteria();
            criteriaCheckinfo.andClear_dateEqualTo(emx.getClear_date());
            criteriaCheckinfo.andPlat_noEqualTo(emx.getPlat_no());
            criteriaCheckinfo.andPayment_plat_noEqualTo(emx.getPayment_plat_no());
            clearCheckinfoEmxList = clearCheckinfoEmxMapper.selectByExample(checkinfoEmxExample);
        } catch (Exception e) {
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION);
        }
        return clearCheckinfoEmxList;
    }

    @Override
    public void addClearInfoMx(HashMap<String, Object> totalMap) {
        try {
            custClearResultMapper.addClearInfoMx(totalMap);
        } catch (Exception e) {
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION);
        }
    }

    @Override
    public void deleteClearInfoByDate(String clear_date, String pay_code, String plat_no) {
        try {
            custClearResultMapper.deleteClearInfoByDate(clear_date, pay_code, plat_no);
        } catch (Exception e) {
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION);
        }
    }

    @Override
    public void addClearInfoLIst(List<HashMap<String, Object>> cutList) {
        try {
            custClearResultMapper.addClearInfoLIst(cutList);
        } catch (Exception e) {
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION);
        }
    }

    @Override
    public List<ClearCheckinfoEmxlist> queryEmxListCharge(ClearCheckinfoEmxlist emxlist) {
        List<ClearCheckinfoEmxlist> clearCheckinfoEmxlists;
        try {
            ClearCheckinfoEmxlistExample emxlistExample = new ClearCheckinfoEmxlistExample();
            ClearCheckinfoEmxlistExample.Criteria criteriaEmxlist = emxlistExample.createCriteria();
            criteriaEmxlist.andClear_dateEqualTo(emxlist.getClear_date());//对账日期
            criteriaEmxlist.andPlat_noEqualTo(emxlist.getPlat_no());//平台号
            criteriaEmxlist.andPay_codeEqualTo(emxlist.getPay_code());//支付编号
            criteriaEmxlist.andOrder_typeNotEqualTo("R");//!R充值
            clearCheckinfoEmxlists = clearCheckinfoEmxlistMapper.selectByExample(emxlistExample);
        } catch (Exception e) {
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION);
        }
        return clearCheckinfoEmxlists;
    }

    @Override
    public List<ClearCheckinfoEmxlist> queryEmxListWithdraw(ClearCheckinfoEmxlist emxlist) {
        List<ClearCheckinfoEmxlist> clearCheckinfoEmxlists;
        try {
            ClearCheckinfoEmxlistExample emxlistExample = new ClearCheckinfoEmxlistExample();
            ClearCheckinfoEmxlistExample.Criteria criteriaEmxlist = emxlistExample.createCriteria();
            criteriaEmxlist.andClear_dateEqualTo(emxlist.getClear_date());//对账日期
            criteriaEmxlist.andPlat_noEqualTo(emxlist.getPlat_no());//平台号
            criteriaEmxlist.andPay_codeEqualTo(emxlist.getPay_code());//支付编号
            criteriaEmxlist.andOrder_typeEqualTo("R");//!R充值
            clearCheckinfoEmxlists = clearCheckinfoEmxlistMapper.selectByExample(emxlistExample);
        } catch (Exception e) {
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION);
        }
        return clearCheckinfoEmxlists;
    }

    @Override
    public List<ClearCheckError> selectClearCheckError(ClearCheckError checkError) {
        List<ClearCheckError> clearCheckErrors;
        try {
            ClearCheckErrorExample example = new ClearCheckErrorExample();
            ClearCheckErrorExample.Criteria criteria = example.createCriteria();
            criteria.andClear_dateEqualTo(checkError.getClear_date());
            criteria.andPlat_noEqualTo(checkError.getPlat_no());
            criteria.andSerial_idEqualTo(checkError.getSerial_id());
            criteria.andPay_codeEqualTo(checkError.getPay_code());
            if (checkError.getTrans_amt() != null) {
                criteria.andTrans_amtEqualTo(checkError.getTrans_amt());
            }
            if (checkError.getPay_amt() != null) {
                criteria.andPay_amtEqualTo(checkError.getPay_amt());
            }
            clearCheckErrors = clearCheckErrorMapper.selectByExample(example);
        } catch (Exception e) {
            logger.error(e);
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION);
        }
        return clearCheckErrors;
    }

    @Override
    public void insertClearError(ClearCheckError clearCheckError) {
        try {
            clearCheckErrorMapper.insert(clearCheckError);
        } catch (Exception e) {
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION);
        }
    }

    @Override
    public List<ClearResult> selectUnCheckedClearResult(ClearResult clearResult, List<String> platNos) {
        List<ClearResult> clearResults;
        try {
            ClearResultExample example = new ClearResultExample();
            ClearResultExample.Criteria criteria = example.createCriteria();
            criteria.andPlat_noIn(platNos);
            criteria.andClear_dateEqualTo(clearResult.getClear_date());
            criteria.andClear_statusNotEqualTo(ClearStatus.CHECKED.getCode());
            clearResults = clearResultMapper.selectByExample(example);
        } catch (Exception e) {
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION);
        }
        return clearResults;
    }

    @Override
    public List<EaccAccountamtlist> selectAmtlist(EaccAccountamtlist eaccAccountamtlist) {
        List<EaccAccountamtlist> eaccAccountamtlists;
        try {
            EaccAccountamtlistExample example = new EaccAccountamtlistExample();
            EaccAccountamtlistExample.Criteria criteria = example.createCriteria();
            criteria.andTrans_serialEqualTo(eaccAccountamtlist.getTrans_serial());
            criteria.andAmt_typeEqualTo(AmtType.EXPENSIVE.getCode());
            if (eaccAccountamtlist.getOppo_sub_subject() != null) {
                criteria.andOppo_sub_subjectEqualTo(eaccAccountamtlist.getOppo_sub_subject());
            }
            eaccAccountamtlists = eaccAccountamtlistMapper.selectByExample(example);
        } catch (Exception e) {
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION);
        }
        return eaccAccountamtlists;
    }

    @Override
    public boolean billCheckNotify(String mall_no, String clear_date) {
        try {
            logger.info("对账完成后通知中间平台传递的参数，mall_no={}, clear_date={}", mall_no, clear_date);
            //DB配置URL
            String url = sysParameterService.querySysParameter(mall_no, URLConfigUtil.BILL_CHECK_READY_NOTIFY_URL);
            Map<String, Object> map = new HashMap<>();
            map.put("mall_no", mall_no);
            map.put("clear_date", clear_date);
            BaseHttpResponse baseHttpResponse = HttpClientUtils.httpPostRequest(url, map);
            logger.info("对账完成后通知中间平台返回结果：" + (baseHttpResponse != null ? baseHttpResponse.toString() : "空"));
            if (baseHttpResponse == null) {
                throw new BusinessException("-1000", "对账完成后通知中间平台返回结果为空");
            } else if (baseHttpResponse.getStatusCode() == HttpStatusCode.OK.value()) {
                String result = baseHttpResponse.getEntityString();
                Map<String, Object> result_map = JSONObject.parseObject(result, Map.class);
                if (!result_map.isEmpty() && "0000".equals(result_map.get("ret_code"))) {
                    logger.info("对账完成后通知中间平台成功。mall_no={}, clear_date={}", mall_no, clear_date);
                    return true;
                } else {
                    throw new BusinessException("-1001", "对账完成后通知中间平台返回结果不是成功状态");
                }
            } else {
                throw new BusinessException("-1002", "对账完成后通知中间平台返回状态码不是200");
            }
        } catch (Exception e) {
            logger.error("对账完成后通知中间平台发生异常，task会重试，异常信息：", e);
            //插入对账通知异常表bill_check_notify_retry (id,mall_no,clear_date,notify_status,create_time,update_time);
            insertBillCheckNotifyRetry(mall_no, clear_date, BillCheckNotifyStatus.INIT.getCode(), new Date());
            return false;
        }
    }

    @Override
    public int updateBillCheckNotifyRetry(BillCheckNotifyRetry billCheckNotifyRetry) {
        try {
            return billCheckNotifyRetryMapper.updateByPrimaryKeySelective(billCheckNotifyRetry);
        } catch (Exception e) {
            logger.error("updateBillCheckNotifyRetry throw exception. mall_no=" + billCheckNotifyRetry.getMall_no() + ",clear_date=" + billCheckNotifyRetry.getClear_date(), e);
            return 0;
        }
    }

    @Override
    public List<BillCheckNotifyRetry> findBillCheckNotifyRetry(String notify_status) {
        try {
            BillCheckNotifyRetryExample billCheckNotifyRetryExample = new BillCheckNotifyRetryExample();
            billCheckNotifyRetryExample.createCriteria().andNotify_statusEqualTo(notify_status);
            return billCheckNotifyRetryMapper.selectByExample(billCheckNotifyRetryExample);
        } catch (Exception e) {
            logger.error("findBillCheckNotifyRetry throw exception. notify_status=" + notify_status, e);
            return null;
        }
    }

    public static List<EaccClearCheckInfo> readTxtFile(String filePath, String clearPerferDate, BigDecimal bankBalanceSum) {
        List<EaccClearCheckInfo> eaccClearCheckInfos = new ArrayList<EaccClearCheckInfo>();
        EaccClearCheckInfo eaccClearCheckInfo = null;
        try {
            String encoding = "GBK";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    String[] ars = lineTxt.split("\\,");
                    eaccClearCheckInfo = new EaccClearCheckInfo();
                    eaccClearCheckInfo.setEaccount(ars[0]);
                    eaccClearCheckInfo.setBalance(ars[1]);
                    eaccClearCheckInfo.setIs_active(ars[2]);
                    eaccClearCheckInfo.setClear_date(clearPerferDate);
                    eaccClearCheckInfo.setCreate_time(new Date());
                    bankBalanceSum = bankBalanceSum.add(new BigDecimal(ars[1]));
                    eaccClearCheckInfos.add(eaccClearCheckInfo);
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return eaccClearCheckInfos;
    }

    public static String readTxtFile(String filePath) {
        String s = "";
        try {
            String encoding = "GBK";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    s = s + lineTxt;
                    s = s + ";";
                }
                read.close();
                return s;
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return s;
    }

    private void insertBillCheckNotifyRetry(String mall_no, String clear_date, String notify_status, Date create_date) {
        BillCheckNotifyRetry billCheckNotifyRetry = new BillCheckNotifyRetry();
        try {
            BillCheckNotifyRetryExample billCheckNotifyRetryExample = new BillCheckNotifyRetryExample();
            billCheckNotifyRetryExample.createCriteria().andClear_dateEqualTo(clear_date).andMall_noEqualTo(mall_no);
            List<BillCheckNotifyRetry> list = billCheckNotifyRetryMapper.selectByExample(billCheckNotifyRetryExample);
            if (CollectionUtils.isEmpty(list)) {
                billCheckNotifyRetry.setMall_no(mall_no);
                billCheckNotifyRetry.setClear_date(clear_date);
                billCheckNotifyRetry.setNotify_status(notify_status);
                billCheckNotifyRetry.setCreate_time(create_date);
                billCheckNotifyRetry.setUpdate_time(create_date);
                int result = billCheckNotifyRetryMapper.insertSelective(billCheckNotifyRetry);
                logger.info("插入对账通知重试表结果：result={}", result);
            } else {
                logger.info("对账通知重试表数据已经存在：clear_date={}， mall_no={}", clear_date, mall_no);
            }
        } catch (Exception e) {
            logger.error("insertBillCheckNotifyRetry throw exception. object = " + JSON.toJSONString(billCheckNotifyRetry), e);
        }
    }

    /**
     * 充值提现对账
     * @param clearDate
     * @return
     */
    public BaseResponse checkCTList(Date clearDate) {
        logger.info("【充值提现对账流水】开始查询流水");
        Boolean isError = false;
        String strDate = DateUtils.format(clearDate, DateUtils.DEF_FORMAT_NOTIME);
        List<String> trans_codes = new ArrayList<>();
        trans_codes.add(TransConsts.QUICK_CONFIRM_CODE);
        trans_codes.add(TransConsts.GATEWAY_RECHARGE_CODE);
        trans_codes.add(TransConsts.COLLECTION_CODE);
        trans_codes.add(TransConsts.BORROW_REPAY_CODE);
        trans_codes.add(TransConsts.INVEST_CHANGE_CODE);
        //充值流水
        List<EaccAccountamtlist> rechargeList = ctCheckService.queryCTListByAccountDate(clearDate, trans_codes);
        trans_codes.clear();
        trans_codes.add(TransConsts.BATCH_WITHDRAW_CODE);
        trans_codes.add(TransConsts.WITHDRAW_AFFIRM_CODE);
        //提现流水
        List<EaccAccountamtlist> withdrawList = withdrawlistFilter(ctCheckService.queryCTListByAccountDate(clearDate, trans_codes));

        //充值记录
        List<RwRecharge> recharges = ctCheckService.querySuccessRecharge(strDate);
        //提现记录
        List<RwWithdraw> withdraws = ctCheckService.querySuccessRwWithdraw(strDate);
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
                        ,strDate,eaccAccountamtlist.getTrans_code(),eaccAccountamtlist.getPlat_no(),eaccAccountamtlist.getPay_code(),eaccAccountamtlist.getAmt());
            } else {
                rechargeMap.put(eaccAccountamtlist.getTrans_serial(), false);
            }
        }
        for (RwRecharge recharge : recharges) {
            Boolean  isExsits = rechargeMap.get(recharge.getTrans_serial());
            if(isExsits == null){
                List<EaccAccountamtlist> eaccAccountamtlists = ctCheckService.queryAmtListByTransSerial(recharge.getTrans_serial());
                if(eaccAccountamtlists.size() == 0){
                    isError = true;
                    logger.info("【内部对账】没有资金流水");
                    insertClearCheckError("{内部对账-充值}无资金流水",recharge.getTrans_serial(),recharge.getOrder_no()
                            ,strDate,recharge.getTrans_code(),recharge.getPlat_no(),recharge.getPay_code(),recharge.getTrans_amt());
                }else {
                    logger.info("【内部对账】有资金流水，修改account_date");
                    eaccAccountamtlists.forEach(eaccAccountamtlist -> {
                        EaccAccountamtlist amtlist = new EaccAccountamtlist();
                        amtlist.setId(eaccAccountamtlist.getId());
                        amtlist.setAccount_date(DateUtils.parseDate(recharge.getPayment_date(), DateUtils.DEF_FORMAT_NOTIME));
                        ctCheckService.updateAccountDate(amtlist);
                    });
                }
            }else if(isExsits){
                isError = true;
                insertClearCheckError("{内部对账-充值}订单重复",recharge.getTrans_serial(),recharge.getOrder_no()
                        ,strDate,recharge.getTrans_code(),recharge.getPlat_no(),recharge.getPay_code(),recharge.getTrans_amt());
            }else {
                rechargeMap.put(recharge.getTrans_serial(),true);//设置为已对账
            }
        }
        for(Map.Entry<String,Boolean> entry:rechargeMap.entrySet()){
            if(!entry.getValue()){
                isError = true;
                insertClearCheckError("{内部对账-充值}资金表有订单，订单表无记录",entry.getKey(),""
                        ,strDate,"C",null,null,null);
            }
        }
        logger.info("【充值提现对账流水】提现对账");
        Map<String,Boolean> withdrawsMap = new HashMap<>();
        for (EaccAccountamtlist eaccAccountamtlist : withdrawList) {
            Boolean isExsits = withdrawsMap.get(eaccAccountamtlist.getTrans_serial());
            if (isExsits != null) {//已存在，重复记账
                isError = true;
                insertClearCheckError("{内部对账-提现}资金流水重复记账",eaccAccountamtlist.getTrans_serial(),eaccAccountamtlist.getOrder_no()
                        ,strDate,"T",eaccAccountamtlist.getPlat_no(),eaccAccountamtlist.getPay_code(),eaccAccountamtlist.getAmt());
            } else {
                withdrawsMap.put(eaccAccountamtlist.getTrans_serial(), false);
            }
        }
        for (RwWithdraw withdraw : withdraws) {
            Boolean  isExsits = withdrawsMap.get(withdraw.getTrans_serial());
            if(isExsits == null){
                List<EaccAccountamtlist> eaccAccountamtlists = ctCheckService.queryAmtListByTransSerial(withdraw.getTrans_serial());
                if(eaccAccountamtlists.size() == 0){
                    isError = true;
                    insertClearCheckError("{内部对账-提现}有提现订单无资金流水",withdraw.getTrans_serial(),withdraw.getOrder_no()
                            ,strDate,"T",withdraw.getPlat_no(),withdraw.getPay_code(),withdraw.getOut_amt());
                }else {
                    logger.info("【内部对账】有流水记录，修改account_date");
                    eaccAccountamtlists.forEach(eaccAccountamtlist -> {
                        EaccAccountamtlist amtlist = new EaccAccountamtlist();
                        amtlist.setId(eaccAccountamtlist.getId());
                        amtlist.setAccount_date(DateUtils.parseDate(withdraw.getPayment_date(), DateUtils.DEF_FORMAT_NOTIME));
                        ctCheckService.updateAccountDate(amtlist);
                    });
                }
            }else if(isExsits){
                isError = true;
                insertClearCheckError("{内部对账-提现}订单重复",withdraw.getTrans_serial(),withdraw.getOrder_no()
                        ,strDate,"T",withdraw.getPlat_no(),withdraw.getPay_code(),withdraw.getOut_amt());
            }else {
                withdrawsMap.put(withdraw.getTrans_serial(),true);//设置为已对账
            }
        }
        for(Map.Entry<String,Boolean> entry:withdrawsMap.entrySet()){
            if(!entry.getValue()){
                isError = true;
                insertClearCheckError("{内部对账-提现}资金表有订单，订单表无记录",entry.getKey(),""
                        ,strDate,"T",null,null,null);
            }
        }
        BaseResponse baseResponse = new BaseResponse();
        if (isError) {
            baseResponse.setRecode(BusinessMsg.FAIL);
            //给管理员发短信
            StringBuilder sb = new StringBuilder();
            if("BOB".equals(deployEnvironment)){
                sb.append("【北京银行】");
            }
            sb.append("【内部对账失败】请关注clear_check_error表中的错误数据");
            //sendMsgService.sendErrorToAdmin(sb.toString(), null);
        }else {
            baseResponse.setRecode(BusinessMsg.SUCCESS);
        }
        return baseResponse;
    }

    private void insertClearCheckError(String remark,String trans_serial,String order_no,String clear_date,String type,
                                       String plat_no,String pay_code,BigDecimal trans_amt){
        logger.info("【充值提现对账流水】"+remark+"，trans_serial:" + trans_serial);
        ClearCheckError clearCheckError = new ClearCheckError();
        clearCheckError.setSerial_id(trans_serial);
        clearCheckError.setClear_date(clear_date);
        clearCheckError.setOrder_no(order_no);
        clearCheckError.setRemark(remark);
        clearCheckError.setStatus(ClearStatus.UNCHECK.getCode());
        clearCheckError.setClear_code(type);
        clearCheckError.setPlat_no(plat_no);
        clearCheckError.setPay_code(pay_code);
        clearCheckError.setTrans_amt(trans_amt);
        clearCheckError.setClear_name("内部对账");
        ctCheckService.insertClearCheckError(clearCheckError);
    }

    private List<EaccAccountamtlist> withdrawlistFilter(List<EaccAccountamtlist> withdrawOriData){
        logger.info("【提现资金流水处理】过滤前提现list大小："+withdrawOriData.size());
        //将所有提现数据groupby
        Map<String, List<EaccAccountamtlist>> withdrawListMap = withdrawOriData.stream()
                .collect(Collectors.groupingBy(EaccAccountamtlist::getTrans_serial));
        //过滤出包含中间账户扣款的trans_serial,只要有中间账户减款的交易，认定为提现成功，与提现表比对。
        for (String trans_serial:withdrawListMap.keySet()){
             EaccAccountamtlist es =withdrawListMap.get(trans_serial).stream()
                .filter(e -> TransConsts.PLAT_MADDLE_ACCOUNTS_NAME.equals(e.getTrans_code()))
                .findAny()
                .orElse(null);
             if (es==null){
                 withdrawListMap.remove(trans_serial);
             }
        }
        //重新组装withdrawList，取用户账户（01，02）转到中间账户那条记录进行比较。
        List<EaccAccountamtlist> withdrawList = new ArrayList<>();
        withdrawListMap.values().forEach(eaccAccountamtlists1 -> eaccAccountamtlists1.stream()
                .filter(eaccAccountamtlist -> AmtType.EXPENSIVE.getCode().equals(eaccAccountamtlist.getAmt_type())
                        &&(Ssubject.INVEST.getCode().equals(eaccAccountamtlist.getSub_subject())
                        ||Ssubject.FINANCING.getCode().equals(eaccAccountamtlist.getSub_subject()))
                        && Tsubject.CASH.getCode().equals(eaccAccountamtlist.getSubject())
                ).forEach(eaccAccountamtlist -> withdrawList.add(eaccAccountamtlist))
        );
        logger.info("【提现资金流水处理】过滤后提现list大小："+withdrawList.size());
        return withdrawList;
    }
}
