package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.RwRecharge;

import java.util.List;

/**
 * Created by terry on 2017/12/8.
 */
public interface IRechargeOptionService {

    /**
     * 执行充值加款。
     * 对于充值成功的，更新充值订单状态为成功（快捷充值确认会同步更新确认订单状态），更新充值状态为成功，加款，发送短信；
     * 对于充值失败的，更新充值订单状态为失败（快捷充值确认会同步更新确认订单状态），更新充值状态为失败；
     * 对于处理中的，更新充值订单状态为处理中，更新充值状态未处理中。
     *
     * @param rwRecharge 充值数据，该参数为需要update的参数，快捷充值确认要求传快捷充值确认的trans_code
     * @param mall_no 集团号
     * @param apply_order_no 快捷确认订单号
     * @param notes      接口来源，例如：快捷充值确认同步接口、快捷充值异步回调、充值订单状态查询
     * @return true:成功 false:其他线程在加款中，不处理，当处理中处理
     * @throws BusinessException 异常
     */
    boolean doRecharge(RwRecharge rwRecharge, String mall_no, String apply_order_no, String notes)throws BusinessException;

    /**
     * 查询处理中的充值记录
     * @param limit
     * @return
     * @throws BusinessException
     */
    List<RwRecharge> queryProcessingRecharge(Integer limit) throws BusinessException;

    public List<RwRecharge> querySuccessRecharge(String date) throws BusinessException;

}
