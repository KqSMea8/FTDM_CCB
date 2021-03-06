package com.sunyard.sunfintech.prod.model.bo;

import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.prod.model.bo.Amtlist;

import java.io.Serializable;
import java.util.Date;

/**
 * 标的操作返回的业务数据
 * Created by terry on 2017/5/4.
 */
public class ProdBusinessData implements Serializable {

    /**
     * order_no
     recode
     remsg
     order_status
     sign
     trans_date
     */

    /**
     * 订单号
     */
    private String order_no;

    /**
     * 订单状态
     */
    private String order_status;

    /**
     * 系统处理日期(yyyyMMddHHmmss))
     */
    private Date process_date;

    /**
     * 平台流水号
     */
    private String query_id;

    /**
     * 资金处理记录
     */
    private Amtlist amtlist;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public Date getProcess_date() {
        return process_date;
    }

    public void setProcess_date(String process_date) {
        this.process_date = DateUtils.parseDate(process_date);
    }

    public String getQuery_id() {
        return query_id;
    }

    public void setQuery_id(String query_id) {
        this.query_id = query_id;
    }

    public Amtlist getAmtlist() {
        return amtlist;
    }

    public void setAmtlist(Amtlist amtlist) {
        this.amtlist = amtlist;
    }

    @Override
    public String toString() {
        return "ProdBusinessData{" +
                "order_no='" + order_no + '\'' +
                ", order_status='" + order_status + '\'' +
                ", process_date=" + process_date +
                ", query_id='" + query_id + '\'' +
                ", amtlist=" + amtlist +
                '}';
    }
}
