package com.wavenet.ding.qpps.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zoubeiwen on 2019/8/12.
 */

public class JCswBean {



    @SerializedName("Code")
    public String Code;
    @SerializedName("Msg")
    public String Msg;
    @SerializedName("Data")
    public DataBeanX Data;

    public static class DataBeanX {

        @SerializedName("GWNo")
        public String GWNo;
        @SerializedName("Yuj")
        public double Yuj;
        @SerializedName("Chaoyji")
        public double Chaoyji;
        @SerializedName("Data")
        public List<DataBean> Data;

        public static class DataBean {
            /**
             * Time : 2019-08-07 00:01:00
             * JDSW : 2.29
             */

            @SerializedName("Time")
            public String name;
            @SerializedName("JDSW")
            public double value;
        }
    }
}
