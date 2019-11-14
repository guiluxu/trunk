package com.wavenet.ding.qpps.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by zoubeiwen on 2019/4/10.
 */

public class DataBean implements Serializable{

    /**
     * Code : 200
     * Msg : 查询成功!
     * Data : [{"N_MILEAGE":null,"N_X":121.10814061266842,"N_Y":31.164791451947853,"T_UPLOAD":"2018-11-28 03:54:18","T_STARTM":null},{"N_MILEAGE":null,"N_X":121.10815206224665,"N_Y":31.164209891211634,"T_UPLOAD":"2018-11-28 03:54:08","T_STARTM":null},{"N_MILEAGE":null,"N_X":121.10808809826983,"N_Y":31.161707792827873,"T_UPLOAD":"2018-11-28 03:53:58","T_STARTM":null},{"N_MILEAGE":null,"N_X":121.10790911330443,"N_Y":31.16042835052079,"T_UPLOAD":"2018-11-28 03:53:38","T_STARTM":null},{"N_MILEAGE":null,"N_X":121.10790911330443,"N_Y":31.16042835052079,"T_UPLOAD":"2018-11-28 03:53:28","T_STARTM":null},{"N_MILEAGE":null,"N_X":121.10790911330443,"N_Y":31.16042835052079,"T_UPLOAD":"2018-11-28 03:53:18","T_STARTM":null},{"N_MILEAGE":null,"N_X":121.107842179511,"N_Y":31.159251923162234,"T_UPLOAD":"2018-11-28 03:53:08","T_STARTM":null},{"N_MILEAGE":null,"N_X":121.107842179511,"N_Y":31.159251923162234,"T_UPLOAD":"2018-11-28 03:52:58","T_STARTM":null},{"N_MILEAGE":null,"N_X":121.107842179511,"N_Y":31.159251923162234,"T_UPLOAD":"2018-11-28 03:52:48","T_STARTM":null},{"N_MILEAGE":null,"N_X":121.107842179511,"N_Y":31.159251923162234,"T_UPLOAD":"2018-11-28 03:52:38","T_STARTM":null}]
     */
//
//    @SerializedName("Code")
//    public String Code;
//    @SerializedName("Msg")
//    public String Msg;
//    @SerializedName("Data")
//    public List<DataBean> Data;

//    public static class DataBean {
//        /**
//         * N_MILEAGE : null
//         * N_X : 121.10814061266842
//         * N_Y : 31.164791451947853
//         * T_UPLOAD : 2018-11-28 03:54:18
//         * T_STARTM : null
//         */

        @SerializedName("N_MILEAGE")
        public double NMILEAGE;
        @SerializedName("N_X")
        public double NX;
        @SerializedName("N_Y")
        public double NY;
        @SerializedName("T_UPLOAD")
        public String TUPLOAD;
        @SerializedName("T_STARTM")
        public String TSTARTM;
        public String S_RECORD_ID;
        public Double orderDis;
//    }
}
