package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.user.model.bo.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用户绑卡服务
 * Created by terry on 2017/5/16.
 */
public interface IUserBindCardService {

    /**
     * 短验绑卡申请
     *
     * @param applyBindCardRequestBo
     * @return
     * @throws BusinessException
     * @author terry
     */
    public boolean bingCardByMsg(ApplyBindCardRequestBo applyBindCardRequestBo) throws BusinessException;

    /**
     * 短验确认
     *
     * @param confirmBindCardBo
     * @throws BusinessException
     */
    public String confirmBindCard(ConfirmBindCardBo confirmBindCardBo) throws BusinessException;

    /**
     * 批量换卡
     *
     * @param unBindCardRequestDetail
     * @throws BusinessException
     * @author terry
     */
    public UnBindCardReturn bathUnbindCard(BaseRequest baseRequest, List<UnBindCardRequestDetail> unBindCardRequestDetail) throws BusinessException;

    /**
     * 三要素绑卡（用于信用卡绑卡）
     *
     * @param personBindCardRequest
     * @return
     * @throws BusinessException
     * @author Raoyulu
     */
    public boolean personBindCard(PersonBindCardRequest personBindCardRequest) throws BusinessException;

    /**
     *批量解绑
     * @author Bob
     */
    public UnBindCardReturn batchUnbindCardForMultiCards(BaseRequest baseRequest, List<UnBindCardForMultiCardsRequestDetail> details) throws BusinessException;

    /**
     * 批量换卡（先绑卡再解绑）
     * @param baseRequest
     * @return
     * @throws BusinessException
     */
    public BatchChangeCardReturn batchChangeCard(BaseRequest baseRequest, List<BatchChangeCardRequestDetail> batchChangeCardRequestList) throws BusinessException;

    /**
     * 短验换卡确认（先短验绑卡申请，再调用换卡）
     * @param changeCardByMsgConfirm
     * @return
     * @throws BusinessException
     */
    public BaseResponse changeCardByMsg(ChangeCardByMsgConfirm changeCardByMsgConfirm)throws BusinessException;

    /**
     * 短验换卡申请（先短验绑卡申请）
     * @param changeCardByMsgRequest
     * @return
     * @throws BusinessException
     */
    BaseResponse changeCardByMsgReq(ChangeCardByMsgRequest changeCardByMsgRequest)throws BusinessException;

    /**
     * 四要素开户申请（内部接口）
     * @param applyRequest
     * @throws BusinessException
     */
    public void apply4ElementsRegister(Apply4ElementsRegisterRequest applyRequest)throws BusinessException;

    /**
     * 发送短信(设置密码)
     * @param vcodeRequest
     * @throws Exception
     * @throws BusinessException
     */
    public void getCode4Password(VcodeRequest vcodeRequest) throws Exception, BusinessException;

    /**
     * 发送短信(企业开户)
     * @param vcodeRequest
     * @throws Exception
     * @throws BusinessException
     */
    public void getCode4Company(VcodeRequest vcodeRequest) throws Exception, BusinessException;

    /**
     * 四要素开户确认（内部接口）
     * @param confirmRequest 四要素开户确认参数
     * @return BatchRegisterReturnData
     */
    public BatchRegisterReturnData confirm4ElementsRegister(Confirm4ElementsRegisterRequest confirmRequest)throws BusinessException;

    /**
     * 三要素开户申请（内部接口）
     * @param confirmRequest 三要素开户申请参数
     * @return BatchRegisterReturnData
     */
    public BatchRegisterReturnData apply3ElementsRegister(Apply3ElementsRegisterRequest confirmRequest)throws BusinessException;

    /**
     * 对公开户申请
     * @param applyOrgRegisterRequest
     * @return
     * @throws BusinessException
     */
    public BatchRegisterReturnData applyOrgRegister(ApplyOrgRegisterRequest applyOrgRegisterRequest)throws BusinessException;

    /**
     * 境外开户
     */
    public  BaseResponse applyOrgRegisterOpening(ApplyOrgRegisterOpeningRequest applyRequests )throws  BusinessException;

    /**
     * 对公开户确认（线下审核）
     * @param confirmOrgRegisterRequest
     * @return
     * @throws BusinessException
     */
    public BaseResponse confirmOrgRegister(ConfirmOrgRegisterRequest confirmOrgRegisterRequest)throws BusinessException;

    /**
     * 绑卡核验
     * @param checkCardRequest
     * @return
     * @throws BusinessException
     */
    public BaseResponse checkCard(CheckCardRequest checkCardRequest)throws BusinessException;

    /**
     * 企业绑卡
     * @param bindCardRequest
     * @return boolean
     * @throws BusinessException
     */
    public boolean orgBindCard(BindCardRequest bindCardRequest)throws BusinessException;

    /**
     * 添加收取信息
     * @param auth_types
     * @param authed_amount
     * @param authed_limittime
     * @param authed_status
     * @param mall_no
     * @param plat_no
     * @param platcust
     * @throws BusinessException
     */
    public void addAuthInfo(String auth_types, BigDecimal authed_amount, String authed_limittime,
                            String authed_status, String mall_no, String plat_no, String platcust)throws BusinessException;

    public void validMsgCode(String originOrderNo,String idCode, String trans_serial,String logTitle)throws BusinessException;

    /**
     * 无短验绑卡
     *
     * @param bindCardRequest
     * @return
     * @throws BusinessException
     */
    public boolean bindCardByNoMsg(BindCardRequest bindCardRequest) throws BusinessException;

}

