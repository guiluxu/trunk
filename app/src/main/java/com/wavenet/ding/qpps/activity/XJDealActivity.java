package com.wavenet.ding.qpps.activity;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dereck.library.base.BaseMvpActivity;
import com.dereck.library.utils.ToastUtils;
import com.google.gson.Gson;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.adapter.PhotosAdapter;
import com.wavenet.ding.qpps.bean.Gps;
import com.wavenet.ding.qpps.bean.ListBean;
import com.wavenet.ding.qpps.bean.PhtotIdBean;
import com.wavenet.ding.qpps.bean.TaskBean;
import com.wavenet.ding.qpps.interf.CallBackMap;
import com.wavenet.ding.qpps.mvp.p.XJDealRequestPresenter;
import com.wavenet.ding.qpps.mvp.v.XJActivityRequestView;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.MaxTextLengthFilter;
import com.wavenet.ding.qpps.utils.SPUtil;
import com.wavenet.ding.qpps.view.ViewCameradeal;
import com.wavenet.ding.qpps.view.ViewGalleryPhoto;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * 从历史记录里面做处置
*/
public class XJDealActivity extends BaseMvpActivity<XJActivityRequestView, XJDealRequestPresenter>
        implements CallBackMap, View.OnClickListener, XJActivityRequestView ,ViewCameradeal.ShowGalleryPhotoListen,ViewGalleryPhoto.NotifyDataSetChangedListen, TakePhoto.TakeResultListener{
   public static int dealSuccess=100;
    public String UTCTime = "T";
  ViewCameradeal mCameraView;
    public TakePhoto takePhoto;
    public ViewGalleryPhoto mViewGalleryPhoto;
    public String mS_MANGE_ID;
    TextView mTvname, mTvsource, mTvstate, mTvrecordtime, mTvpaitime, mTvjinji, mTvclabig, mTvclasmall, mTvaddr, mTvdetailreport;
    TextView mTvdealtime, mTvuser, mTvdealdetail;
    RecyclerView mRvcameraSB;
    LinearLayout mLlcameraSB;
    RelativeLayout mRljinji, mRlname;
    PhotosAdapter mPhotoAdapterSb;
    ArrayList<String> imaDatasSB = new ArrayList<>();
    EditText mEtcontentdeal;
    ArrayList<String> imaDatasphoto = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.view_taskingdealweb;
    }

    @Override
    public void init() {
        findViewById(R.id.ll_back).setOnClickListener(this);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.tv_suer).setOnClickListener(this);
        takePhoto = getTakePhoto();
        mCameraView = findViewById(R.id.c_camer);
        mCameraView.setShowGalleryPhotoListen(this);
        mTvuser = findViewById(R.id.tv_user);
        mTvuser.setText(SPUtil.getInstance(this).getStringValue(SPUtil.APP_MYNAME));
        mTvdealtime = findViewById(R.id.tv_dealtime);
        UTCTime = AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS);
        mTvdealtime.setText(AppTool.getCurrentDate(AppTool.FORMAT_YMDHM));
        mTvname = findViewById(R.id.tv_name);
        mTvsource = findViewById(R.id.tv_source);
        mTvstate = findViewById(R.id.tv_state);
        mTvjinji = findViewById(R.id.tv_jinji);
        mTvclabig = findViewById(R.id.tv_clabig);
        mTvclasmall = findViewById(R.id.tv_clasmall);
        mTvaddr = findViewById(R.id.tv_addr);
        mTvrecordtime = findViewById(R.id.tv_recordtime);
        mTvpaitime = findViewById(R.id.tv_paitime);
        mTvdetailreport = findViewById(R.id.tv_detailreport);
        mRvcameraSB = findViewById(R.id.rv_photosb);
        mEtcontentdeal = findViewById(R.id.et_contentdeal);
        mEtcontentdeal.setFilters(new InputFilter[]{new MaxTextLengthFilter(66)});
        mRljinji = findViewById(R.id.rl_jinji);
        mRlname = findViewById(R.id.rl_name);
        mEtcontentdeal = findViewById(R.id.et_contentdeal);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvcameraSB.setLayoutManager(linearLayoutManager);
        mPhotoAdapterSb = new PhotosAdapter(this, imaDatasSB);
        mRvcameraSB.setAdapter(mPhotoAdapterSb);
        mPhotoAdapterSb.setOnItemClickListener(new PhotosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (AppTool.openFile(XJDealActivity.this,position,imaDatasSB)){
                    return;
                }
                int p=imaDatasSB.size()-imaDatasphoto.size();
                mViewGalleryPhoto.setData1(position,imaDatasSB);
            }
        });
    mLlcameraSB = findViewById(R.id.ll_photosb);
        mLlcameraSB.setVisibility(GONE);
//        mControllerTaskRefuselistView = findViewById(R.id.c_refuselist);

    mViewGalleryPhoto = findViewById(R.id.v_galleryphoto);
        mViewGalleryPhoto.setNotifyDataSetChangedListen(this);

    }

    @Override
    public void requestData() {
        mS_MANGE_ID=getIntent().getStringExtra("S_MANGE_ID");
//        S_MANGE_ID="S_MANGE_ID";
        presenter.clickRequestIsDealDetail(mS_MANGE_ID);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
               finish();
                break;
            case R.id.tv_cancel:
              finish();
                break;
            case R.id.tv_suer:
                if (!mCameraView.getishavefile()) {
                    ToastUtils.showToast("请选择上报的图片");
                    return;
                }
                MainMapXJActivity.g=new Gps(Double.parseDouble(bv.N_Y),Double.parseDouble(bv.N_X));
                EventBus.getDefault().post(MapUtil.czdw);

                //图片上传参数用到这个坐标
                String user = SPUtil.getInstance(this).getStringValue(SPUtil.USERNO);
                presenter.clickTaskDeal( this,mS_MANGE_ID, user, UTCTime, mEtcontentdeal.getText().toString());
                break;
        }
    }

    @Override
    public void onClick(String mOnClick) {

    }
    @Override
    public void takeSuccess(TResult result) {
        mCameraView.notifyData(result);
    }
    @Override
    public void takeFail(TResult result, String msg) {
    }

    @Override
    public void takeCancel() {
    }

    @Override
    public void notifyDataSetChangedListen(int p) {
        mCameraView.notifyData(p);
    }

    @Override
    public void ShowGalleryPhotoListen(boolean isShow, int position) {
        mViewGalleryPhoto.setData(position,mCameraView.imaDatas);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCameraView.onActivityResult(requestCode,resultCode,data);

    }
    ListBean.PATROLMANAGEMENTBean bv;
    public void initDatadaily(ListBean b) {
        ListBean.PATROLMANAGEMENTBean bv = b.PATROL_MANAGEMENT.get(0);
        this.bv=bv;
        mTvuser.setText(SPUtil.getInstance(this).getStringValue(SPUtil.APP_MYNAME));
        UTCTime = AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS);
        mTvdealtime.setText(AppTool.getCurrentDate(AppTool.FORMAT_YMDHM));
//        mTvname.setText(AppTool.getNullStr(bv.S_NAME));
        mRlname.setVisibility(GONE);
        mTvsource.setText(AppTool.getNullStr(bv.S_SOURCE_CN));
        mTvstate.setText(AppTool.getNullStr(bv.S_STATUS_CN));
        mTvrecordtime.setText(AppTool.setTvTime(bv.T_IN_DATE));// TODO 时间要处理
//       mTvpaitime.setText(AppTool.setTvTime(bv.T_IN_DATE));
//        mTvjinji.setText(AppTool.getNullStr(bv.S_EMERGENCY_CN));
        mRljinji.setVisibility(GONE);
        mTvclabig.setText(AppTool.getNullStr(bv.S_CATEGORY_CN));
        mTvclasmall.setText(AppTool.getNullStr(bv.S_TYPE_CN));
        mTvaddr.setText(AppTool.getNullStr(bv.S_LOCAL));
        if (AppTool.isNull(bv.S_DESC)) {
            mTvdetailreport.setText("无");
        } else {
            mTvdetailreport.setText(bv.S_DESC);

        }
        mLlcameraSB.setVisibility(GONE);
        if (!AppTool.isNull(bv.IS_SJSB_FJ) && "1".equals(bv.IS_SJSB_FJ) && !AppTool.isNull(bv.S_SJSB_ID)) {
            mLlcameraSB.setVisibility(View.VISIBLE);
            presenter.RequestFileDetailsPhoto(bv.S_SJSB_ID);
        }

//        mActivity.presenter.RequestRefuselist(bv.S_MANGE_ID);//退单、拒绝列表信息
    }
    public void isNOImmediately(String mS_MANGE_ID) {// TODO 事件id
        this.mS_MANGE_ID = mS_MANGE_ID;
    }
    public void FileRequest(String f) {// TODO 上报文件
        mCameraView.FileRequest(f);
    }
    public void setDate(PhtotIdBean mPhtotIdBean) {
        mLlcameraSB.setVisibility(View.VISIBLE);
        imaDatasSB.clear();
        imaDatasSB.addAll(TaskBean.Builder().setFileUrls(mPhtotIdBean).fileUrls);
        mPhotoAdapterSb.notifyDataSetChanged();
        imaDatasphoto.clear();
        imaDatasphoto.addAll(TaskBean.Builder().setFileUrls(mPhtotIdBean).fileUrls);
        for (int i = 0; i <imaDatasphoto.size() ; i++) {
            if (!imaDatasphoto.get(i).endsWith("视频") &&!imaDatasphoto.get(i).endsWith("语音") ){
                imaDatasphoto.remove(i);
            }
        }
    }


    @Override
    public void requestSuccess(int resultid, String result) {
        try {

        if (!AppTool.isNull(result) && !result.contains("error")) {
            switch (resultid) {
                case 1://事件上报详情

                    JSONObject   JB = new JSONObject(result);

                    if (JB.getInt("Code")!=200){
                        ToastUtils.showToast(JB.getString("Msg"));
                        return;
                    }
                        ListBean taskldetailBean = new Gson().fromJson(result, ListBean.class);
                        if (taskldetailBean==null||taskldetailBean.PATROL_MANAGEMENT==null||taskldetailBean.PATROL_MANAGEMENT.size()==0){
                            ToastUtils.showToast("数据为空");
                            return;
                        }
                        initDatadaily(taskldetailBean);

                    break;
                case 17://事件上报的图片信息
                    PhtotIdBean mPhtotIdBean3 = new Gson().fromJson(result, PhtotIdBean.class);
                    if (mPhtotIdBean3.app.size() > 0) {
                       setDate(mPhtotIdBean3);
                    }
                    break;
                    case 2://处置
                        JSONObject   JB1 = new JSONObject(result);

                        if (JB1.getInt("Code")!=200){
                            ToastUtils.showToast(JB1.getString("Msg"));
                            return;
                        }
                        mCameraView.FileRequest(MapUtil.FTD);
                    break;
                case 61://日常处置的文件上传成功 String FTD
//                    if (Task==1){
//presenter.RequestEndTaskpai1(this,STASKIDcurrent);
//                    }else {
                cancelDialog();
                ToastUtils.showToast("处置成功");
                setResult(dealSuccess);
                finish();//返回要重新刷新前一页H5
//                    presenter.clickRequestTasklistsum(SPUtil.getInstance(this).getStringValue(SPUtil.USERNO), "W1006500002");
//                    }
                    break;


            }
        } else {
            ToastUtils.showToast(result);
        }} catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void requestFailure(int resultid, String result) {
//        mLoadingWaitView.setVisibility(View.GONE);
        ToastUtils.showToast(result);
        switch (resultid) {
            case 1:
                break;
                case 2:

                break;
        }
    }

    @Override
    public void requestSuccess(int resultid, String result, boolean b) {

    }

    @Override
    public void requestFailure(int resultid, String result, boolean b) {

    }

    @Override
    public void requestFailure(int resultid, String result, Map<String, Object> map, ArrayList<TImage> images, String videoPath, String audioPath) {

    }

    @Override
    protected XJDealRequestPresenter createPresenter() {
        return new XJDealRequestPresenter();
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK){
            if (mViewGalleryPhoto.getVisibility() == VISIBLE) {
                mViewGalleryPhoto.setVisibility(GONE);
            }else {
                setResult(dealSuccess);
                finish();//返回要重新刷新前一页H5
                return false;
            }
        }

        return true;
    }


}
