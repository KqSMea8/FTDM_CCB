package com.sunyard.sunfintech.billcheck.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.sunyard.sunfintech.billcheck.handler.AccountUpdateHandler;
import com.sunyard.sunfintech.billcheck.handler.AllAccountHandler;
import com.sunyard.sunfintech.billcheck.handler.FlowUpdateHandler;
import com.sunyard.sunfintech.billcheck.handler.QueryListHandler;
import com.sunyard.sunfintech.billcheck.handler.node.AccountListNode;
import com.sunyard.sunfintech.billcheck.handler.node.ChargeListNode;
import com.sunyard.sunfintech.billcheck.handler.node.TransListNode;
import com.sunyard.sunfintech.billcheck.model.ClearModel;
import com.sunyard.sunfintech.billcheck.model.bo.ClearAccount;
import com.sunyard.sunfintech.billcheck.modulepublic.SendMsgService;
import com.sunyard.sunfintech.billcheck.provider.IClearService;
import com.sunyard.sunfintech.billcheck.utils.BillCheckPropertiesUtil;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.threads.SingleThreadPool;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.HttpClientUtils;
import com.sunyard.sunfintech.core.util.URLConfigUtil;
import com.sunyard.sunfintech.custdao.mapper.*;
import com.sunyard.sunfintech.dao.entity.ClearResult;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import com.sunyard.sunfintech.dao.entity.PlatCardinfo;
import com.sunyard.sunfintech.dao.mapper.ClearResultMapper;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by djh on 2017/6/24.
 * 清算
 */
@Service(interfaceClass = IClearService.class)
@CacheConfig(cacheNames = "clearService")
@org.springframework.stereotype.Service
public class ClearServiceFor51 extends BaseServiceSimple implements IClearService {


    public static final List<String> advanceSubject = Arrays.asList("881","882","883","884","885","886","887","888","889","890");
    @Autowired
    private CustEaccAccountamtlistMapper custEaccAccountamtlistMapper;

    @Autowired
    private CustClearResultMapper custClearResultMapper;

    @Autowired
    private CustAccountSubjectInfoMapper custAccountSubjectInfoMapper;

    @Autowired
    private ClearResultMapper clearResultMapper;

    @Autowired
    private CustPlatCardinfoMapper custPlatCardinfoMapper;

    @Autowired
    private AccountClearService accountClearService;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    CustPlatPlatinfoMapper custPlatPlatinfoMapper;

    @Autowired
    ClearForMallService clearForMallService;

    @Autowired
    CheckClearForMallService checkClearForMallService;

    @Autowired
    SendMsgService sendMsgService;


    //private static final String REDISKEY_SYS_PARAMETER = Constants.CACHE_NAMESPACE + "billcheck:";

    public synchronized BaseResponse doClear(final String plat_no, final String clear_date) {

            //查询是否属于集团
            List<String> plat_nos = custPlatPlatinfoMapper.getPlatNoByMallNo(plat_no);
            if (plat_nos.size() > 1) {
                try {
                    BaseResponse checkClearResponse = checkClearForMallService.checkClear(plat_no,clear_date);
                    if(!BusinessMsg.SUCCESS.equals(checkClearResponse.getRecode())){
                        throw new BusinessException(checkClearResponse);//预清算失败
                    }
                    plat_nos.add(plat_no);
                    BaseResponse baseResponse = clearForMallService.doClearForMall(plat_nos, clear_date);
                    if(baseResponse.getRecode().equals(BusinessMsg.SUCCESS)){
                        return baseResponse;
                    }else {
                        throw new BusinessException(baseResponse);//清算失败
                    }
                } catch (Exception e) {
                    CacheUtil.getCache().del(Constants.REDISKEY_SYS_PARAMETER + plat_no);
                    logger.error("清算发生异常，请关注处理。plat_no="+plat_no+",clear_date="+clear_date,e);
                    sendMsgService.sendErrorToAdmin("清算异常,清算日期："+clear_date,plat_no);

                    Map<String,Object> params = new HashMap<>();
                    params.put("mall_no",plat_no);
                    params.put("clear_date",clear_date);
                    params.put("type", SortingType.PRE_CLEAR_EXCEPTION.getCode());
                    String url = sysParameterService.querySysParameter(plat_no, "ftdm_web_url");
                    //BillCheckPropertiesUtil.getVal("ftdm_web_url")+"notify/notify_clear_status";
                    try {
                        HttpClientUtils.httpPostRequest(url,params);
                    } catch (UnsupportedEncodingException e1) {}
                    throw e;
                }

            } else {
                return doClearForOthers(plat_no, clear_date);
            }

    }

    public BaseResponse clearAll(String plat_no) {
        ClearModel clearModel = new ClearModel(plat_no,DateUtils.format(new Date(),"yyyyMMdd"),null);
        long maxId = custAccountSubjectInfoMapper.getMaxId();
        QueryListHandler queryListHandler = new QueryListHandler(0,maxId,clearModel,AccountListNode.class);
        queryListHandler.asynExecute();
        logger.info("所有待清算账户数量："+queryListHandler.getAllElement());
        AllAccountHandler allAccountHandler = new AllAccountHandler(queryListHandler);
        allAccountHandler.asynExecute();
        logger.info("所有待清算账户数量11111："+queryListHandler.getAllElement());
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setRecode(BusinessMsg.SUCCESS);
        return baseResponse;
    }

    /**
     * 非集团清算
     *
     * @param plat_no    平台号
     * @param clear_date 清算日期
     * @return 清算结果
     */
    private BaseResponse doClearForOthers(final String plat_no, final String clear_date) {
        final ClearModel clearModel = new ClearModel(plat_no, clear_date, null);
        BaseResponse response = new BaseResponse();
        logger.info("【开始清算】：plat_no:" + plat_no + ",clear_date:" + clear_date );
        //清算记录,检查是否可清
        BaseResponse baseResponse = isClearable(clearModel, response);
        if (BusinessMsg.FAIL.equals(baseResponse.getRecode())) return baseResponse;

        //充值记录查询
        Map<String, Long> idScope = custEaccAccountamtlistMapper.getChargeScope(clearModel.getPlat_no(), clearModel.getParsedClear_date());
        QueryListHandler queryListHandler = new QueryListHandler(idScope.get("minId"), idScope.get("maxId"), clearModel, ChargeListNode.class);
        queryListHandler.asynExecute();//多线程异步执行
        logger.info("【充值记录】==========chargeList：" + queryListHandler.getAllElement() + "\n\n");
        final ConcurrentHashMap<String, ClearAccount> accounts = clearModel.getAccounts();
        int[] nodeState = queryListHandler.getNodeState();
        for (int i = 0; i < nodeState.length; i++) {
            List<EaccAccountamtlist> chargeList = queryListHandler.getNodes().get(i).getHandData();
            for (EaccAccountamtlist charge : chargeList) {
                clearModel.addAmtByPayCode(charge.getAmt(), charge.getPay_code());
                String unionKey = charge.getPlatcust() + "#" + charge.getSubject() + "#" + charge.getSub_subject();
                ClearAccount account = accounts.get(unionKey);
                if (account == null) {
                    account = new ClearAccount(charge.getPlatcust(), charge.getSubject(), charge.getSub_subject());
                    account.addAmt(charge.getAmt());
                    accounts.put(unionKey, account);
                } else {
                    account.addAmt(charge.getAmt());
                }
            }
        }

        //多次清算
        while (clearModel.getAccounts().size() > 0) {
            tryClear(clearModel,idScope.get("minId"));
        }

        //流水清算状态修改,状态2批量修改，状态1只能单条修改，状态0忽略
        clearFlows(clearModel);

        logger.info("【异步生成清算文件】==========\n\n");
        //生成内部清算文件
        SingleThreadPool.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    createClearFile(clearModel);
                } catch (Exception e) {
                    logger.error("生成清算文件异常clearService_createClearFileInside", e);
                }
            }
        });
        response.setRecode(BusinessMsg.SUCCESS);
        return response;
    }

    private void tryClear(ClearModel clearModel,long minId) {
        clearModel.incTryTimes();
        //clearModel.incClearTimes();
        ConcurrentHashMap<String, ClearAccount> accounts = clearModel.getAccounts();
        QueryListHandler transListHandler = clearModel.getTransListHandler();
        if (transListHandler == null) {
            //初次清算，记录太多
            Long maxId = custEaccAccountamtlistMapper.getTransMaxId();
            transListHandler = new QueryListHandler(minId, maxId, clearModel, TransListNode.class);
            transListHandler.asynExecute();//多线程异步执行
            logger.info("【转账交易数量】==========chargeList：" + transListHandler.getAllElement() + "\n\n");
            int[] nodeState = transListHandler.getNodeState();
            for (int i = 0; i < nodeState.length; i++) {
                List<EaccAccountamtlist> transList = transListHandler.getNodes().get(i).getHandData();
                clearTrans(accounts, transList);
            }
        } else {
            //当没有再次清算没有新流水，还有账户未清算，不可能清算成功了，存入清算差错账户（clear_account_error）
            long start = clearModel.getMaxTransId();
            if (start == -1 || clearModel.getTryTimes() > ClearModel.MAX_TRY_TIME) {
                logger.info("【没法清算的账户】"+accounts);
                for (Map.Entry<String, ClearAccount> entry : accounts.entrySet()) {
                    accounts.remove(entry.getKey());
                    accountClearService.insertClearCheckError(entry.getValue(), clearModel);
                }
                return;
            } else {
                List<EaccAccountamtlist> eaccList = custEaccAccountamtlistMapper.getTransferList(clearModel.getPlat_no(), start + 1, start + 1000000);
                if (eaccList.size() == 0 && accounts.size() != 0) {
                    for (Map.Entry<String, ClearAccount> entry : accounts.entrySet()) {
                        accounts.remove(entry.getKey());
                        accountClearService.insertClearCheckError(entry.getValue(), clearModel);
                    }
                }
                clearModel.getTransListNew().addAll(eaccList);//加入新查询流水，2017/12/20
                clearTrans(accounts, eaccList);
            }
        }
        //清算资金
        AccountUpdateHandler accountUpdateHandler = new AccountUpdateHandler(clearModel);
        accountUpdateHandler.asynExecute();
    }

    private void clearTrans(ConcurrentHashMap<String, ClearAccount> accounts, List<EaccAccountamtlist> eaccList) {
        for (EaccAccountamtlist trans : eaccList) {
            //初始化自身和对手账户
            String selfKey = trans.getPlatcust() + "#" + trans.getSubject() + "#" + trans.getSub_subject();
            ClearAccount selfAccount = accounts.get(selfKey);
            if (selfAccount == null) {
                selfAccount = new ClearAccount(trans.getPlatcust(), trans.getSubject(), trans.getSub_subject());
                accounts.put(selfKey, selfAccount);
            }
            String oppoKey = trans.getOppo_platcust() + "#" + trans.getOppo_subject() + "#" + trans.getOppo_sub_subject();
            ClearAccount oppoAccount = accounts.get(oppoKey);
            if (oppoAccount == null) {
                oppoAccount = new ClearAccount(trans.getOppo_platcust(), trans.getOppo_subject(), trans.getOppo_sub_subject());
                accounts.put(oppoKey, oppoAccount);
            }
            BigDecimal waitSwitchAmt = trans.getAmt().subtract(trans.getSwitch_amt());//需清算余额
            calClearAmt(trans, selfAccount, oppoAccount, waitSwitchAmt);
        }
    }

    /**
     * 更新清算状态为成功
     *
     * @param clearResults 清算记录
     */
    private void updateClearResult(List<ClearResult> clearResults) {
        for (ClearResult clearResult : clearResults) {
            ClearResult newClearResult = new ClearResult();
            newClearResult.setPid(clearResult.getPid());
            newClearResult.setLiquidation(LiquidationStatus.CHECKED.getCode());
            newClearResult.setLiquidation_recordtime(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            clearResultMapper.updateByPrimaryKeySelective(newClearResult);
        }
    }

    /**
     * 判断当前状态是否可以清算
     *
     * @param response   返回对象
     * @param clearModel 清算对象
     * @return BaseResponse
     */
    private BaseResponse isClearable(ClearModel clearModel, BaseResponse response) {
        List<ClearResult> clearResults = custClearResultMapper.getByUnionKey(clearModel.getPlat_no(), clearModel.getParsedClear_date());
        Map<String, BigDecimal> payCodes = clearModel.getPayCodeCharges();
        for (ClearResult clearResult : clearResults) {
            payCodes.put(clearResult.getPay_code(), BigDecimal.ZERO);
        }

        logger.info("【清算记录】==========clearResult：" + clearResults.size() + "\n\n");
        if (clearResults.size() == 0) {
            response.setRecode(BusinessMsg.FAIL);
            response.setRemsg("没找到对账记录（clear_reuslt中无记录）");
            return response;
        }
        for (ClearResult clearResult : clearResults) {
            if (!LiquidationStatus.UNCHECK.getCode().equals(clearResult.getLiquidation())) {
                response.setRecode(BusinessMsg.FAIL);
                response.setRemsg("清算状态为【liquidation:" + clearResult.getLiquidation() + "】,不能重复清算");
                return response;
            }
            if (ClearStatus.UNCHECK.getCode().equals(clearResult.getClear_status())) {
                response.setRecode(BusinessMsg.FAIL);
                response.setRemsg("未对账，清算前请先对账！");
                return response;
            } else if (ClearStatus.CHECK_ERROR.getCode().equals(clearResult.getClear_status())) {
                response.setRecode(BusinessMsg.FAIL);
                response.setRemsg("对账异常，请处理完异常账单再清算");
                return response;
            }
        }
        //无事务，直接设置为已清算
        updateClearResult(clearResults);
        return response;
    }

    /**
     * 计算清算金额
     *
     * @param trans         资金流水
     * @param selfAccount   自身账户
     * @param oppoAccount   对手账户
     * @param waitSwitchAmt 待清算金额
     */
    private void calClearAmt(EaccAccountamtlist trans, ClearAccount selfAccount, ClearAccount oppoAccount, BigDecimal waitSwitchAmt) {
        //转账
        if (AmtType.EXPENSIVE.getCode().equals(trans.getAmt_type())) {
            if (selfAccount.getN_balance().compareTo(BigDecimal.ZERO) == 0) {
                return;//如果没钱了不继续清算
            }
            if (selfAccount.getN_balance().compareTo(waitSwitchAmt) >= 0) {
                selfAccount.subtractAmt(waitSwitchAmt);
                oppoAccount.addAmt(waitSwitchAmt);
                trans.setSwitch_state(SwitchState.ALLSWITCH.getCode());
                trans.setSwitch_amt(trans.getAmt());
            } else {
                BigDecimal remainAmt = selfAccount.getN_balance();//剩余可清金额
                selfAccount.subtractAmt(remainAmt);
                oppoAccount.addAmt(remainAmt);
                trans.setSwitch_state(SwitchState.PARTSWITCH.getCode());
                trans.setSwitch_amt(trans.getSwitch_amt().add(remainAmt));
            }
        } else {
            //冻结
            if (AmtType.FROZEN.getCode().equals(trans.getAmt_type())) {
                if (selfAccount.getN_balance().compareTo(BigDecimal.ZERO) == 0) {
                    return;//如果没钱了不继续清算
                }
                if (selfAccount.getN_balance().compareTo(waitSwitchAmt) >= 0) {
                    selfAccount.freeze(waitSwitchAmt);
                    trans.setSwitch_state(SwitchState.ALLSWITCH.getCode());
                    trans.setSwitch_amt(trans.getAmt());
                } else {
                    BigDecimal remainAmt = selfAccount.getN_balance();//剩余可清金额
                    selfAccount.freeze(remainAmt);
                    trans.setSwitch_state(SwitchState.PARTSWITCH.getCode());
                    trans.setSwitch_amt(trans.getSwitch_amt().add(remainAmt));
                }
            } else {//解冻
                if (selfAccount.getF_balance().compareTo(BigDecimal.ZERO) <= 0) {
                    return;//如果冻结金额不够了不继续解冻
                }
                if (selfAccount.getF_balance().compareTo(waitSwitchAmt) >= 0) {
                    selfAccount.unfreeze(waitSwitchAmt);
                    trans.setSwitch_state(SwitchState.ALLSWITCH.getCode());
                    trans.setSwitch_amt(trans.getAmt());
                } else {
                    BigDecimal remain_amt = selfAccount.getF_balance();
                    selfAccount.unfreeze(selfAccount.getF_balance());
                    trans.setSwitch_state(SwitchState.PARTSWITCH.getCode());
                    trans.setSwitch_amt(trans.getSwitch_amt().add(remain_amt));
                }
            }
        }
    }

    /**
     * 清算充值和转账流水
     *
     * @param clearModel 清算对象
     */
    private void clearFlows(ClearModel clearModel) {
        //流水清算状态修改,状态2批量修改，状态1只能单条修改，状态0忽略
        FlowUpdateHandler handler = new FlowUpdateHandler(clearModel);
        handler.asynExecute();
    }

    private void uploadFilesIn(String plat_no, String clear_date, StringBuffer sb, String fileName, String pay_code) {
        try {
            String plat_ftp_name = sysParameterService.querySysParameter(plat_no, URLConfigUtil.PLAT_FTP_NAME);
            // + plat_no + "/" + clear_date + "/"
            String pathName = "/home/fund/ftdm/clearResult/" + plat_no + "/" + clear_date + "/" + fileName;
            String ftp_path = "/home/ftp/" + plat_ftp_name + "/" + plat_no + "/" + clear_date;
            File file = new File(pathName);
            FileUtils.writeStringToFile(file, sb.toString(), "utf-8", false);
            logger.info("====================本地文件写成功====================");

            String addr = BillCheckPropertiesUtil.getVal("addr");
            String username = BillCheckPropertiesUtil.getVal("username");
            String password = BillCheckPropertiesUtil.getVal("password");
            com.sunyard.sunfintech.core.util.FtpUtils f = new com.sunyard.sunfintech.core.util.FtpUtils(addr, 21, username, password);
            if (f.open()) {
                logger.info("====================连接FTP成功！！！====================");
                f.changeToParentDir();
//                f.upload(pathName, plat_no + "_"+pay_code + "_" + clear_date + "_in.txt", ftp_path);
                f.upload(pathName, fileName, ftp_path);
                f.close();
                logger.info("=================== ====================");
            } else {
                logger.info("====================连接FTP失败！！！====================");
            }
            logger.info("====================清算：end 生成清算文件====================");
            logger.info("=====生成ready文件=====");
            getReadyFile(plat_no, clear_date);
            logger.info("=====生成ready文件成功=====");
        } catch (IOException e) {
            logger.error("生成清算文件异常", e);
        }
    }


    /**
     * 生成清算对账文件,只区分平台、垫付
     *
     * @param clearModel 清算对象
     */
    public void createClearFile(ClearModel clearModel) {
        //BaseResponse baseResponse = new BaseResponse();
        final String card_type = "01";//存管账户
        PlatCardinfo platCardinfo = custPlatCardinfoMapper.getByUnionKey(clearModel.getPlat_no(), card_type);

        BigDecimal advanceAmt = clearModel.getAdvance();//总垫付金额
        Map<String, BigDecimal> paycodeCharges = clearModel.getPayCodeCharges();
        for (Map.Entry<String, BigDecimal> entry : paycodeCharges.entrySet()) {
            StringBuffer sb = new StringBuffer();
            String pay_code = entry.getKey();
            BigDecimal paycodeAmt = entry.getValue();//渠道充值
            BigDecimal paycodeAdvance;//渠道垫付
            if (paycodeAmt.compareTo(advanceAmt) >= 0) {
                paycodeAdvance = advanceAmt;
                advanceAmt = BigDecimal.ZERO;
                paycodeAmt = paycodeAmt.subtract(paycodeAdvance);
            } else {
                paycodeAdvance = paycodeAmt;
                paycodeAmt = BigDecimal.ZERO;
                advanceAmt = advanceAmt.subtract(paycodeAdvance);
            }

            //添加标题头
            sb.append(clearModel.getPlat_no()).append("|").append(pay_code).append("|").append(entry.getValue()).append("\n");
            sb.append(platCardinfo.getCard_name()).append("|").append("1").append("|")
                    .append(platCardinfo.getCard_no()).append("|").append(paycodeAmt).append("\n");
            sb.append("|3||").append(paycodeAdvance);
            String fileName = clearModel.getPlat_no() + "_" + pay_code + ".txt";
//            try {
//                FileUtils.write(new File("D:/test/"+fileName),sb.toString(),"utf-8");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            //写文件
            uploadFilesIn(clearModel.getPlat_no(), clearModel.getClear_date(), sb, fileName, pay_code);
        }

//        baseResponse.setRecode(BusinessMsg.SUCCESS);
//        baseResponse.setRemsg("生成清算文件成功");
    }

    @Override
    public void getReadyFile(String mall_no, String clear_date) {
        try {
            logger.info("====================本地结束文件写===start=================");
            String plat_ftp_name = sysParameterService.querySysParameter(mall_no, URLConfigUtil.PLAT_FTP_NAME);
            String addr = BillCheckPropertiesUtil.getVal("addr");
            String username = BillCheckPropertiesUtil.getVal("username");
            String password = BillCheckPropertiesUtil.getVal("password");
            com.sunyard.sunfintech.core.util.FtpUtils f = new com.sunyard.sunfintech.core.util.FtpUtils(addr, 21, username, password);
            String fileName_ready = "/home/fund/ftdm/clearResult/uploadFiles/" + mall_no + "/" + clear_date + "/" + "ready";
            String ftp_path = "/home/ftp/" + plat_ftp_name + "/" + mall_no + "/" + clear_date;
            File file = new File(fileName_ready);
            FileUtils.writeStringToFile(file, "", "utf-8", false);
            if (f.open()) {
                logger.info("====================连接FTP成功！！！====================");
                logger.info("====================本地结束文件写成功====================");
                logger.info("====================结束文件====================");
                f.changeToParentDir();
                f.upload(fileName_ready, "ready", ftp_path);
                f.close();
//                f.upload(fileName_ready, "ready", mall_no + "/" + clear_date);
                logger.info("====================FTP文件写成功====================");
            } else {
                logger.info("====================连接FTP失败！！！====================");
            }
            logger.info("====================清算：end 生成清算结束文件====================");
        } catch (IOException e) {
            logger.error("生成清算结束文件异常", e);
        }
        //        }

        logger.info("=================================清分==生成清分文件=======end=========================");
    }

    @Override
    public BaseResponse createClearFileInside(String plat_no, String clear_date, String pay_code) {
        return null;
    }

    public static void main(String[] args) {

        ClearAccount clearAccount = new ClearAccount("01", "02", "03");
        clearAccount.addAmt(BigDecimal.TEN);
        ClearAccount clearAccount2 = new ClearAccount("02", "02", "03");
        EaccAccountamtlist ea = new EaccAccountamtlist();
        ea.setAmt(BigDecimal.valueOf(12));
        ea.setAmt_type("1");
        ea.setPlat_no("p1");
        ea.setPlatcust("01");
        ea.setSubject("02");
        ea.setSub_subject("03");
        ea.setOppo_platcust("02");
        ea.setOppo_subject("02");
        ea.setOppo_sub_subject("03");
        ea.setSwitch_amt(BigDecimal.ZERO);

        new ClearServiceFor51().calClearAmt(ea, clearAccount, clearAccount2, BigDecimal.valueOf(12));
    }

}
