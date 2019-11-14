package com.wavenet.ding.qpps.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ding on 2018/9/11.
 */

public class VersionBean {
    /**
     * Code : 200
     * Msg : 查询成功!
     * Data : {"VERSIONID":31,"VERSIONNUM":"1.2.8","ISUPDATE":1,"URL":"http://222.66.154.70:2088/apk/1.0.6_版本7.apk","UPDATETIME":"2019-06-26 17:47:34","UPDATECONTENT":"1.管网设施故障解决        "}
     */

    @SerializedName("Code")
    public String Code;
    @SerializedName("Msg")
    public String Msg;
    @SerializedName("Data")
    public DataBean Data;

    public static class DataBean {
        /**
         * VERSIONID : 31
         * VERSIONNUM : 1.2.8
         * ISUPDATE : 1
         * URL : http://222.66.154.70:2088/apk/1.0.6_版本7.apk
         * UPDATETIME : 2019-06-26 17:47:34
         * UPDATECONTENT : 1.管网设施故障解决
         */

        @SerializedName("VERSIONID")
        public int VERSIONID;
        @SerializedName("VERSIONNUM")
        public String VERSIONNUM;
        @SerializedName("ISUPDATE")
        public int ISUPDATE;
        @SerializedName("URL")
        public String URL;
        @SerializedName("UPDATETIME")
        public String UPDATETIME;
        @SerializedName("UPDATECONTENT")
        public String UPDATECONTENT;
    }


//    public static class ValueBean {
//        @SerializedName("@odata.etag")
//        public String _$OdataEtag100; // FIXME check this code
//        @SerializedName("UPDATECONTENT")
//        public String UPDATECONTENT;
//        @SerializedName("VERSIONID")
//        public int VERSIONID;
//        @SerializedName("VERSIONNUM")
//        public String VERSIONNUM;
//        @SerializedName("ISUPDATE")
//        public int ISUPDATE;
//        @SerializedName("URL")
//        public String URL;
//    }
}
