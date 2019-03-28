package com.sunyard.sunfintech.schedule.bean;

import com.sunyard.sunfintech.billcheck.provider.IReconciliationService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.dic.HolidayType;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.dao.entity.ClearHoliday;
import com.sunyard.sunfintech.dao.entity.ClearResult;
import com.sunyard.sunfintech.schedule.service.ScheduleService;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 自动对账任务Bean
 */
@Component
public class AutoCheckBean implements IScheduleTaskDealSingle<ClearResult> {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private IReconciliationService reconciliationService;

    @Override
    public boolean execute(ClearResult task, String ownSign) throws Exception {
        try {
            BaseResponse baseResponse = reconciliationService.getAccountFile(task);
            if (BusinessMsg.SUCCESS.equals(baseResponse.getRecode())) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.info("对账异常,", e);
            return false;
        }
    }

    @Override
    public List<ClearResult> selectTasks(String s, String s1, int i, List<TaskItemDefine> list, int catchNum, int i2) throws Exception {
        List<ClearResult> clearResults = new ArrayList<>(0);
        ClearHoliday clearHoliday = scheduleService.queryHoliday(DateUtils.getyyyyMMddDate());
        if (clearHoliday != null) {
            logger.info("当天是节假日，不对账");
            return clearResults;
        }
        try {
            clearResults = scheduleService.queryNoCheckedClearResultByDate(DateUtils.beforeDate(new Date(), 1));
        } catch (Exception e) {
            logger.info("查询未对账记录异常", e);
        }
        return clearResults;
    }

    @Override
    public Comparator<ClearResult> getComparator() {
        return null;
    }
}
