package com.wavenet.ding.qpps.bean;

import java.util.List;

public class TestClassBean {


    /**
     * t_person_collection : {"t_person":[{"S_MAN_ID":"13818988747","S_MAN_NAME":"杨冬敏","S_MAN_NAME_ABBREVIATION":"巡查人员","S_COM_FULLNAME":null,"S_COM_NAME":"上海闵捷工程建设有限公司","S_TOWNID":"夏阳街道","S_PHONE":"13818988747","N_STATUS":null,"S_ID":"19031222204787221844c0ec23539","D_UPDATE":"15-3月 -19","STATUS":"离线","N_X":"121.14321415974263","N_Y":"31.16533940232104","T_LASTUDTIME":null,"N_LAST_X":null,"N_LAST_Y":null,"S_TASK_TYPE":"W1007000001","S_RECORD_ID":"XJ155260923967713818988747","N_SPEED":"0","N_SPEED1":null,"HDPIC":null,"S_TOWNOWNID":"W1007400005"},{"S_MAN_ID":"13916363071","S_MAN_NAME":"全新","S_MAN_NAME_ABBREVIATION":"巡查人员","S_COM_FULLNAME":null,"S_COM_NAME":"上海闵捷工程建设有限公司","S_TOWNID":"夏阳街道","S_PHONE":"13916363071","N_STATUS":null,"S_ID":"1903141332346453515042000437f","D_UPDATE":"20-5月 -19","STATUS":"离线","N_X":"121.14407441388715","N_Y":"31.163426946052002","T_LASTUDTIME":null,"N_LAST_X":null,"N_LAST_Y":null,"S_TASK_TYPE":"W1007000001","S_RECORD_ID":"XJ155831289790113916363071","N_SPEED":"1.9","N_SPEED1":null,"HDPIC":null,"S_TOWNOWNID":"W1007400005"},{"S_MAN_ID":"18930954686","S_MAN_NAME":"陈龙金","S_MAN_NAME_ABBREVIATION":"巡查人员","S_COM_FULLNAME":null,"S_COM_NAME":"上海闵捷工程建设有限公司","S_TOWNID":"夏阳街道","S_PHONE":"18930954686","N_STATUS":null,"S_ID":"18110814164253162571a51b39ed1","D_UPDATE":"29-1月 -19","STATUS":"离线","N_X":"121.14742861401574","N_Y":"31.16844985652358","T_LASTUDTIME":null,"N_LAST_X":null,"N_LAST_Y":null,"S_TASK_TYPE":"W1007000001","S_RECORD_ID":"XJ154872322167518930954686","N_SPEED":"1.25","N_SPEED1":null,"HDPIC":null,"S_TOWNOWNID":"W1007400005"},{"S_MAN_ID":"cbxj1","S_MAN_NAME":"郑海涛","S_MAN_NAME_ABBREVIATION":"巡查人员","S_COM_FULLNAME":null,"S_COM_NAME":"上海畅北市政设施养护有限公司","S_TOWNID":"夏阳街道","S_PHONE":null,"N_STATUS":null,"S_ID":"1808291906265109855faf8b731d0","D_UPDATE":"26-10月-18","STATUS":"离线","N_X":"121.534210424096","N_Y":"31.27864226512879","T_LASTUDTIME":null,"N_LAST_X":null,"N_LAST_Y":null,"S_TASK_TYPE":"W1007000001","S_RECORD_ID":"XJ1540533897255cbxj1","N_SPEED":"0","N_SPEED1":null,"HDPIC":null,"S_TOWNOWNID":"W1007400005"},{"S_MAN_ID":"hlxj1","S_MAN_NAME":"孙成名","S_MAN_NAME_ABBREVIATION":"巡查人员","S_COM_FULLNAME":null,"S_COM_NAME":"上海河路建筑工程有限公司","S_TOWNID":"华新镇","S_PHONE":null,"N_STATUS":null,"S_ID":"1808291901070926412dab0929cb4","D_UPDATE":"08-11月-18","STATUS":"离线","N_X":"121.5335637515495","N_Y":"31.278055552247725","T_LASTUDTIME":null,"N_LAST_X":null,"N_LAST_Y":null,"S_TASK_TYPE":"W1007000001","S_RECORD_ID":"XJ1541658757213hlxj1","N_SPEED":".23","N_SPEED1":null,"HDPIC":null,"S_TOWNOWNID":"W1007400002"},{"S_MAN_ID":"hmxj1","S_MAN_NAME":"冯秀明","S_MAN_NAME_ABBREVIATION":"巡查人员","S_COM_FULLNAME":null,"S_COM_NAME":"上海青浦惠民建设发展有限公司","S_TOWNID":"盈浦街道","S_PHONE":"15221032331","N_STATUS":null,"S_ID":"1808291806536777068ea66b775a1","D_UPDATE":"20-5月 -19","STATUS":"离线","N_X":"121.09963031265126","N_Y":"31.154574989736947","T_LASTUDTIME":null,"N_LAST_X":null,"N_LAST_Y":null,"S_TASK_TYPE":"W1007000001","S_RECORD_ID":"XJ1558316392706hmxj1","N_SPEED":"0","N_SPEED1":null,"HDPIC":null,"S_TOWNOWNID":"W1007400008"},{"S_MAN_ID":"hmxj2","S_MAN_NAME":"丁菊生","S_MAN_NAME_ABBREVIATION":"巡查人员","S_COM_FULLNAME":null,"S_COM_NAME":"上海青浦惠民建设发展有限公司","S_TOWNID":"盈浦街道","S_PHONE":"18717987821","N_STATUS":null,"S_ID":"190312222450652721002317af0a3","D_UPDATE":"20-5月 -19","STATUS":"离线","N_X":"121.1126810274602","N_Y":"31.161509351608963","T_LASTUDTIME":null,"N_LAST_X":null,"N_LAST_Y":null,"S_TASK_TYPE":"W1007000001","S_RECORD_ID":"XJ1558311138711hmxj2","N_SPEED":"0","N_SPEED1":null,"HDPIC":null,"S_TOWNOWNID":"W1007400008"}]}
     */

    private TPersonCollectionBean t_person_collection;

    public TPersonCollectionBean getT_person_collection() {
        return t_person_collection;
    }

    public void setT_person_collection(TPersonCollectionBean t_person_collection) {
        this.t_person_collection = t_person_collection;
    }

    public static class TPersonCollectionBean {
        private List<TPersonBean> t_person;

        public List<TPersonBean> getT_person() {
            return t_person;
        }

        public void setT_person(List<TPersonBean> t_person) {
            this.t_person = t_person;
        }

        public static class TPersonBean {
            /**
             * S_MAN_ID : 13818988747
             * S_MAN_NAME : 杨冬敏
             * S_MAN_NAME_ABBREVIATION : 巡查人员
             * S_COM_FULLNAME : null
             * S_COM_NAME : 上海闵捷工程建设有限公司
             * S_TOWNID : 夏阳街道
             * S_PHONE : 13818988747
             * N_STATUS : null
             * S_ID : 19031222204787221844c0ec23539
             * D_UPDATE : 15-3月 -19
             * STATUS : 离线
             * N_X : 121.14321415974263
             * N_Y : 31.16533940232104
             * T_LASTUDTIME : null
             * N_LAST_X : null
             * N_LAST_Y : null
             * S_TASK_TYPE : W1007000001
             * S_RECORD_ID : XJ155260923967713818988747
             * N_SPEED : 0
             * N_SPEED1 : null
             * HDPIC : null
             * S_TOWNOWNID : W1007400005
             */

            private String S_MAN_ID;
            private String S_MAN_NAME;
            private String S_MAN_NAME_ABBREVIATION;
            private Object S_COM_FULLNAME;
            private String S_COM_NAME;
            private String S_TOWNID;
            private String S_PHONE;
            private Object N_STATUS;
            private String S_ID;
            private String D_UPDATE;
            private String STATUS;
            private String N_X;
            private String N_Y;
            private Object T_LASTUDTIME;
            private Object N_LAST_X;
            private Object N_LAST_Y;
            private String S_TASK_TYPE;
            private String S_RECORD_ID;
            private String N_SPEED;
            private Object N_SPEED1;
            private Object HDPIC;
            private String S_TOWNOWNID;

            public String getS_MAN_ID() {
                return S_MAN_ID;
            }

            public void setS_MAN_ID(String S_MAN_ID) {
                this.S_MAN_ID = S_MAN_ID;
            }

            public String getS_MAN_NAME() {
                return S_MAN_NAME;
            }

            public void setS_MAN_NAME(String S_MAN_NAME) {
                this.S_MAN_NAME = S_MAN_NAME;
            }

            public String getS_MAN_NAME_ABBREVIATION() {
                return S_MAN_NAME_ABBREVIATION;
            }

            public void setS_MAN_NAME_ABBREVIATION(String S_MAN_NAME_ABBREVIATION) {
                this.S_MAN_NAME_ABBREVIATION = S_MAN_NAME_ABBREVIATION;
            }

            public Object getS_COM_FULLNAME() {
                return S_COM_FULLNAME;
            }

            public void setS_COM_FULLNAME(Object S_COM_FULLNAME) {
                this.S_COM_FULLNAME = S_COM_FULLNAME;
            }

            public String getS_COM_NAME() {
                return S_COM_NAME;
            }

            public void setS_COM_NAME(String S_COM_NAME) {
                this.S_COM_NAME = S_COM_NAME;
            }

            public String getS_TOWNID() {
                return S_TOWNID;
            }

            public void setS_TOWNID(String S_TOWNID) {
                this.S_TOWNID = S_TOWNID;
            }

            public String getS_PHONE() {
                return S_PHONE;
            }

            public void setS_PHONE(String S_PHONE) {
                this.S_PHONE = S_PHONE;
            }

            public Object getN_STATUS() {
                return N_STATUS;
            }

            public void setN_STATUS(Object N_STATUS) {
                this.N_STATUS = N_STATUS;
            }

            public String getS_ID() {
                return S_ID;
            }

            public void setS_ID(String S_ID) {
                this.S_ID = S_ID;
            }

            public String getD_UPDATE() {
                return D_UPDATE;
            }

            public void setD_UPDATE(String D_UPDATE) {
                this.D_UPDATE = D_UPDATE;
            }

            public String getSTATUS() {
                return STATUS;
            }

            public void setSTATUS(String STATUS) {
                this.STATUS = STATUS;
            }

            public String getN_X() {
                return N_X;
            }

            public void setN_X(String N_X) {
                this.N_X = N_X;
            }

            public String getN_Y() {
                return N_Y;
            }

            public void setN_Y(String N_Y) {
                this.N_Y = N_Y;
            }

            public Object getT_LASTUDTIME() {
                return T_LASTUDTIME;
            }

            public void setT_LASTUDTIME(Object T_LASTUDTIME) {
                this.T_LASTUDTIME = T_LASTUDTIME;
            }

            public Object getN_LAST_X() {
                return N_LAST_X;
            }

            public void setN_LAST_X(Object N_LAST_X) {
                this.N_LAST_X = N_LAST_X;
            }

            public Object getN_LAST_Y() {
                return N_LAST_Y;
            }

            public void setN_LAST_Y(Object N_LAST_Y) {
                this.N_LAST_Y = N_LAST_Y;
            }

            public String getS_TASK_TYPE() {
                return S_TASK_TYPE;
            }

            public void setS_TASK_TYPE(String S_TASK_TYPE) {
                this.S_TASK_TYPE = S_TASK_TYPE;
            }

            public String getS_RECORD_ID() {
                return S_RECORD_ID;
            }

            public void setS_RECORD_ID(String S_RECORD_ID) {
                this.S_RECORD_ID = S_RECORD_ID;
            }

            public String getN_SPEED() {
                return N_SPEED;
            }

            public void setN_SPEED(String N_SPEED) {
                this.N_SPEED = N_SPEED;
            }

            public Object getN_SPEED1() {
                return N_SPEED1;
            }

            public void setN_SPEED1(Object N_SPEED1) {
                this.N_SPEED1 = N_SPEED1;
            }

            public Object getHDPIC() {
                return HDPIC;
            }

            public void setHDPIC(Object HDPIC) {
                this.HDPIC = HDPIC;
            }

            public String getS_TOWNOWNID() {
                return S_TOWNOWNID;
            }

            public void setS_TOWNOWNID(String S_TOWNOWNID) {
                this.S_TOWNOWNID = S_TOWNOWNID;
            }
        }
    }
}
