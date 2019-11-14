package com.wavenet.ding.qpps.db.bean;

/**
 * Created by Administrator on 2017/1/18.
 */

public class RidingInfoBean {
    private String uid; //用户ID
    private String tag;//开始时间
    private int state; //是否是上传状态
    private int type;//
    private String Rinfo;//骑行时定位到的信息

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRinfo() {
        return Rinfo;
    }

    public void setRinfo(String rinfo) {
        Rinfo = rinfo;
    }
}
