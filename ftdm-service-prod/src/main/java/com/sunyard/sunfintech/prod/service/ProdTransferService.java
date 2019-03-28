package com.sunyard.sunfintech.prod.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.sunyard.sunfintech.account.provider.IAccountTransferService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.AmtType;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.custdao.mapper.CustProdProdInfoMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.ProdShareListMapper;
import com.sunyard.sunfintech.prod.model.bo.ProdInvestData;
import com.sunyard.sunfintech.prod.provider.IProdTransferService;
import com.sunyard.sunfintech.prod.provider.IProductInvestmentService;
import com.sunyard.sunfintech.pub.provider.ISendMsgService;
import com.sunyard.sunfintech.pub.provider.ITransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by terry on 2017/9/12.
 */
@Service(interfaceClass = IProdTransferService.class)
@org.springframework.stereotype.Service
public class ProdTransferService extends BaseServiceSimple implements IProdTransferService {
    @Autowired
    private IProductInvestmentService productInvestmentService;

    @Autowired
    private IAccountTransferService accountTransferService;

    @Autowired
    private ProdShareListMapper prodShareListMapper;

    @Autowired
    private ITransferService transferService;
    @Autowired
    private CustProdProdInfoMapper custProdProdInfoMapper;
    @Autowired
    private ISendMsgService sendMsgService;
    @Override
    @Transactional
    public boolean prodTransTransfer(BaseRequest baseRequest,ProdShareList prodShareListOut, ProdShareList prodShareListIn, ProdTransferRecord transferRecord, List<EaccAccountamtlist> eaccAccountamtlist,boolean transType) throws BusinessException {
        //添加份额明细
        logger.info("【标的转让】========添加明细");
        productInvestmentService.addShareList(prodShareListOut);
        productInvestmentService.addShareList(prodShareListIn);

        //添加transferRecord数据
        logger.info("【标的转让】========添加债券转让信息");
        productInvestmentService.addTransferRecord(transferRecord);

        //批量转账
        logger.info("【标的转让】========批量转账开始");
//        accountTransferService.batchTransfer(eaccAccountamtlist);
//        newAccountTransferService.batchTransfer(eaccAccountamtlist);

        return true;
    }

//    @Override
////    @Transactional
//    public boolean prodInvestTransfer(ProdShareList shareList, List<EaccAccountamtlist> batchEaccAccountamtList, BaseRequest baseRequest) throws BusinessException {
//        //批量转账
//        logger.info("【批量投标】========开始转账");
//        String lockKey="Investment"+shareList.getPlatcust();
//        try {
//            Boolean lock= CacheUtil.getLock(lockKey);
//            logger.info("【批量投标】========lockKey:"+lockKey+",lock:"+lock);
//            while (!lock){
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    logger.error("线程休眠异常"+e);
//                    throw new BusinessException(BusinessMsg.RUNTIME_EXCEPTION,BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION)+"：线程休眠异常");
//                }
//                lock= CacheUtil.getLock(lockKey);
//            }
//            logger.info("转账开始时间："+new Date()+"，订单号："+baseRequest.getOrder_no());
//            accountTransferService.batchTransfer(batchEaccAccountamtList);
//            logger.info("转账结束时间："+new Date()+"，订单号："+baseRequest.getOrder_no());
//        }catch (BusinessException e){
//            logger.error("【批量投标】========转账失败："+e.getErrorMsg());
//            BaseResponse baseResponse=e.getBaseResponse();
//            CacheUtil.unlock(lockKey);
//            throw new BusinessException(baseResponse);
//        }
//        logger.info("【批量投标】========单个投标插入购买明细");
//        try{
//            prodShareListMapper.insert(shareList);
//        }catch (Exception e){
//            //插入失败，回滚转账
//            for(EaccAccountamtlist eaccAccountamtlist:batchEaccAccountamtList){
//                String oppo_platcust=eaccAccountamtlist.getOppo_platcust();
//                String oppo_subject=eaccAccountamtlist.getOppo_subject();
//                String oppo_sub_subject=eaccAccountamtlist.getOppo_sub_subject();
//                eaccAccountamtlist.setOppo_platcust(eaccAccountamtlist.getPlatcust());
//                eaccAccountamtlist.setOppo_subject(eaccAccountamtlist.getSubject());
//                eaccAccountamtlist.setOppo_sub_subject(eaccAccountamtlist.getSub_subject());
//                eaccAccountamtlist.setPlatcust(oppo_platcust);
//                eaccAccountamtlist.setSubject(oppo_subject);
//                eaccAccountamtlist.setSub_subject(oppo_sub_subject);
//            }
//            try {
//                logger.info("转账回滚，订单号："+baseRequest.getOrder_no());
//                accountTransferService.batchTransfer(batchEaccAccountamtList);
//            }catch (BusinessException ex){
//                logger.error("【批量投标】========转账回滚失败："+ex.getErrorMsg());
//                BaseResponse baseResponse=ex.getBaseResponse();
//                throw new BusinessException(baseResponse);
//            }
//        }finally {
//            CacheUtil.unlock(lockKey);
//        }
//
//        return true;
//    }
    @Override
    public void saveInvestAsyncData(BaseRequest baseRequest,  ProdShareList shareList  , List<EaccAccountamtlist> batchEaccAccountamtList, ProdProductinfo productInfo, BigDecimal realVol) throws BusinessException{
//        try {
//            logger.info("【批量投标-异步】========单个投标更新剩余额度");
//            Map<String, Object> params = new HashMap<>();
//            params.put("prod_id", productInfo.getProd_id());
//            params.put("plat_no", productInfo.getPlat_no());
//            params.put("vol", realVol);
//            params.put("update_by", SeqUtil.RANDOM_KEY);
//            params.put("update_time",new Date());
//            logger.info("更新标的剩余份额：", baseRequest.getOrder_no());
//            logger.info("更新开始时间：" + new Date() + "，订单号：" + baseRequest.getOrder_no());
//            custProdProdInfoMapper.subtractProdLimit(params);
//            logger.info("更新结束时间：" + new Date() + "，订单号：" + baseRequest.getOrder_no());
//            shareList.setCreate_time(new Date());
//            shareList.setCreate_by(SeqUtil.RANDOM_KEY);
//            prodShareListMapper.insert(shareList);
//        } catch (Exception e) {
//            BaseResponse baseResponse=new BaseResponse();
//            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
//            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
//            throw new BusinessException(baseResponse);
//        }
        String investLockKey=Constants.PROD_INVEST_LOCK_KEY+productInfo.getProd_id();
        Boolean getLock=true;
        Integer retryNum=0;
        do{
            retryNum++;
            if(!getLock){
                if(retryNum>100){
                    break;
                }
                sleep(50);
            }
            getLock=CacheUtil.getCache().setnx(investLockKey,baseRequest.getOrder_no());
        }while (!getLock);
        CacheUtil.getCache().expire(investLockKey,3600);
        logger.info("【批量投标-异步】获取到锁，执行DB操作，锁有效期为1小时|prod_id:{}|order_no:{}",productInfo.getProd_id(),baseRequest.getOrder_no());

        try {
            //更新份额，添加投资明细
            boolean flag = productInvestmentService.saveInvestmentInfo(baseRequest,shareList,productInfo,realVol);
            if (!flag){
                logger.error(String.format("【批量投标-异步】====更新剩余额度异常: orderno:%s| transserial:%s",baseRequest.getOrder_no(),baseRequest.getLink_trans_serial()));
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION,BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION)+ "投资失败");
            }
        }finally {
            logger.info("【批量投标-异步】DB操作执行完成，删除锁|prod_id:{}|order_no:{}",productInfo.getProd_id(),baseRequest.getOrder_no());
            CacheUtil.getCache().del(investLockKey);
        }
        //转账
        try {
            logger.info(String.format("【批量投标-异步】开始进入TransferQueue队列|orderno:%s，transserial:%s", baseRequest.getOrder_no(), baseRequest.getLink_trans_serial()));
            transferService.transfer(baseRequest, batchEaccAccountamtList);
            logger.info(String.format("【批量投标-异步】结束进入TransferQueue队列|orderno:%s，transserial:%s，是否成功：是", baseRequest.getOrder_no(), baseRequest.getLink_trans_serial()));
        } catch (Exception e) {
            //转账失败,标的份额反向更新
            logger.error("【批量投标-异步】========转账异常: orderno:|" + baseRequest.getOrder_no() + "| transserial:|" + baseRequest.getLink_trans_serial()+"|error:",e);
            try {
                Map<String, Object> params = new HashMap<>();
                params.put("prod_id", productInfo.getProd_id());
                params.put("plat_no", productInfo.getPlat_no());
                params.put("vol", realVol);
                params.put("update_by", SeqUtil.RANDOM_KEY);
                params.put("update_time",new Date());
                custProdProdInfoMapper.addProdLimit(params);
            } catch (Exception e1) {
                logger.error("【批量投标-异步】========标的剩余份额更新反向操作失败: orderno:|" + baseRequest.getOrder_no() + "| transserial:|" + baseRequest.getLink_trans_serial()+"|error:",e1);
                String content = "【批量投标-异步】========标的剩余份额更新反向操作失败orderno:"+baseRequest.getOrder_no()+" prod_id:" + productInfo.getProd_id() + ",vol:" + realVol;
                //给管理员发送通知
                logger.error(content,e1);
                sendMsgService.sendErrorToAdmin(content,baseRequest.getMer_no());
            }
            throw new BusinessException(BusinessMsg.TRANS_FAIL,BusinessMsg.getMsg(BusinessMsg.TRANS_FAIL) + "投资失败");
        }
    }


}
