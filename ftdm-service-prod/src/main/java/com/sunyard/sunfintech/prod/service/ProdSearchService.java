package com.sunyard.sunfintech.prod.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.sunyard.sunfintech.account.model.bo.ProductBalanceRequest;
import com.sunyard.sunfintech.account.model.bo.ProductBalanceResponse;
import com.sunyard.sunfintech.account.model.bo.ProductBalanceResponseDetail;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.FlowStatus;
import com.sunyard.sunfintech.core.dic.ProdState;
import com.sunyard.sunfintech.core.dic.Ssubject;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.custdao.mapper.CustProdShareListMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.*;
import com.sunyard.sunfintech.prod.model.bo.*;
import com.sunyard.sunfintech.prod.provider.IProdSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by terry on 2017/5/11.
 */
@Service(interfaceClass = IProdSearchService.class)
@CacheConfig(cacheNames="prodSearchService")
@org.springframework.stereotype.Service("prodSearchService")
@Transactional
public class ProdSearchService extends BaseServiceSimple implements IProdSearchService {
    @Autowired
    private ProdProductinfoMapper prodProductInfoMapper;

    @Autowired
    private ProdShareInallMapper prodShareInallMapper;
    
    @Autowired
    private EaccFinancinfoMapper eaccFinancinfoMapper;
    
    @Autowired
    private ProdProdrepayMapper prodRepayMapper;

    @Autowired
    private ProdCompensationListMapper prodCompensationListMapper;

    @Autowired
    private TransTransreqMapper transTransreqMapper;

    @Autowired
    private ProdShareListMapper prodShareListMapper;

    @Autowired
    private CustProdShareListMapper custProdShareListMapper;

    @Autowired
    private IAccountQueryService accountQueryService;

    @Autowired
    private ProdBorrowerRealrepayMapper borrowerRealRepayMapper;
    @Autowired
    private ProdRepaymentlistMapper repayMentListMapper;
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

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public boolean updateProdInfo(ProdProductinfo prodProductInfo) throws BusinessException {
        if(null == prodProductInfo.getUpdate_by()) prodProductInfo.setUpdate_by(SeqUtil.RANDOM_KEY);
        prodProductInfo.setUpdate_time(new Date());
        prodProductInfoMapper.updateByPrimaryKeySelective(prodProductInfo);
        return false;
    }

    @Override
    public boolean checkProdInfoIsFound(String plat_no, String prod_id) throws BusinessException {
        ProdProductinfo checkProductInfo=queryProdInfo(plat_no,prod_id);
        if(checkProductInfo==null){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_PRODUCT_ID);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID));
            throw new BusinessException(baseResponse);
        }
        if(!ProdState.FOUND.getCode().equals(checkProductInfo.getProd_state())){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.ESTABLISH_PRODUCT_FAILED);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.ESTABLISH_PRODUCT_FAILED));
            throw new BusinessException(baseResponse);
        }
        return true;
    }

    @Override
    public ProdShareInall queryProdShareInall(String plat_no, String prod_id, String platcust) throws BusinessException {
        ProdShareInallExample example=new ProdShareInallExample();
        ProdShareInallExample.Criteria criteria=example.createCriteria();
        criteria.andPlat_noEqualTo(plat_no);
        criteria.andProd_idEqualTo(prod_id);
        criteria.andPlatcustEqualTo(platcust);
        criteria.andEnabledEqualTo(Constants.ENABLED);
//        example.or(criteria);
        List<ProdShareInall> prodShareInallList = prodShareInallMapper.selectByExample(example);
        if(prodShareInallList.size() > 1){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "prod_share_inall里多条一样的数据");
            throw new BusinessException(baseResponse);
        }else if(prodShareInallList.size() < 1){
            return null;
        }
        return prodShareInallList.get(0);
    }

    @Override
    public List<ProdShareInall> queryProdShareInallList(String plat_no, String prod_id) throws BusinessException {
        ProdShareInallExample example=new ProdShareInallExample();
        ProdShareInallExample.Criteria criteria=example.createCriteria();
        criteria.andPlat_noEqualTo(plat_no);
        criteria.andProd_idEqualTo(prod_id);
        criteria.andEnabledEqualTo(Constants.ENABLED);
//        example.or(criteria);
        return prodShareInallMapper.selectByExample(example);
    }

    @Override
    public boolean checkProdShareInallVol(String plat_no, String prod_id, String platcust, BigDecimal vol) throws BusinessException {
        ProdShareInall prodShareInall=queryProdShareInall(plat_no,prod_id,platcust);
        if(prodShareInall==null){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.NVALID_TRANSFER_NO);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.NVALID_TRANSFER_NO));
            throw new BusinessException(baseResponse);
        }
        if(prodShareInall.getTot_vol().compareTo(vol)<0){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.ASSIGNMENT_DISSATISF);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.ASSIGNMENT_DISSATISF)+",转让人标的份额不足");
            throw new BusinessException(baseResponse);
        }
        return true;
    }
    
    
    /**
     * 检查标融资信息
     * @param plat_no 平台号
     * @param prod_id 产品编号
     * @return List<EaccFinancinfo>   融资信息表，一个标可对应多个融资人
     * @throws BusinessException
     */
    @Override
    public List<EaccFinancinfo> queryEaccFinancinfo(String plat_no, String prod_id,String platcust)throws BusinessException{
        EaccFinancinfoExample example=new EaccFinancinfoExample();
        EaccFinancinfoExample.Criteria criteria=example.createCriteria();
        criteria.andPlat_noEqualTo(plat_no);
        criteria.andProd_idEqualTo(prod_id);
        criteria.andPlatcustEqualTo(platcust);
        criteria.andEnabledEqualTo(Constants.ENABLED);
//        example.or(criteria);
    	return eaccFinancinfoMapper.selectByExample(example);
    }
    
    
    
    /**
     * 查询还款计划
     * @param prod_id
     * @param plat_no
     * @param enabled
     * @return List<ProdProdRepay> 一个标的  多个期别
     */
	@Override
	public List<ProdProdrepay> queryProdProdRepay(String prod_id, String plat_no, String enabled)throws BusinessException {
	    ProdProdrepayExample example=new ProdProdrepayExample();
	    ProdProdrepayExample.Criteria criteria=example.createCriteria();
	    criteria.andProd_idEqualTo(prod_id);
	    criteria.andPlat_noEqualTo(plat_no);
	    criteria.andEnabledEqualTo(enabled);
//        example.or(criteria);
		return  prodRepayMapper.selectByExample(example);
		 
	}

	/**
     * 业务删除还款计划
     * @param prod_id
     * @param plat_no
     * @param enabled
     * @return int 
     */
	@Override
	public Integer updateProdProdRepay(String plat_no, String enabled, String prod_id)throws BusinessException {
	    ProdProdrepayExample example=new ProdProdrepayExample();
	    ProdProdrepayExample.Criteria criteria=example.createCriteria();
	    criteria.andPlat_noEqualTo(plat_no);
	    criteria.andProd_idEqualTo(prod_id);
	    criteria.andEnabledEqualTo(enabled);
        ProdProdrepay prodProdRepay=new ProdProdrepay();
		prodProdRepay.setEnabled(Constants.DISABLED);
//        example.or(criteria);
        return prodRepayMapper.updateByExampleSelective(prodProdRepay,example);
		
	}


    @Override
    @Transactional
    public Integer deleteProdProdRepay(String plat_no,String prod_id) throws BusinessException {
        ProdProdrepayExample example=new ProdProdrepayExample();
        ProdProdrepayExample.Criteria criteria=example.createCriteria();
        criteria.andPlat_noEqualTo(plat_no);
        criteria.andProd_idEqualTo(prod_id);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        return prodRepayMapper.deleteByExample(example);
    }

    /**
     * pzy
     * 标的委托账户表
     */
    @Override
    public List<ProdCompensationList> queryProdCompensationList(String plat_no, String prod_id,String platcust) throws BusinessException {
        ProdCompensationListExample prodCompensationListExample = new ProdCompensationListExample();
        ProdCompensationListExample.Criteria criteria = prodCompensationListExample.createCriteria();
        criteria.andPlat_noEqualTo(plat_no);
        criteria.andProd_idEqualTo(prod_id);
        criteria.andPlatcustEqualTo(platcust);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        return prodCompensationListMapper.selectByExample(prodCompensationListExample);
    }

    /**
     * pzy
     * 标的委托账户表
     */
    @Override
    public List<ProdCompensationList> queryProdCompensationList(String plat_no, String prod_id) throws BusinessException {
        ProdCompensationListExample prodCompensationListExample = new ProdCompensationListExample();
        ProdCompensationListExample.Criteria criteria = prodCompensationListExample.createCriteria();
        criteria.andPlat_noEqualTo(plat_no);
        criteria.andProd_idEqualTo(prod_id);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        return prodCompensationListMapper.selectByExample(prodCompensationListExample);
    }

    @Override
    public List<TransTransreq> queryProcessProductRefund() throws BusinessException {
        //查询最近1小时前到24小时提交过来的处理中的还款
        TransTransreqExample transTransreqExample=new TransTransreqExample();
        TransTransreqExample.Criteria transTransreqCriteria=transTransreqExample.createCriteria();
        transTransreqCriteria.andCreate_timeBetween(DateUtils.getYesterday(),DateUtils.addHour(DateUtils.getNow(),-2));
        List<String> transCodeList=new ArrayList<>();
        transCodeList.add(TransConsts.PRODREPAY_CODE);
        transCodeList.add(TransConsts.TRANSFERDEBT_CODE);
        transTransreqCriteria.andTrans_codeIn(transCodeList);
//        transTransreqCriteria.andTrans_codeEqualTo(TransConsts.PRODREPAY_CODE);
        transTransreqCriteria.andStatusEqualTo(FlowStatus.INPROCESS.getCode());
        transTransreqCriteria.andEnabledEqualTo(Constants.ENABLED);
        List<TransTransreq> processOrders=transTransreqMapper.selectByExample(transTransreqExample);
        return processOrders;
    }

    @Override
    public List<ProdShareList> queryProdShareListByTransSerial(String trans_serial) throws BusinessException {
        ProdShareListExample example=new ProdShareListExample();
        ProdShareListExample.Criteria criteria=example.createCriteria();
        criteria.andTrans_serialEqualTo(trans_serial);
        criteria.andEnabledEqualTo(Constants.ENABLED);
//        example.or(criteria);
        return prodShareListMapper.selectByExample(example);
    }

    /**
     * @author RaoYL
     * 标的信息查询
     * @param productInfoRequest
     * @return ProductInfoResponse
     * @throws BusinessException
     */
    @Override
    public ProductInfoResponse queryProductInfo(ProductInfoRequest productInfoRequest) throws BusinessException{
        logger.info("【标的信息查询】order_no:"+productInfoRequest.getOrder_no());
        ProdProductinfo prodProductinfo = queryProdInfo(productInfoRequest.getMer_no(),productInfoRequest.getProd_id());
        if(prodProductinfo == null){
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_PRODUCT_ID);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID)+"，标的不存在");
            throw new BusinessException(baseResponse);
        }
        ProductInfoResponse productInfoResponse = new ProductInfoResponse();
        List<Map<String,Object>> prodCompensationLists=prodCompensationListMapper.getProdCompensationListByProdId(productInfoRequest.getMer_no(), productInfoRequest.getProd_id());
        ProductInfoResponseDetail productInfoResponseDetail = new ProductInfoResponseDetail();
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
     * 标的投资明细查询
     * @author RaoYL
     * @param investmentDetailRequest
     * @return productInvestmentDetailResponse
     */
    public ProductInvestmentDetailResponse queryInvestmentDeail(ProductInvestmentDetailRequest investmentDetailRequest)throws BusinessException{
        logger.info("【标的投资明细查询】order_no:"+investmentDetailRequest.getOrder_no());
        ProdProductinfo prodProductinfo = queryProdInfo(investmentDetailRequest.getMer_no(),investmentDetailRequest.getProd_id());
        if(prodProductinfo == null){
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_PRODUCT_ID);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID));
            throw new BusinessException(baseResponse);
        }
        ProductInvestmentDetailResponse detailResponse = new ProductInvestmentDetailResponse();
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
     *借款人募集账户(标的账户)余额查询
     * @author RaoYL
     * @param productBalanceRequest 借款人募集账户(标的账户)余额查询  验证请求参数
     * @return ProductBalanceResponse
     * @version 20180118
     */
    @Override
    public ProductBalanceResponse queryProductBalance(ProductBalanceRequest productBalanceRequest)throws BusinessException{
        logger.info("【借款人募集账户余额查询】order_no:"+productBalanceRequest.getOrder_no());
        ProductBalanceResponse productBalanceResponse = new ProductBalanceResponse();
        ProductBalanceResponseDetail productBalanceResponseDetail = new ProductBalanceResponseDetail();
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
    @Override
    public String getRemainAmtByPlacust(String mer_no, String platcust) {
        return custProdShareListMapper.getRemainAmtByPlacust(mer_no,platcust);
    }

    @Override
    public List<ProdRepaymentlist> listRepayment(String platcust, String prod_id, Date start_date, Date end_date)throws BusinessException{
        ProdRepaymentlistExample example = new ProdRepaymentlistExample();
        ProdRepaymentlistExample.Criteria criteria = example.createCriteria();
        criteria.andPlatcustEqualTo(platcust);
        if (prod_id != null) {
            criteria.andProd_idEqualTo(prod_id);
        }
        criteria.andEnabledEqualTo(Constants.ENABLED);
        if (start_date != null || end_date != null) {
            criteria.andReal_repay_dateBetween(start_date,
                    end_date);
        }
        List<ProdRepaymentlist> repaymentlist = repayMentListMapper.selectByExample(example);
        return repaymentlist;
    }
    @Override
    public ProdProductinfo  getProductById(String platNo,String proId)throws BusinessException {
        ProdProductinfoExample productinfoExample = new ProdProductinfoExample();
        ProdProductinfoExample.Criteria createCriteria = productinfoExample.createCriteria();
        createCriteria.andPlat_noEqualTo(platNo);
        createCriteria.andProd_idEqualTo(proId);
        createCriteria.andEnabledEqualTo(Constants.ENABLED);
        List<ProdProductinfo> productInfo = prodProductInfoMapper.selectByExample(productinfoExample);
        if (productInfo!=null&&productInfo.size()>0) {
            return productInfo.get(0);
        }
        return null;
    }
    @Override
    public List<ProdBorrowerRealrepay>   listBorrowerRealRepay(String platCustNo,String proId,Date startDate,Date endDate)throws BusinessException {

        ProdBorrowerRealrepayExample borrowerRealrepayExample = new ProdBorrowerRealrepayExample();
        ProdBorrowerRealrepayExample.Criteria criteria = borrowerRealrepayExample.createCriteria();
        criteria.andPlatcustEqualTo(platCustNo);
        if (proId != null) {
            criteria.andProd_idEqualTo(proId);
        }
        criteria.andEnabledEqualTo(Constants.ENABLED);
        if (startDate != null || endDate != null) {
            criteria.andTrans_dateBetween(DateUtils.formatDateToStr(startDate, DateUtils.DEF_FORMAT_STRING_DATE),
                    DateUtils.formatDateToStr(endDate, DateUtils.DEF_FORMAT_STRING_DATE));
        }
        List<ProdBorrowerRealrepay> borrowerRealRepaylist = borrowerRealRepayMapper.selectByExample(borrowerRealrepayExample);
        return borrowerRealRepaylist;
    }
}
