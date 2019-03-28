package com.sunyard.sunfintech.core.base;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by heroy on 2017/4/6.
 * @version 20170406
 */
public abstract class BaseBusiness<T extends BaseProvider> {
    @Autowired
    protected T provider;

    public abstract java.lang.String getService();
}
