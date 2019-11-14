package com.wavenet.ding.qpps.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dereck.library.utils.ToastUtils;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.ArcGISMapImageSublayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.interf.CallBackMap;
import com.wavenet.ding.qpps.interf.OnClickInteDef;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.MapdownloadUtil;
import com.wavenet.ding.qpps.utils.SPUtil;


public class ViewLegend extends LinearLayout implements View.OnClickListener, RadioGroup.OnCheckedChangeListener ,MapdownloadUtil.A{
    public ImageView mIvbengzhan, mIvguanwang, mIvguanwangws, mIvguanwangys, mIvjianchajing, mIvwushuichang, mIvpaishuihu, mIvzhongdianpfk, mIvpfk;
    public ArcGISMapImageLayer mainMapImageLayer;
    public LinearLayout mLlbengzhan, mLlguanwangys, mLlguanwangws, mLljianchajing, mLlwushuichang, mLlpaishuihu, mLlzhongdianpfk, mLlpfk,mLlmapdownload;
    TextView mTvmapdownload1,mTvmapdownload2;
    Context mContext;
    View mView;
    RadioGroup mRgmap;
    MapdownloadUtil mapdownloadUtil;
    /**
     * 侧滑动画
     */
    boolean isShow = false;//侧滑菜单是否显示隐藏
    CallBackMap mCallBackMap;
    String mOnClick = "TDTVEC";
    int sum1 = 0;//地图显示可查询的图层个数
    String townname = "";

    public ViewLegend(Context context) {
        super(context);
        initView(context);
    }

    public ViewLegend(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ViewLegend(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(mContext).inflate(R.layout.view_legend, this);
        if (!isInEditMode()) {
            mView = findViewById(R.id.v_bg);
            mView.setOnClickListener(this);
            findViewById(R.id.ll_jiance).setVisibility(GONE);
            //地图切换
            LinearLayout ll_mapchange = findViewById(R.id.ll_mapchange);
            ll_mapchange.setVisibility(GONE);
            //人员控制
            LinearLayout ll_people = findViewById(R.id.ll_people);
            ll_people.setVisibility(GONE);
            mRgmap = findViewById(R.id.rg_map);
            mRgmap.setOnCheckedChangeListener(this);
            mIvbengzhan = findViewById(R.id.iv_bengzhan);
            mIvbengzhan.setOnClickListener(this);

            mIvguanwangys = findViewById(R.id.iv_guanwangys);
            mIvguanwangys.setOnClickListener(this);
            mIvguanwangws = findViewById(R.id.iv_guanwangws);
            mIvguanwangws.setOnClickListener(this);
            mIvjianchajing = findViewById(R.id.iv_jianchajing);
            mIvjianchajing.setOnClickListener(this);

            mIvwushuichang = findViewById(R.id.iv_wushuichang);
            mIvwushuichang.setOnClickListener(this);

            mIvpaishuihu = findViewById(R.id.iv_paishuihu);
            mIvpaishuihu.setOnClickListener(this);

            mIvzhongdianpfk = findViewById(R.id.iv_zhongdianpfk);
            mIvzhongdianpfk.setOnClickListener(this);

            mIvpfk = findViewById(R.id.iv_pfk);
            mIvpfk.setOnClickListener(this);
            mLlbengzhan = findViewById(R.id.ll_bengzhan);
            mLlbengzhan.setOnClickListener(this);

            mLlguanwangys = findViewById(R.id.ll_guanwangys);
            mLlguanwangys.setOnClickListener(this);
            mLlguanwangws = findViewById(R.id.ll_guanwangws);
            mLlguanwangws.setOnClickListener(this);

            mLlwushuichang = findViewById(R.id.ll_wushuichang);
            mLlwushuichang.setOnClickListener(this);

            mLlpaishuihu = findViewById(R.id.ll_paishuihu);
            mLlpaishuihu.setOnClickListener(this);

            mLlzhongdianpfk = findViewById(R.id.ll_zhongdianpfk);
            mLlzhongdianpfk.setOnClickListener(this);

            mLlpfk = findViewById(R.id.ll_pfk);
            mLlpfk.setOnClickListener(this);

            mTvmapdownload1 = findViewById(R.id.tv_mapdownload1);
            mTvmapdownload2 = findViewById(R.id.tv_mapdownload2);
            mLlmapdownload = findViewById(R.id.ll_mapdownload);
            mLlmapdownload.setOnClickListener(this);
           ;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.v_bg:
                if (mView.getVisibility() == View.VISIBLE) {
                    showOrHide("");
                }
                break;
            case R.id.ll_mapdownload:
                mapdownloadUtil.startDown();
                break;
            case R.id.iv_bengzhan:
                setShowLayer(mIvbengzhan, AppConfig.IndexPSBZ);
                break;
            case R.id.iv_guanwangys:
                setShowLayer(mIvguanwangys, AppConfig.IndexPSGD);
                break;
            case R.id.iv_guanwangws:
                setShowLayer(mIvguanwangws, AppConfig.IndexPSGDWS);//污水地图图层2下标为2，
                break;
            case R.id.iv_jianchajing:
                setShowLayer(mIvjianchajing, AppConfig.IndexPSJ);
                break;
            case R.id.iv_wushuichang:
                setShowLayer(mIvwushuichang, AppConfig.IndexWSCLC);
                break;
            case R.id.iv_paishuihu:
                setShowLayer(mIvpaishuihu, AppConfig.IndexPSH);
                break;
            case R.id.iv_zhongdianpfk:
                setShowLayer(mIvzhongdianpfk, AppConfig.IndexZDPFK);
                break;
            case R.id.iv_pfk:
                setShowLayer(mIvpfk, AppConfig.IndexPFK);
                break;
            default:
                setmOnClickLl(v);
                break;
        }
    }

    public void changeLayer(int urlid) {//根据图层ID判断打开哪个图层
        if (mainMapImageLayer==null){
            ToastUtils.showToast("底图加载失败");
            return;
        }
        ImageView iv = mIvbengzhan;
        LinearLayout Ll = mLlbengzhan;
        switch (urlid) {

            case AppConfig.PSBZ:
                iv = mIvbengzhan;
                Ll = mLlbengzhan;
                break;
            case AppConfig.PSGD:
                iv = mIvguanwangys;
                Ll = mLlguanwangys;
                break;
            case AppConfig.PSGDJ1:
                iv = mIvguanwangys;
                Ll = mLlguanwangys;
                break;
            case AppConfig.PSGDWS:
                iv = mIvguanwangws;
                Ll = mLlguanwangws;
                break;
            case AppConfig.PSGDWSJ1:
                iv = mIvguanwangws;
                Ll = mLlguanwangws;
                break;
            case AppConfig.WSCLC:
                iv = mIvwushuichang;
                Ll = mLlwushuichang;
                break;
            case AppConfig.PSH:
                iv = mIvpaishuihu;
                Ll = mLlpaishuihu;
                break;
            case AppConfig.ZDPFK:
                iv = mIvzhongdianpfk;
                Ll = mLlzhongdianpfk;
                break;
            case AppConfig.PFK:
                iv = mIvpfk;
                Ll = mLlpfk;
                break;
        }
//        mAdminActivity.mFourthFragment.initStyleSingleTap();// TODO
        colseAllSeachLayer();
        Ll.setSelected(true);
        iv.setSelected(false);
        onClick(iv);

    }

    public void setmOnClickLl(View v) {
        if (mainMapImageLayer==null){
            ToastUtils.showToast("底图加载失败");
            return;
        }
        switch (v.getId()) {
            case R.id.ll_bengzhan:
                setSeachLayer(v, mIvbengzhan,AppConfig.IndexPSBZ);
                break;
            case R.id.ll_guanwangys:
                setSeachLayer(v, mIvguanwangys,AppConfig.IndexPSGD);

                break;
            case R.id.ll_guanwangws:
                setSeachLayer(v, mIvguanwangws,AppConfig.IndexPSGDWS);//污水地图图层2下标为2，

                break;
            case R.id.ll_wushuichang:
                setSeachLayer(v, mIvwushuichang,AppConfig.IndexWSCLC);

                break;
            case R.id.ll_paishuihu:
                setSeachLayer(v, mIvpaishuihu,AppConfig.IndexPSH);

                break;
            case R.id.ll_zhongdianpfk:
                setSeachLayer(v, mIvzhongdianpfk,AppConfig.IndexZDPFK);

                break;
            case R.id.ll_pfk:
                setSeachLayer(v, mIvpfk,AppConfig.IndexPFK);

                break;
        }
    }
    public void setSeachLayer(View v,View iv, int index) {
        if (mainMapImageLayer !=null&&mainMapImageLayer.getLoadStatus() != LoadStatus.LOADED) {
            ToastUtils.showToast("图层加载失败");
            return;
        }
            if (!iv.isSelected()){
                iv.setSelected(true);
                isShowLayer(iv.isSelected(),index);
            }


        colseAllSeachLayer();
//        mAdminActivity.mFourthFragment.initStyleSingleTap();
        v.setSelected(true);
    }

    public void setMainMapImageLayer(ArcGISMapImageLayer mainMapImageLayer) {
        this.mainMapImageLayer = mainMapImageLayer;
    }

    public void setShowLayer(ImageView iv, int index) {
        if (mainMapImageLayer==null){
            ToastUtils.showToast("底图加载失败");
            return;
        }
        if (mainMapImageLayer!=null&&mainMapImageLayer.getLoadStatus() != LoadStatus.LOADED) {
            ToastUtils.showToast("图层加载失败");
            return;
        }
        boolean temIsSelete = iv.isSelected();
//        //打开单个图层
//        colseAllLayer();
        //打开多个图层
//        if (!iv.isSelected()) {
//            sum++;
//        } else {
//            sum--;
//        }
//        if (sum > 3) {
//            sum = 3;
//            ToastUtils.showToast("最多选择一个图层");
//            return;
//        }
        iv.setSelected(!temIsSelete);
        isShowLayer(iv.isSelected(), index);
    }

    public void isShowLayer(Boolean isSelete, int index) {
        townname=  SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNNAME);
        if (index== 2001 ){//雨水管网

            //管
            ArcGISMapImageSublayer mImageSublayerG= (ArcGISMapImageSublayer)mainMapImageLayer.getSubLayerContents().get(2).getSubLayerContents().get(0);
            mImageSublayerG.setVisible(isSelete);
//            ArcGISMapImageSublayer mIs= (ArcGISMapImageSublayer) mImageSublayerG.getSubLayerContents().get(2);
//            mIs.setDefinitionExpression("S_town='"+townname+"'");
            loopImageSublayer(mImageSublayerG,isSelete);

            //井
            ArcGISMapImageSublayer mImageSublayerJ= (ArcGISMapImageSublayer) mainMapImageLayer.getSubLayerContents().get(3).getSubLayerContents().get(0);
            mImageSublayerJ.setVisible(isSelete);
//            ArcGISMapImageSublayer mIs1= (ArcGISMapImageSublayer) mImageSublayerJ.getSubLayerContents().get(3);
//            mIs1.setDefinitionExpression("S_town='"+townname+"'");
            loopImageSublayer(mImageSublayerJ,isSelete);
        }else  if (index == 2002 ){//污水管网
            //管
            ArcGISMapImageSublayer mImageSublayerG= (ArcGISMapImageSublayer)mainMapImageLayer.getSubLayerContents().get(2).getSubLayerContents().get(1) ;
            mImageSublayerG.setVisible(isSelete);
//            .getSubLayerContents().get(2)
//            ArcGISMapImageSublayer mIs= (ArcGISMapImageSublayer) mImageSublayerG.getSubLayerContents().get(3);
//            mIs.setDefinitionExpression("S_town='"+townname+"'");
            loopImageSublayer(mImageSublayerG,isSelete);
            //井
            ArcGISMapImageSublayer mImageSublayerJ= (ArcGISMapImageSublayer) mainMapImageLayer.getSubLayerContents().get(3).getSubLayerContents().get(1  );
            mImageSublayerJ.setVisible(isSelete);
//            ArcGISMapImageSublayer mIs1= (ArcGISMapImageSublayer) mImageSublayerG.getSubLayerContents().get(1);
//            mIs1.setDefinitionExpression("S_town='"+townname+"'");
            loopImageSublayer(mImageSublayerJ,isSelete);
        }else {//其他图层（单个图层架构）
            ArcGISMapImageSublayer mImageSublayer= (ArcGISMapImageSublayer) mainMapImageLayer.getSubLayerContents().get(index);
            loopImageSublayer(mImageSublayer,isSelete);
        }
    }
    public void loopImageSublayer(ArcGISMapImageSublayer mISublayer,Boolean b){
        int size=mISublayer.getSubLayerContents().size();
        if(size>0){
            for (int i = 0; i <size ; i++) {
                ArcGISMapImageSublayer mIs= (ArcGISMapImageSublayer) mISublayer.getSubLayerContents().get(i);
                loopImageSublayer(mIs,b);
            }
        }else {
            if (!AppTool.isNull(townname)){
//                LogUtils.e("upper(S","S_town='"+townname+"'");
//                mISublayer.setDefinitionExpression("S_town ='白鹤镇'")
                mISublayer.setDefinitionExpression("S_town='"+townname+"'");
            }
            mISublayer.setVisible(b);
        }
    }
    public void initShowLayer(boolean IsShowLayer) {
        mIvbengzhan.setSelected(IsShowLayer);
        mIvguanwangys.setSelected(IsShowLayer);
        mIvguanwangws.setSelected(IsShowLayer);
        mIvjianchajing.setSelected(IsShowLayer);
        mIvwushuichang.setSelected(IsShowLayer);
        mIvpaishuihu.setSelected(IsShowLayer);
        mIvzhongdianpfk.setSelected(IsShowLayer);
        mIvpfk.setSelected(IsShowLayer);


    }

    public void initShowLayerchange() {
        if (mIvbengzhan.isSelected()) {
            isShowLayer(mIvbengzhan.isSelected(), AppConfig.IndexPSBZ);
        }
        if (mIvguanwangys.isSelected()) {
            isShowLayer(mIvguanwangys.isSelected(), AppConfig.IndexPSGD);
        }
        if (mIvguanwangws.isSelected()) {
            isShowLayer(mIvguanwangws.isSelected(), AppConfig.IndexPSGDWS);
        }
        if (mIvjianchajing.isSelected()) {
            isShowLayer(mIvjianchajing.isSelected(), AppConfig.IndexPSJ);
        }
        if (mIvwushuichang.isSelected()) {
            isShowLayer(mIvwushuichang.isSelected(), AppConfig.IndexWSCLC);
        }
        if (mIvpaishuihu.isSelected()) {
            isShowLayer(mIvpaishuihu.isSelected(), AppConfig.IndexPSH);
        }
        if (mIvzhongdianpfk.isSelected()) {
            isShowLayer(mIvzhongdianpfk.isSelected(), AppConfig.IndexZDPFK);
        }
        if (mIvpfk.isSelected()) {
            isShowLayer(mIvpfk.isSelected(), AppConfig.IndexPFK);
        }
    }
    public void colseAllSeachLayer() {
        boolean isshow=false;
        if (mLlbengzhan.isSelected()) {
            isshow=mIvbengzhan.isSelected();
            mLlbengzhan.setSelected(false);
            mIvbengzhan.setSelected(isshow);
        }
        if (mLlguanwangys.isSelected()) {
            isshow=mIvguanwangys.isSelected();
            mLlguanwangys.setSelected(false);
            mIvguanwangys.setSelected(isshow);
        }
        if (mLlguanwangws.isSelected()) {
            isshow=mIvguanwangws.isSelected();
            mLlguanwangws.setSelected(false);
            mIvguanwangws.setSelected(isshow);
        }
        if (mLlwushuichang.isSelected()) {
            isshow=mIvwushuichang.isSelected();
            mLlwushuichang.setSelected(false);
            mIvwushuichang.setSelected(isshow);
        }
        if (mLlpaishuihu.isSelected()) {
            isshow=mIvpaishuihu.isSelected();
            mLlpaishuihu.setSelected(false);
            mIvpaishuihu.setSelected(isshow);
        }
        if (mLlzhongdianpfk.isSelected()) {
            isshow=mIvzhongdianpfk.isSelected();
            mLlzhongdianpfk.setSelected(false);
            mIvzhongdianpfk.setSelected(isshow);
        }
        if (mLlpfk.isSelected()) {
            isshow=mIvpfk.isSelected();
            mLlpfk.setSelected(false);
            mIvpfk.setSelected(isshow);
        }
    }

    public int showLayerSum() {
        sum1 = 0;
        if (mLlguanwangys.isSelected() || mLlguanwangws.isSelected()) {
            sum1++;//污水管道、雨水管道包括两个图层（管道、井）
        }
        getSum(mLlbengzhan);
        getSum(mLlguanwangys);
        getSum(mLlguanwangws);
        getSum(mLlwushuichang);
        getSum(mLlpaishuihu);
        getSum(mLlzhongdianpfk);
        getSum(mLlpfk);
        return sum1;
    }

    public int getSum(View v) {
        if (v.isSelected()) {
            sum1++;
        }
        return sum1;
    }


    public void showOrHide(String show) {
        isShow = this.getVisibility() == View.VISIBLE;
        this.setAnimation(AnimationUtils.loadAnimation(mContext, !isShow ? R.anim.in_from_right : R.anim.out_from_right));
        this.setVisibility(!isShow ? View.VISIBLE : View.GONE);
        mView.setVisibility(!isShow ? View.VISIBLE : View.GONE);

    }

    /**
     * 属性侧滑动画
     */
    public void showOrHide(final boolean show, View v) {
        translate(show, v);
//        v.setAnimation(AnimationUtils.loadAnimation(mContext, show ? R.anim.in_lefttoright : R.anim.out_righttoleft))；
    }

    /**
     * 平移属性侧滑动画
     */
    public void translate(final boolean show, final View v) {
        ObjectAnimator.ofFloat(v, "translationX", v.getTranslationX(), show ? -840f : 0.0f).setDuration(500).start();
    }

    public void setCallBackMap(CallBackMap mCallBackMap) {
        this.mCallBackMap = mCallBackMap;
    }
    @Override
    public void Aa(String a, String b) {
        mTvmapdownload1.setText(a);
        mTvmapdownload2.setText(b);
        if (AppTool.isNull(b)){
            mLlmapdownload.setOnClickListener(null);
        }else {
            mLlmapdownload.setOnClickListener(this);
        }
    }
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (radioGroup == mRgmap) {
            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.rb_map:
                    mOnClick = OnClickInteDef.setOnclick(OnClickInteDef.TDTVEC);
                    break;
                case R.id.rb_satellite:
                    mOnClick = OnClickInteDef.setOnclick(OnClickInteDef.TDTIMG);
                    break;
            }
            mCallBackMap.onClick(mOnClick);
        }
    }
    public void setMapdownloadListen(CallBackMap mCallBackMap){
        mapdownloadUtil=   new MapdownloadUtil(mContext).setCallBackMap(mCallBackMap).setAListener(this);
        mapdownloadUtil.Rxhttp();
    }


}
