package com.sunyard.sunfintech.core.dic;

import java.util.Random;

/**
 * Created by PengZY on 2018/1/16.
 */
public enum Payment {

    ONE("881"),TWO("882"), THREE("883"), FOUR("884"), FIVE("885"), SIX("886"), SEVEN("887"), EIGHT("888"), NINE("889"), TEN("890");

    private String code;

    private Payment(String code){
        this.code= code;
    }

    public String getCode(){
        return code;
    }

    public static boolean isPayment(String subSubject){
        switch (subSubject){
            case "881":
                return true;
            case "882":
                return true;
            case "883":
                return true;
            case "884":
                return true;
            case "885":
                return true;
            case "886":
                return true;
            case "887":
                return true;
            case "888":
                return true;
            case "889":
                return true;
            case "890":
                return true;
            default:
                return false;
        }
    }

    public static String getRandom() {
        int max=10;
        int min=1;
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        switch (s){
            case 1:
                return "881";
            case 2:
                return "882";
            case 3:
                return "883";
            case 4:
                return "884";
            case 5:
                return "885";
            case 6:
                return "886";
            case 7:
                return "887";
            case 8:
                return "888";
            case 9:
                return "889";
            case 10:
                return "890";
            default:
                return "881";
        }
    }
}
