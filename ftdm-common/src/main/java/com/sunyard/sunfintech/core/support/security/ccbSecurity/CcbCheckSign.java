package com.sunyard.sunfintech.core.support.security.ccbSecurity;

import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;

import java.lang.reflect.Field;
import java.util.TreeMap;

/**
 * Created by dingjy on 2017/7/13.
 */
public class CcbCheckSign {

    /**
     * 对象属性排序之后加密产生加密字符串
     * @param object
     * @param KEY 机密密钥
     * @return
     */
    public static Boolean checkSignByObject(Object object, String KEY) throws BusinessException{
        //得到类对象
        Class<? extends Object> objectClass = (Class<? extends Object>)object.getClass();
        //得到类的属性
        Field[] fs = objectClass.getDeclaredFields();
        //用TreeMap排序
        TreeMap<String, Object> map = new TreeMap<String, Object>();
        String sign="";
        for (Field f : fs) {
            f.setAccessible(true);
            //遍历属性
            //忽略编译产生的属性
            if (f.isSynthetic()) {
                continue;
            }
            //忽略serialVersionUID
            if (f.getName().equals("serialVersionUID")) {
                continue;
            }
            //忽略签名串
            if (f.getName().equals("cert_sign")) {
                try {
                    sign=f.get(object).toString();
                } catch (IllegalAccessException e) {
                    BaseResponse baseResponse=new BaseResponse();
                    baseResponse.setRecode(BusinessMsg.UNKNOW_ERROE);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE)+"验签失败，签名转化失败");
                    throw new BusinessException(baseResponse);
                }
                continue;
            }
            f.setAccessible(true); //设置些属性是可以访问的
            try {
                //把属性添加到map里
                map.put(f.getName(),f.get(object));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw new BusinessException("签名失败");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new BusinessException("签名失败");
            }
        }
        //拼接属性值
        String str = "";
        for (Object v : map.values()) {
            //忽略null值
            if(null==v || "".equals(v)){
                continue;
            }
            str +=v.toString().trim()+"|";
        }
        str = str.substring(0, str.length()-1);

        String cert_sign = SecurityKeyUtil.getSignString(str, KEY);
        if(cert_sign.equals(sign)){
            return true;
        }
        return false;

    }
}
