package com.sunyard.sunfintech.billcheck.handler;


import com.sunyard.sunfintech.billcheck.handler.node.AbstractListNode;
import com.sunyard.sunfintech.billcheck.handler.node.AccountListNode;
import com.sunyard.sunfintech.billcheck.handler.node.ChargeListNode;
import com.sunyard.sunfintech.billcheck.handler.node.TransListNode;
import com.sunyard.sunfintech.billcheck.model.ClearModel;
import com.sunyard.sunfintech.billcheck.pool.BillThreadPool;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 查询管理
 * Created by djh on 2017/11/22.
 */
public class QueryListHandler {

    private ConcurrentHashMap<Integer,AbstractListNode> nodes = new ConcurrentHashMap<>();

    private ClearModel clearModel;
    private int[] nodeState ;
    private long maxId;
    private long minId;
    private long step = 10000L;//10000L;
    private volatile CountDownLatch latch = null;//new CountDownLatch(2);
    private int tryCount = 0;
    private static  final  int MAX_TRY_COUNT = 3;



    public QueryListHandler(long minId, long maxId, ClearModel clearModel, Class<? extends AbstractListNode> querynode){
        this.maxId = maxId+1;//因为max不包含,加一
        this.minId = minId;
        this.clearModel = clearModel;
        init(querynode);
    }
    public void init(Class<? extends AbstractListNode> querynode){
        if(querynode.equals(ChargeListNode.class)){
            this.clearModel.setChargeListHandler(this);
        }else if(querynode.equals(TransListNode.class)){
            this.clearModel.setTransListHandler(this);
        }
        long start = minId;
        long end = start + step;
        long size = (maxId-minId)/step + 1;
        nodeState = new int[(int)size];
        for(int i=0;i<nodeState.length;i++){
            AbstractListNode node = null;
            if(querynode.equals(ChargeListNode.class)){
                node = new ChargeListNode(start,end,i, this);
            }else if(querynode.equals(TransListNode.class)){
                node = new TransListNode(start,end,i, this);
            }else if(querynode.equals(AccountListNode.class)){
                node = new AccountListNode(clearModel.getPlat_no(),start,end,i, this);
            }
            nodes.put(i,node);
            start = end;
            end = start + step;
        }
        maxId = end;
    }

    /**
     * 获取最大查询值
     * @return
     */
    public long getMergeListMaxId(){
        for(int i=nodeState.length-1;i>=0;i--){
            List<EaccAccountamtlist> list = nodes.get(i).getHandData();
            if(list.size()>0){
                return list.get(list.size()-1).getId();
            }
        }
        return -1;
    }

    /**
     * 获取所有元素
     * @return
     */
    public long getAllElement(){
        int n = 0;
        for(int i=nodeState.length-1;i>=0;i--){
            List<EaccAccountamtlist> list = nodes.get(i).getHandData();
            n += list.size();
        }
        return n;
    }

    public void asynExecute() {
        int latchCount = getLatchCount();
        if(latchCount==0 ) return;//所有任务都执行完毕,退出
        if(tryCount>MAX_TRY_COUNT) throw new BusinessException("查询流水失败");
        tryCount++;
        latch = new CountDownLatch(latchCount);
        for(int i=0;i<nodeState.length;i++){
            if(nodeState[i] == 0){
                BillThreadPool.getThreadPool().addTask(nodes.get(i));
            }
        }
        try {
            latch.await(10L, TimeUnit.MINUTES);
            asynExecute();
        } catch (InterruptedException e) {
            throw new BusinessException(e);
        }
    }
    
    public ConcurrentHashMap<Integer, AbstractListNode> getNodes() {
        return nodes;
    }

    public int[] getNodeState() {
        return nodeState;
    }

    public int getLatchCount() {
        int n = 0;
        for(int i=0;i<nodeState.length;i++) {
            if(nodeState[i] == 0){
                n++;
            }
        }
        return n;
    }

    public long getMaxId() {
        return maxId;
    }

    public long getMinId() {
        return minId;
    }

    public long getStep() {
        return step;
    }

    public ClearModel getClearModel() {
        return clearModel;
    }

    public CountDownLatch getLatch() {
        return latch;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
