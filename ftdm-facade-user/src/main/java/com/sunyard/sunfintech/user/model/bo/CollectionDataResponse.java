package com.sunyard.sunfintech.user.model.bo;

/**
 * Created by dingjy on 2017/5/28.
 */
public class CollectionDataResponse extends CollectionDataBaseResponse{

    //渠道流水号
    private String host_req_serial_no;

    public String getHost_req_serial_no() {
        return host_req_serial_no;
    }

    public void setHost_req_serial_no(String host_req_serial_no) {
        this.host_req_serial_no = host_req_serial_no;
    }
}
