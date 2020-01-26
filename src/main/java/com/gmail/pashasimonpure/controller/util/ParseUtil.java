package com.gmail.pashasimonpure.controller.util;

public class ParseUtil {

    public static Integer tryParseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static String tryParseStr(String str) {
        if(str.equals("")){
            return null;
        }
        return str;
    }

}
