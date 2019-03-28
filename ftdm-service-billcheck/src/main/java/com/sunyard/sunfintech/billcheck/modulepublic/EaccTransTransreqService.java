package com.sunyard.sunfintech.billcheck.modulepublic;

import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.dic.IsUse;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.EaccTransTransreqExample;
import com.sunyard.sunfintech.dao.entity.EaccTransTransreqWithBLOBs;
import com.sunyard.sunfintech.dao.mapper.EaccTransTransreqMapper;
import com.sunyard.sunfintech.pub.provider.IEaccTransTransreqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.Date;
import java.util.List;

/**
 * Created by terry on 2017/7/16.
 */
@CacheConfig(cacheNames="eaccTransTransreqService")
@org.springframework.stereotype.Service
public class EaccTransTransreqService implements IEaccTransTransreqService {

    @Autowired
    private EaccTransTransreqMapper eaccTransTransreqMapper;

    @Override
    public boolean addFlow(EaccTransTransreqWithBLOBs eaccTransTransreq) throws BusinessException {
        eaccTransTransreq.setCreate_by(eaccTransTransreq.getMall_no());
        eaccTransTransreq.setCreate_time(new Date());
        eaccTransTransreq.setEnabled(Integer.valueOf(Constants.ENABLED));
        eaccTransTransreqMapper.insert(eaccTransTransreq);
        return true;
    }

    @Override
    public boolean addBatchFlow(List<EaccTransTransreqWithBLOBs> eaccTransTransreqList) throws BusinessException {
        return false;
    }

    @Override
    public EaccTransTransreqWithBLOBs queryFlowByTransSerial(String trans_serial) throws BusinessException {
        EaccTransTransreqExample eaccTransTransreqExample=new EaccTransTransreqExample();
        EaccTransTransreqExample.Criteria criteria=eaccTransTransreqExample.createCriteria();
        criteria.andParent_trans_serialEqualTo(trans_serial);
        criteria.andEnabledEqualTo(Integer.valueOf(IsUse.YES.getCode()));
        List<EaccTransTransreqWithBLOBs> eaccTransTransreq=eaccTransTransreqMapper.selectByExampleWithBLOBs(eaccTransTransreqExample);
        if(eaccTransTransreq.size()>0){
            return eaccTransTransreq.get(0);
        }
        return null;
    }

    @Override
    public List<EaccTransTransreqWithBLOBs> queryFlowByParentTransSerial(String parent_trans_serial) throws BusinessException {
        EaccTransTransreqExample example=new EaccTransTransreqExample();
        EaccTransTransreqExample.Criteria criteria=example.createCriteria();
        criteria.andTrans_serialEqualTo(parent_trans_serial);
        return eaccTransTransreqMapper.selectByExampleWithBLOBs(example);
    }

    @Override
    public boolean updateFlowByPrimaryKey(EaccTransTransreqWithBLOBs eaccTransTransreq) throws BusinessException {
        eaccTransTransreq.setUpdate_time(new Date());
        eaccTransTransreqMapper.updateByPrimaryKeySelective(eaccTransTransreq);
        return true;
    }

    @Override
    public boolean updateBatchFlowByPrimaryKey(List<EaccTransTransreqWithBLOBs> eaccTransTransreq) throws BusinessException {
        return false;
    }

    @Override
    public boolean updateFlowByTransSerial(String trans_serial, EaccTransTransreqWithBLOBs eaccTransTransreq) throws BusinessException {
        return false;
    }
}
