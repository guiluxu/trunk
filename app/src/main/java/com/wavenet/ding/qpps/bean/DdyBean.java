package com.wavenet.ding.qpps.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ding on 2018/9/11.
 */

public class DdyBean {

    /**
     * Code : 200
     * Msg : 查询成功!
     * Data : {"DPQ":0,"DSH":0}
     */

    @SerializedName("Code")
    public String Code;
    @SerializedName("Msg")
    public String Msg;
    @SerializedName("Data")
    public DataBean Data;
//    {"Code":"200","Msg":"查询成功!","Data":{"ErrCnt":13,"ManageCnt":34}}
    public static class DataBean {
        /**
         * ErrCnt : 0
         * ManageCnt : 0
         */

        @SerializedName("ErrCnt")
        public String ErrCnt;
        @SerializedName("ManageCnt")
        public String ManageCnt;
    }
}
