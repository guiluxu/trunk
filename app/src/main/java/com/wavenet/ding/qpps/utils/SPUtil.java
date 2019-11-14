package com.wavenet.ding.qpps.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Administrator on 2017/6/26.
 */

public class SPUtil {
    //loginKey
    public static final String USERNO = "userno";
    public static final String USERPWD = "userpwd";
    public static final String USERPWDOLD = "olduserpwd";
    public static final String CBSAVE = "cbsaver";
    public static final String AUTOLOGIN = "autologin";
    public static final String Dict = "Dictionaries";
    //secret
    public static final String APP_MYNAME = "myname";
    public static final String APP_PERSONID = "personid";
    public static final String APP_TOWNID = "townid";
    public static final String APP_ROLE = "role";
    public static final String APP_TOWNNAME = "townname";
    public static final String APP_DES = "des";
    public static final String APP_COMPANY = "company";
    public static final String APP_PUSH = "push";
    public static final String strMapUrl = "strMapUrl";
    public static final String MAPOFFERLINE = "mapofferline";
    public static final String MAPOFFERLINEVER = "mapofferlineversion";
    private static final String PREFERENCESNAME = "Preferences";
    public static final String STARTIME = "starttime";
    public   static final String TIMEPAUSE="timePause";
    public static final String ISBREAKOFF = "isbreakoff";
    public static final String BREAKOFFBEAN = "breakoffbean";
    public static final String XJID = "xjrecord";
    public static final String YHID = "yhrecord";
    private static SPUtil instance;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;


    public SPUtil(Context context) {
        sp = context.getSharedPreferences(PREFERENCESNAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.commit();
    }

    public synchronized static SPUtil getInstance(Context context) {
        if (instance == null) instance = new SPUtil(context);
        return instance;
    }

    public String getStringValue(String Key) {
        String value = (String) DeBase64Object(sp.getString(Key, ""));
        if (value == "[]" || value == null) value = "";
        return value;
    }

    public void setStringValue(String Key, String Value) {
        String valueBase64 = Base64Object(Value);
        editor.putString(Key, valueBase64);
        editor.commit();
    }

    public void setIntValue(String Key, int Value) {
        editor.putInt(Key, Value);
        editor.commit();
    }

    public int getIntValue(String Key) {
        int value = sp.getInt(Key, 1);
        return value;
    }

    public int getIntValue(String Key, int defaultvalue) {
        int value = sp.getInt(Key, defaultvalue);
        return value;
    }

    public Object getObjectValue(String key) {
        return DeBase64Object(sp.getString(key, null));
    }

    public void setObjectValue(String Key, Object Value) {
        String valueBase64 = Base64Object(Value);
        editor.putString(Key, valueBase64);
        editor.commit();

    }

    public void setLongValue(String Key, long Value) {
        editor.putLong(Key, Value);
        editor.commit();
    }

    public long getLongValue(String Key) {
        long value = sp.getLong(Key, 0);
        return value;
    }

    public void setFloatValue(String Key, float Value) {
        editor.putFloat(Key, Value);
        editor.commit();
    }

    public float getFloatValue(String Key) {
        float value = sp.getFloat(Key, 0);
        return value;
    }

    public void setBooleanValue(String Key, Boolean Value) {
        editor.putBoolean(Key, Value);
        editor.commit();
    }

    public boolean getBooleanValue(String Key, boolean defaultValue) {
        boolean value = sp.getBoolean(Key, defaultValue);
        return value;
    }


    public void remove(String key) {
        try {
            editor.remove(key);
            editor.commit();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public void clear() {
        editor.clear();
        editor.commit();
    }

    /**
     * Base64转String
     *
     * @param value
     * @return
     */
    private String Base64Object(Object value) {
        String valueBase64 = "";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            valueBase64 = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return valueBase64;
    }

    /**
     * Base64转Object
     *
     * @param base64value
     * @return
     */
    private Object DeBase64Object(String base64value) {
        Object object = null;

        try {
            byte[] productBytes = Base64.decode(base64value.getBytes(), Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(productBytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            object = ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

}
