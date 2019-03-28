package com.sunyard.sunfintech.schedule.bean;

import com.sunyard.sunfintech.account.provider.IAccountServiceStatusQueryService;
import com.sunyard.sunfintech.core.dic.OrderStatus;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.user.provider.IUserTransService;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

/**
 * 授权缴费超时设置状态
 *
 * @author KouKi
 */
@Component
public class PayFeeBean implements IScheduleTaskDealSingle<TransTransreq> {

    protected final Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    AmqpTemplate amqpTemplate;
    @Autowired
    private IUserTransService userTransService;
    @Autowired
    private IAccountServiceStatusQueryService accountServiceStatusQueryService;
    @Override
    public boolean execute(TransTransreq task, String s) throws Exception {
        try {
            logger.info("【授权缴费】开始执行，流水号：" + task.getTrans_serial());
            String orderNo = task.getOrder_no();
            TransTransreq accountTransTransreq = accountServiceStatusQueryService.queryMyTransSerialStatus(orderNo, null, task.getPlat_no());

            if (accountTransTransreq != null) {
                task.setStatus(accountTransTransreq.getStatus());
                task.setReturn_code(accountTransTransreq.getReturn_code());
                task.setReturn_msg("job发起查询置状态位");

            } else {
                task.setStatus(OrderStatus.FAIL.getCode());
                task.setReturn_code(BusinessMsg.FAIL);
                task.setReturn_msg("account中找不到订单，job置超时处理");
            }
            logger.info("【授权缴费】置状态" + task.getStatus());
            MQUtils.send(amqpTemplate, "ftdm.schedule.direct.exchange", "ToUserQueue", task);
            logger.info("【授权缴费】成功推送ToUserQueue队列，置状态" + task.getStatus());
        } catch (Exception e) {
            logger.error("【授权缴费】异常", e);
        }
        return true;
    }

    @Override
    public List<TransTransreq> selectTasks(String s, String s1, int i, List<TaskItemDefine> list, int catchNum, int i2) throws Exception {
        List<TransTransreq> transTransreqs = null;
        try {
            transTransreqs =   userTransService.queryTransProcessing( );;
        }catch (Exception e){
            logger.error("【授权缴费】异常",e);
        }
        return transTransreqs;
    }

    @Override
    public Comparator<TransTransreq> getComparator() {
        return null;
    }



}
