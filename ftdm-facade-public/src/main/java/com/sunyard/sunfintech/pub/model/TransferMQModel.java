package com.sunyard.sunfintech.pub.model;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by terry on 2018/2/3.
 */
public class TransferMQModel implements Serializable {

    private BaseRequest baseRequest;

    private List<EaccAccountamtlist> eaccAccountamtlistList;

    public BaseRequest getBaseRequest() {
        return baseRequest;
    }

    public void setBaseRequest(BaseRequest baseRequest) {
        this.baseRequest = baseRequest;
    }

    public List<EaccAccountamtlist> getEaccAccountamtlistList() {
        return eaccAccountamtlistList;
    }

    public void setEaccAccountamtlistList(List<EaccAccountamtlist> eaccAccountamtlistList) {
        this.eaccAccountamtlistList = eaccAccountamtlistList;
    }
    public TransferMQModel( ) {
    }
    public TransferMQModel(BaseRequest baseRequest, List<EaccAccountamtlist> eaccAccountamtlistList) {
        this.baseRequest = baseRequest;
        this.eaccAccountamtlistList = eaccAccountamtlistList;
    }


    public List<EaccAccountamtlist> getEaccAccountamtlistListClone() {
        List<EaccAccountamtlist> newEaccAccountamtlist=new ArrayList<>();
        for(EaccAccountamtlist eaccAccountamtlist:eaccAccountamtlistList){
            newEaccAccountamtlist.add(eaccAccountamtlistClone(eaccAccountamtlist));
        }
        return newEaccAccountamtlist;
    }

    private EaccAccountamtlist eaccAccountamtlistClone(EaccAccountamtlist oldEaccAccountamtlist){
        EaccAccountamtlist eaccAccountamtlist=new EaccAccountamtlist();
        eaccAccountamtlist.setId(oldEaccAccountamtlist.getId());
        eaccAccountamtlist.setTrans_serial(oldEaccAccountamtlist.getTrans_serial());
        eaccAccountamtlist.setPlat_no(oldEaccAccountamtlist.getPlat_no());
        eaccAccountamtlist.setPlatcust(oldEaccAccountamtlist.getPlatcust());
        eaccAccountamtlist.setSubject(oldEaccAccountamtlist.getSubject());
        eaccAccountamtlist.setSub_subject(oldEaccAccountamtlist.getSub_subject());
        eaccAccountamtlist.setTrans_code(oldEaccAccountamtlist.getTrans_code());
        eaccAccountamtlist.setTrans_name(oldEaccAccountamtlist.getTrans_name());
        eaccAccountamtlist.setTrans_date(oldEaccAccountamtlist.getTrans_date());
        eaccAccountamtlist.setTrans_time(oldEaccAccountamtlist.getTrans_time());
        eaccAccountamtlist.setAmt_type(oldEaccAccountamtlist.getAmt_type());
        eaccAccountamtlist.setOppo_plat_no(oldEaccAccountamtlist.getOppo_plat_no());
        eaccAccountamtlist.setOppo_platcust(oldEaccAccountamtlist.getOppo_platcust());
        eaccAccountamtlist.setOppo_subject(oldEaccAccountamtlist.getSubject());
        eaccAccountamtlist.setOppo_sub_subject(oldEaccAccountamtlist.getOppo_sub_subject());
        eaccAccountamtlist.setAmt(oldEaccAccountamtlist.getAmt());
        eaccAccountamtlist.setRamerk(oldEaccAccountamtlist.getRamerk());
        eaccAccountamtlist.setRemark(oldEaccAccountamtlist.getRemark());
        eaccAccountamtlist.setSwitch_state(oldEaccAccountamtlist.getSwitch_state());
        eaccAccountamtlist.setSwitch_amt(oldEaccAccountamtlist.getSwitch_amt());
        eaccAccountamtlist.setPay_code(oldEaccAccountamtlist.getPay_code());
        eaccAccountamtlist.setAccount_date(oldEaccAccountamtlist.getAccount_date());
        eaccAccountamtlist.setExt1(oldEaccAccountamtlist.getExt1());
        eaccAccountamtlist.setExt2(oldEaccAccountamtlist.getExt2());
        eaccAccountamtlist.setExt3(oldEaccAccountamtlist.getExt3());
        eaccAccountamtlist.setExt4(oldEaccAccountamtlist.getExt4());
        eaccAccountamtlist.setExt5(oldEaccAccountamtlist.getExt5());
        eaccAccountamtlist.setEnabled(oldEaccAccountamtlist.getEnabled());
        eaccAccountamtlist.setCreate_by(oldEaccAccountamtlist.getCreate_by());
        eaccAccountamtlist.setCreate_time(oldEaccAccountamtlist.getCreate_time());
        eaccAccountamtlist.setUpdate_by(oldEaccAccountamtlist.getUpdate_by());
        eaccAccountamtlist.setUpdate_time(oldEaccAccountamtlist.getUpdate_time());
        eaccAccountamtlist.setOrder_no(oldEaccAccountamtlist.getOrder_no());
        eaccAccountamtlist.setBalance(oldEaccAccountamtlist.getBalance());
        return eaccAccountamtlist;
    }
}

