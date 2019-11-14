package com.wavenet.ding.qpps.bean;

/**
 * Created by zoubeiwen on 2018/8/17.
 */

public class XYbean{
    private String code;
    private String msg;
    private DataBean data;
    private long _dt;

    @Override
    public String toString() {
        return "XYbean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", _dt=" + _dt +
                '}';
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
        private String sId;
        private String sManId;
        private String tUpload;
        private double nX;
        private double nY;
        private String tLastudTime;
        private double nLastX;
        private double nLastY;
        private String sTaskType;
        private String sRecordId;
        private double nSpeed;
        private double nSpeed1;
        private double nMileage;

        public String getsId() {
            return sId;
        }

        public void setsId(String sId) {
            this.sId = sId;
        }

        public String getsManId() {
            return sManId;
        }

        public void setsManId(String sManId) {
            this.sManId = sManId;
        }

        public String gettUpload() {
            return tUpload;
        }

        public void settUpload(String tUpload) {
            this.tUpload = tUpload;
        }

        public double getnX() {
            return nX;
        }

        public void setnX(double nX) {
            this.nX = nX;
        }

        public double getnY() {
            return nY;
        }

        public void setnY(double nY) {
            this.nY = nY;
        }

        public String gettLastudTime() {
            return tLastudTime;
        }

        public void settLastudTime(String tLastudTime) {
            this.tLastudTime = tLastudTime;
        }

        public double getnLastX() {
            return nLastX;
        }

        public void setnLastX(double nLastX) {
            this.nLastX = nLastX;
        }

        public double getnLastY() {
            return nLastY;
        }

        public void setnLastY(double nLastY) {
            this.nLastY = nLastY;
        }

        public String getsTaskType() {
            return sTaskType;
        }

        public void setsTaskType(String sTaskType) {
            this.sTaskType = sTaskType;
        }

        public String getsRecordId() {
            return sRecordId;
        }

        public void setsRecordId(String sRecordId) {
            this.sRecordId = sRecordId;
        }

        public double getnSpeed() {
            return nSpeed;
        }

        public void setnSpeed(double nSpeed) {
            this.nSpeed = nSpeed;
        }

        public double getnSpeed1() {
            return nSpeed1;
        }

        public void setnSpeed1(double nSpeed1) {
            this.nSpeed1 = nSpeed1;
        }

        public double getnMileage() {
            return nMileage;
        }

        public void setnMileage(double nMileage) {
            this.nMileage = nMileage;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "sId='" + sId + '\'' +
                    ", sManId='" + sManId + '\'' +
                    ", tUpload='" + tUpload + '\'' +
                    ", nX=" + nX +
                    ", nY=" + nY +
                    ", tLastudTime='" + tLastudTime + '\'' +
                    ", nLastX=" + nLastX +
                    ", nLastY=" + nLastY +
                    ", sTaskType='" + sTaskType + '\'' +
                    ", sRecordId='" + sRecordId + '\'' +
                    ", nSpeed=" + nSpeed +
                    ", nSpeed1=" + nSpeed1 +
                    ", nMileage=" + nMileage +
                    '}';
        }
    }
}
