package com.sunyard.sunfintech.core.support.security.ccbSecurity;

/**
 * Created by dingjy on 2017/7/13.
 */
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import com.sunyard.sunfintech.core.exception.BusinessException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DESede {
    public static final String DESEDE_ALGORITHM = "DESede";

    public static String DESedeKey(){
        try {
            KeyGenerator kg = KeyGenerator.getInstance(DESEDE_ALGORITHM);
            kg.init(168);// must be equal to 112 or 168
            String reuslt = new BASE64Encoder().encode(kg.generateKey().getEncoded());
            System.out.println("SecretKey:" + reuslt);
            return reuslt;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String DESedeEncode(String pass, String KEY) {
        String str = "";
        try {
            Key key = new SecretKeySpec(new BASE64Decoder().decodeBuffer(KEY), DESEDE_ALGORITHM);
            Cipher cipher = Cipher.getInstance(DESEDE_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            str = new BASE64Encoder().encode(cipher.doFinal(pass.getBytes()));
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("SIGN_ERROR");
        }
    }

    public static String DESedeDecode(String pass, String KEY) {
        String str = "";
        try {
            Key key = new SecretKeySpec(new BASE64Decoder().decodeBuffer(KEY), DESEDE_ALGORITHM);
            Cipher cipher = Cipher.getInstance(DESEDE_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);

            str = new String(cipher.doFinal(new BASE64Decoder().decodeBuffer(pass)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }

    public static void main(String[] args){
        String KEY = DESedeKey();
        String src = "";
        String encodeStr = DESedeEncode(src, KEY);
        String dencodeStr = DESedeDecode(encodeStr, KEY);

        System.out.println("encodeStr: " + encodeStr);
        System.out.println("dencodeStr: " + dencodeStr);
    }
}
