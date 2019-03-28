package com.sunyard.sunfintech.billcheck.service;

import com.sunyard.sunfintech.billcheck.provider.IAccountTransferService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.SwitchState;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import com.sunyard.sunfintech.dao.mapper.EaccAccountamtlistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by terry on 2018/3/24.
 */
@Service("accountTransferService")
public class AccountTransferService extends BaseServiceSimple implements IAccountTransferService {

    @Autowired
    private EaccAccountamtlistMapper eaccAccountamtlistMapper;

    @Override
    public void addTransFlow(EaccAccountamtlist eaccAccountamtlist) throws BusinessException {
        eaccAccountamtlist.setCreate_time(DateUtils.today());
        //查看交易流水是否为空，为空则生成新的流水编号
        if(StringUtils.isEmpty(eaccAccountamtlist.getTrans_serial())){
            eaccAccountamtlist.setTrans_serial(SeqUtil.getSeqNum());
        }
        //查看交易日期是否为空，为空则填充当前日期
        if(StringUtils.isEmpty(eaccAccountamtlist.getTrans_date())){
            eaccAccountamtlist.setTrans_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_STRING_DATE));
        }
        //查看交易时间是否为空，为空则填充当前时间
        if(StringUtils.isEmpty(eaccAccountamtlist.getTrans_time())){
            eaccAccountamtlist.setTrans_time(DateUtils.getTime());
        }
        if(null == eaccAccountamtlist.getAccount_date()){
            eaccAccountamtlist.setAccount_date(new Date());
        }
        eaccAccountamtlist.setEnabled(Constants.ENABLED);
        if(StringUtils.isEmpty(eaccAccountamtlist.getSwitch_state())){
            eaccAccountamtlist.setSwitch_state(SwitchState.NOSWITCH.getCode());
        }
        if(eaccAccountamtlist.getSwitch_amt()==null){
            eaccAccountamtlist.setSwitch_amt(BigDecimal.ZERO);
        }
        //添加流水
        //logger.info("【插入交易流水】eaccAccountamtlist："+ JSON.toJSONString(eaccAccountamtlist));
        eaccAccountamtlistMapper.insert(eaccAccountamtlist);
    }
}
