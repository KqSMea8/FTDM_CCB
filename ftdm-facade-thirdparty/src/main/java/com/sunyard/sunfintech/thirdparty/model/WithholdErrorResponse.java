package com.sunyard.sunfintech.thirdparty.model;

import java.io.Serializable;

/**
 * Created by my on 2018/5/17.
 */
public class WithholdErrorResponse implements Serializable {
    private String fail_cause;

    public String getFail_cause() {
        return fail_cause;
    }

    public void setFail_cause(String fail_cause) {
        this.fail_cause = fail_cause;
    }
}
