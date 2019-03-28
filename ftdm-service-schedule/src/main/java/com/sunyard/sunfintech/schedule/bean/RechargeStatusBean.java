package com.sunyard.sunfintech.schedule.bean;

import com.alibaba.fastjson.JSONObject;
import com.sunyard.sunfintech.core.dic.OrderStatus;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.dao.entity.RwRecharge;
import com.sunyard.sunfintech.pub.model.NotifyData;
import com.sunyard.sunfintech.pub.provider.INotifyService;
import com.sunyard.sunfintech.user.provider.IAccountSearchService;
import com.sunyard.sunfintech.user.provider.IRechargeOptionService;
import com.sunyard.sunfintech.user.provider.IUserTransService;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 自动查询处理中的充值订单
 * @author KouKi
 */
@Component
public class RechargeStatusBean implements IScheduleTaskDealSingle<RwRecharge> {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private IRechargeOptionService rechargeOptionService;

    @Autowired
    private IUserTransService userTransService;

    @Autowired
    private IAccountSearchService accountSearchService;

    @Autowired
    private INotifyService notifyService;

    @Override
    public boolean execute(RwRecharge task, String ownSign) throws Exception {
        try {
            RwRecharge rwRecharge = userTransService.queryEpayStatus(task);
            logger.info("========【订单状态查询】rwRecharge参数：" + rwRecharge.toString());
            if (rwRecharge == null) {
                logger.info("========【订单状态查询】去看三方日志managementPayOutService.queryEpayStatus");
                return false;
            }
            String mall_no = accountSearchService.queryMallNoByPlatNo(rwRecharge.getPlat_no());
            if (OrderStatus.SUCCESS.getCode().equals(rwRecharge.getStatus()) || OrderStatus.FAIL.getCode().equals(rwRecharge.getStatus())) {
                //如果状态是成功或者失败
                Map<String, Object> requestMap = new HashMap<>();
                requestMap.put("mall_no", mall_no);
                requestMap.put("plat_no", rwRecharge.getPlat_no());
                requestMap.put("order_no", rwRecharge.getOrder_no());
                requestMap.put("platcust", rwRecharge.getPlatcust());
                requestMap.put("type", "1");
                requestMap.put("order_amt", rwRecharge.getTrans_amt());
                requestMap.put("trans_date", rwRecharge.getTrans_date());
                requestMap.put("trans_time", rwRecharge.getTrans_time());
                requestMap.put("pay_order_no", rwRecharge.getHsepay_order_no());
                requestMap.put("pay_finish_date", DateUtils.today(DateUtils.DEF_FORMAT_NOTIME));
                requestMap.put("pay_finish_time", DateUtils.today("HHmmss"));
                requestMap.put("pay_amt", rwRecharge.getTrans_amt());
                requestMap.put("sign", " ");
                requestMap.put("order_status", rwRecharge.getStatus());
                requestMap.put("recode", BusinessMsg.SUCCESS);
                requestMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                if(OrderStatus.FAIL.getCode().equals(rwRecharge.getStatus())){
                    requestMap.put("error_no", rwRecharge.getReturn_code());
                    requestMap.put("error_info", rwRecharge.getReturn_msg());
                    requestMap.put("recode", rwRecharge.getReturn_code());
                    requestMap.put("remsg", rwRecharge.getReturn_msg());
                }
                String data = JSONObject.toJSONString(requestMap);
                logger.info("********************订单号-->" + rwRecharge.getOrder_no() + "该订单状态" + rwRecharge.getStatus());
                logger.info("********************异步通知平台信息" + data);
                logger.info("********************异步通知平台URL" + rwRecharge.getNotify_url());
                //发送通知
                NotifyData notifyData = new NotifyData();
                notifyData.setMall_no(mall_no);
                notifyData.setNotifyUrl(rwRecharge.getNotify_url());
                notifyData.setNotifyContent(data);
                //TODO 通知
                notifyService.addNotify(notifyData);
            }
        } catch (Exception e) {
            logger.info("充值状态查询异常", e);
            return false;
        }
        return true;
    }

    @Override
    public List<RwRecharge> selectTasks(String taskParameter, String ownSign, int taskItemNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum, int pageNum) throws Exception {
        logger.info("=====开始查询处理中的充值订单=====");
        List<RwRecharge> list = new ArrayList<>();
        if (taskItemList.size() == 0) {
            logger.info("=====当前调度服务器，分配到的可处理队列为 0 =====");
            return list;
        }
        try {
            list = rechargeOptionService.queryProcessingRecharge(eachFetchDataNum);
        } catch (Exception e) {
            logger.info("查询待处理充值异常", e);
        }
        return list;
    }

    @Override
    public Comparator<RwRecharge> getComparator() {
        return new Comparator<RwRecharge>() {
            public int compare(RwRecharge o1, RwRecharge o2) {
                return o1.getId() - o2.getId();
            }

            public boolean equals(Object obj) {
                return this == obj;
            }
        };
    }
}
