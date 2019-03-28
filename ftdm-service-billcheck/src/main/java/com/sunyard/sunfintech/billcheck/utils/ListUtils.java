package com.sunyard.sunfintech.billcheck.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by djh on 2017/7/6.
 */
public class ListUtils {

    /**
     * 合并元素
     * @param objects
     * @param split
     * @return
     */
    public static  String join(List<Object> objects,String split){
        StringBuffer sb = new StringBuffer();
        for(Object obj:objects){
            sb.append(obj.toString()).append(split);
        }
        sb.delete(sb.length()-split.length(),sb.length());
        return sb.toString();
    }

    /**
     * 如果为空设置默认值
     * @param t
     * @param defVal
     * @param <T>
     * @return
     */
    public static <T>T ifNull(T t,T defVal){
        if(t == null){
            return defVal;
        }else {
            return t;
        }
    }
    public static void main(String[] args) {
        ArrayList<Object> list = new ArrayList<>();
        list.add("a");
        list.add("c");
        list.add(1);
        //list.add(null);
        System.out.println(join(list,"#"));
    }
}
