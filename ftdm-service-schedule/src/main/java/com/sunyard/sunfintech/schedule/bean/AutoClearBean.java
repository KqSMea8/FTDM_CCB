package com.sunyard.sunfintech.schedule.bean;

import com.sunyard.sunfintech.billcheck.model.bo.SortingRequest;
import com.sunyard.sunfintech.billcheck.provider.IBillCheckService;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.dic.AutoClear;
import com.sunyard.sunfintech.core.dic.PlatAccType;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.core.util.SysParamterKey;
import com.sunyard.sunfintech.dao.entity.ClearHoliday;
import com.sunyard.sunfintech.dao.entity.ClearResult;
import com.sunyard.sunfintech.dao.entity.PlatPaycode;
import com.sunyard.sunfintech.dao.entity.SysParameter;
import com.sunyard.sunfintech.schedule.modulepublic.SendMsgService;
import com.sunyard.sunfintech.schedule.modulepublic.SysParameterService;
import com.sunyard.sunfintech.schedule.service.ScheduleService;
import com.sunyard.sunfintech.user.model.bo.PlatAccountDetailRequest;
import com.sunyard.sunfintech.user.model.bo.PlatAccountDetailResponseList;
import com.sunyard.sunfintech.user.provider.IAccountSearchService;
import com.sunyard.sunfintech.user.provider.IUserSearchService;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * 自动清算任务Bean
 */
@Component
public class AutoClearBean implements IScheduleTaskDealSingle<ClearResult> {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private IAccountSearchService accountSearchService;
    @Autowired
    private IUserSearchService userSearchService;
    @Autowired
    private IBillCheckService billCheckService;
    @Autowired
    private SendMsgService sendMsgService;
    @Autowired
    private SysParameterService sysParameterService;

    @Override
    public boolean execute(ClearResult task, String ownSign) throws Exception {
        try {
            PlatPaycode platPaycode = accountSearchService.queryPlatPayCode(task.getPlat_no(), task.getPay_code());
            if(AutoClear.NO_AUTO_CLEAR.getCode().equals(platPaycode.getAuto_clear())){
                logger.info("不自动清算");
                return false;
            }
            //查询该平台当日的清算入金
            PlatAccountDetailRequest accountingDetailRequest = new PlatAccountDetailRequest();
            String mall_no = accountSearchService.queryMallNoByPlatNo(task.getPlat_no());
            accountingDetailRequest.setMall_no(mall_no);
            accountingDetailRequest.setMall_no(task.getPlat_no());
            accountingDetailRequest.setIo_flag("C");
            accountingDetailRequest.setDate_flag("CUR");
            accountingDetailRequest.setDate_from(DateUtils.getyyyyMMddDate());
            accountingDetailRequest.setDate_to(DateUtils.getyyyyMMddDate());
            accountingDetailRequest.setAccount_type(PlatAccType.PLATTRUST.getCode());
            accountingDetailRequest.setOrder_no(SeqUtil.getSeqNum());
            List<PlatAccountDetailResponseList> accountDetailList = userSearchService.queryAccountDetail(accountingDetailRequest);
            BigDecimal sumInAmt = BigDecimal.ZERO;
            logger.info("查询该平台配置的清算入金帐号");
            String cardNoStr = sysParameterService.querySysParameter(task.getPlat_no(), SysParamterKey.PLAT_CLEAR_CARD_NOS);
            if(StringUtils.isEmpty(cardNoStr)){
                logger.info("PLAT_CLEAR_CARD_NOS未配置");
                return false;
            }
            String[] cardNos = cardNoStr.split(",");
            Map<String, String> platPaycodeMap = new HashMap<>();
            for(String cardNo : cardNos){
                platPaycodeMap.put(cardNo, cardNo);
            }
            for(PlatAccountDetailResponseList platAccountDetailResponseList : accountDetailList){
                if(platPaycodeMap.get(platAccountDetailResponseList.getOppo_acct()) != null){
                    logger.info("该账户是清算入金账户:" + platAccountDetailResponseList.getOppo_acct());
                    sumInAmt = sumInAmt.add(platAccountDetailResponseList.getTran_amt());
                }
            }
            //查询当前平台的昨日总充值量
            List<ClearResult> clearResults = scheduleService.queryClearResultByDateAndPlatNo(DateUtils.beforeDate(new Date(), 1), task.getPlat_no());
            if(clearResults.size() == 0){
                logger.info("查询不到clearResult表数据");
                return false;
            }
            BigDecimal sumRecharge = BigDecimal.ZERO;
            for(ClearResult clearResult : clearResults){
                sumRecharge = sumRecharge.add(clearResult.getRecharge_sum_outside());
            }
            logger.info("昨日充值总量：{}", sumRecharge);
            logger.info("今日入金总额：{}", sumInAmt);
            if(sumRecharge.compareTo(sumInAmt) != 0){
                logger.info("入金金额不等于充值金额");
                return false;
            }
            SortingRequest sortingRequest = new SortingRequest();
            sortingRequest.setMall_no(mall_no);
            sortingRequest.setClear_date(DateUtils.formatDateToStr(task.getClear_date(), DateUtils.DEF_FORMAT_NOTIME));
            BaseResponse baseResponse = billCheckService.requestSortingFile(sortingRequest);
            if (BusinessMsg.SUCCESS.equals(baseResponse.getRecode())) {
                sendMsgService.sendErrorToAdmin(task.getPlat_no() + "清算成功", task.getPlat_no());
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.info("清算异常,", e);
            return false;
        }
    }

    @Override
    public List<ClearResult> selectTasks(String s, String s1, int i, List<TaskItemDefine> list, int catchNum, int i2) throws Exception {
        List<ClearResult> clearResults = new ArrayList<>(0);
        ClearHoliday clearHoliday = scheduleService.queryHoliday(DateUtils.getyyyyMMddDate());
        if (clearHoliday != null) {
            logger.info("当天是节假日，不清算");
            return clearResults;
        }
        try {
            clearResults = scheduleService.queryNoClearedClearResultByDate(DateUtils.beforeDate(new Date(), 1));
        } catch (Exception e) {
            logger.info("查询未清算记录异常", e);
        }
        return clearResults;
    }

    @Override
    public Comparator<ClearResult> getComparator() {
        return null;
    }
}
