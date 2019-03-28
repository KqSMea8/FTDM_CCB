package com.sunyard.sunfintech.web.security;

import cn.org.bjca.client.security.SecurityEngineDeal;
import com.sunyard.sunfintech.core.support.security.SignAdapter;
import com.sunyard.sunfintech.core.util.SpringContextHolder;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 北京银行验签
 * @author heroy
 * @version 2017/6/16
 */
@Service("BOBSign")
public class BOBSign implements SignAdapter {

    private final Logger logger = LogManager.getLogger();

    @Autowired
    private ISysParameterService sysParameterService;

    private String profilePath;

    public BOBSign(){
        super();
        if(null == sysParameterService)
            sysParameterService = SpringContextHolder.getBean("sysParameterService");
    }

    @Override
    public String signUp(String mall_no,String destContent, Map<String,String> params) {
        try {
            //初始化DSVS环境，需要先进行配置文件的配置，并放在指定目录下，注意必须是BJCAROOT这个文件夹
            logger.info("加签集团号：" + mall_no);
            if(StringUtils.isEmpty(mall_no)){
                mall_no = "FTDM";
            }
            String profilePath = sysParameterService.querySysParameter(mall_no,"pfx_file_path");
            logger.info("加签集团号:" + mall_no);
            SecurityEngineDeal.setProfilePath(profilePath);
            SecurityEngineDeal sed = SecurityEngineDeal.getInstance("SVSDefault");
            // 获得DSVS的签名证书，可以理解为银行端使用这个证书进行数字签名，可根据业务需要传送给用户验签
            // 对原文进行数字签名
            logger.info("加签原文："+destContent);
            logger.info("加签串："+URLEncoder.encode(sed.signData(destContent),"UTF-8"));

            return URLEncoder.encode(sed.signData(destContent),"UTF-8");
        } catch (Exception e) {
            logger.error("加签：",e);
        }
        return "sign";

    }

    @Override
    public boolean checkSign(String mall_no, String signData, String destContent, Map<String,String> params) {
        try {
            //初始化DSVS环境，需要先进行配置文件的配置，并放在指定目录下，注意必须是BJCAROOT这个文件夹
            String profilePath = "";
            String pfxPublic = "";
            if("SDK".equals(mall_no)){
                profilePath = sysParameterService.querySysParameter("FTDM", "pfx_file_path");
                pfxPublic = sysParameterService.querySysParameter("FTDM", "SDK");
            }else {
                profilePath = sysParameterService.querySysParameter(mall_no, "pfx_file_path");
                pfxPublic = sysParameterService.querySysParameter(mall_no, "pfx_public");
            }
            SecurityEngineDeal.setProfilePath(profilePath);
            SecurityEngineDeal sed = SecurityEngineDeal.getInstance("SVSDefault");
            // 验签操作，结果为true或false
            logger.info("签名原文:"+ destContent);
            logger.info("签名："+signData);
            //替换所有的回车，SDK会上送多余的回车换行
            signData = URLDecoder.decode(signData,"UTF-8").replaceAll("(\r\n|\r|\n|\n\r)","");
            return sed.verifySignedData(pfxPublic, destContent, signData);
        } catch (Exception e) {
            logger.error("验签异常：",e);

        }
        return false;
    }

}
