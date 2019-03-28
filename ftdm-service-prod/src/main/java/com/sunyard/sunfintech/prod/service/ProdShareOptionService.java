package com.sunyard.sunfintech.prod.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.dic.IsUse;
import com.sunyard.sunfintech.core.dic.ProdState;
import com.sunyard.sunfintech.core.dic.ShareStatus;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.dao.entity.ProdShareInall;
import com.sunyard.sunfintech.dao.entity.ProdShareInallExample;
import com.sunyard.sunfintech.dao.entity.ProdShareList;
import com.sunyard.sunfintech.dao.entity.ProdShareListExample;
import com.sunyard.sunfintech.dao.mapper.ProdShareInallMapper;
import com.sunyard.sunfintech.dao.mapper.ProdShareListMapper;
import com.sunyard.sunfintech.prod.provider.IProdShareOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.Date;
import java.util.List;

/**
 * Created by terry on 2017/5/6.
 */
@Service(interfaceClass = IProdShareOptionService.class)
@CacheConfig(cacheNames="prodShareOptionService")
//@org.springframework.stereotype.Service
public class ProdShareOptionService implements IProdShareOptionService {

    @Autowired
    private ProdShareListMapper prodShareListMapper;

    @Autowired
    private ProdShareInallMapper prodShareInallMapper;

    @Override
    public void addShare(ProdShareList prodShareList) throws BusinessException {
        if (null == prodShareList.getCreate_by()) prodShareList.setCreate_by(SeqUtil.RANDOM_KEY);
        if(null == prodShareList.getCreate_time()) prodShareList.setCreate_time(new Date());
        prodShareListMapper.insert(prodShareList);
    }

    @Override
    public ProdShareInall queryProdShareInall(ProdShareInall prodShareInall) throws BusinessException {

        ProdShareInallExample example=new ProdShareInallExample();
        ProdShareInallExample.Criteria criteria=example.createCriteria();
        criteria.andEnabledEqualTo(Constants.ENABLED);
        criteria.andPlatcustEqualTo(prodShareInall.getPlatcust());
        criteria.andPlat_noEqualTo(prodShareInall.getPlat_no());
        criteria.andProd_idEqualTo(prodShareInall.getProd_id());
        example.or(criteria);
        List<ProdShareInall> prodShareInallList= prodShareInallMapper.selectByExample(example);
        if(prodShareInallList.size()>0){
            return prodShareInallList.get(0);
        }
        return null;
    }

    @Override
    public boolean updateProdShareInall(ProdShareInall prodShareInall) throws BusinessException {
        if(null == prodShareInall.getUpdate_by()) prodShareInall.setUpdate_by(SeqUtil.RANDOM_KEY);
        if(null == prodShareInall.getUpdate_time()) prodShareInall.setUpdate_time(new Date());
        int rows=prodShareInallMapper.updateByPrimaryKeySelective(prodShareInall);
        if(rows>0){
            return true;
        }
        return false;
    }

    @Override
    public List<ProdShareList> queryProdShareList(String plat_no, String prod_id, String platcust) throws BusinessException {
        ProdShareListExample example=new ProdShareListExample();
        ProdShareListExample.Criteria criteria=example.createCriteria();
        criteria.andPlat_noEqualTo(plat_no);
        criteria.andProd_idEqualTo(prod_id);
        criteria.andPlatcustEqualTo(platcust);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        return prodShareListMapper.selectByExample(example);
    }
}
