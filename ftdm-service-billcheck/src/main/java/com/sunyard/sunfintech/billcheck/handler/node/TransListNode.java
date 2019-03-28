package com.sunyard.sunfintech.billcheck.handler.node;

import com.sunyard.sunfintech.billcheck.handler.QueryListHandler;
import com.sunyard.sunfintech.billcheck.model.ClearModel;
import com.sunyard.sunfintech.core.util.SpringContextHolder;
import com.sunyard.sunfintech.custdao.mapper.CustEaccAccountamtlistMapper;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TransListNode extends AbstractListNode {


    public TransListNode(long start,long end,int id,QueryListHandler handler) {
        super(start,end,id,handler);
    }

    @Override
    public void run() {
        try {
            logger.info("该查询片信息："+this);
            ClearModel clearModel = handler.getClearModel();
            CustEaccAccountamtlistMapper custEaccAccountamtlistMapper = SpringContextHolder.getBean(CustEaccAccountamtlistMapper.class);
            List<EaccAccountamtlist> nodeList = custEaccAccountamtlistMapper.getTransferList(
                    clearModel.getPlat_no(),start,end);
            handData = nodeList;
            handler.getNodeState()[id] = 1;//设置为获取到数据
        }catch (Exception e){
            logger.error("==========分片执行错误",e);
        }finally {
            handler.getLatch().countDown();
        }


    }

    protected Logger logger = LogManager.getLogger(getClass());

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}