package com.sunyard.sunfintech.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.OrderStatus;
import com.sunyard.sunfintech.core.dic.PayStatus;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.FtpUtils;
import com.sunyard.sunfintech.core.util.URLConfigUtil;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.*;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.user.provider.IPlatCacheService;
import com.sunyard.sunfintech.user.provider.IPlatTransService;
import com.sunyard.sunfintech.user.utils.PropertiesUtil;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KouKi on 2017/6/22.
 */
@Service(interfaceClass = IPlatTransService.class)
@CacheConfig(cacheNames="platTransService")
@org.springframework.stereotype.Service("platTransService")
public class PlatTransService extends BaseServiceSimple implements IPlatTransService {

    @Autowired
    private IPlatCacheService platCacheService;

    @Autowired
    private ISysParameterService sysParameterService;

    @Override
    public Boolean makePlatClearCT(String clear_date, String plat_no) throws BusinessException {
        logger.info("====================对账开始：start 生成平台充值提现出入金流水====================");
        List<RwRecharge> rechargeList = platCacheService.queryPlatRecharge(plat_no, clear_date);
        List<RwWithdraw> withdrawList = platCacheService.queryPlatWithdraw(plat_no, clear_date);
        StringBuilder sb = new StringBuilder();
        //添加汇总信息
        sb.append(plat_no).append("\\|").append(clear_date).append("\\|")
                .append(rechargeList.size() + withdrawList.size()).append("\n");
        //添加充值信息
        rechargeList.forEach(rwRecharge ->
                sb.append(plat_no).append("\\|").append(rwRecharge.getTrans_date()).append("\\|")
                        .append(rwRecharge.getTrans_time()).append("\\|").append(rwRecharge.getOrder_no())
                        .append("\\|").append(rwRecharge.getTrans_amt()).append("\\|").append("C").append("\n"));
        //添加提现信息
        withdrawList.forEach(rwWithdraw ->
                sb.append(plat_no).append("\\|").append(rwWithdraw.getTrans_date()).append("\\|")
                        .append(rwWithdraw.getTrans_time()).append("\\|").append(rwWithdraw.getOrder_no())
                        .append("\\|").append(rwWithdraw.getOut_amt()).append("\\|").append("T").append("\n"));
        //添加结束标识
        sb.append("ENDFLAG");

        try {
            String fileDirName = "/home/fund/ftdm/billcheck/uploadFiles/" + "PLAT_DATA/" + plat_no;
            String fileName = "PLAT_DATA_" + clear_date + ".txt";
            File file = new File(fileDirName, fileName);
            if (file.exists()) { // 判断文件或文件夹是否存在
                logger.info(file.getName() + " 已经存在");
            } else {
                try {
                    file.getParentFile().mkdirs();
                    file.createNewFile(); // 创建文件或者文件夹
                    logger.info(file.getName() + " 创建成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String addr = PropertiesUtil.getVal("addr");
            String username = PropertiesUtil.getVal("username");
            String password = PropertiesUtil.getVal("password");
            FtpUtils f = new FtpUtils(addr, 21, username, password);
            FileUtils.writeStringToFile(file, sb.toString(), "utf-8", false);
            logger.info("====================本地文件写成功====================");
            if (f.open()) {
                logger.info("====================连接FTP成功！！！====================");
                String plat_ftp_name = sysParameterService.querySysParameter(plat_no, URLConfigUtil.PLAT_FTP_NAME);
                String ftp_path = "/home/ftp/" + plat_ftp_name + "/" + plat_no;
                f.changeToParentDir();
                f.upload(fileDirName + "/" + fileName, "PLAT_DATA_" + clear_date + ".txt", ftp_path);
                f.close();
                logger.info("====================FTP文件写成功====================");
            } else {
                logger.info("====================连接FTP失败！！！====================");
            }
            logger.info("====================对账：end 生成平台充值提现出入金流水====================");
            return true;
        } catch (Exception e) {
            logger.error("写入ftp文件失败：" + e);
            return false;
        }
    }
}
