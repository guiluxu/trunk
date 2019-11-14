package com.wavenet.ding.qpps.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.wavenet.ding.qpps.utils.AppUtils;
import com.wavenet.ding.qpps.utils.LogUtils;
import com.wavenet.ding.qpps.view.ViewChartCurve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zoubeiwen on 2019/8/12.
 */

public class JCbzAclcBean {

    /**
     * Code : 200
     * Msg : 查询成功!
     * Data : {}
     */

    @SerializedName("Code")
    public String Code;
    @SerializedName("Msg")
    public String Msg;
    @SerializedName("Data")
    public DataBean Data;
    public List<JCDBean> jcDateblist;

    public static class JCDBean {
        public JCDBean(String name, double value) {
            this.name = name;
            this.value = value;
        }   public JCDBean(String name, String values) {
            this.name = name;
            this.values = values;
        }

        public JCDBean(String name, double value, String jckID, String jckName) {
            this.name = name;
            this.value = value;
            JckID = jckID;
            JckName = jckName;
        }

        public String name = "";
        public double value = 0.0;
        public String values = "";

        public String JckID;
        public String JckName;

    }

    public static Map<String, Object> getJCDBean_NH3N(List<JCbzAclcBean.DataBean.NH3NBean> bl, String[] JckIDarry, String[] Jcknamearry, double NWQGLLV, double NWQGULV, int f, boolean isHavejjx) {
        Map<String, Object> map = new HashMap<>();
        if (bl == null || bl.size() < 0) {
            return map;
        }
        List<JCbzAclcBean.JCDBean> jcdBean = new ArrayList<>();
        for (int i = 0; i < bl.size(); i++) {
            JCbzAclcBean.DataBean.NH3NBean b = bl.get(i);
            jcdBean.add(new JCDBean(b.name, b.value, b.JckID, b.JckName));
        }
        if (f == 0) {
            map = AppUtils.getJChisData_one(jcdBean, NWQGLLV, NWQGULV);
        } else {
            map = AppUtils.getJChisData_clc(jcdBean, JckIDarry, Jcknamearry, NWQGLLV, NWQGULV, false);
        }
        return map;
    }

    public static Map<String, Object> getJCDBean_COD(List<JCbzAclcBean.DataBean.CODBean> bl, String[] JckIDarry, String[] Jcknamearry, double NWQGLLV, double NWQGULV, int f, boolean isHavejjx) {
        Map<String, Object> map = new HashMap<>();
        if (bl == null || bl.size() < 0) {
            return map;
        }
        List<JCbzAclcBean.JCDBean> jcdBean = new ArrayList<>();
        for (int i = 0; i < bl.size(); i++) {
            JCbzAclcBean.DataBean.CODBean b = bl.get(i);
            jcdBean.add(new JCDBean(b.name, b.value, b.JckID, b.JckName));
        }
        if (f == 0) {
            map = AppUtils.getJChisData_one(jcdBean, NWQGLLV, NWQGULV);
        } else {
            map = AppUtils.getJChisData_clc(jcdBean, JckIDarry, Jcknamearry, NWQGLLV, NWQGULV, false);
        }
        return map;
    }
    public static Map<String, Object> getJCDBean_TP(List<JCbzAclcBean.DataBean.TPBean> bl, String[] JckIDarry, String[] Jcknamearry, double NWQGLLV, double NWQGULV, int f, boolean isHavejjx) {
        Map<String, Object> map = new HashMap<>();
        if (bl == null || bl.size() < 0) {
            return map;
        }
        List<JCbzAclcBean.JCDBean> jcdBean = new ArrayList<>();
        for (int i = 0; i < bl.size(); i++) {
            JCbzAclcBean.DataBean.TPBean b = bl.get(i);
            jcdBean.add(new JCDBean(b.name, b.value, b.JckID, b.JckName));
        }
        if (f == 0) {

            map = AppUtils.getJChisData_one(jcdBean, NWQGLLV, NWQGULV);
        } else {
            map = AppUtils.getJChisData_clc(jcdBean, JckIDarry, Jcknamearry, NWQGLLV, NWQGULV, false);
        }
        return map;
    }
    public static Map<String, Object> getJCDBean_TN(List<JCbzAclcBean.DataBean.TNBean> bl, String[] JckIDarry, String[] Jcknamearry, double NWQGLLV, double NWQGULV, int f, boolean isHavejjx) {
        Map<String, Object> map = new HashMap<>();
        List<JCbzAclcBean.JCDBean> jcdBean = new ArrayList<>();
        if (bl == null || bl.size() < 0) {
            return map;
        }
        for (int i = 0; i < bl.size(); i++) {
            JCbzAclcBean.DataBean.TNBean b = bl.get(i);
            jcdBean.add(new JCDBean(b.name, b.value, b.JckID, b.JckName));
        }
        if (f == 0) {

            map = AppUtils.getJChisData_one(jcdBean, NWQGLLV, NWQGULV);
        } else {
            map = AppUtils.getJChisData_clc(jcdBean, JckIDarry, Jcknamearry, NWQGLLV, NWQGULV, false);
        }
        return map;
    }
    public static Map<String, Object> getJCDBean_PH(List<JCbzAclcBean.DataBean.PHBean> bl, String[] JckIDarry, String[] Jcknamearry, double NWQGLLV, double NWQGULV, int f, boolean isHavejjx) {
        Map<String, Object> map = new HashMap<>();
        List<JCbzAclcBean.JCDBean> jcdBean = new ArrayList<>();
        if (bl == null || bl.size() < 0) {
            return map;
        }
        for (int i = 0; i < bl.size(); i++) {
            JCbzAclcBean.DataBean.PHBean b = bl.get(i);
            jcdBean.add(new JCDBean(b.name, b.value, b.JckID, b.JckName));
        }
        if (f == 0) {
            map = AppUtils.getJChisData_one(jcdBean, NWQGLLV, NWQGULV);
        } else {
            map = AppUtils.getJChisData_clc(jcdBean, JckIDarry, Jcknamearry, NWQGLLV, NWQGULV, false);
        }
        return map;
    }

    public static Map<String, Object> getJCDBean_PUMPSTATUS(List<JCbzAclcBean.DataBean.SPUMPSTATUSBean> bl, String[] JckIDarry, String[] Jcknamearry, double NWQGLLV, double NWQGULV, int f, boolean isHavejjx) {
        Map<String, Object> map = new HashMap<>();
        if (bl == null || bl.size() < 0) {
            return map;
        }
        List<JCbzAclcBean.JCDBean> jcdBean = new ArrayList<>();
        for (int i = 0; i < bl.size(); i++) {
            JCbzAclcBean.DataBean.SPUMPSTATUSBean b = bl.get(i);
            jcdBean.add(new JCDBean(b.name, b.value));
        }
        map = AppUtils.getJChisData_bzkg(jcdBean, JckIDarry, Jcknamearry, NWQGLLV, NWQGULV, isHavejjx);

        return map;
    }    public static Map<String, Object> getJCDBean_SSLL(List<JCbzAclcBean.DataBean.SSLLBean> bl, String[] JckIDarry, String[] Jcknamearry, double NWQGLLV, double NWQGULV, int f, boolean isHavejjx) {
        Map<String, Object> map = new HashMap<>();
        if (bl == null || bl.size() < 0) {
            return map;
        }
        List<JCbzAclcBean.JCDBean> jcdBean = new ArrayList<>();
        for (int i = 0; i < bl.size(); i++) {
            JCbzAclcBean.DataBean.SSLLBean b = bl.get(i);
            jcdBean.add(new JCDBean(b.name, b.value, b.JckID, b.JckName));
        }
        map = AppUtils.getJChisData_clc(jcdBean, JckIDarry, Jcknamearry, NWQGLLV, NWQGULV, false);

        return map;
    }
    public static Map<String, Object> getJCDBean_SSLL(JCbzAclcBean.DataBean bl, boolean isHavejjx) {
        Map<String, Object> map = new HashMap<>();
        if (bl == null) {
            return map;
        }
        List<Object> serieslist = new ArrayList<>();
        double ymax = 0.0;
        double ymin = 0.0;
        boolean isInitY=false;
//        String[] JckIDarry;//开关
        List<String> Jcknamearry = new ArrayList<>();//开关
        List<JCbzAclcBean.DataBean.SSLLBean>[] ssllarry = new List[]{bl.SSLL1, bl.SSLL2, bl.SSLL3, bl.SSLL4, bl.SSLL5, bl.SSLL6};
        boolean istime=true;
        String startime="";
        for (int i = 0; i < 6; i++) {
            if (ssllarry[i].size() != 0) {
                Jcknamearry.add("瞬时流量" + (i+1) + "号");
                if (istime){
                    istime=false;
                    startime = ssllarry[i].get(0).name.substring(0, 10);
                }
                List<Object> seriestemp = new ArrayList<>();
                for (int j = 0; j < ssllarry[i].size(); j++) {
                    JCbzAclcBean.DataBean.SSLLBean b = ssllarry[i].get(j);
                    List<Object> templist = new ArrayList<>();
                    Map<String, Object> maptemp = new HashMap<>();
                    String name = b.name;
                    double value = b.value;
                    if (!isInitY){
                        isInitY=true;
                        ymin = value ;
                        ymax = value ;
                    }
                    if (ymax < value) {
                        ymax = value;
                    }
                    if (ymin > value) {
                        ymin = value;
                    }
                    templist.add(name);
                    templist.add(value);
                    maptemp.put("name", name);
                    maptemp.put("value", templist);
                    seriestemp.add(maptemp);

                }
                serieslist.add(seriestemp);
            }
        }
        ViewChartCurve.legend=Jcknamearry.size();
        map.put("xmin", startime + " 00:00:00");
        map.put("xmax", startime + " 24:00:00");
        map.put("seriesdata", serieslist);
        map.put("seriesname", Jcknamearry);
        map.put("ymax", ymax);
        map.put("ymin", ymin);
        LogUtils.e("seriesdata111", new Gson().toJson(map));
        return map;
    }
public static  List<JCbzAclcBean.JCDBean> getQYW(List<JCbzAclcBean.DataBean.NYEWEIBean>bl){
    List<JCbzAclcBean.JCDBean> jcdBeann = new ArrayList<>();
    if (bl==null){
        return jcdBeann;
    }
    for (int i = 0; i < bl.size(); i++) {
        JCbzAclcBean.DataBean.NYEWEIBean b = bl.get(i);
        jcdBeann.add(new JCDBean(b.name, b.value));
    }
    return jcdBeann;
}public static  List<JCbzAclcBean.JCDBean> getHYW(List<JCbzAclcBean.DataBean.NWAIYEWEIBean>bl){
    List<JCbzAclcBean.JCDBean> jcdBeann = new ArrayList<>();
    if (bl==null){
        return jcdBeann;
    }
    for (int i = 0; i < bl.size(); i++) {
        JCbzAclcBean.DataBean.NWAIYEWEIBean b = bl.get(i);
        jcdBeann.add(new JCDBean(b.name, b.value));
    }
    return jcdBeann;
}
    public static Map<String, Object> getJCDBean_YW(JCbzAclcBean.DataBean bl, double NWQGLLV,double NWQGULV,boolean isHavejjx) {
        Map<String, Object> map = new HashMap<>();
        if (bl == null) {
            return map;
        }
        //        String[] JckIDarry;//图例
        List<String> Jcknamearry=new ArrayList<>();//图例
        List< List<JCbzAclcBean.JCDBean>> data=new ArrayList<>();
        List<JCbzAclcBean.JCDBean> qywarry =getHYW(bl.NWAIYEWEI);
        List<JCbzAclcBean.JCDBean> hywarry =getQYW(bl.NYEWEI);
        if (qywarry.size()!=0){
            Jcknamearry.add("前池液位" );
            data.add(qywarry);
        }
       if (hywarry.size()!=0){
            Jcknamearry.add("后池液位" );
           data.add(hywarry);
        }
        if (Jcknamearry.size()==0) {
            return map;
        }
        String startime = data.get(0).get(0).name.substring(0, 10);
        List<Object> serieslist = new ArrayList<>();
        double ymax = 0.0;
        double ymin = 0.0;
        boolean isInitY=false;
        for (int i = 0; i < data.size(); i++) {
                List<Object> seriestemp = new ArrayList<>();
                for (int j = 0; j < data.get(i).size(); j++) {
                    JCbzAclcBean.JCDBean b = data.get(i).get(j);
                    List<Object> templist = new ArrayList<>();
                    Map<String, Object> maptemp = new HashMap<>();
                    String name = b.name;
                    double value = b.value;
                    if (!isInitY){
                        isInitY=true;
                        ymax = value ;
                        ymin = value ;
                    }
                    if (ymax < value) {
                        ymax = value;
                    }

                    if (ymin > value) {
                        ymin = value;
                    }
                    templist.add(name);
                    templist.add(value);
                    maptemp.put("name", name);
                    maptemp.put("value", templist);
                    seriestemp.add(maptemp);

                }
                serieslist.add(seriestemp);
        }


        map.put("Yuj", NWQGLLV);
        map.put("Chaoyji", NWQGULV);
            ymax=Math.max(ymax, Math.max(NWQGLLV, NWQGULV));
            ymin=Math.min(ymin, Math.min(NWQGLLV, NWQGULV));

        map.put("xmin", startime + " 00:00:00");
        map.put("xmax", startime + " 24:00:00");
        map.put("seriesdata", serieslist);
        map.put("seriesname", Jcknamearry);
        map.put("ymax", ymax);
        map.put("ymin", ymin);
        LogUtils.e("seriesdata111", new Gson().toJson(map));
        return map;
    }
    public static class DataBean {
        /**
         * S_DRAI_PUMP_ID : BZ00054
         * S_SNAME : 会卓路泵站
         * Jingjx : [{"S_ITMID":"N_YEWEI","N_WQGLLV":-8,"N_WQGULV":8,"S_FACT_ID":"BZ00054"},{"S_ITMID":"N_WAIYEWEI","N_WQGLLV":-8,"N_WQGULV":8,"S_FACT_ID":"BZ00054"},{"S_ITMID":"COD","N_WQGLLV":0,"N_WQGULV":700,"S_FACT_ID":"BZ00054"},{"S_ITMID":"PH","N_WQGLLV":5,"N_WQGULV":9,"S_FACT_ID":"BZ00054"},{"S_ITMID":"NH3N","N_WQGLLV":20,"N_WQGULV":30,"S_FACT_ID":"BZ00054"},{"S_ITMID":"TP","N_WQGLLV":1,"N_WQGULV":8,"S_FACT_ID":"BZ00054"},{"S_ITMID":"TN","N_WQGLLV":1,"N_WQGULV":8,"S_FACT_ID":"BZ00054"}]
         * S_PUMPSTATUS : [{"T_STIME":"2019-05-27 00:00:47","S_PUMPSTATUS":"00000000010"},{"T_STIME":"2019-05-27 00:06:29","S_PUMPSTATUS":"00000000010"},{"T_STIME":"2019-05-27 00:11:28","S_PUMPSTATUS":"00000000010"}]
         * N_YEWEI : [{"T_STIME":"2019-05-27 00:00:47","N_YEWEI":0},{"T_STIME":"2019-05-27 00:06:29","N_YEWEI":0},{"T_STIME":"2019-05-27 00:11:28","N_YEWEI":0},{"T_STIME":"2019-05-27 00:16:28","N_YEWEI":0}]
         * N_WAIYEWEI : [{"T_STIME":"2019-05-27 00:00:47","N_WAIYEWEI":0},{"T_STIME":"2019-05-27 00:06:29","N_WAIYEWEI":0},{"T_STIME":"2019-05-27 00:11:28","N_WAIYEWEI":0}]
         * PH : [{"T_STIME":"2019-05-27 00:00:47","PH":3.516},{"T_STIME":"2019-05-27 00:06:29","PH":3.516}]
         * COD : [{"T_STIME":"2019-05-27 00:00:47","COD":68.569},{"T_STIME":"2019-05-27 00:06:29","COD":68.578}]
         * NH3N : [{"T_STIME":"2019-05-27 00:00:47","NH3N":0},{"T_STIME":"2019-05-27 00:06:29","NH3N":0},{"T_STIME":"2019-05-27 00:11:28","NH3N":0},{"T_STIME":"2019-05-27 00:16:28","NH3N":0}]
         * TP : [{"T_STIME":"2019-05-27 00:00:47","TP":0},{"T_STIME":"2019-05-27 00:06:29","TP":0},{"T_STIME":"2019-05-27 00:11:28","TP":0},{"T_STIME":"2019-05-27 00:16:28","TP":0}]
         * TN : [{"T_STIME":"2019-05-27 00:00:47","TN":0},{"T_STIME":"2019-05-27 00:06:29","TN":0},{"T_STIME":"2019-05-27 00:11:28","TN":0},{"T_STIME":"2019-05-27 00:16:28","TN":0}]
         * SSLL1 : [{"T_STIME":"2019-05-27 00:00:47","SSLL1":0},{"T_STIME":"2019-05-27 00:06:29","SSLL1":0}]
         * SSLL2 : [{"T_STIME":"2019-05-27 00:00:47","SSLL2":0},{"T_STIME":"2019-05-27 00:06:29","SSLL2":0}]
         * SSLL3 : [{"T_STIME":"2019-05-27 00:00:47","SSLL3":0},{"T_STIME":"2019-05-27 00:06:29","SSLL3":0},{"T_STIME":"2019-05-27 00:11:28","SSLL3":0},{"T_STIME":"2019-05-27 00:16:28","SSLL3":0}]
         * SSLL4 : [{"T_STIME":"2019-05-27 00:00:47","SSLL4":0},{"T_STIME":"2019-05-27 00:06:29","SSLL4":0},{"T_STIME":"2019-05-27 00:11:28","SSLL4":0},{"T_STIME":"2019-05-27 00:16:28","SSLL4":0}]
         * SSLL5 : [{"T_STIME":"2019-05-27 00:00:47","SSLL5":0},{"T_STIME":"2019-05-27 00:06:29","SSLL5":0},{"T_STIME":"2019-05-27 00:11:28","SSLL5":0},{"T_STIME":"2019-05-27 00:16:28","SSLL5":0}]
         * SSLL6 : [{"T_STIME":"2019-05-27 00:00:47","SSLL6":0},{"T_STIME":"2019-05-27 00:06:29","SSLL6":0},{"T_STIME":"2019-05-27 00:11:28","SSLL6":0},{"T_STIME":"2019-05-27 00:16:28","SSLL6":0}]
         */

        @SerializedName("GWNo")
        public String GWNo;
        @SerializedName("Yuj")
        public double Yuj;
        @SerializedName("Chaoyji")
        public double Chaoyji;

        @SerializedName("Fact_Id")
        public String FactId;
        @SerializedName("SSLL")
        public List<SSLLBean> SSLL = new ArrayList<>();

        @SerializedName("S_DRAI_PUMP_ID")
        public String SDRAIPUMPID;
        @SerializedName("S_SNAME")
        public String SSNAME;
        @SerializedName("Jingjx")
        public List<JingjxBean> Jingjx;
        @SerializedName("S_PUMPSTATUS")
        public List<SPUMPSTATUSBean> SPUMPSTATUS;
        @SerializedName("N_YEWEI")
        public List<NYEWEIBean> NYEWEI;
        @SerializedName("N_WAIYEWEI")
        public List<NWAIYEWEIBean> NWAIYEWEI;
        @SerializedName("PH")
        public List<PHBean> PH;
        @SerializedName("COD")
        public List<CODBean> COD;
        @SerializedName("NH3N")
        public List<NH3NBean> NH3N;
        @SerializedName("TP")
        public List<TPBean> TP;
        @SerializedName("TN")
        public List<TNBean> TN;
        //        @SerializedName("SSLL1")
//        public List<SSLLBean> SSLLBean;
        @SerializedName("SSLL1")
        public List<SSLLBean> SSLL1 = new ArrayList<>();
        @SerializedName("SSLL2")
        public List<SSLLBean> SSLL2 = new ArrayList<>();
        @SerializedName("SSLL3")
        public List<SSLLBean> SSLL3 = new ArrayList<>();
        @SerializedName("SSLL4")
        public List<SSLLBean> SSLL4 = new ArrayList<>();
        @SerializedName("SSLL5")
        public List<SSLLBean> SSLL5 = new ArrayList<>();
        @SerializedName("SSLL6")
        public List<SSLLBean> SSLL6 = new ArrayList<>();

        public static class JingjxBean {
            /**
             * S_ITMID : N_YEWEI
             * N_WQGLLV : -8
             * N_WQGULV : 8
             * S_FACT_ID : BZ00054
             */

            @SerializedName("S_ITMID")
            public String SITMID;
            @SerializedName("N_WQGLLV")
            public double NWQGLLV;
            @SerializedName("N_WQGULV")
            public double NWQGULV;
            @SerializedName("S_FACT_ID")
            public String SFACTID;
        }

        public static class SPUMPSTATUSBean {
            /**
             * T_STIME : 2019-05-27 00:00:47
             * S_PUMPSTATUS : 00000000010
             */

            @SerializedName("T_STIME")
            public String name;
            @SerializedName("S_PUMPSTATUS")
            public String value;
        }

        public static class NYEWEIBean {
            /**
             * T_STIME : 2019-05-27 00:00:47
             * N_YEWEI : 0
             */

            @SerializedName("T_STIME")
            public String name;
            @SerializedName("N_YEWEI")
            public double value;
        }

        public static class NWAIYEWEIBean {
            /**
             * T_STIME : 2019-05-27 00:00:47
             * N_WAIYEWEI : 0
             */

            @SerializedName("T_STIME")
            public String name;
            @SerializedName("N_WAIYEWEI")
            public double value;
        }

        public static class PHBean {
            /**
             * T_STIME : 2019-05-27 00:00:47
             * PH : 3.516
             */

            @SerializedName("T_STIME")
            public String name;
            @SerializedName("PH")
            public double value;
            public String JckID;
            public String JckName;

        }

        public static class CODBean {
            /**
             * T_STIME : 2019-05-27 00:00:47
             * COD : 68.569
             */

            @SerializedName("T_STIME")
            public String name;
            @SerializedName("COD")
            public double value;

            public String JckID;
            public String JckName;
        }

        public static class NH3NBean {
            /**
             * T_STIME : 2019-05-27 00:00:47
             * NH3N : 0
             */

            @SerializedName("T_STIME")
            public String name;
            @SerializedName("NH3N")
            public double value;

            public String JckID;
            public String JckName;
        }

        public static class TPBean {
            /**
             * T_STIME : 2019-05-27 00:00:47
             * TP : 0
             */

            @SerializedName("T_STIME")
            public String name;
            @SerializedName("TP")
            public double value;

            public String JckID;
            public String JckName;
        }

        public static class TNBean {
            /**
             * T_STIME : 2019-05-27 00:00:47
             * TN : 0
             */

            @SerializedName("T_STIME")
            public String name;
            @SerializedName("TN")
            public double value;

            public String JckID;
            public String JckName;
        }

        public static class SSLL1Bean {
            /**
             * T_STIME : 2019-05-27 00:00:47
             * SSLL1 : 0
             */

            @SerializedName("T_STIME")
            public String name;
            @SerializedName("SSLL1")
            public double value;

            public String JckID;
            public String JckName;
        }

        public static class SSLLBean {
            /**
             * T_STIME : 2019-05-27 00:00:47
             * SSLL1 : 0
             */

            @SerializedName("T_STIME")
            public String name;
            @SerializedName("SSLL1")
            public double value;

            public String JckID;
            public String JckName;
        }

        public static class SSLL2Bean {
            /**
             * T_STIME : 2019-05-27 00:00:47
             * SSLL2 : 0
             */

            @SerializedName("T_STIME")
            public String name;
            @SerializedName("SSLL2")
            public double value;

            public String JckID;
            public String JckName;
        }

        public static class SSLL3Bean {
            /**
             * T_STIME : 2019-05-27 00:00:47
             * SSLL3 : 0
             */

            @SerializedName("T_STIME")
            public String name;
            @SerializedName("SSLL3")
            public double value;

            public String JckID;
            public String JckName;
        }

        public static class SSLL4Bean {
            /**
             * T_STIME : 2019-05-27 00:00:47
             * SSLL4 : 0
             */

            @SerializedName("T_STIME")
            public String name;
            @SerializedName("SSLL4")
            public double value;

            public String JckID;
            public String JckName;
        }

        public static class SSLL5Bean {
            /**
             * T_STIME : 2019-05-27 00:00:47
             * SSLL5 : 0
             */

            @SerializedName("T_STIME")
            public String name;
            @SerializedName("SSLL5")
            public double value;

            public String JckID;
            public String JckName;
        }

        public static class SSLL6Bean {
            /**
             * T_STIME : 2019-05-27 00:00:47
             * SSLL6 : 0
             */

            @SerializedName("T_STIME")
            public String name;
            @SerializedName("SSLL6")
            public double value;

            public String JckID;
            public String JckName;
        }
    }
}
