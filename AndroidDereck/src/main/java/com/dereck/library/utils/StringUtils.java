package com.dereck.library.utils;

import android.text.TextUtils;

/**
 * Created by ding on 2018/8/9.
 */

public class StringUtils {

    public static boolean isEmpty(String str) {
        if (str == null || TextUtils.isEmpty(str))
            return true;
        String s = str.toLowerCase().trim();
        if (s.length() == 0 || TextUtils.isEmpty(s) || "".equals(s) || "null".equals(s))
            return true;
        return false;

    }


}