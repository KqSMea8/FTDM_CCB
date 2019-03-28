//package com.sunyard.mock;
//
//import com.sunyard.sunfintech.account.provider.IAccountTransferService;
//import com.sunyard.sunfintech.core.Constants;
//import com.sunyard.sunfintech.core.dic.SwitchState;
//import com.sunyard.sunfintech.core.exception.BusinessException;
//import com.sunyard.sunfintech.core.util.DateUtils;
//import com.sunyard.sunfintech.core.util.SeqUtil;
//import com.sunyard.sunfintech.core.util.StringUtils;
//import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
//import com.sunyard.sunfintech.dao.entity.EaccTransTransreqWithBLOBs;
//import com.sunyard.sunfintech.dao.mapper.EaccAccountamtlistMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.math.BigDecimal;
//import java.util.List;
//
///**
// * Created by djh on 2017/12/6.
// */
//public class AccountTransferServiceMock implements IAccountTransferService{
//
//    @Autowired
//    private EaccAccountamtlistMapper eaccAccountamtlistMapper;
//
//    @Override
//    public List<EaccTransTransreqWithBLOBs> doTransfer(EaccAccountamtlist eaccAccountamtlist) throws BusinessException {
//        return null;
//    }
//
//    @Override
//    public Boolean transfer(EaccAccountamtlist eaccAccountamtlist) throws BusinessException {
//        return null;
//    }
//
//    @Override
//    public boolean batchTransfer(List<EaccAccountamtlist> eaccAccountamtlist) throws BusinessException {
//        return false;
//    }
//
//    @Override
//    public List<EaccTransTransreqWithBLOBs> doTransferRollBack(EaccAccountamtlist eaccAccountamtlist) throws BusinessException {
//        return null;
//    }
//
//    @Override
//    public Boolean transferRollBack(EaccAccountamtlist eaccAccountamtlist) throws BusinessException {
//        return null;
//    }
//
//    @Override
//    public boolean batchTransferRollBack(List<EaccAccountamtlist> eaccAccountamtlists) throws BusinessException {
//        return false;
//    }
//
//    @Override
//    public boolean fundTransfer(EaccAccountamtlist eaccAccountamtlist) throws BusinessException {
//        return false;
//    }
//
//    @Override
//    public boolean batchFundTransfer(List<EaccAccountamtlist> eaccAccountamtlist) throws BusinessException {
//        return false;
//    }
//
//    @Override
//    public void addTransFlow(EaccAccountamtlist eaccAccountamtlist) throws BusinessException {
//        eaccAccountamtlist.setCreate_time(DateUtils.today());
//        //查看交易流水是否为空，为空则生成新的流水编号
//        if(StringUtils.isEmpty(eaccAccountamtlist.getTrans_serial())){
//            eaccAccountamtlist.setTrans_serial(SeqUtil.getSeqNum());
//        }
//        //查看交易日期是否为空，为空则填充当前日期
//        if(StringUtils.isEmpty(eaccAccountamtlist.getTrans_date())){
//            eaccAccountamtlist.setTrans_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_STRING_DATE));
//        }
//        //查看交易时间是否为空，为空则填充当前时间
//        if(StringUtils.isEmpty(eaccAccountamtlist.getTrans_time())){
//            eaccAccountamtlist.setTrans_time(DateUtils.getTime());
//        }
//        if(StringUtils.isEmpty(eaccAccountamtlist.getAccount_date())){
//            eaccAccountamtlist.setAccount_date(DateUtils.getyyyyMMddDate());
//        }
//        eaccAccountamtlist.setEnabled(Constants.ENABLED);
//        if(StringUtils.isEmpty(eaccAccountamtlist.getSwitch_state())){
//            eaccAccountamtlist.setSwitch_state(SwitchState.NOSWITCH.getCode());
//        }
//        if(eaccAccountamtlist.getSwitch_amt()==null){
//            eaccAccountamtlist.setSwitch_amt(BigDecimal.ZERO);
//        }
//        //添加流水
//        eaccAccountamtlistMapper.insert(eaccAccountamtlist);
//    }
//
//    @Override
//    public void updateTransFlow(EaccAccountamtlist eaccAccountamtlist) throws BusinessException {
//
//    }
//
//    @Override
//    public EaccAccountamtlist queryTransFlow(EaccAccountamtlist eaccAccountamtlist) throws BusinessException {
//        return null;
//    }
//
//    @Override
//    public List<EaccAccountamtlist> queryTransFlowList(EaccAccountamtlist eaccAccountamtlist) throws BusinessException {
//        return null;
//    }
//
//    @Override
//    public boolean queryEaccAccountamtlistByTransSerialAndPlatcust(String trans_serial) throws BusinessException {
//        return false;
//    }
//
//    @Override
//    public boolean queryEaccAccountamtlistByTransSerial(String trans_serial, String df_trans_date) throws BusinessException {
//        return false;
//    }
//
//    @Override
//    public boolean refundBack(String inAccount, String outAccount) throws BusinessException {
//        return false;
//    }
//
//    @Override
//    public void transactionTest(String platcust, BigDecimal n_balance) throws BusinessException {
//
//    }
//
//    @Override
//    public List<EaccAccountamtlist> queryEaccAccountamtlistByTransSerial(String trans_serial) throws BusinessException {
//        return null;
//    }
//}
