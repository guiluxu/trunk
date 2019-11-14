package com.wavenet.ding.qpps.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zoubeiwen on 2018/8/22.
 */

public class PhtotIdBean {

    @SerializedName("app")
    public List<AppBean> app;

    public static class AppBean {
        /**
         * fieldname : file
         * originalname : magazine-unlock-01-2.3.1080-_2F9AC872A6F0BA8AAAE0F678DC7A8BE8.jpg
         * encoding : 7bit
         * mimetype : image/jpeg
         * id : 5b7d8f574073ef13b45a62bd
         * filename : magazine-unlock-01-2.3.1080-_2F9AC872A6F0BA8AAAE0F678DC7A8BE8.jpg
         * metadata : {"x":"121.5339601560661","y":"31.278487081923988","relyid":"XJ1534926611097admin"}
         * bucketName : SJSB
         * chunkSize : 261120
         * size : 48346
         * md5 : f02f52fed3f9cb0605843265cc3a338a
         * uploadDate : 2018-08-22T16:29:11.439Z
         * contentType : image/jpeg
         */

        @SerializedName("fieldname")
        public String fieldname;
        @SerializedName("originalname")
        public String originalname;
        @SerializedName("encoding")
        public String encoding;
        @SerializedName("mimetype")
        public String mimetype;
        @SerializedName("id")
        public String id;
        @SerializedName("_id")
        public String _id;
        @SerializedName("filename")
        public String filename;
        @SerializedName("metadata")
        public MetadataBean metadata;
        @SerializedName("bucketName")
        public String bucketName;
        @SerializedName("chunkSize")
        public int chunkSize;
        @SerializedName("size")
        public int size;
        @SerializedName("md5")
        public String md5;
        @SerializedName("uploadDate")
        public String uploadDate;
        @SerializedName("contentType")
        public String contentType;

        public static class MetadataBean {
            /**
             * x : 121.5339601560661
             * y : 31.278487081923988
             * relyid : XJ1534926611097admin
             */

            @SerializedName("x")
            public String x;
            @SerializedName("y")
            public String y;
            @SerializedName("relyid")
            public String relyid;
        }
    }
}
