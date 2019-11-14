package com.wavenet.ding.qpps.activity;


import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dereck.library.base.BaseMvpActivity;
import com.dereck.library.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerView;
import com.dereck.library.utils.ToastUtils;
import com.google.gson.Gson;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.adapter.PhotosAdapter;
import com.wavenet.ding.qpps.adapter.TasklistRecordAdapter;
import com.wavenet.ding.qpps.bean.ListBean;
import com.wavenet.ding.qpps.bean.PhtotIdBean;
import com.wavenet.ding.qpps.bean.TaskBean;
import com.wavenet.ding.qpps.bean.TasklistBean;
import com.wavenet.ding.qpps.interf.CallBackMap;
import com.wavenet.ding.qpps.mvp.p.XJDealDetailsRequestPresenter;
import com.wavenet.ding.qpps.mvp.v.XJActivityRequestView;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.LogUtils;
import com.wavenet.ding.qpps.view.ControllerTaskRefuselistView;
import com.wavenet.ding.qpps.view.ControllerXJRecordSelecetTop;
import com.wavenet.ding.qpps.view.ViewGalleryPhoto;

import org.devio.takephoto.model.TImage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class XJDealDetailsActivity extends BaseMvpActivity<XJActivityRequestView, XJDealDetailsRequestPresenter>
        implements CallBackMap, View.OnClickListener, XJActivityRequestView {
    PullToRefreshRecyclerView wxRecyclerview;
    ListView mLvrecord;
    TasklistRecordAdapter mTradapter;
    ArrayList<TasklistBean.ValueBean> mBeanList = new ArrayList<>();
    int pageIndexlist = 1;

    ControllerXJRecordSelecetTop SelecetTop;
    Map<String, String> filterMap = new HashMap<>();
    TextView mTvtime;
    RecyclerView mRvcamera;
    PhotosAdapter mPhotoAdapter;
    ArrayList<String> imaDatas = new ArrayList<>();
    TextView mTvname, mTvsource, mTvstate, mTvrecordtime, mTvpaitime, mTvjinji, mTvclabig, mTvclasmall, mTvaddr, mTvdetailreport;
    TextView mTvdealtime, mTvuser, mTvdealdetail;
    LinearLayout mLlczdetails, mLlyes, mLlno;
    RecyclerView mRvcameraSB, mRvcameraCZ;
    LinearLayout mLlcameraSB, mLlcameraCZ;
    ControllerTaskRefuselistView mControllerTaskRefuselistView;
    ControllerTaskRefuselistView mControllerTaskTDView;
    PhotosAdapter mPhotoAdapterSb, mPhotoAdapterCZ;
    ArrayList<String> imaDatasSB = new ArrayList<>();
    ArrayList<String> imaDatasCZ = new ArrayList<>();

    ArrayList<String> imaDatasSBphtot = new ArrayList<>();
    ArrayList<String> imaDatasCZphtot = new ArrayList<>();
    ViewGalleryPhoto mViewGalleryPhoto;
    @Override
    public int getLayoutId() {
        return R.layout.activity_xjdealdetails;
    }

    @Override
    public void init() {
        setTitle("处置详情");
        mTvname = findViewById(R.id.tv_name);
        mTvsource = findViewById(R.id.tv_source);
        mTvstate = findViewById(R.id.tv_state);
        mTvrecordtime = findViewById(R.id.tv_recordtime);
        mTvpaitime = findViewById(R.id.tv_paitime);
        mTvdealtime = findViewById(R.id.tv_dealtime);
        mTvuser = findViewById(R.id.tv_user);
        mTvjinji = findViewById(R.id.tv_jinji);
        mTvclabig = findViewById(R.id.tv_clabig);
        mTvclasmall = findViewById(R.id.tv_clasmall);
        mTvaddr = findViewById(R.id.tv_addr);
        mTvdetailreport = findViewById(R.id.tv_detailreport);
        mLlyes = findViewById(R.id.ll_yes);
        mLlno = findViewById(R.id.ll_no);
        mLlczdetails = findViewById(R.id.ll_czdetails);
        mTvdealdetail = findViewById(R.id.tv_dealdetail);

        mViewGalleryPhoto = findViewById(R.id.v_galleryphoto);
        mRvcameraSB = findViewById(R.id.rv_photosb);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvcameraSB.setLayoutManager(linearLayoutManager);
        mPhotoAdapterSb = new PhotosAdapter(this, imaDatasSB);
        mRvcameraSB.setAdapter(mPhotoAdapterSb);
        mPhotoAdapterSb.setOnItemClickListener(new PhotosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (AppTool.openFile(XJDealDetailsActivity.this,position,imaDatasSB)){
                    return;
                }

                int p=imaDatasSB.size()-imaDatasSBphtot.size();
                mViewGalleryPhoto.setData1(position-p,imaDatasSBphtot);

            }
        });

        mRvcameraCZ = findViewById(R.id.rv_photocz);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvcameraCZ.setLayoutManager(linearLayoutManager1);
        mPhotoAdapterCZ = new PhotosAdapter(this, imaDatasCZ);
        mRvcameraCZ.setAdapter(mPhotoAdapterCZ);
        mPhotoAdapterCZ.setOnItemClickListener(new PhotosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (AppTool.openFile(XJDealDetailsActivity.this,position,imaDatasCZ)){
                    return;
                }
                int p=imaDatasCZ.size()-imaDatasCZphtot.size();
                mViewGalleryPhoto.setData1(position-p,imaDatasCZphtot);
            }
        });
        mLlcameraSB = findViewById(R.id.ll_photosb);
        mLlcameraCZ = findViewById(R.id.ll_photocz);
        mLlcameraSB.setVisibility(View.GONE);
        mLlcameraCZ.setVisibility(View.GONE);
        mControllerTaskRefuselistView = findViewById(R.id.c_refuselist);
        mControllerTaskTDView = findViewById(R.id.c_tdlist);

    }

    @Override
    public void requestData() {
        String mS_MANGE_ID = getIntent().getExtras().getString("mS_MANGE_ID");
        if (mS_MANGE_ID != null) {
            presenter.RequestDealDetails(mS_MANGE_ID);
        }

    }

    public void initData(ListBean b) {
        ListBean.PATROLMANAGEMENTBean bv = b.PATROL_MANAGEMENT.get(0);
        mTvname.setText(bv.S_NAME);

        mTvdealtime.setText(AppTool.setTvTime(bv.T_MANGE_TIME));


        if (bv.S_MANGE_FULL != null) {

            mTvuser.setText(AppTool.getNullStr(bv.S_MANGE_FULL));

        } else {

            mTvuser.setText("未知");

        }

        mTvsource.setText(AppTool.getNullStr(bv.S_SOURCE_CN));
        mTvjinji.setText(AppTool.getNullStr(bv.S_EMERGENCY_CN));
        mTvstate.setText(AppTool.getNullStr(bv.S_STATUS_CN));
        mTvclabig.setText(AppTool.getNullStr(bv.S_CATEGORY_CN));
        mTvclasmall.setText(AppTool.getNullStr(bv.S_TYPE_CN));
        mTvaddr.setText(AppTool.getNullStr(bv.S_LOCAL));
        if (AppTool.isNull(bv.S_DESC)) {
            mTvdetailreport.setText("无");
        } else {
            mTvdetailreport.setText(bv.S_DESC);

        }
        if (AppTool.isNull(bv.S_MANGE_REMARK)) {
            mTvdealdetail.setText("无");
        } else {
            mTvdealdetail.setText(bv.S_MANGE_REMARK);

        }
        mTvrecordtime.setText(AppTool.setTvTime(bv.T_IN_DATE));
        mTvpaitime.setText(AppTool.setTvTime(bv.T_MANGE_TIME));
        if ("W1006500004".equals(bv.S_SOURCE)) {
            isShowDeal(true);
        } else {
            isShowDeal(false);
        }
        presenter.RequestReportDetails(bv.S_MANGE_ID);
        if (!AppTool.isNull(bv.IS_SJSB_FJ) && "1".equals(bv.IS_SJSB_FJ) && !AppTool.isNull(bv.S_SJSB_ID)) {
            presenter.RequestFileDetailsPhoto(201, bv.S_SJSB_ID);
        }
        if (!AppTool.isNull(bv.IS_SJCZ_FJ) && "1".equals(bv.IS_SJCZ_FJ) && !AppTool.isNull(bv.S_SJCZ_ID)) {
            presenter.RequestFileDetailsPhoto(202, bv.S_SJCZ_ID);
        }
        if (!AppTool.isNull(bv.IS_TD) && "1".equals(bv.IS_TD)) {
            presenter.RequestTDlist(bv.S_MANGE_ID);
        }
        if (!AppTool.isNull(bv.IS_JJ) && "1".equals(bv.IS_JJ)) {
            presenter.RequestRefuselist(bv.S_MANGE_ID);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }

    }

    //获得照片的输出保存Uri
    private Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }

    @Override
    public void onClick(String mOnClick) {

    }

    public void isShowDeal(boolean isShow) {
//        mLlyes.setVisibility(isShow ? View.VISIBLE : View.GONE);
//        mLlno.setVisibility(isShow ? View.GONE : View.VISIBLE);
        mLlczdetails.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void requestSuccess(int resultid, String result) {
        JSONObject JB = null;
        try {
            JB = new JSONObject(result);
            if (resultid!=201&&resultid!=202&&JB.getInt("Code")!=200){
                ToastUtils.showToast(JB.getString("Msg"));
                return;
            }
        if (!AppTool.isNull(result) && !result.contains("error")) {
            switch (resultid) {
                case 5:
                    ListBean taskldetailBean = new Gson().fromJson(result, ListBean.class);
                    initData(taskldetailBean);
                    break;

                case 1:
                    TasklistBean tasklistBean = new Gson().fromJson(result, TasklistBean.class);
                    if (tasklistBean.value.size() == 0) {
                        isShowDeal(false);
                        return;
                    }
//                    isShowDeal(true);
//                        mTvdealtime.setText(AppTool.setTvTime(tasklistBean.value.get(0).TMANGETIME));
//                    mTvuser.setText(tasklistBean.value.get(0).SMANGEMAN);


                    isShowDeal(true);

                    break;
                case 201:
                    PhtotIdBean mPhtotIdBean = new Gson().fromJson(result, PhtotIdBean.class);
                    if (mPhtotIdBean.app.size() > 0) {
                        mLlcameraSB.setVisibility(View.VISIBLE);
                        imaDatasSB.clear();
                        imaDatasSB.addAll(TaskBean.Builder().setFileUrls(mPhtotIdBean).fileUrls);
                        mPhotoAdapterSb.notifyDataSetChanged();
                        imaDatasSBphtot.clear();
                        for (int i = 0; i <imaDatasSB.size() ; i++) {
                            if (!imaDatasSB.get(i).endsWith("视频") &&!imaDatasSB.get(i).endsWith("语音") ){
                                imaDatasSBphtot.add(imaDatasSB.get(i));
                            }
                        }
                    }

                    break;
                case 202:

                    LogUtils.e("图片数据", result + "..........");
                    PhtotIdBean mPhtotIdBean1 = new Gson().fromJson(result, PhtotIdBean.class);
                    imaDatasCZ.clear();
                    imaDatasCZ.addAll(TaskBean.Builder().setFileUrls1(mPhtotIdBean1).fileUrls);
                    if (mPhtotIdBean1.app.size() > 0) {
                        mLlcameraCZ.setVisibility(View.VISIBLE);
                        mPhotoAdapterCZ.notifyDataSetChanged();
                        imaDatasCZphtot.clear();
                        for (int i = 0; i <imaDatasCZ.size() ; i++) {
                            if (!imaDatasCZ.get(i).endsWith("视频") &&!imaDatasCZ.get(i).endsWith("语音") ){
                                imaDatasCZphtot.add(imaDatasCZ.get(i));
                            }
                        }
                    }
                    break;
                case 3:
                    TasklistBean tlb = new Gson().fromJson(result, TasklistBean.class);
                    mControllerTaskRefuselistView.setdata(tlb);
                    break;
                case 4:
                    TasklistBean tlb1 = new Gson().fromJson(result, TasklistBean.class);
                    mControllerTaskTDView.setdata(tlb1);
                    break;
            }
        } else {
            ToastUtils.showToast(result);
        }
        } catch (JSONException e) {
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
        switch (resultid) {
            case 2:
                ToastUtils.showToast("新任务执行失败，请重新开始执行");
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
    protected XJDealDetailsRequestPresenter createPresenter() {
        return new XJDealDetailsRequestPresenter();
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK){
            if (mViewGalleryPhoto.getVisibility() == View.VISIBLE) {
                mViewGalleryPhoto.setVisibility(View.GONE);
            }else {
                return false;
            }
        }

        return true;
    }

}
