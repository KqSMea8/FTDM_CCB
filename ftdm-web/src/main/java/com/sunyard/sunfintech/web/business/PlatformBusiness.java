package com.sunyard.sunfintech.web.business;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.core.dic.OrderStatus;
import com.sunyard.sunfintech.core.dic.PayStatus;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import com.sunyard.sunfintech.dao.entity.RwRecharge;
import com.sunyard.sunfintech.dao.entity.RwWithdraw;
import com.sunyard.sunfintech.user.model.bo.*;
import com.sunyard.sunfintech.user.provider.IAccountSearchService;
import com.sunyard.sunfintech.user.provider.IPlatformFeeQueryService;
import com.sunyard.sunfintech.user.provider.IPlatformOptionService;
import com.sunyard.sunfintech.web.model.vo.platform.DownloadAccountListRequest;
import com.sunyard.sunfintech.user.model.bo.WithdrawResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by wubin on 2017/5/23.
 */
@Service("platformBusiness")
public class PlatformBusiness {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private IPlatformOptionService platformOptionService;

    @Autowired
    private IPlatformFeeQueryService platformFeeQueryService;

    @Autowired
    private NotifyBusiness notifyBusiness;


    @Autowired
    private IAccountSearchService accountSearchService;
    @Autowired
    private IAccountQueryService accountQueryService;

    /**
     * 资金冻结解冻
     * @author wubin
     * @param freezeFundRequest 资金冻结解冻请求参数
     * @return FreezeFundResponse 资金冻结解冻响应参数
     */
    public FreezeFundResponse freezeFund(FreezeFundRequest freezeFundRequest){

        FreezeFundResponse freezeFundResponse = new FreezeFundResponse();
        try{
            freezeFundResponse = platformOptionService.freezeFund(freezeFundRequest);
        } catch (BusinessException e){
            freezeFundResponse.setRecode(e.getBaseResponse().getRecode());
            freezeFundResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        return freezeFundResponse;
    }

    /**
     * 平台转帐个人
     * @author wubin
     * @param transferToPersonRequest 平台转帐个人请求参数
     */
    public BaseResponse transferToPerson(TransferToPersonRequest transferToPersonRequest){
        BaseResponse baseResponse = new BaseResponse();
        try{
            baseResponse = platformOptionService.transferToPerson(transferToPersonRequest);
        } catch (BusinessException e){
            baseResponse.setRecode(e.getBaseResponse().getRecode());
            baseResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        return baseResponse;
    }

    /**
     * 批量平台转帐个人
     * @author wubin
     * @param batchTransferToPersonRequest 平台转帐个人请求参数
     */
    public BatchTransferToPersonResponse batchTransferToPerson(BatchTransferToPersonRequest batchTransferToPersonRequest){
        BatchTransferToPersonResponse batchTransferToPersonResponse = new BatchTransferToPersonResponse();
        batchTransferToPersonResponse.setTrans_date(DateUtils.getyyyyMMddDate());
        try{
            batchTransferToPersonResponse = platformOptionService.batchTransferToPerson(batchTransferToPersonRequest);
            batchTransferToPersonResponse.setOrder_status(OrderStatus.PROCESSING.getCode());
            batchTransferToPersonResponse.setRecode(BusinessMsg.SUCCESS);
            batchTransferToPersonResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        } catch (BusinessException e){
            batchTransferToPersonResponse.setRecode(e.getBaseResponse().getRecode());
            batchTransferToPersonResponse.setRemsg(e.getBaseResponse().getRemsg());
            batchTransferToPersonResponse.setOrder_status(OrderStatus.FAIL.getCode());
            batchTransferToPersonResponse.setOrder_info(OrderStatus.FAIL.getMsg());
        }
        return batchTransferToPersonResponse;
    }


    public BaseResponse transferFromPerson(TransferToPersonRequest transferToPersonRequest){
        BaseResponse baseResponse = new BaseResponse();
        try{
            baseResponse = platformOptionService.transferFromPerson(transferToPersonRequest);
        } catch (BusinessException e){
            baseResponse.setRecode(e.getBaseResponse().getRecode());
            baseResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        baseResponse.setOrder_no(transferToPersonRequest.getOrder_no());
        baseResponse.setSign("");
        return baseResponse;
    }
    /**
     * 平台转帐个人撤销
     * @author wuml
     * @param  transferToPersonBackRequest 平台转帐个人撤销请求参数
     */
    public BaseResponse rollback_plat2person(TransferToPersonBackRequest transferToPersonBackRequest){
        BaseResponse baseResponse = new BaseResponse();
        try{
            baseResponse = platformOptionService.rollback_plat2person(transferToPersonBackRequest);
        } catch (BusinessException e){
            baseResponse.setRecode(e.getBaseResponse().getRecode());
            baseResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        return baseResponse;
    }

    /**
     * 平台不同账户转账
     * @author wubin
     * @param transferToPlatformRequest 平台不同账户转账请求参数
     * @return BaseResponse 平台不同账户转账响应参数
     */
    public TransferToPlatformResponse transferToPlatform(TransferToPlatformRequest transferToPlatformRequest){

        TransferToPlatformResponse transferToPlatformResponse = new TransferToPlatformResponse();
        try{
            transferToPlatformResponse = platformOptionService.transferToPlatform(transferToPlatformRequest);

        } catch (BusinessException e){
            transferToPlatformResponse.setRecode(e.getBaseResponse().getRecode());
            transferToPlatformResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        return transferToPlatformResponse;
    }

    /**
     * 平台充值
     * @author wml
     * @param chargeRequest
     * @return BaseResponse
     */
    public ChargeResponse charge(ChargeRequest chargeRequest){
        ChargeResponse chargeResponse = new ChargeResponse();
        OrderData orderData=new OrderData();
        try{
            chargeResponse = platformOptionService.charge(chargeRequest);
        }catch (BusinessException b){
            chargeResponse.setRecode(b.getBaseResponse().getRecode());
            chargeResponse.setRemsg(b.getBaseResponse().getRemsg());
            orderData.setProcess_date(DateUtils.formatDateToStr(new Date()));
            orderData.setOrder_status(OrderStatus.FAIL.getCode());
        }
        chargeResponse.setOrder_no(chargeRequest.getOrder_no());
        return chargeResponse;
    }

    public BaseResponse platChargeAsyn(Map<String,Object> map){
        BaseResponse baseResponse = new BaseResponse();
        try{
            Map<String,Object> changeBussMap = platformOptionService.platChargeAsyn(map);
            if(null==changeBussMap){
                //说明接收到支付异步时，存管已经是终态，不再通知平台
                baseResponse.setRecode(BusinessMsg.SUCCESS);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS)+"success");
                return baseResponse;
            }
            baseResponse.setRecode(BusinessMsg.SUCCESS);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS)+"success");
        }catch (BusinessException b){
            baseResponse.setRecode(b.getBaseResponse().getRecode());
            baseResponse.setRemsg(b.getBaseResponse().getRemsg());
        }
        return baseResponse;
    }

    /**
     * 平台提现
     * @author pzy
     * @param withdrawRequest 平台提现请求参数
     * @return WithdrawResponse 平台提现响应参数
     */
    public WithdrawResponse withdraw(WithdrawRequest withdrawRequest){
        return platformOptionService.withdraw(withdrawRequest);
    }

    public BaseResponse platWithdrawAsyn(Map<String,Object> map){
        BaseResponse baseResponse = new BaseResponse();
        try{
            Map<String,Object> withDrawBussMap = platformOptionService.platWithdrawAsyn(map);
           // PlatAsynRequest platAsynRequest = setRequestAsynObject(withDrawBussMap);
           // notifyBusiness.sendNotify(withDrawBussMap.get("mall_no").toString(),withDrawBussMap.get("notify").toString(),withDrawAsynRqString);
            baseResponse.setRecode(BusinessMsg.SUCCESS);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS)+"success");
        }catch (BusinessException b){
            baseResponse.setRecode(b.getBaseResponse().getRecode());
            baseResponse.setRemsg(b.getBaseResponse().getRemsg());
        }
        return baseResponse;
    }

    public List<SubstitutePayFeeData> substitutePayFee(SubstitutePayFeeRequest substitutePayFeeRequest){
        return platformFeeQueryService.substitutePayFeeQuery(substitutePayFeeRequest);
    }

    public String downAccountList(DownloadAccountListRequest downloadAccountListRequest, HttpServletRequest httpServletRequest){
        String filePath="";
        try {
            List<EaccAccountamtlist> eaccAccountamtlistList=    accountQueryService.accountFlowListQuery(downloadAccountListRequest.getPlatcust(),
                    downloadAccountListRequest.getStart_date(),downloadAccountListRequest.getEnd_date());
//            List<EaccAccountamtlist> eaccAccountamtlistList=platformOptionService.downAccountList(downloadAccountListRequest.getPlatcust(),
//                    downloadAccountListRequest.getStart_date(),downloadAccountListRequest.getEnd_date());
            if(eaccAccountamtlistList!=null){
                //将数据写入文件
                try{
                    filePath=creatPDF(eaccAccountamtlistList,httpServletRequest);
                }catch (Exception e){
                    BaseResponse baseResponse=new BaseResponse();
                    baseResponse.setRecode(BusinessMsg.DOWNLOADFILE_FAILED);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DOWNLOADFILE_FAILED)+"：PDF生成失败");
                    throw new BusinessException(baseResponse);
                }
            }
        }catch (Exception e){
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException) e).getBaseResponse();
            }else{
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            throw new BusinessException(baseResponse);
        }
        return filePath;
    }

    public String creatPDF(List<EaccAccountamtlist> eaccAccountamtlists,HttpServletRequest httpServletRequest) throws Exception {
        String dest=httpServletRequest.getSession().getServletContext().getRealPath("") ;
        String filePath=dest+"\\inputFiles\\"+ SeqUtil.getSeqNum()+".pdf";
        File file = new File(filePath);
        file.getParentFile().mkdirs();

        BaseFont baseFont = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
        Font font = new Font(baseFont);

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();
        PdfPTable table = new PdfPTable(15);

        //添加表头
        table.addCell(new Paragraph("流水号",font));
        table.addCell(new Paragraph("平台编号",font));
        table.addCell(new Paragraph("平台客户号",font));
        table.addCell(new Paragraph("科目",font));
        table.addCell(new Paragraph("子科目",font));
        table.addCell(new Paragraph("交易代码",font));
        table.addCell(new Paragraph("交易名称",font));
        table.addCell(new Paragraph("交易日期",font));
        table.addCell(new Paragraph("交易时间",font));
        table.addCell(new Paragraph("交易类型",font));
        table.addCell(new Paragraph("对手账户",font));
        table.addCell(new Paragraph("对手科目",font));
        table.addCell(new Paragraph("对手子科目",font));
        table.addCell(new Paragraph("交易金额",font));
        table.addCell(new Paragraph("创建时间",font));
        //输出数据
        for(EaccAccountamtlist eaccAccountamtlist:eaccAccountamtlists){
            table.addCell(eaccAccountamtlist.getTrans_serial());
            table.addCell(eaccAccountamtlist.getPlat_no());
            table.addCell(eaccAccountamtlist.getPlatcust());
            table.addCell(eaccAccountamtlist.getSubject());
            table.addCell(eaccAccountamtlist.getSub_subject());
            table.addCell(eaccAccountamtlist.getTrans_code());
            table.addCell(new Paragraph(eaccAccountamtlist.getTrans_name(),font));
            table.addCell(eaccAccountamtlist.getTrans_date());
            table.addCell(eaccAccountamtlist.getTrans_time());
            table.addCell(eaccAccountamtlist.getAmt_type());
            table.addCell(eaccAccountamtlist.getOppo_platcust());
            table.addCell(eaccAccountamtlist.getOppo_subject());
            table.addCell(eaccAccountamtlist.getOppo_sub_subject());
            table.addCell(eaccAccountamtlist.getAmt().toString());
            table.addCell(DateUtils.format(eaccAccountamtlist.getCreate_time(),DateUtils.DEF_FORMAT));
        }
        document.add(table);
        document.close();
        return file.getAbsolutePath();
    }

    //充值提现异步通知参数设置
    private PlatAsynRequest setRequestAsynObject(Map<String,Object> bussMap){
        PlatAsynRequest platAsynRequest = new PlatAsynRequest();
        platAsynRequest.setPlat_no(bussMap.get("plat_no").toString());
        platAsynRequest.setAmt(new BigDecimal(bussMap.get("amt").toString()));
        platAsynRequest.setCode(bussMap.get("code").toString());
        platAsynRequest.setOrder_no(bussMap.get("order_no").toString());
        platAsynRequest.setMsg(bussMap.get("msg").toString());
        platAsynRequest.setSign("");
        return platAsynRequest;
    }

    public void batchRwRechargeNotify(){

        logger.info("【向E支付请求查询订单状态 - 充值表】开始平台充值请求订单号并发送异步通知  web");

        List<RwRecharge> rwRecharges = new ArrayList<RwRecharge>();

        try{

            rwRecharges =null;//todo wml managementPayOutService.queryRwRecharge(TransConsts.PLAT_CHARGE_CODE);
            if(null==rwRecharges || 0==rwRecharges.size()){
                return;
            }
            for (RwRecharge rwRecharge : rwRecharges){
                RwRecharge rwRechargeNotify =null;//todo wml  managementPayOutService.epayRwRechargeQuery(rwRecharge);
                if(rwRechargeNotify != null && (rwRechargeNotify.getStatus().equals(OrderStatus.SUCCESS.getCode()) || rwRechargeNotify.getStatus().equals(OrderStatus.FAIL.getCode()))){

                    logger.info("【E支付返回成功后 - 充值表】开始发送异步通知 success or fail 的通知   RwRecharge   id:" + rwRechargeNotify.getId());

                    String mall_no = accountSearchService.queryMallNoByPlatNo(rwRechargeNotify.getPlat_no());
                    PlatAsynRequest platAsynRequest = new PlatAsynRequest();
                    platAsynRequest.setPlat_no(rwRechargeNotify.getPlat_no());
                    platAsynRequest.setAmt(rwRechargeNotify.getTrans_amt());
                    platAsynRequest.setOrder_no(rwRechargeNotify.getOrder_no());
                    platAsynRequest.setMall_no(mall_no);
                    if(rwRechargeNotify.getStatus().equals(OrderStatus.SUCCESS.getCode()))
                        platAsynRequest.setCode("1");
                    if(rwRechargeNotify.getStatus().equals(OrderStatus.FAIL.getCode()))

                        platAsynRequest.setCode("2");
                    String data = JSONObject.toJSONString(platAsynRequest);
                    logger.info("【准备发送平台充值异步通知】=============url:"+rwRechargeNotify.getNotify_url()+",json:"+data);
                    //todo  通知方法被磊哥注释掉了，为了避免错误，注释下面这条
                    //notifyBusiness.nofity(rwRechargeNotify.getNotify_url(),data);

                }
            }

        }catch (Exception e){
            logger.error("开始平台充值请求订单号并发送异步通知  web  异常 - 充值表" + e);
            e.printStackTrace();
        }
    }

    /**
     *  平台提现请求订单号并发送异步通知
     */
    public void batchPlatWithdrawNotify(){

        logger.info("【向E支付请求查询订单状态 - 提现表】开始平台提现请求订单号并发送异步通知  web");

        List<RwWithdraw> rwWithdrawList = new ArrayList<RwWithdraw>();

        try{

            rwWithdrawList = null;// todo wml managementPayOutService.queryRwWithdraw(TransConsts.PLAT_WITHDRAW_CODE);
            if(0==rwWithdrawList.size() || null==rwWithdrawList){
                return;
            }
            logger.info("【向E支付请求查询平台提现订单状态 - 提现表】查询到的平台提现数据  web :" + rwWithdrawList.size());

            for (RwWithdraw rwWithdraw : rwWithdrawList){

                RwWithdraw rwWithdrawNotify =null;// todo wml  managementPayOutService.epayPlatRwBatchQuery(rwWithdraw);

                if(rwWithdrawNotify != null && (rwWithdrawNotify.getPay_status().equals(PayStatus.SUCCESS.getCode()) || rwWithdrawNotify.getPay_status().equals(PayStatus.FAIL.getCode()))){

                    logger.info("【E支付平台提现返回成功后 - 提现表】开始发送异步通知 success or fail 的通知   RwWithdraw   id:" + rwWithdrawNotify.getId());

                    String mall_no = accountSearchService.queryMallNoByPlatNo(rwWithdrawNotify.getPlat_no());
                    PlatAsynRequest platAsynRequest = new PlatAsynRequest();
                    platAsynRequest.setPlat_no(rwWithdrawNotify.getPlat_no());
                    platAsynRequest.setAmt(rwWithdrawNotify.getOut_amt());
                    platAsynRequest.setOrder_no(rwWithdrawNotify.getOrder_no());
                    platAsynRequest.setMall_no(mall_no);
                    if(rwWithdrawNotify.getPay_status().equals(PayStatus.SUCCESS.getCode()))
                        platAsynRequest.setCode("1");
                    if(rwWithdrawNotify.getPay_status().equals(PayStatus.FAIL.getCode()))
                        platAsynRequest.setCode("2");
                    String data = JSONObject.toJSONString(platAsynRequest);
                    logger.info("【准备发送平台提现异步通知】=============url:"+rwWithdrawNotify.getNotify_url()+",json:"+data);
                    //todo  通知方法被磊哥注释掉了，为了避免错误，注释下面这条
                    //notifyBusiness.nofity(rwWithdrawNotify.getNotify_url(),data);

                }

            }

        }catch (Exception e){

            logger.error("开始平台提现请求订单号并发送异步通知  web  异常 - 提现表" + e);
            e.printStackTrace();

        }
    }

}
