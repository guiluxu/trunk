package com.wavenet.ding.qpps.db.bean;


import com.wavenet.ding.qpps.bean.XYbean;
import com.wavenet.ding.qpps.utils.AppTool;

/**
 * Created by Administrator on 2017/1/19.
 */

public class TrackBean {

//    "create table if not exists tasktrack(rid integer primary key autoincrement,s_id varchar(36),s_man_id varchar(32),t_upload varchar(32),n_latx double,n_lony double,n_speed double,s_task_type varchar(32),s_recode_id varchar(32),mileage double,interger datecur)";

        public TrackBean( ) {
        }
        public TrackBean(double n_latx, double n_lony) {
            this.n_latx=n_latx;
            this.n_lony=n_lony;
        }

        public String s_id;
        public String s_man_id;
        public String t_upload;
        public double n_latx;
        public double n_lony;
        public double n_speed;
        public String s_task_type;
        public String s_recode_id;
        public double mileage;
        public int datecur;
        public  TrackBean (XYbean xyb){
              this.s_id=AppTool.getNullStr(xyb.getData().getsId())  ;
              this.s_man_id= AppTool.getNullStr( xyb.getData().getsManId());
              this.t_upload= AppTool.getNullStr( xyb.getData().gettUpload());
              this.n_latx=  xyb.getData().getnLastX();//纬度
              this.n_lony=  xyb.getData().getnLastY();//经度
              this.n_speed=  xyb.getData().getnSpeed();
              this.s_task_type= AppTool.getNullStr( xyb.getData().getsTaskType());
              this.s_recode_id=  AppTool.getNullStr(xyb.getData().getsRecordId());
              this.mileage=  xyb.getData().getnMileage();
              this.datecur= Integer.parseInt(AppTool.getCurrentDate().replace("-",""));
        }
}
