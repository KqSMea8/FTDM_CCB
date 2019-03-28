package com.sunyard.sunfintech.web.controller;

import com.sunyard.sunfintech.core.annotation.Log;
import com.sunyard.sunfintech.core.annotation.Sign;
import com.sunyard.sunfintech.core.base.BaseController;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.LocalCacheUtil;
import com.sunyard.sunfintech.core.util.ParamterUtil;
import com.sunyard.sunfintech.core.util.SecurityUtil;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.web.business.SysBusiness;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author heroy
 * @version 2018/1/2
 */
@RestController
@RequestMapping("/sys")
public class SysController extends BaseController {

    @Autowired
    private SysBusiness sysBusiness;


    /**
     * 获取全部的系统参数
     * @return 系统参数列表
     */
    @RequestMapping("/getsysparams")
    @Sign
    @Log(method = "getSysParams")
    public Map<String, Object> getSysParams(){
        return LocalCacheUtil.locaCache;
    }

    @RequestMapping("/modifysysparams")
    @Sign
    @Log(method = "modifySysParams", transCode = "SYS00001")
    public BaseResponse modifySysParams(HttpServletRequest httpServletRequest, BaseRequest baseModel){
        BaseResponse baseResponse = new BaseResponse();
        try {
            Map<String, Object> param = ParamterUtil.getParamterMap(httpServletRequest);
            String mall_no = MapUtils.getString(param, "mall_no", "");
            String mer_no = MapUtils.getString(param, "mer_no", "");
            String parameter_key = MapUtils.getString(param, "parameter_key", "");
            String parameter_value = MapUtils.getString(param, "parameter_value", "");

            if(StringUtils.isBlank(mall_no)||StringUtils.isBlank(mer_no)
                    ||StringUtils.isBlank(parameter_key)||StringUtils.isBlank(parameter_value)){
                baseResponse.setRecode(BusinessMsg.FAIL);
                baseResponse.setRemsg("缺少必要参数，mall_no，mer_no，parameter_key，parameter_value");
            }

            if(isAuth(param)) {
                sysBusiness.modifySysParams(param);
                baseResponse.setRecode(BusinessMsg.SUCCESS);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            }else{
                baseResponse.setRecode(BusinessMsg.FAIL);
                baseResponse.setRemsg("没有授权");
            }
            return baseResponse;
        }catch (Exception e){
            baseResponse.setRecode(BusinessMsg.FAIL);
            baseResponse.setRemsg(e.getMessage());
        }
        return baseResponse;
    }

    private boolean isAuth(Map<String, Object> param){
        String auth = MapUtils.getString(param, "sys_auth_key", "");

        if(StringUtils.isNotBlank(auth) && SecurityUtil.decryptDes(auth).equals(sysBusiness.querySysParameter("FTDM","sys_auth_key"))){
            logger.info("【系统参数接口】: " + auth);
            return true;
        }
        return true;
    }


}
