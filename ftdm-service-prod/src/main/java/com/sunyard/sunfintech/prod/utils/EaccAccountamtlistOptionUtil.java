package com.sunyard.sunfintech.prod.utils;

import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;

/**
 * Created by terry on 2017/5/15.
 */
public class EaccAccountamtlistOptionUtil {
    private static EaccAccountamtlistOptionUtil ourInstance = new EaccAccountamtlistOptionUtil();

    public static EaccAccountamtlistOptionUtil getInstance() {
        return ourInstance;
    }

    private EaccAccountamtlistOptionUtil() {
    }

    public static EaccAccountamtlist setSubject(EaccAccountamtlist eaccAccountamtlist, String subject,
                                                String sub_subject, String oppo_subject, String oppo_sub_subject,
                                                String trans_date,String trans_time){
        eaccAccountamtlist.setSubject(subject);
        eaccAccountamtlist.setSub_subject(sub_subject);
        eaccAccountamtlist.setOppo_subject(oppo_subject);
        eaccAccountamtlist.setOppo_sub_subject(oppo_sub_subject);
        eaccAccountamtlist.setTrans_date(trans_date);
        eaccAccountamtlist.setTrans_time(trans_time);
        return eaccAccountamtlist;
    }
}
