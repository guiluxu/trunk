package com.wavenet.ding.qpps.bean;

import java.util.List;

public class AdminPersonBean2 {

    /**
     * Code : 200
     * Msg : 查询成功!
     * Data : [{"S_MAN_ID":"chenjw","S_MAN_NAME":"陈金稳","S_MAN_NAME_ABBREVIATION":"养护人员","S_COM_FULLNAME":null,"S_COM_NAME":"上海重启建筑装饰工程有限公司","S_TOWNID":"徐泾镇","S_TOWNOWNID":"W1007400007","S_PHONE":"13761981338","N_STATUS":null,"S_ID":"1903122211084555110c8f9b1ffe0","D_UPDATE":"2019-05-25 16:03:27","STATUS":"离线","N_X":121.25288369891766,"N_Y":31.178821462192197,"T_LASTUDTIME":null,"N_LAST_X":null,"N_LAST_Y":null,"S_TASK_TYPE":"W1007000002","S_RECORD_ID":"1558771267852_YH_chenjw","N_SPEED":0.64,"N_SPEED1":null,"HDPIC":null},{"S_MAN_ID":"sunjx","S_MAN_NAME":"孙建新","S_MAN_NAME_ABBREVIATION":"巡查人员","S_COM_FULLNAME":null,"S_COM_NAME":"上海重启建筑装饰工程有限公司","S_TOWNID":"盈浦街道","S_TOWNOWNID":"W1007400008","S_PHONE":"19901757692","N_STATUS":null,"S_ID":"190312220515039354290b89ea976","D_UPDATE":"2019-05-26 09:55:18","STATUS":"离线","N_X":121.0829565276753,"N_Y":31.139197551009794,"T_LASTUDTIME":null,"N_LAST_X":null,"N_LAST_Y":null,"S_TASK_TYPE":"W1007000001","S_RECORD_ID":"XJ1558835537684sunjx","N_SPEED":11.68,"N_SPEED1":null,"HDPIC":null},{"S_MAN_ID":"zhuxg","S_MAN_NAME":"朱学干","S_MAN_NAME_ABBREVIATION":"巡查人员","S_COM_FULLNAME":null,"S_COM_NAME":"上海重启建筑装饰工程有限公司","S_TOWNID":"徐泾镇","S_TOWNOWNID":"W1007400007","S_PHONE":"13916220786","N_STATUS":null,"S_ID":"1903122210053166833bcafb6c167","D_UPDATE":"2019-05-15 15:40:49","STATUS":"离线","N_X":121.26247230328865,"N_Y":31.185813842802165,"T_LASTUDTIME":null,"N_LAST_X":null,"N_LAST_Y":null,"S_TASK_TYPE":"W1007000001","S_RECORD_ID":"XJ1557906048981zhuxg","N_SPEED":0,"N_SPEED1":null,"HDPIC":null},{"S_MAN_ID":"zqszxc","S_MAN_NAME":"朱召龙","S_MAN_NAME_ABBREVIATION":"巡查人员","S_COM_FULLNAME":null,"S_COM_NAME":"上海重启建筑装饰工程有限公司","S_TOWNID":"盈浦街道","S_TOWNOWNID":"W1007400008","S_PHONE":"15300579005","N_STATUS":null,"S_ID":"1903141338585761008a27a98a3fc","D_UPDATE":"2019-05-28 08:19:47","STATUS":"离线","N_X":121.08241170082732,"N_Y":31.148414924920882,"T_LASTUDTIME":null,"N_LAST_X":null,"N_LAST_Y":null,"S_TASK_TYPE":"W1007000001","S_RECORD_ID":"XJ1559002772372zqszxc","N_SPEED":0,"N_SPEED1":null,"HDPIC":null},{"S_MAN_ID":"zqszyh","S_MAN_NAME":"杨德镜","S_MAN_NAME_ABBREVIATION":"养护人员","S_COM_FULLNAME":null,"S_COM_NAME":"上海重启建筑装饰工程有限公司","S_TOWNID":"盈浦街道","S_TOWNOWNID":"W1007400008","S_PHONE":"18202117713","N_STATUS":null,"S_ID":"19031413413387499359d4a50991f","D_UPDATE":"2019-05-24 14:39:39","STATUS":"离线","N_X":121.08489472944007,"N_Y":31.15081478556349,"T_LASTUDTIME":null,"N_LAST_X":null,"N_LAST_Y":null,"S_TASK_TYPE":"W1007000002","S_RECORD_ID":"1558665105785_YH_zqszyh","N_SPEED":1.1,"N_SPEED1":null,"HDPIC":null},{"S_MAN_ID":"wangah","S_MAN_NAME":"王爱华","S_MAN_NAME_ABBREVIATION":"巡查人员","S_COM_FULLNAME":null,"S_COM_NAME":"上海重启建筑装饰工程有限公司","S_TOWNID":"徐泾镇","S_TOWNOWNID":"W1007400007","S_PHONE":"15189966018","N_STATUS":null,"S_ID":"190312220835453354939200c8c9e","D_UPDATE":null,"STATUS":"离线","N_X":null,"N_Y":null,"T_LASTUDTIME":null,"N_LAST_X":null,"N_LAST_Y":null,"S_TASK_TYPE":null,"S_RECORD_ID":null,"N_SPEED":null,"N_SPEED1":null,"HDPIC":null}]
     */

    private String Code;
    private String Msg;
    private List<DataBean> Data;

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * S_MAN_ID : chenjw
         * S_MAN_NAME : 陈金稳
         * S_MAN_NAME_ABBREVIATION : 养护人员
         * S_COM_FULLNAME : null
         * S_COM_NAME : 上海重启建筑装饰工程有限公司
         * S_TOWNID : 徐泾镇
         * S_TOWNOWNID : W1007400007
         * S_PHONE : 13761981338
         * N_STATUS : null
         * S_ID : 1903122211084555110c8f9b1ffe0
         * D_UPDATE : 2019-05-25 16:03:27
         * STATUS : 离线
         * N_X : 121.25288369891766
         * N_Y : 31.178821462192197
         * T_LASTUDTIME : null
         * N_LAST_X : null
         * N_LAST_Y : null
         * S_TASK_TYPE : W1007000002
         * S_RECORD_ID : 1558771267852_YH_chenjw
         * N_SPEED : 0.64
         * N_SPEED1 : null
         * HDPIC : null
         */

        private String S_MAN_ID;
        private String S_MAN_NAME;
        private String S_MAN_NAME_ABBREVIATION;
        private Object S_COM_FULLNAME;
        private String S_COM_NAME;
        private String S_TOWNID;
        private String S_TOWNOWNID;
        private String S_PHONE;
        private Object N_STATUS;
        private String S_ID;
        private String D_UPDATE;
        private String STATUS;
        private double N_X;
        private double N_Y;
        private Object T_LASTUDTIME;
        private Object N_LAST_X;
        private Object N_LAST_Y;
        private String S_TASK_TYPE;
        private String S_RECORD_ID;
        private double N_SPEED;
        private Object N_SPEED1;
        private Object HDPIC;

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

        public String getS_TOWNOWNID() {
            return S_TOWNOWNID;
        }

        public void setS_TOWNOWNID(String S_TOWNOWNID) {
            this.S_TOWNOWNID = S_TOWNOWNID;
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

        public double getN_X() {
            return N_X;
        }

        public void setN_X(double N_X) {
            this.N_X = N_X;
        }

        public double getN_Y() {
            return N_Y;
        }

        public void setN_Y(double N_Y) {
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

        public double getN_SPEED() {
            return N_SPEED;
        }

        public void setN_SPEED(double N_SPEED) {
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
    }
}
