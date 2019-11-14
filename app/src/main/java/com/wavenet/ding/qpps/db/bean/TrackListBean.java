package com.wavenet.ding.qpps.db.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zoubeiwen on 18/1/5.
 */

public class TrackListBean implements Serializable {

    /**
     * yw_track_head_list : [{"track_head_id":"5ae95d90-a918-49a2-b763-736cc2461fb2","person_name":"wavenet","track_name":"gddx","track_begin_time":"2018-01-04 17:46:58","track_end_time":"2018-01-04 17:47:15","status":1,"kilometre":0,"calorie":0,"steps":0}]
     * totalCount : 1
     */

    @SerializedName("totalCount")
    public int totalCount;
    @SerializedName("yw_track_head_list")
    public List<YwTrackHeadListBean> ywTrackHeadList;

    public static class YwTrackHeadListBean implements Serializable {
        /**
         * track_head_id : 5ae95d90-a918-49a2-b763-736cc2461fb2
         * person_name : wavenet
         * track_name : gddx
         * track_begin_time : 2018-01-04 17:46:58
         * track_end_time : 2018-01-04 17:47:15
         * status : 1
         * kilometre : 0
         * calorie : 0
         * steps : 0
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
        public String kilometre;
        @SerializedName("calorie")
        public int calorie;
        @SerializedName("steps")
        public int steps;
    }
}
