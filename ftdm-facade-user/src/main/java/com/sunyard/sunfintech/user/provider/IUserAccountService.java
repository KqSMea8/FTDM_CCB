package com.sunyard.sunfintech.user.provider;


import com.sunyard.sunfintech.account.model.bo.FirstCertRequest;
import com.sunyard.sunfintech.account.model.bo.FirstCertResponse;
import com.sunyard.sunfintech.account.model.bo.QueryFirstCertRequest;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.user.model.bo.ProdPublishRequest;
import com.sunyard.sunfintech.user.model.bo.*;

import java.util.List;

/**
 * Created by terry on 2017/5/15.
 */
public interface IUserAccountService {

//    /**
//     * 用户投融资账户转账
//     * @author terry
//     * @param switchAccountRequestBo
//     * @return true：转账成功
//     * @throws BusinessException
//     */
//    public boolean switchAccount(SwitchAccountRequestBo switchAccountRequestBo)throws BusinessException;


    /**
     * 客户信息变更
     *
     * @param changeAccountInfoRequest
     * @return
     * @throws BusinessException 抛出的异常
     * @author Zz
     */
    public ChangeAccountInfoResponse changeAccountInfo(ChangeAccountInfoRequest changeAccountInfoRequest) throws BusinessException;

    /**
     * 查询平台账户映射关系
     *
     * @param mall_no
     * @param plat_no
     * @param platcust
     * @return
     * @throws BusinessException
     */
    public EaccAccountinfo queryEaccAccountInfo(String mall_no, String plat_no, String platcust) throws BusinessException;

    /**
     * 查询用户绑卡信息
     *
     * @param mall_no
     * @param mallcust
     * @return
     * @throws BusinessException
     * @author terry
     */
    public EaccCardinfo queryEaccCardInfo(String mall_no, String mallcust, String card_no, String pay_channel) throws BusinessException;

    /**
     * 查询用户所有绑卡信息
     *
     * @param mall_no
     * @param mallcust
     * @return
     * @throws BusinessException
     * @author terry
     */
    public List<EaccCardinfo> queryEaccCardInfolist(String mall_no, String mallcust,String card_no) throws BusinessException;

    /**
     * 判断用户信用卡绑卡是否超限
     *
     * @param mall_no
     * @param mallcust
     * @return boolean
     * @throws BusinessException
     * @author raoyulu
     */
    public boolean checkCreditCardLimit(String mall_no, String mallcust) throws BusinessException;

    /**
     * 判断用户借机卡绑卡是否超限
     *
     * @param mall_no
     * @param mallcust
     * @return List<EaccCardinfo>
     * @throws BusinessException
     * @author terry
     */
    public boolean checkCardLimit(String mall_no, String mallcust) throws BusinessException;

    /**
     * 查询最后解绑的一张卡
     *
     * @param mall_no
     * @param mallcust
     * @return
     * @throws BusinessException
     */
    public EaccCardinfo queryLastUnbindEaccCardInfo(String mall_no, String mallcust) throws BusinessException;

    /**
     * 更新用户绑卡信息
     *
     * @param eaccCardInfo
     * @return
     * @throws BusinessException
     * @author terry
     */
    public boolean updateCardInfo(EaccCardinfo eaccCardInfo) throws BusinessException;

    /**
     * 通过身份证号和用户姓名查询用户信息
     *
     * @param mall_no
     * @param id_code
     * @param mallcust
     * @return
     * @throws BusinessException
     * @author terry
     */
    public EaccUserinfo queryEaccUserInfo(String mall_no, String mallcust, String id_code, String org_name) throws BusinessException;

    /**
     * 通过身份证号和用户姓名查询用户信息(带状态)
     *
     * @param mall_no
     * @param id_code
     * @param mallcust
     * @param status
     * @return
     * @throws BusinessException
     * @author terry
     */
    public EaccUserinfo queryEaccUserInfo(String mall_no, String mallcust, String id_code, String org_name,String... status) throws BusinessException;

    /**
     * 查询用户信息
     *
     * @param mall_no  集团号
     * @param platcust 集团客户号
     * @return EaccUserinfo
     * @throws BusinessException
     * @author pengzy
     */
    public EaccUserinfo queryEaccUserInfoByMallNoAndPlatcust(String mall_no, String platcust) throws BusinessException;

    /**
     * 通过集团客户号更新用户信息
     *
     * @param eaccUserinfo
     * @param mall_no
     * @param mallcust
     * @return
     * @throws BusinessException
     */
    public boolean updateEaccUserInfo(EaccUserinfo eaccUserinfo, String mall_no, String mallcust) throws BusinessException;

    /**
     * 通过主键更新用户信息
     *
     * @param eaccUserinfo
     * @return
     * @throws BusinessException
     */
    public boolean updateEaccUserInfo(EaccUserinfo eaccUserinfo) throws BusinessException;

    /**
     * 注册绑卡信息
     *
     * @param eaccAccountinfo
     * @param eaccUserInfo
     * @return
     * @throws BusinessException
     * @author terry
     */
    public boolean register(EaccAccountinfo eaccAccountinfo, EaccUserinfo eaccUserInfo) throws BusinessException;

    /**
     * 添加绑卡信息
     *
     * @param eaccCardInfo
     * @return
     * @throws BusinessException
     * @author terry
     */
    public boolean addBindCardInfo(EaccCardinfo eaccCardInfo) throws BusinessException;

    /**
     * 查询支付平台映射
     *
     * @param plat_no
     * @param pay_code
     * @return
     * @throws BusinessException
     * @author terry
     */
    public PlatPaycode queryPlatPaycode(String plat_no, String pay_code) throws BusinessException;

    /**
     * 四要素批量开户
     *
     * @param baseRequest 平台号
     * @param list
     * @return BatchRegisterReturnData
     */
    public BatchRegisterReturnOldData batchRegister(BaseRequest baseRequest, List<BatchRegisterRequestDetail> list) throws BusinessException;

    /**
     * 实名认证批量开户
     *
     * @param baseRequest
     * @param batchAuthenticationList
     * @return BatchAuthenticationResponseSuccessDetail
     */
    public BatchAuthenticationReturnData batchAuthentication(BaseRequest baseRequest, List<BatchAuthenticationRequestDetail> batchAuthenticationList) throws BusinessException;

    /**
     * 电子帐户开户
     *
     * @param platplatcustRegister
     * @return platplatcustRegisterResponseData
     */
    public PlatplatcustRegisterResponseData openEaccount(PlatplatcustRegisterRequest platplatcustRegister) throws BusinessException;

    /**
     * 检查用户是否已经注册
     *
     * @param name
     * @param id_card
     * @param plat_no 在isMall为false时可以为null
     * @param isMall  true:查询是否存在集团用户账户 false:查询用户是否在平台用户账户
     * @return
     * @throws BusinessException
     */
    public EaccUserinfo checkUserInfo(String mall_no, String name, String id_card, String plat_no, boolean isMall) throws BusinessException;

    /**
     * 检查企业用户是否已注册
     *
     * @param org_name
     * @param plat_no  在isMall为false时可以为null
     * @param isMall   true:查询是否存在集团用户账户 false:查询用户是否在平台用户账户
     * @return
     * @throws BusinessException
     */
    public EaccUserinfo checkMallUserInfo(String mall_no, String org_name, String plat_no, boolean isMall) throws BusinessException;

    /**
     * 子账户现金转电子账户
     *
     * @param transferEaccountRequest
     * @return
     * @throws BusinessException
     * @author terry
     */
    public boolean transferEaccount(TransferEaccountRequest transferEaccountRequest) throws BusinessException;

    /**
     * 根据渠道编号查询paycode信息
     *
     * @param mer_no
     * @param channel
     * @return
     * @throws BusinessException
     */
    public PlatPaycode queryPayCodeByChannel(String mer_no, String channel) throws BusinessException;

    /**
     * 电子帐户开户for存量用户
     *
     * @param platplatcustRegister
     * @return platplatcustRegisterResponseData
     */
    public PlatplatcustRegisterResponseData openEaccountForRegularSubscribers(PlatplatcustRegisterRequest platplatcustRegister) throws BusinessException;

    /**
     * 查询用户绑卡信息（多卡支持）
     *
     * @author bob
     */
    public List<EaccCardinfo> queryCardInfoForMultiCards(String mall_no, String mallcust) throws BusinessException;

    /**
     * 查询用户绑卡信息（多卡支持）（带状态）
     *
     * @author bob
     */
    public List<EaccCardinfo> queryCardInfoForMultiCards(String mall_no, String mallcust, String... status) throws BusinessException;

    /**
     * 首笔认证
     *
     * @author wml
     */
    FirstCertResponse firstCert(FirstCertRequest firstCertRequest);

    /**
     * 首笔认证查询
     *
     * @author wml
     */
    BaseResponse queryfirstCert(QueryFirstCertRequest queryFirstCertRequest);

    /**
     * 查询对公用户卡信息
     *
     * @author bob
     */
    public EaccCardinfo queryCompanyCard(String mall_no, String mallcust) throws BusinessException;

    /**
     * 用户四要素开户申请
     *
     * @param registerRequest4
     */
    String fourElementsRegister(RegisterRequest4 registerRequest4) throws BusinessException;

    //更新用户充值信息
    public Boolean updateRwRecharge(RwRecharge rwRecharge);

    /**
     * 添加授权信息
     *
     * @param eaccUserauth
     * @throws BusinessException
     */
    public void addEaccUserAuth(EaccUserauth eaccUserauth) throws BusinessException;

    /**
     * 更新授权信息
     *
     * @param updateEaccUserauth
     * @throws BusinessException
     */
    public void updateEaccUserAuth(EaccUserauth updateEaccUserauth) throws BusinessException;

    /**
     * 根据platcust更新授权信息
     *
     * @param updateEaccUserauth
     * @throws BusinessException
     */
    public void updateEaccUserAuthByPlatcust(EaccUserauth updateEaccUserauth) throws BusinessException;

    /**
     * 用户三要素开户申请
     *
     * @param registerRequest3
     * @return
     */
    String threeElementsRegister(RegisterRequest3 registerRequest3) throws BusinessException;

    /**
     * 更换预留手机号
     *
     * @param changePreMobileRequest
     */
    BaseResponse modifyMobile(ChangePreMobileRequest changePreMobileRequest) throws BusinessException;


    /**
     * 业务授权申请
     *
     * @param applyAuthOperaRequest
     * @return
     */
    BaseResponse applyAuthOpera(ApplyAuthOperaRequest applyAuthOperaRequest) throws BusinessException;

    /**
     * 业务授权确认
     *
     * @param confirmAuthOperaRequest
     * @return
     */
    BaseResponse confirmAuthOpera(ConfirmAuthOperaRequest confirmAuthOperaRequest)  throws BusinessException;

    /**
     * 业务授权取消
     *
     * @param cancelAuthOperaRequest
     * @return
     */
    BaseResponse cancelAuthOpera(CancelAuthOperaRequest cancelAuthOperaRequest)  throws BusinessException;

    /**
     * 用户投融资账户转账
     *
     * @param switchAccountRequest
     * @return
     */
    BaseResponse switchAccount(SwitchAccountRequest switchAccountRequest)  throws BusinessException;

    /**
     * 企业开户
     *
     * @param companyRegisterRequest
     * @return
     */
    String companyRegister(CompanyRegisterRequest companyRegisterRequest) throws BusinessException ;

    /**
     * 设置新密码
     *
     * @return
     * @throws BusinessException
     */
    public BaseResponse setNewPassword(SetNewPasswordRequest setNewPasswordRequest) throws BusinessException;

    /**
     * 设置密码
     *
     * @param setPwdRequest
     * @return
     */
    SetPwdRequest setPassword(SetPwdRequest setPwdRequest) throws BusinessException;

    /**
     * 修改密码
     *
     * @param modifyPwdRequest
     * @return
     */
    ModifyPwdRequest modifyPassword(ModifyPwdRequest modifyPwdRequest);

    /**
     * 获取授权列表
     *
     * @param platcust
     * @return
     */
    List<EaccUserauth> listEaccUserAuth(String mallno,String platno,String platcust);



    BaseResponse freezeAccount(FreezeAccountRequest freezeAccountRequest) throws BusinessException;

    BaseResponse unregAccount(UnregAccountRequest unregAccountRequest);


    void changeTransSerialWhileSameOrderNo(TransTransreq transTransReq, RwRecharge rwRecharge);

    AuthInfoResponse queryAuthInfo(AuthInfoRequest authInfoRequest);
}

