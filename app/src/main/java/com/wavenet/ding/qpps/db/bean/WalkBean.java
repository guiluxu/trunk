package com.wavenet.ding.qpps.db.bean;


import com.amap.api.maps.model.LatLng;

/**
 * Created by Administrator on 2017/1/19.
 */

public class WalkBean {

    private double gonglishu;//公里数
    private double kaluli;//卡路里
    private int bushu;//步数
    private int tine;//时间

    private LatLng latLng;//经纬度坐标

    public double getGonglishu() {
        return gonglishu;
    }

    public void setGonglishu(double gonglishu) {
        this.gonglishu = gonglishu;
    }

    public int getTine() {
        return tine;
    }

    public void setTine(int tine) {
        this.tine = tine;
    }

    public double getKaluli() {
        return kaluli;
    }

    public void setKaluli(double kaluli) {
        this.kaluli = kaluli;
    }

    public int getBushu() {
        return bushu;
    }

    public void setBushu(int bushu) {
        this.bushu = bushu;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
