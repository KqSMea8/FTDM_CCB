package com.sunyard.sunfintech.custdao.mapper;

import com.sunyard.sunfintech.dao.entity.ClearResult;
import com.sunyard.sunfintech.dao.entity.ClearResultExample;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CustClearResultMapper {
    /**
     *
     * @mbggenerated 2017-06-14
     */
    int countByExample(ClearResultExample example);

    List<ClearResult> selectClearResult(Map<String, Object> map);

    int insertResult(ClearResult record);

    /**
     *
     * @mbggenerated 2017-06-14
     */
    int deleteByExample(ClearResultExample example);

    /**
     *
     * @mbggenerated 2017-06-14
     */
    int deleteByPrimaryKey(Integer pid);

    /**
     *
     * @mbggenerated 2017-06-14
     */
    int insert(ClearResult record);

    /**
     *
     * @mbggenerated 2017-06-14
     */
    int insertSelective(ClearResult record);

    /**
     *
     * @mbggenerated 2017-06-14
     */
    List<ClearResult> selectByExample(ClearResultExample example);

    /**
     *
     * @mbggenerated 2017-06-14
     */
    ClearResult selectByPrimaryKey(Integer pid);

    /**
     *
     * @mbggenerated 2017-06-14
     */
    int updateByExampleSelective(@Param("record") ClearResult record, @Param("example") ClearResultExample example);

    /**
     *
     * @mbggenerated 2017-06-14
     */
    int updateByExample(@Param("record") ClearResult record, @Param("example") ClearResultExample example);

    /**
     *
     * @mbggenerated 2017-06-14
     */
    int updateByPrimaryKeySelective(ClearResult record);

    /**
     *
     * @mbggenerated 2017-06-14
     */
    int updateByPrimaryKey(ClearResult record);

    int addClearResult(Map<String, Object> map);

    List<Map<String, Object>> selectPlatBalance(Map<String, Object> map);

    List<Map<String, Object>> selectBankBalance(Map<String, Object> map);

    List<ClearResult> getByUnionKey(@Param("plat_no")String plat_no,@Param("clear_date")Date clear_date);

    //新增外部对账情况明细表
    void addClearInfoMx(HashMap<String, Object> totalMap);

    //删除指定日期的数据，避免重复插入
    void deleteClearInfoByDate(@Param("clear_date")String clearDate,@Param("pay_code")String pay_code,@Param("plat_no")String plat_no);

    //新增外部对账情况明细表列表
    void addClearInfoLIst(List<HashMap<String, Object>> emxlist);

}