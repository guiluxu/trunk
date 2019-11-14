package com.wavenet.ding.qpps.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dereck.library.utils.ToastUtils;
import com.guoqi.actionsheet.ActionSheet;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.activity.MainMapXJActivity;
import com.wavenet.ding.qpps.activity.PlayVideoActivity;
import com.wavenet.ding.qpps.activity.XJDealActivity;
import com.wavenet.ding.qpps.adapter.CameraAdapter;
import com.wavenet.ding.qpps.adapter.PhotoGvAdapter;
import com.wavenet.ding.qpps.bean.PhotoBean;
import com.wavenet.ding.qpps.fragment.PlaybackFragment;
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


public class ControllerCameraView extends LinearLayout implements View.OnClickListener, ActionSheet.OnActionSheetSelected, GoBackInteDef.GoBack {
    private static final String TAG = TakePhotoActivity.class.getName();
    Context mContext;
    MainMapXJActivity mActivity;
    XJDealActivity mActivity1;
    RecyclerView mRvcamera;
    public CameraAdapter mCameraAdapter;
    TextView mTvphotosum;
    public ArrayList<TImage> imaDatas = new ArrayList<>();
    ImageView mIvvideo, mIvspeech, mIvphoto, mIvvideobitmap, mIvdelvideo, mIvdelsound;
    int isUn;
    //    OkHttpPost mOkHttpPost;
    int a = 0;
    private Uri imageUri;  //图片保存路径
    private InvokeParam invokeParam;
    private CompressConfig compressConfig;  //压缩参数
    RelativeLayout mRlvideo, mRlsound;
    private GridView gvphoto;
    private PhotoGvAdapter photo_gvadapter;
    DialogSound mDialogSound;

    private int tag;//判断是否是上报进来的，需要屏蔽选择相册

    public ControllerCameraView(Context context) {
        super(context);
        initView(context);
    }

    public ControllerCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    public ControllerCameraView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        mActivity = (MainMapXJActivity) mContext;
        setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT));
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(mContext).inflate(R.layout.view_camera, this);
        if (!isInEditMode()) {
            isUn = AppTool.isRegistered(true, this, isUn);
            mTvphotosum = findViewById(R.id.tv_photosum);
            mRvcamera = findViewById(R.id.rv_photo);
            mIvvideo = findViewById(R.id.iv_video);
            mIvvideo.setOnClickListener(this);
            mRlvideo = findViewById(R.id.rl_video);
            mRlvideo.setOnClickListener(this);
            mIvdelvideo = findViewById(R.id.iv_delvideo);
            mIvdelvideo.setOnClickListener(this);
            mIvspeech = findViewById(R.id.iv_speech);
            mIvspeech.setOnClickListener(this);
            mRlsound = findViewById(R.id.rl_sound);
            mRlsound.setOnClickListener(this);
            mIvdelsound = findViewById(R.id.iv_delsound);
            mIvdelsound.setOnClickListener(this);
            mIvvideobitmap = findViewById(R.id.iv_videobitmap);
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
                        getFileSum();
                    } else {
                        if (mShowGalleryPhotoListen != null) {
                            mShowGalleryPhotoListen.ShowGalleryPhotoListen(true, position);
                        }
                    }
                }
            });
            initData();

        }
    }

    private void initData() {

    }

    public int getFileSum() {
        int s = 0;
        if (mRlvideo.getVisibility() == VISIBLE) {

            s++;
        }
        if (mRlsound.getVisibility() == VISIBLE) {
            s++;
        }
        s = s + imaDatas.size();
        mTvphotosum.setText(s + "/9文件");
        return s;
    }

    public void setActivity(int activity) {//0,1
        a = activity;
        if (activity == 0) {
            mActivity = (MainMapXJActivity) mContext;
        } else if (activity == 1) {
            mActivity1 = (XJDealActivity) mContext;
        }

    }

    public boolean isAllFile() {
        int i = 0;
        if (mRlvideo.getVisibility() == VISIBLE) {
            i++;
        }
        if (mRlsound.getVisibility() == VISIBLE) {
            i++;
        }
        i = i + imaDatas.size();
        return i < 9 ? false : true;
    }

    long timel;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_video:
                if (isAllFile()) {
                    ToastUtils.showToast("附件选择不能超过9个");
                    return;
                }

                Intent i = new Intent(mActivity, com.wavenet.ding.qpps.activity.TakePhotoActivity.class);
                mActivity.startActivityForResult(i, 2);
                break;

            case R.id.rl_video:
                Intent intent = new Intent(mActivity, PlayVideoActivity.class);
//               String f= Environment.getExternalStorageDirectory().getAbsolutePath()+"/IMG_0114.MOV";
                intent.putExtra("file", mfilevideo);
                mActivity.startActivity(intent);
//                MapUtil.getInstance(mContext).showIspalyvoideDialog(mContext, this);
                break;

            case R.id.iv_delvideo:
                mvideoBitmap = "";
                mfilevideo = "";
                mRlvideo.setVisibility(GONE);
                getFileSum();
                break;
            case R.id.iv_speech:
                if (isAllFile()) {
                    ToastUtils.showToast("附件选择不能超过9个");
                    return;
                }
                if (mDialogSound == null) {
                    mDialogSound = new DialogSound(mContext);
                }
                mDialogSound.setSListener(new DialogSound.SListener() {
                    @Override
                    public void sListener(String soundurl, long time) {
                        timel = time;
                        if (mRlsound.getVisibility() == GONE) {
                            mSoundrul = soundurl;
                            mRlsound.setVisibility(VISIBLE);
                            getFileSum();
                        } else {
                            MapUtil.getInstance(mContext).showIsReplaceFileDialog(mContext, ControllerCameraView.this, GoBackInteDef.setBack(GoBackInteDef.REPLACESOUND), "只能上传一个语音文件，是否要替换已有的语音?");
                        }

                    }
                });
                mDialogSound.show();
//                    mDialogSound.createDialog();

                break;
            case R.id.rl_sound:
                try {
                    PlaybackFragment playbackFragment =
                            new PlaybackFragment().newInstance(mSoundrul, timel);

                    FragmentTransaction transaction = ((FragmentActivity) mContext)
                            .getSupportFragmentManager()
                            .beginTransaction();

                    playbackFragment.show(transaction, "dialog_playback");

                } catch (Exception e) {
//                        Log.e(LOG_TAG, "exception", e);
                }
                break;
            case R.id.iv_delsound:
                mSoundrul = "";
                mRlsound.setVisibility(GONE);
                getFileSum();
                break;

            case R.id.iv_photo:
                if (isAllFile()) {
                    ToastUtils.showToast("附件选择不能超过9个");
                    return;
                }
                ActionSheetSuper.showSheet(mContext, tag == ControllerMainUIView.REPORT, ControllerCameraView.this, null);
                break;
        }

    }

    @Override
    public void goBack(int mGoBack) {
        switch (mGoBack) {
            case GoBackInteDef.DELEVIDEO:
                break;
            case GoBackInteDef.PALYVIDEO:

                break;
            case GoBackInteDef.REPLACEVIDEO:
                setVideo();
                break;
            case GoBackInteDef.REPLACESOUND:
                mSoundrul = mDialogSound.mFilePath;
                getFileSum();
                break;

        }

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

    int requestCode = 2;

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public void ImaDatasclear() {
        mSoundrul = "";
        mRlsound.setVisibility(GONE);
        mvideoBitmap = "";
        mfilevideo = "";
        mRlvideo.setVisibility(GONE);
        imaDatas.clear();
        mTvphotosum.setText("0/9文件");
        mCameraAdapter.notifyDataSetChanged();
    }


    public void notifyData(TResult result) {
        int fs = result.getImages().size();
        if (getFileSum() + fs < 10) {
            imaDatas.addAll(result.getImages());
            getFileSum();
            mCameraAdapter.notifyDataSetChanged();
        } else {
            ToastUtils.showToast("附件选择不能超过9个");
        }

    }

    public void notifyData(int p) {
        imaDatas.remove(p);
        getFileSum();
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
        if (imaDatas.size() == 0) {
            return false;
        }
        if (AppTool.isNull(mfilevideo) && AppTool.isNull(mSoundrul) && imaDatas.size() == 0) {
            return false;
        }

        return true;
    }

    private String mSoundrul;//录音路径
    private String mvideoBitmap;//视频图片路径
    private String mfilevideo;//视频路径
    private String mvideoBitmapTem;//视频图片路径临时
    private String mfilevideoTem;//视频路径临时

    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        switch (requestCode) {
            case 2:
                if (resultCode == 2) {
                    mfilevideoTem = data.getStringExtra("bitmap");//视频的路径
                    mvideoBitmapTem = data.getStringExtra("suolvetu");//视频图片路径
                    if (AppTool.isNull(mvideoBitmapTem) || AppTool.isNull(mfilevideoTem)) {
                        ToastUtils.showToast("视频保存失败，请重新录制");
                        return;
                    }
                    if (mRlvideo.getVisibility() == View.GONE) {
                        mRlvideo.setVisibility(VISIBLE);
                        setVideo();
                    } else {

                        MapUtil.getInstance(mContext).showIsReplaceFileDialog(mContext, this, GoBackInteDef.setBack(GoBackInteDef.REPLACEVIDEO), "只能上传一个视频，是否要替换已有的视频?");
                    }
                }
                break;
        }
    }

    public void setVideo() {
        mvideoBitmap = mvideoBitmapTem;
        mfilevideo = mfilevideoTem;
        getFileSum();
        Glide.with(mContext)
                .load(mvideoBitmap).into(mIvvideobitmap);
    }

    //获得照片的输出保存Uri
    private Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }

    /**
     * 上传附件
     */
    public void FileRequest(String filestr) {
        int file = 6;
        if (MapUtil.FR.equals(filestr)) {
            file = 6;
        } else if (MapUtil.FTD.equals(filestr)) {
            file = 61;
        } else if (MapUtil.FTA.equals(filestr)) {
            file = 62;
        } else if (MapUtil.FTDP.equals(filestr)) {
            file = 63;
        }

        fileRequest(file);
        //上传文件前，先判断网络状态
//        String netType = NetUtils.getCurrentNetworkType();
//        if (netType.equals("2G") || netType.equals("3G")) {
//            showTipDialog(file);
//        }
    }

    public void showTipDialog(final int file) {

        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
        window.setContentView(R.layout.dialog_tips);
        window.findViewById(R.id.version_describe).setVisibility(View.VISIBLE);
        TextView tv_content = window.findViewById(R.id.tv_content);
        tv_content.setText("当前网络状态不稳定，附件可能上传失败，是否继续？");

        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                fileRequest(file);
            }
        });

        window.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                listener.showExit();
            }
        });
    }

    private void fileRequest(int file) {

        Map<String, Object> map = new HashMap<>();

        map.put("x", MainMapXJActivity.g.getWgLon());
        map.put("y", MainMapXJActivity.g.getWgLat());
        map.put("relyid", MainMapXJActivity.S_MANGE_ID);//S_SJSB_ID
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < imaDatas.size(); i++) {
            arrayList.add(imaDatas.get(i).getCompressPath());

        }
        if (mRlvideo.getVisibility() == VISIBLE && !AppTool.isNull(mfilevideo)) {
            arrayList.add(mfilevideo);
        }
        String audioPath = null;
        if (mRlsound.getVisibility() == VISIBLE && !AppTool.isNull(mSoundrul)) {
            File f = new File(mSoundrul);
            File mm = AppTool.getAudioFolder(mContext, "/MyRecording" + System.currentTimeMillis() + "@" + timel + ".mp4");
            if (f.renameTo(mm)) {
                audioPath = mm.getAbsolutePath();
                arrayList.add(audioPath);
            }
        }
        mActivity.presenter.FileRequest(file, map, arrayList, imaDatas, mfilevideo, audioPath);
    }

    public void setType(int type) {
        this.tag = type;
    }


    public interface ShowGalleryPhotoListen {
        void ShowGalleryPhotoListen(boolean isShow, int position);
    }

    ShowGalleryPhotoListen mShowGalleryPhotoListen;

    public void setShowGalleryPhotoListen(ShowGalleryPhotoListen mShowGalleryPhotoListen) {
        this.mShowGalleryPhotoListen = mShowGalleryPhotoListen;
    }

    public interface ShowExitViewListener {
        void showExit();
    }

    private ShowExitViewListener listener;

    public void setExitViewListener(ShowExitViewListener listener) {
        this.listener = listener;
    }
}
