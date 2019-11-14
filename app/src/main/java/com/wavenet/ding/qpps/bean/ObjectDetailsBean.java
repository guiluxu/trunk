package com.wavenet.ding.qpps.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zoubeiwen on 2018/9/14.
 */

public class ObjectDetailsBean {

    public List<FeaturesBean> features;


    public static class FeaturesBean {
        /**
         * attributes : {"S_MANHOLE_NAME_ROAD":"纪鹤公路","N_MANHOLE_STATE":1,"N_WellForm":4,"N_MANHOLE_DEPTH":1.5,"N_MANHOLE_MATERIAL":1,"N_MANHOLE_LENGTH":600,"N_MANHOLE_ALT_GRD":4.61,"S_ManageUnit":"白鹤管网公司","N_MANHOLE_GRADE":1,"N_MANHOLE_TYPE":1}
         */

        @SerializedName("attributes")
        public AttributesBean attributes;
        /**
         * geometry : {"paths":[[[121.14740609962507,31.251214110632986],[121.14772129772552,31.25120082455851]]]}
         */

        @SerializedName("geometry")
        public GeometryBean geometry;

        /**
         * attributes : {"s_name":"品牌鞋子折扣店","s_address":"上海市青浦区京华路55号","s_type":"企事业单位","s_area":"青浦区","s_town":"徐泾镇"}
         */

        public static class AttributesBean {
            /**
             * 排水井
             * S_MANHOLE_NAME_ROAD : 纪鹤公路
             * N_MANHOLE_STATE : 1
             * N_WellForm : 4
             * N_MANHOLE_DEPTH : 1.5
             * N_MANHOLE_MATERIAL : 1
             * N_MANHOLE_LENGTH : 600
             * N_MANHOLE_ALT_GRD : 4.61
             * S_ManageUnit : 白鹤管网公司
             * N_MANHOLE_GRADE : 1
             * N_MANHOLE_TYPE : 1
             */


            @SerializedName("OBJECTID")
            public int OBJECTID;
            @SerializedName("S_MANHOLE_NAME_ROAD")
            public String SMANHOLENAMEROAD;
            @SerializedName("N_MANHOLE_STATE")
            public int NMANHOLESTATE;
            @SerializedName("N_WellForm")
            public int NWellForm;
            @SerializedName("N_MANHOLE_DEPTH")
            public double NMANHOLEDEPTH;
            @SerializedName("N_MANHOLE_MATERIAL")
            public int NMANHOLEMATERIAL;
            @SerializedName("N_MANHOLE_LENGTH")
            public String NMANHOLELENGTH;
            @SerializedName("N_MANHOLE_ALT_GRD")
            public double NMANHOLEALTGRD;
            @SerializedName("S_ManageUnit")
            public String SManageUnit;
            @SerializedName("N_MANHOLE_GRADE")
            public String NMANHOLEGRADE;
            @SerializedName("N_MANHOLE_TYPE")
            public int NMANHOLETYPE;
            @SerializedName("S_XTBM")
            public String SXTBM;
            @SerializedName("S_MANHOLE_ID")
            public String SMANHOLEID;
            @SerializedName("N_DRAI_PIPE_MATERIAL")
            public String NDRAI_PIPEMATERIAL;
            /**
             * * 排水管道
             * "S_DRAI_PIPE_NAME_ROAD": "鹤顺路",
             * "N_DRAI_PIPE_GRADE": 3,
             * "N_DRAI_PIPE_TYPE": 1,
             * "S_DRAI_PIPE_EROAD_NAME": "油墩港",
             * "N_DRAI_PIPE_STYLE": 1,
             * "N_DRAI_PIPE_D1": 500,
             * "N_DRAI_PIPE_LENGTH": 29.68,
             * "N_DRAI_PIPE_PALT_BEG": 2.3999999999999999,
             * "N_DRAI_PIPE_PALT_END": 2.5,
             * "S_DRAI_PIPE_BROAD_NAME": "鹤祥路",
             * "S_ManageUnit": "白鹤管网公司"
             */

            @SerializedName("S_DRAI_PIPE_NAME_ROAD")
            public String SDRAIPIPENAMEROAD;
            @SerializedName("N_DRAI_PIPE_GRADE")
            public int NDRAIPIPEGRADE;
            @SerializedName("N_DRAI_PIPE_TYPE")
            public int NDRAIPIPETYPE;
            @SerializedName("S_DRAI_PIPE_EROAD_NAME")
            public String SDRAIPIPEEROADNAME;
            @SerializedName("N_DRAI_PIPE_STYLE")
            public int NDRAIPIPESTYLE;
            @SerializedName("N_DRAI_PIPE_D1")
            public int NDRAIPIPED1;
            @SerializedName("N_DRAI_PIPE_LENGTH")
            public double NDRAIPIPELENGTH;
            @SerializedName("N_DRAI_PIPE_PALT_BEG")
            public double NDRAIPIPEPALTBEG;
            @SerializedName("N_DRAI_PIPE_PALT_END")
            public double NDRAIPIPEPALTEND;
            @SerializedName("S_DRAI_PIPE_BROAD_NAME")
            public String SDRAIPIPEBROADNAME;
//                @SerializedName("S_ManageUnit")
//                public String SManageUnit;//重复
//@SerializedName("S_XTBM")
//public int SXTBM;
            /**
             *
             * 排放口
             *   * S_MANHOLE_NAME_ROAD : 顾会浦路
             * N_MANHOLE_GRADE : null
             * N_MANHOLE_TYPE : 1
             * N_MANHOLE_STATE : 1
             * N_WellForm : null
             * N_MANHOLE_LENGTH : null
             * N_MANHOLE_ALT_GRD : 0.98
             * N_MANHOLE_DEPTH : 0
             */


//                @SerializedName("S_MANHOLE_NAME_ROAD")
//                public String SMANHOLENAMEROAD;
//                @SerializedName("N_MANHOLE_GRADE")
//                public Object NMANHOLEGRADE;
//                @SerializedName("N_MANHOLE_TYPE")
//                public int NMANHOLETYPE;
//                @SerializedName("N_MANHOLE_STATE")
//                public int NMANHOLESTATE;
//                @SerializedName("N_WellForm")
//                public Object NWellForm;
//                @SerializedName("N_MANHOLE_LENGTH")
//                public Object NMANHOLELENGTH;
//                @SerializedName("N_MANHOLE_ALT_GRD")
//                public double NMANHOLEALTGRD;
//                @SerializedName("N_MANHOLE_DEPTH")
//                public int NMANHOLEDEPTH;
            /**
             * 排水泵站
             * S_DRAI_PUMP_NAME : 朱家角污水厂珠溪路泵站
             * N_DRAI_PUMP_TYPE : 2
             * N_DRAI_PUMP_TYPE_FEAT : 5
             * S_DRAI_PUMP_ADD : 朱家角珠溪路浦漕平支路50米处
             * S_DRAI_PUMP_PHONE : null
             * S_DRAI_PUMP_COM_MNG : 上海朱家角污水处理工程建设有限公司
             */

            @SerializedName("S_DRAI_PUMP_NAME")
            public String SDRAIPUMPNAME;
            @SerializedName("N_DRAI_PUMP_TYPE")
            public String NDRAIPUMPTYPE;
            @SerializedName("N_DRAI_PUMP_TYPE_FEAT")
            public String NDRAIPUMPTYPEFEAT;
            @SerializedName("S_DRAI_PUMP_ADD")
            public String SDRAIPUMPADD;
            @SerializedName("S_DRAI_PUMP_PHONE")
            public Object SDRAIPUMPPHONE;
            @SerializedName("S_DRAI_PUMP_COM_MNG")
            public String SDRAIPUMPCOMMNG;
//            @SerializedName("S_XTBM")
//            public int SXTBM;
            /**
             * 污水处理厂
             * S_FACT_NAME : 大观园污水处理厂
             * S_FACT_ADD : NULL
             * S_FACT_PHONE : null
             * S_FACT_POST_CODE : null
             * N_FACT_CAP_DSN : null
             * S_FACT_METHOD_TREAT : null
             * S_FACT_LET_GRADE : null
             */

            @SerializedName("S_FACT_NAME")
            public String SFACTNAME;
            @SerializedName("S_FACT_ADD")
            public String SFACTADD;
            @SerializedName("S_FACT_PHONE")
            public Object SFACTPHONE;
            @SerializedName("S_FACT_POST_CODE")
            public Object SFACTPOSTCODE;
            @SerializedName("N_FACT_CAP_DSN")
            public Object NFACTCAPDSN;
            @SerializedName("S_FACT_METHOD_TREAT")
            public Object SFACTMETHODTREAT;
            @SerializedName("S_FACT_LET_GRADE")
            public Object SFACTLETGRADE;
            @SerializedName("S_FACT_ID")
            public String SFACTID;
            /**
             * 排水户
             * s_name : 品牌鞋子折扣店
             * s_address : 上海市青浦区京华路55号
             * s_type : 企事业单位
             * s_area : 青浦区
             * s_town : 徐泾镇
             */

            @SerializedName("S_NAME")
            public String sName;
            @SerializedName("S_ADDRESS")
            public String sAddress;
            @SerializedName("S_TYPE")
            public String sType;
            @SerializedName("S_AREA")
            public String sArea;
            @SerializedName("S_TOWN")
            public String sTown;
            @SerializedName("QPPSH_TABLE_ID")
            public String QPPSHTABLEID;
            /**
             * 重点排放口
             * S_ManageUnit : 上海市青浦区给水排水管理所
             * S_pairufangxiang : 青浦城河
             * S_SYS_NAME :
             * S_OutletNAME : 庆丰泵站排口
             */

//            @SerializedName("S_ManageUnit")
//            public String SManageUnit;
            @SerializedName("S_pairufangxiang")
            public String SPairufangxiang;
            @SerializedName("S_SYS_NAME")
            public String SSYSNAME;
            @SerializedName("S_OutletNAME")
            public String SOutletNAME;
            @SerializedName("S_OutletId")
            public String SOUTLETID;
        }

        /**
         * attributes : {"S_MANHOLE_ID":"04030202181039095"}
         * geometry : {"x":121.13899574225275,"y":31.248546008966798}
         * * geometry : {"paths":[[[121.14740609962507,31.251214110632986],[121.14772129772552,31.25120082455851]]]}
         */

        public static class GeometryBean {
            /**
             * x : 121.13899574225275
             * y : 31.248546008966798
             */

            @SerializedName("x")
            public double x;
            @SerializedName("y")
            public double y;
            @SerializedName("paths")
            public List<List<List<Double>>> paths;
        }

    }


}
