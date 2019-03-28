package com.sunyard.sunfintech.user.modulepublic;

import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.CardStatus;
import com.sunyard.sunfintech.core.dic.CardType;
import com.sunyard.sunfintech.core.dic.IsUse;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.threads.SingleThreadPool;
import com.sunyard.sunfintech.core.util.*;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.AccountSubjectInfoMapper;
import com.sunyard.sunfintech.dao.mapper.EaccCardinfoMapper;
import com.sunyard.sunfintech.dao.mapper.EaccUserinfoMapper;
import com.sunyard.sunfintech.dao.mapper.MessageSwithesMapper;
import com.sunyard.sunfintech.pub.model.CcbSendMsgReq;
import com.sunyard.sunfintech.pub.model.MailModel;
import com.sunyard.sunfintech.pub.model.MsgModel;
import com.sunyard.sunfintech.pub.provider.ISendMsgService;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.user.provider.IAccountSearchService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by terry on 2018/1/2.
 */
@CacheConfig(cacheNames="sendMsgService")
@Service("sendMsgService")
public class SendMsgService extends BaseServiceSimple implements ISendMsgService {

    @Resource(name = "amqpTemplate")
    private AmqpTemplate amqpTemplate;

    @Autowired
    private MessageSwithesMapper messageSwithesMapper;

    @Autowired
    private EaccCardinfoMapper eaccCardinfoMapper;

    @Autowired
    private EaccUserinfoMapper eaccUserinfoMapper;

//    @Autowired
//    private IOutsideService iOutsideService;

    @Autowired
    private AccountSubjectInfoMapper accountSubjectInfoMapper;

    @Autowired
    private IAccountSearchService accountSearchService;

    @Autowired
    private ISysParameterService sysParameterService;

    //yanglei static final String REDISKEY_SYS_PARAMETER = Constants.CACHE_NAMESPACE + "sys_parameter:";

    @Override
    public void sendErrorToAdmin(String content, String plat_no) throws BusinessException {
        try{
            SingleThreadPool.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    logger.info("【短信发送】========发送系统异常通知短信给管理员");
                    MsgModel msgModel = new MsgModel();
                    msgModel.setOrder_no("000000000000000");
                    msgModel.setPlat_no(plat_no);
                    msgModel.setTrans_code(TransConsts.SYSTEM_ERROR_CODE);
                    String mall_no=accountSearchService.queryMallNoByPlatNo(plat_no);
                    String mobileArrayStr=sysParameterService.querySysParameter(mall_no, SysParamterKey.SYSTEM_ADMIN_MOBILE);
                    String[] mobileArray=mobileArrayStr.split(",");
                    for(String mobile:mobileArray){
                        msgModel.setMobile(mobile);
                        msgModel.setMsgContent(content);
                        addMsgToQueue(msgModel);
                    }
                }
            });
        }catch (Exception e){
            logger.info("【短信发送】========系统异常通知管理员失败：",e);
        }
    }

    /**
     *发送短信
     * @param msgModel
     * @throws BusinessException
     */
    @Override
    public void addMsgToQueue(final MsgModel msgModel) throws BusinessException {
        try{
            if(StringUtils.isEmpty(msgModel.getMsgContent())){
                logger.info("【短信发送】短信内容不可为空");
                return;
            }
            String permission = querySendMsgPermission(msgModel);
            if (IsUse.NO.getCode().equals(permission)){
                logger.info("【短信发送】短信开关控制表控制改操作不发送短信，trans_code："+msgModel.getTrans_code()+",plat_no："+msgModel.getPlat_no());
            }else{
                if(StringUtils.isEmpty(msgModel.getMobile())){
                    if(StringUtils.isEmpty(msgModel.getPlatcust())){
                        logger.info("【短信发送】platcust和mobile均为空，无法发送短信！");
                        return;
                    }
                    //根据平台客户号查询绑卡手机号
                    EaccCardinfoExample eaccCardinfoExample=new EaccCardinfoExample();
                    EaccCardinfoExample.Criteria criteria=eaccCardinfoExample.createCriteria();
                    criteria.andMall_noEqualTo(msgModel.getMall_no());
                    criteria.andMallcustEqualTo(msgModel.getPlatcust());
                    criteria.andEnabledEqualTo(Constants.ENABLED);
                    criteria.andStatusEqualTo(CardStatus.ACTIVE.getCode());
                    criteria.andCard_typeEqualTo(CardType.DEBIT.getCode());
                    List<EaccCardinfo> eaccCardinfoList=eaccCardinfoMapper.selectByExample(eaccCardinfoExample);
                    if(eaccCardinfoList.size()<=0){
                        EaccUserinfoExample eaccUserinfoExample=new EaccUserinfoExample();
                        EaccUserinfoExample.Criteria userCriteria=eaccUserinfoExample.createCriteria();
                        userCriteria.andMall_noEqualTo(msgModel.getMall_no());
                        userCriteria.andMallcustEqualTo(msgModel.getPlatcust());
                        userCriteria.andEnabledEqualTo(Constants.ENABLED);
                        List<EaccUserinfo> eaccUserinfoList=eaccUserinfoMapper.selectByExample(eaccUserinfoExample);
                        if(eaccUserinfoList.size()>0 && !StringUtils.isEmpty(eaccUserinfoList.get(0).getMobile())){
                            msgModel.setMobile(eaccUserinfoList.get(0).getMobile());
                        }else{
                            return;
                        }
                    }else{
                        msgModel.setMobile(eaccCardinfoList.get(0).getMobile());
                    }
                }
//                String destination="MsgSendReqQueue";
//                jmsQueueTemplate.send(destination, new MessageCreator() {
//                    @Override
//                    public Message createMessage(Session session) throws JMSException {
//                        System.out.println("发送消息");
//                        return jmsQueueTemplate.getMessageConverter().toMessage(msgModel, session);
//                    }
//                });
                logger.info("【短信发送】deployEnvironment："+deployEnvironment);
                if("BOB".equals(deployEnvironment)){
                    MQUtils.send(amqpTemplate, "ftdm.user.direct.exchange", "MsgSendReqQueue", msgModel);
                }else{
                    MQUtils.send(amqpTemplate, "ftdm.user.direct.exchange", "CCBMsgSendReqQueue", msgModel);
                }
            }
        }catch (Exception e){
            logger.info("【短信发送】异常：",e);
        }
    }

    @Override
    public BaseResponse ccbSendMsg(CcbSendMsgReq ccbSendMsgReq) throws BusinessException {
        return null;
    }


    @Override
    public BigDecimal getAccountAllAmount(String platcust) throws BusinessException {
        AccountSubjectInfoExample accountSubjectInfoExample=new AccountSubjectInfoExample();
        AccountSubjectInfoExample.Criteria criteria=accountSubjectInfoExample.createCriteria();
        criteria.andPlatcustEqualTo(platcust);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<AccountSubjectInfo> currentAccountList= accountSubjectInfoMapper.selectByExample(accountSubjectInfoExample);
        BigDecimal allAmt=BigDecimal.ZERO;
        for (AccountSubjectInfo currentAccount:currentAccountList){
            allAmt=allAmt.add(currentAccount.getN_balance());
        }
        return allAmt;
    }


    @Override
    public void sendMail(String content, String mail_subject) throws BusinessException {
        try{
            //先读取redis，5分钟内相同内容的邮件只能发一封，防止邮件轰炸。
            if(CacheUtil.getCache().setnx(mail_subject+content, DateUtils.todayStr())){
                CacheUtil.getCache().expire(mail_subject+content,300);
                MailModel mailModel=new MailModel();
                mailModel.setMail_content(content);
                mailModel.setSubject(mail_subject);
                MQUtils.send(amqpTemplate, "ftdm.user.direct.exchange", "MailSendQueue", mailModel);
            }
        }catch (Exception e){
            logger.error("邮件发送异常：",e);
        }
    }

    /**
     * 从缓存中获取对应trans_code的短信发送权限
     * @param msgModel
     * @return
     * @throws BusinessException
     */
    public String querySendMsgPermission(MsgModel msgModel) throws BusinessException {
        validate(msgModel);
        String dataKey = msgModel.getPlat_no() + Constants.DATA_SPLIT_WITH + msgModel.getTrans_code();
        String key = Constants.getMessageSwithesKey(serviceName, dataKey);

        logger.info("开始从reids获取系统参数");
        if(null == CacheUtil.getCache().get(key)){
            logger.info("reids中没有key所对应的系统参数");
            MessageSwithesExample messageSwithesExample=new MessageSwithesExample();
            MessageSwithesExample.Criteria criteria=messageSwithesExample.createCriteria();
            criteria.andPlat_noEqualTo(msgModel.getPlat_no());
            criteria.andTrans_codeEqualTo(msgModel.getTrans_code());
            criteria.andEnabledEqualTo(Constants.ENABLED);
            List<MessageSwithes> messageSwithesList=messageSwithesMapper.selectByExample(messageSwithesExample);
            if(messageSwithesList.size() == 1 && null != messageSwithesList.get(0)){
                logger.info("把"+key+":"+messageSwithesList.get(0).getPermission()+"系统参数设置到reids中");
                //重新设置参数到缓存中
                CacheUtil.getCache().set(key, messageSwithesList.get(0).getPermission());
                return messageSwithesList.get(0).getPermission();
            }else{
                logger.info("获取不到正确的系统参数");
                logger.info("获取不到正确的系统参数=======================================================");
                //throw new BusinessException(BusinessMsg.FAIL, "获取不到正确的系统参数");
                return IsUse.NO.getCode();
            }
        }
        return CacheUtil.getCache().get(key).toString();
    }
}
