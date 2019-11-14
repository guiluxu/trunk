package com.wavenet.ding.qpps.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zoubeiwen on 2019/8/8.
 */

public class RequestDataBean {//监测实时数据

    /**
     * Code : 200
     * Msg : 查询成功!
     * Data : [{"Fact_Id":"04030401160000001","Fact_Name":"枫泾水质净化厂","JckID":"1082","JckName":"进口","Datetime":"2018-10-25 21:00:30","PH":7.03,"COD":576.2,"NH3N":0,"TP":0,"TN":0,"SSLL":269.1},{"Fact_Id":"04030401160000001","Fact_Name":"枫泾水质净化厂","JckID":"1083","JckName":"出口","Datetime":"2018-10-25 20:47:30","PH":7.13,"COD":10.7,"NH3N":0.08,"TP":0.055,"TN":2.01,"SSLL":120.38}]
     */

    @SerializedName("Code")
    public String Code;
    @SerializedName("Msg")
    public String Msg;
    @SerializedName("Data")
    public List<DataBean> Data;

    public static class DataBean {
        /**
         * "S_DRAI_PUMP_ID": "BZ00041",
         "S_SNAME": "漕盈泵站",
         "T_STIME": "2019-06-14 10:45:19",
         "S_PUMPSTATUS": "000",
         "N_YEWEI": 3.62,
         "N_WAIYEWEI": 3.5,
         "PH": 11.1076,
         "COD": 381.55,
         "NH3N": 11.31,
         "TP": 5.042,
         "TN": 0,
         "SSLL1": 0.5,
         "SSLL2": 0,
         "SSLL3": 0,
         "SSLL4": 0,
         "SSLL5": 0,
         "SSLL6": 0
         *
         *
         *
         * Fact_Id : 04030401160000001
         * Fact_Name : 枫泾水质净化厂
         * JckID : 1082
         * JckName : 进口
         * Datetime : 2018-10-25 21:00:30
         * PH : 7.03
         * COD : 576.2
         * NH3N : 0
         * TP : 0
         * TN : 0
         * SSLL : 269.1
         *
         *
         *
         *
         *  "GWNo": "GW0010",
         "GWName": "华清路新达路",
         "XDSW": 0,
         "ZDSS": 0,
         "JDSW": -1.32,
         "Time": "2019-08-07 16:54:00"
         */

        @SerializedName("S_DRAI_PUMP_ID")
        public String S_DRAI_PUMP_ID="-";
        @SerializedName("S_SNAME")
        public String S_SNAME="-";
        @SerializedName("T_STIME")
        public String T_STIME="-";
        @SerializedName("S_PUMPSTATUS")
        public String S_PUMPSTATUS="-";
        @SerializedName("N_YEWEI")
        public double N_YEWEI=0;
        @SerializedName("N_WAIYEWEI")
        public double N_WAIYEWEI=0;
        @SerializedName("Fact_Id")
        public String FactId="-";
        @SerializedName("Fact_Name")
        public String FactName="-";
        @SerializedName("JckID")
        public String JckID="-";
        @SerializedName("JckName")
        public String JckName="-";
        @SerializedName("Datetime")
        public String Datetime="-";
        @SerializedName("PH")
        public double PH=0;
        @SerializedName("COD")
        public double COD=0;
        @SerializedName("NH3N")
        public double NH3N=0;
        @SerializedName("TP")
        public double TP=0;
        @SerializedName("TN")
        public double TN=0;
        @SerializedName("SSLL")
        public double SSLL=0;
        @SerializedName("SSLL1")
        public double SSLL1=0;
        @SerializedName("SSLL2")
        public double SSLL2=0;
        @SerializedName("SSLL3")
        public double SSLL3=0;
        @SerializedName("SSLL4")
        public double SSLL4=0;
        @SerializedName("SSLL5")
        public double SSLL5=0;
        @SerializedName("SSLL6")
        public double SSLL6=0;
        @SerializedName("GWNo")
        public String GWNo="-";
        @SerializedName("GWName")
        public String GWName="-";
        @SerializedName("XDSW")
        public double XDSW=0;
        @SerializedName("ZDSS")
        public double ZDSS=0;
        @SerializedName("JDSW")
        public double JDSW=0;
        @SerializedName("Time")
        public String Time="-";
    }
}
