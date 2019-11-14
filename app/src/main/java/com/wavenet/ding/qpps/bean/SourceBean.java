package com.wavenet.ding.qpps.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.ArrayList;

public class SourceBean {
    /**
     * Code : 200
     * Msg : 查询成功!
     * Data : [{"S_CODE":"W10075","S_PCODE":null,"S_NAME":"任务状态","S_CORRESPOND":"W1007500001","S_VALUE":"群众举报","REMARK":"QZ","TIME":null},{"S_CODE":"W10075","S_PCODE":null,"S_NAME":"任务状态","S_CORRESPOND":"W1007500002","S_VALUE":"热线转交","REMARK":"RX","TIME":null},{"S_CODE":"W10075","S_PCODE":null,"S_NAME":"任务状态","S_CORRESPOND":"W1007500003","S_VALUE":"街镇自查","REMARK":"JZ","TIME":null},{"S_CODE":"W10075","S_PCODE":null,"S_NAME":"任务状态","S_CORRESPOND":"W1007500004","S_VALUE":"巡查上报","REMARK":"XC","TIME":null}]
     */

    public int options;
    private String Code;
    private String Msg;
    private ArrayList<DataBean> Data;

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

    public ArrayList<DataBean> getData() {
        return Data;
    }

    public void setData(ArrayList<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean implements IPickerViewData {
        /**
         * S_CODE : W10075
         * S_PCODE : null
         * S_NAME : 任务状态
         * S_CORRESPOND : W1007500001
         * S_VALUE : 群众举报
         * REMARK : QZ
         * TIME : null
         */

        private String S_CODE;
        private Object S_PCODE;
        private String S_NAME;
        private String S_CORRESPOND;
        private String S_VALUE;
        private String REMARK;
        private Object TIME;

        public String getS_CODE() {
            return S_CODE;
        }

        public void setS_CODE(String S_CODE) {
            this.S_CODE = S_CODE;
        }

        public Object getS_PCODE() {
            return S_PCODE;
        }

        public void setS_PCODE(Object S_PCODE) {
            this.S_PCODE = S_PCODE;
        }

        public String getS_NAME() {
            return S_NAME;
        }

        public void setS_NAME(String S_NAME) {
            this.S_NAME = S_NAME;
        }

        public String getS_CORRESPOND() {
            return S_CORRESPOND;
        }

        public void setS_CORRESPOND(String S_CORRESPOND) {
            this.S_CORRESPOND = S_CORRESPOND;
        }

        public String getS_VALUE() {
            return S_VALUE;
        }

        public void setS_VALUE(String S_VALUE) {
            this.S_VALUE = S_VALUE;
        }

        public String getREMARK() {
            return REMARK;
        }

        public void setREMARK(String REMARK) {
            this.REMARK = REMARK;
        }

        public Object getTIME() {
            return TIME;
        }

        public void setTIME(Object TIME) {
            this.TIME = TIME;
        }

        @Override
        public String getPickerViewText() {
            return S_VALUE;
        }
    }
}
