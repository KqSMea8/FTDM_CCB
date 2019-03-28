package com.sunyard.sunfintech.prod.service;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.account.provider.IAccountTransferService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.AmtType;
import com.sunyard.sunfintech.core.dic.ProdState;
import com.sunyard.sunfintech.core.dic.ProdType;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.custdao.mapper.CustProdProductinfoMapper;
import com.sunyard.sunfintech.custdao.mapper.CustProdRepayMapper;
import com.sunyard.sunfintech.custdao.mapper.CustProdShareInallMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.EaccFinancinfoMapper;
import com.sunyard.sunfintech.dao.mapper.ProdCompensationListMapper;
import com.sunyard.sunfintech.dao.mapper.ProdProductinfoMapper;
import com.sunyard.sunfintech.dao.mapper.ProdShareListMapper;
import com.sunyard.sunfintech.prod.model.bo.ProdEstablishOrAbort;
import com.sunyard.sunfintech.prod.model.bo.ProdEstablishOrAbortRequest;
import com.sunyard.sunfintech.prod.model.bo.ProdEstablishOrAbortRequestOld;
import com.sunyard.sunfintech.prod.model.bo.RepayPlanListObject;
import com.sunyard.sunfintech.prod.provider.IProductOptionExtService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@CacheConfig(cacheNames = "ProductOptionExtService")
@org.springframework.stereotype.Service
public class ProductOptionExtService extends BaseServiceSimple implements IProductOptionExtService {

    private final Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private CustProdShareInallMapper custProdShareInallMapper;

    @Autowired
    private CustProdRepayMapper custProdRepayMapper;

    @Autowired
    private ProdProductinfoMapper prodProductInfoMapper;

    @Autowired
    private CustProdProductinfoMapper custProdProductinfoMapper;

    @Autowired
    private ProdShareListMapper prodShareListMapper;

    @Autowired
    private ProdCompensationListMapper prodCompensationListMapper;

    @Autowired
    private EaccFinancinfoMapper eaccFinancinfoMapper;

    @Autowired
    private IAccountTransferService newAccountTransferService;

    @Override
    @Transactional
    public void publish(ProdProductinfo prodProductinfo,EaccFinancinfo eaccFinancinfo,List<ProdCompensationList> prodCompensationLists,String order_no) throws BusinessException {
        try {
            if(null == prodProductinfo.getCreate_by()) prodProductinfo.setCreate_by(SeqUtil.RANDOM_KEY);
            if(null == prodProductinfo.getCreate_time()) prodProductinfo.setCreate_time(new Date());
            prodProductInfoMapper.insert(prodProductinfo);
            logger.info("【借款人募集申请】-->添加标的信息完成-->order_no:" + order_no);

            if(null != prodCompensationLists && prodCompensationLists.size()>0) {
                for (ProdCompensationList prodCompensationList : prodCompensationLists) {
                    prodCompensationListMapper.insert(prodCompensationList);
                    logger.info("【借款人募集申请】-->添加代偿信息成功-->order_no:" + order_no);
                }
            }
            eaccFinancinfoMapper.insert(eaccFinancinfo);
            logger.info("【借款人募集申请】-->添加融资信息完成-->order_no:" + order_no);
        }catch (Exception e){
            logger.error("【借款人募集申请】-->异常-->order_no:" + order_no,e);
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION,BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void doEstablish(ProdEstablishOrAbortRequest prodEstablishOrAbort, List<ProdShareList> prodShareLists, List<RepayPlanListObject> repayPlanListObjectList, EaccAccountamtlist eaccAccountamtlist, String prod_type) throws BusinessException {
        logger.info("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成立(新)>>开始单笔转账**********" + JSON.toJSON(eaccAccountamtlist));
        newAccountTransferService.transfer(eaccAccountamtlist);
        logger.info("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成立(新)>>单笔转账完成**********");
        try {
            addShareInall(prodShareLists);
            logger.info("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成立(新)>>新增标的份额成功**********");

            //新增还款计划 prod_prodRepay
            addRepay(repayPlanListObjectList, prodEstablishOrAbort.getMer_no(), prodEstablishOrAbort.getProd_id(), prodEstablishOrAbort.getRemark());
            logger.info("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成立(新)>>新增标的还款计划成功**********");

            if(ProdType.PERIOD.getCode().equals(prod_type)){
                //更新标的信息为成立(只有定期标的才会更新)
                ProdProductinfoExample example = new ProdProductinfoExample();
                ProdProductinfoExample.Criteria criteria = example.createCriteria();
                criteria.andPlat_noEqualTo(prodEstablishOrAbort.getMer_no());
                criteria.andProd_idEqualTo(prodEstablishOrAbort.getProd_id());
                criteria.andEnabledEqualTo(Constants.ENABLED);
                ProdProductinfo prodProductinfo = new ProdProductinfo();
                prodProductinfo.setProd_state(ProdState.FOUND.getCode());
                prodProductinfo.setUpdate_time(new Date());
                prodProductinfo.setUpdate_by(SeqUtil.RANDOM_KEY);
                prodProductInfoMapper.updateByExampleSelective(prodProductinfo, example);
                logger.info("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成立(新)>>更新标的信息为成立**********");
            }
        } catch (Exception e) {
            //如果添加份额，添加还款计划，修改标的状态，3个方法之一存在异常，则回滚资金转账操作
            logger.error("【标的成立(新)】异常", e);
            logger.error("【标的成立(新)】异常", "执行资金回滚");
            EaccAccountamtlist eaccAccountamtlist_rollback = new EaccAccountamtlist();
            eaccAccountamtlist_rollback.setPlat_no(eaccAccountamtlist.getPlat_no());
            eaccAccountamtlist_rollback.setOppo_plat_no(eaccAccountamtlist.getPlat_no());
            eaccAccountamtlist_rollback.setTrans_serial(eaccAccountamtlist.getTrans_serial());
            eaccAccountamtlist_rollback.setTrans_code(eaccAccountamtlist.getTrans_code());
            eaccAccountamtlist_rollback.setTrans_name(eaccAccountamtlist.getTrans_name());
            eaccAccountamtlist_rollback.setTrans_date(prodEstablishOrAbort.getPartner_trans_date());
            eaccAccountamtlist_rollback.setTrans_time(prodEstablishOrAbort.getPartner_trans_time());
            eaccAccountamtlist_rollback.setAmt_type(AmtType.EXPENSIVE.getCode());
            eaccAccountamtlist_rollback.setOrder_no(prodEstablishOrAbort.getOrder_no());
            eaccAccountamtlist_rollback.setAmt(eaccAccountamtlist.getAmt());
            eaccAccountamtlist_rollback.setPlatcust(eaccAccountamtlist.getOppo_platcust());
            eaccAccountamtlist_rollback.setSubject(eaccAccountamtlist.getOppo_subject());
            eaccAccountamtlist_rollback.setSub_subject(eaccAccountamtlist.getOppo_sub_subject());
            eaccAccountamtlist_rollback.setOppo_subject(eaccAccountamtlist.getSubject());
            eaccAccountamtlist_rollback.setOppo_sub_subject(eaccAccountamtlist.getSub_subject());
            eaccAccountamtlist_rollback.setOppo_platcust(eaccAccountamtlist.getPlatcust());
            eaccAccountamtlist_rollback.setRemark("资金回滚");
            newAccountTransferService.transfer(eaccAccountamtlist_rollback);
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
        }
    }

    //试用老的标的成立接口
    @Transactional(rollbackFor = Exception.class)
    public void doEstablish(ProdEstablishOrAbortRequestOld prodEstablishOrAbort, List<ProdShareList> prodShareLists, List<RepayPlanListObject> repayPlanListObjectList, List<EaccAccountamtlist> eaccAccountamtlist, String prod_type) throws BusinessException {
        logger.info("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成立(老)>>开始单笔转账**********" + JSON.toJSON(eaccAccountamtlist));
        newAccountTransferService.batchTransfer(eaccAccountamtlist);
        logger.info("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成立(老)>>单笔转账完成**********");
        try {
            addShareInall(prodShareLists);
            logger.info("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成立(老)>>新增标的份额成功**********");

            //新增还款计划 prod_prodRepay
            addRepay(repayPlanListObjectList, prodEstablishOrAbort.getMer_no(), prodEstablishOrAbort.getProd_id(), prodEstablishOrAbort.getRemark());
            logger.info("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成立(老)>>新增标的还款计划成功**********");

            if(ProdType.PERIOD.getCode().equals(prod_type)){
                //更新标的信息为成立(只有定期标的才会更新)
                ProdProductinfoExample example = new ProdProductinfoExample();
                ProdProductinfoExample.Criteria criteria = example.createCriteria();
                criteria.andPlat_noEqualTo(prodEstablishOrAbort.getMer_no());
                criteria.andProd_idEqualTo(prodEstablishOrAbort.getProd_id());
                criteria.andEnabledEqualTo(Constants.ENABLED);
                ProdProductinfo prodProductinfo = new ProdProductinfo();
                prodProductinfo.setProd_state(ProdState.FOUND.getCode());
                prodProductinfo.setUpdate_time(new Date());
                prodProductinfo.setUpdate_by(SeqUtil.RANDOM_KEY);
                prodProductInfoMapper.updateByExampleSelective(prodProductinfo, example);
                logger.info("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成立(老)>>更新标的信息为成立**********");
            }
        } catch (Exception e) {
            //如果添加份额，添加还款计划，修改标的状态，3个方法之一存在异常，则回滚资金转账操作
            logger.error("【标的成立(老)】异常", e);
            logger.error("【标的成立(老)】异常", "执行资金回滚");
            for(EaccAccountamtlist  ea: eaccAccountamtlist){
                EaccAccountamtlist eaccAccountamtlist_rollback =new EaccAccountamtlist();
                eaccAccountamtlist_rollback.setPlat_no(ea.getPlat_no());
                eaccAccountamtlist_rollback.setOppo_plat_no(ea.getPlat_no());
                eaccAccountamtlist_rollback.setTrans_serial(ea.getTrans_serial());
                eaccAccountamtlist_rollback.setTrans_code(ea.getTrans_code());
                eaccAccountamtlist_rollback.setTrans_name(ea.getTrans_name());
                eaccAccountamtlist_rollback.setTrans_date(prodEstablishOrAbort.getPartner_trans_date());
                eaccAccountamtlist_rollback.setTrans_time(prodEstablishOrAbort.getPartner_trans_time());
                eaccAccountamtlist_rollback.setAmt_type(AmtType.EXPENSIVE.getCode());
                eaccAccountamtlist_rollback.setOrder_no(prodEstablishOrAbort.getOrder_no());
                eaccAccountamtlist_rollback.setAmt(ea.getAmt());
                eaccAccountamtlist_rollback.setPlatcust(ea.getOppo_platcust());
                eaccAccountamtlist_rollback.setSubject(ea.getOppo_subject());
                eaccAccountamtlist_rollback.setSub_subject(ea.getOppo_sub_subject());
                eaccAccountamtlist_rollback.setOppo_subject(ea.getSubject());
                eaccAccountamtlist_rollback.setOppo_sub_subject(ea.getSub_subject());
                eaccAccountamtlist_rollback.setOppo_platcust(ea.getPlatcust());
                eaccAccountamtlist_rollback.setRemark("资金回滚");
            }
            newAccountTransferService.batchTransfer(eaccAccountamtlist);
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
        }
    }

    @Transactional
    @Override
    public void doAbortInvestment(List<EaccAccountamtlist> rollBackList, List<ProdShareList> prodShareLists, ProdShareList prodShareList, ProdProductinfo prodProductInfo) throws BusinessException {
        newAccountTransferService.batchTransfer(rollBackList);
        try {
            //更新标的份额
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("vol", prodShareLists.get(0).getVol());
            map.put("prod_id", prodProductInfo.getProd_id());
            map.put("plat_no", prodProductInfo.getPlat_no());
            map.put("update_time",new Date());
            map.put("update_by",SeqUtil.RANDOM_KEY);
            custProdProductinfoMapper.updateProd(map);
            logger.info("******投资撤销>>更新标的份额成功******");
            prodShareList.setCreate_by(SeqUtil.RANDOM_KEY);
            prodShareList.setCreate_time(DateUtils.today());
            prodShareListMapper.insert(prodShareList);
            logger.info("******投资撤销>>插入投资明细数据成功******");
        } catch (Exception e) {
            //如果更新份额，还原明细，2个方法之一存在异常，则回滚资金转账操作
            logger.error("【投资撤销】异常", e);
            logger.error("【投资撤销】异常", "执行资金回滚");
            List<EaccAccountamtlist> rollback_list=new ArrayList<EaccAccountamtlist>();
            for (EaccAccountamtlist org_eacc : rollBackList) {
                EaccAccountamtlist eaccAccountamtlist_rollback = new EaccAccountamtlist();
                eaccAccountamtlist_rollback.setPlat_no(org_eacc.getPlat_no());
                eaccAccountamtlist_rollback.setOppo_plat_no(org_eacc.getPlat_no());
                eaccAccountamtlist_rollback.setTrans_serial(org_eacc.getTrans_serial());
                eaccAccountamtlist_rollback.setTrans_code(org_eacc.getTrans_code());
                eaccAccountamtlist_rollback.setTrans_name(org_eacc.getTrans_name());
                eaccAccountamtlist_rollback.setTrans_date(org_eacc.getTrans_date());
                eaccAccountamtlist_rollback.setTrans_time(org_eacc.getTrans_time());
                eaccAccountamtlist_rollback.setAmt_type(AmtType.EXPENSIVE.getCode());
                eaccAccountamtlist_rollback.setOrder_no(org_eacc.getOrder_no());
                eaccAccountamtlist_rollback.setAmt(org_eacc.getAmt());
                eaccAccountamtlist_rollback.setPlatcust(org_eacc.getOppo_platcust());
                eaccAccountamtlist_rollback.setSubject(org_eacc.getOppo_subject());
                eaccAccountamtlist_rollback.setSub_subject(org_eacc.getOppo_sub_subject());
                eaccAccountamtlist_rollback.setOppo_subject(org_eacc.getSubject());
                eaccAccountamtlist_rollback.setOppo_sub_subject(org_eacc.getSub_subject());
                eaccAccountamtlist_rollback.setOppo_platcust(org_eacc.getPlatcust());
                eaccAccountamtlist_rollback.setOrder_no(org_eacc.getOrder_no());
                eaccAccountamtlist_rollback.setRemark("资金回滚");
                rollback_list.add(eaccAccountamtlist_rollback);
            }
            newAccountTransferService.batchTransfer(rollback_list);
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
        }
    }

    private void addShareInall(List<ProdShareList> prodShareLists) {
        ProdShareInall prodShareInall = null;
        List<ProdShareInall> prodShareInalls = new ArrayList<ProdShareInall>();
        for (ProdShareList ps : prodShareLists) {
            prodShareInall = new ProdShareInall();
            prodShareInall.setPlat_no(ps.getPlat_no());
            prodShareInall.setProd_id(ps.getProd_id());
            prodShareInall.setPlatcust(ps.getPlatcust());
            prodShareInall.setVol(ps.getVol());
            prodShareInall.setTot_vol(ps.getVol());
            prodShareInall.setEnabled(Constants.ENABLED);
            prodShareInall.setCreate_time(new Date());
            prodShareInall.setCreate_by(SeqUtil.RANDOM_KEY);
            prodShareInalls.add(prodShareInall);
        }
        custProdShareInallMapper.insertMore(prodShareInalls);
        prodShareInalls.clear();
    }

    private void addRepay(List<RepayPlanListObject> repayPlanListObjectList, String plat_no, String prod_id, String remark) {
        List<ProdProdrepay> prodProdRepayList = new ArrayList<ProdProdrepay>();
        ProdProdrepay prodProdRepay = null;
        for (int i = 0; i < repayPlanListObjectList.size(); i++) {
            prodProdRepay = new ProdProdrepay();
            prodProdRepay.setTrans_serial(SeqUtil.getSeqNum());
            prodProdRepay.setPlat_no(plat_no);
            prodProdRepay.setRepay_num(Integer.valueOf(repayPlanListObjectList.get(i).getRepay_num()));
            prodProdRepay.setProd_id(prod_id);
            prodProdRepay.setRepay_date(repayPlanListObjectList.get(i).getRepay_date());
            prodProdRepay.setRepay_amt(new BigDecimal(repayPlanListObjectList.get(i).getRepay_amt()));
            prodProdRepay.setRepay_fee(new BigDecimal(repayPlanListObjectList.get(i).getRepay_fee()));
            prodProdRepay.setEnabled(Constants.ENABLED);
            prodProdRepay.setRemark(remark);
            prodProdRepay.setCreate_time(new Date());
            prodProdRepayList.add(prodProdRepay);
        }
        custProdRepayMapper.insertMore(prodProdRepayList);
        prodProdRepayList.clear();
    }
}
