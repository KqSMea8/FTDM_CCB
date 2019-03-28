package com.sunyard.sunfintech.listcheck.strategy;

import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import com.sunyard.sunfintech.listcheck.model.AmtListStrategy;
import com.sunyard.sunfintech.listcheck.model.ReqLogStrategy;
import com.sunyard.sunfintech.listcheck.model.TransAmtCheckContent;

import java.util.List;

/**
 * @author heroy
 * @version 2018/6/13
 */
public interface IListCheckStrategy {

    /**
     * 获取流水策略
     * 第一次从数据库中读取配置，保存缓存中
     * @param transCode
     * @return
     */
    public AmtListStrategy getAmtListStrategy(String transCode);

    /**
     * 获取请求日志策略
     * @param transCode
     * @return
     */
    public ReqLogStrategy getReqLogStrategy(String transCode);

    /**
     * 获取流水对账
     * @param transCode
     * @param eaccAccountamtlist
     * @return
     */
    public TransAmtCheckContent getAmtListCheckResult(String transCode, List<EaccAccountamtlist> eaccAccountamtlist);

    /**
     * 获取reqlog的对账数据
     * @param transCode
     * @param req_param
     * @return
     */
    public TransAmtCheckContent getReqLogListCheckResult(String transCode, String req_param);


}
