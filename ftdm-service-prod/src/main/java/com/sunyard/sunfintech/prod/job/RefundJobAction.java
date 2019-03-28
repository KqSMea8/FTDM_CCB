package com.sunyard.sunfintech.prod.job;

import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.prod.autoexecute.QueryBatchPayNew;
import com.sunyard.sunfintech.prod.quartz.QuartzJob;
import com.sunyard.sunfintech.prod.quartz.QuartzJobManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wubin on 2017/6/24.
 */
@Service("refundJobAction")
public class RefundJobAction {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private QuartzJobManager quartzJobManager;

    public void startQueryBatchPayNew(String trans_serial,String mall_no,String plat_no){

        logger.info("初始化启动job查询新批量代付支付状态");

        try{

            QuartzJob quartzJob = new QuartzJob();

            quartzJob.setJobName("JOB"+trans_serial);

            //时间表达式  一分钟执行一次
            String time = CronDateUtils.getCron("JOB"+trans_serial);
            quartzJob.setCronExpression(time);

            Map<String,Object> dataMap=new HashMap<>();
            dataMap.put("trans_serial",trans_serial);
            dataMap.put("mall_no",mall_no);
            dataMap.put("plat_no",plat_no);

            quartzJob.setData(dataMap);

            quartzJob.setJobStatus(1);

            quartzJobManager.initJob(quartzJob, QueryBatchPayNew.class);

        }catch (Exception e){
            logger.error("初始化启动job查询新批量代付支付状态失败" + e);
            e.printStackTrace();
        }
    }
}
