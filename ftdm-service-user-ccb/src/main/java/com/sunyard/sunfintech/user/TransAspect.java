package com.sunyard.sunfintech.user;


import com.sunyard.sunfintech.core.annotation.SerialNo;
import com.sunyard.sunfintech.core.annotation.SerialNoDetail;
import com.sunyard.sunfintech.core.base.BaseModel;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseSerialNoRequest;
import com.sunyard.sunfintech.core.dic.FlowStatus;
import com.sunyard.sunfintech.core.dic.OrderStatus;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
//import com.common.utils.ReflectUtils;

/**
 *
 * Created by wyc on 2017/4/7.
 */
@Aspect
//@Component
public class TransAspect {

    private final Logger logger = LogManager.getLogger();
    public TransAspect() {
        super();
    }

    @Pointcut("@annotation(com.sunyard.sunfintech.core.annotation.SerialNo)")
    public void controllerAspect() {

    }

    @Autowired
    private ITransReqService transReqService;


    @Around(value = "controllerAspect() && args(baseRequest,..)&& @annotation(serialNoAnno)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, BaseRequest baseRequest, final SerialNo serialNoAnno) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().getName();
        TransTransreq parentTrans = transReqService.getTransTransReq(baseRequest.clone(), serialNoAnno.transCode(), serialNoAnno.description(), serialNoAnno.isBatch());
        if (StringUtils.isNotBlank(baseRequest.getLink_trans_serial())) {
            parentTrans.setTrans_serial(baseRequest.getLink_trans_serial());
        } else {
            baseRequest.setLink_trans_serial(parentTrans.getTrans_serial());
        }
        parentTrans.setPlatcust(baseRequest.getBase_serial_plutcust());
        parentTrans.setOrigin_order_no(baseRequest.getBase_serial_ori_order_no());
        parentTrans.setNotify_url(baseRequest.getBase_serial_notify_url());
        Object result;
        //执行目标方法

        try {
            logger.info("Aspect["+serialNoAnno.description()+"]methodName="+methodName + "，Trans_serial=" + parentTrans.getTrans_serial() + "开始");
            //前置通知
            System.out.println("The method " + methodName + " begin with" + Arrays.asList(proceedingJoinPoint.getArgs()));
           noRepeat(parentTrans);
            List<TransTransreq> detailTransList = null;
            if (serialNoAnno.isBatch()) {
                detailTransList = genDetailTransBeforeProcess(baseRequest, serialNoAnno, proceedingJoinPoint);
            }
            result = proceedingJoinPoint.proceed();
            boolean isParentSucc = false;
            if (serialNoAnno.isBatch()) {
                isParentSucc = updateDetailTransAfterPorcess(detailTransList, result, serialNoAnno.isAsync());
            } else {
                BaseResponse baseResponse = parseResult(parentTrans.getOrder_no(), result);
                isParentSucc = BusinessMsg.SUCCESS.equals(baseResponse.getRecode());
            }
            //后置通知
            System.out.println("The method " + methodName + " end with" + result);
            if (isParentSucc) {
                //更新批量流水
                parentTrans.setReturn_code(BusinessMsg.SUCCESS);
                parentTrans.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                parentTrans.setStatus(FlowStatus.SUCCESS.getCode());
                if (serialNoAnno.isAsync()) {
                    parentTrans.setRemark("异步处理成功");
                }
            }
            transReqService.insert(parentTrans);
            logger.info("Aspect["+serialNoAnno.description()+"]methodName="+methodName + "，Trans_serial=" + parentTrans.getTrans_serial() + "执行无异常");

        } catch (Throwable e) {
            //如果进入到这里，且如果是批量记录，则保持明细的流水为processing状态，由本模块发起查询更新状态
            //更新主流水状态为失败Fail
            System.out.println("The method occurs exception : " + e);
            logger.info("Aspect["+serialNoAnno.description()+"]methodName="+methodName + "，Trans_serial=" + parentTrans.getTrans_serial() + "执行进入异常");
            BaseResponse baseResponse = new BaseResponse();


            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            parentTrans.setReturn_code(baseResponse.getRecode());
            parentTrans.setReturn_msg(baseResponse.getRemsg());
            parentTrans.setStatus(FlowStatus.FAIL.getCode());
            transReqService.insert(parentTrans);
            throw e;
        }finally {
            logger.info("Aspect["+serialNoAnno.description()+"]methodName="+methodName + "，Trans_serial=" + parentTrans.getTrans_serial() + "结束");
        }
        return result;
    }

    private void noRepeat(TransTransreq parentTrans ) {
        BaseResponse noRepeatResp = transReqService.insert(parentTrans);
        if (noRepeatResp != null) {
            logger.info(" 【aspect】orderno=" + parentTrans.getOrder_no() + "重复提交拦截");
            String  orderStatus = OrderStatus.getMsgByCode(noRepeatResp.getOrder_status());
            throw new BusinessException(BusinessMsg.ORDERNUMBER_REPEATED, BusinessMsg.getMsg(BusinessMsg.ORDERNUMBER_REPEATED) + "，请勿重复提交。当前订单处理状态：" + orderStatus);
        }
        if (StringUtils.isNotBlank(parentTrans.getOrigin_order_no())) {
           List< TransTransreq   > noRepeat = transReqService.queryTransByOriginalOrderno(parentTrans.getOrigin_order_no(), parentTrans.getTrans_code(), FlowStatus.FAIL.getCode(),parentTrans.getTrans_serial() );
            if (noRepeat != null&& noRepeat.size()>0) {
                logger.info("【aspect】重复提交的请求。原订单号originorderno:" + parentTrans.getOrigin_order_no());
                throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NO_ERROR, "重复提交的请求。原订单号originorderno:" + parentTrans.getOrigin_order_no() + ",处理状态：" + OrderStatus.getMsgByCode(noRepeat.get(0).getStatus()));
            }
        }
    }

    private boolean updateDetailTransAfterPorcess(List<TransTransreq> detailTransList, Object result, boolean isAsyn) {

        boolean isParentSucc = true;
        try {
            if (detailTransList != null && detailTransList.size() > 0) {
                for (int i = 0; i < detailTransList.size(); i++) {
                    TransTransreq detailTrans = detailTransList.get(i);
                    BaseResponse baseResponse = parseResult(detailTrans.getOrder_no(), result);
                    if (baseResponse != null) {
                        boolean isSucc = BusinessMsg.SUCCESS.equals(baseResponse.getRecode());
                        String orderStatus = isSucc ? (isAsyn ? FlowStatus.INPROCESS.getCode() : FlowStatus.SUCCESS.getCode()) : FlowStatus.FAIL.getCode();
                        detailTrans.setReturn_code(baseResponse.getRecode());
                        detailTrans.setReturn_msg(baseResponse.getRemsg());
                        detailTrans.setStatus(orderStatus);
                        transReqService.insert(detailTrans);
                        if (!isSucc) {
                            isParentSucc = false;
                        }
                    }
                }

            }
            return isParentSucc;
        } catch (Exception e) {
            logger.error("updateDetailTransAfterPorcess()异常：" + e.getMessage(), e);
            throw new BusinessException(BusinessMsg.TRANS_SERIAL_INNER_ERR, "updateDetailTransAfterPorcess()异常" + e.getMessage());
        }
    }

    private BaseResponse parseResult(String orderNo, Object result) {
        try {
            if (result instanceof BaseModel) {
                BaseModel baseModel = ((BaseModel) result);
                Map<String, BaseResponse> map = baseModel.getOrderAfterProcessMap();
                if (map != null && map.containsKey(orderNo)) {
                    BaseResponse obj = map.get(orderNo);
                    return obj;
                }
            }
        } catch (Exception e) {
            logger.error("parseResult异常", e);
        }
        return null;
    }

    public List<TransTransreq> genDetailTransBeforeProcess(BaseRequest parentBaseRequest, SerialNo serialNoAnno, ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object target = joinPoint.getTarget();
            //拦截的方法名
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method targetMethod = methodSignature.getMethod();
            Object[] args = joinPoint.getArgs();

            //获取该方法在参数上的注解，每个参数可以有多个注解，得到的是一个二维数组
            Annotation[][] parameterAnnotaions = targetMethod.getParameterAnnotations();
            for (int i = 0; i < parameterAnnotaions.length; i++) {
                boolean isHasParamAnno = findAnnotionForParam(parameterAnnotaions[i]);
                if (isHasParamAnno) {
                    return generateSerialAndSave(parentBaseRequest, serialNoAnno, args[i]);

                } else {
                    BeanInfo beanInfo = Introspector.getBeanInfo(args[i].getClass());
                    PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
                    for (PropertyDescriptor pd : pds) {
                        Method readMethod = pd.getReadMethod();
                        if (!pd.getName().equals("class") && !pd.getName().equals("empty")) {
                            SerialNoDetail fieldProp = (SerialNoDetail) findDetailAnnotionForField(args[i], pd.getName());
                            if (fieldProp != null) {
                                Object field = readMethod.invoke(args[i]);
                                if (field != null) {
                                    return generateSerialAndSave(parentBaseRequest, serialNoAnno, field);

                                }
                            }
                        }


                    }
                }
            }
        } catch (Exception e) {
            logger.error("genDetailTransBeforeProcess()异常：" + e.getMessage(), e);
            throw new BusinessException(BusinessMsg.TRANS_SERIAL_INNER_ERR,"genDetailTransBeforeProcess()异常"+ e.getMessage());
        }

        return new ArrayList<>();
    }

    private List<TransTransreq> generateSerialAndSave(BaseRequest parentBaseRequest, SerialNo serialNo, Object arg) {
        List<TransTransreq> transList = new ArrayList<>();
        if (arg instanceof List) {
            List<?> value = (List<BaseSerialNoRequest>) arg;
            if (value != null && value.size() > 0) {
                for (int k = 0; k < value.size(); k++) {
                    BaseSerialNoRequest detailBaseRequet = (BaseSerialNoRequest) value.get(k);
                    TransTransreq detailTrans = saveDetailTrans(parentBaseRequest.clone(), detailBaseRequet, serialNo.transCode(), serialNo.description());
                    transList.add(detailTrans);
                }
            }
        } else if (arg instanceof BaseSerialNoRequest) {
            BaseSerialNoRequest baseSerialNoRequest = (BaseSerialNoRequest) arg;
            TransTransreq detailTrans = saveDetailTrans(parentBaseRequest.clone(), baseSerialNoRequest, serialNo.transCode(), serialNo.description());
            transList.add(detailTrans);
        }
        return transList;
    }

    private TransTransreq saveDetailTrans(BaseRequest baseRequest, BaseSerialNoRequest baseSerialNoRequest, String transCode, String transName) {
        TransTransreq detailTrans = transReqService.getTransTransReq(baseRequest, transCode, transName, false);
        if (StringUtils.isBlank(baseSerialNoRequest.getLink_trans_serial())) {
            baseSerialNoRequest.setLink_trans_serial(SeqUtil.getSeqNum());
        }
        detailTrans.setPlatcust(baseSerialNoRequest.getBase_serial_plutcust());
        detailTrans.setOrder_no(baseSerialNoRequest.getBase_serial_order_no());
        detailTrans.setTrans_serial(baseSerialNoRequest.getLink_trans_serial());
        detailTrans.setBatch_order_no(baseRequest.getOrder_no());
        detailTrans.setNotify_url(baseRequest.getBase_serial_notify_url());
        transReqService.insert(detailTrans);
        return detailTrans;
    }

    public boolean findAnnotionForParam(Annotation[] oneParameterAnnotaions) {
        for (int j = 0; j < oneParameterAnnotaions.length; j++) {
            //获取到基于正则表达式上的注解，在实际应用中可能需要根据业务对其他注解也进行相关的校验
            if (oneParameterAnnotaions[j].annotationType() == SerialNo.class) {
                //SerialNo  transSerial = (SerialNo) oneParameterAnnotaions[j];
                return true;

            }
        }
        return false;
    }

    /**
     * 获取绑定字段属性值
     *
     * @param PropertyName
     * @return
     * @author wyc 2016年4月16日
     */
    public Annotation findDetailAnnotionForField(Object query, String PropertyName) {
        try {
            Field field = query.getClass().getDeclaredField(PropertyName);
            Annotation anno = field.getAnnotation(SerialNoDetail.class);
            if (anno != null) {
                return ((SerialNoDetail) anno);
            }
        } catch (SecurityException e) {
            logger.error("[getBindFieldName Exception:]"
                    + e.getMessage());
        } catch (NoSuchFieldException e) {
            logger.error("[getBindFieldName NoSuchFieldException:]"
                    + e.getMessage());
        }
        return null;
    }
}


