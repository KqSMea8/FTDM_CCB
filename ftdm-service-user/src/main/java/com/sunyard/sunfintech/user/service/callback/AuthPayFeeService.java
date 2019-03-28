package com.sunyard.sunfintech.user.service.callback;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.core.dic.FlowStatus;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.dao.entity.PayfeeInfo;
import com.sunyard.sunfintech.dao.entity.PayfeeInfoExample;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.dao.mapper.PayfeeInfoMapper;
import com.sunyard.sunfintech.pub.model.NotifyData;
import com.sunyard.sunfintech.pub.provider.INotifyService;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import com.sunyard.sunfintech.user.model.bo.PayFeeNotify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 【功能描述】
 *
 * @author wyc  2018/2/11.
 */
@Service
public class AuthPayFeeService extends BaseServiceSimple implements  TransRouter {

    @Autowired
    private ITransReqService transReqService;

    @Autowired
    private INotifyService notifyService;

    @Autowired
    private PayfeeInfoMapper payfeeInfoMapper;

    @Override
    public void onCallBack(TransTransreq transTransreq) {
        transReqService.insert(transTransreq);
        logger.info(String.format("【User异步通知接收-%s】|trans_serial:%s|status:%s|return_code:%s|return_msg:%s。更新成功",
                TransConsts.AUTH_PAY_FEE_NAME, transTransreq.getTrans_serial(), transTransreq.getStatus(), transTransreq.getReturn_code(), transTransreq.getReturn_msg()));
        //updateParentTrans(transTransreq);
        if(FlowStatus.FAIL.getCode().equals(transTransreq.getStatus())){
            //如果转账失败，回滚缴费信息
            PayfeeInfo payfeeInfo=queryPayfeeInfo(transTransreq.getTrans_serial());
            logger.info("【User异步通知接收-缴费】payfeeInfo="+ JSON.toJSONString(payfeeInfo));
            if(payfeeInfo!=null){
                payfeeInfo.setEnabled(Constants.DISABLED);
                logger.info("【User异步通知接收-缴费】payfeeInfo="+ JSON.toJSONString(payfeeInfo));
                payfeeInfoMapper.updateByPrimaryKey(payfeeInfo);
                //回滚限额
                if(StringUtils.isBlank(payfeeInfo.getProd_id())){
                    logger.info("【User异步通知接收-缴费】缴费到平台，回滚限额|plat_no:{}",payfeeInfo.getPlat_no());
                    String payFeeToPlatRedisKey=Constants.PAY_FEE_DAY_LIMIT_KEY+payfeeInfo.getPlat_no()+ DateUtils.getyyyyMMddDate();
                    String bd100=String.valueOf(payfeeInfo.getAmt().multiply(new BigDecimal(100)));
                    CacheUtil.getCache().increment(payFeeToPlatRedisKey,0L-Long.parseLong(bd100.indexOf(".")>0?bd100.substring(0,bd100.indexOf(".")):bd100));
                }
            }

        }
        notify(transTransreq);
    }

    private void updateParentTrans(TransTransreq callbackTrans) {
        try {
            TransTransreq cachedTrans = transReqService.queryTransTransReqByOrderNoAndBatchOrderNo(callbackTrans.getTrans_serial(), callbackTrans.getOrder_no());
            if (cachedTrans == null) {
                logger.error(String.format("【User异步通知接收-授权缴费】|trans_serial:%s|status:%s|return_code:%s|return_msg:%s。查找不到该流水",
                        callbackTrans.getTrans_serial(), callbackTrans.getStatus(), callbackTrans.getReturn_code(), callbackTrans.getReturn_msg()));
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR));
            }
            if (StringUtils.isNotBlank(cachedTrans.getBatch_order_no())) {
                List<TransTransreq> batchOrderTrans = transReqService.queryTransTransReq(cachedTrans.getBatch_order_no());
                if (batchOrderTrans != null && batchOrderTrans.size() > 0) {
                    boolean isNeedUpdateBatch = true;
                    TransTransreq batchTran = null;
                    for (int i = 0; i < batchOrderTrans.size(); i++) {
                        if (batchOrderTrans.get(i).getOrder_no().equals(cachedTrans.getBatch_order_no())) {
                            batchTran = batchOrderTrans.get(i);
                            continue;
                        }
                        if (batchOrderTrans.get(i).getStatus() != null && !batchOrderTrans.get(i).getStatus().equals(callbackTrans.getStatus())) {
                            isNeedUpdateBatch = false;
                        }
                    }
                    if (isNeedUpdateBatch && batchTran != null) {
                        batchTran.setStatus(callbackTrans.getStatus());
                        transReqService.insert(batchTran);
                    }
                    logger.info(String.format("【User异步通知接收-授权缴费】|trans_serial:%s|status:%s|return_code:%s|return_msg:%s。isNeedUpdateBatch=%s,batchTranorderno=%s",
                            callbackTrans.getTrans_serial(), callbackTrans.getStatus(), callbackTrans.getReturn_code(), callbackTrans.getReturn_msg(), isNeedUpdateBatch, batchTran.getOrder_no()));
                }
            } else {
                logger.error(String.format("【User异步通知接收-授权缴费】|trans_serial:%s|status:%s|return_code:%s|return_msg:%s。找不到批次订单号",
                        callbackTrans.getTrans_serial(), callbackTrans.getStatus(), callbackTrans.getReturn_code(), callbackTrans.getReturn_msg()));
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void notify(TransTransreq callbackTrans) {
        try {
            TransTransreq cachedTrans = transReqService.queryTransTransReqByOrderNoAndBatchOrderNo(callbackTrans.getTrans_serial(), callbackTrans.getOrder_no());
            if (cachedTrans == null) {
                logger.error(String.format("【User异步通知接收-授权缴费】|trans_serial:%s|status:%s|return_code:%s|return_msg:%s。查找不到该流水",
                        callbackTrans.getTrans_serial(), callbackTrans.getStatus(), callbackTrans.getReturn_code(), callbackTrans.getReturn_msg()));
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR));
            }
            String key = Constants.AUTHPAYFEE_ORDER_KEY + serviceName + "_order:" + callbackTrans.getOrder_no();
            String notifyURL = cachedTrans.getNotify_url();
            String notifyContent = (String) CacheUtil.getCache().get(key);
            logger.info(String.format("【User异步通知接收-授权缴费回调-通知】|trans_serial:%s|status:%s|return_code:%s|return_msg:%s。notifyurl：%s，notifyInfo：%s",
                    callbackTrans.getTrans_serial(), callbackTrans.getStatus(), callbackTrans.getReturn_code(), callbackTrans.getReturn_msg(), notifyURL, notifyContent));
            if (StringUtils.isNotBlank(notifyURL) && StringUtils.isNotBlank(notifyContent)) {
                NotifyData notifyData = new NotifyData();
                notifyData.setNotifyUrl(notifyURL);
                PayFeeNotify cacheNotifyInfo = JSON.parseObject(notifyContent, PayFeeNotify.class);
                if (cacheNotifyInfo != null) {
                    notifyData.setMall_no(cacheNotifyInfo.getMall_no());
                    cacheNotifyInfo.setOrder_no(callbackTrans.getOrder_no());
                    cacheNotifyInfo.setOrder_status(callbackTrans.getStatus());
                    cacheNotifyInfo.setRecode(callbackTrans.getReturn_code());
                    cacheNotifyInfo.setRemsg(callbackTrans.getReturn_msg());
                    notifyData.setNotifyContent(JSON.toJSONString(cacheNotifyInfo, GlobalConfig.serializerFeature));
                    notifyService.addNotify(notifyData);
                    logger.info("【User异步通知接收-授权缴费回调-通知】|trans_serial:%s|status:%s|return_code:%s|return_msg:%s。notifyurl：%s，notifyInfo：%s,进入notify队列成功",
                            callbackTrans.getTrans_serial(), callbackTrans.getStatus(), callbackTrans.getReturn_code(), callbackTrans.getReturn_msg(), notifyURL, notifyContent);
                } else {
                    logger.info("【User异步通知接收-授权缴费回调-通知】|trans_serial:%s|status:%s|return_code:%s|return_msg:%s。notifyurl：%s，notifyInfo：%s,反序列化失败，不进notify队列",
                            callbackTrans.getTrans_serial(), callbackTrans.getStatus(), callbackTrans.getReturn_code(), callbackTrans.getReturn_msg(), notifyURL, notifyContent);

                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private PayfeeInfo queryPayfeeInfo(String trans_serial){
        PayfeeInfoExample example =new PayfeeInfoExample();
        PayfeeInfoExample.Criteria criteria=example.createCriteria();
        criteria.andEnabledEqualTo(Constants.ENABLED);
        criteria.andTrans_serialEqualTo(trans_serial);
        List<PayfeeInfo> payfeeInfoList=payfeeInfoMapper.selectByExample(example);
        if(payfeeInfoList.size()>0){
            return payfeeInfoList.get(0);
        }
        return null;
    }
}
