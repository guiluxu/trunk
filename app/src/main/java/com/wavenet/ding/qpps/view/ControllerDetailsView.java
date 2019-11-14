package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.activity.MainMapXJActivity;
import com.wavenet.ding.qpps.activity.XJDealDetailsActivity;
import com.wavenet.ding.qpps.adapter.PhotosAdapter;
import com.wavenet.ding.qpps.bean.Gps;
import com.wavenet.ding.qpps.bean.ListBean;
import com.wavenet.ding.qpps.bean.PhtotIdBean;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.SPUtil;

import org.devio.takephoto.app.TakePhotoActivity;
import org.devio.takephoto.model.InvokeParam;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ControllerDetailsView extends LinearLayout implements View.OnClickListener {
    private static final String TAG = TakePhotoActivity.class.getName();
    Context mContext;
    MainMapXJActivity mActivity;
    TextView mTvtime, mTvuser, mTvclabig, mTvclasmall, mTvclose, mTvdeal, mTvaddr, mTvdetailreport, mTvsource, mTvstate;
    LinearLayout mLlphoto;
    RecyclerView mRvcamera;
    PhotosAdapter mPhotoAdapter;
    ArrayList<String> imaDatas = new ArrayList<>();
    ArrayList<String> imaDatasphoto = new ArrayList<>();
    int isUn;
    //    OkHttpPost mOkHttpPost;
    String mS_MANGE_ID;
    private Uri imageUri;  //图片保存路径
    private InvokeParam invokeParam;

    public ViewGalleryPhoto mViewGalleryPhoto;
    public ControllerDetailsView(Context context) {
        super(context);
        initView(context);
    }

    public ControllerDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ControllerDetailsView(Context context, AttributeSet attrs, int defStyle) {
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
        LayoutInflater.from(mContext).inflate(R.layout.view_details, this);
        if (!isInEditMode()) {
            setOnClickListener(this);
            findViewById(R.id.ll_back).setOnClickListener(this);
            mTvtime = findViewById(R.id.tv_time);
            mTvuser = findViewById(R.id.tv_user);
            mTvclabig = findViewById(R.id.tv_clabig);
            mTvclasmall = findViewById(R.id.tv_clasmall);
            mTvaddr = findViewById(R.id.tv_addr);
            mTvsource = findViewById(R.id.tv_source);
            mTvstate = findViewById(R.id.tv_state);
            findViewById(R.id.tv_close).setOnClickListener(this);
            mTvdetailreport = findViewById(R.id.tv_detailreport);
            mTvdeal = findViewById(R.id.tv_deal);
            mTvdeal.setOnClickListener(this);
            mViewGalleryPhoto = findViewById(R.id.v_galleryphoto);
            mLlphoto = findViewById(R.id.ll_photo);
            mRvcamera = findViewById(R.id.rv_photo);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            linearLayoutManager.setAutoMeasureEnabled(true);mRvcamera.setLayoutManager(linearLayoutManager);
            mPhotoAdapter = new PhotosAdapter(mContext, imaDatas);
            mRvcamera.setAdapter(mPhotoAdapter);

            mPhotoAdapter.setOnItemClickListener(new PhotosAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (AppTool.openFile(mContext,position,imaDatas)){
                        return;
                    }
                   int p=imaDatas.size()-imaDatasphoto.size();
                    mViewGalleryPhoto.setData1(position-p,imaDatasphoto);
                }
            });
            initData();

        }
    }

    private void initData() {
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void EventBusCar(TResult result) {
//        imaDatas.addAll(result.getImages());
//        mCameraAdapter.notifyDataSetChanged();
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                setVisibility(View.GONE);
                break;
            case R.id.tv_close:
                setVisibility(View.GONE);
                break;
            case R.id.tv_deal:
                setVisibility(View.GONE);
                if ("处置".equals(mTvdeal.getText().toString())) {
                    mActivity.mTaskDealView.isNOImmediately(mS_MANGE_ID);
                    mActivity.presenter.clickRequestIsDealDetail2(mS_MANGE_ID);
//                    mActivity.mTaskDealView.showView(false);
                } else {
                    Intent i = new Intent(mContext, XJDealDetailsActivity.class);
                    i.putExtra("mS_MANGE_ID", mS_MANGE_ID);
                    mActivity.startActivity(i);

                }

                break;
        }

    }

    //获得照片的输出保存Uri
    private Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }

    public void setData(ListBean b) {
        if (b == null) {
            return;
        }
        ListBean.PATROLMANAGEMENTBean bv = b.PATROL_MANAGEMENT.get(0);
        setVisibility(VISIBLE);
        mTvuser.setText(SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_MYNAME));
        mTvtime.setText(AppTool.getNullStr(bv.T_IN_DATE));
        mTvsource.setText(AppTool.getNullStr(bv.S_SOURCE_CN));
        mTvstate.setText(AppTool.getNullStr(bv.S_STATUS_CN));
        mTvclabig.setText(AppTool.getNullStr(bv.S_CATEGORY_CN));
        mTvclasmall.setText(AppTool.getNullStr(bv.S_TYPE_CN));
        mTvaddr.setText(AppTool.getNullStr(bv.S_LOCAL));
        if (AppTool.isNull(bv.S_DESC)) {
            mTvdetailreport.setText("无");
        } else {
            mTvdetailreport.setText(bv.S_DESC);

        }
        if (!AppTool.isNull(bv.IS_SJSB_FJ) && "1".equals(bv.IS_SJSB_FJ) && !AppTool.isNull(bv.S_SJSB_ID)) {
            mRvcamera.setVisibility(View.VISIBLE);
            mActivity.presenter.RequestFileDetailsPhoto1(bv.S_SJSB_ID);
        }
        mTvuser.setText(SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_MYNAME));

        MainMapXJActivity.g = new Gps(Double.parseDouble(bv.N_X), Double.parseDouble(bv.N_Y));
        this.mS_MANGE_ID = bv.S_MANGE_ID;
//        mActivity.presenter.RequestISDeal(tb.S_MANGE_ID);
//        if (tb.fileUrls != null && tb.fileUrls.size() > 0) {
//            setPhotonotifyData(tb.fileUrls);
//        } else {
//            mActivity.presenter.RequestReportDetails(mS_MANGE_ID);
//
//
//        }

    }

    public void setPhotonotifyData(List<String> fileUrls) {
        imaDatas.clear();
        imaDatas.addAll(fileUrls);
        imaDatasphoto.clear();
        imaDatasphoto.addAll(fileUrls);
        for (int i = 0; i <imaDatasphoto.size() ; i++) {
            if (!imaDatasphoto.get(i).endsWith("视频") &&!imaDatasphoto.get(i).endsWith("语音") ){
                imaDatasphoto.remove(i);
            }
        }
        if (imaDatas.size() > 0) {
            mLlphoto.setVisibility(VISIBLE);
            mRvcamera.setVisibility(VISIBLE);
            mPhotoAdapter.notifyDataSetChanged();
        } else {
            mLlphoto.setVisibility(GONE);
            mRvcamera.setVisibility(GONE);
        }
    }
    public void setPhotonotifyData(PhtotIdBean mPhtotIdBean) {

    }

    public void IsShowDeal(String str) {
        if ("W1001600000".equals(str)) {
            mTvdeal.setText("处置");
        } else if ("W1001600001".equals(str)) {
            mTvdeal.setText("处置详情");
        }

    }
}
