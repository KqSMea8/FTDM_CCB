package com.sunyard.sunfintech.web.business;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.MailUtil;
import com.sunyard.sunfintech.core.util.MessageSendUtil;
import com.sunyard.sunfintech.web.model.modulepublic.OutsideResponse;
import com.sunyard.sunfintech.web.utils.OutsidePropertiesUtil;
import com.sunyard.sunfintech.web.utils.SingletonClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by terry on 2018/1/2.
 */
@Service("outsideBusiness")
public class OutsideBusiness {
    private final Logger logger = LogManager.getLogger(this.getClass());


    /**
     * @param mobile 手机号
     * @param msg    发送信息
     * @param channel_code  通道号（10-行内短信接口，20-亿美短信通道）
     * @return
     * @throws BusinessException
     */
    public OutsideResponse sendMsg(String trans_serial, String mobile, String msg, String channel_code, String plat_no) throws BusinessException{
        if("20".equals(channel_code)){
            return sendMsgByChannel20_1(mobile, msg);
        }else if("10".equals(channel_code)) {
            return sendMsgByChannel10_1(mobile, msg);
        }else if("102".equals(channel_code)) {
            return sendMsgByChannel10_2(mobile, msg);
        }else{
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"短信通道号不存在");
        }
    }

    public OutsideResponse sendMail(String subject, String content) throws BusinessException {
        OutsideResponse response = new OutsideResponse();
        try{
            MailUtil.sendMail(subject, content);
            response.setResult(true);
        }catch (Exception e){
            response.setResult(false);
            if(e instanceof BusinessException){
                response.setReturn_data(((BusinessException) e).getErrorMsg());
            }else{
                response.setReturn_data(e.getMessage());
            }
        }
        return response;
    }

    private OutsideResponse sendMsgByChannel10_2(String mobile,String msg){
        OutsideResponse response = new OutsideResponse();
        try {
            String sn = "SDK-BJR-010-00584";
            String pwd = "b-b[57-[bb2";
            String url=OutsidePropertiesUtil.getVal("md_msg_send_url");
            final MessageSendUtil client = new MessageSendUtil(sn,pwd,url);
            //String msg = "【北京银行杭州分行】尊敬的用户，您已在北京银行存管平台（牛板金）成功开通交易账户。";
            //短信发送
            final String content   =   java.net.URLEncoder.encode(msg,"utf-8");
            String rs = client.mdsmssend(mobile, content, "", "", "", "");
            logger.info("OutsideService_sendMsgByChannel10发送结果"+rs);
            response.setResult(true);
            response.setReturn_data(rs);
            return response;
        } catch (Exception e) {
            logger.error("OutsideService_sendMsgByChannel10 短信通道异常:",e);
            throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,e.getMessage());
        }
    }

    private OutsideResponse sendMsgByChannel10_1(String mobile,String msg){
        OutsideResponse response = new OutsideResponse();
        try {
            String sn = "SDK-BJR-010-00662";
            String pwd = "[d-ffa[-[ca";
            String url= OutsidePropertiesUtil.getVal("md_msg_send_url");
            final MessageSendUtil client = new MessageSendUtil(sn,pwd,url);
            //String msg = "【北京银行杭州分行】尊敬的用户，您已在北京银行存管平台（牛板金）成功开通交易账户。";
            //短信发送
            final String content   =   java.net.URLEncoder.encode(msg,"utf-8");
            String rs = client.mdsmssend(mobile, content, "", "", "", "");
            logger.info("OutsideService_sendMsgByChannel10发送结果"+rs);
            response.setResult(true);
            response.setReturn_data(rs);
            return response;
        } catch (Exception e) {
            logger.error("OutsideService_sendMsgByChannel10 短信通道异常:",e);
            throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,e.getMessage());
        }
    }

    private OutsideResponse sendMsgByChannel20_1(String mobile,String msg){
        OutsideResponse response = new OutsideResponse();
        try {
            int rs = SingletonClient.getClient().sendSMS(new String[] { mobile }, msg, "",5);// 带扩展码
            response.setResult(true);
            response.setReturn_data(String.valueOf(rs));
            return response;
        } catch (Exception e) {
            logger.error("OutsideService_sendMsgByChannel20 短信通道异常:",e);
            throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,e.getMessage());
        }
    }
}
