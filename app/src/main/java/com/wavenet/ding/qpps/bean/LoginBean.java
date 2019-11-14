package com.wavenet.ding.qpps.bean;

import android.content.Context;

import com.wavenet.ding.qpps.utils.SPUtil;

import java.util.List;

/**
 * Created by ding on 2018/7/27.
 */

public class LoginBean {
    /**
     * code : 0000
     * msg : 成功
     * data : {"success":false,"messge":"","role":"巡查人员","roleId":"","name":"xjtest","myName":"巡检测试","personId":"1808092231532527656ae51d3f879","townId":"W1007400005","townName":"","company":"上海闵捷工程建设有限公司","des":"夏阳街道","pushList":["zhangyj","skhy","qudiaodu","xydd","chenx"]}
     * _dt : 1573179307323
     */

    private String code;
    private String msg;
    private DataBean data;
    private long _dt;
//    /**
//     * success : false
//     * messge : 用户名或者密码错误
//     */
//
//    public boolean success;
//    public String messge;
//    /**
//     * role : 巡查人员
//     */
//
//    public String des;//调度员街镇信息
//    public String role;
//    @SerializedName(value = "roleid",alternate = "roleId")
//    public String roleid;
//    public String name;
//    @SerializedName(value = "myname",alternate = "myName")
//    public String myname;//d:S_MAN_FULLNAME
//    @SerializedName(value = "personid",alternate = "personId")
//    public String personid;
//    @SerializedName(value = "townid",alternate = "townId")
//    public String townid;//
//    @SerializedName(value = "townname",alternate = "townName")
//    public String townname;//
//    public String company;
//    public List<String> PushList;



    public void setSPUtil(Context mContext) {
//        SPUtil.getInstance(mContext).setStringValue(SPUtil.APP_MYNAME, myname);
//        SPUtil.getInstance(mContext).setStringValue(SPUtil.APP_PERSONID, personid);
//        SPUtil.getInstance(mContext).setStringValue(SPUtil.APP_TOWNID, townid);
//        SPUtil.getInstance(mContext).setStringValue(SPUtil.APP_ROLE, role);
//        SPUtil.getInstance(mContext).setStringValue(SPUtil.APP_TOWNNAME, townname);
//        SPUtil.getInstance(mContext).setStringValue(SPUtil.APP_DES, des);
//        SPUtil.getInstance(mContext).setStringValue(SPUtil.APP_COMPANY, company);
//        SPUtil.getInstance(mContext).setObjectValue(SPUtil.APP_PUSH, PushList);

        SPUtil.getInstance(mContext).setStringValue(SPUtil.APP_MYNAME, getData().myName);
        SPUtil.getInstance(mContext).setStringValue(SPUtil.APP_PERSONID, getData().personId);
        SPUtil.getInstance(mContext).setStringValue(SPUtil.APP_TOWNID, getData().townId);
        SPUtil.getInstance(mContext).setStringValue(SPUtil.APP_ROLE, getData().role);
        SPUtil.getInstance(mContext).setStringValue(SPUtil.APP_TOWNNAME, getData().townName);
        SPUtil.getInstance(mContext).setStringValue(SPUtil.APP_DES, getData().des);
        SPUtil.getInstance(mContext).setStringValue(SPUtil.APP_COMPANY, getData().company);
        SPUtil.getInstance(mContext).setObjectValue(SPUtil.APP_PUSH, getData().pushList);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public long get_dt() {
        return _dt;
    }

    public void set_dt(long _dt) {
        this._dt = _dt;
    }

    public static class DataBean {
        /**
         * success : false
         * messge :
         * role : 巡查人员
         * roleId :
         * name : xjtest
         * myName : 巡检测试
         * personId : 1808092231532527656ae51d3f879
         * townId : W1007400005
         * townName :
         * company : 上海闵捷工程建设有限公司
         * des : 夏阳街道
         * pushList : ["zhangyj","skhy","qudiaodu","xydd","chenx"]
         */

        private boolean success;
        private String messge;
        private String role;
        private String roleId;
        private String name;
        private String myName;
        private String personId;
        private String townId;
        private String townName;
        private String company;
        private String des;
        private List<String> pushList;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessge() {
            return messge;
        }

        public void setMessge(String messge) {
            this.messge = messge;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMyName() {
            return myName;
        }

        public void setMyName(String myName) {
            this.myName = myName;
        }

        public String getPersonId() {
            return personId;
        }

        public void setPersonId(String personId) {
            this.personId = personId;
        }

        public String getTownId() {
            return townId;
        }

        public void setTownId(String townId) {
            this.townId = townId;
        }

        public String getTownName() {
            return townName;
        }

        public void setTownName(String townName) {
            this.townName = townName;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public List<String> getPushList() {
            return pushList;
        }

        public void setPushList(List<String> pushList) {
            this.pushList = pushList;
        }
    }
}
