package com.wavenet.ding.qpps.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.adapter.MapLegendAdapter;
import com.wavenet.ding.qpps.api.QPSWApplication;
import com.wavenet.ding.qpps.bean.Legendbean;
import com.wavenet.ding.qpps.interf.CallBackMap;
import com.wavenet.ding.qpps.interf.InterfListen;
import com.wavenet.ding.qpps.interf.OnClickInteDef;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.AppUtils;

import java.util.ArrayList;


public class ViewMapLegend extends LinearLayout implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    public ArcGISMapImageLayer mainMapImageLayer;
    Context mContext;
    View mView;
    RadioGroup mRgmap;
    WrapContentListView mWclvTc;
    MapLegendAdapter mAdapter;
    ArrayList<Legendbean> mListBeen=new ArrayList<>();
    /**
     * 侧滑动画
     */
    boolean isShow = false;//侧滑菜单是否显示隐藏
    CallBackMap mCallBackMap;
    String mOnClick = "TDTVEC";

    public ViewMapLegend(Context context) {
        super(context);
        initView(context);
    }

    public ViewMapLegend(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ViewMapLegend(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(mContext).inflate(R.layout.view_maplegend, this);
        if (!isInEditMode()) {
            mView = findViewById(R.id.v_bg);
            mView.setOnClickListener(this);
            mView = findViewById(R.id.v_bg);
            mView.setOnClickListener(this);
            //地图切换
            LinearLayout ll_mapchange = findViewById(R.id.ll_mapchange);
            mRgmap = findViewById(R.id.rg_map);
            mRgmap.setOnCheckedChangeListener(this);

            mWclvTc = findViewById(R.id.wclv_tc);
            initData();
        }
    }
public void initData(){
    mAdapter=new MapLegendAdapter(mContext,mListBeen);
    mWclvTc.setAdapter(mAdapter);
    mListBeen.clear();
    mListBeen.addAll(AppUtils.getTcData()) ;
    mAdapter.notifyDataSetChanged();
}


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (radioGroup == mRgmap) {
            if (mG==null){
                return;
            }
            QPSWApplication.CenterpointPolygon = mG.gA().getVisibleArea();
            AppTool.getScreenCenterPoint_84((Activity) mContext,mG.gA());
            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.rb_map:
                    mOnClick = OnClickInteDef.setOnclick(OnClickInteDef.TDTVEC);
                    break;
                case R.id.rb_satellite:
                    mOnClick = OnClickInteDef.setOnclick(OnClickInteDef.TDTIMG);
                    break;
            }
            if (mCallBackMap!=null){
                mCallBackMap.onClick(mOnClick);
            }
        }
    }

    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.v_bg:
        if (mView.getVisibility() == View.VISIBLE) {
            showOrHide("");
        }
        break;
}
    }

public  boolean isHadSelectIv(){
    for (int i = 0; i <mListBeen.size() ; i++) {
        if (mListBeen.get(i).isSelectIv){
            i=mListBeen.size()-1;
            return true;
        }
    }
    return false;
}
public ArrayList<Legendbean> getListbean(){
    return mListBeen;
}public ArrayList<Legendbean> setListbean(int type){
        mListBeen.get(type).isSelectIv=true;
        mListBeen.get(type).isSelectLl=true;
        mAdapter.notifyDataSetChanged();
    return mListBeen;
}
    public void showOrHide(String show) {
        isShow = this.getVisibility() == View.VISIBLE;
        this.setAnimation(AnimationUtils.loadAnimation(mContext, !isShow ? R.anim.in_from_right : R.anim.out_from_right));
            this.setVisibility(!isShow ? View.VISIBLE : View.GONE);
            mView.setVisibility(!isShow ? View.VISIBLE : View.GONE);
            if (mH!=null){
                mH.hA(isShow ? View.VISIBLE : View.GONE);
            }

    }


    public void setCallBackMap(CallBackMap mCallBackMap) {
        this.mCallBackMap = mCallBackMap;
    }

    public void setAListener(MapLegendAdapter.A mA) {
        mAdapter.setAListener(mA);
    }
    InterfListen.G mG;
    InterfListen.H mH;
    public void setIGListener(InterfListen.G mG) {
       this.mG=mG;
    }
    public void setIHListen(InterfListen.H mH) {
        this.mH = mH;
    }
}
