package com.sunyard.sunfintech.billcheck.provider;

import com.sunyard.sunfintech.billcheck.model.bo.SortingRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wubin on 2017/6/17.
 */
public interface IBillCheckService {

    /**
     * 生成支付渠道资金进出数据
     * 汇总：平台编号|对账日期|笔数|总金额
     * 明细：平台编号|交易日期|交易时间|订单号|金额|C-0，T-1|支付编号|流水号
     * @param plat_no
     * @param clear_date
     * @return
     * @throws BusinessException
     */
    Boolean makePayCodeClearCT(String plat_no, String clear_date) throws BusinessException;

    /**
     * 获取未确认的提现订单;处理中，提现时间前一天，年月日，存到未确认订单表
     * @param clear_date
     * @param plat_no
     * @return
     * @throws BusinessException
     */
    Boolean getNoConfim(String clear_date, String plat_no) throws BusinessException;

    /**
     * 生成账户余额对账文件
     * 汇总：平台编号|对账日期|总金额|可用|冻结
     * 明细：平台编号|科目|子科目|总金额|可用金额|冻结金额，平台编号
     * @param plat_no
     * @return
     * @throws BusinessException
     */
    Boolean makeAccountBalance(String plat_no) throws BusinessException;

    /**
     * 生成电子账户对账文件
     * 汇总：集团编号|对账日期|总金额|可用|冻结
     * 明细：集团编号|科目|子科目|总金额|可用金额|冻结金额，平台编号
     * @param plat_no
     * @return
     * @throws BusinessException
     */
    Boolean makeElcAccountBalance(String plat_no) throws BusinessException;

    /**
     * 日终统计
     * @param clear_date
     * @return
     * @throws BusinessException
     */
    //日终统计 createClearList
    Boolean countClearList(String clear_date, String plat_no) throws BusinessException;

    Boolean countEaccAccountBalance(String mall_no) throws BusinessException;

    /**
     * 生成电子账户出入金流水
     * 集团编号|时间|笔数
     * 集团编号|日期|时间|platcust|科目|子科目|金额|C-0，T-1
     * @param accType
     * @param clear_date
     * @return
     * @throws BusinessException
     */
    Boolean makeElcAccountTrans(String mall_no, String accType, String clear_date)throws BusinessException;

    /**
     * 生成账户出入金流水
     * 集团编号|时间|笔数
     * 集团编号|日期|时间|platcust|科目|子科目|金额|C-0，T-1
     * @param clear_date
     * @return
     * @throws BusinessException
     */
    Boolean makeAccountTrans(String mall_no, String clear_date)throws BusinessException;

    public BaseResponse requestSortingFile(SortingRequest sortingRequest) throws BusinessException;

    public BaseResponse checkClearResult(SortingRequest sortingRequest) throws BusinessException;

    Boolean checkBankByElc(String date) throws BusinessException;

    List<ClearResult> getClearResult(String mall_no, String clear_date) throws BusinessException;


    /**
     * 根据Id修改clear_result
     * @param clearResult
     */
    void updateClearResultById(ClearResult clearResult);

    /**
     * 查询三方对账汇总表
     * @param emx
     * @return
     */
    List<ClearCheckinfoEmx> queryClearCheckinfoEmx(ClearCheckinfoEmx emx);

    /**
     * 插入汇总表
     * @param totalMap
     */
    void addClearInfoMx(HashMap<String, Object> totalMap);

    /**
     * 删除clear_result数据
     * @param clear_date
     * @param pay_code
     * @param plat_no
     */
    void deleteClearInfoByDate(String clear_date, String pay_code, String plat_no);

    /**
     *  插入clear_result
     * @param cutList
     */
    void addClearInfoLIst(List<HashMap<String, Object>> cutList);

    /**
     * 查询外部充值数据
     * @param emxlist
     * @return
     */
    List<ClearCheckinfoEmxlist> queryEmxListCharge(ClearCheckinfoEmxlist emxlist);

    /**
     * 查询外部提现数据
     * @param emxlist
     * @return
     */
    List<ClearCheckinfoEmxlist> queryEmxListWithdraw(ClearCheckinfoEmxlist emxlist);

    /**
     * 查询差错数据
     * @param checkError
     * @return
     */
    List<ClearCheckError> selectClearCheckError(ClearCheckError checkError);

    /**
     * 插入差错表
     * @param clearCheckError
     */
    void insertClearError(ClearCheckError clearCheckError);

    /**
     * 查询clear_result数据
     * @param clearResult
     * @return
     */
    List<ClearResult> selectUnCheckedClearResult(ClearResult clearResult, List<String> platNos);

    /**
     * 查询资金流水
     * @param eaccAccountamtlist
     * @return
     */
    List<EaccAccountamtlist> selectAmtlist(EaccAccountamtlist eaccAccountamtlist);

    /**
     * 对账成功后通知中间平台
     * @param mall_no
     * @param clear_date
     */
    boolean billCheckNotify(String mall_no, String clear_date);

    /**
     * 更新通知中间平台重试记录
     * @param billCheckNotifyRetry
     * @return
     */
    int updateBillCheckNotifyRetry(BillCheckNotifyRetry billCheckNotifyRetry);

    /**
     * 查询通知中间平台重试记录
     * @param notify_status
     * @return
     */
    List<BillCheckNotifyRetry> findBillCheckNotifyRetry(String notify_status);

    /**
     * 内部对账
     * @param clearDate
     * @return
     */
    BaseResponse checkCTList(Date clearDate);

}
