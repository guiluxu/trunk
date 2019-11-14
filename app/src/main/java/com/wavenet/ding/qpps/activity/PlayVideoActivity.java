package com.wavenet.ding.qpps.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dereck.library.base.BaseMvpActivity;
import com.dereck.library.base.BaseMvpPersenter;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.utils.LogUtils;

import java.io.File;


public class PlayVideoActivity extends BaseMvpActivity implements View.OnClickListener{


    private MediaPlayer mediaPlayer;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private boolean isSurfaceCreated =false;        //surface是否已经创建好
    private int curIndex = 0;
    private File mFile;
    //读取本地文件
    String titlestr="";
    String meg="";
    // 返回按钮
    private Button back_bt;
    // 发送按钮
    private ImageView mIvdownload;
    private RelativeLayout mHeadview;
    private RelativeLayout mBbottomlayout;
    //删除按钮
    private Button mBtdownload;
//    private UMShareListener mShareListener;
//    private ShareAction mShareAction;
    String url="";

    @Override
    public int getLayoutId() {
         return R.layout.activity_video_play;
    }

    @Override
    public void init() {
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mHeadview = (RelativeLayout) findViewById(R.id.headview);
        mBbottomlayout = (RelativeLayout) findViewById(R.id.bottom_layout);
        back_bt = (Button) findViewById(R.id.gallery_back);
        back_bt.setOnClickListener(this);
        mIvdownload = (ImageView) findViewById(R.id.iv_download);
        mIvdownload.setOnClickListener(this);
        mBtdownload = (Button)findViewById(R.id.bt_download);
        mBtdownload.setOnClickListener(this);
        mediaPlayer = new MediaPlayer();
        Intent intent=getIntent();
        titlestr = intent.getStringExtra("title");
        meg = intent.getStringExtra("meg");
        if (TextUtils.isEmpty(titlestr)){
            mIvdownload.setVisibility(View.GONE);
        }
        mBtdownload.setVisibility(View.GONE);
        String fileName=intent.getStringExtra("file");
        if(!TextUtils.isEmpty(fileName)){
            mFile = new File(fileName);
        }
        url=intent.getStringExtra("url");
        if(TextUtils.isEmpty(url)){
        }else{
//            mBtdownload.setVisibility(View.VISIBLE);
//            mBbottomlayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void requestData() {
        setBoad();
        initPlayerObj();

        //设置 surfaceView点击监听
        surfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                        } else {
                            mediaPlayer.start();
                        }
                        break;
                }
                //返回True代表事件已经处理了
                return true;
            }
        });
    }

    @Override
    protected BaseMvpPersenter createPresenter() {
             return new BaseMvpPersenter() {
        };
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
//        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 屏幕横竖屏切换时避免出现window leak的问题
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        mShareAction.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gallery_back:
                finish();
                break;
//            case R.id.bt_share:
//                mShareAction.open();
//                break;
            case R.id.bt_download:
//                new DownFileUtils(PlayVideoActivity.this,url);
                break;
        }
    }
    public void setBoad(){
//        SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
//        mShareListener = new CustomShareListener(this);
//        /*增加自定义按钮的分享面板*/
//        mShareAction = new ShareAction(PlayVideoActivity.this).setDisplayList(
//                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
//                .setShareboardclickCallback(new ShareBoardlistener() {
//                    @Override
//                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
//                        {
//                            BitmapFactory.Options opt=new BitmapFactory.Options();
//                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.play_big, opt);
//                            UMVideo video = new UMVideo(url);
//                            video.setThumb(new UMImage(PlayVideoActivity.this, AppTool.combineBitmap(OISApplication.videoThumbnail,bitmap)));
//                            video.setTitle(titlestr);
//                            video.setDescription(meg);
//                            new ShareAction(PlayVideoActivity.this).withMedia(video)
//                                    .setPlatform(share_media)
//                                    .setCallback(mShareListener).share();
//                        }
//                    }
//                });
    }
    private void initPlayerObj()
    {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);      //设置视频流类型
        CreateSurface();
    }

    /**
     * 创建视频展示页面
     */
    private void CreateSurface()
    {
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); //兼容4.0以下的版本
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

                isSurfaceCreated = false;
                if(mediaPlayer.isPlaying())//此处需要注意
                {
                    curIndex = mediaPlayer.getCurrentPosition();
                    mediaPlayer.stop();
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                isSurfaceCreated = true;
                if (mediaPlayer!=null){
                    mediaPlayer.reset();
                    try {
                        //设置视屏文件图像的显示参数
                        mediaPlayer.setDisplay(holder);

                        //file.getAbsolutePath()本地视频
                        //uri 网络视频
//                        if(TextUtils.isEmpty(url)){
//                            mediaPlayer.setDataSource(PlayVideoActivity.this, Uri.parse(mFile.getAbsolutePath()));
//                        }else{
//                            mediaPlayer.setDataSource(PlayVideoActivity.this, Uri.parse(url));
//                        }
                        //prepare();表示准备工作同步进行，（准备工作在UI线程中进行）
                        //当播放网络视频时，如果网络不要 会报ARN 所以不采用该方法
                        //mediaPlayer.prepare();
                        //异步准备 准备工作在子线程中进行 当播放网络视频时候一般采用此方法
//                        mediaPlayer.prepareAsync();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }}
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {

            }
        });

    }
    /**
     * 释放播放器资源
     */
    private void ReleasePlayer()
    {
        if(mediaPlayer!=null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }
    /**
     * 暂停
     */
    private void Pause()
    {
        if (mediaPlayer != null && mediaPlayer.isPlaying())
        {
            curIndex = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();

        }

    }


    private void Play(final int currentPosition)
    {
        try
        {
            mediaPlayer.reset();
            if(TextUtils.isEmpty(url)){
                mediaPlayer.setDataSource(PlayVideoActivity.this, Uri.parse(mFile.getAbsolutePath()));
//                mediaPlayer.setDataSource(PlayVideoActivity.this, Uri.parse(Environment.getExternalStorageDirectory()+"/zzz.mp4"));
            }else{
                mediaPlayer.setDataSource(PlayVideoActivity.this, Uri.parse(url));
            }
            mediaPlayer.setDisplay(surfaceView.getHolder());
            mediaPlayer.setLooping(true);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
            {

                @Override
                public void onPrepared(MediaPlayer mp)
                {
                    mediaPlayer.seekTo(currentPosition);
                    mediaPlayer.start();
                }
            });

            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {

                    return false;
                }
            });
        }
        catch (Exception e)
        {
            LogUtils.i("line:210--GuidVideoActivity--Play--error",e.getMessage());
        }
    }

    /**
     * 创建完毕页面后需要将播放操作延迟10ms防止因surface创建不及时导致播放失败
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        new Handler().postDelayed(new Runnable()
        {
            public void run()
            {
                if(isSurfaceCreated)
                {
                    Play(curIndex);
                }
            }
        }, 10);

    }

    /**
     * 页面从前台到后台会执行 onPause ->onStop 此时Surface会被销毁，
     * 再一次从后台 到前台时需要 重新创建Surface
     */
    @Override
    protected void onRestart()
    {
        super.onRestart();
        if(!isSurfaceCreated)
        {
            CreateSurface();
        }
    }


    @Override
    protected void onPause()
    {
        super.onPause();
        Pause();
    }

    @Override
    protected void onStop()
    {
        super.onStop();

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
//        UMShareAPI.get(this).release();
        ReleasePlayer();

    }


}