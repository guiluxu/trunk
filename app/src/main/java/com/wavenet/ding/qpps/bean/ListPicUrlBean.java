package com.wavenet.ding.qpps.bean;

import com.wavenet.ding.qpps.utils.AppConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ding on 2018/8/20.
 */

public class ListPicUrlBean {


    public List<AppBean> app;

    public static class AppBean {
        /**
         * _id : 5b7ad97074aa671eaca3a2c4
         * length : 40030
         * chunkSize : 261120
         * uploadDate : 2018-08-20T15:08:32.655Z
         * filename : 8167772b-41d7-4e64-af85-0e5b2d92ba48.jpg
         * md5 : eb0f1cc6f76c6970fb6d70d1ba08541f
         * contentType : image/jpeg
         * metadata : {"relyid":"1534748984580_YH_admin","yxfa":"1534748984833","x":"31.278396116271086","y":"121.53389756045999"}
         */

        public String _id;
        public int length;
        public int chunkSize;
        public String uploadDate;
        public String filename;
        public String md5;
        public String contentType;
        public MetadataBean metadata;

        public static class MetadataBean {
            /**
             * relyid : 1534748984580_YH_admin
             * yxfa : 1534748984833
             * x : 31.278396116271086
             * y : 121.53389756045999
             */

            public String relyid;
            public String yxfa;
            public String x;
            public String y;
            public String S_TYPE;
            public String S_DESC;
        }


        public static List<String> setFileUrls(List<ListPicUrlBean.AppBean> mPABeanL) {
             List<String> fileUrls = new ArrayList<>();
            for (int i = 0; i < mPABeanL.size(); i++) {
                if ("video/mp4".equals(mPABeanL.get(i).contentType)){

                    fileUrls.add(0, AppConfig.BeasUrl+"2083/file/download/SSYH?id=" + mPABeanL.get(i)._id+ "&" + "yxfa" + mPABeanL.get(i).metadata.yxfa+"视频");
                }else if ("audio/mpeg".equals(mPABeanL.get(i).contentType)){
                    String[] urlarry=mPABeanL.get(i).filename.split("@");
                    int index=0;
                    if (fileUrls.size()>0 && fileUrls.get(0).endsWith("视频")){
                        index=1;
                    }else {
                        index=0;
                    }
                    if (urlarry.length>1){
                        fileUrls.add(index,AppConfig.BeasUrl+"2083/file/download/SSYH?id=" + mPABeanL.get(i)._id+ "&" + "yxfa" + mPABeanL.get(i).metadata.yxfa+"@"+urlarry[1].replace(".mp4","")+"语音");
                    } }else {
                    fileUrls.add(AppConfig.BeasUrl+"2083/file/download/SSYH?id=" + mPABeanL.get(i)._id+ "&" + "yxfa" + mPABeanL.get(i).metadata.yxfa);
                }
            }
            return fileUrls;
        }
    }
}
