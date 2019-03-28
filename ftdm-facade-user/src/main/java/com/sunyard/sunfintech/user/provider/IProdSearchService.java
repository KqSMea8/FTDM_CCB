package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.account.model.bo.ProductBalanceRequest;
import com.sunyard.sunfintech.account.model.bo.ProductBalanceResponse;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.user.model.bo.ProductInfoRequest;
import com.sunyard.sunfintech.user.model.bo.ProductInfoResponse;
import com.sunyard.sunfintech.user.model.bo.ProductInvestmentDetailRequest;
import com.sunyard.sunfintech.user.model.bo.ProductInvestmentDetailResponse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by terry on 2017/5/11.
 */
public interface IProdSearchService {

    /**
     * 查询标的信息
     * @param plat_no 平台号
     * @param prod_id 产品编号
     * @return ProdProductInfo
     * @throws BusinessException
     */
    public ProdProductinfo queryProdInfo(String plat_no, String prod_id)throws BusinessException;

    /**
     * 更新标的信息
     * @param prodProductInfo
     * @return true：成功
     * @throws BusinessException
     */
    public boolean updateProdInfo(ProdProductinfo prodProductInfo)throws BusinessException;

    /**
     * 检查标的是否成标
     * @param plat_no 平台号
     * @param prod_id 产品编号
     * @return true 已成标
     * @throws BusinessException
     */
    public boolean checkProdInfoIsFound(String plat_no, String prod_id)throws BusinessException;

    /**
     * 查询投标份额
     * @param plat_no 平台号
     * @param prod_id 产品编号
     * @param platcust 平台用户号
     * @return ProdShareInall
     * @throws BusinessException
     */
    public ProdShareInall queryProdShareInall(String plat_no, String prod_id, String platcust)throws BusinessException;

    /**
     * 查询平台投标份额
     * @param plat_no 平台号
     * @param prod_id 产品编号
     * @return ProdShareInall
     * @throws BusinessException
     */
    public List<ProdShareInall> queryProdShareInallList(String plat_no, String prod_id) throws BusinessException;


    /**
     * 检查标的份额是否充足
     * @param plat_no 平台号
     * @param prod_id 产品编号
     * @param platcust 平台用户号
     * @param vol 转出份额
     * @return
     * @throws BusinessException
     */
    public boolean checkProdShareInallVol(String plat_no, String prod_id, String platcust, BigDecimal vol)throws BusinessException;
   
    /**
     * 检查标融资信息
     * @param plat_no 平台号
     * @param prod_id 产品编号
     * @return List<EaccFinancinfo>   融资信息表，一个标可对应多个融资人
     * @throws BusinessException
     */
    public List<EaccFinancinfo> queryEaccFinancinfo(String plat_no, String prod_id, String platcust)throws BusinessException;

    /**
     * 检查标融资信息
     * @param plat_no 平台号
     * @param prod_id 产品编号
     * @return List<EaccFinancinfo>   融资信息表，一个标可对应多个融资人
     * @throws BusinessException
     */
    public List<EaccFinancinfo> queryEaccFinancinfo(String plat_no, String prod_id)throws BusinessException;


    /**
     * 查询还款计划
     * @param prod_id
     * @param plat_no
     * @param enabled
     * @return List<ProdProdRepay> 一个标的  多个期别
     */
    public List<ProdProdrepay> queryProdProdRepay(String prod_id, String plat_no, String enabled)throws BusinessException;
    /**
     * 业务删除还款计划
     * @param prod_id
     * @param plat_no
     * @param enabled
     * @return int
     */
    public Integer updateProdProdRepay(String plat_no, String enabled, String prod_id)throws BusinessException;

    /**
     * @author dingjy
     * @param plat_no
     * @param prod_id
     * @return Integer
     * @throws BusinessException
     */
    public Integer deleteProdProdRepay(String plat_no, String prod_id)throws BusinessException;

    /**
     * 查询标的委托账户表
     * @author pzy
     * @param plat_no
     * @param prod_id
     * @param platcust
     * @return List<ProdCompensationList>
     * @throws BusinessException
     */
    public List<ProdCompensationList> queryProdCompensationList(String plat_no, String prod_id, String platcust)throws BusinessException;

    /**
     * 查询标的委托账户表
     * @author pzy
     * @param plat_no
     * @param prod_id
     * @return List<ProdCompensationList>
     * @throws BusinessException
     */
    public List<ProdCompensationList> queryProdCompensationList(String plat_no, String prod_id)throws BusinessException;

    /**
     * 查询长时间处理中的还款
     * @throws BusinessException
     */
    public List<TransTransreq> queryProcessProductRefund()throws BusinessException;

    /**
     * 根据流水号查询投资信息
     * @param trans_serial
     * @throws BusinessException
     */
    public List<ProdShareList> queryProdShareListByTransSerial(String trans_serial)throws BusinessException;

    /**
     * @author RaoYL
     * 标的信息查询
     * @param productInfoRequest
     * @return ProductInfoResponse
     * @throws BusinessException
     */
    public ProductInfoResponse queryProductInfo(ProductInfoRequest productInfoRequest)throws BusinessException;

    /**
     * 标的投资明细查询
     * @author RaoYL
     * @param investmentDetailRequest
     * @return productInvestmentDetailResponse
     */
    public ProductInvestmentDetailResponse queryInvestmentDeail(ProductInvestmentDetailRequest investmentDetailRequest)throws BusinessException;

    /**
     *借款人募集账户(标的账户)余额查询
     * @author RaoYL
     * @version 20180118
     */
    public ProductBalanceResponse queryProductBalance(ProductBalanceRequest productBalanceRequest)throws BusinessException;

    String getRemainAmtByPlacust(String mer_no, String platcust);

    List<ProdRepaymentlist> listRepayment(String platcust, String prod_id, Date start_date, Date end_date)throws BusinessException;

    ProdProductinfo  getProductById(String platNo, String proId)throws BusinessException;

    List<ProdBorrowerRealrepay>   listBorrowerRealRepay(String platCustNo, String proId, Date startDate, Date endDate)throws BusinessException;

    List<EaccFinancinfo> queryEaccFinancinfos(String plat_no,String prod_id)throws BusinessException;
}
