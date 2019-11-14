package com.wavenet.ding.qpps.bean;

import java.util.List;

/**
 * Created by ding on 2018/8/17.
 */

public class PicBean {


    public List<AppBean> app;

    public static class AppBean {
        /**
         * fieldname : file
         * originalname : ff2d65a1-c861-4dbc-8454-6fc5f6ed4662.jpg
         * encoding : 7bit
         * mimetype : image/jpeg
         * id : 5b76f91974aa671eaca3a270
         * filename : ff2d65a1-c861-4dbc-8454-6fc5f6ed4662.jpg
         * metadata : {"relyid":"1534494893395_YH_admin","yxfa":"1534494937434","x":"31.278393466759713","y":"121.53381104081107"}
         * bucketName : SSYH
         * chunkSize : 261120
         * size : 48954
         * md5 : 677fd26443e4bb5511d7792faa2d2b6f
         * uploadDate : 2018-08-17T16:34:33.653Z
         * contentType : image/jpeg
         */

        public String fieldname;
        public String originalname;
        public String encoding;
        public String mimetype;
        public String id;
        public String filename;
        public MetadataBean metadata;
        public String bucketName;
        public int chunkSize;
        public int size;
        public String md5;
        public String uploadDate;
        public String contentType;

        public static class MetadataBean {
            /**
             * relyid : 1534494893395_YH_admin
             * yxfa : 1534494937434
             * x : 31.278393466759713
             * y : 121.53381104081107
             */

            public String relyid;
            public String yxfa;
            public String x;
            public String y;
        }
    }
}
