package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.user.model.bo.PlatfromToPersonData;

/**
 * Created by PengZY on 2017/10/17.
 */
public interface IPlatfromToPersonMQService {

    public void addPlatfromToPerson(PlatfromToPersonData platfromToPersonData)throws BusinessException;

    public void doPlatfromToPerson(TransTransreq transTransreq)throws BusinessException;

}
