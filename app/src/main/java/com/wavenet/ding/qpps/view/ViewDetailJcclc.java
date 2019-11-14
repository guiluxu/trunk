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

import java.util.HashMap;
import java.util.Map;


public class ViewDetailJcclc extends LinearLayout implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {
    Context mContext;
    RadioGroup mRgmap;
    RadioGroup mRgmaplx;
    TextView mTvaddrtitle,mTv1,mTv2,mTv3, mTv4, mTv5, mTv6, mTv7, tv_hide;
    ViewChartCurve chartCurve;
    String[] JckIDarry;
    String[] Jcknamearry;
    Map<String ,Double> yjlmap=new HashMap<>();
    Map<String ,Double> yjumap=new HashMap<>();
    public ViewDetailJcclc(Context context) {
        super(context);
        initView(context);
    }

    public ViewDetailJcclc(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ViewDetailJcclc(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        setOrientation(LinearLayout.VERTICAL);
        setOnClickListener(this);
        LayoutInflater.from(mContext).inflate(R.layout.view_jcdetailsclc, this);
        if (!isInEditMode()) {
            mTvaddrtitle = findViewById(R.id.tv_addrtitle);
            mTv1 =findViewById(R.id.tv1);
            mTv2 =  findViewById(R.id.tv2);
            mTv3 = findViewById(R.id.tv3);
            mTv4 = findViewById(R.id.tv4);
            mTv5 =findViewById(R.id.tv5);
            mTv6 =  findViewById(R.id.tv6);
            mRgmap =  findViewById(R.id.rg);
            mRgmap.setOnCheckedChangeListener(this);
            mRgmaplx =  findViewById(R.id.rg_lx);
            mRgmaplx.setOnCheckedChangeListener(this);
            tv_hide = findViewById(R.id.tv_hide);
            tv_hide.setOnClickListener(this);
            chartCurve =  findViewById(R.id.c_chartCurve);

        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
    public void  initData(){
    }
    RequestDataBean rdatabean=null;
    public   void  setData(RequestDataBean rdatabean){
        cleanData();
        setVisibility(VISIBLE);
        this.rdatabean=rdatabean;
        RequestDataBean.DataBean b=rdatabean.Data.get(0);
        setdata(b);
        //动态图例线条个数
        JckIDarry=new String[rdatabean.Data.size()];
        Jcknamearry=new String[rdatabean.Data.size()];
        for (int i = 0; i <rdatabean.Data.size() ; i++) {
            JckIDarry[i]=rdatabean.Data.get(i).JckID;
            Jcknamearry[i]=rdatabean.Data.get(i).JckName;
        }
        //出口入口UI
        AppUtils.addview(mContext,mRgmap, AppUtils.getListSize(rdatabean));
    }
    String ds="%.4f";
    public  void setdata( RequestDataBean.DataBean b){
        chartCurve.setShow(b.FactId);
        mTvaddrtitle.setText(b.FactName);
        mTv1.setText(AppUtils.saveOneBit(ds,b.COD)+"mg/L");
        mTv2.setText(AppUtils.saveOneBit(ds,b.NH3N)+"mg/L");
        mTv3.setText(AppUtils.saveOneBit(ds,b.TP)+"mg/L");
        mTv4.setText(AppUtils.saveOneBit(ds,b.TN)+"mg/L");
        mTv5.setText(AppUtils.saveOneBit(ds,b.PH)+"");
        mTv6.setText(b.Datetime);
    }
    JCbzAclcBean jchis;
    boolean isechart=false;
    public void getJCData_his( JCbzAclcBean jchis){
        this.jchis=jchis;
        if (jchis==null||jchis.Data==null||jchis.Data==null){
            return;
        }
        yjlmap.put("NH3N",0.0);
        yjumap.put("NH3N",0.0);
        //每个类型的警戒线和超警戒线
//        for (int i = 0; i <jchis.Data.Jingjx.size() ; i++) {
//            JCbzAclcBean.DataBean.JingjxBean yjlist=jchis.Data.Jingjx.get(i);
//            yjlmap.put(yjlist.SITMID,yjlist.NWQGLLV);
//            yjumap.put(yjlist.SITMID,yjlist.NWQGULV);
//        }
        //默认显示第一个状态数据数据
        isechart=true;
        setlx(rbtext);
    }
    Map<String ,Object> M=new HashMap<>();
    public void setlx(String textstr){
        if (jchis==null||jchis.Data==null){
            return;
        }
        ViewChartCurve.legend=Jcknamearry.length;
        M.clear();
        switch (textstr){
            case "NH3N":
                M=JCbzAclcBean.getJCDBean_NH3N(jchis.Data.NH3N,  JckIDarry,Jcknamearry,yjlmap.get("NH3N"),yjumap.get("NH3N"),1,false);
                break;
                case "COD":
                M=JCbzAclcBean.getJCDBean_COD(jchis.Data.COD,  JckIDarry,Jcknamearry,yjlmap.get("NH3N"),yjumap.get("NH3N"),1,false);
                break; case "TP":
              M=JCbzAclcBean.getJCDBean_TP(jchis.Data.TP,  JckIDarry,Jcknamearry,yjlmap.get("NH3N"),yjumap.get("NH3N"),1,false);
                break;case "瞬时流量":
               M=JCbzAclcBean.getJCDBean_SSLL(jchis.Data.SSLL,  JckIDarry,Jcknamearry,yjlmap.get("NH3N"),yjumap.get("NH3N"),1,false);
//               M=JCbzAclcBean.getJCDBean_SSLL(jchis.Data,false);
                break;case "TN":
               M=JCbzAclcBean.getJCDBean_TN(jchis.Data.TN,  JckIDarry,Jcknamearry,yjlmap.get("NH3N"),yjumap.get("NH3N"),1,false);
                break;case "PH":
                M=JCbzAclcBean.getJCDBean_PH(jchis.Data.PH,  JckIDarry,Jcknamearry,yjlmap.get("NH3N"),yjumap.get("NH3N"),1,false);
                break;
        }
        setechart();
    }
    public void setechart(){
        if (M==null||M.size()==0){
            chartCurve.isWebviewShow(View.INVISIBLE);
            ToastUtils.showToast("数据为空");
        }else {
            chartCurve.changeheigt(new InterfListen.D() {
                @Override
                public void dA(String s) {
                    chartCurve.isWebviewShow(View.VISIBLE);
                    M.put("minInterval",0);
                    M.put("isHaveJjx",false);
                    M.put("legend",s);
                    chartCurve.setsdata(new Gson().toJson(M),"report1.html");
                }
            });
        }
    }
    public void cleanData(){
        mRgmap.removeAllViews();
        chartCurve.isWebviewShow(View.INVISIBLE);
        isechart=false;
        mRgmaplx.check(R.id.rb1_lx);
        rbtext="NH3N";
        RequestDataBean.DataBean b=new RequestDataBean.DataBean();
        setdata( b);
    }
    public   void  isHide(boolean b){
        tv_hide.setVisibility(b? View.GONE: View.VISIBLE);
//        chartCurve.setVisibility(b? View.VISIBLE: View.GONE);
//        if (chartCurve.getVisibility()== View.VISIBLE){
//            if (isFrist &&!TextUtils.isEmpty(sNo)){
//                isFrist=false;
//                chartCurve.setShow(sNo);
//            }
//        }
    }





    InterfListen.A mA;
    public void setCallBackDataListener( InterfListen.A mA) {
        this.mA = mA;
    }
   String rbtext="NH3N";
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton rb = findViewById(checkedId);
        if (group==mRgmap){
                if (rb!=null&&rb.getTag()!=null){
                    int tag = (int) rb.getTag();
                    setdata( ViewDetailJcclc.this.rdatabean.Data.get(tag));
                }
        }else if (group==mRgmaplx) {
            if (isechart){
            if (rb!=null){
                String str =  rb.getText().toString();
                rbtext=str;
                setlx( str);
                } }
        }

    }
    public void setEListen(InterfListen.E mE){
        chartCurve.setEListen(mE);
    }
}
