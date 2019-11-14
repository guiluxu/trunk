package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dereck.library.utils.ToastUtils;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.bean.JCswBean;
import com.wavenet.ding.qpps.bean.RequestDataBean;
import com.wavenet.ding.qpps.interf.InterfListen;
import com.wavenet.ding.qpps.utils.AppUtils;


public class ViewDetailJcsw extends LinearLayout implements View.OnClickListener {
    Context mContext;
    View mView,mViclegendico1;
    RadioGroup mRgmap;
    TextView mTvaddrtitle,mTv1,mTv2,mTv3, mTv4, mTv5, mTv6, mTv7, tv_hide;
    RelativeLayout mlnavi;
LinearLayout mLlfeatures;
    ViewChartCurve chartCurve;
    public ViewDetailJcsw(Context context) {
        super(context);
        initView(context);
    }

    public ViewDetailJcsw(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ViewDetailJcsw(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        setOrientation(LinearLayout.VERTICAL);
        setOnClickListener(this);
        LayoutInflater.from(mContext).inflate(R.layout.view_jcdetailssw, this);
        if (!isInEditMode()) {
            mTvaddrtitle = (TextView) findViewById(R.id.tv_addrtitle);
            mTv1 = (TextView) findViewById(R.id.tv1);
            mTv2 = (TextView) findViewById(R.id.tv2);
            mTv3 = (TextView) findViewById(R.id.tv3);
            mTv4 = (TextView) findViewById(R.id.tv4);
            mTv5 = (TextView) findViewById(R.id.tv5);
            tv_hide = (TextView) findViewById(R.id.tv_hide);
            tv_hide.setOnClickListener(this);

            chartCurve = findViewById(R.id.c_chartCurve);

        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    public   void  setData(RequestDataBean rdatabean){
        cleanData();
        setVisibility(VISIBLE);
        RequestDataBean.DataBean b=rdatabean.Data.get(0);
        setdata(b);
    }
    String ds="%.4f";
    public  void setdata( RequestDataBean.DataBean b){
        chartCurve.setShow(b.GWNo);
        mTvaddrtitle.setText(b.GWName+"");
        mTv1.setText(b.GWNo);
        mTv2.setText(AppUtils.saveOneBit(ds,b.XDSW)+"");
        mTv3.setText(AppUtils.saveOneBit(ds,b.ZDSS)+"");
        mTv4.setText(AppUtils.saveOneBit(ds,b.JDSW)+"");
        mTv5.setText(b.Time);
    }
    public void getJCData_his( JCswBean jchis){
        if (jchis==null||jchis.Data==null||jchis.Data.Data==null||jchis.Data.Data.size()==0){
            chartCurve.isWebviewShow(View.INVISIBLE);
            ToastUtils.showToast("没有查询到数据");
            return;
        }
        chartCurve.isWebviewShow(View.VISIBLE);
        chartCurve.setsdata(AppUtils.getJChisData_one( jchis),"report.html");
    }
    public void cleanData(){
        chartCurve.isWebviewShow(View.INVISIBLE);
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
    public void setEListen(InterfListen.E mE){
        chartCurve.setEListen(mE);
    }
}
