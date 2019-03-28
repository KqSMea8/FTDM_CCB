package com.sunyard.sunfintech.pub.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.pub.model.NotifyData;

/**
 * Created by terry on 2017/8/21.
 */
public interface INotifyService {

    public void addNotify(NotifyData notifyData)throws BusinessException;
}
