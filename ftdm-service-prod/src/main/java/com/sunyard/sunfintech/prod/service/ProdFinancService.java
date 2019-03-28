package com.sunyard.sunfintech.prod.service;

import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.custdao.mapper.CustMonitorFinancBalanceMapper;
import com.sunyard.sunfintech.dao.entity.MonitorFinancBalance;
import com.sunyard.sunfintech.dao.entity.PlatPlatinfo;
import com.sunyard.sunfintech.prod.provider.IProdFinancService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KouKi on 2017/7/9.
 */
@CacheConfig(cacheNames = "prodFinancService")
@org.springframework.stereotype.Service
public class ProdFinancService extends BaseServiceSimple implements IProdFinancService {
    @Autowired
    private CustMonitorFinancBalanceMapper custMonitorFinancBalanceMapper;

    /**
     * 统计融资人单笔待还份额
     *
     * @param monitorDate
     * @return
     */
    @Override
    public void makeProdFinancBalance(String monitorDate, List<PlatPlatinfo> platPlatinfoList) throws BusinessException {
        Map<String, Object> map = new HashMap<>();
        map.put("monitor_date", monitorDate);
        platPlatinfoList.forEach(platPlatinfo -> {
            map.put("mall_no", platPlatinfo.getMall_no());
            map.put("plat_no", platPlatinfo.getPlat_no());
            try {
                logger.info("【生成融资人待还余额】开始查询数据");
                List<MonitorFinancBalance> monitorFinancBalanceList = custMonitorFinancBalanceMapper.getMonitorFinancBalance(map);
                logger.info("【生成融资人待还余额】数据查询完毕，开始插入数据");
                //每10000笔记录插入一次
                int size = 10000;//插入一次大小
                int count = (int) Math.ceil(monitorFinancBalanceList.size() / (float) size);//总共插入次数
                for (int i = 0; i < count; i++) {
                    int firstIndex = i * size;//截取开始位置
                    int lastIndex;//截取结束位置
                    if (i == count - 1) {
                        lastIndex = monitorFinancBalanceList.size();
                    } else {
                        lastIndex = (i + 1) * size;
                    }
                    List<MonitorFinancBalance> cutList = monitorFinancBalanceList.subList(firstIndex, lastIndex);
                    custMonitorFinancBalanceMapper.insertMonitorFinancBalance(cutList);
                    logger.info("【生成融资人待还余额】单挑数据插入完毕，循环次数：" + i + 1);
                }
                logger.info("【生成融资人待还余额】总数据插入完毕");
            } catch (Exception e) {
                logger.info("【生成融资人待还余额】异常", e);
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION);
            }
        });
    }
}
