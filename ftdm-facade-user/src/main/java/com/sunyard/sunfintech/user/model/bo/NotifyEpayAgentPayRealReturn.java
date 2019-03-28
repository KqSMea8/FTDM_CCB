package com.sunyard.sunfintech.user.model.bo;

import java.io.Serializable;
import java.util.List;

/**
 * 行内代付异步通知接受参数
 * Created by PengZY on 2017/7/12.
 */
public class NotifyEpayAgentPayRealReturn implements Serializable {

    private List<NotifyEpayAgentPayRealReturnData> data;

    public List<NotifyEpayAgentPayRealReturnData> getData() {
        return data;
    }

    public void setData(List<NotifyEpayAgentPayRealReturnData> data) {
        this.data = data;
    }

}
