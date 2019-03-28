package com.sunyard.sunfintech.billcheck.modulepublic;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.FlowStatus;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.dao.entity.TransChangeCard;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.dao.entity.TransTransreqExample;
import com.sunyard.sunfintech.dao.mapper.TransChangeCardMapper;
import com.sunyard.sunfintech.dao.mapper.TransTransreqMapper;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by terry on 2017/5/11.
 */
@CacheConfig(cacheNames="transReqService")
@org.springframework.stereotype.Service("transReqService")
public class TransReqService extends BaseServiceSimple implements ITransReqService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Value("${service.name}")
    private String serviceName;

    @Resource(name = "amqpTemplate")
    private AmqpTemplate amqpTemplate;

    @Autowired
    private TransTransreqMapper transTransReqMapper;

    @Autowired
    private TransChangeCardMapper transChangeCardMapper;


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public BaseResponse insert(final TransTransreq transTransReq) throws BusinessException {
        BaseResponse baseResponse=new BaseResponse();
        if(transTransReq!=null && !StringUtils.isEmpty(transTransReq.getTrans_serial())){
            try{
                transTransReq.setService_name(serviceName);
                logger.info("【业务流水队列】流水内容："+ JSON.toJSONString(transTransReq));
                //流水信息先入redis
                String key=Constants.TRANSREQ_ORDER_KEY+serviceName+":"+transTransReq.getOrder_no();
                Boolean addSuccess= CacheUtil.getCache().setnx(key,JSON.toJSONString(transTransReq));
                TransTransreq oldTransreq=null;
                if(!addSuccess){
                    oldTransreq=JSON.parseObject((String)CacheUtil.getCache().get(key),TransTransreq.class);
                }else{
                    oldTransreq=queryTransReqByOrderNo(transTransReq.getOrder_no());
                    if(null!=oldTransreq){
                        CacheUtil.getCache().set(key,JSON.toJSONString(oldTransreq));
                    }
                }
                //判断当前插入订单是否为相同订单
                if(oldTransreq!=null){
                    if(oldTransreq.getTrans_serial().equals(transTransReq.getTrans_serial())){
                        logger.info("【业务流水队列】相同订单，允许将订单状态由非0改为0，order:"+transTransReq.getOrder_no());
                    }else{
                        logger.info("【业务流水队列】原订单状态为："+oldTransreq.getStatus()+"，将订单返回给业务层，order:"+transTransReq.getOrder_no());
                        //查询该订单数据库中的状态，判断订单是否需要补单
                        if(!addSuccess){
                            //如果读取的是数据库中缓存的数据，需要同步redis和数据库中的数据
                            boolean updateFlag=false;
                            TransTransreq oldTransreqDB=queryTransReqByOrderNo(transTransReq.getOrder_no());
                            if(oldTransreqDB==null){
                                logger.info("【业务流水队列】order："+transTransReq.getOrder_no()+"，redis中订单在数据库中不存在，需重新落单");
                                //数据库中订单不存在
                                updateFlag=true;
                            }else if(!oldTransreq.getStatus().equals(oldTransreqDB.getStatus())){
                                logger.info("【业务流水队列】order："+transTransReq.getOrder_no()+"，redis中订单状态与数据库中订单状态不一致，需更改订单状态");
                                updateFlag=true;
                            }
                            try{
                                if(updateFlag){
                                    logger.info("【业务流水队列】order："+transTransReq.getOrder_no()+"，进行补单");
                                    addFlow(oldTransreq);
                                }
                            }catch (Exception e){
                                logger.error("【业务流水队列】业务流水记录异常：",e);
                            }
                        }
                        //返回信息
                        baseResponse.setOrder_no(oldTransreq.getOrder_no());
                        baseResponse.setOrder_status(oldTransreq.getStatus());
                        baseResponse.setRecode(oldTransreq.getReturn_code());
                        baseResponse.setRemsg(oldTransreq.getReturn_msg());
                        baseResponse.setTrans_serial(oldTransreq.getTrans_serial());

                        return baseResponse;
                    }
                }
                CacheUtil.getCache().expire(key,24*3600);
//                logger.info("【业务流水队列】发送消息，order:"+transTransReq.getOrder_no());
//                MQUtils.send(amqpTemplate, "ftdm.user.direct.exchange", "TransTransReqQueue:"+serviceName, transTransReq);
//                logger.info("【业务流水队列】消息发送成功！order:"+transTransReq.getOrder_no());
                addFlow(transTransReq);
            }catch (Exception e){
                logger.error("【业务流水队列】业务流水记录异常：",e);
            }
            return null;
        }
        return null;
    }

    @Override
    public boolean addFlow(TransTransreq transTransReq) {
        logger.info("【业务流水处理】"+transTransReq.toString());
//        String lockKey=Constants.TRANSREQ_ORDER_LOCK_KEY+serviceName+":"+transTransReq.getOrder_no();
//        boolean deleteKeyFlag=true;
        try{
//            Integer maxSleepNum=5;
//            while (!CacheUtil.getCache().setnx(lockKey,transTransReq.getStatus())){
//                if(--maxSleepNum<=0){
//                    logger.info(String.format("【业务流水处理】重试5次还没成功，丢入MQ|order_no:%s",transTransReq.getOrder_no()));
//                    MQUtils.send(amqpTemplate, "ftdm.user.direct.exchange", "TransTransReqQueue:"+serviceName, transTransReq);
//                    deleteKeyFlag=false;
//                    return true;
//                }
//                logger.info(String.format("【业务流水处理】线程已被占用，随机休眠10-100毫秒|order_no:%s",transTransReq.getOrder_no()));
//                sleep(100);
//            }
//            CacheUtil.getCache().expire(lockKey,600);
            if(!updateFlow(transTransReq)){
                transTransReq.setCreate_by(transTransReq.getPlat_no());
                transTransReq.setCreate_time(DateUtils.today());
                transTransReqMapper.insert(transTransReq);
            }
            String key=Constants.TRANSREQ_ORDER_KEY+serviceName+":"+transTransReq.getOrder_no();
            CacheUtil.getCache().set(key,JSON.toJSONString(transTransReq));
            if(FlowStatus.SUCCESS.getCode().equals(transTransReq.getStatus()) || FlowStatus.FAIL.getCode().equals(transTransReq.getStatus())){
                //如果是终态，缩短超时时间为6小时
                CacheUtil.getCache().expire(key,3600*6);
            }
        }catch (Exception e){
            logger.info("【业务流水处理】业务流水记录失败："+transTransReq.toString());
            logger.error("【业务流水处理】记录业务流水异常：",e);
        }finally {
//            if(deleteKeyFlag){
//                CacheUtil.getCache().del(lockKey);
//            }
        }
        return true;
    }

    private boolean updateFlow(TransTransreq transTransReq) throws BusinessException {
        List<TransTransreq> oldTransTransReqList=getTransTransreqWithTransSerial(transTransReq.getTrans_serial());
        if(oldTransTransReqList.size()>1){
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, "一个订单号存在多条业务流水");
        }else if(oldTransTransReqList.size() == 1){
            TransTransreq oldTransTransReq = oldTransTransReqList.get(0);
            logger.info("【业务流水处理】查询出的订单状态："+oldTransTransReq.getStatus());
            if("back".equals(transTransReq.getExt1())){
                TransTransreq updateTransTransReq=new TransTransreq();
                updateTransTransReq.setId(oldTransTransReq.getId());
                updateTransTransReq.setReturn_code(transTransReq.getReturn_code());
                updateTransTransReq.setReturn_msg(transTransReq.getReturn_msg());
                updateTransTransReq.setUpdate_by(transTransReq.getPlat_no());
                updateTransTransReq.setUpdate_time(DateUtils.today());
                updateTransTransReq.setStatus(transTransReq.getStatus());
                transTransReqMapper.updateByPrimaryKeySelective(updateTransTransReq);
            }else if(!FlowStatus.SUCCESS.getCode().equals(oldTransTransReq.getStatus())
                    && !FlowStatus.FAIL.getCode().equals(oldTransTransReq.getStatus())){
                TransTransreq updateTransTransReq=new TransTransreq();
                updateTransTransReq.setReturn_code(transTransReq.getReturn_code());
                updateTransTransReq.setReturn_msg(transTransReq.getReturn_msg());
                updateTransTransReq.setUpdate_by(transTransReq.getPlat_no());
                updateTransTransReq.setUpdate_time(DateUtils.today());
                updateTransTransReq.setStatus(transTransReq.getStatus());
                TransTransreqExample example=new TransTransreqExample();
                TransTransreqExample.Criteria criteria=example.createCriteria();
                criteria.andIdEqualTo(oldTransTransReq.getId());
                List<String> statusList=new ArrayList<>();
                statusList.add(FlowStatus.SUCCESS.getCode());
                statusList.add(FlowStatus.FAIL.getCode());
                criteria.andStatusNotIn(statusList);
                transTransReqMapper.updateByExampleSelective(updateTransTransReq,example);
            }
             return true;
        }
        return false;
    }

    /**
     * 获取TransTransReq的基本信息
     * @param baseRequest
     * @param batchFlag 是否是批量流水 true：是 false：不是
     * @return
     */
    public TransTransreq getTransTransReq(BaseRequest baseRequest, String trans_code, String trans_name, boolean batchFlag){
        TransTransreq transTransReq=new TransTransreq();
        if(batchFlag){
            transTransReq.setBatch_order_no(baseRequest.getOrder_no());
        }
        transTransReq.setOrder_no(baseRequest.getOrder_no());
        transTransReq.setTrans_code(trans_code);
        transTransReq.setTrans_name(trans_name);
        transTransReq.setPlat_no(baseRequest.getMer_no());
        transTransReq.setTrans_serial(SeqUtil.getSeqNum());
        if(StringUtils.isBlank(baseRequest.getPartner_trans_date())){
            transTransReq.setTrans_date(DateUtils.getyyyyMMddDate());
        }else{
            transTransReq.setTrans_date(baseRequest.getPartner_trans_date());
        }
        if(StringUtils.isBlank(baseRequest.getPartner_trans_time())){
            transTransReq.setTrans_time(DateUtils.getyyyyMMddDate());
        }else{
            transTransReq.setTrans_time(baseRequest.getPartner_trans_time());
        }
        transTransReq.setStatus(FlowStatus.INPROCESS.getCode());
        transTransReq.setEnabled(Constants.ENABLED);
        return transTransReq;
    }


    public List<TransTransreq> queryTransTransReq(String batchOrderNo)throws BusinessException{

        TransTransreqExample transTransreqExample = new TransTransreqExample();
        TransTransreqExample.Criteria criteria = transTransreqExample.createCriteria();
        if(StringUtils.isNotBlank(batchOrderNo))
            criteria.andBatch_order_noEqualTo(batchOrderNo);
        else{
            throw new BusinessException(BusinessMsg.PARAMETER_LACK, "batchOrderNo不能为空");
        }
        criteria.andEnabledEqualTo(Constants.ENABLED);
        criteria.andService_nameEqualTo(serviceName);
        List<TransTransreq> transTransreqs = transTransReqMapper.selectByExample(transTransreqExample);
        if(transTransreqs==null || transTransreqs.size()==0){
            throw new BusinessException(BusinessMsg.PARAMETER_LACK, "根据总订单流水号未获得交易流水记录");
        }
        return transTransreqs;
    }


    //根据订单号查询单个流水
    @Override
    public TransTransreq queryTransTransReqByOrderno(String order_no) throws BusinessException {

        TransTransreqExample transTransreqExample = new TransTransreqExample();
        TransTransreqExample.Criteria criteria = transTransreqExample.createCriteria();

        if (StringUtils.isNotBlank(order_no)) {
            criteria.andOrder_noEqualTo(order_no);
        } else {
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "order_no不能为空");
        }
        criteria.andEnabledEqualTo(Constants.ENABLED);
        criteria.andService_nameEqualTo(serviceName);

        List<TransTransreq> transTransreqs = transTransReqMapper.selectByExample(transTransreqExample);
        if (transTransreqs == null || transTransreqs.size() == 0) {
            //查redis
            String key=Constants.TRANSREQ_ORDER_KEY+serviceName+":"+order_no;
            Object transObj=CacheUtil.getCache().get(key);
            if(transObj!=null){
                return JSON.parseObject(String.valueOf(transObj),TransTransreq.class);
            }
            throw new BusinessException(BusinessMsg.PARAMETER_LACK, "根据订单号未获得流水记录");
        } else if (transTransreqs.size() > 1) {
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "订单号重复查询到多个流水记录");
        }
        return transTransreqs.get(0);
    }

    @Override
    public TransTransreq queryTransReqByOrderNo(String order_no) {
        TransTransreqExample transTransreqExample = new TransTransreqExample();
        TransTransreqExample.Criteria criteria = transTransreqExample.createCriteria();

        if (StringUtils.isNotBlank(order_no)) {
            criteria.andOrder_noEqualTo(order_no);
        } else {
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "order_no不能为空");
        }
        criteria.andEnabledEqualTo(Constants.ENABLED);
        criteria.andService_nameEqualTo(serviceName);

        List<TransTransreq> transTransreqs = transTransReqMapper.selectByExample(transTransreqExample);
        if (transTransreqs.size() > 0) {
            return transTransreqs.get(0);
        }
        return null;
    }

    @Override
    public TransTransreq queryTransTransReqByOrderNoAndBatchOrderNo(String trans_serial, String order_no) throws BusinessException {
        TransTransreqExample transTransreqExample = new TransTransreqExample();
        TransTransreqExample.Criteria criteria = transTransreqExample.createCriteria();

        if(StringUtils.isNotBlank(order_no))
            criteria.andOrder_noEqualTo(order_no);

        if(StringUtils.isNotBlank(trans_serial))
            criteria.andTrans_serialEqualTo(trans_serial);

        criteria.andEnabledEqualTo(Constants.ENABLED);
        criteria.andService_nameEqualTo(serviceName);

        List<TransTransreq> transTransreqs = transTransReqMapper.selectByExample(transTransreqExample);
        if(transTransreqs == null || transTransreqs.size()==0){
            throw new BusinessException(BusinessMsg.PARAMETER_LACK, "根据订单号未获得流水记录");
        }else if(transTransreqs.size() > 1){
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "订单号重复查询到多个流水记录");
        }
        return transTransreqs.get(0);
    }

    @Override
    public TransTransreq queryTransTransReqByOrdernoAndPlatcust(String order_no, String platcust, String trans_code) throws BusinessException {
        TransTransreqExample transTransreqExample = new TransTransreqExample();
        TransTransreqExample.Criteria criteria = transTransreqExample.createCriteria();

        if(StringUtils.isNotBlank(order_no))
            criteria.andOrder_noEqualTo(order_no);

        if(StringUtils.isNotBlank(platcust))
            criteria.andPlatcustEqualTo(platcust);
        if(StringUtils.isNotBlank(trans_code))
            criteria.andTrans_codeEqualTo(trans_code);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        criteria.andService_nameEqualTo(serviceName);

        List<TransTransreq> transTransreqs = transTransReqMapper.selectByExample(transTransreqExample);
        if(transTransreqs == null || transTransreqs.size()==0){
            throw new BusinessException(BusinessMsg.PARAMETER_LACK, "根据订单号未获得流水记录");
        }else if(transTransreqs.size() > 1){
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "订单号重复查询到多个流水记录");
        }
        return transTransreqs.get(0);
    }

    @Override
    public boolean insert(TransChangeCard transChangeCard) throws BusinessException {
        if(transChangeCard!=null){
            addFlow(transChangeCard);
            return true;
        }
        return false;
    }

    @Override
    public TransTransreq queryApplyTransreqByConfirmOrderNo(String order_no) throws BusinessException {
        TransTransreqExample transTransreqExample = new TransTransreqExample();
        TransTransreqExample.Criteria criteria = transTransreqExample.createCriteria();

        if(StringUtils.isNotBlank(order_no))
            criteria.andOrigin_order_noEqualTo(order_no);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        criteria.andService_nameEqualTo(serviceName);

        List<TransTransreq> transTransreqs = transTransReqMapper.selectByExample(transTransreqExample);
        if(transTransreqs == null || transTransreqs.size()==0){
            return null;
        }
        //如果发现有多条相同的确认流水，取第一条
        return transTransreqs.get(0);
    }

    private boolean addFlow(TransChangeCard transChangeCard) {
        logger.info("【短验换卡流水】====="+transChangeCard.toString());
        String lockKey = getLockKey(transChangeCard.getTrans_serial());
        try{
            boolean lock = CacheUtil.getLock(lockKey);
            while (!lock) {
                sleep(50);//没取到锁50毫秒后重试
                lock = CacheUtil.getLock(lockKey);
            }
            logger.debug("start insert");
            transChangeCard.setCreate_by(transChangeCard.getPlat_no());
            transChangeCard.setCreate_time(DateUtils.today());
            transChangeCardMapper.insert(transChangeCard);
            logger.debug("insert success");
            CacheUtil.unlock(lockKey);
        }catch (Exception e){
            logger.info("【业务流水】=====记录业务流水异常：\n"+e);
            logger.info("【业务流水】=====业务流水记录失败："+transChangeCard.toString());
            CacheUtil.unlock(lockKey);
        }
        return true;
    }

    private List<TransTransreq> getTransTransreqWithTransSerial(String trans_serial)throws BusinessException{
        TransTransreqExample transTransreqExample = new TransTransreqExample();
        TransTransreqExample.Criteria criteria = transTransreqExample.createCriteria();
        criteria.andTrans_serialEqualTo(trans_serial);
        criteria.andService_nameEqualTo(serviceName);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        return transTransReqMapper.selectByExample(transTransreqExample);
    }

    @Override
    public boolean updateOrderInfo(TransTransreq transTransReq, String orderNo) {
        return false;
    }
    @Override
    public   List<TransTransreq>   queryTransByOriginalOrderno(String originalOrder_no,  String trans_code,String excludeStatus,String excludeTransSerial) throws BusinessException {
        TransTransreqExample transTransreqExample = new TransTransreqExample();
        TransTransreqExample.Criteria criteria = transTransreqExample.createCriteria();

        if (StringUtils.isNotBlank(originalOrder_no))
            criteria.andOrigin_order_noEqualTo(originalOrder_no);
        if (criteria.getAllCriteria().size() == 0) {
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "订单号不能为空");
        }
        if (StringUtils.isNotBlank(trans_code))
            criteria.andTrans_codeEqualTo(trans_code);
        if (StringUtils.isNotBlank(excludeStatus))
            criteria.andStatusNotEqualTo(excludeStatus);
        if (StringUtils.isNotBlank(excludeTransSerial))
            criteria.andTrans_serialNotEqualTo(excludeTransSerial);


        criteria.andEnabledEqualTo(Constants.ENABLED);
        criteria.andService_nameEqualTo(serviceName);

        List<TransTransreq> transTransreqs = transTransReqMapper.selectByExample(transTransreqExample);
        return transTransreqs;
    }

    @Autowired
    private ISysParameterService sysParameterService;

}
