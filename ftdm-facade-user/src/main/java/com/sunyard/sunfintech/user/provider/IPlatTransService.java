package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;

/**
 * Created by KouKi on 2017/6/22.
 */
public interface IPlatTransService {

    /**
     * 生成怕你个头出入金对账文件
     * @param clear_date
     * @param plat_no
     * @return
     * @throws BusinessException
     */
    Boolean makePlatClearCT(String clear_date, String plat_no) throws BusinessException;
}
