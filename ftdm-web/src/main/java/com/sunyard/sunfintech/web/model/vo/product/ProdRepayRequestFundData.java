package com.sunyard.sunfintech.web.model.vo.product;

import com.sunyard.sunfintech.prod.model.bo.CustRepay;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by terry on 2017/5/4.
 */
public class ProdRepayRequestFundData {

    /**
     * 还给投资人还款明细
     */
    @NotNull
    private List<CustRepay> custRepayList;
}
