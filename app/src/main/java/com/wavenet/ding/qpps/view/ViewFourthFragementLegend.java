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
import com.wavenet.ding.qpps.api.QPSWApplication;
import com.wavenet.ding.qpps.fragment.FourthFragment;
import com.wavenet.ding.qpps.interf.CallBackMap;
import com.wavenet.ding.qpps.interf.OnClickInteDef;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.AppUtils;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.MapdownloadUtil;
import com.wavenet.ding.qpps.utils.SPUtil;


public class ViewFourthFragementLegend extends LinearLayout implements View.OnClickListener, RadioGroup.OnCheckedChangeListener,MapdownloadUtil.A {
    public ImageView mIvjcsw,mIvjcclc,mIvjcbz,mIvbengzhan, mIvguanwangys, mIvguanwangws, mIvjianchajing, mIvwushuichang, mIvpaishuihu, mIvzhongdianpfk, mIvpfk, mIvpeoplexj, mIvpeopleyh;
    public LinearLayout mLljc, mLljcsw,mLljcclc,mLljcbz,mLlbengzhan, mLlguanwangys, mLlguanwangws, mLljianchajing, mLlwushuichang, mLlpaishuihu, mLlzhongdianpfk, mLlpfk,mLlmapdownload,mLlpeople;
    TextView mTvmapdownload1,mTvmapdownload2;
    Context mContext;
    View mView;
//    AdminActivity mAdminActivity;
    RadioGroup mRgmap;
    int sum = 0;//选择图层的个数
    int summax = 1;//可选图层最大极限
    int sum1 = 0;//地图显示可查询的图层个数
    /**
     * 侧滑动画
     */
    boolean isShow = false;//侧滑菜单是否显示隐藏
    CallBackMap mCallBackMap;
    String mOnClick = "TDTVEC";
   public int ImageLayer=0 ;//0代表管网图层，1代表监测图层
    public ViewFourthFragementLegend(Context context) {
        super(context);
        initView(context);
    }

    public ViewFourthFragementLegend(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ViewFourthFragementLegend(Context context, AttributeSet attrs, int defStyle) {
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
            //地图切换
            mRgmap = findViewById(R.id.rg_map);
            mRgmap.setOnCheckedChangeListener(this);

            mLlpeople=findViewById(R.id.ll_people);
            mIvpeoplexj = findViewById(R.id.iv_peoplexj);
            mIvpeoplexj.setOnClickListener(this);
            mIvpeopleyh = findViewById(R.id.iv_peopleyh);
            mIvpeopleyh.setOnClickListener(this);

            mLljc=findViewById(R.id.ll_jiance);
            mIvjcsw = findViewById(R.id.iv_jcsw);
            mIvjcsw.setOnClickListener(this);
            mIvjcclc = findViewById(R.id.iv_jcclc);
            mIvjcclc.setOnClickListener(this);
            mIvjcbz = findViewById(R.id.iv_jcbz);
            mIvjcbz.setOnClickListener(this);
            mLljcsw = findViewById(R.id.ll_jiancesw);
            mLljcsw.setOnClickListener(this);
            mLljcclc = findViewById(R.id.ll_jianceclc);
            mLljcclc.setOnClickListener(this);
            mLljcbz = findViewById(R.id.ll_jiancebz);
            mLljcbz.setOnClickListener(this);

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


        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.v_bg) {
            if (mView.getVisibility() == View.VISIBLE) {
                showOrHide("");
            }
            return;
        } else if (v.getId() == R.id.ll_mapdownload) {
                mapdownloadUtil.startDown();
            return;
        }else if (v.getId() == R.id.iv_peoplexj) {
            if (mFourthFragment.mainMapImageLayer==null){
                ToastUtils.showToast("底图加载失败");
                return;
            }
            if (!mFourthFragment.isOnclickPeopleXJ)
                return;

            mIvpeoplexj.setSelected(!mIvpeoplexj.isSelected());
            FourthFragment.onClicgraphicsOverlayXJ.setVisible(mIvpeoplexj.isSelected());
            if (mIvpeoplexj.isSelected()) {
                mFourthFragment.isOnclickPeopleXJ = false;
//                String filterStr= AppUtils.getPopleFilteXJ(mContext);
//                mAdminActivity.mFourthFragment.presenter.requestPeople(301, filterStr);
                String filterStr = AppUtils.getParamPeople(mContext, mContext.getResources().getString(R.string.xj_people));
                mFourthFragment.presenter.requestPeople2(1008, filterStr);
            }
//            &S_TOWNID=W1007400001
            return;
        } else if (v.getId() == R.id.iv_peopleyh) {
            if (mFourthFragment.mainMapImageLayer==null){
                ToastUtils.showToast("底图加载失败");
                return;
            }
            if (!mFourthFragment.isOnclickPeopleYH)
                return;
            mIvpeopleyh.setSelected(!mIvpeopleyh.isSelected());
            FourthFragment.onClicgraphicsOverlayYH.setVisible(mIvpeopleyh.isSelected());
            if (mIvpeopleyh.isSelected()) {
                mFourthFragment.isOnclickPeopleYH = false;
//                String filterStr= AppUtils.getPopleFilteYH(mContext);
//                mAdminActivity.mFourthFragment.presenter.requestPeople(302, filterStr);
                String filterStr = AppUtils.getParamPeople(mContext, mContext.getResources().getString(R.string.yh_people));
                mFourthFragment.presenter.requestPeople2(1009, filterStr);
            }
            return;
        }
        setmOnClickIv(v);
        setmOnClickLl(v);
    }

    public void changeLayer(int urlid) {//根据图层ID判断打开哪个图层
        if (mFourthFragment.mainMapImageLayer==null){
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
        mFourthFragment.initStyleSingleTap();
        colseAllSeachLayer();//查询UI全部关闭
        Ll.setSelected(true);
        iv.setSelected(false);
        setmOnClickIv(iv);//图层控制

    }

    public void setmOnClickIv(View v) {
        switch (v.getId()) {
            case R.id.v_bg:
                if (mView.getVisibility() == View.VISIBLE) {
                    showOrHide("");
                }
                break;
            case R.id.iv_jcsw:
                setShowLayerJc(v,  AppConfig.IndexJCsw);

                break;
            case R.id.iv_jcclc:
                setShowLayerJc(v,  AppConfig.IndexJCclc);

                break;
            case R.id.iv_jcbz:
                setShowLayerJc(v,  AppConfig.IndexJCbz);

                break;
            case R.id.iv_bengzhan:
                setShowLayer(v,  AppConfig.IndexPSBZ);

                break;
            case R.id.iv_guanwangys:
                setShowLayer(v,  AppConfig.IndexPSGD);

                break;
            case R.id.iv_guanwangws:
                setShowLayer(v, AppConfig.IndexPSGDWS);//污水地图图层2下标为2，

                break;
            case R.id.iv_wushuichang:
                setShowLayer(v, AppConfig.IndexWSCLC);

                break;
            case R.id.iv_paishuihu:
                setShowLayer(v, AppConfig.IndexPSH);

                break;
            case R.id.iv_zhongdianpfk:
                setShowLayer(v, AppConfig.IndexZDPFK);

                break;
            case R.id.iv_pfk:
                setShowLayer(v, AppConfig.IndexPFK);
                break;
        }
    }
    public void setmOnClickLl(View v) {
        if (mFourthFragment.mainMapImageLayer==null){
            ToastUtils.showToast("底图加载失败");
            return;
        }
        ImageLayer=0;
        switch (v.getId()) {
            case R.id.ll_jiancesw:
                setSeachLayerJc(v, mIvjcsw,AppConfig.IndexJCsw);
                break;
            case R.id.ll_jianceclc:
                setSeachLayerJc(v, mIvjcclc,AppConfig.IndexJCclc);

                break;
            case R.id.ll_jiancebz:
                setSeachLayerJc(v, mIvjcbz,AppConfig.IndexJCbz);

                break;
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
        if (mFourthFragment.mainMapImageLayer==null){
            ToastUtils.showToast("底图加载失败");
            return;
        }
           if (mFourthFragment.mainMapImageLayer.getLoadStatus() != LoadStatus.LOADED){
            ToastUtils.showToast("图层加载失败");
            return;
        }
       if (!iv.isSelected()){
           iv.setSelected(true);
           isShowLayer(iv.isSelected(),index);
       }
        colseAllSeachLayer();
        mFourthFragment.initStyleSingleTap();
        v.setSelected(true);
    }  public void setSeachLayerJc(View v,View iv, int index) {
        if (mFourthFragment.mainMapImageLayerjc==null){
            ToastUtils.showToast("底图加载失败");
            return;
        } if (mFourthFragment.mainMapImageLayerjc.getLoadStatus() != LoadStatus.LOADED){
            ToastUtils.showToast("图层加载失败");
            return;
        }
        ImageLayer=1;
       if (!iv.isSelected()){
           iv.setSelected(true);
           isShowLayerJc(iv.isSelected(),index);
       }
        colseAllSeachLayer();
        mFourthFragment.initStyleSingleTap();
        v.setSelected(true);
    }

    public void setShowLayer(View v, int index) {
        if (mFourthFragment.mainMapImageLayer==null||mFourthFragment.mainMapImageLayer.getLoadStatus() != LoadStatus.LOADED){
            ToastUtils.showToast("底图加载失败");
            return;
        }
        //打开单个图层
//        colseAllLayer();
        //打开多个图层
//        if (!iv.isSelected()) {
//            if (sum ==summax) {
//                ToastUtils.showToast("最多选择一个图层");
//                return;
//            }
//            sum++;
//
//        } else {
//            sum--;
//        }
//        if (isSeachChangeLayer) {
//            iv.setSelected(true);
//        } else {
//            iv.setSelected(!temIsSelete);
//        }
        v.setSelected(!v.isSelected());
//        showToSeach(v);
        isShowLayer(v.isSelected(), index);
    }public void setShowLayerJc(View v, int index) {
        if (mFourthFragment.mainMapImageLayerjc==null||mFourthFragment.mainMapImageLayerjc.getLoadStatus() != LoadStatus.LOADED){
            ToastUtils.showToast("底图加载失败");
            return;
        }
        v.setSelected(!v.isSelected());
        isShowLayerJc(v.isSelected(), index);
    }
String des="";
    public void isShowLayer(Boolean isSelete, int index) {
        if (mFourthFragment.mainMapImageLayer==null){
            ToastUtils.showToast("底图加载失败");
            return;
        }
        des=  SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_DES);
        if (index== 2001 ){//雨水管网
            //管
            ArcGISMapImageSublayer mImageSublayerG= (ArcGISMapImageSublayer) mFourthFragment.mainMapImageLayer.getSubLayerContents().get(2).getSubLayerContents().get(0);
            mImageSublayerG.setVisible(isSelete);
            loopImageSublayer(mImageSublayerG,isSelete);
            //井
            ArcGISMapImageSublayer mImageSublayerJ= (ArcGISMapImageSublayer) mFourthFragment.mainMapImageLayer.getSubLayerContents().get(3).getSubLayerContents().get(0);
            mImageSublayerJ.setVisible(isSelete);
            loopImageSublayer(mImageSublayerJ,isSelete);
        }else  if (index == 2002 ){//污水管网
            //管
            ArcGISMapImageSublayer mImageSublayerG= (ArcGISMapImageSublayer) mFourthFragment.mainMapImageLayer.getSubLayerContents().get(2).getSubLayerContents().get(1);
            mImageSublayerG.setVisible(isSelete);
            loopImageSublayer(mImageSublayerG,isSelete);
            //井
            ArcGISMapImageSublayer mImageSublayerJ= (ArcGISMapImageSublayer) mFourthFragment.mainMapImageLayer.getSubLayerContents().get(3).getSubLayerContents().get(1  );
            mImageSublayerJ.setVisible(isSelete);
            loopImageSublayer(mImageSublayerJ,isSelete);
        }else {//其他图层（单个图层架构）
            ArcGISMapImageSublayer mImageSublayer= (ArcGISMapImageSublayer) mFourthFragment.mainMapImageLayer.getSubLayerContents().get(index);
            loopImageSublayer(mImageSublayer,isSelete);
        }
    } public void isShowLayerJc(Boolean isSelete, int index) {
        if (mFourthFragment.mainMapImageLayerjc==null){
            ToastUtils.showToast("底图加载失败");
            return;
        }
        des=  SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_DES);
        if (index == 3 ){//泵站
            //泵站
            ArcGISMapImageSublayer mImageSublayerbz= (ArcGISMapImageSublayer) mFourthFragment.mainMapImageLayerjc.getSubLayerContents().get(index);
            mImageSublayerbz.setVisible(isSelete);
            //污水泵站
            ArcGISMapImageSublayer mImageSublayerbzws= (ArcGISMapImageSublayer)mImageSublayerbz.getSubLayerContents().get(1);
            mImageSublayerbzws.setVisible(isSelete);
        }else {//其他图层（单个图层架构）
            ArcGISMapImageSublayer mImageSublayer= (ArcGISMapImageSublayer) mFourthFragment.mainMapImageLayerjc.getSubLayerContents().get(index);
            loopImageSublayerJc(mImageSublayer,isSelete);
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
                if (!AppTool.isNull(des)){
//                    S_town in ('夏阳街道','盈浦街道')

                    mISublayer.setDefinitionExpression("S_town " + MapUtil.getDes(des));

//                mISublayer.setDefinitionExpression("S_town in ('夏阳街道') ");
                }
             mISublayer.setVisible(b);
            }
}public void loopImageSublayerJc(ArcGISMapImageSublayer mISublayer,Boolean b){
    int size=mISublayer.getSubLayerContents().size();
            if(size>0){
                for (int i = 0; i <size ; i++) {
                    ArcGISMapImageSublayer mIs= (ArcGISMapImageSublayer) mISublayer.getSubLayerContents().get(i);
                    loopImageSublayer(mIs,b);
                }
            }else {
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

    public void initShowLayerchangejc() {
        if (mIvjcsw.isSelected()) {
            isShowLayerJc(mIvjcsw.isSelected(), AppConfig.IndexJCsw);
        }  if (mIvjcclc.isSelected()) {
            isShowLayerJc(mIvjcclc.isSelected(), AppConfig.IndexJCclc);
        }  if (mIvjcbz.isSelected()) {
            isShowLayerJc(mIvjcbz.isSelected(), AppConfig.IndexJCbz);
        }
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
        }   if (mLljcsw.isSelected()) {
            isshow=mIvjcsw.isSelected();
            mLljcsw.setSelected(false);
            mIvjcsw.setSelected(isshow);
        }   if (mLljcclc.isSelected()) {
            isshow=mIvjcclc.isSelected();
            mLljcclc.setSelected(false);
            mIvjcclc.setSelected(isshow);
        }   if (mLljcbz.isSelected()) {
            isshow=mIvjcbz.isSelected();
            mLljcbz.setSelected(false);
            mIvjcbz.setSelected(isshow);
        }
    }
public void isShowlayer(ArcGISMapImageLayer layer,boolean ishow){
    for (int i = 0; i <layer.getSublayers().size() ; i++) {
        layer.getSublayers().get(i).setVisible(ishow);
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
        getSum(mLljcsw);
        getSum(mLljcclc);
        getSum(mLljcbz);
        return sum1;
    }

    public int getSum(View v) {
        if (v.isSelected()) {
            sum1++;
        }
        return sum1;
    }
    FourthFragment mFourthFragment;
public void setFragment(FourthFragment mFourthFragment){
    this.mFourthFragment=mFourthFragment;

    if ("一般用户".equals(mFourthFragment.role)){
        mLlpeople.setVisibility(GONE);
    }
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
        if (mFourthFragment.mainMapImageLayer==null){
            ToastUtils.showToast("底图加载失败");
            return;
        }
        if (mFourthFragment.mainMapImageLayer!=null&&mFourthFragment.mainMapImageLayer.getLoadStatus() != LoadStatus.LOADED) {
            ToastUtils.showToast("图层加载失败");
            return;
        }
        if (radioGroup == mRgmap) {
            QPSWApplication.CenterpointPolygon = mFourthFragment.mMapView.getVisibleArea();
            AppTool.getScreenCenterPoint_84(mFourthFragment.getActivity(),mFourthFragment.mMapView);
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
    MapdownloadUtil mapdownloadUtil;
    public void setMapdownloadListen(CallBackMap mCallBackMap){
        mapdownloadUtil=   new MapdownloadUtil(mContext).setCallBackMap(mCallBackMap).setAListener(this);
        mapdownloadUtil.Rxhttp();
    }

}
