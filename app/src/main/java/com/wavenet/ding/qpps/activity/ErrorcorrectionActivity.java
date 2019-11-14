package com.wavenet.ding.qpps.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dereck.library.base.BaseMvpActivity;
import com.dereck.library.utils.ToastUtils;
import com.dereck.library.view.LoadingWaitView;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.api.QPSWApplication;
import com.wavenet.ding.qpps.mvp.p.ErrorcorrectionRequestPresenter;
import com.wavenet.ding.qpps.mvp.v.RequestView;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.SPUtil;
import com.wavenet.ding.qpps.view.ControllerCameraView;
import com.wavenet.ding.qpps.view.ViewCameraJiucuo;
import com.wavenet.ding.qpps.view.ViewGalleryPhoto;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.model.TResult;

//管理员版本纠错页面
public class ErrorcorrectionActivity extends BaseMvpActivity<RequestView, ErrorcorrectionRequestPresenter> implements RequestView,View.OnClickListener,ViewGalleryPhoto.NotifyDataSetChangedListen,ViewCameraJiucuo.ShowGalleryPhotoListen,TakePhoto.TakeResultListener{

    private RadioGroup rgTr;
    private RadioButton rb1;
    private RadioButton rb2;
    private ImageView ivLocation;
    private TextView tvAddr;
    private EditText mEtdetails, mEtphone;
    private ControllerCameraView cCamer;
    private TextView tvSuer;
    private ViewCameraJiucuo mCameraView;
    public  ViewGalleryPhoto mViewGalleryPhoto;
    LoadingWaitView mLoadingWaitView;
    public TakePhoto takePhoto;
    String FACSTATE="设施存在";//设施状态
    String FACNAME;//设施名称
    String DEVICEID;//设备编号
    String N_X;
    String N_Y;
    String ADDRESS;//地址
    String REMARK;//详细描述
    String PHONE;//手机
    String RELYID;//照片集合ID(对应下面的附件接口)
    @Override
    public int getLayoutId() {
        return R.layout.activity_errorcorrection;
    }

    @Override
    public void init() {
        setTitle("纠错上报");
        mLoadingWaitView = findViewById(R.id.loadingWaitView);
//        mLoadingWaitView.setVisibility(View.VISIBLE);
        rgTr = (RadioGroup) findViewById(R.id.rg_tr);
        rb1 = (RadioButton) findViewById(R.id.rb_1);
        rb2 = (RadioButton) findViewById(R.id.rb_2);
        ivLocation = (ImageView) findViewById(R.id.iv_location);
        ivLocation.setOnClickListener(this);
        tvAddr = (TextView) findViewById(R.id.tv_addr);
        mEtdetails =  findViewById(R.id.et_details);
        mEtphone = findViewById(R.id.et_phone);
        tvSuer = (TextView) findViewById(R.id.tv_suer);
        tvSuer.setOnClickListener(this);
        mCameraView = findViewById(R.id.c_camer);
        mCameraView.setShowGalleryPhotoListen(this);
        mViewGalleryPhoto = findViewById(R.id.v_galleryphoto);
        mViewGalleryPhoto.setNotifyDataSetChangedListen(this);
    }

    @Override
    public void requestData() {
        takePhoto = getTakePhoto();
        rgTr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = findViewById(i);
                FACSTATE = rb.getText().toString();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_location:
          Intent intent=new Intent(this, ErrorcorrectionmapActivity.class);
        startActivityForResult(intent,100);
                break;
                case R.id.tv_suer:
if (AppTool.isNull(tvAddr.getText().toString()) ||AppTool.isNull(mEtdetails.getText().toString())||AppTool.isNull(mEtphone.getText().toString())){
    ToastUtils.showToast("请把信息填写完整");
    return;
}
                if (!mCameraView.getishavefile()) {
                    ToastUtils.showToast("请选择上报的图片");
                    return;
                }
                    mLoadingWaitView.setVisibility(View.VISIBLE);
String CREATEMAN= SPUtil.getInstance(this).getStringValue(SPUtil.APP_MYNAME);
                    RELYID=System.currentTimeMillis()+"";
                    presenter.requestUPJiucuo(FACSTATE, QPSWApplication.maBean.title,N_X,N_Y,tvAddr.getText().toString(),mEtdetails.getText().toString(),mEtphone.getText().toString(),RELYID,QPSWApplication.maBean.url, CREATEMAN);
                break;
        }
//
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
        switch (requestCode){
            case 100:
                if (resultCode==2){
                    tvAddr.setText(data.getStringExtra("addr"));
                    N_X=data.getStringExtra("point84_X");
                    N_Y=data.getStringExtra("point84_Y");
                }
                break;
        }
    }

    @Override
    protected ErrorcorrectionRequestPresenter createPresenter() {
        return new ErrorcorrectionRequestPresenter() {
        };
    }


    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void requestFailure(int resultid, String result) {
        mLoadingWaitView.setVisibility(View.GONE);
        ToastUtils.showToast(result);
    }
    @Override
    public void requestSuccess(int resultid, String result) {
        mLoadingWaitView.setVisibility(View.GONE);
        if (!AppTool.isNull(result) && !result.contains("error")) {

            switch (resultid) {
                case 1:
                   mCameraView. FileRequest(RELYID);
                    break;
                case 2:
                    ToastUtils.showToast("纠错完成");
//                    ToastUtils.showToast(result);
                    finish();

                    break;
            }
        }else {
                ToastUtils.showToast(result);
            }
    }




}
