package com.sunyard.sunfintech.custdao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sunyard.sunfintech.dao.entity.AccountSubjectInfo;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CustAccountSubjectInfoMapper extends BaseMapper<AccountSubjectInfo> {
    /**
     * @mbggenerated 2017-04-20
     */
    Integer insert(AccountSubjectInfo record);

    /**
     * @mbggenerated 2017-04-20
     */
    AccountSubjectInfo selectByPrimaryKey(Long id);

    /**
     * @mbggenerated 2017-04-20
     */
    List<AccountSubjectInfo> selectAll();

    List<AccountSubjectInfo> selectAllActive();


    /**
     * @mbggenerated 2017-04-20
     */
    int updateByPrimaryKey(AccountSubjectInfo record);


    /**
     * 根据（plat_no,platcust,subject,sub_subject）获取账户科目
     *
     * @param expense（plat_no,platcust,subject,sub_subject）
     * @return
     * @mbggenerated 2017-04-20
     */
    AccountSubjectInfo selectByUnionKey(EaccAccountamtlist expense);

    /**
     * 通过业务主键（plat_no,platcust,subject,sub_subject）减钱
     *
     * @param income（plat_no,platcust,subject,sub_subject,amt）
     * @return
     */
    int substractAmtByUnionKey(EaccAccountamtlist income);

    /**
     * 通过业务主键（plat_no,platcust,subject,sub_subject）减钱(不能减成负值)
     *
     * @param income（plat_no,platcust,subject,sub_subject,amt）
     * @return
     */
    int substractAmtByUnionKeyNoNeg(EaccAccountamtlist income);

    /**
     * 扣款
     *
     * @param expense（id,amt）
     * @return
     */
    int substractAmt(EaccAccountamtlist expense);

    /**
     * 扣款
     *
     * @param expense（id,amt）
     * @return
     */
    int substractAmtVIP(EaccAccountamtlist expense);

    /**
     * 加款
     *
     * @param income（plat_no,platcust,subject,sub_subject,amt）
     * @return
     */
    int addAmt(EaccAccountamtlist income);

    /**
     * 加款
     *
     * @param income（plat_no,platcust,subject,sub_subject,amt）
     * @return
     */
    int addAmtByUnionKey(EaccAccountamtlist income);

    /**
     * 查询账户金额
     *
     * @param accountId
     * @return AccountSubjectInfo
     */
    List<AccountSubjectInfo> selectByAccountId(String accountId);

    /**
     * 冻结
     *
     * @param freeze（amt,id）
     * @return
     */
    int freezeAmt(EaccAccountamtlist freeze);

    /**
     * 解冻
     *
     * @param unfreeze（amt,id）
     * @return
     */
    int unfreezeAmt(EaccAccountamtlist unfreeze);


    /**
     * 清算在途冻结
     *
     * @param expense（plat_no,platcust,subject,sub_subject,amt）
     * @return
     */
    int clearFloatFrozen(EaccAccountamtlist expense);

    /**
     * 清算现金冻结
     *
     * @param income（plat_no,platcust,subject,sub_subject,amt）
     * @return
     */
    int clearCashFrozen(EaccAccountamtlist income);

    /**
     * 清算在途减钱冻结(不能为负值)
     *
     * @param expense（plat_no,platcust,subject,sub_subject,amt）
     * @return
     */
    int clearFloatFrozenNoNeg(EaccAccountamtlist expense);


    /**
     * 查询平台账户信息
     *
     * @param queryPlatAccountParams
     * @return
     */
    List<AccountSubjectInfo> queryPlatAccount(@Param("params") Map<String, Object> queryPlatAccountParams);

    /**
     * 根据平台客户号查询用户账户平台列表
     *
     * @param queryAccountPlatNoListParams
     * @return
     */
    List<AccountSubjectInfo> queryAccountPlatNoList(@Param("params") Map<String, Object> queryAccountPlatNoListParams);


    /**
     * 查询汇总
     *
     * @param map
     * @return
     */
    Map<String, Object> selectTolAccountSubject(Map<String, Object> map);

    Map<String, Object> selectTolAccountSubjectByElc(Map<String, Object> map);

    /**
     * 查询明细
     *
     * @param map
     * @return
     */
    List<Map<String, Object>> selectDetailAccountSubject(Map<String, Object> map);

    List<Map<String, Object>> selectDetailAccountSubjectByElc(Map<String, Object> map);

    Map<String, Object> selectTolManage(Map<String, Object> map);

    Map<String, Object> selectTolElcBalance(Map<String, Object> map);

    List<Map<String, Object>> selectEaccAccountBalance(Map<String, Object> map);

    List<AccountSubjectInfo> queryAccountListByIdsForUpdate(List<Long> ids);

    int updateAccountSubjectInfoListByIds(AccountSubjectInfo subjectInfoList);

    /**
     * 查询集团所有在途有钱的账户
     * @param plat_nos
     * @param clear_date
     * @return
     */
    List<AccountSubjectInfo> getMallFloatSubject(@Param("list") List<String> plat_nos, @Param("clear_date") String clear_date);

    /**
     * 获取最大id
     * @return
     */
    long getMaxId();

    /**
     * 查询id段内在途大于0的账户
     * @param plat_no
     * @param start
     * @param end
     * @return
     */
    List<AccountSubjectInfo> getFloatSubjectGreaterZero(@Param("plat_no") String plat_no, @Param("start") long start, @Param("end")long end);

    /**
     * 查询所有分账户总余额
     * @param plat_no
     * @return
     */
    String querySubAmt(@Param("plat_no") String plat_no);


}