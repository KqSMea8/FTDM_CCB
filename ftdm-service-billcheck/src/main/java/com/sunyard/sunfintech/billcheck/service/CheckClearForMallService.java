package com.sunyard.sunfintech.billcheck.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.sunyard.sunfintech.billcheck.provider.ICheckClearService;
import com.sunyard.sunfintech.billcheck.model.bo.ClearAccount;
import com.sunyard.sunfintech.billcheck.model.bo.MallClearAccount;
import com.sunyard.sunfintech.billcheck.provider.IClearService;
import com.sunyard.sunfintech.billcheck.utils.ListUtils;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.AmtType;
import com.sunyard.sunfintech.core.dic.ClearStatus;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.custdao.mapper.CustAccountSubjectInfoMapper;
import com.sunyard.sunfintech.custdao.mapper.CustClearResultMapper;
import com.sunyard.sunfintech.custdao.mapper.CustEaccAccountamtlistMapper;
import com.sunyard.sunfintech.custdao.mapper.CustPlatPlatinfoMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.ClearResultMapper;
import com.sunyard.sunfintech.dao.mapper.ClearYuAccountMapper;
import com.sunyard.sunfintech.dao.mapper.ClearYuAccountamtlistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by djh on 2018/02/02
 * 清算
 */
@Service(interfaceClass = ICheckClearService.class)
@CacheConfig(cacheNames = "checkClearForMallService")
@org.springframework.stereotype.Service
public class CheckClearForMallService extends BaseServiceSimple implements ICheckClearService {


    @Autowired
    private CustEaccAccountamtlistMapper custEaccAccountamtlistMapper;

    @Autowired
    private CustClearResultMapper custClearResultMapper;

    @Autowired
    private ClearResultMapper clearResultMapper;

    @Autowired
    private CustAccountSubjectInfoMapper custAccountSubjectInfoMapper;

    @Autowired
    CustPlatPlatinfoMapper custPlatPlatinfoMapper;

    @Autowired
    ClearYuAccountamtlistMapper clearYuAccountamtlistMapper;

    @Autowired
    ClearYuAccountMapper clearYuAccountMapper;


    public BaseResponse checkClear(final String plat_no, final String clear_date) {
        try {
            Thread.sleep(50*1000L);//睡50s再执行
        } catch (InterruptedException e) {
            logger.error("线程被异常中断",e);
        }
        //查询是否属于集团
        List<String> plat_nos = custPlatPlatinfoMapper.getPlatNoByMallNo(plat_no);
        if (plat_nos.size() > 1) {
            plat_nos.add(plat_no);
            return doCheckClearForMall(plat_nos, clear_date);
        } else {
            return null;
        }
    }

    private BaseResponse doCheckClearForMall(final List<String> plat_nos, final String clear_date) {
        BaseResponse response = new BaseResponse();
        logger.info("【集团】开始预清算：plat_nos:" + plat_nos + ",clear_date:" + clear_date);
        List<ClearResult> mallClearResults = new ArrayList<>();
        for (int i=0;i<plat_nos.size()-1;i++) {//不检查集团
            String plat_no = plat_nos.get(i);
            //清算记录
            List<ClearResult> clearResults = custClearResultMapper.getByUnionKey(plat_no,
                    DateUtils.parseDate(clear_date, "yyyyMMdd"));
            BaseResponse baseResponse = isCheckClearable(clearResults, response);
            if (BusinessMsg.FAIL.equals(baseResponse.getRecode())) return baseResponse;
            mallClearResults.addAll(clearResults);//后面需要修改清算状态的，先暂存在list中
        }
        //充值记录
        List<EaccAccountamtlist> chargeList = custEaccAccountamtlistMapper.getMallAllChargeList(plat_nos, DateUtils.parseDate(clear_date, "yyyyMMdd"));
        //资金流水
        List<EaccAccountamtlist> eaccList = custEaccAccountamtlistMapper.getMallTransferList(plat_nos, DateUtils.parseDate(clear_date, "yyyyMMdd"));
        //在途金额
        List<AccountSubjectInfo> accountSubjectInfos = custAccountSubjectInfoMapper.getMallFloatSubject(plat_nos, clear_date);

        List<EaccAccountamtlist> errorList = new ArrayList<>();//预清算异常流水
        List<MallClearAccount> errorAccounts = new ArrayList<>();//预清算异常账户
        final Map<String, MallClearAccount> accounts = new ConcurrentHashMap<>();
        for (EaccAccountamtlist charge : chargeList) {
                String unionKeyMall = charge.getPlat_no() + "#" + charge.getPlatcust() + "#" + charge.getSubject() + "#" + charge.getSub_subject();
                MallClearAccount account = accounts.get(unionKeyMall);//
                if (account == null) {
                    account = new MallClearAccount(charge.getPlat_no(), charge.getPlatcust(), charge.getSubject(), charge.getSub_subject());
                    account.addAmt(charge.getAmt());
                    accounts.put(unionKeyMall, account);
                } else {
                    account.addAmt(charge.getAmt());
                }
        }

        for (EaccAccountamtlist trans : eaccList) {
            //初始化自身和对手账户
            String selfKey = trans.getPlat_no() + "#" + trans.getPlatcust() + "#" + trans.getSubject() + "#" + trans.getSub_subject();
            MallClearAccount selfAccount = accounts.get(selfKey);
            if (selfAccount == null) {
                selfAccount = new MallClearAccount(trans.getPlat_no(), trans.getPlatcust(), trans.getSubject(), trans.getSub_subject());
                accounts.put(selfKey, selfAccount);
            }
            String oppo_plat_no = ListUtils.ifNull(trans.getOppo_plat_no(), trans.getPlat_no());
            String oppoKey = oppo_plat_no + "#" + trans.getOppo_platcust() + "#" + trans.getOppo_subject() + "#" + trans.getOppo_sub_subject();
            MallClearAccount oppoAccount = accounts.get(oppoKey);
            if (oppoAccount == null) {
                oppoAccount = new MallClearAccount(oppo_plat_no, trans.getOppo_platcust(), trans.getOppo_subject(), trans.getOppo_sub_subject());
                accounts.put(oppoKey, oppoAccount);
            }

            calClearAmt(errorList,trans, selfAccount, oppoAccount);
        }

        compareAccounts(accountSubjectInfos,accounts,errorAccounts);
        //预清算异常的账户和流水
        insertClearYuAccounts(errorAccounts,clear_date);
        insertClearYuAccountamtlists(errorList,clear_date);

        //修改预清算状态，差错流水表和账户表都正常则预清算成功
        if (errorAccounts.size()==0 && errorList.size()==0) {
            response.setRecode(BusinessMsg.SUCCESS);
            for (ClearResult clearResult : mallClearResults) {
                updateClearStatusCheckSuc(clearResult);
            }
        } else {
            response.setRecode(BusinessMsg.FAIL);
            response.setRemsg("预清算异常，错误账户数："+errorAccounts.size()+",错误流水数："+errorList.size());
            for (ClearResult clearResult : mallClearResults) {
                updateClearStatusCheckErr(clearResult);
            }
        }
        return response;
    }

    private void insertClearYuAccounts(List<MallClearAccount> errorAccounts,String clear_date) {
        for(MallClearAccount account:errorAccounts){
            ClearYuAccount clearYuAccount = new ClearYuAccount();
            clearYuAccount.setPlat_no(account.getPlat_no());
            clearYuAccount.setPlatcust(account.getPlatcust());
            clearYuAccount.setSub_subject(account.getSub_subject());
            clearYuAccount.setClear_date(clear_date);

            clearYuAccount.setN_balance(account.getN_balance());
            clearYuAccount.setF_balance(account.getF_balance());
            clearYuAccount.setCur_f_balance(account.getCur_f_balance());
            clearYuAccount.setCur_n_balance(account.getCur_n_balance());
            clearYuAccount.setCreate_time(new Date());
            clearYuAccountMapper.insertSelective(clearYuAccount);
        }

    }

    private void insertClearYuAccountamtlists(List<EaccAccountamtlist> errorList,String clear_date) {
        for(EaccAccountamtlist eacc:errorList){
            ClearYuAccountamtlist clearYuAccountamtlist = new ClearYuAccountamtlist();
            clearYuAccountamtlist.setTrans_id(eacc.getId());
            clearYuAccountamtlist.setPlat_no(eacc.getPlat_no());
            clearYuAccountamtlist.setSwitch_amt(eacc.getSwitch_amt());
            clearYuAccountamtlist.setAccount_date(DateUtils.parseDate(clear_date,"yyyyMMdd"));
            clearYuAccountamtlist.setAmt(eacc.getAmt());
            clearYuAccountamtlist.setCreate_time(new Date());
            clearYuAccountamtlistMapper.insertSelective(clearYuAccountamtlist);
        }
    }

    /**
     * 判断当前状态是否可以清算
     *
     * @param clearResults
     * @param response
     * @return
     */
    private BaseResponse isCheckClearable(List<ClearResult> clearResults, BaseResponse response) {
        if (clearResults.size() == 0) {
            response.setRecode(BusinessMsg.FAIL);
            response.setRemsg("没找到对账记录（clear_reuslt中无记录）");
            return response;
        }
        for (ClearResult clearResult : clearResults) {
            if (ClearStatus.CHECKED.getCode().equals(clearResult.getClear_status())
                    || ClearStatus.CHECK_CLEAR_ERR.getCode().equals(clearResult.getClear_status())
                    || ClearStatus.CHECK_CLEAR_SUC.getCode().equals(clearResult.getClear_status())) {
                //do nothing 已对账或预清算异常都可进行预清算
            } else {
                response.setRecode(BusinessMsg.FAIL);
                response.setRemsg("对账异常，请处理完异常账单再清算");
                return response;
            }
        }
        response.setRemsg(BusinessMsg.SUCCESS);
        return response;
    }

    /**
     * 更新对账状态为预清算成功
     *
     * @param clearResult 清算记录
     * @return 更新条数
     */
    private int updateClearStatusCheckSuc(ClearResult clearResult) {
        ClearResult newClearResult = new ClearResult();
        newClearResult.setPid(clearResult.getPid());
        newClearResult.setClear_status(ClearStatus.CHECK_CLEAR_SUC.getCode());
        return clearResultMapper.updateByPrimaryKeySelective(newClearResult);
    }

    /**
     * 更新对账状态为预清算失败
     *
     * @param clearResult 清算记录
     * @return 更新条数
     */
    private int updateClearStatusCheckErr(ClearResult clearResult) {
        ClearResult newClearResult = new ClearResult();
        newClearResult.setPid(clearResult.getPid());
        newClearResult.setClear_status(ClearStatus.CHECK_CLEAR_ERR.getCode());
        return clearResultMapper.updateByPrimaryKeySelective(newClearResult);
    }

    /**
     * 计算清算金额
     *
     * @param trans         资金流水
     * @param selfAccount   自身账户
     * @param oppoAccount   对手账户
     */
    private void calClearAmt(List<EaccAccountamtlist> errorList, EaccAccountamtlist trans, ClearAccount selfAccount, ClearAccount oppoAccount) {
        BigDecimal waitSwitchAmt = trans.getAmt().subtract(trans.getSwitch_amt());//需清算余额
        //转账
        if (AmtType.EXPENSIVE.getCode().equals(trans.getAmt_type())) {
            if (selfAccount.getN_balance().compareTo(waitSwitchAmt) >= 0) {
                selfAccount.subtractAmt(waitSwitchAmt);
                oppoAccount.addAmt(waitSwitchAmt);
                trans.setSwitch_amt(trans.getAmt());
            } else {
                BigDecimal remainAmt = selfAccount.getN_balance();//剩余可清金额
                selfAccount.subtractAmt(remainAmt);
                oppoAccount.addAmt(remainAmt);
                trans.setSwitch_amt(trans.getSwitch_amt().add(remainAmt));
                errorList.add(trans);//预清算所有的流水状态都应该为已清算，出现部分清算即错误
            }
        }
    }

    /**
     * 查找资金错误的账户
     * @param accountSubjectInfos   数据库中现有余额
     * @param accounts              算出该清算金额
     * @param errorAccounts         accountSubjectInfos，accounts两边对比不一致的账户
     */
    private void compareAccounts(List<AccountSubjectInfo> accountSubjectInfos,Map<String,MallClearAccount> accounts,List<MallClearAccount> errorAccounts){
        //比较实际余额和清算余额
        for(AccountSubjectInfo asf:accountSubjectInfos){
            String key = asf.getPlat_no()+"#"+asf.getPlatcust()+"#"+asf.getSubject()+"#"+asf.getSub_subject();
            MallClearAccount mapAccount = accounts.get(key);
            if(mapAccount==null){
                //在途账户有钱，但没有进入清算，异常账户
                mapAccount = new MallClearAccount(asf.getPlat_no(),asf.getPlatcust(),asf.getSubject(),asf.getSub_subject());
                mapAccount.setCur_n_balance(asf.getN_balance());
                mapAccount.setCur_f_balance(asf.getF_balance());
                //errorAccounts.add(mapAccount);
                logger.warn("=============在途账户有钱，但没有进入清算，异常账户："+asf+"====================");
            }else if(mapAccount.getN_balance().compareTo(asf.getN_balance()) > 0
                    || mapAccount.getF_balance().compareTo(asf.getF_balance()) > 0){
                mapAccount.setCur_n_balance(asf.getN_balance());
                mapAccount.setCur_f_balance(asf.getF_balance());
                errorAccounts.add(mapAccount);
                accounts.remove(key);//删除
            }else if(mapAccount.getN_balance().compareTo(asf.getN_balance()) < 0
                    || mapAccount.getF_balance().compareTo(asf.getF_balance()) < 0){
                mapAccount.setCur_n_balance(asf.getN_balance());
                mapAccount.setCur_f_balance(asf.getF_balance());
                logger.warn("=============预清算资金匹配，账户在途大于计算该清算金额："+mapAccount+"====================");
                accounts.remove(key);//删除
            }else {
                accounts.remove(key);//
            }
        }
        for(Map.Entry<String,MallClearAccount> entry:accounts.entrySet()){
            MallClearAccount account = entry.getValue();
            if(account.getT_balance().compareTo(BigDecimal.ZERO) != 0){
                //errorAccounts.add(account);//计算出该清算在途的账户，但实际账户资金为零，该资金可能再查询资金流水之后充值的，不管
                logger.warn("=============预清算资金匹配，账户存在在途："+account+"====================");
            }
            accounts.remove(entry.getKey());
        }
    }
}