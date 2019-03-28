package com.sunyard.sunfintech.billcheck.service;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.billcheck.model.bo.ClearAccount;
import com.sunyard.sunfintech.billcheck.model.bo.ClearFileNotifyRequest;
import com.sunyard.sunfintech.billcheck.model.bo.MallClearAccount;
import com.sunyard.sunfintech.billcheck.provider.IAccountTransferService;
import com.sunyard.sunfintech.billcheck.utils.BillCheckPropertiesUtil;
import com.sunyard.sunfintech.billcheck.utils.ListUtils;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.threads.SingleThreadPool;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.core.util.URLConfigUtil;
import com.sunyard.sunfintech.custdao.mapper.*;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.ClearResultMapper;
import com.sunyard.sunfintech.pub.model.NotifyData;
import com.sunyard.sunfintech.pub.provider.INotifyService;
import com.sunyard.sunfintech.pub.provider.ISendMsgService;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by djh on 2017/6/24.
 */
@org.springframework.stereotype.Service
public class ClearForMallService extends BaseServiceSimple {

    @Autowired
    private CustAccountSubjectInfoMapper custAccountSubjectInfoMapper;

    @Autowired
    private CustEaccAccountamtlistMapper custEaccAccountamtlistMapper;

    @Autowired
    private CustClearResultMapper custClearResultMapper;

    @Autowired
    private ClearResultMapper clearResultMapper;

    @Autowired
    CustPlatCardinfoMapper custPlatCardinfoMapper;

    @Autowired
    CustPlatPaycodeMapper custPlatPaycodeMapper;

    @Autowired
    CustEaccUserinfoMapper custEaccUserinfoMapper;

    @Autowired
    CustPlatPlatinfoMapper custPlatPlatinfoMapper;

    @Autowired
    private IAccountTransferService accountTransferService;

    @Autowired
    private ISysParameterService sysParameterService;
    @Autowired
    private ISendMsgService sendMsgService;

    private static final String REDISKEY_SYS_PARAMETER = Constants.CACHE_NAMESPACE + "billcheck:";
    @Autowired
    private INotifyService notifyService;


    /**
     * 集团清算
     *
     * @param plat_nos 平台号
     * @param clear_date 清算日期
     * @return
     */
    @Transactional
    public BaseResponse doClearForMall(final List<String> plat_nos, final String clear_date) {
        BaseResponse response = new BaseResponse();
        logger.info("【集团】开始清算：plat_nos:" + plat_nos + ",clear_date:" + clear_date);
        List<ClearResult> clearResults = new ArrayList<>();
        for (int i=0;i<plat_nos.size()-1;i++) {//不检查集团
            String plat_no = plat_nos.get(i);
            List<ClearResult> clearResultList = custClearResultMapper.getByUnionKey(plat_no,
                    DateUtils.parseDate(clear_date, "yyyyMMdd"));
            BaseResponse baseResponse = isClearable(clearResultList,response) ;
            if(BusinessMsg.FAIL.equals(baseResponse.getRecode())) return  baseResponse;
            clearResults.addAll(clearResultList);//后面需要修改清算状态的，先暂存在list中
        }
        //final AccountSubjectInfo bankAccount = getBankAccount(mall_no);
        //充值记录
        List<EaccAccountamtlist> chargeList = custEaccAccountamtlistMapper.getMallChargeList(plat_nos, DateUtils.parseDate(clear_date, "yyyyMMdd"));
        Map<String,Map<String, MallClearAccount>> payCodeAccount = new HashMap<>();//每个渠道一个账户体系
        for(ClearResult clearResult:clearResults){
            Map<String, MallClearAccount> accounts = new HashMap<>();
            payCodeAccount.put(clearResult.getPay_code(),accounts);
        }
        for (EaccAccountamtlist charge : chargeList) {
            String unionKey = charge.getPlat_no() + "#" + charge.getPlatcust() + "#" + charge.getSubject() + "#" + charge.getSub_subject();
            Map<String, MallClearAccount> accounts = payCodeAccount.get(charge.getPay_code());
            if(accounts == null){
                throw new BusinessException("===存在未配置渠道,"+charge.getPay_code());
            }
            MallClearAccount account = accounts.get(unionKey);
            if (account == null) {
                account = new MallClearAccount(charge.getPlat_no(), charge.getPlatcust(), charge.getSubject(), charge.getSub_subject());
                account.addAmt(charge.getAmt());
                accounts.put(unionKey, account);
            } else {
                account.addAmt(charge.getAmt());
            }
        }

        //资金流水
        List<EaccAccountamtlist> eaccList = custEaccAccountamtlistMapper.getMallTransferList(plat_nos, DateUtils.parseDate(clear_date, "yyyyMMdd"));
        for(Map.Entry<String,Map<String, MallClearAccount>> accountEntry:payCodeAccount.entrySet()){
            Map<String, MallClearAccount> accounts = accountEntry.getValue();
            if(accounts == null || accounts.size()==0){
                continue;
            }
            for (EaccAccountamtlist trans : eaccList) {
                //初始化自身和对手账户
                String selfKey = trans.getPlat_no() + "#" + trans.getPlatcust() + "#" + trans.getSubject() + "#" + trans.getSub_subject();
                MallClearAccount selfAccount = accounts.get(selfKey);
                if (selfAccount == null) {
                    continue;//该条流水直接跳过
                }
                String oppo_plat_no = ListUtils.ifNull(trans.getOppo_plat_no(), trans.getPlat_no());
                String oppoKey = oppo_plat_no + "#" + trans.getOppo_platcust() + "#" + trans.getOppo_subject() + "#" + trans.getOppo_sub_subject();
                MallClearAccount oppoAccount = accounts.get(oppoKey);
                if (oppoAccount == null) {
                    oppoAccount = new MallClearAccount(oppo_plat_no, trans.getOppo_platcust(), trans.getOppo_subject(), trans.getOppo_sub_subject());
                    accounts.put(oppoKey, oppoAccount);
                }
                BigDecimal waitSwitchAmt = trans.getAmt().subtract(trans.getSwitch_amt());//需清算余额

                calClearAmt(trans,selfAccount,oppoAccount,waitSwitchAmt);
            }
            logger.info("【待清算账户及金额】========accounts："+accounts.toString());
        }

        logger.info("【资金流水】==========eaccList数量：" + eaccList.size() + "\n\n");
        for(Map.Entry<String,Map<String, MallClearAccount>> accountEntry:payCodeAccount.entrySet()) {
            Map<String, MallClearAccount> accounts = accountEntry.getValue();
            //在途清算
            clearFund(accounts, clear_date,accountEntry.getKey());
        }
        //流水清算状态修改,状态2批量修改，状态1只能单条修改，状态0忽略
        clearFlows(chargeList, eaccList);
        //修改清算状态为已清算
        for(ClearResult clearResult:clearResults){
            updateClearResult(clearResult);
        }
        logger.info("【异步生成外部清算文件】==========\n\n");
        //分平台生成对账文件
//        final Map<String, Map<String, MallClearAccount>> platMap = new HashMap<>();
//        for (String plat_no : plat_nos) {
//            Map<String, MallClearAccount> platAccounts = new HashMap<>();
//            for (Map.Entry<String, MallClearAccount> entry : accounts.entrySet()) {
//                if (plat_no.equals(entry.getValue().getPlat_no())) {
//                    platAccounts.put(entry.getKey(), entry.getValue());
//                }
//            }
//            platMap.put(plat_no, platAccounts);
//        }
        //异步生成外部清算文件
        SingleThreadPool.getThreadPool().execute(() -> {
            String path = "/home/fund/";
            //一个集团只生成一份文件
            try{
                for(Map.Entry<String,Map<String, MallClearAccount>> accountEntry:payCodeAccount.entrySet()) {
                    Map<String, MallClearAccount> accounts = accountEntry.getValue();
                    createClearFile(plat_nos.get(plat_nos.size() - 1), accounts, clear_date, accountEntry.getKey(), path);
                }
                //清算文件生成后通知中间业务平台
                clearFileNotify(plat_nos.get(plat_nos.size() - 1), clear_date);
            }catch(Exception e) {
                logger.error("生成清算文件异常clearService_createClearFile",e);
                sendMsgService.sendErrorToAdmin(plat_nos.get(plat_nos.size()-1),"生成外部清算文件异常"+clear_date);
            }

        });
        //生成内部清算文件
//        SingleThreadPool.getThreadPool().execute(new Runnable() {
//            @Override
//            public void run() {
//                String path = "/home/fund/";///home/fund/
//                for (Map.Entry<String, Map<String, MallClearAccount>> entry : platMap.entrySet()) {
//                    try {
//                        createClearFileInside(entry.getKey(), entry.getValue(), clear_date, null, path);
//                    }catch(Exception e) {
//                        logger.error("生成清算文件异常clearService_createClearFileInside",e);
//                        sendMsgService.sendErrorToAdmin(plat_nos.get(plat_nos.size()-1),"生成内部清算文件异常"+clear_date);
//
//                    }
//                }
//            }
//        });
        response.setRecode(BusinessMsg.SUCCESS);
        return response;
    }

    /**
     * 通知中间业务平台转账文件已经生成
     * 通知失败不处理，中间业务平台会定时获取该文件
     * @param plat_no
     * @param clear_date
     */
    private void clearFileNotify(String plat_no,String clear_date){
        try {
            String mallNo = custPlatPlatinfoMapper.getMallNoByPlatNo(plat_no);
            logger.info("==========通知中间业务平台plat_no={} clear_date={} 转账文件已生成",plat_no,clear_date);
            String clear_file_ready_notify_url = sysParameterService.querySysParameter(mallNo, "clear_file_ready_notify_url");
            logger.info("==========通知地址:" + clear_file_ready_notify_url);
            NotifyData notifyData=new NotifyData();
            notifyData.setMall_no(mallNo);
            notifyData.setNotifyUrl(clear_file_ready_notify_url);
            ClearFileNotifyRequest clearFileNotifyRequest = new ClearFileNotifyRequest();
            clearFileNotifyRequest.setMall_no(mallNo);
            clearFileNotifyRequest.setClear_date(clear_date);
            String json = JSON.toJSONString(clearFileNotifyRequest);
            notifyData.setNotifyContent(json);
            notifyService.addNotify(notifyData);
        }catch (Exception e){
            logger.error("==========通知中间业务平台plat_no={} clear_date={} 转账文件已生成异常",plat_no,clear_date,e);
        }
    }
    public BaseResponse createClearFileInside( String plat_no, String clear_date, String pay_code){
        BaseResponse baseResponse = new BaseResponse();
        BigDecimal totalAmt = BigDecimal.ZERO;
        StringBuffer sb = new StringBuffer();
        List<EaccAccountamtlist> list = custEaccAccountamtlistMapper.getClearRecord(plat_no, clear_date, pay_code);
        for (EaccAccountamtlist eacc : list) {
            totalAmt = totalAmt.add(eacc.getAmt());
            sb.append(eacc.getPlatcust()).append("|")
                    .append(eacc.getSubject()).append("|")
                    .append(eacc.getSub_subject()).append("|")
                    .append(eacc.getAmt()).append("\n");
        }
        //添加标题头
        sb.insert(0, plat_no + "|" + pay_code + "|" + totalAmt + "\n");
        String fileName = plat_no+"_"+pay_code+"_"+clear_date+"_in.txt";
        //写文件
        uploadFilesIn(plat_no, pay_code, sb,fileName ,pay_code);

        baseResponse.setRecode(BusinessMsg.SUCCESS);
        baseResponse.setRemsg("生成清算文件成功");
        return  baseResponse;
    }


    public BigDecimal getB(Map<String,MallClearAccount> map){
        BigDecimal t = BigDecimal.ZERO;
        for(String key:map.keySet()){
            t = t.add(map.get(key).getT_balance());
        }
        return  t;
    }

    /**
     * 更新清算状态为成功
     * @param clearResult 清算记录
     * @return 更新条数
     */
    private int updateClearResult(ClearResult clearResult){
        ClearResult newClearResult = new ClearResult();
        newClearResult.setPid(clearResult.getPid());
        newClearResult.setLiquidation(LiquidationStatus.CHECKED.getCode());
        newClearResult.setLiquidation_recordtime(DateUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        return clearResultMapper.updateByPrimaryKeySelective(newClearResult);
    }

    /**
     * 判断当前状态是否可以清算
     * @param clearResultList  清算列表
     * @param response 返回值
     * @return
     */
    private BaseResponse isClearable(List<ClearResult> clearResultList,BaseResponse response){
        for(ClearResult clearResult:clearResultList){
            if(clearResult == null){
                response.setRecode(BusinessMsg.FAIL);
                response.setRemsg("没找到对账记录（clear_reuslt中无记录）");
                return response;
            }
            if(!LiquidationStatus.UNCHECK.getCode().equals(clearResult.getLiquidation())){
                response.setRecode(BusinessMsg.FAIL);
                response.setRemsg("清算状态为【liquidation:"+clearResult.getLiquidation()+"】,不能重复清算");
                return response;
            }
            if (!ClearStatus.CHECK_CLEAR_SUC.getCode().equals(clearResult.getClear_status())) {
                response.setRecode(BusinessMsg.FAIL);
                response.setRemsg("对账状态异常，不能清算");
                return response;
            }
        }
        return  response;
    }
    /**
     * 计算清算金额
     * @param trans  资金流水
     * @param selfAccount  自身账户
     * @param oppoAccount  对手账户
     * @param waitSwitchAmt  待清算金额
     */
    private void calClearAmt(EaccAccountamtlist trans,ClearAccount selfAccount,ClearAccount oppoAccount,BigDecimal waitSwitchAmt){
        //转账
        if (AmtType.EXPENSIVE.getCode().equals(trans.getAmt_type())) {
            if(selfAccount.getN_balance().compareTo(BigDecimal.ZERO)==0){
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
                if(selfAccount.getN_balance().compareTo(BigDecimal.ZERO)==0){
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
                if(selfAccount.getF_balance().compareTo(BigDecimal.ZERO) <= 0){
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
     * @param chargeList 充值流水
     * @param eaccList   转账流水
     */
    private void clearFlows(List<EaccAccountamtlist> chargeList, List<EaccAccountamtlist> eaccList) {
        //流水清算状态修改,状态2批量修改，状态1只能单条修改，状态0忽略
        List<Long> allSwitch = new ArrayList<>();
        List<EaccAccountamtlist> partSwitch = new ArrayList<>();
        for (EaccAccountamtlist eacc : chargeList) {
            allSwitch.add(eacc.getId());
        }
        for (EaccAccountamtlist eacc : eaccList) {
            if (eacc.getSwitch_state().equals(SwitchState.ALLSWITCH.getCode())) {
                allSwitch.add(eacc.getId());
            } else if (eacc.getSwitch_state().equals(SwitchState.PARTSWITCH.getCode())) {
                partSwitch.add(eacc);
            }
        }

        logger.info("【流水清算状态修改】==========allSwitch：" + allSwitch.toString() + "\n\n");
        logger.info("【流水清算状态修改】==========partSwitch：" + partSwitch.toString() + "\n\n");

        if (allSwitch.size() > 0) {
            custEaccAccountamtlistMapper.batchUpdateAllSwitch(allSwitch);
            //TODO 分批更新
        }

        for (EaccAccountamtlist part : partSwitch) {
            custEaccAccountamtlistMapper.updatePartSwitch(part);
        }
    }

    /**
     * 单平台清算资金
     *
     * @param accounts  账户
     */
    private void clearFund(Map<String, MallClearAccount> accounts, String clear_date,String pay_code) {
        List<EaccAccountamtlist> cashList = new ArrayList<>();
        List<EaccAccountamtlist> frozenList = new ArrayList<>();
        for (Map.Entry<String, MallClearAccount> entry : accounts.entrySet()) {
            MallClearAccount account = entry.getValue();
            if (account.getN_balance().compareTo(BigDecimal.ZERO) > 0) {
                EaccAccountamtlist cashEacc = new EaccAccountamtlist();
                cashEacc.setPlat_no(account.getPlat_no());
                cashEacc.setAmt(account.getN_balance());
                cashEacc.setAccount_date(DateUtils.parseDate(clear_date,"yyyyMMdd"));
                cashEacc.setPlatcust(account.getPlatcust());
                cashEacc.setSub_subject(account.getSub_subject());
                cashEacc.setSubject(Tsubject.FLOAT.getCode());
                cashEacc.setOppo_platcust(account.getPlatcust());
                cashEacc.setOppo_subject(Tsubject.CASH.getCode());
                cashEacc.setOppo_sub_subject(account.getSub_subject());
                cashEacc.setTrans_date(DateUtils.format(new Date(), "yyyyMMdd"));
                cashEacc.setTrans_time(DateUtils.format(new Date(), "HHmmss"));
                cashEacc.setTrans_code(TransConsts.CLEAR_N_CODE);
                cashEacc.setTrans_name(TransConsts.CLEAR_N_NAME);
                cashEacc.setOrder_no("");
                cashEacc.setPay_code(pay_code);
                clearN_balance(cashEacc);
            }
            if (account.getF_balance().compareTo(BigDecimal.ZERO) > 0) {
                EaccAccountamtlist frozenEacc = new EaccAccountamtlist();
                frozenEacc.setPlat_no(account.getPlat_no());
                frozenEacc.setAmt(account.getF_balance());
                frozenEacc.setAccount_date(DateUtils.parseDate(clear_date,"yyyyMMdd"));
                frozenEacc.setPlatcust(account.getPlatcust());
                frozenEacc.setSub_subject(account.getSub_subject());
                frozenEacc.setSubject(Tsubject.FLOAT.getCode());
                frozenEacc.setOppo_platcust(account.getPlatcust());
                frozenEacc.setOppo_subject(Tsubject.CASH.getCode());
                frozenEacc.setOppo_sub_subject(account.getSub_subject());
                frozenEacc.setTrans_date(DateUtils.format(new Date(), "yyyyMMdd"));
                frozenEacc.setTrans_time(DateUtils.format(new Date(), "HHmmss"));
                frozenEacc.setTrans_code(TransConsts.CLEAR_F_CODE);
                frozenEacc.setTrans_name(TransConsts.CLEAR_F_NAME);
                frozenEacc.setOrder_no("");
                frozenEacc.setPay_code(pay_code);
                clearF_balance(frozenEacc);
            }
        }
        logger.info("【清算资金】==========frozenList：" + frozenList.toString() + "\n\n");
        logger.info("【清算资金】==========cashList：" + cashList.toString() + "\n\n");
    }


    private void clearN_balance(EaccAccountamtlist eaccAccountamtlist) {
        EaccAccountamtlist incomeAccount = new EaccAccountamtlist();
        try {
            eaccAccountamtlist.setSwitch_amt(eaccAccountamtlist.getAmt());
            eaccAccountamtlist.setSwitch_state(SwitchState.ALLSWITCH.getCode());
            BeanUtils.copyProperties(incomeAccount, eaccAccountamtlist);
            incomeAccount.setPlatcust(eaccAccountamtlist.getOppo_platcust());
            incomeAccount.setOppo_platcust(eaccAccountamtlist.getPlatcust());
            eaccAccountamtlist.setAmt_type(AmtType.EXPENSIVE.getCode());
            incomeAccount.setAmt_type(AmtType.INCOME.getCode());
            incomeAccount.setSub_subject(eaccAccountamtlist.getOppo_sub_subject());
            incomeAccount.setSubject(eaccAccountamtlist.getOppo_subject());
            incomeAccount.setOppo_subject(eaccAccountamtlist.getSubject());
            incomeAccount.setOppo_sub_subject(eaccAccountamtlist.getSub_subject());
            if(null == eaccAccountamtlist.getUpdate_by()) eaccAccountamtlist.setUpdate_by(SeqUtil.RANDOM_KEY);
            eaccAccountamtlist.setUpdate_time(new Date());
            logger.info("【转账】==========incomeAccount：" + incomeAccount.toString() + "\n\n");
            int flag = custAccountSubjectInfoMapper.substractAmtByUnionKeyNoNeg(eaccAccountamtlist);
            //logger.debug("【转账】==========incomeAccount：" + incomeAccount.toString() + "\n\n");
            if(flag != 1){
                throw new BusinessException("账户扣款异常："+eaccAccountamtlist);
            }
            if(null == incomeAccount.getUpdate_by()) incomeAccount.setUpdate_by(SeqUtil.RANDOM_KEY);
            incomeAccount.setUpdate_time(new Date());
            int flagAdd =  custAccountSubjectInfoMapper.addAmtByUnionKey(incomeAccount);
            if(flagAdd != 1){
                throw new BusinessException("账户加款异常："+incomeAccount);
            }
            accountTransferService.addTransFlow(eaccAccountamtlist);
            accountTransferService.addTransFlow(incomeAccount);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    /**
     * 清算冻结金额
     * @param eaccAccountamtlist 资金记录
     * @return
     */
    private int clearF_balance(EaccAccountamtlist eaccAccountamtlist) {
        EaccAccountamtlist incomeAccount = new EaccAccountamtlist();
        try {
            eaccAccountamtlist.setSwitch_amt(eaccAccountamtlist.getAmt());
            eaccAccountamtlist.setSwitch_state(SwitchState.ALLSWITCH.getCode());
            BeanUtils.copyProperties(incomeAccount, eaccAccountamtlist);
            incomeAccount.setPlatcust(eaccAccountamtlist.getOppo_platcust());
            incomeAccount.setOppo_platcust(eaccAccountamtlist.getPlatcust());
            eaccAccountamtlist.setAmt_type(AmtType.EXPENSIVE.getCode());
            incomeAccount.setAmt_type(AmtType.INCOME.getCode());
            incomeAccount.setSub_subject(eaccAccountamtlist.getOppo_sub_subject());
            incomeAccount.setSubject(eaccAccountamtlist.getOppo_subject());
            incomeAccount.setOppo_subject(eaccAccountamtlist.getSubject());
            incomeAccount.setOppo_sub_subject(eaccAccountamtlist.getSub_subject());
            if(null == incomeAccount.getUpdate_by()) incomeAccount.setUpdate_by(SeqUtil.RANDOM_KEY);
            incomeAccount.setUpdate_time(new Date());
            logger.info("【转账】==========incomeAccount：" + incomeAccount.toString() + "\n\n");
            custAccountSubjectInfoMapper.clearCashFrozen(incomeAccount);
            if(null == eaccAccountamtlist.getUpdate_by()) eaccAccountamtlist.setUpdate_by(SeqUtil.RANDOM_KEY);
            eaccAccountamtlist.setUpdate_time(new Date());
            int flag = custAccountSubjectInfoMapper.clearFloatFrozenNoNeg(eaccAccountamtlist);
            if(flag == 0){
                return flag;//清算失败
            }
            accountTransferService.addTransFlow(eaccAccountamtlist);
            accountTransferService.addTransFlow(incomeAccount);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        return  1;
    }

    /**
     * 创建外部清算文件(限集团平台)
     *
     * @param plat_no 平台号（集团）
     * @param accounts 待清算账户
     * @param clear_date 清算日期
     * @param pay_code 渠道
     * @param path  文件路径
     */
    private void createClearFile(String plat_no, Map<String, MallClearAccount> accounts, String clear_date, String pay_code, String path) {
        Map<String ,BigDecimal> platAmtMap = new HashMap<>();
        BigDecimal totalAmt = BigDecimal.ZERO;
        StringBuffer sb = new StringBuffer();
        BigDecimal inlinepayment = BigDecimal.ZERO;//银行垫资金额
        for (Map.Entry<String, MallClearAccount> entry : accounts.entrySet()) {
            MallClearAccount clearAccount = entry.getValue();
            BigDecimal waitAmt = clearAccount.getT_balance();
            if (waitAmt.compareTo(BigDecimal.ZERO) == 0) {
                continue;//待清算金额为0直接跳过
            }
            totalAmt = totalAmt.add(clearAccount.getT_balance());
            //非电子账户金额统一计入各平台账户，电子账户
            if (!clearAccount.getSub_subject().equals(Ssubject.EACCOUNT.getCode())) {
                if(Tsubject.FLOAT.getCode().equals(clearAccount.getSubject()) && (ClearServiceFor51.advanceSubject.contains(clearAccount.getSub_subject()) || Ssubject.INLINEPAYMENT.getCode().equals(clearAccount.getSub_subject()))){
                    inlinepayment = inlinepayment.add(clearAccount.getT_balance());
                    continue;//垫资账户不管，垫资金额看bankAccount
                }
                BigDecimal platAmt = platAmtMap.get(clearAccount.getPlat_no());
                if(platAmt == null){
                    platAmt = clearAccount.getT_balance();
                }else {
                    platAmt = platAmt.add(clearAccount.getT_balance());
                }
                platAmtMap.put(clearAccount.getPlat_no(),platAmt);
            } else {
                if(!plat_no.equals(clearAccount.getPlat_no())){
                    logger.error("============该电子账户平台号非集团【"+clearAccount+"】==================");
                }
                //查找电子账号
                EaccUserinfo eaccUserinfos = custEaccUserinfoMapper.getByUnionKey(plat_no, clearAccount.getPlatcust());
                sb.append(eaccUserinfos.getName()).append("|")
                        .append("2").append("|")
                        .append(eaccUserinfos.getEaccount()).append("|")
                        .append(waitAmt).append(System.lineSeparator());
            }
        }
        //添加标题头
        PlatPaycode platPaycode = custPlatPaycodeMapper.getByUnionKey(plat_no, pay_code);
        sb.insert(0, plat_no + "|" + pay_code + "|" + platPaycode.getPayment_plat_no() + "|" + totalAmt + "\n");
        //非电子账号资金都转到各存管户，添加存管户资金
        final String card_type = "01";//存管账户
        for(Map.Entry<String,BigDecimal> entry:platAmtMap.entrySet()){
            PlatCardinfo platCardinfo = custPlatCardinfoMapper.getByUnionKey(entry.getKey(), card_type);
            sb.append(platCardinfo.getCard_name()).append("|").append("1").append("|").append(platCardinfo.getCard_no()).append("|").append(entry.getValue()).append("\n");
        }
        sb.append("|3||").append(inlinepayment.toString());
        String fileName = plat_no+"_"+pay_code+"_"+clear_date+".txt";
        uploadFiles(plat_no, clear_date, sb,fileName,pay_code);
        //notify
    }

    /**
     * 创建内部清算文件
     *
     * @param plat_no  平台号
     * @param accounts 待清算账户
     * @param clear_date 清算日期
     * @param pay_code 渠道
     * @param path 文件生成路径
     */
    private void createClearFileInside(String plat_no, Map<String, ? extends ClearAccount> accounts, String clear_date, String pay_code, String path) {
        BigDecimal totalAmt = BigDecimal.ZERO;
        StringBuffer sb = new StringBuffer();

        for (Map.Entry<String, ? extends ClearAccount> entry : accounts.entrySet()) {
            ClearAccount clearAccount = entry.getValue();
            if (clearAccount.getT_balance().compareTo(BigDecimal.ZERO) > 0) {
                totalAmt = totalAmt.add(clearAccount.getT_balance());

                sb.append(clearAccount.getPlatcust()).append("|")
                        .append(clearAccount.getSubject()).append("|")
                        .append(clearAccount.getSub_subject()).append("|")
                        .append(clearAccount.getT_balance()).append("\n");
            }
        }
        //添加标题头
        sb.insert(0, plat_no + "|" + pay_code + "|" + totalAmt + "\n");
        String fileName = plat_no+"_"+pay_code+"_"+clear_date+"_in.txt";
        //写文件
        uploadFilesIn(plat_no, pay_code, sb,fileName ,pay_code);
        //notify
    }

    private void uploadFiles(String plat_no, String clear_date, StringBuffer sb,String fileName,String pay_code) {
        try {
            String plat_ftp_name = sysParameterService.querySysParameter(plat_no, "plat_ftp_name");
            // + plat_no + "/" + clear_date + "/"
            String pathName = "/home/fund/ftdm/clearResult/uploadFiles/" + plat_no + "/" + clear_date + "/"+ fileName;
            //String pathName = "C:/test/" + plat_no + "/" + clear_date + "/"+ fileName;
            String ftp_path = "/home/ftp/"+plat_ftp_name+"/" + plat_no + "/" + clear_date;
            File file = new File(pathName);
            FileUtils.writeStringToFile(file, sb.toString(), "utf-8", false);
            logger.info("====================本地文件写成功====================");

            String addr = BillCheckPropertiesUtil.getVal("addr");
            String username = BillCheckPropertiesUtil.getVal("username");
            String password = BillCheckPropertiesUtil.getVal("password");
            com.sunyard.sunfintech.core.util.FtpUtils f = new com.sunyard.sunfintech.core.util.FtpUtils(addr, 21, username, password);
            if (f.open()) {
                logger.info("====================连接FTP成功！！！====================");
//                f.upload(pathName, plat_no + "_"+pay_code + ".txt", plat_no + "/" + clear_date);
                f.changeToParentDir();
                f.upload(pathName, plat_no + "_"+pay_code + ".txt", ftp_path);
                f.close();
                logger.info("====================FTP文件写成功====================");
            } else {
                logger.info("====================连接FTP失败！！！====================");
            }
            logger.info("====================清算：end 生成清算文件====================");
//            int num = (int) CacheUtil.getCache().get(REDISKEY_SYS_PARAMETER + plat_no + clear_date);
//            CacheUtil.getCache().set(REDISKEY_SYS_PARAMETER + plat_no + clear_date, num - 1);
//            num = (int) CacheUtil.getCache().get(REDISKEY_SYS_PARAMETER + plat_no + clear_date);
            long num = CacheUtil.getCache().increment(REDISKEY_SYS_PARAMETER + plat_no + clear_date,-1);
            logger.info("=================================剩余支付渠道数量,"+num+"================================");
            if(num == 0){
                logger.info("=================================开始生成清算结束文件================================");
                getReadyFile(plat_no,clear_date);
                CacheUtil.getCache().del(REDISKEY_SYS_PARAMETER + plat_no + clear_date);
                logger.info("=================================清分==生成清分文件=======end=========================");
            }
        } catch (IOException e) {
            logger.error("生成清算文件异常", e);
        }
    }

    private void uploadFilesIn(String plat_no, String clear_date, StringBuffer sb,String fileName,String pay_code) {
        try {
            String plat_ftp_name = sysParameterService.querySysParameter(plat_no, URLConfigUtil.PLAT_FTP_NAME);
            // + plat_no + "/" + clear_date + "/"
            String pathName = "/home/fund/ftdm/clearResult/uploadFiles/" + plat_no + "/" + clear_date + "/"+ fileName;
            String ftp_path = "/ftp/"+plat_ftp_name+"/" + plat_no + "/" + clear_date;
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
                logger.info("====================FTP文件写成功====================");
            } else {
                logger.info("====================连接FTP失败！！！====================");
            }
            logger.info("====================清算：end 生成清算文件====================");
        } catch (IOException e) {
            logger.error("生成清算文件异常", e);
        }
    }

    public void getReadyFile(String mall_no,String clear_date){
        try {
            logger.info("====================本地结束文件写===start=================");
            String plat_ftp_name = sysParameterService.querySysParameter(mall_no, URLConfigUtil.PLAT_FTP_NAME);
            String addr = BillCheckPropertiesUtil.getVal("addr");
            String username = BillCheckPropertiesUtil.getVal("username");
            String password = BillCheckPropertiesUtil.getVal("password");
            com.sunyard.sunfintech.core.util.FtpUtils f = new com.sunyard.sunfintech.core.util.FtpUtils(addr, 21, username, password);
            String fileName_ready = "/home/fund/ftdm/clearResult/uploadFiles/" + mall_no + "/" + clear_date + "/"+ "ready";
            String ftp_path = "/home/ftp/"+plat_ftp_name+"/" + mall_no + "/" + clear_date;
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

    public static void main(String[] args) {


    }

}
