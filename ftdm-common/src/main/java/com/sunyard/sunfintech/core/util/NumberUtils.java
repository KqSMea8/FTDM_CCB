package com.sunyard.sunfintech.core.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Created by terry on 2017/6/29.
 */
public class NumberUtils {

    public static String formatNumber(BigDecimal number){
        NumberFormat numberFormat=NumberFormat.getNumberInstance();
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat.format(number);
    }
}
