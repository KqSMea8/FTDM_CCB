package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.dao.entity.*;

import java.util.List;

/**
 * Created by Lid on 2017/6/22.
 */
public interface IPlatCacheService {
    /**
     * 平台卡信息
     * @param plat_no
     * @param card_type
     * @return value
     */
    public String queryCardInfoToCache(String plat_no, String card_type);

    /**
     * 查询所有的平台号
     * @return
     */
    List<PlatPlatinfo> queryAllPlatInfo();

    /**
     * 查询对应平台的paycode
     * @param plat_no
     * @return
     */
    List<PlatPaycode> queryTransactionCode(String plat_no);

    /**
     * 查询平台充值
     * @param plat_no
     * @param paymentDate
     * @return
     */
    List<RwRecharge> queryPlatRecharge(String plat_no, String paymentDate);

    /**
     * 查询平台提现
     * @param plat_no
     * @param clear_date
     * @return
     */
    List<RwWithdraw> queryPlatWithdraw(String plat_no, String clear_date);
}
