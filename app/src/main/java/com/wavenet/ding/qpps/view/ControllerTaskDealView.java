package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dereck.library.utils.ToastUtils;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.activity.MainMapXJActivity;
import com.wavenet.ding.qpps.adapter.PhotosAdapter;
import com.wavenet.ding.qpps.bean.ListBean;
import com.wavenet.ding.qpps.bean.PhtotIdBean;
import com.wavenet.ding.qpps.bean.TaskBean;
import com.wavenet.ding.qpps.bean.TasklistBean;
import com.wavenet.ding.qpps.utils.AppAttribute;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.MaxTextLengthFilter;
import com.wavenet.ding.qpps.utils.SPUtil;

import org.devio.takephoto.model.TResult;

import java.util.ArrayList;


public class ControllerTaskDealView extends LinearLayout implements View.OnClickListener  ,    ControllerCameraView.ShowGalleryPhotoListen,ViewGalleryPhoto.NotifyDataSetChangedListen{
    public boolean isImmediately = true;
    public String UTCTime = "T";
    Context mContext;
    MainMapXJActivity mActivity;
    ControllerCameraView mCameraView;

   public ViewGalleryPhoto mViewGalleryPhoto;
    String mS_MANGE_ID;
    TextView mTvname, mTvsource, mTvstate, mTvrecordtime, mTvpaitime, mTvjinji, mTvclabig, mTvclasmall, mTvaddr, mTvdetailreport;
    TextView mTvdealtime, mTvuser, mTvdealdetail;
    LinearLayout mLlczdetails, mLlyes, mLlno;
    RecyclerView mRvcameraSB;
    LinearLayout mLlcameraSB;
    RelativeLayout mRljinji, mRlname;
    //    ControllerTaskRefuselistView mControllerTaskRefuselistView;
    PhotosAdapter mPhotoAdapterSb;
    ArrayList<String> imaDatasSB = new ArrayList<>();
    EditText mEtcontentdeal;

    public ControllerTaskDealView(Context context) {
        super(context);
        initView(context);
    }

    public ControllerTaskDealView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ControllerTaskDealView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        mActivity = (MainMapXJActivity) context;
        setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT));
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(mContext).inflate(R.layout.view_taskingdeal, this);
        if (!isInEditMode()) {
            setOnClickListener(this);
            findViewById(R.id.ll_back).setOnClickListener(this);
            findViewById(R.id.tv_cancel).setOnClickListener(this);
            findViewById(R.id.tv_suer).setOnClickListener(this);
            mCameraView = findViewById(R.id.c_camer);
            mCameraView.setShowGalleryPhotoListen(this);
            mTvuser = findViewById(R.id.tv_user);
            mTvuser.setText(SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_MYNAME));
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
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRvcameraSB.setLayoutManager(linearLayoutManager);
            mPhotoAdapterSb = new PhotosAdapter(mContext, imaDatasSB);
            mRvcameraSB.setAdapter(mPhotoAdapterSb);
            mPhotoAdapterSb.setOnItemClickListener(new PhotosAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (AppTool.openFile(mContext,position,imaDatasSB)){
                        return;
                    }
                    int p=imaDatasSB.size()-imaDatasphoto.size();
                    mViewGalleryPhoto.setData1(position,imaDatasSB);
                }
            });
        }
        mLlcameraSB = findViewById(R.id.ll_photosb);
        mLlcameraSB.setVisibility(View.GONE);
//        mControllerTaskRefuselistView = findViewById(R.id.c_refuselist);

        mViewGalleryPhoto = findViewById(R.id.v_galleryphoto);
        mViewGalleryPhoto.setNotifyDataSetChangedListen(this);
    }

    public void initData(TasklistBean.ValueBean bv) {
        setVisibility(VISIBLE);
        mTvuser.setText(SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_MYNAME));

        UTCTime = AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS);
        mTvdealtime.setText(AppTool.getCurrentDate(AppTool.FORMAT_YMDHM));
        mRlname.setVisibility(VISIBLE);
        mTvname.setText(AppTool.getNullStr(bv.S_NAME));
        mTvsource.setText(AppTool.getNullStr(bv.S_SOURCE_CN));
        mTvstate.setText(AppTool.getNullStr(bv.S_STATUS_CN));
        mTvrecordtime.setText(AppTool.setTvTime(bv.T_IN_DATE));// TODO 时间要处理
//       mTvpaitime.setText(AppTool.setTvTime(bv.T_IN_DATE));
        mRljinji.setVisibility(VISIBLE);
        mTvjinji.setText(AppTool.getNullStr(bv.S_EMERGENCY_CN));
        mTvclabig.setText(AppTool.getNullStr(bv.S_CATEGORY_CN));
        mTvclasmall.setText(AppTool.getNullStr(bv.S_TYPE_CN));
        mTvaddr.setText(AppTool.getNullStr(bv.S_LOCAL));
        if (AppTool.isNull(bv.S_DESC)) {
            mTvdetailreport.setText("无");
        } else {
            mTvdetailreport.setText(bv.S_DESC);

        }
        mLlcameraSB.setVisibility(View.GONE);
        if (!AppTool.isNull(bv.IS_SJSB_FJ) && "1".equals(bv.IS_SJSB_FJ) && !AppTool.isNull(bv.S_SJSB_ID)) {
            mLlcameraSB.setVisibility(View.VISIBLE);
            mActivity.presenter.RequestFileDetailsPhoto(bv.S_SJSB_ID);
        }

//        mActivity.presenter.RequestRefuselist(bv.S_MANGE_ID);//退单、拒绝列表信息
    }

    public void initDatadaily(ListBean b) {
        if (b == null) {
            return;
        }
        ListBean.PATROLMANAGEMENTBean bv = b.PATROL_MANAGEMENT.get(0);
        setVisibility(VISIBLE);
        mTvuser.setText(SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_MYNAME));
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
        mLlcameraSB.setVisibility(View.GONE);
        if (!AppTool.isNull(bv.IS_SJSB_FJ) && "1".equals(bv.IS_SJSB_FJ) && !AppTool.isNull(bv.S_SJSB_ID)) {
            mLlcameraSB.setVisibility(View.VISIBLE);
            mActivity.presenter.RequestFileDetailsPhoto(bv.S_SJSB_ID);
        }

//        mActivity.presenter.RequestRefuselist(bv.S_MANGE_ID);//退单、拒绝列表信息
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                setVisibility(View.GONE);
                break;
            case R.id.tv_cancel:
                cleanData();
                setVisibility(View.GONE);
//                EventBus.getDefault().post(MapUtil.TLH);
                break;
            case R.id.tv_suer:
                if (!mCameraView.getishavefile()) {
                    ToastUtils.showToast("请选择上报的图片");
                    return;
                }
                String user = SPUtil.getInstance(mContext).getStringValue(SPUtil.USERNO);
                //

                if (MainMapXJActivity.Task == 0) {

                    if (isImmediately) {
                        mActivity.presenter.clickTaskDeal( mContext, MainMapXJActivity.S_MANGE_ID, user, UTCTime, mEtcontentdeal.getText().toString());
                    } else {
                        mActivity.presenter.clickTaskDeal( mContext, mS_MANGE_ID, user, UTCTime, mEtcontentdeal.getText().toString());
                    }
                } else {

//                    if ("W1007500004".equals(mActivity.mtvBean.S_SOURCE)) {
//                        mActivity.presenter.clickTaskDeal1(mContext, mActivity.mtvBean.S_MANGE_ID, user, UTCTime, MainMapXJActivity.S_RECODE_ID, mEtcontentdeal.getText().toString());
//                    } else {
//                        MainMapXJActivity.S_MANGE_ID = System.currentTimeMillis() + user;

                    mActivity.presenter.clickTaskDeal2(AppTool.getDoubleAccurate(MainMapXJActivity.Distance), mContext, MainMapXJActivity.S_MANGE_ID, user, UTCTime, AppAttribute.F.getXJID(mContext), mEtcontentdeal.getText().toString());
//                    }

                }
                break;
        }
    }
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        mCameraView.onActivityResult(requestCode,resultCode,data);
    }
    public void cleanData() {
        mCameraView.ImaDatasclear();
        UTCTime = AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS);
        mTvdealtime.setText("");
        mTvuser.setText("");
        mTvname.setText("");
        mTvsource.setText("");
        mTvstate.setText("");
        mTvjinji.setText("");
        mTvclabig.setText("");
        mTvclasmall.setText("");
        mTvaddr.setText("");
        mTvrecordtime.setText("");
        mTvpaitime.setText("");
        mTvdetailreport.setText("");
        mEtcontentdeal.setText("");
        imaDatasSB.clear();
        mPhotoAdapterSb.notifyDataSetChanged();
        mRvcameraSB.setAdapter(mPhotoAdapterSb);
        mLlcameraSB.setVisibility(View.GONE);

    }

    public void isNOImmediately(String mS_MANGE_ID) {
        isImmediately = false;
        this.mS_MANGE_ID = mS_MANGE_ID;
    }

    public void showView(Boolean b) {
        isImmediately = b;//true 上报完成直接处置 ， false 点击地图marker进入进步详情后点击处置
        setVisibility(VISIBLE);
        cleanData();
    }

    public void FileRequest(String f) {
        mCameraView.FileRequest(f);
    }

    public void notifyData(TResult result) {
        mCameraView.notifyData(result);
    }
    ArrayList<String> imaDatasphoto = new ArrayList<>();
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

    public void setTaskRefuselistView(TasklistBean tlb) {
//        mControllerTaskRefuselistView.setdata(tlb);
    }

    @Override
    public void notifyDataSetChangedListen(int p) {
        mCameraView.notifyData(p);
    }

    @Override
    public void ShowGalleryPhotoListen(boolean isShow, int position) {
        mViewGalleryPhoto.setData(position,mCameraView.imaDatas);
    }
}
