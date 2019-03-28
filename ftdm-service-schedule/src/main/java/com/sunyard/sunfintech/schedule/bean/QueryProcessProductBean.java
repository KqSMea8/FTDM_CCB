package com.sunyard.sunfintech.schedule.bean;

import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.prod.provider.IProdSearchService;
import com.sunyard.sunfintech.prod.provider.IProdServiceStatusQueryService;
import com.sunyard.sunfintech.prod.provider.IProductOptionService;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

/**
 * 自动查询处理中的标的还款
 * @author KouKi
 */
@Component
public class QueryProcessProductBean implements IScheduleTaskDealSingle<TransTransreq> {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private IProdServiceStatusQueryService prodServiceStatusQueryService;

    @Override
    public boolean execute(TransTransreq task, String ownSign) throws Exception {
        try {
            logger.info(String.format("【开始执行处理中的还款】流水号：%s", task.getTrans_serial()));
            prodServiceStatusQueryService.doProcessOrders(task);
        } catch (Exception e) {
            logger.error("定时任务查询流水号为：" + task.getTrans_serial() + "的标的还款异常：", e);
            return false;
        }
        return true;
    }

    @Override
    public List<TransTransreq> selectTasks(String taskParameter, String ownSign, int taskItemNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum, int pageNum) throws Exception {
        logger.info("=====开始查询处理中的标的还款订单=====");
        List<TransTransreq> transTransreqs = null;
        try {
            transTransreqs = prodServiceStatusQueryService.autoQueryAccountStatus(eachFetchDataNum);
        } catch (Exception e) {
            logger.info("【查询处理中的标的还款失败】");
        }
        return transTransreqs;
    }

    @Override
    public Comparator<TransTransreq> getComparator() {
        return new Comparator<TransTransreq>() {
            @Override
            public int compare(TransTransreq o1, TransTransreq o2) {
                return o1.getId() - o2.getId();
            }

            public boolean equals(Object obj) {
                return this == obj;
            }
        };
    }

}
