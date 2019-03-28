package com.sunyard.sunfintech.schedule.bean;

import com.sunyard.sunfintech.dao.entity.RwWithdraw;
import com.sunyard.sunfintech.user.provider.IRwWithdrawOptionService;
import com.sunyard.sunfintech.user.provider.IUserSearchService;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

/**
 * 自动退款任务Bean
 */
@Component
public class AutoRefundBean implements IScheduleTaskDealSingle<RwWithdraw> {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private IRwWithdrawOptionService rwWithdrawOptionService;

    @Override
    public boolean execute(RwWithdraw task, String ownSign) throws Exception {
        try {
            logger.info("【待退款提现】-->开始执行任务-->订单号：" + task.getOrder_no());
            rwWithdrawOptionService.doAutomaticRefund(task);
        }catch (Exception e){
            logger.info("【待退款提现】-->异常-->订单号：" + task.getOrder_no(),e);
        }
        return true;
    }

    @Override
    public List<RwWithdraw> selectTasks(String s, String s1, int i, List<TaskItemDefine> list, int catchNum, int i2) throws Exception {
        List<RwWithdraw> withdrawList = null;
        try {
            logger.info("【待退款提现】查询银联退款的提现");
            withdrawList = rwWithdrawOptionService.queryAllAutomaticRefund(catchNum);
        }catch (Exception e){
            logger.info("【待退款提现】查询银联退款的提现时异常",e);
        }
        return withdrawList;
    }

    @Override
    public Comparator<RwWithdraw> getComparator() {
        return null;
    }
}
