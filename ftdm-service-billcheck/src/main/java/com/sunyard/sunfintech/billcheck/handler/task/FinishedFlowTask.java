package com.sunyard.sunfintech.billcheck.handler.task;

import com.sunyard.sunfintech.core.util.SpringContextHolder;
import com.sunyard.sunfintech.custdao.mapper.CustEaccAccountamtlistMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 更新资金流水为已清算
 * Created by djh on 2017/11/23.
 */
public class FinishedFlowTask implements Runnable {


    private List<Long> list ;

    public FinishedFlowTask(List<Long> list){
        this.list = list;
    }
    @Override
    public void run() {
        try {
            CustEaccAccountamtlistMapper custEaccAccountamtlistMapper = SpringContextHolder.getBean(CustEaccAccountamtlistMapper.class);
            custEaccAccountamtlistMapper.batchUpdateAllSwitch(list);
        }catch (Exception e){
            logger.error("==========批次执行失败："+ list.toString(),e);
        }

    }

    protected Logger logger = LogManager.getLogger(getClass());

}
