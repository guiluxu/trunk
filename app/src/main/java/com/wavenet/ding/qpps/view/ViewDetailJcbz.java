package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dereck.library.utils.ToastUtils;
import com.google.gson.Gson;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.bean.JCbzAclcBean;
import com.wavenet.ding.qpps.bean.RequestDataBean;
import com.wavenet.ding.qpps.interf.InterfListen;
import com.wavenet.ding.qpps.utils.AppUtils;
import com.wavenet.ding.qpps.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;


public class ViewDetailJcbz extends LinearLayout implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    Context mContext;
    TextView mTvaddrtitle, mTv1, mTv2, mTv3, mTv4, mTv5, mTv6, mTv7, tv_hide;
    LinearLayout mLlfeatures;
    RadioGroup mRgmaplx;
    ViewChartCurve chartCurve;
    Map<String, Double> yjlmap = new HashMap<>();
    Map<String, Double> yjumap = new HashMap<>();
    String[] JckIDarry;//开关
    String[] Jcknamearry;//开关

    public ViewDetailJcbz(Context context) {
        super(context);
        initView(context);
    }

    public ViewDetailJcbz(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ViewDetailJcbz(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        setOnClickListener(this);
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(mContext).inflate(R.layout.view_jcdetailsbz, this);
        if (!isInEditMode()) {
            mTvaddrtitle = (TextView) findViewById(R.id.tv_addrtitle);
            mTv1 = (TextView) findViewById(R.id.tv1);
            mTv2 = (TextView) findViewById(R.id.tv2);
            mTv3 = (TextView) findViewById(R.id.tv3);
            mTv4 = (TextView) findViewById(R.id.tv4);
            mTv5 = (TextView) findViewById(R.id.tv5);
            mTv6 = (TextView) findViewById(R.id.tv6);
            mTv7 = (TextView) findViewById(R.id.tv7);
            mLlfeatures = findViewById(R.id.ll_bjzt);
            mRgmaplx = findViewById(R.id.rg_lx);
            mRgmaplx.setOnCheckedChangeListener(this);
            tv_hide = (TextView) findViewById(R.id.tv_hide);
            tv_hide.setOnClickListener(this);
            chartCurve = (ViewChartCurve) findViewById(R.id.c_chartCurve);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    public void setData(RequestDataBean rdatabean) {
        cleanData();
        setVisibility(VISIBLE);
        RequestDataBean.DataBean b = rdatabean.Data.get(0);
        setdata(b);
        String p=rdatabean.Data.get(0).S_PUMPSTATUS;
        //动态图例线条个数
        int length=p.length();
        JckIDarry=new String[length];
        Jcknamearry=new String[length];
        for (int i = 0; i <length ; i++) {
            JckIDarry[i]=i+"";
            Jcknamearry[i]="污水泵"+(i+1)+"号";
        }
        //泵机状态UI显示
        AppUtils.addViewbjzt(mContext ,mLlfeatures,p);
    }

    String ds = "%.4f";
    public void setdata(RequestDataBean.DataBean b) {
        chartCurve.setShow(b.S_DRAI_PUMP_ID);
        mTvaddrtitle.setText(b.S_SNAME);
        mTv1.setText(AppUtils.saveOneBit(ds, b.N_YEWEI) + "m");
        mTv2.setText(AppUtils.saveOneBit(ds, b.N_WAIYEWEI) + "m");
        mTv3.setText(AppUtils.saveOneBit(ds, b.COD) + "mg/L");
        mTv4.setText(AppUtils.saveOneBit(ds, b.NH3N) + "mg/L");
        mTv5.setText(AppUtils.saveOneBit(ds, b.TP) + "mg/L");
        mTv6.setText(AppUtils.saveOneBit(ds, b.PH) + "");
        mTv7.setText(b.T_STIME + "");
    }
    JCbzAclcBean jchis;
    public void getJCData_his(JCbzAclcBean jchis) {
        this.jchis = jchis;
        if (jchis == null || jchis.Data == null || jchis.Data == null) {
            return;
        }
        //每个类型的警戒线和超警戒线
        for (int i = 0; i < jchis.Data.Jingjx.size(); i++) {
            JCbzAclcBean.DataBean.JingjxBean yjlist = jchis.Data.Jingjx.get(i);
            yjlmap.put(yjlist.SITMID, yjlist.NWQGLLV);
            yjumap.put(yjlist.SITMID, yjlist.NWQGULV);
        }
        //默认显示第一个状态数据数据
        isechart=true;
        setlx(rbtext);
    }
    int f = 0;
    Map<String, Object> M = new HashMap<>();
    int  minInterval=0;
    public void setlx(String textstr) {
        if (jchis == null || jchis.Data == null) {
            return;
        }
        M.clear();
        f=0;
        ViewChartCurve.legend=0;
          minInterval= 0;
        switch (textstr) {

            case "NH3N":
                f = 0;
                M = JCbzAclcBean.getJCDBean_NH3N(jchis.Data.NH3N, null, null, yjlmap.get("NH3N"), yjumap.get("NH3N"), 0,true);
                break;
            case "COD":
                f = 0;
                M = JCbzAclcBean.getJCDBean_COD(jchis.Data.COD, null, null, yjlmap.get("COD"), yjumap.get("COD"), 0,true);
                break;
            case "TP":
                f = 0;
                M = JCbzAclcBean.getJCDBean_TP(jchis.Data.TP, null, null, yjlmap.get("TP"), yjumap.get("TP"), 0,true);
                break;
            case "瞬时流量":
                f = 3;//没有警戒线的多条折线
                M = JCbzAclcBean.getJCDBean_SSLL(jchis.Data , false);
                break;
            case "TN":
                f = 0;
                M = JCbzAclcBean.getJCDBean_TN(jchis.Data.TN, null, null, yjlmap.get("TN"), yjumap.get("TN"), 0,true);
                break;
            case "PH":
                f = 0;
                M = JCbzAclcBean.getJCDBean_PH(jchis.Data.PH, null, null, yjlmap.get("PH"), yjumap.get("PH"), 0,true);
                break;
            case "开关":
                f = 3;
                minInterval= 1;
                ViewChartCurve.legend=Jcknamearry.length;
                M = JCbzAclcBean.getJCDBean_PUMPSTATUS(jchis.Data.SPUMPSTATUS, JckIDarry, Jcknamearry, 1, 1, 2,true);
                M.put("ymax", 1);
                M.put("ymin", 0);
                break;
            case "液位":
                f = 1;
                double yjl=Math.max(yjlmap.get("N_YEWEI"),yjlmap.get("N_WAIYEWEI"));
                double yju=Math.max(yjumap.get("N_YEWEI"),yjumap.get("N_WAIYEWEI"));
                M = JCbzAclcBean.getJCDBean_YW(jchis.Data, yjl,yju,true);
                break;
        }
        setechart();
    }
    public void setechart(){
        if (M==null||M.size()== 0) {
            chartCurve.isWebviewShow(View.INVISIBLE);
            ToastUtils.showToast("数据为空");
        } else {
            chartCurve.isWebviewShow(View.VISIBLE);
            chartCurve. changeheigt(new InterfListen.D() {
                @Override
                public void dA(String s) {
                    M.put("legend",s);
                    M.put("isHaveJjx",true);
                    M.put("minInterval",minInterval);
                    if (f==3){
                        M.put("isHaveJjx",false);
                    }
                    if (f == 0) {
                        chartCurve.setsdata(new Gson().toJson(M), "report.html");
                    } else {
                        chartCurve.setsdata(new Gson().toJson(M), "report1.html");
                    }
                }
            });
    }
    }
    boolean isechart=false;
    public void cleanData() {
        mLlfeatures.removeAllViews();
        mRgmaplx.check(R.id.rb1_bzlx);
        chartCurve.isWebviewShow(View.INVISIBLE);
        isechart=false;
         rbtext="NH3N";
        mRgmaplx.check(R.id.rb1_bzlx);
        RequestDataBean.DataBean b = new RequestDataBean.DataBean();
        setdata(b);
    }

    InterfListen.A mA;

    public void setCallBackDataListener(InterfListen.A mA) {
        this.mA = mA;
    }
    int i;
    String rbtext="NH3N";
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton rb = findViewById(checkedId);
        if (group == mRgmaplx) {

            if ( isechart){
                LogUtils.e("mRgmaplx", i++);
            if (rb != null) {
                String str = rb.getText().toString();
                rbtext=str;
                setlx(str);
            }
            }
        }
    }
    public void setEListen(InterfListen.E mE){
        chartCurve.setEListen(mE);
    }
}

