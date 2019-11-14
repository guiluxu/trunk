package com.wavenet.ding.qpps.db.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/1/18.
 */

public class TrackInfoBean {
    /**
     * RealTimeWaterListGetResponse : {"totalCount":9,"yw_track_head":{"track_head_id":"a145277d-c5d8-4473-904e-819247f82fa0","person_name":"82138502","track_name":"沪宁铁路真华路车行地道","track_begin_time":"2017-11-29 10:57:31","track_end_time":"网波股份","status":1,"kilometre":0,"calorie":0,"steps":0,"yw_track_line_list":[{"track_head_id":"2017","track_time":"网波股份","bd_X":0,"bd_Y":0},{"track_head_id":"2017","track_time":"网波股份","bd_X":0,"bd_Y":0}]}}
     */

    @SerializedName("RealTimeWaterListGetResponse")
    public RealTimeWaterListGetResponseBean RealTimeWaterListGetResponse;

    public static class RealTimeWaterListGetResponseBean {
        /**
         * totalCount : 9
         * yw_track_head : {"track_head_id":"a145277d-c5d8-4473-904e-819247f82fa0","person_name":"82138502","track_name":"沪宁铁路真华路车行地道","track_begin_time":"2017-11-29 10:57:31","track_end_time":"网波股份","status":1,"kilometre":0,"calorie":0,"steps":0,"yw_track_line_list":[{"track_head_id":"2017","track_time":"网波股份","bd_X":0,"bd_Y":0},{"track_head_id":"2017","track_time":"网波股份","bd_X":0,"bd_Y":0}]}
         */

        @SerializedName("totalCount")
        public int totalCount;
        @SerializedName("yw_track_head")
        public YwTrackHeadBean ywTrackHead;

        public static class YwTrackHeadBean {
            /**
             * track_head_id : a145277d-c5d8-4473-904e-819247f82fa0
             * person_name : 82138502
             * track_name : 沪宁铁路真华路车行地道
             * track_begin_time : 2017-11-29 10:57:31
             * track_end_time : 网波股份
             * status : 1
             * kilometre : 0
             * calorie : 0
             * steps : 0
             * yw_track_line_list : [{"track_head_id":"2017","track_time":"网波股份","bd_X":0,"bd_Y":0},{"track_head_id":"2017","track_time":"网波股份","bd_X":0,"bd_Y":0}]
             */

            @SerializedName("track_head_id")
            public String trackHeadId;
            @SerializedName("person_name")
            public String personName;
            @SerializedName("track_name")
            public String trackName;
            @SerializedName("track_begin_time")
            public String trackBeginTime;
            @SerializedName("track_end_time")
            public String trackEndTime;
            @SerializedName("status")
            public int status;
            @SerializedName("kilometre")
            public int kilometre;
            @SerializedName("calorie")
            public int calorie;
            @SerializedName("steps")
            public int steps;
            @SerializedName("yw_track_line_list")
            public List<YwTrackLineListBean> ywTrackLineList;

            public static class YwTrackLineListBean {
                /**
                 * track_head_id : 2017
                 * track_time : 网波股份
                 * bd_X : 0
                 * bd_Y : 0
                 * s_no: 14,
                 * s_name: 龙吴路下立交,
                 * x: 121.44168311,
                 * y: 31.16596035,
                 */

                @SerializedName("track_head_id")
                public String trackHeadId;
                @SerializedName("track_time")
                public String trackTime;
                @SerializedName("bd_X")
                public double bdX;
                @SerializedName("bd_Y")
                public double bdY;
                @SerializedName("yw_wbid")
                public String ywWbid;
                @SerializedName("yw_wbtype")
                public String ywWbtype;
                @SerializedName("s_no")
                public String sNo;
                @SerializedName("s_name")
                public String sName;
                @SerializedName("x")
                public double X;
                @SerializedName("y")
                public double Y;
            }
        }
    }
}
