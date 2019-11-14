//package com.wavenet.ding.qpps.view;
//
//import android.content.Context;
//import android.net.Uri;
//import android.os.Environment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.FrameLayout;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.dereck.library.utils.ToastUtils;
//import com.guoqi.actionsheet.ActionSheet;
//import com.wavenet.ding.qpps.R;
//import com.wavenet.ding.qpps.activity.MainMapXJActivity;
//import com.wavenet.ding.qpps.bean.EventBean;
//import com.wavenet.ding.qpps.bean.Gps;
//import com.wavenet.ding.qpps.bean.PhotoBean;
//import com.wavenet.ding.qpps.utils.AppTool;
//import com.wavenet.ding.qpps.utils.MapUtil;
//import com.wavenet.ding.qpps.utils.PositionUtil;
//
//import org.devio.takephoto.app.TakePhotoActivity;
//import org.devio.takephoto.compress.CompressConfig;
//import org.devio.takephoto.model.InvokeParam;
//import org.devio.takephoto.model.TImage;
//import org.devio.takephoto.model.TResult;
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//
//public class ControllerCameraView1 extends LinearLayout implements View.OnClickListener, ActionSheet.OnActionSheetSelected {
//    private static final String TAG = TakePhotoActivity.class.getName();
//    Context mContext;
//    MainMapXJActivity mActivity;
//    RecyclerView mRvcamera;
//    ImageRecyclerViewAdapter mCameraAdapter;
//    TextView mTvphotosum;
//    ArrayList<TImage> imaDatas = new ArrayList<>();
//    int isUn;
//    //    OkHttpPost mOkHttpPost;
//
//    private Uri imageUri;  //图片保存路径
//    private InvokeParam invokeParam;
//    private CompressConfig compressConfig;  //压缩参数
//
//    public ControllerCameraView1(Context context) {
//        super(context);
//        initView(context);
//    }
//
//    public ControllerCameraView1(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        initView(context);
//    }
//
//    public ControllerCameraView1(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        initView(context);
//    }
//
//    private void initView(Context context) {
//        mContext = context;
//        mActivity = (MainMapXJActivity) context;
//        setLayoutParams(new FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.MATCH_PARENT,
//                FrameLayout.LayoutParams.WRAP_CONTENT));
//        setOrientation(LinearLayout.VERTICAL);
//        LayoutInflater.from(mContext).inflate(R.layout.view_camera, this);
//        if (!isInEditMode()) {
//            isUn = AppTool.isRegistered(true, this, isUn);
//            mTvphotosum = findViewById(R.id.tv_photosum);
//            mRvcamera = findViewById(R.id.rv_photo);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
//            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//            mRvcamera.setLayoutManager(linearLayoutManager);
//            mCameraAdapter = new ImageRecyclerViewAdapter(mContext, imaDatas);
//            mRvcamera.setAdapter(mCameraAdapter);
//
//            mCameraAdapter.setOnItemClickListener(new ImageRecyclerViewAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(View view, int position) {
//                    if (position == 0) {
//                        ActionSheet.showSheet(mContext, ControllerCameraView1.this, null);
//                    }
//                }
//            });
//            initData();
//
//        }
//    }
//
//    private void initData() {
//    }
//
//    @Override
//    public void onClick(View view) {
////        switch ()
//
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void EventBusCar(PhotoBean mPhotoBean) {
//        if (mPhotoBean.position == -1) {
//            ActionSheet.showSheet(mContext, this, null);
//        }
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void EventBusCar(String state) {
//        if (MapUtil.PC.equals(state)) {
//            imaDatas.clear();
//            mCameraAdapter.notifyDataSetChanged();
//        } else if (MapUtil.VEU.equals(state)) {
//            AppTool.isRegistered(false, this, isUn);
//        }
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void EventBusCar(EventBean e) {
//        if (MapUtil.FR.equals(e.state)) {
//            if (mActivity.isup) {
//                mActivity.isup = false;
//                FileRequest(6);
//            }
//        } else if (MapUtil.FTD.equals(e.state)) {
//            if (mActivity.isup) {
//                mActivity.isup = false;
//                FileRequest(61);
//            }
//        } else if (MapUtil.FTA.equals(e.state)) {
//            if (mActivity.isup) {
//                mActivity.isup = false;
//                FileRequest(62);
//            }
//        }
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void EventBusCar(TResult result) {
//        imaDatas.addAll(result.getImages());
//        mTvphotosum.setText(imaDatas.size() + "/9");
//        mCameraAdapter.notifyDataSetChanged();
//    }
//
//    @Override
//    public void onClick(int whichButton) {
//        compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
//        mActivity.takePhoto.onEnableCompress(compressConfig, true);
//        switch (whichButton) {
//            case ActionSheet.CHOOSE_PICTURE:
//                //相册
//                ToastUtils.showToast("相册");
//                mActivity.takePhoto.onPickMultiple(9);
//                break;
//            case ActionSheet.TAKE_PICTURE:
//                ToastUtils.showToast("拍照");
//                imageUri = getImageCropUri();
//                mActivity.takePhoto.onPickFromCapture(imageUri);
//                break;
//            case ActionSheet.CANCEL:
//                ToastUtils.showToast("取消");
//                break;
//        }
//    }
//
//    public boolean getishavefile() {
//        return imaDatas.size() != 0;
//    }
//    //获得照片的输出保存Uri
//    private Uri getImageCropUri() {
//        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
//        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
//        return Uri.fromFile(file);
//    }
//
//    public void FileRequest(int file) {
//        Map<String, Object> map = new HashMap<>();
////把高德经纬度转换为84
//        Gps g = PositionUtil.gcj_To_Gps84(mActivity.aMapLocation.getLatitude(), mActivity.aMapLocation.getLongitude());
//
//        map.put("x", g.getWgLat());
//
//        map.put("y", g.getWgLon());
//        map.put("relyid", MainMapXJActivity.S_RECODE_ID);
//
//        ArrayList<String> arrayList = new ArrayList<>();
//        for (int i = 0; i < imaDatas.size(); i++) {
//            arrayList.add(imaDatas.get(i).getCompressPath());
//        }
//
//        mActivity.presenter.FileRequest(file, map, arrayList);
//    }
//
//}
