package com.wavenet.ding.qpps.bean;

import com.wavenet.ding.qpps.api.QPSWApplication;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoubeiwen on 2018/7/24.
 */

public class TaskBean {
    static TaskBean mTaskBean;
    public String clabig;
    public String clasmall;
    public String username;
    public String time;
    public String S_MANGE_ID;
    public String addr;
    public String beizhu;
    public double x = 0;
    public double y = 0;
    public List<String> fileUrls = new ArrayList<>();
    public TaskBean() {
    }

    public TaskBean(String S_MANGE_ID, String clabig, String clasmall, String username, String time, double x, double y) {
        this.S_MANGE_ID = S_MANGE_ID;
        this.clabig = clabig;
        this.clasmall = clasmall;
        this.username = username;
        this.time = time;
        this.x = x;
        this.y = y;
    }

    public static TaskBean Builder() {
        return new TaskBean();

    }

    public static List<TaskBean> mTaskBean(RelevantTaskBean mRelevantTaskBean) {
        int size = mRelevantTaskBean.value.size();
        List<TaskBean> beanList = new ArrayList<>();
        if (size > 0) {
            TaskBean tb;
            for (int i = 0; i < size; i++) {
                RelevantTaskBean.ValueBean rvb = mRelevantTaskBean.value.get(i);
                tb = new TaskBean(rvb.SMANGEID, QPSWApplication.getInstance().m.get(AppTool.getNullStr(rvb.SCATEGORY)), QPSWApplication.getInstance().m.get(AppTool.getNullStr(rvb.STYPE)), rvb.SINMAN, rvb.TINDATE, 31, 121);
//                tb=new TaskBean(rvb.SMANGEID,rvb.STYPE,rvb.STYPE,rvb.SINMAN,rvb.TINDATE,AppTool.strtodouble(rvb.NY), AppTool.strtodouble(rvb.NX));
                beanList.add(tb);
            }
        }
        return beanList;
    }

    public TaskBean setFileUrls(PhtotIdBean mPhtotIdBean) {
        fileUrls.clear();
        for (int i = 0; i < mPhtotIdBean.app.size(); i++) {
            if ("video/mp4".equals(mPhtotIdBean.app.get(i).contentType)){

                fileUrls.add(0, AppConfig.BeasUrl+"2083/file/download/SJSB?id=" + mPhtotIdBean.app.get(i)._id+"视频");
            }else if ("audio/mpeg".equals(mPhtotIdBean.app.get(i).contentType)){
                String[] urlarry=mPhtotIdBean.app.get(i).filename.split("@");
                int index=0;
                if (fileUrls.size()>0 && fileUrls.get(0).endsWith("视频")){
                    index=1;
                }else {
                    index=0;
                }
                if (urlarry.length>1){
                fileUrls.add(index,AppConfig.BeasUrl+"2083/file/download/SJSB?id=" + mPhtotIdBean.app.get(i)._id+"@"+urlarry[1].replace(".mp4","")+"语音");
                } }else {
                fileUrls.add(AppConfig.BeasUrl+"2083/file/download/SJSB?id=" + mPhtotIdBean.app.get(i)._id);
            }
        }
        return this;
    }

    public TaskBean setFileUrls2(PhtotIdBean mPhtotIdBean) {
        fileUrls.clear();
        for (int i = 0; i < mPhtotIdBean.app.size(); i++) {

            if ("video/mp4".equals(mPhtotIdBean.app.get(i).contentType)){
                fileUrls.add(0,AppConfig.BeasUrl+"2083/file/download/SJSB?id=" + mPhtotIdBean.app.get(i).id+"视频");
            }else if ("audio/mpeg".equals(mPhtotIdBean.app.get(i).contentType)){
                String[] urlarry=mPhtotIdBean.app.get(i).filename.split("@");
                int index=0;
                if (fileUrls.size()>0 && fileUrls.get(0).endsWith("视频")){
                    index=1;
                }else {
                    index=0;
                }
                if (urlarry.length>1){
                fileUrls.add(index,AppConfig.BeasUrl+"2083/file/download/SJSB?id=" + mPhtotIdBean.app.get(i).id+"@"+urlarry[1].replace(".mp4","")+"语音");}}else {
            fileUrls.add(AppConfig.BeasUrl+"2083/file/download/SJSB?id=" + mPhtotIdBean.app.get(i).id);
        }
        }
        return this;
    }

    public TaskBean setFileUrls1(PhtotIdBean mPhtotIdBean) {
        fileUrls.clear();
        for (int i = 0; i < mPhtotIdBean.app.size(); i++) {
            if ("video/mp4".equals(mPhtotIdBean.app.get(i).contentType)){
                fileUrls.add(0,AppConfig.BeasUrl+"2083/file/download/SJCZ?id=" + mPhtotIdBean.app.get(i)._id+"视频");
            }else if ("audio/mpeg".equals(mPhtotIdBean.app.get(i).contentType) ){
                String[] urlarry=mPhtotIdBean.app.get(i).filename.split("@");
                int index=0;
                if (fileUrls.size()>0 && fileUrls.get(0).endsWith("视频")){
                    index=1;
                }else {
                    index=0;
                }
                if (urlarry.length>1){
                    fileUrls.add(index,AppConfig.BeasUrl+"2083/file/download/SJCZ?id=" + mPhtotIdBean.app.get(i)._id+"@"+urlarry[1].replace(".mp4","")+"语音");}}else {
                    fileUrls.add(AppConfig.BeasUrl+"2083/file/download/SJCZ?id=" + mPhtotIdBean.app.get(i)._id);
                }
        }

        return this;
    }

}
