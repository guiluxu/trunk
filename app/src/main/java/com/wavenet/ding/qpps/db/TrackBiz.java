package com.wavenet.ding.qpps.db;

import android.content.Context;

import com.wavenet.ding.qpps.db.bean.TaskEvenBean;
import com.wavenet.ding.qpps.db.bean.TaskInfoBean;
import com.wavenet.ding.qpps.db.bean.TrackBean;

import java.util.List;

/**
 * 轨迹信息
 * Created by Admin on 2016/6/6.
 */
public class TrackBiz {
    TrackDB mTrackDB;
    public TrackBiz(Context context) {
        this.mTrackDB = new TrackDB(context);
    }

    /**
     * 添加巡检任务记录
     */
    public void inserttaskinfo(TaskInfoBean bean) {
        mTrackDB.inserttaskinfo(bean);
    }
    /**
     *添加巡检任务事件
     */
    public void inserttaskeven(TaskEvenBean bean) {
        mTrackDB.inserttaskeven(bean);
    }
      /**
     *添加巡检记录任务轨迹
     */

    public void inserttasktrack(TrackBean bean) {
        mTrackDB.inserttasktrack(bean);}
    /**
     * 根据s_recode_id更新任务记录
     */
    public int updateTaskInfo_recodeid(String s_recode_id, double mileage,  String t_end){
        return mTrackDB.updateTaskInfo_recodeid(s_recode_id,mileage,t_end);
    }
    /**
     * 根据s_recode_id获取巡检记录任务轨迹信息
     */
    public List<TrackBean> findTaskTrack(String s_recode_id){
        return mTrackDB.findTaskTrack(s_recode_id);
    }
 /**
     * 根据s_recode_id删除对应的巡检任务记录
     */
    public  int deleTaskInfo_recodeid(String s_recode_id){
        return mTrackDB.deleTaskInfo_recodeid(s_recode_id);
    }/**
     * 根据当天时间删除当天之前的巡检轨迹
     */
    public  void deleTaskTrack_datecur(int datecur){
         mTrackDB.deleTaskTrack_datecur(datecur);
    }
    /**
     * 删除所有巡检记录任务轨迹
     */
    public  void deleTaskTrack(){
        mTrackDB.deleTaskTrack();
    }
}
