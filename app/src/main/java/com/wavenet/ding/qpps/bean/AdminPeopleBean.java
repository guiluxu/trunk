package com.wavenet.ding.qpps.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zoubeiwen on 2018/11/11.
 */

public class AdminPeopleBean {

    /**
     * t_person_collection : {"t_person":[{"S_MAN_ID":"18930954686","S_MAN_NAME":"陈龙金","S_MAN_NAME_ABBREVIATION":"巡查人员","S_COM_FULLNAME":null,"S_COM_NAME":"上海闵捷工程建设有限公司","S_TOWNID":"夏阳街道","S_PHONE":null,"N_STATUS":null,"S_ID":"1541820395121user","D_UPDATE":"2018-11-10T19:26:35.000+08:00","STATUS":"离线","N_X":"121.14818319236636","N_Y":"31.17318452229596","T_LASTUDTIME":null,"N_LAST_X":null,"N_LAST_Y":null,"S_TASK_TYPE":"W1007000001","S_RECORD_ID":"XJ154182036491518930954686","N_SPEED":"0","N_SPEED1":null},{"S_MAN_ID":"cbxj1","S_MAN_NAME":"郑十","S_MAN_NAME_ABBREVIATION":"巡查人员","S_COM_FULLNAME":null,"S_COM_NAME":"上海畅北市政设施养护有限公司","S_TOWNID":"夏阳街道","S_PHONE":null,"N_STATUS":null,"S_ID":"1540533927685user","D_UPDATE":"2018-10-26T22:05:27.000+08:00","STATUS":"离线","N_X":"121.534210424096","N_Y":"31.27864226512879","T_LASTUDTIME":null,"N_LAST_X":null,"N_LAST_Y":null,"S_TASK_TYPE":"W1007000001","S_RECORD_ID":"XJ1540533897255cbxj1","N_SPEED":"0","N_SPEED1":null},{"S_MAN_ID":"hlxj1","S_MAN_NAME":"孙七","S_MAN_NAME_ABBREVIATION":"巡查人员","S_COM_FULLNAME":null,"S_COM_NAME":"上海河路建筑工程有限公司","S_TOWNID":"华新镇","S_PHONE":null,"N_STATUS":null,"S_ID":"1541658757469user","D_UPDATE":"2018-11-08T22:32:37.000+08:00","STATUS":"离线","N_X":"121.5335637515495","N_Y":"31.278055552247725","T_LASTUDTIME":null,"N_LAST_X":null,"N_LAST_Y":null,"S_TASK_TYPE":"W1007000001","S_RECORD_ID":"XJ1541658757213hlxj1","N_SPEED":"0.23","N_SPEED1":null},{"S_MAN_ID":"hmxj1","S_MAN_NAME":"冯秀明","S_MAN_NAME_ABBREVIATION":"巡查人员","S_COM_FULLNAME":null,"S_COM_NAME":"上海青浦惠民建设发展有限公司","S_TOWNID":"盈浦街道","S_PHONE":null,"N_STATUS":null,"S_ID":"1541924304119user","D_UPDATE":"2018-11-12T00:18:24.000+08:00","STATUS":"离线","N_X":"121.09535746041465","N_Y":"31.17035901110291","T_LASTUDTIME":null,"N_LAST_X":null,"N_LAST_Y":null,"S_TASK_TYPE":"W1007000001","S_RECORD_ID":"XJ1541924294027hmxj1","N_SPEED":"0","N_SPEED1":null},{"S_MAN_ID":"hmyh1","S_MAN_NAME":"杜和生","S_MAN_NAME_ABBREVIATION":"养护人员","S_COM_FULLNAME":null,"S_COM_NAME":"上海青浦惠民建设发展有限公司","S_TOWNID":"盈浦街道","S_PHONE":null,"N_STATUS":null,"S_ID":"1541916129581","D_UPDATE":"2018-11-11T22:02:09.000+08:00","STATUS":"离线","N_X":"121.093882","N_Y":"31.134117","T_LASTUDTIME":null,"N_LAST_X":null,"N_LAST_Y":null,"S_TASK_TYPE":"W1007000002","S_RECORD_ID":null,"N_SPEED":"0.16","N_SPEED1":null},{"S_MAN_ID":"xjtest","S_MAN_NAME":"巡检测试","S_MAN_NAME_ABBREVIATION":"巡查人员","S_COM_FULLNAME":null,"S_COM_NAME":"上海闵捷工程建设有限公司","S_TOWNID":"白鹤镇","S_PHONE":null,"N_STATUS":null,"S_ID":"1541920698406user","D_UPDATE":"2018-11-11T23:18:18.000+08:00","STATUS":"离线","N_X":"121.53395613755151","N_Y":"31.278566039238964","T_LASTUDTIME":null,"N_LAST_X":null,"N_LAST_Y":null,"S_TASK_TYPE":"W1007000001","S_RECORD_ID":"XJ1541920687839xjtest","N_SPEED":"0.03","N_SPEED1":null},{"S_MAN_ID":"yhtest","S_MAN_NAME":"养护测试","S_MAN_NAME_ABBREVIATION":"养护人员","S_COM_FULLNAME":null,"S_COM_NAME":"上海闵捷工程建设有限公司","S_TOWNID":"白鹤镇","S_PHONE":null,"N_STATUS":null,"S_ID":"1541924814424","D_UPDATE":"2018-11-12T00:26:54.000+08:00","STATUS":"离线","N_X":"121.53834906684028","N_Y":"31.27648681640625","T_LASTUDTIME":null,"N_LAST_X":null,"N_LAST_Y":null,"S_TASK_TYPE":"W1007000002","S_RECORD_ID":null,"N_SPEED":"0","N_SPEED1":null},{"S_MAN_ID":"yhycb","S_MAN_NAME":"杨朝斌","S_MAN_NAME_ABBREVIATION":"养护人员","S_COM_FULLNAME":null,"S_COM_NAME":"上海闵捷工程建设有限公司","S_TOWNID":"夏阳街道","S_PHONE":null,"N_STATUS":null,"S_ID":"1541146920764","D_UPDATE":"2018-11-03T00:22:00.000+08:00","STATUS":"离线","N_X":"121.13674343532986","N_Y":"31.15192409939236","T_LASTUDTIME":null,"N_LAST_X":null,"N_LAST_Y":null,"S_TASK_TYPE":"W1007000002","S_RECORD_ID":null,"N_SPEED":"0.71","N_SPEED1":null}]}
     */

    @SerializedName("t_person_collection")
    public TPersonCollectionBean tPersonCollection;

    public static class TPersonCollectionBean {
        @SerializedName("t_person")
        public List<TPersonBean> tPerson;

        public static class TPersonBean {
            /**
             * S_MAN_ID : 18930954686
             * S_MAN_NAME : 陈龙金
             * S_MAN_NAME_ABBREVIATION : 巡查人员
             * S_COM_FULLNAME : null
             * S_COM_NAME : 上海闵捷工程建设有限公司
             * S_TOWNID : 夏阳街道
             * S_PHONE : null
             * N_STATUS : null
             * S_ID : 1541820395121user
             * D_UPDATE : 2018-11-10T19:26:35.000+08:00
             * STATUS : 离线
             * N_X : 121.14818319236636
             * N_Y : 31.17318452229596
             * T_LASTUDTIME : null
             * N_LAST_X : null
             * N_LAST_Y : null
             * S_TASK_TYPE : W1007000001
             * S_RECORD_ID : XJ154182036491518930954686
             * N_SPEED : 0
             * N_SPEED1 : null
             * S_PHONE : 18930954686
             * HDPIC : 头像
             */


/*            {
                    "S_MAN_ID": "13818988747",
                    "S_MAN_NAME": "杨冬敏",
                    "S_MAN_NAME_ABBREVIATION": "巡查人员",
                    "S_COM_FULLNAME": null,
                    "S_COM_NAME": "上海闵捷工程建设有限公司",
                    "S_TOWNID": "夏阳街道",
                    "S_PHONE": "13818988747",
                    "N_STATUS": null,
                    "S_ID": "19031222204787221844c0ec23539",
                    "D_UPDATE": "15-3月 -19",
                    "STATUS": "离线",
                    "N_X": "121.14321415974263",
                    "N_Y": "31.16533940232104",
                    "T_LASTUDTIME": null,
                    "N_LAST_X": null,
                    "N_LAST_Y": null,
                    "S_TASK_TYPE": "W1007000001",
                    "S_RECORD_ID": "XJ155260923967713818988747",
                    "N_SPEED": "0",
                    "N_SPEED1": null,
                    "HDPIC": null,
                    "S_TOWNOWNID": "W1007400005"
            }*/


            @SerializedName("S_MAN_ID")
            public String SMANID;
            @SerializedName("S_MAN_NAME")
            public String SMANNAME;
            @SerializedName("S_MAN_NAME_ABBREVIATION")
            public String SMANNAMEABBREVIATION;
            @SerializedName("S_COM_FULLNAME")
            public Object SCOMFULLNAME;
            @SerializedName("S_COM_NAME")
            public String SCOMNAME;
            @SerializedName("S_TOWNID")
            public String STOWNID;
            @SerializedName("S_PHONE")
            public Object SPHONE;
            @SerializedName("N_STATUS")
            public Object NSTATUS;
            @SerializedName("S_ID")
            public String SID;
            @SerializedName("D_UPDATE")
            public String DUPDATE;
            @SerializedName("STATUS")
            public String STATUS;
            @SerializedName("N_X")
            public String NX;
            @SerializedName("N_Y")
            public String NY;
            @SerializedName("T_LASTUDTIME")
            public Object TLASTUDTIME;
            @SerializedName("N_LAST_X")
            public Object NLASTX;
            @SerializedName("N_LAST_Y")
            public Object NLASTY;
            @SerializedName("S_TASK_TYPE")
            public String STASKTYPE;
            @SerializedName("S_RECORD_ID")
            public String SRECORDID;
            @SerializedName("N_SPEED")
            public String NSPEED;
            @SerializedName("N_SPEED1")
            public Object NSPEED1;
            @SerializedName("HDPIC")
            public Object NHDPIC;
            @SerializedName("S_TOWNOWNID")
            public String NTOWNOWNID;


        }
    }
}
