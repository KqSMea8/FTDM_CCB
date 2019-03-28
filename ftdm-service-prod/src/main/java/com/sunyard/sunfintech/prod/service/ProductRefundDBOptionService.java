package com.sunyard.sunfintech.prod.service;

import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.AmtType;
import com.sunyard.sunfintech.core.dic.ProdType;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.custdao.mapper.CustProdShareInallMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.ProdRepaymentlistMapper;
import com.sunyard.sunfintech.dao.mapper.ProdShareInallMapper;
import com.sunyard.sunfintech.dao.mapper.ProdShareListMapper;
import com.sunyard.sunfintech.prod.provider.IProdSearchService;
import com.sunyard.sunfintech.prod.provider.IProductRefundDBOptionService;
import com.sunyard.sunfintech.pub.provider.ISendMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by terry on 2018/7/11.
 */
@Service
public class ProductRefundDBOptionService extends BaseServiceSimple implements IProductRefundDBOptionService {

    @Autowired
    private ProdRepaymentlistMapper repayMentListMapper;

    @Autowired
    private ProdShareListMapper prodShareListMapper;

    @Autowired
    private CustProdShareInallMapper custProdShareInallMapper;

    @Autowired
    private IProdSearchService prodSearchService;

    @Autowired
    private ISendMsgService sendMsgService;

    @Override
    @Transactional
    public void doRefundDBOption(ProdProductinfo prodProductinfo, BigDecimal real_repay_amount, String platcust, ProdShareList selectOne, ProdRepaymentlist repaymentlist) throws BusinessException {
        //只有定期标才更新份额表
        if(ProdType.PERIOD.getCode().equals(prodProductinfo.getProd_type())){
            logger.info("【标的还款】========更新投资人份额表");
            Map<String,Object> params=new HashMap<>();
            params.put("vol",real_repay_amount);
            params.put("prod_id",prodProductinfo.getProd_id());
            params.put("platcust",platcust);
            params.put("plat_no",prodProductinfo.getPlat_no());
            params.put("update_by", SeqUtil.RANDOM_KEY);
            params.put("update_time",new Date());
            int updateCounts=custProdShareInallMapper.subtractInallVol(params);
            if(updateCounts<=0){
                //份额不足
                logger.info("【标的还款】========剩余待还款份额不足");
                throw new BusinessException(BusinessMsg.REPAYMENT_FAILED,BusinessMsg.getMsg(BusinessMsg.REPAYMENT_FAILED)+"，剩余待还款份额不足");
            }else if(updateCounts>1){
                //数据异常
                logger.info("【标的还款】========有重复份额数据，数据异常");
                throw new BusinessException(BusinessMsg.REPAYMENT_FAILED,BusinessMsg.getMsg(BusinessMsg.REPAYMENT_FAILED)+"，数据异常");
            }
        }
        logger.info("【标的还款】========新增产品购买明细表");
        prodShareListMapper.insert(selectOne);
        logger.info("【标的还款】========新增标的还款信息");
        repayMentListMapper.insert(repaymentlist);
    }

    @Override
    public ProdRepaymentlist queryProdRepaymentlist(String trans_serial) throws BusinessException {
        ProdRepaymentlistExample repaymentlistExample=new ProdRepaymentlistExample();
        ProdRepaymentlistExample.Criteria criteria=repaymentlistExample.createCriteria();
        criteria.andExt1EqualTo(trans_serial);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<ProdRepaymentlist> prodRepaymentlistList=repayMentListMapper.selectByExample(repaymentlistExample);
        if(prodRepaymentlistList.size()>0){
            return prodRepaymentlistList.get(0);
        }
        return null;
    }

    @Override
    public boolean delProdRepaymentlist(ProdRepaymentlist prodRepaymentlist) throws BusinessException {
        if(prodRepaymentlist.getExt1()==null){
            return false;
        }
        ProdRepaymentlistExample repaymentlistExample=new ProdRepaymentlistExample();
        ProdRepaymentlistExample.Criteria criteria=repaymentlistExample.createCriteria();
        criteria.andExt1EqualTo(prodRepaymentlist.getExt1());
        criteria.andEnabledEqualTo(Constants.ENABLED);
        Integer delRows=repayMentListMapper.deleteByExample(repaymentlistExample);
        if(delRows<=0){
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public void backProdRefund(TransTransreq transTransreq) throws BusinessException {
        //查询是否有资金流水记录
        try {
            List<ProdShareList> prodShareListList=prodSearchService.queryProdShareListByTransSerial(transTransreq.getTrans_serial());
            if(prodShareListList.size()>0){
                //将份额回滚
                logger.info("【标的相关】========回滚份额");
                for (ProdShareList prodShareList:prodShareListList){
                    //更新份额表
                    Map<String,Object> params=new HashMap<>();
                    params.put("prod_id",prodShareList.getProd_id());
                    params.put("platcust",prodShareList.getPlatcust());
                    params.put("plat_no",prodShareList.getPlat_no());
                    params.put("vol",prodShareList.getVol());
                    params.put("update_by",SeqUtil.RANDOM_KEY);
                    params.put("update_time",new Date());
                    //更新转让人份额
                    if(AmtType.EXPENSIVE.getCode().equals(prodShareList.getAmt_type())){
                        int successCount=custProdShareInallMapper.addInallVol(params);
                        if(successCount<=0){
                            BaseResponse baseResponse = new BaseResponse();
                            baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                            baseResponse.setRecode(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "：回滚加份额记录不存在。");
                            throw new BusinessException(baseResponse);
                        }
                    }else if(AmtType.INCOME.getCode().equals(prodShareList.getAmt_type())){
                        int successCount=custProdShareInallMapper.subtractInallVol(params);
                        if(successCount<=0){
                            BaseResponse baseResponse = new BaseResponse();
                            baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                            baseResponse.setRecode(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "：回滚扣份额记录不存在或不够扣。");
                            throw new BusinessException(baseResponse);
                        }
                    }
                    prodShareList.setEnabled(Constants.DISABLED);
                    if(null == prodShareList.getUpdate_by()) prodShareList.setUpdate_by(SeqUtil.RANDOM_KEY);
                    prodShareList.setUpdate_time(new Date());
                    prodShareListMapper.updateByPrimaryKey(prodShareList);
                }
                //如果有还款记录，删除还款记录
                ProdRepaymentlist prodRepaymentlist=queryProdRepaymentlist(transTransreq.getTrans_serial());
                if(prodRepaymentlist!=null){
                    delProdRepaymentlist(prodRepaymentlist);
                }
            }
        }catch (Exception e){
            logger.error("【标的相关】回滚份额失败|error：",e);
            String content="【北京银行】标的还款回滚份额失败，order_no:"+transTransreq.getOrder_no();
            sendMsgService.sendErrorToAdmin(content,transTransreq.getPlat_no());
        }
    }

}
