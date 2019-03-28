package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.dao.entity.SysParameter;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;

import java.util.List;

/**
 *
 * @author heroy
 * @version 2018/1/17
 */
public interface IUserSysParameterService extends ISysParameterService{
    public List<SysParameter> queryAllUserSysParameters();
}
