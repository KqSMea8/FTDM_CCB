package com.sunyard.sunfintech.listcheck.service;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.dic.FlowStatus;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.ClearListCheckResultMapper;
import com.sunyard.sunfintech.dao.mapper.EaccAccountamtlistMapper;
import com.sunyard.sunfintech.dao.mapper.ReqLogMapper;
import com.sunyard.sunfintech.listcheck.model.ListCheckResult;
import com.sunyard.sunfintech.listcheck.model.TransAmtCheckContent;
import com.sunyard.sunfintech.listcheck.strategy.ListCheckStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by terry on 2018/6/25.
 */
@Service
public class ListCheckService implements IListCheckService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private EaccAccountamtlistMapper eaccAccountamtlistMapper;

    @Autowired
    private ReqLogMapper reqLogMapper;

    @Autowired
    private ClearListCheckResultMapper clearListCheckResultMapper;

    @Autowired
    private ListCheckStrategy listCheckStrategy;

    @Override
    public boolean doCheck(List<TransTransreq> transTransreqList) throws BusinessException {
        List<String> trans_serial_list=new ArrayList<>();
        List<String> order_no_list=new ArrayList<>();
        for(TransTransreq transTransreq:transTransreqList){
            trans_serial_list.add(transTransreq.getTrans_serial());
            order_no_list.add(transTransreq.getOrder_no());
        }
        List<EaccAccountamtlist> accountamtlistAll=getAmtList(trans_serial_list);
        Map<String,List<EaccAccountamtlist>> accountamtlistMap=new HashMap<>();
        for(EaccAccountamtlist accountamtlist:accountamtlistAll){
            String trans_serial=accountamtlist.getTrans_serial();
            List<EaccAccountamtlist> nowAmtlist=accountamtlistMap.get(trans_serial);
            if(nowAmtlist==null){
                nowAmtlist=new ArrayList<>();
                nowAmtlist.add(accountamtlist);
                accountamtlistMap.put(trans_serial,nowAmtlist);
            }else{
                nowAmtlist.add(accountamtlist);
            }
        }
        List<ReqLogWithBLOBs> reqLogAll=getReqLogAll(order_no_list);
        Map<String,ReqLogWithBLOBs> reqLogMap=new HashMap<>();
        for(ReqLogWithBLOBs reqLog:reqLogAll){
            ReqLogWithBLOBs oldReqLog=reqLogMap.get(reqLog.getOrder_no());
            if(oldReqLog!=null){
                if(!oldReqLog.getMethod().equals(reqLog.getMethod()) && reqLog.getMethod().equals("confirmQuickPayRewrite")){
                    //如果方法名称不相同，去除快捷确认的req_log。
                    continue;
                }else if(oldReqLog.getMethod().equals(reqLog.getMethod()) && oldReqLog.getCreate_time().getTime()>reqLog.getCreate_time().getTime()){
                    //如果方法名称相同，以最后一次提交的请求数据为准。
                    continue;
                }
            }
            reqLogMap.put(reqLog.getOrder_no(),reqLog);
        }

        for(TransTransreq transTransreq:transTransreqList){
            //对账结果对象
            ClearListCheckResult clearListCheckResult=new ClearListCheckResult();
            clearListCheckResult.setOrder_no(transTransreq.getOrder_no());
            clearListCheckResult.setSer_no(transTransreq.getTrans_serial());
            clearListCheckResult.setTrans_code(transTransreq.getTrans_code());
            clearListCheckResult.setCheck_time(new Date());
            //查询资金流水
            if(FlowStatus.INPROCESS.getCode().equals(transTransreq.getStatus())){
                //处理中订单
                clearListCheckResult.setChenk_result(String.valueOf(ListCheckResult.ORDER_PROCESS.getCode()));
                clearListCheckResult.setSuggestion(ListCheckResult.ORDER_PROCESS.getMsg());
                clearListCheckResult.setRecheck("1");
            }else{
                try{
                    List<EaccAccountamtlist> accountamtlists=accountamtlistMap.get(transTransreq.getTrans_serial());
                    if(accountamtlists!=null && accountamtlists.size()>0){
                        if(FlowStatus.SUCCESS.getCode().equals(transTransreq.getStatus())){
                            ReqLogWithBLOBs reqLog=reqLogMap.get(transTransreq.getOrder_no());
                            if(reqLog!=null && StringUtils.isNotBlank(reqLog.getReq_param())){
                                TransAmtCheckContent amtListCheckResult=listCheckStrategy.getAmtListCheckResult(transTransreq.getTrans_code(),accountamtlists);
                                TransAmtCheckContent reqLogListCheckResult=listCheckStrategy.getReqLogListCheckResult(transTransreq.getTrans_code(),reqLog.getReq_param());
                                int resultCode=amtListCheckResult.compareTo(reqLogListCheckResult);
                                logger.info("【数据比对】比对结果：{}", ListCheckResult.GETMSG.getMsgByCode(resultCode));
                                clearListCheckResult.setCheck_content_amt(JSON.toJSONString(amtListCheckResult));
                                clearListCheckResult.setCheck_content_req(JSON.toJSONString(reqLogListCheckResult));
                                clearListCheckResult.setChenk_result(String.valueOf(resultCode));
                                clearListCheckResult.setSuggestion("成功订单，"+(ListCheckResult.EQUAL.getCode()==resultCode?ListCheckResult.EQUAL.getMsg():ListCheckResult.GETMSG.getMsgByCode(resultCode)));
                                clearListCheckResult.setRecheck(ListCheckResult.EQUAL.getCode()==resultCode?"0":"1");
                            }else{
                                clearListCheckResult.setChenk_result(String.valueOf(ListCheckResult.NO_REQLOG.getCode()));
                                clearListCheckResult.setSuggestion(ListCheckResult.NO_REQLOG.getMsg());
                                clearListCheckResult.setRecheck("1");
                            }
                        }else{
                            //订单为失败，却有资金流水
                            clearListCheckResult.setChenk_result(String.valueOf(ListCheckResult.AMT_LIST_NO_ROLLBACK.getCode()));
                            clearListCheckResult.setSuggestion(ListCheckResult.AMT_LIST_NO_ROLLBACK.getMsg()+"，回滚资金流水");
                            clearListCheckResult.setRecheck("1");
                        }
                    }else{
                        //订单为成功，资金流水缺失
                        if(FlowStatus.SUCCESS.getCode().equals(transTransreq.getStatus())){
                            clearListCheckResult.setChenk_result(String.valueOf(ListCheckResult.NO_AMT_LIST.getCode()));
                            clearListCheckResult.setSuggestion(ListCheckResult.NO_AMT_LIST.getMsg()+"，补充资金流水");
                            clearListCheckResult.setRecheck("1");
                        }else{
                            clearListCheckResult.setChenk_result(String.valueOf(ListCheckResult.EQUAL.getCode()));
                            clearListCheckResult.setSuggestion("失败订单，"+ListCheckResult.EQUAL.getMsg());
                            clearListCheckResult.setRecheck("0");
                        }
                    }
                    //如果为标的订单(投资，债转，标的还款，标的成立，投资撤销)，检查投资明细和份额
                    if(TransConsts.BUY_CODE.equals(transTransreq.getTrans_code()) || TransConsts.TRANSFERDEBT_CODE.equals(transTransreq.getTrans_code())
                            || TransConsts.PRODREPAY_CODE.equals(transTransreq.getTrans_code()) || TransConsts.ESTAB_CODE.equals(transTransreq.getTrans_code())
                            || TransConsts.INVEST_ABORT_CODE.equals(transTransreq.getTrans_code())){
                        logger.info("【数据比对】该交易涉及到份额变动，需检查份额数据");
                        //TODO 暂不实现
                    }
                }catch (Exception e){
                    logger.error("【数据比对】异常",e);
                    clearListCheckResult.setChenk_result(String.valueOf(ListCheckResult.ERROR.getCode()));
                    clearListCheckResult.setSuggestion(e.getMessage());
                    clearListCheckResult.setRecheck("1");
                }

            }
            insertResult(clearListCheckResult);
        }
        return true;
    }

    private List<EaccAccountamtlist> getAmtList(String trans_serial){
        EaccAccountamtlistExample example=new EaccAccountamtlistExample();
        EaccAccountamtlistExample.Criteria criteria=example.createCriteria();
        criteria.andTrans_serialEqualTo(trans_serial);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        return eaccAccountamtlistMapper.selectByExample(example);
    }

    private List<EaccAccountamtlist> getAmtList(List<String> trans_serial_list){
        EaccAccountamtlistExample example=new EaccAccountamtlistExample();
        EaccAccountamtlistExample.Criteria criteria=example.createCriteria();
        criteria.andTrans_serialIn(trans_serial_list);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        return eaccAccountamtlistMapper.selectByExample(example);
    }

    private List<ReqLogWithBLOBs> getReqLogAll(List<String> order_no_list){
        ReqLogExample example=new ReqLogExample();
        ReqLogExample.Criteria criteria=example.createCriteria();
        criteria.andOrder_noIn(order_no_list);
        criteria.andRemarkEqualTo("begin");
        List<ReqLogWithBLOBs> reqLogList=reqLogMapper.selectByExampleWithBLOBs(example);
        return reqLogList;
    }

    private int insertResult(ClearListCheckResult clearListCheckResult){
        List<ClearListCheckResult> clearListCheckResultList=queryResult(clearListCheckResult.getOrder_no());
        if(clearListCheckResultList.size()>0){
            return updateResult(clearListCheckResult);
        }else{
            clearListCheckResult.setEnabled(Constants.ENABLED);
            clearListCheckResult.setCreate_by("Admin");
            clearListCheckResult.setCreate_time(new Date());
            return clearListCheckResultMapper.insert(clearListCheckResult);
        }
    }

    private List<ClearListCheckResult> queryResult(String order_no){
        ClearListCheckResultExample example=new ClearListCheckResultExample();
        ClearListCheckResultExample.Criteria criteria=example.createCriteria();
        criteria.andEnabledEqualTo(Constants.ENABLED);
        criteria.andOrder_noEqualTo(order_no);
        return clearListCheckResultMapper.selectByExample(example);
    }

    private int updateResult(ClearListCheckResult clearListCheckResult){
        ClearListCheckResultExample example=new ClearListCheckResultExample();
        ClearListCheckResultExample.Criteria criteria=example.createCriteria();
        criteria.andEnabledEqualTo(Constants.ENABLED);
        criteria.andOrder_noEqualTo(clearListCheckResult.getOrder_no());
        return clearListCheckResultMapper.updateByExampleSelective(clearListCheckResult,example);
    }

}
