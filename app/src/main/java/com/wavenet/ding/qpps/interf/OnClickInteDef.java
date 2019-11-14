package com.wavenet.ding.qpps.interf;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by zoubeiwen on 18/1/23.
 */

public class OnClickInteDef {
    public static final int REMOVEMAPLOC = 100;
    public static final int DOWNLOADSUCCESS = 101;
    public static final int DOWNLOADFIL = 102;
    public static final int BENGZHAN = 103;
    public static final int GUANWANG = 104;
    public static final int JIANCHAJING = 105;
    public static final int WUSHUICHANG = 106;
    public static final int PAISHUIHU = 107;
    public static final int ZHONGDIANPFK = 108;
    public static final int PFK = 109;

    public static final int TDTVEC = 110;
    public static final int TDTIMG = 111;


    public static final int RECORD = 112;
    public static final int PALYVIDEO = 113;
    public static int mOnclick;

    public static String setOnclick(@Onclick int onclick) {
        mOnclick = onclick;
        return getOnclick();
    }

    public static  String getOnclick() {

        if (mOnclick == REMOVEMAPLOC) {
            return "REMOVEMAPLOC";
        } else if (mOnclick == DOWNLOADSUCCESS) {
            return "DOWNLOADSUCCESS";
        } else if (mOnclick == DOWNLOADFIL) {
            return "DOWNLOADFIL";
        } else if (mOnclick == BENGZHAN) {
            return "BENGZHAN";
        } else if (mOnclick == GUANWANG) {
            return "GUANWANG";
        } else if (mOnclick == JIANCHAJING) {
            return "JIANCHAJING";
        } else if (mOnclick == WUSHUICHANG) {
            return "WUSHUICHANG";
        } else if (mOnclick == PAISHUIHU) {
            return "PAISHUIHU";
        } else if (mOnclick == ZHONGDIANPFK) {
            return "ZHONGDIANPFK";
        } else if (mOnclick == PFK) {
            return "PFK";
        } else if (mOnclick == TDTVEC) {
            return "TDTVEC";
        } else if (mOnclick == TDTIMG) {
            return "TDTIMG";
        } else if (mOnclick == RECORD) {
            return "RECORD";
        } else if (mOnclick == PALYVIDEO) {
            return "PALYVIDEO";
        } else {
            return "";
        }

    }

    @IntDef({
            DOWNLOADSUCCESS,
            DOWNLOADFIL,
            REMOVEMAPLOC,
            BENGZHAN,
            GUANWANG,
            JIANCHAJING,
            WUSHUICHANG,
            PAISHUIHU,
            ZHONGDIANPFK,
            PFK,
            TDTVEC,
            TDTIMG,
            RECORD,
            PALYVIDEO

    })
    @Retention(RetentionPolicy.SOURCE) //表示注解所存活的时间,在运行时,而不会存在. class 文件.
    public @interface Onclick { //接口，定义新的注解类型
    }


}
