package com.sunyard.sunfintech.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.sunyard.sunfintech.core.dic.RepayDetailType;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.dao.entity.ProdBorrowerRealrepay;
import com.sunyard.sunfintech.dao.entity.ProdProductinfo;
import com.sunyard.sunfintech.dao.entity.ProdRepaymentlist;
import com.sunyard.sunfintech.user.model.bo.RepayDetailRequest;
import com.sunyard.sunfintech.user.model.bo.RepayDetailResponse;
import com.sunyard.sunfintech.user.model.bo.RepayDetailResponseDetail;
import com.sunyard.sunfintech.user.provider.IProdRISearchService;
import com.sunyard.sunfintech.user.provider.IProdSearchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author create by RaoYL
 * @version 20180126
 */
@Service(interfaceClass = IProdRISearchService.class)
@CacheConfig(cacheNames = "prodRISearchService")
@org.springframework.stereotype.Service("prodRISearchService")
public class ProdRISearchService implements IProdRISearchService{

    private final Logger logger = LogManager.getLogger(this.getClass());

//    @Autowired
//    private ProdRepaymentlistMapper repayMentListMapper;
    @Autowired
    private IProdSearchService prodSearchService;
  //  @Autowired
 //   private ProdProductinfoMapper prodProductInfoMapper;

//    @Autowired
//    private ProdBorrowerRealrepayMapper borrowerRealRepayMapper;

    /**
     * 还款明细查询
     * @author RaoYL
     * @param repayDetailRequest
     * @return repayDetailResponse
     */
    public RepayDetailResponse queryRepayDetail(RepayDetailRequest repayDetailRequest)throws BusinessException{
        logger.info("【还款明细查询】order_no:"+repayDetailRequest.getOrder_no());
        RepayDetailResponse repayDetailResponse = new RepayDetailResponse();
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
        List<RepayDetailResponseDetail> repayDetailResponseDetails = new ArrayList<RepayDetailResponseDetail>();
        if (RepayDetailType.REPAYMENTLIST.getCode().equals(type)) {
            //查询投资人收款记录

            List<ProdRepaymentlist> repaymentlist =prodSearchService.listRepayment(repayDetailRequest.getPlatcust(),repayDetailRequest.getProd_id(),repayDetailRequest.getStart_date(),repayDetailRequest.getEnd_date());
            for (ProdRepaymentlist repayment : repaymentlist) {
                RepayDetailResponseDetail repayDetailResponseDetail = new RepayDetailResponseDetail();
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
                RepayDetailResponseDetail repayDetailResponseDetail = new RepayDetailResponseDetail();
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

}
