package com.example.oneplus.opnew.util;

public class StringUtils {

    public static boolean isEmpty (String value){
        if (value != null && !"".equalsIgnoreCase(value.trim()) && !"null".equalsIgnoreCase(value.trim())){
            return false;
        }else {
            return true;
        }
    }

}
