package com.sunyard.sunfintech.billcheck.handler.node;

import com.sunyard.sunfintech.billcheck.handler.QueryListHandler;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public abstract class AbstractListNode<T> implements Runnable{

    protected QueryListHandler handler;
    protected long end ;
    protected long start ;
    protected int id ;
    public AbstractListNode(long start, long end, int id, QueryListHandler handler) {
        this.start = start;
        this.end = end;
        this.id = id;
        this.handler = handler;
    }
    //private boolean status = false;//是否获取到数据
    List<T> handData;


    @Override
    public abstract void run() ;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public long getEnd() {
        return end;
    }

    public long getStart() {
        return start;
    }

    public int getId() {
        return id;
    }

//    public boolean isStatus() {
//        return status;
//    }

    public List<T> getHandData() {
        return handData;
    }

    protected Logger logger = LogManager.getLogger(getClass());

}