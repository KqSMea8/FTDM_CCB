package com.sunyard.sunfintech.user.model.bo;

import com.sunyard.sunfintech.dao.entity.AccountSubjectInfo;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import com.sunyard.sunfintech.dao.entity.EaccCardinfo;
import com.sunyard.sunfintech.dao.entity.EaccUserinfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by PengZY on 2018/1/30.
 */
public class RwWithdrawEaccAccountAmtList implements Serializable{

    //垫付金额
    private BigDecimal advance_amt;

    //需要转账的数据
    private List<EaccAccountamtlist> eaccAccountamtlists;

    private String is_advance;

    private EaccUserinfo eaccUserinfo;

    private EaccCardinfo eaccCardinfo;

    private AccountSubjectInfo accountSubjectInfo;

    public BigDecimal getAdvance_amt() {
        return advance_amt;
    }

    public void setAdvance_amt(BigDecimal advance_amt) {
        this.advance_amt = advance_amt;
    }

    public List<EaccAccountamtlist> getEaccAccountamtlists() {
        return eaccAccountamtlists;
    }

    public void setEaccAccountamtlists(List<EaccAccountamtlist> eaccAccountamtlists) {
        this.eaccAccountamtlists = eaccAccountamtlists;
    }

    public String getIs_advance() {
        return is_advance;
    }

    public void setIs_advance(String is_advance) {
        this.is_advance = is_advance;
    }

    public EaccUserinfo getEaccUserinfo() {
        return eaccUserinfo;
    }

    public void setEaccUserinfo(EaccUserinfo eaccUserinfo) {
        this.eaccUserinfo = eaccUserinfo;
    }

    public EaccCardinfo getEaccCardinfo() {
        return eaccCardinfo;
    }

    public void setEaccCardinfo(EaccCardinfo eaccCardinfo) {
        this.eaccCardinfo = eaccCardinfo;
    }

    public AccountSubjectInfo getAccountSubjectInfo() {
        return accountSubjectInfo;
    }

    public void setAccountSubjectInfo(AccountSubjectInfo accountSubjectInfo) {
        this.accountSubjectInfo = accountSubjectInfo;
    }
}
