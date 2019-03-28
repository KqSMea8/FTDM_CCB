package com.sunyard.sunfintech.user.service.callback;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.FlowStatus;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.dao.entity.PayfeeInfo;
import com.sunyard.sunfintech.dao.entity.PayfeeInfoExample;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.dao.mapper.PayfeeInfoMapper;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
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
public class PayfeeService  extends BaseServiceSimple implements  TransRouter {
    @Autowired
    private ITransReqService transReqService;

    @Autowired
    private PayfeeInfoMapper payfeeInfoMapper;

    @Override
    public void onCallBack(TransTransreq transTransreq) {
        transReqService.insert(transTransreq);
        logger.info(String.format("【User异步通知接收-缴费】|trans_serial:%s|status:%s|return_code:%s|return_msg:%s。更新成功",
                transTransreq.getTrans_serial(), transTransreq.getStatus(), transTransreq.getReturn_code(), transTransreq.getReturn_msg()));
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
