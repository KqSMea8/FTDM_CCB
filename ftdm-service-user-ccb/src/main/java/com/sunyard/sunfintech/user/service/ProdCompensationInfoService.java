package com.sunyard.sunfintech.user.service;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.ProdCompensationInfo;
import com.sunyard.sunfintech.dao.entity.ProdCompensationRepay;
import com.sunyard.sunfintech.dao.mapper.ProdCompensationInfoMapper;
import com.sunyard.sunfintech.dao.mapper.ProdCompensationRepayMapper;
import com.sunyard.sunfintech.user.provider.IProdCompensationInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by terry on 2018/2/2.
 */
@Service
public class ProdCompensationInfoService implements IProdCompensationInfoService {
    @Autowired
    private ProdCompensationInfoMapper prodCompensationInfoMapper;

    @Autowired
    private ProdCompensationRepayMapper prodCompensationRepayMapper;

    @Override
    public boolean addCompensationInfo(ProdCompensationInfo compensationInfo ) throws BusinessException {
        prodCompensationInfoMapper.insert(compensationInfo);
        return true;
    }

    @Override
    public boolean addCompensationRepay (ProdCompensationRepay prodCompensationRepay) throws BusinessException {
        prodCompensationRepayMapper.insert(prodCompensationRepay);
        return true;
    }
}
