package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.account.model.bo.BatchPayQueryResponseDataDetail;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.ProdCompensationInfo;
import com.sunyard.sunfintech.dao.entity.ProdCompensationRepay;

import java.util.List;


public interface IProdCompensationInfoService {

	boolean addCompensationInfo(ProdCompensationInfo compensationInfo) throws BusinessException;

	boolean addCompensationRepay(ProdCompensationRepay prodCompensationRepay) throws BusinessException;
}
