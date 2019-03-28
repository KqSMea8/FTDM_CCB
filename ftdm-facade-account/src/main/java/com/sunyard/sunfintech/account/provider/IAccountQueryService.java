package com.sunyard.sunfintech.account.provider;

import com.sunyard.sunfintech.account.model.bo.*;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.*;

import java.util.List;

/**
 * @author heroy
 * @version 2017/4/12
 */
public interface IAccountQueryService {

    public List<AccountSubjectInfo> queryBalance(String AccountId) throws BusinessException;

    public AccountSubjectInfo queryAccount(String plat_no, String platcust, String subject, String sub_subject)throws BusinessException;

    public List<AccountSubjectInfo> queryAccountlist(String plat_no, String platcust)throws BusinessException;

    public List<AccountSubjectInfo> queryAccountlist(String plat_no, String platcust,String status)throws BusinessException;

    public AccountSubjectInfo queryPlatAccount(String prod_id, String plat_no, String subject, String sub_subject)throws BusinessException;

    //接入的网贷平台银行开户信息查询
    public PlatCardinfo queryPlatCardinfo(String mall_no,String plat_no,String card_type);

    /*
     *  pzy
     *  查询电子账户
     */
    public AccountSubjectInfo queryFINANCINGPlatAccount(String plat_no, String platcust,String account_type, String subject, String sub_subject)throws BusinessException;

    /*
     *  pzy
     *  查询电子账户现金和在途
     */
    public List<AccountSubjectInfo> queryFINANCINGPlatAccountlist(String plat_no, String platcust,String account_type, String sub_subject)throws BusinessException;

    /*
     *  pzy
     *  批量查询账户
     */
    public List<AccountSubjectInfo> queryPlatAccountList(String plat_no, Object platcust, String subject, Object sub_subject)throws BusinessException;

    /**
     * 查询平台实体账户
     * @param plat_no
     * @param card_type
     * @return
     * @throws BusinessException
     */
    public PlatCardinfo getPlatcardinfo(String plat_no,String card_type)throws BusinessException;

    /**
     * 查询用户电子账户
     * @param mall_no
     * @param mallcust
     * @return
     * @throws BusinessException
     */
    public EaccUserinfo getEaccUserinfo(String mall_no, String mallcust)throws BusinessException;

    public EaccUserinfo getEaccUserinfoByExist(String mall_no, String mallcust)throws BusinessException;

    public EaccUserinfo getEUserinfoByExist(String mall_no, String mallcust)throws BusinessException;
    public EaccUserinfo getEUserinfoByExist(String mall_no, String mallcust,String status)throws BusinessException;
    public EaccCardinfo getEaccCardInfo(String mall_no, String mallcust)throws BusinessException;

    /**
     * 根据集团号和用户号查询所以可用卡
     * @param mall_no
     * @param mallcust
     * @return
     * @throws BusinessException
     */
    public List<EaccCardinfo> getEaccCardInfoList(String mall_no, String mallcust)throws BusinessException;


    /**
     * 提现查询绑卡信息发送短信
     * @param mall_no
     * @param mallcust
     * @return
     * @throws BusinessException
     */
    public EaccCardinfo getEaccCardInfoByMallnoAndMallcustAndCardno(String mall_no, String mallcust,String card_no)throws BusinessException;

    /**
     * 资金变动明细查询
     * @param fundChangeRequest
     * @return
     * @throws BusinessException
     */
    public FundChangeResponse queryFundChange(FundChangeRequest fundChangeRequest)throws BusinessException;


    /**
     *  pzy
     *  资金余额查询
     */
    public AccountBalanceResponse queryAccounBalance(AccountBalanceRequest accountBalanceRequest)throws BusinessException;

    /**
     * 账户余额明细查询
     * @author Lid
     * @param accountBalanceDetailRequest
     * @return accountBalanceDetailResponse
     * @throws BusinessException
     */
    public AccountBalanceDetailResponse queryAccountBalanceDetail(AccountBalanceDetailRequest accountBalanceDetailRequest) throws BusinessException;

    /**
     * 根据订单查询资金流水
     * @param eaccountPordListRequest
     * @return
     * @throws BusinessException
     */
    public EaccountProdResponseData queryEaccAccoun(EaccountPordListRequest eaccountPordListRequest)throws BusinessException;

    public EaccountProdResponseToData queryEaccToAccoun(BaseRequest baseRequest)throws BusinessException;

    /**
     *借款人募集账户(标的账户)余额查询
     * @author RaoYL
     * @version 20180118
     */
    public ProductBalanceResponse queryProductBalance(ProductBalanceRequest productBalanceRequest)throws BusinessException;

    List<RwWithdraw> getUnDoneRwWithdrawTransByPlatcust(String mer_no, String platcust) throws BusinessException;

    List<EaccAccountamtlist> queryTransFlowList(EaccAccountamtlist allEaccAccountamtlist) throws BusinessException;

    List<EaccAccountamtlist> queryEaccAccountamtlistByTransSerial(String trans_serial) throws BusinessException;

    /**
     * 查询特定账户流水单据
     * @param platcust
     * @param startDate
     * @param endDate
     * @return
     * @throws BusinessException
     */
    public List<EaccAccountamtlist> accountFlowListQuery(String platcust, String startDate, String endDate)throws BusinessException;
    List<String> listOpenConfigByMallNo(String mallNo) throws BusinessException;
}
