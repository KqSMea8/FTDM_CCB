package com.sunyard.sunfintech.listcheck.strategy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.dic.AmtType;
import com.sunyard.sunfintech.core.dic.StrategyType;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.NumberUtils;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.ClearListCheckConfMapper;
import com.sunyard.sunfintech.dao.mapper.ReqLogMapper;
import com.sunyard.sunfintech.dao.mapper.TransTransreqMapper;
import com.sunyard.sunfintech.listcheck.model.AmtListStrategy;
import com.sunyard.sunfintech.listcheck.model.ReqLogStrategy;
import com.sunyard.sunfintech.listcheck.model.SubSubjectDirection;
import com.sunyard.sunfintech.listcheck.model.TransAmtCheckContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author heroy
 * @version 2018/6/15
 */
@Service
public class ListCheckStrategy implements IListCheckStrategy {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ClearListCheckConfMapper clearListCheckConfMapper;

    @Autowired
    private ReqLogMapper reqLogMapper;

    @Autowired
    private TransTransreqMapper transTransreqMapper;

    @Override
    public AmtListStrategy getAmtListStrategy(String transCode) {
        List<ClearListCheckConf> checkConfList=queryConf(transCode,StrategyType.AMTLIST.getCode());
        if(checkConfList.size()==0){
            throw new BusinessException(BusinessMsg.NOT_FUND_CONFIG,"AmtListStrategy："+BusinessMsg.getMsg(BusinessMsg.NOT_FUND_CONFIG));
        }
        AmtListStrategy amtListStrategy=new AmtListStrategy();
        ClearListCheckConf checkConf=checkConfList.get(0);
        String confStr=checkConf.getConf();
        JSONObject confObject= JSON.parseObject(confStr);
        amtListStrategy.setTransCode(transCode);
        Map<String,Object> directionMap=getDirection(confObject);
        amtListStrategy.setAmt((SubSubjectDirection) directionMap.get("amt"));
        amtListStrategy.setFee((SubSubjectDirection) directionMap.get("fee"));
        amtListStrategy.setGift((SubSubjectDirection) directionMap.get("gift"));
        amtListStrategy.setInterest((SubSubjectDirection) directionMap.get("interest"));
        return amtListStrategy;
    }

    @Override
    public ReqLogStrategy getReqLogStrategy(String transCode) {
        List<ClearListCheckConf> checkConfList=queryConf(transCode,StrategyType.REQLOG.getCode());
        if(checkConfList.size()==0){
            throw new BusinessException(BusinessMsg.NOT_FUND_CONFIG,"ReqLogStrategy："+BusinessMsg.getMsg(BusinessMsg.NOT_FUND_CONFIG));
        }
        ReqLogStrategy reqLogStrategy=new ReqLogStrategy();
        ClearListCheckConf checkConf=checkConfList.get(0);
        String confStr=checkConf.getConf();
        JSONObject confObject= JSON.parseObject(confStr);
        reqLogStrategy.setTransCode(transCode);
        Map<String,Object> directionMap=getDirection(confObject);
        reqLogStrategy.setAmt(directionMap.get("amt")==null?null:(String) directionMap.get("amt"));
        reqLogStrategy.setFee(directionMap.get("fee")==null?null:(String) directionMap.get("fee"));
        reqLogStrategy.setGift(directionMap.get("gift")==null?null:(String) directionMap.get("gift"));
        reqLogStrategy.setInterest(directionMap.get("interest")==null?null:(String) directionMap.get("interest"));
        reqLogStrategy.setLog_type(directionMap.get("log_type")==null?null:(String) directionMap.get("log_type"));
        reqLogStrategy.setOrigin_order_no(directionMap.get("origin_order_no")==null?null:(String) directionMap.get("origin_order_no"));
        return reqLogStrategy;
    }

    @Override
    public TransAmtCheckContent getAmtListCheckResult(String transCode, List<EaccAccountamtlist> eaccAccountamtlists) {
        AmtListStrategy amtListStrategy=getAmtListStrategy(transCode);
        TransAmtCheckContent transAmtCheckContent=new TransAmtCheckContent();
        logger.info("【获取资金流水中的对账参数】资金流水号：{}|对账参数策略：{}",
                eaccAccountamtlists.get(0).getTrans_serial(),JSON.toJSONString(amtListStrategy));
        for(EaccAccountamtlist eaccAccountamtlist:eaccAccountamtlists){
            logger.info("【获取资金流水中的对账参数】资金流水|trans_serial:{}|subject:{}|sub_subject:{}|" +
                    "oppo_subject:{}|oppo_sub_subject:{}|amt:{}",eaccAccountamtlist.getTrans_serial(),
                    eaccAccountamtlist.getSubject(), eaccAccountamtlist.getSub_subject(),
                    eaccAccountamtlist.getOppo_subject(),eaccAccountamtlist.getOppo_sub_subject(),
                    eaccAccountamtlist.getAmt());
            if(AmtType.EXPENSIVE.getCode().equals(eaccAccountamtlist.getAmt_type())
                    && StringUtils.isNotBlank(eaccAccountamtlist.getOppo_sub_subject())){
                //判断本金
                SubSubjectDirection amtDirection=amtListStrategy.getAmt();
                if(amtDirection!=null && amtDirection.getTo_subject()!=null){
                    String[] subject_from_array=amtDirection.getSubject_from().split(",");
                    List<String> subject_from_list= Arrays.asList(subject_from_array);
                    String[] subject_to_array=amtDirection.getTo_subject().split(",");
                    List<String> subject_to_list= Arrays.asList(subject_to_array);
                    if((subject_from_list.indexOf(eaccAccountamtlist.getSub_subject())>=0
                            || "A".equals(amtDirection.getSubject_from()))
                            && (subject_to_list.indexOf(eaccAccountamtlist.getOppo_sub_subject())>=0
                            || "A".equals(amtDirection.getTo_subject()))){
                        logger.info("【获取资金流水中的对账参数】报告大人，我是本金|trans_serial：{}",
                                eaccAccountamtlist.getTrans_serial());
                        transAmtCheckContent.setAmt(transAmtCheckContent.getAmt().add(eaccAccountamtlist.getAmt()));
                        continue;
                    }
                }
                //判断手续费
                SubSubjectDirection feeDirection=amtListStrategy.getFee();
                if(feeDirection!=null){
                    String[] subject_from_array=feeDirection.getSubject_from().split(",");
                    List<String> subject_from_list= Arrays.asList(subject_from_array);
                    String[] subject_to_array=feeDirection.getTo_subject().split(",");
                    List<String> subject_to_list= Arrays.asList(subject_to_array);
                    if((subject_from_list.indexOf(eaccAccountamtlist.getSub_subject())>=0
                            || "A".equals(feeDirection.getSubject_from()))
                            && (subject_to_list.indexOf(eaccAccountamtlist.getOppo_sub_subject())>=0
                            || "A".equals(feeDirection.getTo_subject()))){
                        logger.info("【获取资金流水中的对账参数】报告大人，我是手续费|trans_serial：{}",
                                eaccAccountamtlist.getTrans_serial());
                        transAmtCheckContent.setFee(transAmtCheckContent.getFee().add(eaccAccountamtlist.getAmt()));
                        continue;
                    }
                }
                //判断红包
                SubSubjectDirection giftDirection=amtListStrategy.getGift();
                if(giftDirection!=null){
                    String[] subject_from_array=giftDirection.getSubject_from().split(",");
                    List<String> subject_from_list= Arrays.asList(subject_from_array);
                    String[] subject_to_array=giftDirection.getTo_subject().split(",");
                    List<String> subject_to_list= Arrays.asList(subject_to_array);
                    if((subject_from_list.indexOf(eaccAccountamtlist.getSub_subject())>=0
                            || "A".equals(giftDirection.getSubject_from()))
                            && (subject_to_list.indexOf(eaccAccountamtlist.getOppo_sub_subject())>=0
                            || "A".equals(giftDirection.getTo_subject()))){
                        logger.info("【获取资金流水中的对账参数】报告大人，我是红包|trans_serial：{}",
                                eaccAccountamtlist.getTrans_serial());
                        transAmtCheckContent.setGift(transAmtCheckContent.getGift().add(eaccAccountamtlist.getAmt()));
                        continue;
                    }
                }
                //判断利息
                SubSubjectDirection interestDirection=amtListStrategy.getInterest();
                if(interestDirection!=null){
                    String[] subject_from_array=interestDirection.getSubject_from().split(",");
                    List<String> subject_from_list= Arrays.asList(subject_from_array);
                    String[] subject_to_array=interestDirection.getTo_subject().split(",");
                    List<String> subject_to_list= Arrays.asList(subject_to_array);
                    if((subject_from_list.indexOf(eaccAccountamtlist.getSub_subject())>=0
                            || "A".equals(interestDirection.getSubject_from()))
                            && (subject_to_list.indexOf(eaccAccountamtlist.getOppo_sub_subject())>=0
                            || "A".equals(interestDirection.getTo_subject()))){
                        logger.info("【获取资金流水中的对账参数】报告大人，我是利息|trans_serial：{}",
                                eaccAccountamtlist.getTrans_serial());
                        transAmtCheckContent.setInterest(transAmtCheckContent.getInterest().add(eaccAccountamtlist.getAmt()));
                    }
                }
            }else{
                //单边账(充值、提现)
                if(StringUtils.isBlank(eaccAccountamtlist.getOppo_sub_subject())){
                    if(TransConsts.PLAT_MADDLE_ACCOUNTS_CODE.equals(eaccAccountamtlist.getTrans_code())
                            || TransConsts.PLAT_WITHDRAW_CODE.equals(eaccAccountamtlist.getTrans_code())){
                        //提现
                        logger.info("【获取资金流水中的对账参数】报告大人，我是提现本金|trans_serial：{}",
                                eaccAccountamtlist.getTrans_serial());
                        transAmtCheckContent.setAmt(transAmtCheckContent.getAmt().add(eaccAccountamtlist.getAmt()));
                    }else{
                        //充值
                        logger.info("【获取资金流水中的对账参数】报告大人，我是充值本金|trans_serial：{}",
                                eaccAccountamtlist.getTrans_serial());
                        transAmtCheckContent.setAmt(transAmtCheckContent.getAmt().add(eaccAccountamtlist.getAmt()));
                    }
                }
            }
        }
        logger.info("【获取资金流水中的对账参数】对账参数：{}",JSON.toJSONString(transAmtCheckContent));
        return transAmtCheckContent;
    }

    @Override
    public TransAmtCheckContent getReqLogListCheckResult(String transCode, String req_param) {
        TransAmtCheckContent transAmtCheckContent=new TransAmtCheckContent();
        ReqLogStrategy reqLogStrategy=getReqLogStrategy(transCode);
        JSONObject reqParamsObj=JSON.parseObject(req_param);
        String order_no=reqParamsObj.getString("order_no");
        if(order_no==null){
            order_no=reqParamsObj.getString("detail_no");
        }
        if("1".equals(reqLogStrategy.getLog_type())){
            String origin_order_no_item=reqLogStrategy.getOrigin_order_no();
            String origin_order_no=reqParamsObj.getString(origin_order_no_item);
            //根据订单号取原req_log;
            ReqLogWithBLOBs reqLogWithBLOBs=getReqLog(origin_order_no);
            if(reqLogWithBLOBs==null){
                throw new BusinessException(BusinessMsg.ORDER_NOEXISTENT,"原订单的req_log不存在");
            }
            //查询原订单，获取trans_code
            TransTransreq transTransreq=queryTrans(origin_order_no);
            if(transTransreq==null){
                throw new BusinessException(BusinessMsg.ORDER_NOEXISTENT,"原订单业务流水不存在");
            }
            //重新获取策略及数据
            reqParamsObj=JSON.parseObject(reqLogWithBLOBs.getReq_param());
            reqLogStrategy=getReqLogStrategy(transTransreq.getTrans_code());
        }
        //本金
        logger.info("【获取req_log的值】获取本金|order_no：{}|策略：{}|req_log:{}",order_no,JSON.toJSONString(reqLogStrategy),req_param);
        if(StringUtils.isNotBlank(reqLogStrategy.getAmt())){
            String[] amtList=reqLogStrategy.getAmt().split(",");
            BigDecimal amt=BigDecimal.ZERO;
            for(String amtItem:amtList){
                logger.info("【获取req_log的值】amtItem：{}",amtItem);
                String[] amtIndexList=amtItem.split("\\.");
                amt=amt.add(getAmt(amtIndexList,reqParamsObj));
            }
            transAmtCheckContent.setAmt(amt);
        }
        //手续费
        logger.info("【获取req_log的值】获取手续费|order_no：{}",order_no);
        if(StringUtils.isNotBlank(reqLogStrategy.getFee())){
            BigDecimal fee=BigDecimal.ZERO;
            String[] feeList=reqLogStrategy.getFee().split(",");
            for(String feeItem:feeList){
                String[] feeIndexList=feeItem.split("\\.");
                fee=fee.add(getAmt(feeIndexList,reqParamsObj));
            }
            transAmtCheckContent.setFee(fee);
        }
        //红包
        logger.info("【获取req_log的值】获取红包|order_no：{}",order_no);
        if(StringUtils.isNotBlank(reqLogStrategy.getGift())){
            String[] giftIndexList=reqLogStrategy.getGift().split("\\.");
            BigDecimal gift=getAmt(giftIndexList,reqParamsObj);
            transAmtCheckContent.setGift(gift);
        }
        //利息
        logger.info("【获取req_log的值】获取利息|order_no：{}",order_no);
        if(StringUtils.isNotBlank(reqLogStrategy.getInterest())){
            String[] interestIndexList=reqLogStrategy.getInterest().split("\\.");
            BigDecimal interest=getAmt(interestIndexList,reqParamsObj);
            transAmtCheckContent.setInterest(interest);
        }

        return transAmtCheckContent;
    }

    private List<ClearListCheckConf> queryConf(String transCode,String strategyType){
        String keyStr="ftdm:listcheck:config:"+transCode+":"+strategyType;
        Object cacheObj= CacheUtil.getCache().get(keyStr);
        if(cacheObj!=null){
            List<ClearListCheckConf> checkConfList=new ArrayList<>();
            checkConfList.add(JSON.parseObject(cacheObj.toString(),ClearListCheckConf.class));
            return checkConfList;
        }
        ClearListCheckConfExample checkConfExample=new ClearListCheckConfExample();
        ClearListCheckConfExample.Criteria criteria=checkConfExample.createCriteria();
        criteria.andTrans_codeEqualTo(transCode);
        criteria.andTypeEqualTo(strategyType);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<ClearListCheckConf> configList=clearListCheckConfMapper.selectByExample(checkConfExample);
        CacheUtil.getCache().set(keyStr,JSON.toJSONString(configList.get(0)));
        return configList;
    }

    private Map<String,Object> getDirection(JSONObject confObject){
        Map<String,Object> resultMap=new HashMap<>();
        if(confObject.get("log_type")!=null){
            resultMap.put("log_type",confObject.get("log_type"));
        }
        if(confObject.get("origin_order_no")!=null){
            resultMap.put("origin_order_no",confObject.get("origin_order_no"));
        }
        //本金
        SubSubjectDirection amtDirection=new SubSubjectDirection();
        Object amtConfObj=confObject.get("amt");
        if(amtConfObj!=null){
            if(amtConfObj instanceof String){
                resultMap.put("amt",amtConfObj);
            }else{
                JSONObject amtConfObject= (JSONObject) amtConfObj;
                amtDirection.setRemark(amtConfObject.getString("remark"));
                amtDirection.setSubject_from(amtConfObject.getString("subject_from"));
                amtDirection.setTo_subject(amtConfObject.getString("to_subject"));
                resultMap.put("amt",amtDirection);
            }
        }
        //手续费
        SubSubjectDirection feeDirection=new SubSubjectDirection();
        Object feeConfObj=confObject.get("fee");
        if(feeConfObj!=null){
            if(feeConfObj instanceof String){
                resultMap.put("fee",feeConfObj);
            }else{
                JSONObject feeConfObject=confObject.getJSONObject("fee");
                feeDirection.setRemark(feeConfObject.getString("remark"));
                feeDirection.setSubject_from(feeConfObject.getString("subject_from"));
                feeDirection.setTo_subject(feeConfObject.getString("to_subject"));
                resultMap.put("fee",feeDirection);
            }
        }
        //红包
        SubSubjectDirection giftDirection=new SubSubjectDirection();
        Object giftConfObj=confObject.get("gift");
        if(giftConfObj!=null){
            if(giftConfObj instanceof String){
                resultMap.put("gift",giftConfObj);
            }else{
                JSONObject giftConfObject=confObject.getJSONObject("gift");
                giftDirection.setRemark(giftConfObject.getString("remark"));
                giftDirection.setSubject_from(giftConfObject.getString("subject_from"));
                giftDirection.setTo_subject(giftConfObject.getString("to_subject"));
                resultMap.put("gift",giftDirection);
            }
        }
        //利息
        SubSubjectDirection interestDirection=new SubSubjectDirection();
        Object interestConfObj=confObject.get("interest");
        if(interestConfObj!=null){
            if(interestConfObj instanceof String){
                resultMap.put("interest",interestConfObj);
            }else{
                JSONObject interestConfObject=confObject.getJSONObject("interest");
                interestDirection.setRemark(interestConfObject.getString("remark"));
                interestDirection.setSubject_from(interestConfObject.getString("subject_from"));
                interestDirection.setTo_subject(interestConfObject.getString("to_subject"));
                resultMap.put("interest",interestDirection);
            }
        }
        return resultMap;
    }

    private BigDecimal getAmt(String[] amtIndexList,JSONObject reqParamsObj){
        logger.info("【获取req_log的值】字段索引：{}|req_log：{}",
                JSON.toJSONString(amtIndexList),JSON.toJSON(reqParamsObj));
        BigDecimal amt=BigDecimal.ZERO;
        if(amtIndexList.length==0){
            return BigDecimal.ZERO;
        }else if(amtIndexList.length==1){
            Object amtObj=reqParamsObj.get(amtIndexList[0]);
            if(amtObj!=null){
                amt=amt.add(new BigDecimal(amtObj.toString()));
            }
//            amt=amt.add(reqParamsObj.getBigDecimal(amtIndexList[0]));
        }else{
            Object amtObject=reqParamsObj;
            for(String index:amtIndexList){
                if(amtObject!=null){
                    boolean isAdd=true;
                    if("-".equals(index.substring(0,1))){
                        isAdd=false;
                    }
                    String indexNew=index.replace("[","").replace("]","").replace("-","");
                    amtObject=((JSONObject)amtObject).get(indexNew);
                    if("[".equals(index.substring(0,1))){
                        amtObject=(JSON.parseArray(String.valueOf(amtObject))).get(0);
                    }
                    if(amtObject==null){
                        break;
                    }
                    if(isNumeric(amtObject.toString())){
                        if(isAdd){
                            amt=amt.add(new BigDecimal(amtObject.toString()));
                        }else{
                            amt=amt.subtract(new BigDecimal(amtObject.toString()));
                        }
                    }else {
                        amtObject=JSON.parseObject(amtObject.toString());
                    }
                }else{
                    break;
                }
            }
        }
        logger.info("【获取req_log的值】获取到的金额：{}",amt);
        return amt;
    }

    private ReqLogWithBLOBs getReqLog(String order_no){
        ReqLogExample example=new ReqLogExample();
        ReqLogExample.Criteria criteria=example.createCriteria();
        criteria.andOrder_noEqualTo(order_no);
        criteria.andRemarkEqualTo("begin");
        List<ReqLogWithBLOBs> reqLogList=reqLogMapper.selectByExampleWithBLOBs(example);
        if(reqLogList.size()>0){
            return reqLogList.get(0);
        }
        return null;
    }

    private TransTransreq queryTrans(String order_no){
        TransTransreqExample example=new TransTransreqExample();
        TransTransreqExample.Criteria criteria=example.createCriteria();
        criteria.andEnabledEqualTo(Constants.ENABLED);
        criteria.andService_nameNotEqualTo("account");
        criteria.andOrder_noEqualTo(order_no);
        List<TransTransreq> transreqList=transTransreqMapper.selectByExample(example);
        if(transreqList.size()>0){
            return transreqList.get(0);
        }
        return null;
    }

    private static boolean isNumeric(String str){
        return org.apache.commons.lang.math.NumberUtils.isNumber(str);
    }

    public static void main(String[] args) {
        JSONObject jsonObject=new JSONObject();
        Object obj=new Object();
        obj="1.11";
        System.out.println(isNumeric(obj.toString()));
        obj=jsonObject;
        System.out.println(isNumeric(obj.toString()));

    }
}
