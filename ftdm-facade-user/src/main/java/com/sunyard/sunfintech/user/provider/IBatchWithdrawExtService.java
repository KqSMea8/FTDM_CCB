package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.AccountSubjectInfo;
import com.sunyard.sunfintech.user.model.bo.BatchWithdrawRequest;
import com.sunyard.sunfintech.user.model.bo.DateDetail;
import com.sunyard.sunfintech.user.model.bo.SuccessData;

/**
 * Created by PengZY on 2018/1/11.
 */
public interface IBatchWithdrawExtService {

    /**
     * 批量提现
     * @param plat_account   手续费现金账户
     * @param plat_account_inline   行内垫资现金账户
     * @param plat_account_inline_float   行内垫资在途账户
     * @param dateDetail   批量订单明细
     * @return SuccessData
     * @author pzy
     */
    public SuccessData transfer(AccountSubjectInfo plat_account,AccountSubjectInfo plat_account_ploat, AccountSubjectInfo plat_account_inline, AccountSubjectInfo plat_account_inline_float, DateDetail dateDetail, BaseRequest baseRequest)throws BusinessException;

}
