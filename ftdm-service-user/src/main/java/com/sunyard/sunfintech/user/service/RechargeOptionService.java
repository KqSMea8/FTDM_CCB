package com.sunyard.sunfintech.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.account.provider.IAccountAssetService;
import com.sunyard.sunfintech.account.provider.IAccountFrozenService;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.NumberUtils;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.core.util.SysParamterKey;
import com.sunyard.sunfintech.custdao.mapper.CustRwRechargeMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.RwRechargeMapper;
import com.sunyard.sunfintech.pub.model.MsgModel;
import com.sunyard.sunfintech.pub.provider.ISendMsgService;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import com.sunyard.sunfintech.user.provider.IAccountSearchService;
import com.sunyard.sunfintech.user.provider.IRechargeOptionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by terry on 2017/12/8.
 */
@Service(interfaceClass = IRechargeOptionService.class)
@org.springframework.stereotype.Service
public class RechargeOptionService extends BaseServiceSimple implements IRechargeOptionService {

    @Autowired
    private CustRwRechargeMapper custRwRechargeMapper;

    @Autowired
    private IAccountSearchService accountSearchService;

    @Autowired
    private IAccountQueryService accountQueryService;

    @Autowired
    private IAccountFrozenService accountFrozenService;

    @Autowired
    private RwRechargeMapper rwRechargeMapper;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private ISendMsgService sendMsgService;

    @Autowired
    private ITransReqService transReqService;

    @Override
    public boolean doRecharge(RwRecharge rwRecharge, String mall_no, String apply_order_no, String notes) throws BusinessException {
        /**
         * 充值加款会出现并发情况，在出现并发时加锁，保持幂等性
         */
        logger.info("【充值加款】接口调用来源：" + notes + "，trans_serial:" + rwRecharge.getTrans_serial() + "，\nrecharge:" + JSON.toJSONString(rwRecharge));
        if (StringUtils.isEmpty(rwRecharge.getTrans_serial()) || StringUtils.isEmpty(rwRecharge.getPlat_no())
                || StringUtils.isEmpty(mall_no) || StringUtils.isEmpty(notes)) {
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + ",充值加款参数缺失！");
        }
//        String keys="RechargeTransSerial"+rwRecharge.getTrans_serial();
        try {
//            logger.info("【充值加款】充值加款开始，设置redis："+keys);
//            Boolean isRunning=CacheUtil.getCache().setnx(keys,"1");
//            if(!isRunning){
//                //如果已经有线程在加款了，返回false
//                return false;
//            }
            //先查询充值表数据，备份当前状态
            logger.info("【充值加款】查询原始充值订单，trans_serial：" + rwRecharge.getTrans_serial());
            RwRecharge oldRwRecharge = accountSearchService.queryRwRecharge(rwRecharge.getPlat_no(), rwRecharge.getOrder_no());
            if (oldRwRecharge == null) {
                throw new BusinessException(BusinessMsg.ORDER_NOEXISTENT, BusinessMsg.getMsg(BusinessMsg.ORDER_NOEXISTENT) + "：根据原充值订单未找到充值记录");
            }
            //oldTransTransreq为充值申请流水
            TransTransreq oldTransTransreq = transReqService.queryTransTransReqByOrderno(rwRecharge.getOrder_no());
            if (oldTransTransreq == null) {
                throw new BusinessException(BusinessMsg.ORDER_NOEXISTENT, BusinessMsg.getMsg(BusinessMsg.ORDER_NOEXISTENT) + "：根据原充值订单未找到充值申请订单");
            }
            //oldApplyTransTransreq为充值申请流水
            TransTransreq oldApplyTransTransreq = new TransTransreq();
            if (TransConsts.QUICK_CONFIRM_CODE.equals(rwRecharge.getTrans_code()) &&
                    !StringUtils.isEmpty(apply_order_no) && !FlowStatus.INPROCESS.getCode().equals(rwRecharge.getStatus())) {
                oldApplyTransTransreq = transReqService.queryTransTransReqByOrderno(apply_order_no);
                if (oldApplyTransTransreq == null) {
                    logger.info("根据快捷充值确认订单号" + apply_order_no + "未找到快捷充值确认订单");
                    //todo 此处不影响正常业务逻辑，故不抛出异常
                }
            }
            RwRechargeExample rwRechargeExample = new RwRechargeExample();
            RwRechargeExample.Criteria criteria = rwRechargeExample.createCriteria();
            criteria.andIdEqualTo(rwRecharge.getId());
            List<String> statusList = new ArrayList<>();
            statusList.add(FlowStatus.INPROCESS.getCode());
            statusList.add(FlowStatus.REQUESTSUCCESS.getCode());
            criteria.andStatusIn(statusList);
            logger.info("【充值加款】开始更新充值订单，trans_serial：" + rwRecharge.getTrans_serial());
            if (rwRecharge.getSelf_bank_flag() == null && oldRwRecharge.getSelf_bank_flag() == null) {
                rwRecharge.setSelf_bank_flag(IsSelfBank.NO.getCode());
            }
            Integer rechargeUpdateRowsCount = rwRechargeMapper.updateByExampleSelective(rwRecharge, rwRechargeExample);
            logger.info("【充值加款】开始更新充值订单，更新条数：" + rechargeUpdateRowsCount + "，trans_serial：" + rwRecharge.getTrans_serial());
            TransTransreq backTransTransreq = new TransTransreq();
            TransTransreq backApplyTransTransreq = new TransTransreq();
            if (rechargeUpdateRowsCount == 1) {
                //充值流水更新成功，备份业务流水参数
                backTransTransreq.setStatus(oldTransTransreq.getStatus());
                backTransTransreq.setReturn_code(oldTransTransreq.getReturn_code());
                backTransTransreq.setReturn_msg(oldTransTransreq.getReturn_msg());
                //更新业务流水
                oldTransTransreq.setStatus(rwRecharge.getStatus());
                oldTransTransreq.setReturn_code(rwRecharge.getReturn_code());
                oldTransTransreq.setReturn_msg(rwRecharge.getReturn_msg());
                try {
                    logger.info("【充值加款】更新充值申请流水，trans_serial：" + rwRecharge.getTrans_serial());
                    if (TransConsts.QUICK_CONFIRM_CODE.equals(oldTransTransreq.getTrans_code())) {
                        //平台要求，申请确认2步走的订单，成功状态值得是21，而不是1
                        if(OrderStatus.SUCCESS.getCode().equals(rwRecharge.getStatus())){
                            oldTransTransreq.setStatus(FlowStatus.CONFIRMSUCCESS.getCode());
                        }else if(OrderStatus.FAIL.getCode().equals(rwRecharge.getStatus())){
                            oldTransTransreq.setStatus(FlowStatus.CONFIRMFAIL.getCode());
                        }
                    }
                    transReqService.insert(oldTransTransreq);
                } catch (Exception ex) {
                    //流水更新失败，回滚充值状态
                    logger.error("【充值加款】充值申请流水更新失败，回滚充值状态，trans_serial：" + rwRecharge.getTrans_serial() + "，异常信息：", ex);
                    //回滚充值状态
                    rwRechargeMapper.updateByPrimaryKeySelective(oldRwRecharge);
                    return false;
                }

                if (TransConsts.QUICK_CONFIRM_CODE.equals(rwRecharge.getTrans_code()) &&
                        null != oldApplyTransTransreq && null != oldApplyTransTransreq.getTrans_serial() &&
                        !FlowStatus.INPROCESS.getCode().equals(rwRecharge.getStatus())) {
                    //充值流水更新成功，备份业务流水参数
                    backApplyTransTransreq.setStatus(oldApplyTransTransreq.getStatus());
                    backApplyTransTransreq.setReturn_code(oldApplyTransTransreq.getReturn_code());
                    backApplyTransTransreq.setReturn_msg(oldApplyTransTransreq.getReturn_msg());
                    //更新业务流水
                    oldApplyTransTransreq.setStatus(rwRecharge.getStatus());
                    oldApplyTransTransreq.setReturn_code(rwRecharge.getReturn_code());
                    oldApplyTransTransreq.setReturn_msg(rwRecharge.getReturn_msg());
                    try {
                        logger.info("【充值加款】快捷充值订单，更新充值确认流水，trans_serial：" + rwRecharge.getTrans_serial());
                        transReqService.insert(oldApplyTransTransreq);
                    } catch (Exception ex) {
                        //流水更新失败，回滚充值状态
                        logger.error("【充值加款】充值确认流水更新失败，回滚充值状态，trans_serial：" + oldApplyTransTransreq.getTrans_serial() + "，异常信息：", ex);
                        //回滚充值状态
                        rwRechargeMapper.updateByPrimaryKeySelective(oldRwRecharge);
                        //回滚充值申请流水状态
                        backTransTransreq.setExt1("back");
                        transReqService.insert(backTransTransreq);
                        return false;
                    }
                }

                //对于充值成功的，会更新订单状态为成功，更新充值状态为成功，加款，发送短信；对于充值失败的，更新订单状态为失败，更新充值状态为失败；对于处理中的，更新充值状态未处理中。
                //判断充值状态，只有成功的充值才会加款
                if (FlowStatus.SUCCESS.getCode().equals(rwRecharge.getStatus())) {
                    try {
                        //直接update充值表数据，如果update条数为1，更新成功，加款
                        EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
                        if (null != rwRecharge.getPayment_date() && !"".equals(rwRecharge.getPayment_date())) {
                            eaccAccountamtlist.setAccount_date(new SimpleDateFormat("yyyy-MM-dd").parse(DateUtils.format(rwRecharge.getPayment_date(), "yyyyMMdd", "yyyy-MM-dd")));
                        }
                        eaccAccountamtlist.setOrder_no(apply_order_no);
                        if (Ssubject.EACCOUNT.getCode().equals(rwRecharge.getCharge_type())) {
                            eaccAccountamtlist.setPlat_no(mall_no);
                        } else {
                            eaccAccountamtlist.setPlat_no(rwRecharge.getPlat_no());
                        }
                        eaccAccountamtlist.setAmt(rwRecharge.getTrans_amt());
                        eaccAccountamtlist.setPlatcust(rwRecharge.getPlatcust());
                        eaccAccountamtlist.setTrans_code(rwRecharge.getTrans_code());
                        eaccAccountamtlist.setTrans_name(rwRecharge.getTrans_name());
                        eaccAccountamtlist.setTrans_date(rwRecharge.getTrans_date());
                        eaccAccountamtlist.setTrans_time(rwRecharge.getTrans_time());
                        eaccAccountamtlist.setTrans_serial(rwRecharge.getTrans_serial());
                        eaccAccountamtlist.setAmt_type(AmtType.INCOME.getCode());
                        eaccAccountamtlist.setPay_code(rwRecharge.getPay_code());
                        eaccAccountamtlist.setRemark(notes);
                        //如果是本行卡 现金
                        if (IsSelfBank.YES.getCode().equals(rwRecharge.getSelf_bank_flag())) {
                            logger.info("【充值加款】本行卡，trans_serial" + rwRecharge.getTrans_serial());
                            eaccAccountamtlist.setSubject(Tsubject.CASH.getCode());
                        } else {
                            logger.info("【充值加款】不是本行卡，trans_serial" + rwRecharge.getTrans_serial());
                            //如果不是本行卡 则是在途
                            eaccAccountamtlist.setSubject(Tsubject.FLOAT.getCode());
                        }
                        AccountSubjectInfo account = accountQueryService.queryFINANCINGPlatAccount(mall_no, rwRecharge.getPlatcust()
                                , AccountType.ElectronicAccount.getCode(), null, Ssubject.EACCOUNT.getCode());
                        if (null != account && Ssubject.EACCOUNT.getCode().equals(rwRecharge.getCharge_type())) {
                            logger.info("【充值加款】向用户电子账户中加款，trans_serial" + rwRecharge.getTrans_serial());
                            eaccAccountamtlist.setPlat_no(mall_no);
                            eaccAccountamtlist.setSub_subject(Ssubject.EACCOUNT.getCode());
                        } else {
                            logger.info("【充值加款】向用户虚拟账户中加款，trans_serial" + rwRecharge.getTrans_serial());
                            eaccAccountamtlist.setPlat_no(rwRecharge.getPlat_no());
                            eaccAccountamtlist.setSub_subject(rwRecharge.getCharge_type());
                        }//账户加款
                        logger.info("【充值加款】开始加款，加款参数：" + JSON.toJSONString(eaccAccountamtlist));
//                        accountTransferService.singleCharge(eaccAccountamtlist);
                        accountFrozenService.charge(eaccAccountamtlist);
                    } catch (Exception ex) {
                        logger.error("【充值加款】加款失败，回滚充值状态，trans_serial：" + rwRecharge.getTrans_serial() + "，异常信息：", ex);
                        //回滚充值状态
                        rwRechargeMapper.updateByPrimaryKeySelective(oldRwRecharge);
                        //回滚充值申请流水状态
                        backTransTransreq.setExt1("back");
                        transReqService.insert(backTransTransreq);
                        if (TransConsts.QUICK_CONFIRM_CODE.equals(rwRecharge.getTrans_code()) &&
                                null != oldApplyTransTransreq && null != oldApplyTransTransreq.getTrans_serial() &&
                                !FlowStatus.INPROCESS.getCode().equals(rwRecharge.getStatus())) {
                            //如果为快捷确认，回滚确认流水状态
                            backApplyTransTransreq.setExt1("back");
                            transReqService.insert(backApplyTransTransreq);
                        }
                        return false;
                    }
                    //发送短信
                    try {
                        logger.info("【充值加款】加款完成，发送短信，trans_serial" + rwRecharge.getTrans_serial());
                        String name = sysParameterService.querySysParameter(rwRecharge.getCreate_by(), SysParamterKey.MALL_NAME);
                        MsgModel msgModel = new MsgModel();
                        msgModel.setMall_no(mall_no);
//                        msgModel.setPlatcust(rwRecharge.getPlatcust());
                        msgModel.setMobile(rwRecharge.getMobile());
                        msgModel.setPlat_no(rwRecharge.getPlat_no());
                        msgModel.setTrans_code(rwRecharge.getTrans_code());
                        msgModel.setOrder_no(rwRecharge.getOrder_no());
                        String content=sysParameterService.querySysParameter(rwRecharge.getCreate_by(),MsgContent.RECHARGE_SUCCESS_NOTIFY.getMsg());
                        if(StringUtils.isNotBlank(content)){
                            String text = String.format(content, name, NumberUtils.formatNumber(rwRecharge.getTrans_amt()), name);
                            msgModel.setMsgContent(text);
                            sendMsgService.addMsgToQueue(msgModel);
                        }
                    } catch (Exception e) {
                        logger.error("【充值加款】短信发送失败，trans_serial：" + rwRecharge.getTrans_serial() + "，异常信息：", e);
                    }
                }

            } else {
                //如果update条数为0，不加款
                logger.info("【充值加款】更新充值表状态为" + rwRecharge.getStatus() + ",更新成功0条，不做操作，trans_serial：" + rwRecharge.getTrans_serial());
            }

            return true;
        } catch (Exception e) {
            logger.error("【充值加款】充值异常，trans_serial：" + rwRecharge.getTrans_serial() + "，异常信息：", e);
            return false;
        }
//        finally {
//            logger.info("【充值加款】充值加款完成，删除redis："+keys);
//            CacheUtil.getCache().del(keys);
//        }
    }

    @Override
    public List<RwRecharge> queryProcessingRecharge(Integer limit) throws BusinessException {
        String transCode = sysParameterService.querySysParameter(SysParamterKey.FTDM, SysParamterKey.RECHARGE_TRANS_CODE);
        String startTime = sysParameterService.querySysParameter(SysParamterKey.FTDM, SysParamterKey.RECHARGE_START_TIME);
        String endTime = sysParameterService.querySysParameter(SysParamterKey.FTDM, SysParamterKey.RECHARGE_END_TIME);
        String[] transCodes = transCode.split(",");
        Map<String, Object> map = new HashMap<>();
        map.put("queryStartTime", startTime);
        map.put("queryEndTime", endTime);
        map.put("limit", limit);
        map.put("trans_codes", transCodes);
        List<RwRecharge> rwRechargeList = custRwRechargeMapper.queryProcessingRecharge(map);
        return rwRechargeList;
    }

    @Override
    public List<RwRecharge> querySuccessRecharge(String date) throws BusinessException {
        RwRechargeExample example = new RwRechargeExample();
        RwRechargeExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(OrderStatus.SUCCESS.getCode());
        criteria.andPayment_dateEqualTo(date);
        List<RwRecharge> recharges = rwRechargeMapper.selectByExample(example);
        return recharges;
    }
}
