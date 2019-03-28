package com.sunyard.sunfintech.user.modulepublic;

import com.alibaba.dubbo.config.annotation.Service;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.account.provider.IAccountTransferService;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.AmtType;
import com.sunyard.sunfintech.core.dic.FlowStatus;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.pub.provider.IOrderCheck;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by terry on 2017/9/12.
 */
@Service(interfaceClass = IOrderCheck.class)
@CacheConfig(cacheNames = "orderCheck")
@org.springframework.stereotype.Service
public class OrderCheck extends BaseServiceSimple implements IOrderCheck {

    @Autowired
    private ITransReqService transReqService;

    @Autowired
    private IAccountQueryService accountQueryService;

    @Autowired
    private IAccountTransferService accountTransferService;

    @Override
    public boolean checkOrder(String order_no) throws BusinessException {
        Boolean flag = true;
        if(!StringUtils.isEmpty(order_no)){
            //有就返回false，没有返回true
            flag = CacheUtil.getCache().setnx(order_no,order_no);
            if(flag){
                //之前订单不存在于redis
                logger.info("订单防重查询到订单已落redis，有效期30分钟，order_no="+order_no);
                //设置order_no键的有效时间为24小时，单位为秒
                CacheUtil.getCache().expire(order_no,20*60);
                //查库，检查订单是否已落库
                try{
                    TransTransreq transTransreq=transReqService.queryTransTransReqByOrderno(order_no);
                    if(transTransreq!=null){
                        //订单已落库
                        flag=false;
                        logger.info("订单防重查询到订单已落库，order_no="+order_no);
                    }
                }catch (Exception e){
                    if(e instanceof BusinessException){
                        logger.info("订单防重查询到订单不存在，order_no="+order_no);
                    }else{
                        logger.error("订单查询异常，order_no="+order_no+",失败原因：",e);
                        return false;
                    }
                }
            }
        }else{
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR+":订单号不可为空"));
        }
        return flag;
    }

    @Override
    public boolean checkOrderStatus(TransTransreq transTransreq) throws BusinessException {
        TransTransreq prodTransTransreq=transReqService.queryTransTransReqByOrderno(transTransreq.getOrder_no());
        if(FlowStatus.SUCCESS.getCode().equals(prodTransTransreq.getStatus())){
            return false;
        }
        if(FlowStatus.FAIL.getCode().equals(prodTransTransreq.getStatus()) && FlowStatus.SUCCESS.getCode().equals(transTransreq.getStatus())){
            //回滚转账
            List<EaccAccountamtlist> redoEaccAccountamtlist=accountQueryService.queryEaccAccountamtlistByTransSerial(transTransreq.getTrans_serial());
            List<EaccAccountamtlist> backEaccAccountamtlist=getBackEaccAccountamtlist(redoEaccAccountamtlist);
            accountTransferService.batchTransfer(backEaccAccountamtlist);
            return false;
        }
        return true;
    }

    public List<EaccAccountamtlist> getBackEaccAccountamtlist(List<EaccAccountamtlist> eaccAccountamtlists)throws BusinessException{
        List<EaccAccountamtlist> newEaccAccountamtlists=new ArrayList<>();
        for(EaccAccountamtlist eaccAccountamtlist:eaccAccountamtlists){
            if(AmtType.INCOME.getCode().equals(eaccAccountamtlist.getAmt_type())){
                //把出金参数当成反向转账参数进行转账
                eaccAccountamtlist.setAmt_type(null);
                eaccAccountamtlist.setCreate_time(null);
                eaccAccountamtlist.setUpdate_time(null);
                eaccAccountamtlist.setId(null);
                eaccAccountamtlist.setAccount_date(null);
                eaccAccountamtlist.setSwitch_state(null);
                eaccAccountamtlist.setSwitch_amt(null);
                newEaccAccountamtlists.add(eaccAccountamtlist);
            }
        }
        return newEaccAccountamtlists;
    }
}
