package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dereck.library.utils.ToastUtils;
import com.guoqi.actionsheet.ActionSheet;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.activity.ErrorcorrectionActivity;
import com.wavenet.ding.qpps.adapter.CameraAdapter;
import com.wavenet.ding.qpps.adapter.PhotoGvAdapter;
import com.wavenet.ding.qpps.bean.PhotoBean;
import com.wavenet.ding.qpps.interf.GoBackInteDef;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.MapUtil;

import org.devio.takephoto.app.TakePhotoActivity;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ViewCameraJiucuo extends LinearLayout implements View.OnClickListener, ActionSheet.OnActionSheetSelected,GoBackInteDef.GoBack {
    private static final String TAG = TakePhotoActivity.class.getName();
    Context mContext;
    ErrorcorrectionActivity mActivity;
    RecyclerView mRvcamera;
    public CameraAdapter mCameraAdapter;
    TextView mTvphotosum;
    public ArrayList<TImage> imaDatas = new ArrayList<>();
    ImageView mIvphoto;
    int isUn;
    //    OkHttpPost mOkHttpPost;
    int a = 0;
    private Uri imageUri;  //图片保存路径
    private InvokeParam invokeParam;
    private CompressConfig compressConfig;  //压缩参数
    private GridView gvphoto;
    private PhotoGvAdapter photo_gvadapter;
    public ViewCameraJiucuo(Context context) {
        super(context);
        initView(context);
    }

    public ViewCameraJiucuo(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    public ViewCameraJiucuo(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        mActivity = (ErrorcorrectionActivity) mContext;
        setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT));
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(mContext).inflate(R.layout.view_camerajiucuo, this);
        if (!isInEditMode()) {
            isUn = AppTool.isRegistered(true, this, isUn);
            mTvphotosum = findViewById(R.id.tv_photosum);
            mRvcamera = findViewById(R.id.rv_photo);


            mIvphoto = findViewById(R.id.iv_photo);
            mIvphoto.setOnClickListener(this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//            linearLayoutManager.setAutoMeasureEnabled(false);
            mRvcamera.setLayoutManager(linearLayoutManager);
            mRvcamera.setNestedScrollingEnabled(false);

            mCameraAdapter = new CameraAdapter(mContext, imaDatas);
            mRvcamera.setAdapter(mCameraAdapter);

            mCameraAdapter.setOnItemClickListener(new CameraAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (view.getId() == R.id.iv_delete) {
                        mTvphotosum.setText(imaDatas.size() + "/5图片");
                    }else {
                        if (mShowGalleryPhotoListen!=null){
                            mShowGalleryPhotoListen.ShowGalleryPhotoListen(true,position);
                        }
                    }
                }
            });
            initData();

        }
    }

    private void initData() {

    }

    public void setActivity(int activity) {//0,1


    }
    public boolean isAllFile(){
        return imaDatas.size()<5?false:true;
    }
long timel;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_photo:
                if (isAllFile()){
                    ToastUtils.showToast("附件选择不能超过9个");
                    return;
                }
                ActionSheet.showSheet(mContext, ViewCameraJiucuo.this, null);
                break;
        }

    }

    @Override
    public void goBack(int mGoBack) {


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBusCar(PhotoBean mPhotoBean) {
        if (mPhotoBean.position == -1) {
            ActionSheet.showSheet(mContext, this, null);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBusCar(String state) {
        if (MapUtil.VEU.equals(state)) {
            AppTool.isRegistered(false, this, isUn);
        }
    }
    int requestCode=2;
public void setRequestCode(int requestCode){
    this.requestCode=requestCode;
}
    public void ImaDatasclear() {
        imaDatas.clear();
        mTvphotosum.setText("0/5图片");
        mCameraAdapter.notifyDataSetChanged();
    }


    public void notifyData(TResult result) {
        if (imaDatas.size() + result.getImages().size() < 6) {
            imaDatas.addAll(result.getImages());
            mTvphotosum.setText(imaDatas.size() + "/5图片");
            mCameraAdapter.notifyDataSetChanged();
        } else {
            ToastUtils.showToast("照片选择不能超过5张");
        }

    }
    public void notifyData(int p) {
        imaDatas.remove(p);
        mTvphotosum.setText(imaDatas.size() + "/5图片");
        mCameraAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(int whichButton) {
        compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
        mActivity.takePhoto.onEnableCompress(compressConfig, true);
        switch (whichButton) {
            case ActionSheet.CHOOSE_PICTURE:
                //相册
                ToastUtils.showToast("相册");
                mActivity.takePhoto.onPickMultiple(9);
                break;
            case ActionSheet.TAKE_PICTURE:
                ToastUtils.showToast("拍照");
                imageUri = getImageCropUri();
                mActivity.takePhoto.onPickFromCapture(imageUri);
                break;
            case ActionSheet.CANCEL:
                ToastUtils.showToast("取消");
                break;
        }
    }

    public boolean getishavefile() {
        if ( imaDatas.size() == 0){
            return false;
        }

        return true;
    }


    //获得照片的输出保存Uri
    private Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }

    public void FileRequest(String RELYID) {
        Map<String, Object> map = new HashMap<>();
//把高德经纬度转换为84

        map.put("relyid", RELYID);
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < imaDatas.size(); i++) {
            arrayList.add(imaDatas.get(i).getCompressPath());

        }
        mActivity.presenter.FileRequest(2, map, arrayList);
    }


    public interface ShowGalleryPhotoListen {
        void ShowGalleryPhotoListen(boolean isShow, int position);
    }
    ShowGalleryPhotoListen mShowGalleryPhotoListen;
    public void setShowGalleryPhotoListen(ShowGalleryPhotoListen mShowGalleryPhotoListen){
        this.mShowGalleryPhotoListen=mShowGalleryPhotoListen;
    }
}
