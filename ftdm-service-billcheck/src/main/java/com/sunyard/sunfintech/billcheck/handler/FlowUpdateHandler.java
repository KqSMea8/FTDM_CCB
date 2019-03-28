package com.sunyard.sunfintech.billcheck.handler;


import com.sunyard.sunfintech.billcheck.handler.node.AbstractListNode;
import com.sunyard.sunfintech.billcheck.handler.task.FinishedFlowTask;
import com.sunyard.sunfintech.billcheck.handler.task.PartFlowTask;
import com.sunyard.sunfintech.billcheck.model.ClearModel;
import com.sunyard.sunfintech.billcheck.pool.BillThreadPool;
import com.sunyard.sunfintech.core.dic.SwitchState;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 查询管理
 * Created by djh on 2017/11/22.
 */
public class FlowUpdateHandler {

    private ClearModel clearModel;

    //private volatile CountDownLatch latch = null;//new CountDownLatch(2);
    //private int tryCount = 0;
    //private static  final  int MAX_TRY_COUNT = 3;

    private static  final  int BATCH_SUBMIT_COUNT = 1000;//一次提交数量




    public FlowUpdateHandler(ClearModel clearModel){
        this.clearModel = clearModel;
    }

    /**
     * 不需要等待所有任务都执行完毕即可结束任务
     */
    public void asynExecute() {
        List<Long> allSwitch = new ArrayList<>();
        List<EaccAccountamtlist> partSwitch = new ArrayList<>();
        QueryListHandler queryListHandler = clearModel.getChargeListHandler();
        ConcurrentHashMap<Integer, AbstractListNode> nodes = queryListHandler.getNodes();
        logger.debug("===========充值记录："+queryListHandler.getAllElement());
        int[] state = queryListHandler.getNodeState();
        for(int i=0;i<state.length;i++){
            List<EaccAccountamtlist> list = nodes.get(i).getHandData();
            for(EaccAccountamtlist eacc:list){
                allSwitch.add(eacc.getId());
                if(allSwitch.size()==BATCH_SUBMIT_COUNT){
                    BillThreadPool.getThreadPool().addTask(new FinishedFlowTask(allSwitch));
                    allSwitch = new ArrayList<>();//每一千条提交一次
                }
            }
        }


        QueryListHandler transListHandler = clearModel.getTransListHandler();
        if(transListHandler != null){
            ConcurrentHashMap<Integer, AbstractListNode> transNodes = transListHandler.getNodes();
            int[] transState = transListHandler.getNodeState();
            for(int i=0;i<transState.length;i++){
                List<EaccAccountamtlist> transList = transNodes.get(i).getHandData();
                for(EaccAccountamtlist eacc:transList){
                    if(eacc.getSwitch_state().equals(SwitchState.ALLSWITCH.getCode())){
                        allSwitch.add(eacc.getId());
                        if(allSwitch.size()==BATCH_SUBMIT_COUNT){
                            BillThreadPool.getThreadPool().addTask(new FinishedFlowTask(allSwitch));
                            allSwitch = new ArrayList<>();//每一千条提交一次
                        }
                    }else if(eacc.getSwitch_state().equals(SwitchState.PARTSWITCH.getCode())){
                        partSwitch.add(eacc);
                        if(partSwitch.size()==BATCH_SUBMIT_COUNT){
                            BillThreadPool.getThreadPool().addTask(new PartFlowTask(partSwitch));
                            partSwitch = new ArrayList<>();//每一千条提交一次
                        }
                    }
                }
            }
        }
        for(EaccAccountamtlist eacc:clearModel.getTransListNew()) {
            if(eacc.getSwitch_state().equals(SwitchState.PARTSWITCH.getCode())){
                partSwitch.add(eacc);
                if(partSwitch.size()==BATCH_SUBMIT_COUNT){
                    BillThreadPool.getThreadPool().addTask(new PartFlowTask(partSwitch));
                    partSwitch = new ArrayList<>();//每一千条提交一次
                }
            }else if(eacc.getSwitch_state().equals(SwitchState.ALLSWITCH.getCode())){
                allSwitch.add(eacc.getId());
                if(allSwitch.size()==BATCH_SUBMIT_COUNT){
                    BillThreadPool.getThreadPool().addTask( new FinishedFlowTask(allSwitch));
                    allSwitch = new ArrayList<>();//每一千条提交一次 多次
                }
            }
        }

        if(allSwitch.size()>0){
            BillThreadPool.getThreadPool().addTask(new FinishedFlowTask(allSwitch));
        }
        if(partSwitch.size()>0){
            BillThreadPool.getThreadPool().addTask(new PartFlowTask(partSwitch));
        }
    }

    protected Logger logger = LogManager.getLogger(getClass());
}
