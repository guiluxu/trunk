package com.wavenet.ding.qpps.bean;

/**
 * Created by ding on 2018/8/29.
 */

public class LL2Bean {


    /**
     * jz : {"status":200,"msg":"OK","data":0}
     * cj : {"status":200,"msg":"OK","data":0}
     */

    public JzBean jz;
    public CjBean cj;
    public QxBean qx;

    public static class JzBean {
        /**
         * status : 200
         * msg : OK
         * data : 0
         */

        public String status;
        public String msg;
        public String data;
    }

    public static class CjBean {
        /**
         * status : 200
         * msg : OK
         * data : 0
         */

        public String status;
        public String msg;
        public String data;
    }

    public static class QxBean {
        /**
         * status : 200
         * msg : OK
         * data : 0
         */

        public String status;
        public String msg;
        public String data;
    }
}
