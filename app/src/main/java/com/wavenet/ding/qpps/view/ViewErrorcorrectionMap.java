//package com.wavenet.ding.qpps.view;
//
//import android.animation.ObjectAnimator;
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.animation.AnimationUtils;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RadioGroup;
//
//import com.dereck.library.utils.ToastUtils;
//import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
//import com.esri.arcgisruntime.layers.ArcGISMapImageSublayer;
//import com.esri.arcgisruntime.loadable.LoadStatus;
//import com.wavenet.ding.qpps.R;
//import com.wavenet.ding.qpps.interf.CallBackMap;
//import com.wavenet.ding.qpps.interf.OnClickInteDef;
//import com.wavenet.ding.qpps.utils.AppConfig;
//import com.wavenet.ding.qpps.utils.AppTool;
//import com.wavenet.ding.qpps.utils.SPUtil;
//
//
//public class ViewErrorcorrectionMap extends LinearLayout implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
//
//    public ArcGISMapImageLayer mainMapImageLayer;
//    Context mContext;
//
//    public ViewErrorcorrectionMap(Context context) {
//        super(context);
//        initView(context);
//    }
//
//    public ViewErrorcorrectionMap(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        initView(context);
//    }
//
//    public ViewErrorcorrectionMap(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        initView(context);
//    }
//
//    private void initView(Context context) {
//        mContext = context;
//        setOrientation(LinearLayout.VERTICAL);
//        LayoutInflater.from(mContext).inflate(R.layout.view_legend, this);
//        if (!isInEditMode()) {
//
////            mLlbengzhan = findViewById(R.id.ll_bengzhan);
////            mLlbengzhan.setOnClickListener(this);
////
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()) {
//            case R.id.v_bg:
//                if (mView.getVisibility() == View.VISIBLE) {
//                    showOrHide("");
//                }
//                break;
//            case R.id.iv_bengzhan:
//                setShowLayer(mIvbengzhan, AppConfig.IndexPSBZ);
//
//                break;
//            case R.id.iv_guanwangys:
//                setShowLayer(mIvguanwangys, AppConfig.IndexPSGD);
//
//                break;
//            case R.id.iv_guanwangws:
//                setShowLayer(mIvguanwangws, AppConfig.IndexPSGDWS);//污水地图图层2下标为2，
//
//                break;
//            case R.id.iv_jianchajing:
//                setShowLayer(mIvjianchajing, AppConfig.IndexPSJ);
//
//                break;
//            case R.id.iv_wushuichang:
//                setShowLayer(mIvwushuichang, AppConfig.IndexWSCLC);
//
//                break;
//            case R.id.iv_paishuihu:
//                setShowLayer(mIvpaishuihu, AppConfig.IndexPSH);
//
//                break;
//            case R.id.iv_zhongdianpfk:
//                setShowLayer(mIvzhongdianpfk, AppConfig.IndexZDPFK);
//
//                break;
//            case R.id.iv_pfk:
//                setShowLayer(mIvpfk, AppConfig.IndexPFK);
//                break;
////                default:setmOnClickLl( v);
////                break;
//        }
//    }
//    public void setmOnClickLl(View v) {
//        switch (v.getId()) {
//            case R.id.ll_bengzhan:
//                setSeachLayer(v, mIvbengzhan,AppConfig.IndexPSBZ);
//                break;
//            case R.id.ll_guanwangys:
//                setSeachLayer(v, mIvguanwangys,AppConfig.IndexPSGD);
//
//                break;
//            case R.id.ll_guanwangws:
//                setSeachLayer(v, mIvguanwangws,AppConfig.IndexPSGDWS);//污水地图图层2下标为2，
//
//                break;
//            case R.id.ll_wushuichang:
//                setSeachLayer(v, mIvwushuichang,AppConfig.IndexWSCLC);
//
//                break;
//            case R.id.ll_paishuihu:
//                setSeachLayer(v, mIvpaishuihu,AppConfig.IndexPSH);
//
//                break;
//            case R.id.ll_zhongdianpfk:
//                setSeachLayer(v, mIvzhongdianpfk,AppConfig.IndexZDPFK);
//
//                break;
//            case R.id.ll_pfk:
//                setSeachLayer(v, mIvpfk,AppConfig.IndexPFK);
//
//                break;
//        }
//    }
//    public void setSeachLayer(View v,View iv, int index) {
//        if (mainMapImageLayer.getLoadStatus() != LoadStatus.LOADED) {
//            ToastUtils.showToast("图层加载失败");
//            return;
//        }
//        if (!iv.isSelected()){
//            iv.setSelected(true);
//            isShowLayer(iv.isSelected(),index);
//        }
//        colseAllSeachLayer();
////        mAdminActivity.mFourthFragment.initStyleSingleTap();
//        v.setSelected(true);
//    }
//    public void setMainMapImageLayer(ArcGISMapImageLayer mainMapImageLayer) {
//        this.mainMapImageLayer = mainMapImageLayer;
//    }
//
//    public void setShowLayer(Boolean isShowL,int index) {
//
//        isShowLayer(iv.isSelected(), index);
//    }
//
//    String townname="";
//    public void isShowLayer(Boolean isSelete, int index) {
//        townname=  SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNNAME);
//        if (index== 2001 ){//雨水管网
//            //管
//            ArcGISMapImageSublayer mImageSublayerG= (ArcGISMapImageSublayer)mainMapImageLayer.getSubLayerContents().get(2).getSubLayerContents().get(0);
//            mImageSublayerG.setVisible(isSelete);
//            loopImageSublayer(mImageSublayerG,isSelete);
//            //井
//            ArcGISMapImageSublayer mImageSublayerJ= (ArcGISMapImageSublayer) mainMapImageLayer.getSubLayerContents().get(3).getSubLayerContents().get(0);
//            mImageSublayerJ.setVisible(isSelete);
//            loopImageSublayer(mImageSublayerJ,isSelete);
//        }else  if (index == 2002 ){//污水管网
//            //管
//            ArcGISMapImageSublayer mImageSublayerG= (ArcGISMapImageSublayer)mainMapImageLayer.getSubLayerContents().get(2).getSubLayerContents().get(1);
//            mImageSublayerG.setVisible(isSelete);
//            loopImageSublayer(mImageSublayerG,isSelete);
//            //井
//            ArcGISMapImageSublayer mImageSublayerJ= (ArcGISMapImageSublayer) mainMapImageLayer.getSubLayerContents().get(3).getSubLayerContents().get(1  );
//            mImageSublayerJ.setVisible(isSelete);
//            loopImageSublayer(mImageSublayerJ,isSelete);
//        }else {//其他图层（单个图层架构）
//            ArcGISMapImageSublayer mImageSublayer= (ArcGISMapImageSublayer) mainMapImageLayer.getSubLayerContents().get(index);
//            loopImageSublayer(mImageSublayer,isSelete);
//        }
//    }
//    public void loopImageSublayer(ArcGISMapImageSublayer mISublayer,Boolean b){
//        int size=mISublayer.getSubLayerContents().size();
//        if(size>0){
//            for (int i = 0; i <size ; i++) {
//                ArcGISMapImageSublayer mIs= (ArcGISMapImageSublayer) mISublayer.getSubLayerContents().get(i);
//                loopImageSublayer(mIs,b);
//            }
//        }else {
//            if (!AppTool.isNull(townname)){
//                mISublayer.setDefinitionExpression("S_town='"+townname+"'");
//            }
//            mISublayer.setVisible(b);
//        }
//    }
//    public void initShowLayer(boolean IsShowLayer) {
//        mIvbengzhan.setSelected(IsShowLayer);
//        mIvguanwangys.setSelected(IsShowLayer);
//        mIvguanwangws.setSelected(IsShowLayer);
//        mIvjianchajing.setSelected(IsShowLayer);
//        mIvwushuichang.setSelected(IsShowLayer);
//        mIvpaishuihu.setSelected(IsShowLayer);
//        mIvzhongdianpfk.setSelected(IsShowLayer);
//        mIvpfk.setSelected(IsShowLayer);
//
//
//    }
//
//    public void initShowLayerchange() {
//        if (mIvbengzhan.isSelected()) {
//            isShowLayer(mIvbengzhan.isSelected(), AppConfig.IndexPSBZ);
//        }
//        if (mIvguanwangys.isSelected()) {
//            isShowLayer(mIvguanwangys.isSelected(), AppConfig.IndexPSGD);
//        }
//        if (mIvguanwangws.isSelected()) {
//            isShowLayer(mIvguanwangws.isSelected(), AppConfig.IndexPSGDWS);
//        }
//        if (mIvjianchajing.isSelected()) {
//            isShowLayer(mIvjianchajing.isSelected(), AppConfig.IndexPSJ);
//        }
//        if (mIvwushuichang.isSelected()) {
//            isShowLayer(mIvwushuichang.isSelected(), AppConfig.IndexWSCLC);
//        }
//        if (mIvpaishuihu.isSelected()) {
//            isShowLayer(mIvpaishuihu.isSelected(), AppConfig.IndexPSH);
//        }
//        if (mIvzhongdianpfk.isSelected()) {
//            isShowLayer(mIvzhongdianpfk.isSelected(), AppConfig.IndexZDPFK);
//        }
//        if (mIvpfk.isSelected()) {
//            isShowLayer(mIvpfk.isSelected(), AppConfig.IndexPFK);
//        }
//    }
//
//
//
//    public void showOrHide(String show) {
//        isShow = this.getVisibility() == View.VISIBLE;
//        this.setAnimation(AnimationUtils.loadAnimation(mContext, !isShow ? R.anim.in_from_right : R.anim.out_from_right));
//        this.setVisibility(!isShow ? View.VISIBLE : View.GONE);
//        mView.setVisibility(!isShow ? View.VISIBLE : View.GONE);
//
//    }
//
//    /**
//     * 属性侧滑动画
//     */
//    public void showOrHide(final boolean show, View v) {
//        translate(show, v);
////        v.setAnimation(AnimationUtils.loadAnimation(mContext, show ? R.anim.in_lefttoright : R.anim.out_righttoleft))；
//    }
//
//    /**
//     * 平移属性侧滑动画
//     */
//    public void translate(final boolean show, final View v) {
//        ObjectAnimator.ofFloat(v, "translationX", v.getTranslationX(), show ? -840f : 0.0f).setDuration(500).start();
//    }
//
//    public void setCallBackMap(CallBackMap mCallBackMap) {
//        this.mCallBackMap = mCallBackMap;
//    }
//
//    @Override
//    public void onCheckedChanged(RadioGroup radioGroup, int i) {
//        if (radioGroup == mRgmap) {
//            switch (radioGroup.getCheckedRadioButtonId()) {
//                case R.id.rb_map:
//                    mOnClick = OnClickInteDef.setOnclick(OnClickInteDef.TDTVEC);
//                    break;
//                case R.id.rb_satellite:
//                    mOnClick = OnClickInteDef.setOnclick(OnClickInteDef.TDTIMG);
//                    break;
//            }
//            mCallBackMap.onClick(mOnClick);
//        }
//    }
//}
