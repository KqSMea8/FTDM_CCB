package com.sunyard.sunfintech.listcheck.service;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.dao.entity.TransTransreq;

import java.util.List;

/**
 * @author heroy
 * @version 2018/6/20
 */
public interface IListCheckService {

    public boolean doCheck(List<TransTransreq> transTransreqList)throws BusinessException;
}
