package com.sunyard.sunfintech.billcheck.handler.node;

import com.sunyard.sunfintech.billcheck.handler.QueryListHandler;
import com.sunyard.sunfintech.core.util.SpringContextHolder;
import com.sunyard.sunfintech.custdao.mapper.CustAccountSubjectInfoMapper;
import com.sunyard.sunfintech.dao.entity.AccountSubjectInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AccountListNode extends AbstractListNode {


    private String plat_no = null;

    public AccountListNode(String plat_no,long start, long end, int id, QueryListHandler handler) {
        super(start,end,id,handler);
        this.plat_no = plat_no;
    }

    @Override
    public void run() {
        //logger.error("runninggggggggggggggggggggggggggggggggggg");
        try {
            logger.info("该查询片信息："+this);
            //Thread.sleep(60*1000);
            CustAccountSubjectInfoMapper custAccountSubjectInfoMapper = SpringContextHolder.getBean(CustAccountSubjectInfoMapper.class);
            List<AccountSubjectInfo> nodeList = custAccountSubjectInfoMapper.getFloatSubjectGreaterZero(
                   plat_no,start,end);
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