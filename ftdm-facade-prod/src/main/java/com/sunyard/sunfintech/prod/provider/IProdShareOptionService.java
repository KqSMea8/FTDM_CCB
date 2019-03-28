package com.sunyard.sunfintech.prod.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.ProdShareInall;
import com.sunyard.sunfintech.dao.entity.ProdShareList;

import java.util.List;

/**
 * Created by terry on 2017/4/28.
 */
public interface IProdShareOptionService {

    /**
     * 新增购买明细
     * @param prodShareList
     * @throws BusinessException
     */
    public void addShare(ProdShareList prodShareList) throws BusinessException;

    public ProdShareInall queryProdShareInall(ProdShareInall prodShareInall)throws BusinessException;

    public boolean updateProdShareInall(ProdShareInall prodShareInall)throws BusinessException;

    public List<ProdShareList> queryProdShareList(String plat_no,String prod_id,String platcust)throws BusinessException;
}
