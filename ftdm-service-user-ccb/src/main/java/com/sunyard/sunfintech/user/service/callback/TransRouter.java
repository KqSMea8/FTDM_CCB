package com.sunyard.sunfintech.user.service.callback;

import com.sunyard.sunfintech.dao.entity.TransTransreq;

/**
 * 【功能描述】
 *
 * @author wyc  2018/2/11.
 */
public interface TransRouter {

    /**
     * user模块业务流水状态回执
     * @param transTransreq
     */
    public void onCallBack(TransTransreq transTransreq);
}
