package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.AccountSubjectInfo;
import com.sunyard.sunfintech.dao.entity.EaccCardinfo;
import com.sunyard.sunfintech.dao.entity.EaccUserauth;
import com.sunyard.sunfintech.dao.entity.EaccUserinfo;
import com.sunyard.sunfintech.user.model.bo.*;

import java.util.List;

/**
 * Created by terry on 2017/7/10.
 */
public interface IUserAccountExtService {

    public String userRegister(BaseRequest baseRequests, BatchRegisterRequestDetail batchRegisterRequestDetail)throws BusinessException;

    public String authentication(BatchAuthenticationRequestDetail batchAuthenticationRequestDetail, BaseRequest baseRequest)throws BusinessException;

    public void delEaccAccount(String platcust, String mall_no)throws BusinessException;
    /**
     * 批量更新授权列表
     * @param eaccUserauths
     */
    void updateEaccUserAuth(List<EaccUserauth> eaccUserauths);
    void deleteEaccUserAuth(List<EaccUserauth> eaccUserauths);
    void updateAccountStatus(EaccUserinfo eaccUserinfo, List<AccountSubjectInfo> subjectInfoList, List<EaccCardinfo> eaccCardinfoList, String trans_type)throws BusinessException;
    /**
     * 缴费
     *
     * @param payFeeRequest
     * @return
     */
    BaseResponse payFee(PayFeeRequest payFeeRequest) throws BusinessException;

    /**
     * 授权缴费
     *
     * @param confirmAuthOperaRequest
     * @return
     */
    AuthPayFeeResponse authPayFee(AuthPayFeeRequest confirmAuthOperaRequest)  throws BusinessException;

    /**
     * 取消缴费
     *
     * @param cancelAuthOperaRequest
     * @return
     */
    BaseResponse cancelPayFee(CancelPayFeeRequest cancelAuthOperaRequest)  throws BusinessException;

    void unBindCard(List<EaccCardinfo> eaccCardInfos);
}
