package com.sunyard.sunfintech.search.service;


import com.alibaba.dubbo.config.annotation.Service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.sunyard.sunfintech.account.model.bo.*;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;

import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.*;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.*;


import com.sunyard.sunfintech.prod.provider.IProdSearchService;
import com.sunyard.sunfintech.pub.model.NotifyData;
import com.sunyard.sunfintech.pub.provider.INotifyService;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.search.model.*;
import com.sunyard.sunfintech.search.provider.ISearchAllQueryService;
import com.sunyard.sunfintech.thirdparty.model.*;
import com.sunyard.sunfintech.thirdparty.provider.IAdapterService;
import com.sunyard.sunfintech.user.model.bo.*;
import com.sunyard.sunfintech.user.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.math.BigDecimal;
import java.util.*;


/**
 * Created by my on 2018/7/20.
 */
@Service(interfaceClass = ISearchAllQueryService.class)
@CacheConfig(cacheNames="serchAllQueryService")
@org.springframework.stereotype.Service
public class SerchAllQueryService extends BaseServiceSimple implements ISearchAllQueryService {

    @Autowired
    private AccountSubjectInfoMapper accountSubjectInfoMapper;

    @Autowired
    private IProdSearchService prodSearchService;

    @Autowired
    private ProdProductinfoMapper prodProductInfoMapper;

    @Autowired
    private ProdShareListMapper prodShareListMapper;

    @Autowired
    private ProdCompensationListMapper prodCompensationListMapper;

    @Autowired
    private IAccountQueryService accountQueryService;

    @Autowired
    private TransTransreqMapper transReqMapper;

    @Autowired
    private RwRechargeMapper rwRechargeMapper;

    @Autowired
    private IUserTransService userTransService;

    @Autowired
    private RwRecodeMapper rwRecodeMapper;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private IPlatCacheService platCacheService;

    @Autowired
    private IAdapterService adapterService;

    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private EaccUserauthMapper eaccUserauthMapper;

    @Autowired
    private TransTransreqMapper transTransreqMapper;

    @Autowired
    private EaccAccountamtlistMapper eaccAccountamtlistMapper;

    @Autowired
    private INotifyService notifyService;

    @Autowired
    private IRechargeOptionService rechargeOptionService;

    @Autowired
    private IAccountSearchService accountSearchService;

    @Override
    public List<AccountSubjectInfo> queryAccountlist(String plat_no, String platcust) throws BusinessException {
        AccountSubjectInfoExample accountSubjectInfoExample = new AccountSubjectInfoExample();
        AccountSubjectInfoExample.Criteria criteria = accountSubjectInfoExample.createCriteria();
        criteria.andEnabledEqualTo(Constants.ENABLED);
        if (StringUtils.isNotBlank(plat_no))
            criteria.andPlat_noEqualTo(plat_no);
        if (StringUtils.isNotBlank(platcust))
            criteria.andPlatcustEqualTo(platcust);

        return accountSubjectInfoMapper.selectByExample(accountSubjectInfoExample);

    }


    /**
     * 资金余额查询
     * @author RaoYL
     * @param accountBalanceRequest
     * @return AccountBalanceResponse
     * @throws BusinessException
     */
    @Override
    public SearchAccountBalanceResponse queryAccounBalance(SearchAccountBalanceRequest accountBalanceRequest) throws BusinessException {
        logger.info("【资金余额查询】order_no:" + accountBalanceRequest.getOrder_no());
        SearchAccountBalanceResponse accountBalanceResponse = new SearchAccountBalanceResponse();
        BigDecimal bigDecimalall = BigDecimal.ZERO;
        //查询账户是否存在
        List<AccountSubjectInfo> accountSubjectInfos = queryAccountlist(accountBalanceRequest.getMer_no(), accountBalanceRequest.getAccount());
        if (accountSubjectInfos.size() == 0) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",账户不存在");
            throw new BusinessException(baseResponse);
        }
        for (AccountSubjectInfo a : accountSubjectInfos) {
            bigDecimalall = bigDecimalall.add(a.getN_balance());
        }
//		List<AccountSubjectInfo> accountSubjectInfo_f = queryFINANCINGPlatAccountlist(accountBalanceRequest.getMer_no(), accountBalanceRequest.getAccount(), AccountType.ElectronicAccount.getCode(), Ssubject.EACCOUNT.getCode());
//		for (AccountSubjectInfo a : accountSubjectInfo_f) {
//			bigDecimalall = bigDecimalall.add(a.getN_balance());
//		}
        SearchAccountBalanceResponseDetail accountBalanceResponseDetail = new SearchAccountBalanceResponseDetail();
        accountBalanceResponseDetail.setBalance(bigDecimalall);
        accountBalanceResponse.setData_detail(accountBalanceResponseDetail);
        accountBalanceResponse.setRecode(BusinessMsg.SUCCESS);
        accountBalanceResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        return accountBalanceResponse;
    }



    public SearchRepayDetailResponse queryRepayDetail(SearchRepayDetailRequest repayDetailRequest)throws BusinessException{
        logger.info("【还款明细查询】order_no:"+repayDetailRequest.getOrder_no());
        SearchRepayDetailResponse repayDetailResponse = new SearchRepayDetailResponse();
        String type = RepayDetailType.REPAYMENTLIST.getCode();

        if(repayDetailRequest.getType() != null) {
            type = repayDetailRequest.getType();
        }

        if (repayDetailRequest.getPagenum() == null) {
            repayDetailRequest.setPagenum(1);
        }

        if (repayDetailRequest.getPagesize() == null) {
            repayDetailRequest.setPagesize(10);
        }

        if (repayDetailRequest.getStart_date() == null) {
            repayDetailRequest.setStart_date(DateUtils.getYesterday());
        }

        if (repayDetailRequest.getEnd_date() == null) {
            repayDetailRequest.setEnd_date(DateUtils.getNow());
        }

        // 设置分页
        PageHelper.startPage(repayDetailRequest.getPagenum(), repayDetailRequest.getPagesize());
        List<SearchRepayDetailResponseDetail> repayDetailResponseDetails = new ArrayList<SearchRepayDetailResponseDetail>();
        if (RepayDetailType.REPAYMENTLIST.getCode().equals(type)) {
            //查询投资人收款记录

            List<ProdRepaymentlist> repaymentlist =prodSearchService.listRepayment(repayDetailRequest.getPlatcust(),repayDetailRequest.getProd_id(),repayDetailRequest.getStart_date(),repayDetailRequest.getEnd_date());
            for (ProdRepaymentlist repayment : repaymentlist) {
                SearchRepayDetailResponseDetail repayDetailResponseDetail = new SearchRepayDetailResponseDetail();
                repayDetailResponseDetail.setPlat_no(repayment.getPlat_no());
                if (repayment.getReal_repay_amt() == null) {
                    repayment.setReal_repay_amt(new BigDecimal(0));
                }
                repayDetailResponseDetail.setReal_repay_amt(repayment.getReal_repay_amt().toString());

                repayDetailResponseDetail.setReal_repay_date(DateUtils.formatDateToStr(repayment.getReal_repay_date()));
                if (repayment.getRepay_amt() == null) {
                    repayment.setRepay_amt(new BigDecimal(0));
                }
                repayDetailResponseDetail.setRepay_amt(repayment.getRepay_amt().toString());
                if (repayment.getRepay_date() == null) {
                    repayment.setRepay_date(new Date());
                }
                repayDetailResponseDetail.setRepay_date(DateUtils.formatDateToStr(repayment.getRepay_date(), DateUtils.DEF_FORMAT_STRING));
                if (repayment.getRepay_num() == null) {
                    repayment.setRepay_num(0);
                }
                repayDetailResponseDetail.setRepay_num(repayment.getRepay_num().toString());
                repayDetailResponseDetail.setStatus(repayment.getStatus());
//                ProdProductinfoExample productinfoExample = new ProdProductinfoExample();
//                ProdProductinfoExample.Criteria createCriteria = productinfoExample.createCriteria();
//                createCriteria.andPlat_noEqualTo(repayment.getPlat_no());
//                createCriteria.andProd_idEqualTo(repayment.getProd_id());
//                createCriteria.andEnabledEqualTo(Constants.ENABLED);
//                List<ProdProductinfo> productInfo = prodProductInfoMapper.selectByExample(productinfoExample);
                ProdProductinfo productInfo =prodSearchService.getProductById(repayment.getPlat_no(),repayment.getProd_id());
                repayDetailResponseDetail.setProd_name(productInfo.getProd_name());
                repayDetailResponseDetail.setProd_id(productInfo.getProd_id());
                repayDetailResponseDetails.add(repayDetailResponseDetail);
            }
        } else if (RepayDetailType.PRODBORROWERREALREPAY.getCode().equals(type)) {
            //查询借款人还款记录
//            ProdBorrowerRealrepayExample borrowerRealrepayExample = new ProdBorrowerRealrepayExample();
//            ProdBorrowerRealrepayExample.Criteria criteria = borrowerRealrepayExample.createCriteria();
//            criteria.andPlatcustEqualTo(repayDetailRequest.getPlatcust());
//            if (repayDetailRequest.getProd_id() != null) {
//                criteria.andProd_idEqualTo(repayDetailRequest.getProd_id());
//            }
//            criteria.andEnabledEqualTo(Constants.ENABLED);
//            if (repayDetailRequest.getStart_date() != null || repayDetailRequest.getEnd_date() != null) {
//                criteria.andTrans_dateBetween(DateUtils.formatDateToStr(repayDetailRequest.getStart_date(), DateUtils.DEF_FORMAT_STRING_DATE),
//                        DateUtils.formatDateToStr(repayDetailRequest.getEnd_date(), DateUtils.DEF_FORMAT_STRING_DATE));
//            }
            // List<ProdBorrowerRealrepay> borrowerRealRepaylist = borrowerRealRepayMapper.selectByExample(borrowerRealrepayExample);
            List<ProdBorrowerRealrepay> borrowerRealRepaylist =prodSearchService.listBorrowerRealRepay(repayDetailRequest.getPlatcust(),repayDetailRequest.getProd_id(),repayDetailRequest.getStart_date(),repayDetailRequest.getEnd_date());
            for (ProdBorrowerRealrepay borrowerRealRepay : borrowerRealRepaylist) {
                SearchRepayDetailResponseDetail repayDetailResponseDetail = new SearchRepayDetailResponseDetail();
                repayDetailResponseDetail.setPlat_no(borrowerRealRepay.getPlat_no());
                if (borrowerRealRepay.getReal_repay_amt() == null) {
                    borrowerRealRepay.setReal_repay_amt(new BigDecimal(0));
                }
                repayDetailResponseDetail.setReal_repay_amt(borrowerRealRepay.getReal_repay_amt().toString());
                repayDetailResponseDetail.setReal_repay_date(borrowerRealRepay.getTrans_date());
                if (borrowerRealRepay.getReal_repay_amt() == null) {
                    borrowerRealRepay.setReal_repay_amt(new BigDecimal(0));
                }
                repayDetailResponseDetail.setRepay_amt(borrowerRealRepay.getRepay_amt().toString());
                if (borrowerRealRepay.getRepay_date() == null) {
                    borrowerRealRepay.setRepay_date(DateUtils.formatDateToStr(new Date(), DateUtils.DEF_FORMAT_STRING));
                }
                repayDetailResponseDetail.setRepay_date(borrowerRealRepay.getRepay_date());
                if (borrowerRealRepay.getRepay_num() == null) {
                    borrowerRealRepay.setRepay_num(0);
                }
                repayDetailResponseDetail.setRepay_num(borrowerRealRepay.getRepay_num().toString());
                repayDetailResponseDetail.setStatus(borrowerRealRepay.getStatus());
//                ProdProductinfoExample productinfoExample = new ProdProductinfoExample();
//                ProdProductinfoExample.Criteria createCriteria = productinfoExample.createCriteria();
//                createCriteria.andPlat_noEqualTo(borrowerRealRepay.getPlat_no());
//                createCriteria.andProd_idEqualTo(borrowerRealRepay.getProd_id());
//                createCriteria.andEnabledEqualTo(Constants.ENABLED);
//                List<ProdProductinfo> productInfo = prodProductInfoMapper.selectByExample(productinfoExample);
                ProdProductinfo prodProductinfo=   prodSearchService.getProductById(borrowerRealRepay.getPlat_no(),borrowerRealRepay.getProd_id());
                repayDetailResponseDetail.setProd_name(prodProductinfo.getProd_name());
                repayDetailResponseDetail.setProd_id(prodProductinfo.getProd_id());
                repayDetailResponseDetails.add(repayDetailResponseDetail);
            }
        }
        repayDetailResponse.setRecode(BusinessMsg.SUCCESS);
        repayDetailResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        logger.info("还款明细查询返回参数："+repayDetailResponseDetails);
        repayDetailResponse.setData_detail(repayDetailResponseDetails);
        return repayDetailResponse;
    }


    @Override
    public ProdProductinfo queryProdInfo(String plat_no, String prod_id) throws BusinessException {
        ProdProductinfoExample example=new ProdProductinfoExample();
        ProdProductinfoExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(plat_no))
            criteria.andPlat_noEqualTo(plat_no);
        if(StringUtils.isNotBlank(prod_id))
            criteria.andProd_idEqualTo(prod_id);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<ProdProductinfo> prodProductInfoList = prodProductInfoMapper.selectByExample(example);
        if(prodProductInfoList.size() > 1){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "----有重复的标prod_id="+prod_id);
            throw new BusinessException(baseResponse);
        }else if(prodProductInfoList.size() < 1){
            return null;
        }
        return prodProductInfoList.get(0);
    }

    /**
     * 标的投资明细查询
     * @author RaoYL
     * @param investmentDetailRequest
     * @return productInvestmentDetailResponse
     */
    public SearchProductInvestmentDetailResponse queryInvestmentDeail(SearchProductInvestmentDetailRequest investmentDetailRequest)throws BusinessException{
        logger.info("【标的投资明细查询】order_no:"+investmentDetailRequest.getOrder_no());
        ProdProductinfo prodProductinfo = queryProdInfo(investmentDetailRequest.getMer_no(),investmentDetailRequest.getProd_id());
        if(prodProductinfo == null){
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_PRODUCT_ID);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID));
            throw new BusinessException(baseResponse);
        }
        SearchProductInvestmentDetailResponse detailResponse = new  SearchProductInvestmentDetailResponse();
        //校验分页参数
        if (investmentDetailRequest.getPagenum() == null) {
            investmentDetailRequest.setPagenum("1");
        }

        if (investmentDetailRequest.getPagesize() == null) {
            investmentDetailRequest.setPagesize("10");
        }
        List<Map<String,Object>> detailDatas = prodShareListMapper.getInvestmentDetail(investmentDetailRequest.getMer_no(),investmentDetailRequest.getProd_id());
        detailResponse.setRecode(BusinessMsg.SUCCESS);
        detailResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        detailResponse.setDetail_map(detailDatas);
        return detailResponse;
    }

    /**
     * @author RaoYL
     * 标的信息查询
     * @param productInfoRequest
     * @return ProductInfoResponse
     * @throws BusinessException
     */
    @Override
    public SearchProductInfoResponse queryProductInfo(SearchProductInfoRequest productInfoRequest) throws BusinessException{
        logger.info("【标的信息查询】order_no:"+productInfoRequest.getOrder_no());
        ProdProductinfo prodProductinfo = queryProdInfo(productInfoRequest.getMer_no(),productInfoRequest.getProd_id());
        if(prodProductinfo == null){
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_PRODUCT_ID);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID)+"，标的不存在");
            throw new BusinessException(baseResponse);
        }
        SearchProductInfoResponse productInfoResponse = new SearchProductInfoResponse();
        List<Map<String,Object>> prodCompensationLists=prodCompensationListMapper.getProdCompensationListByProdId(productInfoRequest.getMer_no(), productInfoRequest.getProd_id());
        SearchProductInfoResponseDetail productInfoResponseDetail = new SearchProductInfoResponseDetail();
        productInfoResponseDetail.setIst_year(prodProductinfo.getIst_year());
        productInfoResponseDetail.setTotal_limit(prodProductinfo.getTotal_limit());
        productInfoResponseDetail.setRemain_limit(prodProductinfo.getRemain_limit());
        productInfoResponseDetail.setSaled_limit(prodProductinfo.getSaled_limit());
        productInfoResponseDetail.setChargeOff_auto(prodProductinfo.getCharge_off_auto());
        productInfoResponseDetail.setPlat_no(prodProductinfo.getPlat_no());
        productInfoResponseDetail.setProd_id(prodProductinfo.getProd_id());
        productInfoResponseDetail.setProd_account(prodProductinfo.getProd_account());
        productInfoResponseDetail.setProd_state(prodProductinfo.getProd_state());
        productInfoResponseDetail.setProd_name(prodProductinfo.getProd_name());
        productInfoResponseDetail.setCompensation(prodCompensationLists);
        productInfoResponse.setData_detail(productInfoResponseDetail);
        productInfoResponse.setRecode(BusinessMsg.SUCCESS);
        productInfoResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        return productInfoResponse;
    }

    /**
     * 账户余额明细查询
     * @author RaoYL
     * @param accountBalanceDetailRequest
     * @return AccountBalanceDetailResponse
     * @throws BusinessException
     */
    @Override
    public SearchAccountBalanceDetailResponse queryAccountBalanceDetail(SearchAccountBalanceDetailRequest accountBalanceDetailRequest) throws BusinessException {
        logger.info("【账户余额明细查询】order_no:"+accountBalanceDetailRequest.getOrder_no());
        if(StringUtils.isBlank(accountBalanceDetailRequest.getAccount_type())){
            accountBalanceDetailRequest.setAccount_type(AccountType.Platcust.getCode());
        }

        SearchAccountBalanceDetailResponse accountBalanceDetailResponse = new SearchAccountBalanceDetailResponse();

        List<SearchAccountBalanceDetailResponseDetail> accountBalanceDetailResponseDetails = new ArrayList<SearchAccountBalanceDetailResponseDetail>();

        List<AccountSubjectInfo> accountSubjectInfos = new ArrayList<AccountSubjectInfo>();

        BaseResponse baseResponse = new BaseResponse();

        if(accountBalanceDetailRequest.getAccount_type().equals(AccountType.Platcust.getCode())){
            //查询用户账户是否存在
            accountSubjectInfos = queryAccountlist(accountBalanceDetailRequest.getMer_no(),accountBalanceDetailRequest.getAccount());
            if(accountSubjectInfos.size() == 0){
                baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
                throw new BusinessException(baseResponse);
            }
            for(AccountSubjectInfo accountSubjectInfo : accountSubjectInfos){
                if(accountSubjectInfo.getSub_subject().equals(Ssubject.INVEST.getCode())||accountSubjectInfo.getSub_subject().equals(Ssubject.FINANCING.getCode())){
                    SearchAccountBalanceDetailResponseDetail accountBalanceDetailResponseDetail = new SearchAccountBalanceDetailResponseDetail();
                    accountBalanceDetailResponseDetail.setPlatcust(accountSubjectInfo.getPlatcust());
                    accountBalanceDetailResponseDetail.setPlat_no(accountSubjectInfo.getPlat_no());
                    accountBalanceDetailResponseDetail.setSubject(accountSubjectInfo.getSubject());
                    accountBalanceDetailResponseDetail.setSub_subject(accountSubjectInfo.getSub_subject());
                    accountBalanceDetailResponseDetail.setT_balance(accountSubjectInfo.getT_balance());
                    accountBalanceDetailResponseDetail.setN_balance(accountSubjectInfo.getN_balance());
                    accountBalanceDetailResponseDetail.setF_balance(accountSubjectInfo.getF_balance());
                    accountBalanceDetailResponseDetails.add(accountBalanceDetailResponseDetail);
                }
            }
        }else if(accountBalanceDetailRequest.getAccount_type().equals(AccountType.Plat.getCode())){
            //查询平台账户是否存在
            accountSubjectInfos = queryAccountlist(accountBalanceDetailRequest.getMer_no(),accountBalanceDetailRequest.getAccount());
            if(accountSubjectInfos.size() == 0){
                baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
                throw new BusinessException(baseResponse);
            }
            for(AccountSubjectInfo accountSubjectInfo : accountSubjectInfos){
                if(accountSubjectInfo.getSub_subject().equals(Ssubject.PLAT.getCode())
                        ||accountSubjectInfo.getSub_subject().equals(Ssubject.FEE.getCode())
                        ||accountSubjectInfo.getSub_subject().equals(Ssubject.EXPERIENCE.getCode())
                        ||accountSubjectInfo.getSub_subject().equals(Ssubject.VOUCHER.getCode())
                        ||accountSubjectInfo.getSub_subject().equals(Ssubject.PLUSRATE.getCode())
                        ||accountSubjectInfo.getSub_subject().equals(Ssubject.DEPOSIT.getCode())
                        ||accountSubjectInfo.getSub_subject().equals(Ssubject.PAYMENT.getCode())
                        ||accountSubjectInfo.getSub_subject().equals(Ssubject.INLINEPAYMENT.getCode())
                        ||accountSubjectInfo.getSub_subject().equals(Ssubject.INTEREST.getCode())
                        || Payment.isPayment(accountSubjectInfo.getSub_subject()) ){
                    SearchAccountBalanceDetailResponseDetail accountBalanceDetailResponseDetail = new SearchAccountBalanceDetailResponseDetail();
                    accountBalanceDetailResponseDetail.setPlatcust(accountSubjectInfo.getPlatcust());
                    accountBalanceDetailResponseDetail.setPlat_no(accountSubjectInfo.getPlat_no());
                    accountBalanceDetailResponseDetail.setSubject(accountSubjectInfo.getSubject());
                    accountBalanceDetailResponseDetail.setSub_subject(accountSubjectInfo.getSub_subject());
                    accountBalanceDetailResponseDetail.setT_balance(accountSubjectInfo.getT_balance());
                    accountBalanceDetailResponseDetail.setN_balance(accountSubjectInfo.getN_balance());
                    accountBalanceDetailResponseDetail.setF_balance(accountSubjectInfo.getF_balance());
                    accountBalanceDetailResponseDetails.add(accountBalanceDetailResponseDetail);
                }
            }
        }else if(accountBalanceDetailRequest.getAccount_type().equals(AccountType.Product.getCode())){
            //查询标的账户(借款人募集账户)是否存在
            accountSubjectInfos = queryAccountlist(accountBalanceDetailRequest.getMer_no(),accountBalanceDetailRequest.getAccount());
            if(accountSubjectInfos.size() == 0){
                baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
                throw new BusinessException(baseResponse);
            }
            for(AccountSubjectInfo accountSubjectInfo : accountSubjectInfos){
                if(accountSubjectInfo.getSub_subject().equals(Ssubject.PROD.getCode())){
                    SearchAccountBalanceDetailResponseDetail accountBalanceDetailResponseDetail = new SearchAccountBalanceDetailResponseDetail();
                    accountBalanceDetailResponseDetail.setPlatcust(accountSubjectInfo.getPlatcust());
                    accountBalanceDetailResponseDetail.setPlat_no(accountSubjectInfo.getPlat_no());
                    accountBalanceDetailResponseDetail.setSubject(accountSubjectInfo.getSubject());
                    accountBalanceDetailResponseDetail.setSub_subject(accountSubjectInfo.getSub_subject());
                    accountBalanceDetailResponseDetail.setT_balance(accountSubjectInfo.getT_balance());
                    accountBalanceDetailResponseDetail.setN_balance(accountSubjectInfo.getN_balance());
                    accountBalanceDetailResponseDetail.setF_balance(accountSubjectInfo.getF_balance());
                    accountBalanceDetailResponseDetails.add(accountBalanceDetailResponseDetail);
                }
            }
        }else{
            baseResponse.setRecode(BusinessMsg.UNKNOWN_ACCOUNT_TYPE);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNKNOWN_ACCOUNT_TYPE));
            throw new BusinessException(baseResponse);
        }
        accountBalanceDetailResponse.setData_detail(accountBalanceDetailResponseDetails);
        accountBalanceDetailResponse.setOrder_no(accountBalanceDetailRequest.getOrder_no());
        accountBalanceDetailResponse.setRecode(BusinessMsg.SUCCESS);
        accountBalanceDetailResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        return accountBalanceDetailResponse;
    }

    /**
     *借款人募集账户(标的账户)余额查询
     * @author RaoYL
     * @param productBalanceRequest 借款人募集账户(标的账户)余额查询  验证请求参数
     * @return ProductBalanceResponse
     * @version 20180118
     */
    @Override
    public SearchProductBalanceResponse queryProductBalance(SearchProductBalanceRequest productBalanceRequest)throws BusinessException{
        logger.info("【借款人募集账户余额查询】order_no:"+productBalanceRequest.getOrder_no());
        SearchProductBalanceResponse productBalanceResponse = new SearchProductBalanceResponse();
        SearchProductBalanceResponseDetail productBalanceResponseDetail = new SearchProductBalanceResponseDetail();
        BaseResponse baseResponse = new BaseResponse();
        //查询标的是否存在
        ProdProductinfo prodProductinfo = queryProdInfo(productBalanceRequest.getMer_no(),productBalanceRequest.getProd_id());
        if(prodProductinfo == null){
            baseResponse.setRecode(BusinessMsg.INVALID_PRODUCT_ID);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID));
            throw new BusinessException(baseResponse);
        }

        //查询标的账户是否存在
        List<AccountSubjectInfo> accountSubjectInfos = accountQueryService.queryPlatAccountList(productBalanceRequest.getMer_no(),prodProductinfo.getProd_account(),productBalanceRequest.getType(), Ssubject.PROD.getCode());
        if(accountSubjectInfos.size() == 0){
            baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
            throw new BusinessException(baseResponse);
        }
        //获得标的账户的总额
        BigDecimal bigDecimal = BigDecimal.ZERO;
        for(AccountSubjectInfo accountSubjectInfo : accountSubjectInfos){
            bigDecimal = bigDecimal.add(accountSubjectInfo.getN_balance());
        }
        productBalanceResponseDetail.setBalance(bigDecimal);
        productBalanceResponse.setData_detail(productBalanceResponseDetail);
        productBalanceResponse.setRecode(BusinessMsg.SUCCESS);
        productBalanceResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));

        return productBalanceResponse;
    }

    /**
     * @author RaoYL
     * @param orderStatusRequest
     * @return TransTransreq
     * @throws BusinessException
     */
    @Override
    public TransTransreq queryOrderStatus(SearchOrderStatusRequest orderStatusRequest) throws BusinessException {
        logger.info(String.format("【订单状态查询】查询订单号:%s",orderStatusRequest.getQuery_order_no()));

        //查询业务流水表
        TransTransreqExample example = new TransTransreqExample();
        TransTransreqExample.Criteria criteria = example.createCriteria();
        criteria.andPlat_noEqualTo(orderStatusRequest.getMer_no());
        criteria.andOrder_noEqualTo(orderStatusRequest.getQuery_order_no());
        List<String> serviceNameList=new ArrayList<>();
        serviceNameList.add("user");
        serviceNameList.add("prod");
        criteria.andService_nameIn(serviceNameList);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<TransTransreq> transTransReqs = transReqMapper.selectByExample(example);
        if(transTransReqs.size() > 1){
            logger.info(String.format("【订单状态查询】数据库有重复的订单|查询订单号:%s",orderStatusRequest.getQuery_order_no()));
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "数据库有重复的订单");
            throw new BusinessException(baseResponse);
        }
        if(transTransReqs.size()==0){
            logger.info(String.format("【订单状态查询】在数据库中查不到订单数据，查redis|查询订单号:%s",orderStatusRequest.getQuery_order_no()));
            String key=Constants.TRANSREQ_ORDER_KEY+orderStatusRequest.getQuery_order_no();
            Object objectKey= CacheUtil.getCache().get(key);
            if(objectKey!=null){
                return JSON.parseObject(String.valueOf(objectKey),TransTransreq.class);
            }else{
                logger.info(String.format("【订单状态查询】订单不存在|查询订单号:%s",orderStatusRequest.getQuery_order_no()));
            }
            return null;
        }
        return transTransReqs.get(0);
    }

    /**
     * 充值订单状态查询
     * @author RaoYL
     * @param chargeStatus
     * @return RwRecharge
     * @throws BusinessException
     */
    public RwRecharge queryChargeStatus(ChargeStatus chargeStatus) throws BusinessException {

        logger.info("【充值订单状态查询】请求第三方支付参数：" + JSON.toJSONString(chargeStatus, GlobalConfig.serializerFeature));
        //=================添加充值处理标志==========
        RwRecharge rwRecharge;
        //查询
        RwRechargeExample example = new RwRechargeExample();
        RwRechargeExample.Criteria criteria = example.createCriteria();
        criteria.andPlat_noEqualTo(chargeStatus.getMer_no());
        criteria.andOrder_noEqualTo(chargeStatus.getOriginal_serial_no());
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<RwRecharge> rwRecharges = rwRechargeMapper.selectByExample(example);
        if (rwRecharges.size() > 1) {
            logger.info("【充值订单状态查询】==========数据库有重复充值订单："+chargeStatus.getOriginal_serial_no());
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "数据库有重复充值订单");
            throw new BusinessException(baseResponse);
        }

        if (null == rwRecharges || rwRecharges.size() == 0) {
            return null;
        }
        rwRecharge = rwRecharges.get(0);

        if(rwRecharge.getTrans_amt().compareTo(chargeStatus.getOccur_balance())!=0){
            logger.info("【充值订单状态查询】==========金额错误,原充值订单号："+chargeStatus.getOriginal_serial_no());
            throw new BusinessException(BusinessMsg.MONEY_ERROR,BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR)+":查询金额不符");
        }

        try {
            //如果充值订单状态不是终态。则调用云融惠付查询订单状态。
            if (!OrderStatus.SUCCESS.getCode().equals(rwRecharge.getStatus()) && !OrderStatus.FAIL.getCode().equals(rwRecharge.getStatus())) {
                logger.info("【充值订单状态查询】==========开始调用queryEpayStatus查询充值订单，请求参数：" + rwRecharge.getTrans_serial() + "充值订单金额：" + chargeStatus.getOccur_balance());
                rwRecharge=userTransService.queryEpayStatus(rwRecharge);
            }
            logger.info("【充值订单状态查询】==========支付返回最终订单状态：" + rwRecharge.getStatus());
            return rwRecharge;
        } catch (Exception e) {
            logger.error("充值订单状态查询异常：" + e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg("充值订单状态查询异常");
            }
            throw new BusinessException(baseResponse);
        }
    }

    /**
     * 退票补单查询
     *
     * @return RwRecode
     */
    @Override
    public RwRecode QueryRrfund(SearchRefundRequest refundRequest) throws BusinessException {
        logger.info("search:---------------退票补单查询");
        //查询
        RwRecodeExample example = new RwRecodeExample();
        RwRecodeExample.Criteria criteria = example.createCriteria();
        criteria.andPlat_noEqualTo(refundRequest.getMer_no());
        criteria.andOrder_noEqualTo(refundRequest.getQuery_order_no());
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<RwRecode> rwRecodes = rwRecodeMapper.selectByExample(example);
        if (rwRecodes.size() > 1) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "数据库有重复退票补单数据");
            throw new BusinessException(baseResponse);
        }

        if (null == rwRecodes || rwRecodes.size() == 0) {
            return null;
        }
        return rwRecodes.get(0);
    }

    @Override
    public SearchCompanyAccountBalanceResponseDetail queryCompanyAccountBalance(SearchCompanyAccountBalanceRequest companyAccountBalanceRequest) throws BusinessException {
        logger.info("【平台对公账户余额查询】order_no:"+companyAccountBalanceRequest.getOrder_no());
        BaseResponse baseResponse = new BaseResponse();
        Map<String,Object> realParams = new HashMap<String,Object>();
        SearchCompanyAccountBalanceResponseDetail dataDetail = new SearchCompanyAccountBalanceResponseDetail();
        try{
            //从redis缓存中查询平台对公卡信息
            String platCardInfo = platCacheService.queryCardInfoToCache(companyAccountBalanceRequest.getMer_no(),companyAccountBalanceRequest.getCard_type());
            JSONObject platCardInfoJson = JSONObject.parseObject(platCardInfo);
            logger.info("从redis中查询出来的平台对公卡信息："+platCardInfoJson.toString());
            //请求支付查询平台对公卡信息
            String host = sysParameterService.querySysParameter(companyAccountBalanceRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY);
            String url = sysParameterService.querySysParameter(companyAccountBalanceRequest.getMall_no(), URLConfigUtil.EPAY_QUERY_BALANCE);
            if("BOB".equals(deployEnvironment)) {
                realParams.put("partner_id", "90000009");//商户ID
                realParams.put("partner_serial_no", SeqUtil.getSeqNum());//流水号
                realParams.put("client_property", "1");
                realParams.put("bank_acct_no", platCardInfoJson.get("card_no").toString());
            }
            if("CCB".equals(deployEnvironment)){
                JSONArray trandata=new JSONArray();
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("card_no",platCardInfoJson.get("card_no").toString());
                trandata.add(map);
                logger.info("平台实体账户卡号："+platCardInfoJson.get("card_no").toString()+"map:"+map+"trandata:"+trandata);
                realParams.put("partner_id","cg001");//固定值
                realParams.put("channelId","01050000");//固定值
//                realParams.put("third_no",companyAccountBalanceRequest.getMer_no());//平台号
                realParams.put("tran_type","queryBalance");
                realParams.put("third_batch_no",SeqUtil.getSeqNum());//流水号
                realParams.put("trandata",trandata);//trandata里面是一个JSON格式的list字符串  card_no卡号
            }
            realParams.put("host",host);
            realParams.put("url",url);
            logger.info("【平台对公账户查询】order_no:"+companyAccountBalanceRequest.getOrder_no()+"开始请求支付，参数："+JSON.toJSON(realParams));
            Map<String,Object> resultMap = adapterService.queryAccountOfCompany(realParams);
            if("BOB".equals(deployEnvironment)) {
                if(null == resultMap || null == resultMap.get("order_status") || ".".equals(resultMap.get("order_status"))){
                    logger.info("【平台对公账户查询】order_no:"+companyAccountBalanceRequest.getOrder_no()+"第三方支付返回结果为空");
                    baseResponse.setRecode(BusinessMsg.UNKNOW_REMOTE_STATUS);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_REMOTE_STATUS));
                    throw new BusinessException(baseResponse);
                }
            }
            if("CCB".equals(deployEnvironment)){
                if(null == resultMap ){
                    logger.info("【平台对公账户查询】order_no:"+companyAccountBalanceRequest.getOrder_no()+"第三方支付返回结果为空");
                    baseResponse.setRecode(BusinessMsg.UNKNOW_REMOTE_STATUS);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_REMOTE_STATUS));
                    throw new BusinessException(baseResponse);
                }
            }
            if(!OrderStatus.SUCCESS.getCode().equals(resultMap.get("order_status"))){
                logger.info("【平台对公账户查询】order_no:"+companyAccountBalanceRequest.getOrder_no()+"第三方支付返回错误信息");
                baseResponse.setRecode(resultMap.get("recode").toString());
                baseResponse.setRemsg(resultMap.get("remsg").toString());
                throw new BusinessException(baseResponse);
            }
            logger.info("平台对公账户查询支付返回结果："+resultMap);
            if("CCB".equals(deployEnvironment)){
                dataDetail.setOpen_bank(resultMap.get("open_bank").toString());
                dataDetail.setAcct_name_ch(resultMap.get("acct_name_ch").toString());
                dataDetail.setReal_time_balance(resultMap.get("real_time_balance").toString());
                dataDetail.setToday_amt(resultMap.get("today_amt").toString());
                dataDetail.setAcct_status(resultMap.get("acct_status").toString());
            }
            if("BOB".equals(deployEnvironment)){
                dataDetail.setOpen_bank(resultMap.get("open_bank").toString());
                dataDetail.setAcct_name_ch(resultMap.get("client_name").toString());
                dataDetail.setReal_time_balance(resultMap.get("real_time_balance").toString());
                dataDetail.setToday_amt(resultMap.get("today_balance").toString());
                dataDetail.setYesterday_amt(resultMap.get("yesterday_balance").toString());
            }
            logger.info("【平台对公账户查询】order_no:"+companyAccountBalanceRequest.getOrder_no()+"查询结束");
        }catch (Exception ex){
            logger.info("【平台对公账户查询】order_no:"+companyAccountBalanceRequest.getOrder_no()+"查询开始异常");
            logger.error(ex);
            if(ex instanceof BusinessException){
                baseResponse = ((BusinessException) ex).getBaseResponse();
                throw new BusinessException(baseResponse);
            }else {
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION,BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
        }
        return dataDetail;
    }
    /**
     *  @author RaoYL
     *  @version 20180125
     *  账户信息查询
     */
    @Override
    public AccountInfoResponse queryAccountInfo(EaccountInfoRequest eaccountInfoRequest) throws BusinessException {

        logger.info(String.format("【用户信息查询】开始查询|order_no:%s|params:%s",
                eaccountInfoRequest.getOrder_no(),JSON.toJSONString(eaccountInfoRequest)));

        AccountInfoResponse accountInfoResponse = new AccountInfoResponse();

        if(StringUtils.isBlank(eaccountInfoRequest.getPid()) && StringUtils.isBlank(eaccountInfoRequest.getPlatcust())
                && StringUtils.isBlank(eaccountInfoRequest.getOrg_name())){
            logger.info(String.format("【用户信息查询】参数错误|order_no:%s|platcust:%s|id_code:%s",
                    eaccountInfoRequest.getOrder_no(),eaccountInfoRequest.getPlatcust(),eaccountInfoRequest.getPid()));
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "|请填写用户编号或者证件号");
            throw new BusinessException(baseResponse);
        }

        //查询用户账户是否存在(包含已销户客户)
        EaccUserinfo eaccUserInfo = userAccountService.queryEaccUserInfo(
                eaccountInfoRequest.getMall_no(),eaccountInfoRequest.getPlatcust(),eaccountInfoRequest.getPid(),eaccountInfoRequest.getOrg_name(),
                AcctStatus.ACTIVE.getCode(),AcctStatus.FORZEN.getCode(),AcctStatus.UNACTIVE.getCode());
        if(eaccUserInfo == null){
            logger.info(String.format("【用户信息查询】用户不存在|order_no:%s|platcust:%s|id_code:%s",
                    eaccountInfoRequest.getOrder_no(),eaccountInfoRequest.getPlatcust(),eaccountInfoRequest.getPid()));
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
            throw new BusinessException(baseResponse);
        }

        //查询用户绑卡信息(不包含已解绑卡)
        List<EaccCardinfo> eaccCardinfoList = userAccountService.queryCardInfoForMultiCards(eaccUserInfo.getMall_no(),eaccUserInfo.getMallcust(),
                AcctStatus.ACTIVE.getCode(),AcctStatus.FORZEN.getCode());
        AccountInfoResponseDetail accountInfoResponseDetail = new AccountInfoResponseDetail();
        accountInfoResponseDetail.setPlatcust(eaccUserInfo.getMallcust());
        accountInfoResponseDetail.setName(eaccUserInfo.getName());
        accountInfoResponseDetail.setPid(eaccUserInfo.getId_code());
        accountInfoResponseDetail.setEflg(eaccUserInfo.getEaccount_flg()!=null?eaccUserInfo.getEaccount_flg():"0");
        accountInfoResponseDetail.setMobile(eaccUserInfo.getMobile());
        accountInfoResponseDetail.setCus_type(eaccUserInfo.getCus_type());
        accountInfoResponseDetail.setBank_license(eaccUserInfo.getBank_license());
        accountInfoResponseDetail.setOrg_name(eaccUserInfo.getOrg_name());
        accountInfoResponseDetail.setBusiness_license(eaccUserInfo.getBusiness_license());
        //TODO accountInfoResponseDetail.setPwd_flg();
        switch (eaccUserInfo.getEnabled()){
            case Constants.DISABLED:{
                accountInfoResponseDetail.setCancel_flg(Constants.ENABLED);
                accountInfoResponseDetail.setFreeze_flg(Constants.ENABLED);
                break;
            }
            case Constants.ENABLED:{
                accountInfoResponseDetail.setCancel_flg(Constants.DISABLED);
                accountInfoResponseDetail.setFreeze_flg(Constants.DISABLED);
                break;
            }
            case "2":{
                accountInfoResponseDetail.setCancel_flg(Constants.DISABLED);
                accountInfoResponseDetail.setFreeze_flg(Constants.ENABLED);
                break;
            }
        }
        List<EaccUserauth> eaccUserauthList=queryEaccUserAuth(eaccUserInfo.getMallcust(),
                eaccountInfoRequest.getMer_no(),eaccountInfoRequest.getMall_no(),AuthStatus.ALREADY_AUTH.getCode());
        if(eaccUserauthList.size()>0){
            List<AccountInfoResponseDetailAuthInfo> authInfoList=new ArrayList<>();
            for(EaccUserauth eaccUserauth:eaccUserauthList){
                AccountInfoResponseDetailAuthInfo authInfo=new AccountInfoResponseDetailAuthInfo();
                authInfo.setAuthed_amount(eaccUserauth.getAuthed_amount());
                authInfo.setAuthed_limittime(eaccUserauth.getAuthed_limittime());
                authInfo.setAuthed_type(eaccUserauth.getAuthed_type());
                authInfoList.add(authInfo);
            }
            accountInfoResponseDetail.setAuth_info(JSON.toJSONString(authInfoList));
        }
        List<CardInfoDetail> cardInfoDetailList = new ArrayList<>();
        if(eaccCardinfoList != null && eaccCardinfoList.size()!=0){
            for (EaccCardinfo cardinfo:eaccCardinfoList) {
                CardInfoDetail cardInfoDetail = new CardInfoDetail();
                cardInfoDetail.setMobile(cardinfo.getMobile());
                cardInfoDetail.setBoundbankid(cardinfo.getCard_no());
                cardInfoDetail.setType(CardType.CREDIT.getCode().equals(cardinfo.getCard_type())?"1":"0");

                if(StringUtils.isNotBlank(cardinfo.getPay_channel())){
                    PlatPaycode platPaycode=userAccountService.queryPayCodeByChannel(cardinfo.getMall_no(),cardinfo.getPay_channel());
                    if(platPaycode!=null){
                        cardInfoDetail.setPay_code(platPaycode.getPay_code());
                    }
                }
                cardInfoDetailList.add(cardInfoDetail);
            }
        }
        accountInfoResponseDetail.setPwd_flg(eaccUserInfo.getPwd_flg()==null?Constants.NO:eaccUserInfo.getPwd_flg());
        accountInfoResponseDetail.setCardInfoDetail(cardInfoDetailList);
        accountInfoResponse.setRecode(BusinessMsg.SUCCESS);
        accountInfoResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        accountInfoResponse.setData_detail(accountInfoResponseDetail);

        return accountInfoResponse;
    }
    @Override
    public List<EaccUserauth> queryEaccUserAuth(String platcust,String plat_no,String mall_no,String... status) throws BusinessException {
        EaccUserauthExample example=new EaccUserauthExample();
        EaccUserauthExample.Criteria criteria=example.createCriteria();
        criteria.andPlat_noEqualTo(plat_no);
        criteria.andMall_noEqualTo(mall_no);
        criteria.andPlatcustEqualTo(platcust);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        if(status.length==1){
            criteria.andAuthed_statusEqualTo(status[0]);
        }else if(status.length>1){
            List<String> statusList=new ArrayList<>();
            statusList.addAll(Arrays.asList(status));
            criteria.andAuthed_statusIn(statusList);
        }
        return eaccUserauthMapper.selectByExample(example);
    }

    /**
     * 资金变动明细查询
     * @author RaoYL
     * @param fundChangeRequest
     * @return FundChangeResponse
     * @throws BusinessException
     */
    @Override
    public FundChangeResponse queryFundChange(FundChangeRequest fundChangeRequest) throws BusinessException {

        logger.info("【资金变动明细查询】order_no:" + fundChangeRequest.getOrder_no());

        FundChangeResponse fundChangeResponse = new FundChangeResponse();

        //查询账户是否存在的
        AccountSubjectInfo accountSubjectInfo = queryAccount(fundChangeRequest.getMer_no(), fundChangeRequest.getPlatcust(), Tsubject.CASH.getCode(), Ssubject.INVEST.getCode());
        if (accountSubjectInfo == null) {
            logger.info("【资金变动明细查询】账户不存在");

            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
            throw new BusinessException(baseResponse);
        }

        if (fundChangeRequest.getPagenum() == null ) {
            fundChangeRequest.setPagenum(1);
        }

        if(fundChangeRequest.getPagesize() == null){
            fundChangeRequest.setPagesize(10);
        }
        PageHelper.startPage(fundChangeRequest.getPagenum(), fundChangeRequest.getPagesize());

        EaccAccountamtlistExample example = new EaccAccountamtlistExample();

        EaccAccountamtlistExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(fundChangeRequest.getPlatcust()))
            criteria.andPlatcustEqualTo(fundChangeRequest.getPlatcust());

        if (StringUtils.isNotBlank(fundChangeRequest.getMer_no()))
            criteria.andPlat_noEqualTo(fundChangeRequest.getMer_no());

        if (StringUtils.isNotBlank(fundChangeRequest.getTrans_name()))
            criteria.andTrans_nameLike(fundChangeRequest.getTrans_name());

        if (fundChangeRequest.getStart_date() == null ) {
            fundChangeRequest.setStart_date(DateUtils.getYesterday());
        }

        if(fundChangeRequest.getEnd_date() == null){
            fundChangeRequest.setEnd_date(DateUtils.getNow());
        }

        criteria.andCreate_timeBetween(fundChangeRequest.getStart_date(), new Date(fundChangeRequest.getEnd_date().getTime() + 24 * 60 * 60 * 1000));

        List<FundChangeResponseDetail> fundChangeResponseDetailList = new ArrayList<FundChangeResponseDetail>();
        List<EaccAccountamtlist> eaccAccountamtlistArrayList = eaccAccountamtlistMapper.selectByExample(example);
        for (EaccAccountamtlist eaccAccountamtlist2 : eaccAccountamtlistArrayList) {
            FundChangeResponseDetail fundChangeResponseDetail = new FundChangeResponseDetail();

            fundChangeResponseDetail.setPlat_no(eaccAccountamtlist2.getPlat_no());
            fundChangeResponseDetail.setPlatcust(eaccAccountamtlist2.getPlatcust());
            fundChangeResponseDetail.setTrans_time(eaccAccountamtlist2.getTrans_time());
            fundChangeResponseDetail.setTrans_date(eaccAccountamtlist2.getTrans_date());
            fundChangeResponseDetail.setAmt(eaccAccountamtlist2.getAmt().toString());
            fundChangeResponseDetail.setAmt_type(eaccAccountamtlist2.getAmt_type());
            fundChangeResponseDetail.setTrans_name(eaccAccountamtlist2.getTrans_name());
            fundChangeResponseDetail.setOppo_platcust(eaccAccountamtlist2.getOppo_platcust());
            fundChangeResponseDetail.setRemark(eaccAccountamtlist2.getRemark());
            TransTransreqExample transTransreqExample = new TransTransreqExample();
            TransTransreqExample.Criteria transCriteria = transTransreqExample.createCriteria();
            transCriteria.andTrans_serialEqualTo(eaccAccountamtlist2.getTrans_serial());
            List<TransTransreq> transTransreqList = transTransreqMapper.selectByExample(transTransreqExample);
            if (transTransreqList == null || transTransreqList.size() <= 0) {
                fundChangeResponseDetail.setOrder_no(null);
            } else {
                fundChangeResponseDetail.setOrder_no(transTransreqList.get(0).getOrder_no());
            }
            fundChangeResponseDetailList.add(fundChangeResponseDetail);
        }
        fundChangeResponse.setData_detail(fundChangeResponseDetailList);
        fundChangeResponse.setRecode(BusinessMsg.SUCCESS);
        fundChangeResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        return fundChangeResponse;
    }

    @Override
    public AccountSubjectInfo queryAccount(String plat_no, String platcust, String subject, String sub_subject) throws BusinessException {
        AccountSubjectInfoExample accountSubjectInfoExample = new AccountSubjectInfoExample();
        AccountSubjectInfoExample.Criteria criteria = accountSubjectInfoExample.createCriteria();
        criteria.andEnabledEqualTo(Constants.ENABLED);
        if (StringUtils.isNotBlank(plat_no))
            criteria.andPlat_noEqualTo(plat_no);
        if (StringUtils.isNotBlank(platcust)) {
            criteria.andPlatcustEqualTo(platcust);
        } else {
            criteria.andPlatcustEqualTo(plat_no);
        }
        if (StringUtils.isNotBlank(subject))
            criteria.andSubjectEqualTo(subject);
        if (StringUtils.isNotBlank(sub_subject))
            criteria.andSub_subjectEqualTo(sub_subject);

        List<AccountSubjectInfo> accountSubjectInfos = accountSubjectInfoMapper.selectByExample(accountSubjectInfoExample);
        if (accountSubjectInfos.size() > 1) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "--数据库有重复的账户");
            throw new BusinessException(baseResponse);
        } else if (accountSubjectInfos.size() <= 0) {
            return null;
        }
        return accountSubjectInfos.get(0);
    }

    /**
     * 代扣订单查询
     */
    public WithholdResponseDetail queryWithhold(WithholdRequest withholdRequest)throws BusinessException{
        logger.info("批量代扣查询开始",withholdRequest.getOrder_no());
        WithholdResponseDetail withholdResponseDetail=new WithholdResponseDetail();
        TransTransreqExample transTransreqExample = new TransTransreqExample();
        List<WithholdResponse>withholdResponseList=new ArrayList<>();
        TransTransreqExample.Criteria criteria = transTransreqExample.createCriteria();
        criteria.andOrder_noEqualTo(withholdRequest.getBatch_order_no());
        List<TransTransreq>transTransreqs=transTransreqMapper.selectByExample(transTransreqExample);
        if (transTransreqs.size()==0){
            throw  new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
        }
        try {
            PlatPaycode platPayCode = accountSearchService.queryPlatPayCode(withholdRequest.getMer_no(), withholdRequest.getPay_code());

            String host = sysParameterService.querySysParameter(withholdRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY);
            String url = sysParameterService.querySysParameter(withholdRequest.getMall_no(), URLConfigUtil.BATCH_PAY_QUERY_NEW);

            Map<String,Object>map=new HashMap<>();
            map.put("partner_id",platPayCode.getPayment_plat_no());
            map.put("partner_serial_no", SeqUtil.getSeqNum());
            map.put("partner_trans_date", DateUtils.getyyyyMMddDate());
            map.put("partner_trans_time",DateUtils.getHHmmssTime());
            map.put("original_serial_no",withholdRequest.getBatch_order_no());
            map.put("url",url);
            map.put("host",host);
            String mall_no=withholdRequest.getMall_no();
            logger.info("【 批量代扣】三方请求参数："+ JSON.toJSONString(map));
            Map<String,Object> res=adapterService.batchPayQueryTo(map);
            List<JSONObject> list = (List) res.get("data");
            WithholdResponse withholdResponse=new WithholdResponse();
            for (JSONObject jo:list){
                withholdResponse.setOccur_balance(jo.getBigDecimal("occur_balance"));
                withholdResponse.setDetail_no(jo.getString("detail_no"));
                withholdResponse.setClient_name(jo.getString("client_name"));
                withholdResponse.setStatus(jo.getString("order_status"));
                withholdResponse.setTotal_num(jo.getString("total_num"));
                withholdResponse.setSummary(jo.getString("summary"));
                withholdResponse.setTotal_balance(jo.getString("total_balance"));
                withholdResponse.setCard_no(jo.getString("card_no"));
                String trans_serial=withholdResponse.getDetail_no();
                RwRecharge rwRecharge=new RwRecharge();
                rwRecharge=accountSearchService.queryRwRechargeBySerial(trans_serial,null,null);
                String apply_order_no=rwRecharge.getOrder_no();
                if("7".equals(withholdResponse.getStatus())){
                    withholdResponse.setFail_cause(jo.getString("remsg"));
                    withholdResponse.setStatus("2");
                    rwRecharge.setStatus("2");
                }else if("3".equals(withholdResponse.getStatus())){
                    withholdResponse.setStatus("1");
                    rwRecharge.setStatus("1");
                }
                //调陈瑞的转账
                try {
                    rechargeOptionService.doRecharge(rwRecharge,mall_no,apply_order_no, "批量代扣查询");
                }catch (Exception e){
                    logger.error("【批量代扣查询】==============异常：%s",e);
                    if (e instanceof BusinessException){
                        throw e;
                    }else {
                        logger.error("【批量代扣查询】==============转账异常：%s",e);
                        throw new BusinessException(BusinessMsg.UNKNOW_ERROE,BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE)+ "转账异常");
                    }

                }
                withholdResponseList.add(withholdResponse);
            }
            withholdResponseDetail.setRecode(BusinessMsg.SUCCESS);
            withholdResponseDetail.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        }catch (Exception e){
            logger.error("未知异常", e);
            throw new BusinessException(BusinessMsg.UNKNOW_ERROE,BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE));
        }
        if(withholdRequest.getNotify_url()!=null) {
            NotifyData notifyData = new NotifyData();
            notifyData.setNotifyUrl(withholdRequest.getNotify_url());
            notifyData.setMall_no(withholdRequest.getMall_no());
            withholdResponseDetail.setData_detail(withholdResponseList);
            withholdResponseDetail.setOrder_no(withholdRequest.getOrder_no());
            withholdResponseDetail.setRecode(BusinessMsg.SUCCESS);
            withholdResponseDetail.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            notifyData.setNotifyContent(JSON.toJSONString(withholdResponseDetail));
            notifyService.addNotify(notifyData);
            logger.info("【代扣查询】-->丢入MQ成功-->order_no:" + withholdRequest.getBatch_order_no());
        }
        return  withholdResponseDetail;
    }

    /**
     * @version 20180307
     * @author RaoYL
     * 客户账务往来明细查询--调公司支付(晋商银行调用)
     */
    @Override
    public List<PlatAccountDetailResponseList> queryAccountDetail(PlatAccountDetailRequest accountingDetailRequest) throws BusinessException {
        logger.info("【平台真实账务往来明细查询】order_no:"+accountingDetailRequest.getOrder_no());
        BaseResponse baseResponse = new BaseResponse();
        List<PlatAccountDetailResponseList> array_tran_list = new ArrayList<PlatAccountDetailResponseList>();
        Map<String,Object> realParams = new HashMap<String,Object>();

        try{
            String platCardJson = platCacheService.queryCardInfoToCache(accountingDetailRequest.getMer_no(), accountingDetailRequest.getAccount_type());
            JSONObject platCardJsonObj = JSON.parseObject(platCardJson);
            logger.info("从redis缓存中查出的平台卡信息："+platCardJsonObj);
            String host = sysParameterService.querySysParameter(accountingDetailRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY);
            String url = sysParameterService.querySysParameter(accountingDetailRequest.getMall_no(), URLConfigUtil.EPAY_QUERY_ACCDETAIL);
            realParams.put("partner_id","cg001");//固定值
            realParams.put("channelId","01050000");//固定值
            realParams.put("third_batch_no", SeqUtil.getSeqNum());//合作商流水号为空，入账结果不确定
            realParams.put("tran_type","query");
            realParams.put("host",host);
            realParams.put("url",url);
            JSONArray trandata = new JSONArray();
            Map<String,Object> eMap = new HashMap<String, Object>();
            eMap.put("type",accountingDetailRequest.getIo_flag());//借贷标志【D：借 C:贷 A:全部】
            eMap.put("acct_no",platCardJsonObj.get("card_no").toString());//卡号
            eMap.put("flag",accountingDetailRequest.getDate_flag());//日期标志
            eMap.put("from_date",accountingDetailRequest.getDate_from());
            eMap.put("to_date",accountingDetailRequest.getDate_to());
            trandata.add(eMap);
            logger.info("CCB平台真实账务往来查询【trandata】"+trandata);
            realParams.put("trandata",trandata);
            logger.info(String.format("【平台真实账务往来明细查询】准备客户账务往来明细数据查询|order_no:%s|params:%s",accountingDetailRequest.getOrder_no(),JSON.toJSON(realParams)));
            Map<String,Object> resultMap = adapterService.queryListOfCompanyTransfer(realParams);
            logger.info(String.format("【平台真实账务往来明细查询】准备客户账务往来明细数据查询|order_no:%s|resultMap:%s",accountingDetailRequest.getOrder_no(),JSON.toJSON(resultMap)));

            if(!OrderStatus.SUCCESS.getCode().equals(resultMap.get("order_status"))){
                logger.info("【平台真实账务往来明细查询】order_no:"+accountingDetailRequest.getOrder_no()+"第三方支付返回错误信息");
                baseResponse.setRecode(resultMap.get("recode").toString());
                baseResponse.setRemsg(resultMap.get("remsg").toString());
                throw new BusinessException(baseResponse);
            }
            Object listObj=resultMap.get("array_tran_list");
            if(null == listObj){
                logger.info("【平台真实账务往来明细查询】第三方支付返回结果为空|order_no:"+accountingDetailRequest.getOrder_no());
                baseResponse.setRecode(BusinessMsg.UNKNOW_REMOTE_STATUS);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_REMOTE_STATUS));
                throw new BusinessException(baseResponse);
            }
            array_tran_list= (List<PlatAccountDetailResponseList>) listObj;
            logger.info("***************【"+accountingDetailRequest.getOrder_no()+"】平台真实账务往来明细查询结束*****************");
        }catch (Exception e){
            logger.error("***************【"+accountingDetailRequest.getOrder_no()+"】平台真实账务往来明细查询异常*****************");
            logger.error(e);
            if(e instanceof BusinessException){
                baseResponse = ((BusinessException) e).getBaseResponse();
                throw new BusinessException(baseResponse);
            }else{
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
        }
        return array_tran_list;
    }
    /**
     * 签约查询
     */
    public ContractApplyResponseDetail contractStatus(ContractApplyRequest contractApplyRequest)throws  BusinessException {
        ContractApplyResponseDetail contractApplyResponseDetail=new ContractApplyResponseDetail();
        logger.info("【签约查询】-->进入dubbo-->order_no:"+contractApplyRequest.getOrder_no());
        ContractApplyResponse contractApplyResponseList=new ContractApplyResponse();

        try {
            PlatPaycode platPayCode = accountSearchService.queryPlatPayCode(contractApplyRequest.getMer_no(), contractApplyRequest.getPay_code());
            if(platPayCode==null){
                logger.info("渠道信息错误");
                throw  new BusinessException(BusinessMsg.NO_CHANNEL_INFORMATION,BusinessMsg.getMsg(BusinessMsg.NO_CHANNEL_INFORMATION));
            }
            String host = sysParameterService.querySysParameter(contractApplyRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY);
            String url = sysParameterService.querySysParameter(contractApplyRequest.getMall_no(), URLConfigUtil.EPAY_AGR_BIND_STATUS);

            Map<String,Object>res=new HashMap<>();
            res.put("partner_id",platPayCode.getPayment_plat_no());
            res.put("partner_serial_no", SeqUtil.getSeqNum());
            res.put("partner_trans_date",DateUtils.getyyyyMMddDate());
            res.put("partner_trans_time",DateUtils.getHHmmssTime());
            res.put("client_name",contractApplyRequest.getName());
            res.put("id_kind","0");
            res.put("id_no",contractApplyRequest.getId_code());
            res.put("card_no",contractApplyRequest.getCard_no());
            res.put("mobile_tel",contractApplyRequest.getMobile());
            res.put("channelid",platPayCode.getChannel_id());
            res.put("url",url);
            res.put("host",host);
            logger.info("【 签约查询】三方请求参数："+ JSON.toJSONString(res));
            Map<String,Object> params=adapterService.queryContractAppTo(res);
            logger.info("【 签约查询】三方返回参数："+ JSON.toJSONString(params));
            List<JSONObject> list = (List) params.get("data");
            ContractApplyResponse contractApplyResponse=new ContractApplyResponse();
            for(JSONObject jo:list){
                contractApplyResponse.setStatus((String) params.get("order_status"));
                contractApplyResponse.setPay_code(jo.getString("channelid"));

            }
			/*String order_status="2";
			contractApplyResponse.setStatus("4");
			contractApplyResponse.setPay_code(contractApplyRequest.getPay_code());
			contractApplyResponseList.add(contractApplyResponse);*/

			/*if("2".equals(params.get("order_status"))){
				contractApplyResponseDetail.setData_detail(contractApplyResponseList);
				contractApplyResponseDetail.setOrder_no(contractApplyRequest.getOrder_no());
				contractApplyResponseDetail.setRecode(BusinessMsg.SUCCESS);
				contractApplyResponseDetail.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
				contractApplyResponseDetail.setOrder_status("1");

			}else*/

            if(BusinessMsg.SUCCESS.equals(params.get("recode"))){
                contractApplyResponseDetail.setData_detail(contractApplyResponse);
                contractApplyResponseDetail.setOrder_no(contractApplyRequest.getOrder_no());
                contractApplyResponseDetail.setRecode(BusinessMsg.SUCCESS);
                contractApplyResponseDetail.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            } else if (OrderStatus.FAIL.getCode().equals(params.get("order_status"))){
                logger.info("支付异常信息", contractApplyRequest.getOrder_no());
                throw new BusinessException(params.get("recode").toString(), params.get("remsg").toString());
            }
        } catch (Exception e){
            BaseResponse baseResponse=new BaseResponse();
            logger.info("【签约查询】异常:"+contractApplyRequest.getOrder_no());
            if (e instanceof BusinessException){
                baseResponse=((BusinessException) e).getBaseResponse();
                contractApplyResponseDetail.setRemsg(baseResponse.getRemsg());
                contractApplyResponseDetail.setRecode(baseResponse.getRecode());
            }else{
                contractApplyResponseDetail.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL));
                contractApplyResponseDetail.setRecode(BusinessMsg.FAIL);
                contractApplyResponseDetail.setOrder_no(contractApplyRequest.getOrder_no());
            }
        }
        return contractApplyResponseDetail;
    }
}
