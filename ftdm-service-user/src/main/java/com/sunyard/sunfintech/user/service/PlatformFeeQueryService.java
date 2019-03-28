package com.sunyard.sunfintech.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlistExample;
import com.sunyard.sunfintech.dao.entity.SunbobTaskUnionpay;
import com.sunyard.sunfintech.dao.entity.SunbobTaskUnionpayExample;
import com.sunyard.sunfintech.dao.mapper.EaccAccountamtlistMapper;
import com.sunyard.sunfintech.dao.mapper.SunbobTaskUnionpayMapper;
import com.sunyard.sunfintech.user.model.bo.SubstitutePayFeeData;
import com.sunyard.sunfintech.user.model.bo.SubstitutePayFeeRequest;
import com.sunyard.sunfintech.user.provider.IPlatformFeeQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by terry on 2017/5/24.
 */
@Service(interfaceClass = IPlatformFeeQueryService.class)
@CacheConfig(cacheNames="platformFeeQueryService")
@org.springframework.stereotype.Service
public class PlatformFeeQueryService extends BaseServiceSimple implements IPlatformFeeQueryService {

    @Autowired
    private EaccAccountamtlistMapper eaccAccountamtlistMapper;

    @Autowired
    private SunbobTaskUnionpayMapper sunbobTaskUnionpayMapper;
    @Override
    public List<SubstitutePayFeeData> substitutePayFeeQuery(SubstitutePayFeeRequest substitutePayFeeRequest) throws BusinessException {
        logger.info("【自有资金账户手续费扣款查询】开始，入参:"+substitutePayFeeRequest);
        List<SubstitutePayFeeData> substitutePayFeeDataList=new ArrayList<SubstitutePayFeeData>();
        try{
            if(!"1".equals(substitutePayFeeRequest.getFee_type())){
                logger.info("【自有资金账户手续费扣款查询】:该接口暂时只支持银联代扣手续费查询");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+"：该接口暂时只支持银联代扣手续费查询");
            }
            SunbobTaskUnionpayExample example=new SunbobTaskUnionpayExample();
            SunbobTaskUnionpayExample.Criteria criteria=example.createCriteria();
            criteria.andPlat_noEqualTo(substitutePayFeeRequest.getMer_no());
            List<String> typeList = new ArrayList<String>();
            typeList.add("01");
            typeList.add("02");
            criteria.andTrans_typeIn(typeList);
            criteria.andTrans_dateBetween(substitutePayFeeRequest.getStart_date(),substitutePayFeeRequest.getEnd_date());
            List<SunbobTaskUnionpay> sunbobTaskUnionpayList=sunbobTaskUnionpayMapper.selectByExample(example);
            for(SunbobTaskUnionpay sunbobTaskUnionpay:sunbobTaskUnionpayList){
                SubstitutePayFeeData substitutePayFeeData=new SubstitutePayFeeData();
                substitutePayFeeData.setAmt(String.valueOf(sunbobTaskUnionpay.getAmt()));
                substitutePayFeeData.setFee_type(sunbobTaskUnionpay.getTrans_type());
                substitutePayFeeData.setRemark(sunbobTaskUnionpay.getSummary_wn());
                substitutePayFeeData.setTrasn_date(sunbobTaskUnionpay.getTrans_date());
                substitutePayFeeDataList.add(substitutePayFeeData);
            }
        }catch (Exception e){
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException) e).getBaseResponse();
            }else{
                logger.error("【查询由代付等业务产生的手续费扣款情况】："+e);
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            throw new BusinessException(baseResponse);
        }
        return substitutePayFeeDataList;
    }

//    @Override
//    public List<EaccAccountamtlist> accountFlowListQuery(String platcust, String startDate, String endDate) throws BusinessException {
//        EaccAccountamtlistExample example = new EaccAccountamtlistExample();
//        EaccAccountamtlistExample.Criteria criteria = example.createCriteria();
//        criteria.andPlatcustEqualTo(platcust);
//        criteria.andEnabledEqualTo(Constants.ENABLED);
//        criteria.andTrans_dateBetween(startDate,endDate);
//        return eaccAccountamtlistMapper.selectByExample(example);
//    }
}
