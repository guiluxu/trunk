package com.wavenet.ding.qpps.bean;

import com.wavenet.ding.qpps.utils.AppTool;

import java.text.DecimalFormat;

/**
 * Created by zoubeiwen on 2019/3/26.
 * 巡检当中程序崩溃巡检数据信息缓存记录
 */

public class BreakOffBean {
    public static boolean isStartde=false;//用来判断手机后台进入前台是时间的其中一个处理判断
    public static boolean isContinue=false;//false表示这次巡检不是从上次巡检中崩溃结束后继续，
    static BreakOffBean b;
    public static BreakOffBean getInitSingle(){
        if (b==null){
            b=new BreakOffBean();
        }
        return b;
    }
    public void init(){
        b=null;
    }
public void setBean(BreakOffBean b){
    getInitSingle();
    if (b==null)
        return;
    this.S_RECODE_ID=b.S_RECODE_ID;
    this.S_MANGE_ID=b.S_MANGE_ID;
    this.STASKID=b.STASKID;
    this.STASKIDcurrent=b.STASKIDcurrent;
    this.STASKIDTem=b.STASKIDTem;
    this.STASKIDTem=b.STASKIDTem;
    this.Task=b.Task;
    this.time=b.time;
    this.distance=b.distance;
    this.mtvBean=b.mtvBean;
}
    public  String S_RECODE_ID = "";//记录编号
    public  String S_MANGE_ID = "";//事件编号
    public  String STASKID = "";//派单任务id
    public  String STASKIDcurrent = "";//当前派单任务id
    public  String STASKIDTem = "";//临时派单任务id
    public  TasklistBean.ValueBean mtvBean;//派单任务bean
    public  int Task = 0;//0 日常，1派单
    public int time;//巡检时间
    public int timeend;//巡检最新时间
    public double distance;//巡检距离
    public  String getTaskType(){
     return Task==0?"日常巡检":"派单巡检";
    }  public  String getTaskTime(){
     return AppTool.StringFormTime(time);
    }

    public String getTaskDis() {
        DecimalFormat  fnum = new DecimalFormat("##0.00");
        return fnum.format(distance) + " Km";
    }
public String  toString(){
    return "S_RECODE_ID="+S_RECODE_ID+";"+"S_MANGE_ID="+S_MANGE_ID+";"+"STASKID="+STASKID+";"+"S_RECODE_ID="+S_RECODE_ID+";"+"STASKIDcurrent="+STASKIDcurrent+";"+"STASKIDTem="+STASKIDTem+";"+"Task="+Task+";"+"time="+time+";"+"timeend="+timeend+";"+"distance="+distance;
}
}
