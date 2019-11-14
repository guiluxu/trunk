package com.wavenet.ding.qpps.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.ImageFactory;
import com.wavenet.ding.qpps.utils.cameralibrary.JCameraView;
import com.wavenet.ding.qpps.utils.cameralibrary.listener.ClickListener;
import com.wavenet.ding.qpps.utils.cameralibrary.listener.ErrorListener;
import com.wavenet.ding.qpps.utils.cameralibrary.listener.JCameraListener;
import com.wavenet.ding.qpps.utils.cameralibrary.util.DeviceUtil;

import java.io.IOException;

public class TakePhotoActivity extends Activity {
    private JCameraView jCameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_take_photo);
        jCameraView = findViewById(R.id.jcameraview);
        //设置视频保存路径
//        jCameraView.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + "JCamera");
        jCameraView.setSaveVideoPath(AppTool.getVideoFolder(this, null).getAbsolutePath());
        jCameraView.setFeatures(JCameraView.BUTTON_STATE_ONLY_RECORDER);
        jCameraView.setTip("");
//        jCameraView.setTip("JCameraView Tip");
        jCameraView.setMediaQuality(JCameraView.MEDIA_QUALITY_MIDDLE);
        jCameraView.setErrorLisenter(new ErrorListener() {
            @Override
            public void onError() {
                //错误监听
                Log.i("CJT", "camera error");
                Intent intent = new Intent();
                setResult(103, intent);
                finish();
            }

            @Override
            public void AudioPermissionError() {
                Toast.makeText(TakePhotoActivity.this, "给点录音权限可以?", Toast.LENGTH_SHORT).show();
            }
        });
        //JCameraView监听
        jCameraView.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
                Intent intent = new Intent();
                ImageFactory factory = new ImageFactory();
                long timeMillis = System.currentTimeMillis();
                try {
                    String mOutPath;
                    mOutPath = AppTool.getPhotoFolder(TakePhotoActivity.this, timeMillis + ".JPEG").getAbsolutePath();
//                    mOutPath = dcim+ "/" +"Citywater"+"/" + timeMillis + ".JPEG";
//                    mOutPath = Environment.getExternalStorageDirectory().getPath() + "/" +"Citywater"+"/" + timeMillis + ".JPEG";
                    factory.compressAndGenImage(bitmap, mOutPath, 1000000);
                    sendFile(mOutPath);
                    intent.putExtra("bitmap", mOutPath);
                    setResult(1, intent);
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
                ImageFactory factory = new ImageFactory();
                long timeMillis = System.currentTimeMillis();
                //获取成功录像后的视频路径
                try {
                    String mOutPath = AppTool.getPhotoFolder(TakePhotoActivity.this, timeMillis + ".JPEG").getAbsolutePath();
//                    String mOutPath =dcim +  "/" +"Citywater"+"/"  + timeMillis + ".JPEG";
//                    String mOutPath = Environment.getExternalStorageDirectory().getPath() +  "/" +"Citywater"+"/"  + timeMillis + ".JPEG";
                    factory.compressAndGenImage(firstFrame, mOutPath, 1000000);
                    sendFile(url);
                    sendFile(mOutPath);
                    Intent intent = new Intent();
                    intent.putExtra("bitmap", url);//视频路径
                    intent.putExtra("suolvetu", mOutPath);//视频图片路径
                    setResult(2, intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });

        jCameraView.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                TakePhotoActivity.this.finish();
            }
        });
        jCameraView.setRightClickListener(new ClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(TakePhotoActivity.this, "Right", Toast.LENGTH_SHORT).show();
            }
        });
        Log.i("CJT", DeviceUtil.getDeviceModel());
    }

    @Override
    protected void onStart() {
        super.onStart();
        //全屏显示
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        jCameraView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        jCameraView.onPause();
    }

    public void sendFile(String mOutPath) {
//        File f=new File(mOutPath);
//        Uri uri = Uri.fromFile(f);
//        Intent it = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri);
//        sendBroadcast(it);
        MediaScannerConnection.scanFile(this,
                new String[]{mOutPath}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }
}

