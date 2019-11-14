package com.wavenet.ding.qpps.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 *
 */

public class UrlUtils {
    public static String toURLEncoded(String paramString) {
        if (paramString == null || paramString.equals("")) {
            return "";
        }

        try {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
            return str;
        } catch (Exception localException) {
        }

        return "";
    }

    /**
     * 直接加码 两次
     */
    public static String toURLEncodeTwo(String paramString) {
        paramString = toURLEncoded(paramString);
        return toURLEncoded(paramString);
    }

    public static String toURLDecoded(String paramString) {
        if (paramString == null || paramString.equals("")) {
            return "";
        }

        try {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLDecoder.decode(str, "UTF-8");
            return str;
        } catch (Exception localException) {
        }

        return "";
    }
}
