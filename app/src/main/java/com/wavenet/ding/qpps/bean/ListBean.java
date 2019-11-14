package com.wavenet.ding.qpps.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ding on 2018/10/17.
 */

public class ListBean {

    /**
     * PATROL_MANAGEMENT_Collection : {"PATROL_MANAGEMENT":[{"S_MANGE_ID":"1539314237868xjtest","S_CATEGORY":"W1002600001","S_TYPE":"W1002700001","S_DESC":null,"S_LOCAL":null,"S_IN_MAN":"xjtest","T_IN_DATE":"2018/10/12 11:16:45","S_MANGE_MAN":null,"T_MANGE_TIME":null,"S_MANGE_REMARK":null,"S_IS_MANGE":"W1001600000","N_X":"121.53389210889824","N_Y":"31.278516224359045","S_STATUS":"W1006500002","S_SJSB_ID":"XJ1539314211935xjtest","S_SJCZ_ID":null,"S_SOURCE":"W1007500002","S_EMERGENCY":"W1008100001","S_TOWNID_IN":"W1007400001","S_COMPANY_IN":null,"S_IN_MAN_FULL":null,"S_MANGE_FULL":null,"S_TOWNID_MANGE":"W1007400001","S_COMPANY_MANGE":null,"S_NAME":"蟠中路未处置","S_DELETE":null,"T_CREATE":null,"S_CREATE_MAN":null,"S_RECODE_ID":"XJ1539314211935xjtest","S_MANGE_STATUS":null}]}
     */

    @SerializedName("Code")
    public String Code;
    @SerializedName("Msg")
    public String Msg;

    @SerializedName("Data")
        public List<PATROLMANAGEMENTBean> PATROL_MANAGEMENT;

        public static class PATROLMANAGEMENTBean implements Serializable {
            /**
             * S_MANGE_ID : 1539314237868xjtest
             * S_CATEGORY : W1002600001
             * S_TYPE : W1002700001
             * S_DESC : null
             * S_LOCAL : null
             * S_IN_MAN : xjtest
             * T_IN_DATE : 2018/10/12 11:16:45
             * S_MANGE_MAN : null
             * T_MANGE_TIME : null
             * S_MANGE_REMARK : null
             * S_IS_MANGE : W1001600000
             * N_X : 121.53389210889824
             * N_Y : 31.278516224359045
             * S_STATUS : W1006500002
             * S_SJSB_ID : XJ1539314211935xjtest
             * S_SJCZ_ID : null
             * S_SOURCE : W1007500002
             * S_EMERGENCY : W1008100001
             * S_TOWNID_IN : W1007400001
             * S_COMPANY_IN : null
             * S_IN_MAN_FULL : null
             * S_MANGE_FULL : null
             * S_TOWNID_MANGE : W1007400001
             * S_COMPANY_MANGE : null
             * S_NAME : 蟠中路未处置
             * S_DELETE : null
             * T_CREATE : null
             * S_CREATE_MAN : null
             * S_RECODE_ID : XJ1539314211935xjtest
             * S_MANGE_STATUS : null
             */

            public String S_MANGE_ID;
            public String S_CATEGORY;

            @SerializedName("S_category_cn")
            public String S_CATEGORY_CN;
            public String S_TYPE;

            @SerializedName("S_type_cn")
            public String S_TYPE_CN;
            public String S_DESC;
            public String S_LOCAL;
            public String S_IN_MAN;
            public String T_IN_DATE;
            public Object S_MANGE_MAN;
            public String T_MANGE_TIME;
            public String S_MANGE_REMARK;
            public String S_IS_MANGE;
            public String N_X;
            public String N_Y;
            public String S_STATUS;
            public String S_STATUS_CN;
            public String S_SJSB_ID;
            public String S_SJCZ_ID;
            public String S_SOURCE;
            public String S_SOURCE_CN;
            public String S_EMERGENCY;
            public String S_EMERGENCY_CN;
            public String S_TOWNID_IN;
            public String S_COMPANY_IN;
            public String S_IN_MAN_FULL;
            public String S_MANGE_FULL;
            public String S_TOWNID_MANGE;
            public Object S_COMPANY_MANGE;
            public String S_NAME;
            public String S_DELETE;
            public String T_CREATE;
            public String S_CREATE_MAN;
            public String S_RECODE_ID;
            public Object S_MANGE_STATUS;
            public String IS_TD;
            public String IS_JJ;
            public String IS_SJSB_FJ;
            public String IS_SJCZ_FJ;
            //管理员版本人员管理
            public String S_MAN_NAME;//陈龙金
            public String S_MAN_NAME_ABBREVIATION;//巡查人员
            public String S_COM_NAME;//上海闵捷工程建设有限公司
            public String S_TOWNID;//夏阳街道
            public String S_PHONE;
            public String STATUS;//离线
            //            public double   N_X ;//
//            public double   N_Y ;//
            public String S_TASK_TYPE;//W1007000001   1日常，3应急，2养护

            @Override
            public String toString() {
                return "PATROLMANAGEMENTBean{" +
                        "S_MANGE_ID='" + S_MANGE_ID + '\'' +
                        ", S_CATEGORY='" + S_CATEGORY + '\'' +
                        ", S_TYPE='" + S_TYPE + '\'' +
                        ", S_DESC=" + S_DESC +
                        ", S_LOCAL=" + S_LOCAL +
                        ", S_IN_MAN='" + S_IN_MAN + '\'' +
                        ", T_IN_DATE='" + T_IN_DATE + '\'' +
                        ", S_MANGE_MAN=" + S_MANGE_MAN +
                        ", T_MANGE_TIME=" + T_MANGE_TIME +
                        ", S_MANGE_REMARK=" + S_MANGE_REMARK +
                        ", S_IS_MANGE='" + S_IS_MANGE + '\'' +
                        ", N_X='" + N_X + '\'' +
                        ", N_Y='" + N_Y + '\'' +
                        ", S_STATUS='" + S_STATUS + '\'' +
                        ", S_SJSB_ID='" + S_SJSB_ID + '\'' +
                        ", S_SJCZ_ID='" + S_SJCZ_ID + '\'' +
                        ", S_SOURCE='" + S_SOURCE + '\'' +
                        ", S_EMERGENCY='" + S_EMERGENCY + '\'' +
                        ", S_TOWNID_IN='" + S_TOWNID_IN + '\'' +
                        ", S_COMPANY_IN='" + S_COMPANY_IN + '\'' +
                        ", S_IN_MAN_FULL='" + S_IN_MAN_FULL + '\'' +
                        ", S_MANGE_FULL='" + S_MANGE_FULL + '\'' +
                        ", S_TOWNID_MANGE='" + S_TOWNID_MANGE + '\'' +
                        ", S_COMPANY_MANGE=" + S_COMPANY_MANGE +
                        ", S_NAME='" + S_NAME + '\'' +
                        ", S_DELETE='" + S_DELETE + '\'' +
                        ", T_CREATE='" + T_CREATE + '\'' +
                        ", S_CREATE_MAN='" + S_CREATE_MAN + '\'' +
                        ", S_RECODE_ID='" + S_RECODE_ID + '\'' +
                        ", S_MANGE_STATUS=" + S_MANGE_STATUS +
                        '}';
            }
        }

}
