package com.wavenet.ding.qpps.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Create on 2019/5/27 18:53 by bll
 */


public class SearchHistory implements Serializable {

    /**
     * Code : 200
     * Msg : 查询成功!
     * Data : [{"ID":"c19d23088e714e37a13009285050726a","APPTYPE":1,"USERID":"xjtest","SEARCHVALUE":"路","SDATE":"2019-05-27 19:43:50"}]
     */

    private String Code;
    private String Msg;
    private List<DataBean> Data;
    public int Type;//2最后一个布局

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
         * ID : c19d23088e714e37a13009285050726a
         * APPTYPE : 1
         * USERID : xjtest
         * SEARCHVALUE : 路
         * SDATE : 2019-05-27 19:43:50
         */

        private String ID;
        private int APPTYPE;
        private String USERID;
        private String SEARCHVALUE;
        private String SDATE;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public int getAPPTYPE() {
            return APPTYPE;
        }

        public void setAPPTYPE(int APPTYPE) {
            this.APPTYPE = APPTYPE;
        }

        public String getUSERID() {
            return USERID;
        }

        public void setUSERID(String USERID) {
            this.USERID = USERID;
        }

        public String getSEARCHVALUE() {
            return SEARCHVALUE;
        }

        public void setSEARCHVALUE(String SEARCHVALUE) {
            this.SEARCHVALUE = SEARCHVALUE;
        }

        public String getSDATE() {
            return SDATE;
        }

        public void setSDATE(String SDATE) {
            this.SDATE = SDATE;
        }
    }
}
