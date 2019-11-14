package com.wavenet.ding.qpps.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.bean.ClasBean;
import com.wavenet.ding.qpps.bean.JCbzAclcBean;
import com.wavenet.ding.qpps.bean.JCswBean;
import com.wavenet.ding.qpps.bean.Legendbean;
import com.wavenet.ding.qpps.bean.RequestDataBean;
import com.wavenet.ding.qpps.interf.InterfListen;
import com.wavenet.ding.qpps.view.viewutils.scrollLayout.ScrollLayoutView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AppUtils {
    public static String getPopleFilteXJ(Context mContext) {
        String filterStr = "";
        String townid = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNID);
        String UserId = SPUtil.getInstance(mContext).getStringValue(SPUtil.USERNO);
        String rolename = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_ROLE);
        String companyname = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_COMPANY);
        LogUtils.d("qingpu", "rolename = " + rolename);
        if (!AppTool.isNull(rolename)) {
            if (rolename.equals("超级管理员") || rolename.equals("区排水所管理员") || rolename.equals("区排水所调度员") || rolename.equals("区管理员")) {
                filterStr = "Role=巡查人员";
            } else {
                if (rolename.equals("养护单位管理员")) {
                    if (!AppTool.isNull(companyname)) {
                        filterStr = "Role=巡查人员" + "&Company=" + companyname;
                    } else {
                        filterStr = "Role=巡查人员";
                    }
                } else if (rolename.equals("街镇管理员") || rolename.equals("街镇调度员")) {
                    if (!AppTool.isNull(townid)) {
                        filterStr = "Role=巡查人员" + "&Town=" + townid;
                    } else {
                        filterStr = "Role=巡查人员";
                    }
                } else {

                }
            }
        } else {

        }
        return filterStr;
    }

    public static String getPopleFilteYH(Context mContext) {
        String filterStr = "";
        String UserId = SPUtil.getInstance(mContext).getStringValue(SPUtil.USERNO);
        String rolename = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_ROLE);
        String townid = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNID);
        String companyname = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_COMPANY);
        if (!AppTool.isNull(rolename)) {
            if (rolename.equals("超级管理员") || rolename.equals("区排水所管理员") || rolename.equals("区排水所调度员") || rolename.equals("区管理员")) {
                filterStr = "Role=养护人员";
            } else {
                if (rolename.equals("养护单位管理员")) {
                    if (!AppTool.isNull(companyname)) {
                        filterStr = "Role=养护人员" + "&Company=" + companyname;
                    } else {
                        filterStr = "Role=养护人员";
                    }
                } else if (rolename.equals("街镇管理员") || rolename.equals("街镇调度员")) {
                    if (!AppTool.isNull(townid)) {
                        filterStr = "Role=巡查人员" + "&Town=" + townid;
                    } else {
                        filterStr = "Role=巡查人员";
                    }
                } else {

                }
            }
        } else {

        }
        /*if (!AppTool.isNull(companyname)){
            if (!AppTool.isNull(townid)){
                filterStr="S_MAN_NAME2=养护人员&S_TOWNID="+townid+"&Company="+companyname;
            }else {
                filterStr="S_MAN_NAME2=养护人员&Company="+companyname;
            }
        }else {
            if (!AppTool.isNull(townid)){
                filterStr="S_MAN_NAME2=养护人员&S_TOWNID="+townid;
            }else {
                filterStr="S_MAN_NAME2=养护人员";
            }
        }*/
        return filterStr;
    }

    public static String getParamPeople(Context mContext, String type) {
        String filterStr = "";
        String rolename = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_ROLE);
        String townid = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNID);
        String companyname = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_COMPANY);
        switch (rolename) {
            case "":
                break;
            case "超级管理员":
            case "区排水所管理员":
            case "区排水所调度员":
            case "区管理员":
                filterStr = "Role=" + type;
                break;
            case "养护单位管理员":
                filterStr = "Role=" + type + "&Company=" + companyname;
                break;
            case "街镇管理员":
            case "街镇调度员":
                filterStr = "Role=" + type + "&Town=" + townid;
                break;
        }
        return filterStr;
    }

    public static String getH5F1(Context mContext) {
        String filterStr = "";
        String townid = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNID);
        String rolename = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_ROLE);
        String Company = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_COMPANY);
        String UserId = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_PERSONID);
        String TownText = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNNAME);
        String Des = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_DES);
       filterStr=AppConfig.AdminTJFrame+"?TownCode="+townid+"&RoleName="+rolename+"&Company="+Company+"&UserId="+UserId+"&TownText="+TownText+"&Des="+Des;
//        filterStr = "http://222.66.154.70:2088/QP_H5/module/home/index.html#/SY?TownCode=" + townid + "&RoleName=" + rolename + "&Company=" + Company + "&UserId=" + UserId + "&TownText=" + TownText + "&Des=" + Des;
        return filterStr;
    }

    public static String getH5F2(Context mContext) {
        String townid = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNID);
        String userid = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_PERSONID);
        String rolename = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_ROLE);
        String TownText = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNNAME);
        String Des = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_DES);
        String Company = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_COMPANY);
        StringBuilder sb = new StringBuilder("?");
//        if (!AppTool.isNull(townid)){
        sb.append("&TownCode=" + townid);
//        }
//        if (!AppTool.isNull(rolename)){
        sb.append("&RoleName=" + rolename);
//        }
//        if (!AppTool.isNull(userid)){
        sb.append("&UserId=" + userid);
//        }
//        if (!AppTool.isNull(userid)){
        sb.append("&TownText=" + TownText);
//        }
//        if (!AppTool.isNull(userid)){
        sb.append("&Des=" + Des);
//        }
//        if (!AppTool.isNull(userid)){
        sb.append("&Company=" + Company);
//        }
        String filterStr = AppConfig.AdminXCFrame;
        String sbstr = sb.toString();
//        String filterStrurl=filterStr+sbstr;
        String filterStrurl = "";
        if (sbstr.contains("?&")) {
            sbstr = sbstr.replace("?&", "?");
            filterStrurl = filterStr + sbstr;
        } else {
            filterStrurl = filterStr;
        }
//       /
// ?TownCode=W1007400009&RoleName=街镇调度员&UserId=19010712462832596923dca4e0718
        return filterStrurl;
    }

    public static String getH5F3(Context mContext) {
        String townid = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNID);
        String userid = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_PERSONID);
        String rolename = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_ROLE);
        String TownText = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNNAME);
        String Des = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_DES);
        String Company = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_COMPANY);
        StringBuilder sb = new StringBuilder("?");
//        if (!AppTool.isNull(townid)){
        sb.append("&TownCode=" + townid);
//        }
//        if (!AppTool.isNull(rolename)){
        sb.append("&RoleName=" + rolename);
//        }
//        if (!AppTool.isNull(userid)){
        sb.append("&UserId=" + userid);
//        }
//        if (!AppTool.isNull(userid)){
        sb.append("&TownText=" + TownText);
//        }
//        if (!AppTool.isNull(userid)){
        sb.append("&Des=" + Des);
//        }
//        if (!AppTool.isNull(userid)){
        sb.append("&Company=" + Company);
//        }
        String filterStr = AppConfig.AdminYHFrame;
        String sbstr = sb.toString();
//        String filterStrurl=filterStr+sbstr;
        String filterStrurl = "";
        if (sbstr.contains("?&")) {
            sbstr = sbstr.replace("?&", "?");
            filterStrurl = filterStr + sbstr;
        } else {
            filterStrurl = filterStr;
        }
//       /
// ?TownCode=W1007400009&RoleName=街镇调度员&UserId=19010712462832596923dca4e0718
        return filterStrurl;
    }

    public static ArrayList<ClasBean.ValueBean> getClaphotolist() {


        ArrayList<ClasBean.ValueBean> vl = new ArrayList<ClasBean.ValueBean>();
        vl.add(new ClasBean.ValueBean("检查井"));
        vl.add(new ClasBean.ValueBean("雨水口"));
        vl.add(new ClasBean.ValueBean("雨水管"));
        vl.add(new ClasBean.ValueBean("污水管"));
        vl.add(new ClasBean.ValueBean("接户管、连管"));
        vl.add(new ClasBean.ValueBean("排放口"));
        vl.add(new ClasBean.ValueBean("潮闸门"));
        vl.add(new ClasBean.ValueBean("管道检测"));
        return vl;
    }

    public static boolean isNullCashlog(Context context) {

        List<String> s = AppTool.getFilesAllName(AppTool.getCrashLogFolder(context, ""));
        if (s == null || s.size() == 0)
            return true;
        return false;
    }

    public static List<Double> bubbleSort(List<Double> a) {
        double temp;
        int size = a.size();
        for (int i = 1; i < size; i++) {
            for (int j = 0; j < size - i; j++) {
                if (a.get(j) < a.get(j + 1)) {
//                    temp = a[j];
//                    a[j]=a[j+1];
//                    a[j+1]=temp;
                    temp = a.get(j);
                    a.set(j, a.get(j + 1));
                    a.set(j + 1, temp);

                }
            }
        }
        return a;
    }

    public static ScrollLayoutView setScrollLayoutView(Context mContext, View mainView, InterfListen.C mC) {

        // 详情
        ScrollLayoutView mScrollLayoutView = mainView.findViewById(R.id.scroll_down_layoutm);

        /**设置 setting*/
        mScrollLayoutView.setMinOffset(AppTool.dip2px(mContext, 0));
//        mScrollLayoutView.setMinOffset((int) (AppTool.getScreenHeight(getActivity()) ));
        mScrollLayoutView.setMaxOffset((int) (AppTool.getScreenHeight((Activity) mContext) * 0.5));
        mScrollLayoutView.setExitOffset(0);
        mScrollLayoutView.setIsSupportExit(true);
        mScrollLayoutView.setAllowHorizontalScroll(true);
        mScrollLayoutView.setToExit();//第一次显示
//        mScrollLayoutView.setToOpen();//第二次显示
//        mScrollLayoutView.setToClosed();//第三次显示
        mScrollLayoutView.getBackground().setAlpha(0);
        if (mC!=null){
            mScrollLayoutView.setOnScrollChangedListener(new AppUtils.A(mScrollLayoutView, mC));
        }
        return mScrollLayoutView;

    }

    public static class A implements ScrollLayoutView.OnScrollChangedListener {

        ScrollLayoutView mScrollLayoutView;
        InterfListen.C mC;

        public A(ScrollLayoutView mScrollLayoutView, InterfListen.C mC) {
            this.mScrollLayoutView = mScrollLayoutView;
            this.mC = mC;
        }

        @Override
        public void onScrollProgressChanged(float currentProgress) {
            if (currentProgress >= 0) {
                float precent = 255 * currentProgress;
                if (precent > 255) {
                    precent = 255;
                } else if (precent < 0) {
                    precent = 0;
                }
                mScrollLayoutView.getBackground().setAlpha(255 - (int) precent);
            }
//            if (text_foot.getVisibility() == View.VISIBLE)
//                text_foot.setVisibility(View.GONE);
//            if (ll_foot.getVisibility() == View.VISIBLE)
//                ll_foot.setVisibility(View.GONE);
        }

        @Override
        public void onScrollFinished(ScrollLayoutView.Status currentStatus) {
            if (mC == null) {
                return;
            }
            if (currentStatus.equals(ScrollLayoutView.Status.OPENED)) {
                mC.cA(InterfListen.onScrollFinished, currentStatus, false);
//                mControllerDV.isHide(false);
            } else {
//                mControllerDV.isHide(true);
                mC.cA(InterfListen.onScrollFinished, currentStatus, true);
            }
        }

        @Override
        public void onChildScroll(int top) {

        }
    }

    public static List<String> getListSize(RequestDataBean b) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < b.Data.size(); i++) {
            list.add(b.Data.get(i).JckName);
        }
        return list;
    }

    //动态添加视图
    public static void addview(Context mContext, RadioGroup radiogroup, List<String> blist) {

        int index = 0;
        for (String ss : blist) {

            RadioButton button = new RadioButton(mContext);
            setRaidBtnAttribute(mContext, button, ss, index);

            radiogroup.addView(button);

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button
                    .getLayoutParams();
            layoutParams.setMargins(0, 0, AppTool.dip2px(mContext, 10), 0);//4个参数按顺序分别是左上右下
            button.setLayoutParams(layoutParams);
            index++;
        }


    }

    private static void setRaidBtnAttribute(Context mContext, final RadioButton codeBtn, String btnContent, int id) {
        if (null == codeBtn) {
            return;
        }
        codeBtn.setBackground(null);
        codeBtn.setTextColor(mContext.getResources().getColorStateList(R.color.btn_login_bg));
        codeBtn.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
//        codeBtn.setButtonDrawable(getResources().getDrawable(R.drawable.selectot_gbbg_jcclc));
        // 使用代码设置drawableTop
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.selectot_gbbg_jcclc);
// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        codeBtn.setCompoundDrawables(null, null, null, drawable);
        codeBtn.setId(id);
        codeBtn.setText(btnContent);
        //codeBtn.setPadding(2, 0, 2, 0);
        codeBtn.setTag(id);
        codeBtn.setGravity(Gravity.CENTER);
        codeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, codeBtn.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        //DensityUtilHelps.Dp2Px(this,40)
        LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, AppTool.dip2px(mContext, 40), 1.0f);

        codeBtn.setLayoutParams(rlp);
        if (id==0){
            codeBtn.setChecked(true);
        }
    }

    /**
     * 保留小数，进行四舍五入（该方法经测试 较为精准）
     *
     * @param d
     * @return
     */
    public static Double saveOneBit(String format, Double d) {
//        String str = String.format("%.1f",d);
        String str = String.format(format, d);
        double c = Double.parseDouble(str);
        return c;
    }

    public static String getJChisData_one(JCswBean jchis) {
        Map<String, Object> map = new HashMap<>();
        List<Object> serieslist = new ArrayList<>();
        int size = jchis.Data.Data.size();
        double ymax = 0.0;
        double ymin = 0.0;
        boolean isInitY=false;
        for (int i = 0; i < size; i++) {
            List<Object> templist = new ArrayList<>();
            Map<String, Object> maptemp = new HashMap<>();
            String name = jchis.Data.Data.get(i).name;
            double value = jchis.Data.Data.get(i).value;
            templist.add(name);
            templist.add(value);

//            LogUtils.e("seriesdatatemplist",new Gson().toJson(templist));
            maptemp.put("name", name);
            maptemp.put("value", templist);
//            LogUtils.e("seriesdatamaptemp",new Gson().toJson(maptemp));
            serieslist.add(maptemp);
//            LogUtils.e("seriesdataserieslist",new Gson().toJson(serieslist));
            if (!isInitY){
                isInitY=true;
                ymax = value ;
                ymin = value ;
            }
            if (ymax < value ) {
                ymax = value ;
            }

            if (ymin > value ) {
                ymin = value ;
            }

        }
        ymax=Math.max(ymax, Math.max(jchis.Data.Chaoyji, jchis.Data.Yuj));
        ymin=Math.min(ymin, Math.min(jchis.Data.Chaoyji, jchis.Data.Yuj));
        String startime = jchis.Data.Data.get(0).name.substring(0, 10);
//        String endtime=DateUtil.getSpecifiedDayAfter(startime, AppTool.FORMAT_YMD);
        map.put("xmin", startime + " 00:00:00");
        map.put("xmax", startime + " 24:00:00");
        map.put("seriesdata", serieslist);
        map.put("seriesname", " 数值");
        map.put("Yuj", jchis.Data.Yuj);
        map.put("Chaoyji", jchis.Data.Chaoyji);
        map.put("ymax", ymax);
        map.put("ymin", ymin);
        map.put("minInterval",0);
        LogUtils.e("seriesdata", new Gson().toJson(map));
        return new Gson().toJson(map);
    }public static Map<String ,Object> getJChisData_one(List<JCbzAclcBean.JCDBean> jcdBean,double NWQGLLV,double NWQGULV) {
        Map<String, Object> map = new HashMap<>();
        List<Object> serieslist = new ArrayList<>();
        int size = jcdBean.size();
        double ymax = 0.0;
        double ymin = 0.0;
        boolean isInitY=false;
        for (int i = 0; i < size; i++) {
            List<Object> templist = new ArrayList<>();
            Map<String, Object> maptemp = new HashMap<>();
            String name = jcdBean.get(i).name;
            double value = jcdBean.get(i).value;
            templist.add(name);
            templist.add(value);

//            LogUtils.e("seriesdatatemplist",new Gson().toJson(templist));
            maptemp.put("name", name);
            maptemp.put("value", templist);
//            LogUtils.e("seriesdatamaptemp",new Gson().toJson(maptemp));
            serieslist.add(maptemp);
//            LogUtils.e("seriesdataserieslist",new Gson().toJson(serieslist));
            if (!isInitY){
                isInitY=true;
                ymax = value ;
                ymin = value ;
            }
            if (ymax < value ) {
                ymax = value ;
            }

            if (ymin > value ) {
                ymin = value ;
            }

        }

        ymax=Math.max(ymax, Math.max(NWQGLLV, NWQGULV));
        ymin=Math.min(ymin, Math.min(NWQGLLV, NWQGULV));
        String startime = jcdBean.get(0).name.substring(0, 10);
//        String endtime=DateUtil.getSpecifiedDayAfter(startime, AppTool.FORMAT_YMD);
        map.put("xmin", startime + " 00:00:00");
        map.put("xmax", startime + " 24:00:00");
        map.put("seriesdata", serieslist);
        map.put("seriesname", " 数值");
        map.put("Yuj", NWQGLLV);
        map.put("Chaoyji", NWQGULV);
        map.put("ymax", ymax);
        map.put("ymin", ymin);
        LogUtils.e("seriesdata", new Gson().toJson(map));
        return map;
    }

    public static Map<String ,Object>  getJChisData_clc(List<JCbzAclcBean.JCDBean> jcdBean, String[] JckIDarry,String[] Jcknamearry,double NWQGLLV,double NWQGULV ,boolean isHavejjx) {

        Map<Integer, List<JCbzAclcBean.JCDBean>> map = new HashMap<>();
        String startime = jcdBean.get(0).name.substring(0, 10);
//        String endtime=DateUtil.getSpecifiedDayAfter(startime, AppTool.FORMAT_YMD);
        for (int i = 0; i < JckIDarry.length; i++) {
            List<JCbzAclcBean.JCDBean> v = new ArrayList<>();
            map.put(i, v);
        }
        for (int i = 0; i < jcdBean.size(); i++) {
            for (int j = 0; j < JckIDarry.length; j++) {
                if (!AppTool.isNull(JckIDarry[j]) && JckIDarry[j].equals(jcdBean.get(i).JckID)) {
                    JCbzAclcBean.JCDBean b= jcdBean.get(i) ;
                    map.get(j).add(b);
                }
            }
        }
return getMapEchart(map,startime,Jcknamearry, NWQGLLV, NWQGULV , isHavejjx);
    }
    public static Map<String ,Object>  getJChisData_bzkg(List<JCbzAclcBean.JCDBean> jcdBean, String[] JckIDarry,String[] Jcknamearry,double NWQGLLV,double NWQGULV ,boolean isHavejjx) {
        Map<String, Object> m = new HashMap<>();
        Map<Integer, List<JCbzAclcBean.JCDBean>> map = new HashMap<>();
        String startime = jcdBean.get(0).name.substring(0, 10);
//        String endtime=DateUtil.getSpecifiedDayAfter(startime, AppTool.FORMAT_YMD);
        for (int i = 0; i < JckIDarry.length; i++) {
            List<JCbzAclcBean.JCDBean> v = new ArrayList<>();
            map.put(i, v);
        }
        for (int i = 0; i < jcdBean.size(); i++) {
            for (int j = 0; j < JckIDarry.length; j++) {
                char c=jcdBean.get(i).values.charAt(j);
                String s=String.valueOf(c);
                double v=Double.parseDouble(s);
                JCbzAclcBean.JCDBean b= new JCbzAclcBean.JCDBean(jcdBean.get(i).name,v) ;
                    map.get(j).add( b);
            }
        }
        return getMapEchart(map,startime,Jcknamearry, NWQGLLV, NWQGULV , isHavejjx);
    }
    public static Map<String ,Object>  getMapEchart( Map<Integer, List<JCbzAclcBean.JCDBean>> map,String startime,String[] Jcknamearry,double NWQGLLV,double NWQGULV ,boolean isHavejjx){
        Map<String, Object> m = new HashMap<>();
        //value
        List<Object> serieslist = new ArrayList<>();
        Iterator iter = map.entrySet().iterator();
        double ymax=0.0;
        double ymin=0.0;
        boolean isInitY=false;
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Integer key = (Integer) entry.getKey();
            List<JCbzAclcBean.JCDBean> v = (List<JCbzAclcBean.JCDBean>) entry.getValue();
            List<Object> seriestemp = new ArrayList<>();
            for (int i = 0; i < v.size(); i++) {
                List<Object> templist = new ArrayList<>();
                Map<String, Object> maptemp = new HashMap<>();
                String name = v.get(i).name;
                double value = v.get(i).value;
                if (!isInitY){
                    isInitY=true;
                    ymax = value ;
                    ymin = value ;
                }
                if (ymax<value){
                    ymax=value;
                }

                if (ymin>value){
                    ymin=value;
                }
                templist.add(name);
                templist.add(value);
                maptemp.put("name", name);
                maptemp.put("value", templist);
                seriestemp.add(maptemp);

            }
            serieslist.add(key,seriestemp);
        }
        if (isHavejjx){
            m.put("Yuj", NWQGLLV);
            m.put("Chaoyji", NWQGULV);
            ymax=Math.max(ymax, Math.max(NWQGLLV, NWQGULV));
            ymin=Math.min(ymin, Math.min(NWQGLLV, NWQGULV));
        }
        m.put("xmin", startime + " 00:00:00");
        m.put("xmax", startime + " 24:00:00");
        m.put("seriesdata", serieslist);
        m.put("seriesname", Jcknamearry);
        m.put("ymax", ymax);
        m.put("ymin", ymin);
        LogUtils.e("seriesdata111", new Gson().toJson(m));
        return  m;
    }
    public  static void addViewbjzt(Context mContext,LinearLayout v,String bjzt){
        if (!"-".equals(bjzt)){
            for (int i = 0; i <bjzt.length() ; i++) {
                ImageView r=new ImageView(mContext);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0,0,10,0);
                lp.gravity=Gravity.CENTER_VERTICAL;
                r.setLayoutParams(lp);  //设置图片宽高
                r.setMinimumWidth(25);
                r.setMinimumHeight(25);
                String s = String.valueOf(bjzt.charAt(i)); //效率最高的方法
                if ("0".equals(s)){
                    r.setImageResource(R.drawable.or_iv_red); //图片资源
                }else    if ("1".equals(s)){
                    r.setImageResource(R.drawable.or_iv_green); //图片资源
                }
                v.addView(r);
            }
        }
    }
    public  static ArrayList<Legendbean>  getTcData(){
        ArrayList<Legendbean> mListBeen=new ArrayList<>();
        mListBeen.add(new Legendbean(true,"在线监测" ,AppConfig.JCswsname,R.mipmap.ico_sheshi__shuiwjc,0, AppConfig.JCsw,AppConfig.IndexJCsw,"在线监测",new String[]{AppConfig.JCsws},new int[]{AppConfig.IndexJCsw},1,AppConfig.jCurlsw,4,AppConfig.jCurlswhis));
        mListBeen.add(new Legendbean(false,"在线监测" ,AppConfig.JCclcsname,R.mipmap.ico_sheshi__wusclc,0,AppConfig.JCclc,AppConfig.IndexJCclc,"在线监测",new String[]{AppConfig.JCclcs},new int[]{AppConfig.IndexJCclc},2,AppConfig.jCurlclc,5,AppConfig.jCurlclchis));
        mListBeen.add(new Legendbean(false,"在线监测" ,AppConfig.JCbzsname,R.mipmap.ico_sheshi__wusbz,1,AppConfig.JCbz,AppConfig.IndexJCbz,"在线监测",new String[]{AppConfig.JCbzs},new int[]{AppConfig.IndexJCbz,1},3,AppConfig.jCurlbz,6,AppConfig.jCurlbzhis));
        return mListBeen;
    }
}
