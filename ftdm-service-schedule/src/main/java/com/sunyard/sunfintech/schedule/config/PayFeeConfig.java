package com.sunyard.sunfintech.schedule.config;

import com.taobao.pamirs.schedule.strategy.TBScheduleManagerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 凌晨跑对账服务
 * @author KouKi
 */
@Component
public class PayFeeConfig implements ApplicationListener<ContextRefreshedEvent> {
    protected final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private TBScheduleManagerFactory scheduleManagerFactory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        String baseTaskTypeName = "payfee";
//        try {
//            while (this.scheduleManagerFactory.isZookeeperInitialSucess() == false) {
//                Thread.sleep(1000);
//            }
//            scheduleManagerFactory.stopServer(null);
//            Thread.sleep(1000);
//        } catch (Exception e) {
//
//        }
//        try {
//            this.scheduleManagerFactory.getScheduleDataManager()
//                    .deleteTaskType(baseTaskTypeName);
//        } catch (Exception e) {
//
//        }
//        // 创建任务调度DemoTask的基本信息
//        ScheduleTaskType baseTaskType = new ScheduleTaskType();
//        baseTaskType.setHeartBeatRate(2000);//心跳频率
//        baseTaskType.setJudgeDeadInterval(10000);//服务器死亡时间
//        baseTaskType.setPermitRunStartTime("0 0/1 * * * ?");//调度任务开始时间
////        baseTaskType.setPermitRunEndTime();//调度任务结束时间
////        baseTaskType.setSleepTimeNoData(1000 * 60 * 2);//采取不到数据休眠时间
//       baseTaskType.setSleepTimeInterval(60 * 1000);//每处理完一批数据后休眠时间
//        baseTaskType.setFetchDataNumber(50);//每次采集任务数量
//        baseTaskType.setExecuteNumber(10);//每次处理任务数量
//        baseTaskType.setThreadNumber(1);//每个线程组中线程数量
//        baseTaskType.setBaseTaskType(baseTaskTypeName);
//        baseTaskType.setDealBeanName("payFeeBean");
////        baseTaskType.setProcessorType("SLEEP");//处理器类型(SLEEP--NOSLEEP) 默认SLEEP
//        baseTaskType.setTaskParameter("AREA=杭州,YEAR>30");//任务项
//        baseTaskType.setTaskItems(ScheduleTaskType.splitTaskItem(
//                "0:{TYPE=A,KIND=1},1:{TYPE=A,KIND=2},2:{TYPE=A,KIND=3},3:{TYPE=A,KIND=4}," +
//                        "4:{TYPE=A,KIND=5},5:{TYPE=A,KIND=6},6:{TYPE=A,KIND=7},7:{TYPE=A,KIND=8}," +
//                        "8:{TYPE=A,KIND=9},9:{TYPE=A,KIND=10}"));
//        try {
//            this.scheduleManagerFactory.getScheduleDataManager()
//                    .createBaseTaskType(baseTaskType);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        logger.info("创建调度任务成功:" + baseTaskType.toString());
//
//        // 创建任务DemoTask的调度策略
//        String taskName = baseTaskTypeName + "$FTDM";
//        String strategyName = baseTaskTypeName + "-Strategy";
//        try {
//            this.scheduleManagerFactory.getScheduleStrategyManager()
//                    .deleteMachineStrategy(strategyName, true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        ScheduleStrategy strategy = new ScheduleStrategy();
//        strategy.setStrategyName(strategyName);
//        strategy.setKind(ScheduleStrategy.Kind.Schedule);
//        strategy.setTaskName(taskName);
//        strategy.setTaskParameter("缴费超时设置状态");
//        strategy.setNumOfSingleServer(1);//每个server最大任务项||线程组数目(0表示无限制)
//        strategy.setAssignNum(1);//指定所有机器上运行的线程组总数
//        strategy.setIPList("127.0.0.1".split(","));
//        try {
//            this.scheduleManagerFactory.getScheduleStrategyManager()
//                    .createScheduleStrategy(strategy);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        logger.info("创建调度策略成功:" + strategy.toString());

    }
}
