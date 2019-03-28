package com.sunyard.sunfintech.schedule.bean;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.billcheck.provider.IBillCheckService;
import com.sunyard.sunfintech.core.dic.BillCheckNotifyStatus;
import com.sunyard.sunfintech.dao.entity.BillCheckNotifyRetry;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 对账完成且成功后需要给中间平台发送通知，告诉中间平台可以下载支付扣款文件并进行扣款操作
 * 节假日：中间平台只处理最后一个节假日的通知，其他日期的通知不会去下载支付扣款文件，需要中间平台有防重处理
 * 该任务只会在对账业务通知中间平台失败时启用，发送通知的数据来源于bill_check_notify_retry
 */
@Component
public class BillCheckNotifyRetryBean implements IScheduleTaskDealSingle<BillCheckNotifyRetry> {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private IBillCheckService billCheckService;

    @Override
    public boolean execute(BillCheckNotifyRetry billCheckNotifyRetry, String s) throws Exception {
        try {
            logger.info("开始重新发送通知。billCheckNotifyRetry={}", JSON.toJSONString(billCheckNotifyRetry));
            boolean notify_result = billCheckService.billCheckNotify(billCheckNotifyRetry.getMall_no(), billCheckNotifyRetry.getClear_date());
            if (notify_result) {
                logger.info("重新发送通知给中间平台成功。mall_no={}, clear_date={}", billCheckNotifyRetry.getMall_no(), billCheckNotifyRetry.getClear_date());
                //更新初始状态为成功
                BillCheckNotifyRetry _billCheckNotifyRetry = new BillCheckNotifyRetry();
                _billCheckNotifyRetry.setId(billCheckNotifyRetry.getId());
                _billCheckNotifyRetry.setNotify_status(BillCheckNotifyStatus.SUCCESS.getCode());
                _billCheckNotifyRetry.setUpdate_time(new Date());
                int result = billCheckService.updateBillCheckNotifyRetry(_billCheckNotifyRetry);
                logger.info("更新通知状态为SUCCESS的结果：result={}", result);
            } else {
                logger.error("重新发送通知给中间平台再次失败。mall_no={}, clear_date={}", billCheckNotifyRetry.getMall_no(), billCheckNotifyRetry.getClear_date());
            }
        } catch (Exception e) {
            logger.error("重新发送通知给中间平台发生异常。mall_no="+billCheckNotifyRetry.getMall_no()+",clear_date="+billCheckNotifyRetry.getClear_date(), e);
        }
        return true;
    }

    @Override
    public List<BillCheckNotifyRetry> selectTasks(String s, String s1, int i, List<TaskItemDefine> list, int i1, int i2) throws Exception {
        try {
            //查询需要重新发送通知给中间平台的记录
            return billCheckService.findBillCheckNotifyRetry(BillCheckNotifyStatus.INIT.getCode());
        } catch (Exception e) {
            logger.error("查询通知中间平台失败的记录发生异常。", e);
            return null;
        }
    }

    @Override
    public Comparator<BillCheckNotifyRetry> getComparator() {
        return null;
    }
}
