package com.sunyard.sunfintech.schedule.bean;

import com.sunyard.sunfintech.dao.entity.RwWithdraw;
import com.sunyard.sunfintech.user.provider.IRwWithdrawOptionService;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

/**
 * 自动查询处理中的自动加值
 * @author KouKi
 */
@Component
public class CcbRwWithdrawAutoAddValueQueryBean implements IScheduleTaskDealSingle<RwWithdraw> {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private IRwWithdrawOptionService rwWithdrawOptionService;

    @Override
    public boolean execute(RwWithdraw task, String ownSign) throws Exception {
        try {
            logger.info(String.format("【开始执行提现自动加值处理中订单查询】流水号：%s", task.getTrans_serial()));
            rwWithdrawOptionService.doAutoAddValueProcessingOrders(task);
        } catch (Exception e) {
            logger.error("定时任务处理流水号为：" + task.getTrans_serial() + "的提现自动加值订单异常：", e);
            return false;
        }
        return true;
    }

    @Override
    public List<RwWithdraw> selectTasks(String taskParameter, String ownSign, int taskItemNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum, int pageNum) throws Exception {
        logger.info("=====开始查询处理中的提现自动加值订单=====");
        List<RwWithdraw> rwWithdrawList = null;
        try {
            rwWithdrawList = rwWithdrawOptionService.queryAutoAddValueProcessingOrders(eachFetchDataNum);
        } catch (Exception e) {
            logger.info("【查询处理中的提现自动加值订单失败】");
        }
        return rwWithdrawList;
    }

    @Override
    public Comparator<RwWithdraw> getComparator() {
        return new Comparator<RwWithdraw>() {
            @Override
            public int compare(RwWithdraw o1, RwWithdraw o2) {
                return o1.getId() - o2.getId();
            }

            public boolean equals(Object obj) {
                return this == obj;
            }
        };
    }

}
