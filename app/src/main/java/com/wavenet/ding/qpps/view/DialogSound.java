package com.wavenet.ding.qpps.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dereck.library.view.AnimRotationView;
import com.melnykov.fab.FloatingActionButton;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.utils.AppTool;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by zoubeiwen on 2018/12/4.
 */

public class DialogSound  extends Dialog {
    private Context mContext;
    LinearLayout mLladdview;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_POSITION = "position";
    AnimRotationView mAnimRotationView;
    private int position;

    //Recording controls
    private FloatingActionButton mRecordButton = null;
    private Button mPauseButton = null;

    private TextView mRecordingPrompt;
    private int mRecordPromptCount = 0;

    private boolean mStartRecording = true;
    private boolean mPauseRecording = true;

    private Chronometer mChronometer = null;
    long timeWhenPaused = 0; //stores time when user clicks pause button


    private static final String LOG_TAG = "RecordingService";

    private String mFileName = null;
    public  String mFilePath = null;

    private MediaRecorder mRecorder = null;

    private long mStartingTimeMillis = 0;
    private long mElapsedMillis = 0;
    private int mElapsedSeconds = 0;
    private static final SimpleDateFormat mTimerFormat = new SimpleDateFormat("mm:ss", Locale.getDefault());
    public DialogSound(Context context) {
        super(context);
        mContext = context;
    }

    public DialogSound(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    protected DialogSound(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    protected DialogSound(Context context,SListener mSListener) {
        super(context);
        this.mSListener=mSListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sound);
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.BOTTOM;
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;
        Window window = getWindow();
        window.getDecorView().setPadding(0, 50, 0, 50);
        window.setAttributes(layoutParams);
        window.setDimAmount(0f);//核心代码
        window.setBackgroundDrawable(new ColorDrawable());
        setCancelable(false);
        setCanceledOnTouchOutside(false);
         mAnimRotationView=findViewById(R.id.animRotationView);
        mChronometer = findViewById(R.id.chronometer);
        //update recording prompt text
        mRecordingPrompt =findViewById(R.id.recording_status_text);

        mRecordButton =findViewById(R.id.btnRecord);
        mRecordButton.setColorNormal(mContext.getResources().getColor(R.color.colorPrimaryDark));
        mRecordButton.setColorPressed(mContext.getResources().getColor(R.color.colorPrimaryDark));
    }
    @Override
    public void show() {
        super.show();
        mAnimRotationView.setVisibility(View.VISIBLE);
        intData();
    }

    @Override
    public void dismiss() {
        if (mRecorder != null) {
            stopRecording();
        }
        mAnimRotationView.setVisibility(View.GONE);
        super.dismiss();


    }

    public  void  intData(){
        mRecordButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
         onRecord(false);

//            onRecord(mStartRecording);

//            mStartRecording = !mStartRecording;
        }
    });
        onRecord(true);

    }
    private void onRecord(boolean start){

//        Intent intent = new Intent(mContext, RecordingService.class);

        if (start) {
            // start recording
            mRecordButton.setImageResource(R.mipmap.ic_media_stop);
            //mPauseButton.setVisibility(View.VISIBLE);
//            File folder = new File(Environment.getExternalStorageDirectory() + "/SoundRecorder");
//            File folder = new File(Environment.getExternalStorageDirectory() + "/SoundRecorder");
//            if (!folder.exists()) {
//                //folder /SoundRecorder doesn't exist, create the folder
//                folder.mkdir();
//            }
          File folder=AppTool.getAudioFolder(mContext,"");
            //start Chronometer
            mChronometer.setBase(SystemClock.elapsedRealtime());
            mChronometer.start();
            mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    if (mRecordPromptCount == 0) {
                        mRecordingPrompt.setText("正在录音" + ".");
                    } else if (mRecordPromptCount == 1) {
                        mRecordingPrompt.setText("正在录音" + "..");
                    } else if (mRecordPromptCount == 2) {
                        mRecordingPrompt.setText("正在录音"  + "...");
                        mRecordPromptCount = -1;
                    }

                    mRecordPromptCount++;
                }
            });

            //start RecordingService
//            mContext.startService(intent);
            //keep screen on while recording
            startRecording();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            mRecordingPrompt.setText("正在录音" + ".");
            mRecordPromptCount++;

        } else {
            //stop recording
            mRecordButton.setImageResource(R.mipmap.ic_media_stop);
            //mPauseButton.setVisibility(View.GONE);
            mChronometer.stop();
            mChronometer.setBase(SystemClock.elapsedRealtime());
            timeWhenPaused = 0;
            mRecordingPrompt.setText("正在录音");
            if (mRecorder != null) {
                stopRecording();
            }

            //allow the screen to turn off again once recording is finished
             getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        }
    }
    public void startRecording() {
        setFileNameAndPath();

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setOutputFile(mFilePath);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setAudioChannels(1);
//        if (MySharedPreferences.getPrefHighQuality(this)) {
        mRecorder.setAudioSamplingRate(44100);
        mRecorder.setAudioEncodingBitRate(192000);
//        }

        try {
            mRecorder.prepare();
            mRecorder.start();
            mStartingTimeMillis = System.currentTimeMillis();

            //startTimer();
            //startForeground(1, createNotification());

        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }
    public void setFileNameAndPath(){
        int count = 0;
        File f;

        do{
            count++;

            mFileName = "MyRecording"+System.currentTimeMillis() + ".mp4";
//            mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
//            mFilePath += "/SoundRecorder/" + mFileName;
            f =  AppTool.getAudioFolder(mContext,mFileName);
            mFilePath =  f.getAbsolutePath();
        }while (f.exists() && !f.isDirectory());
    }

    public void stopRecording() {
        mRecorder.stop();
        mElapsedMillis = (System.currentTimeMillis() - mStartingTimeMillis);
        mRecorder.release();
//        Toast.makeText(mContext, "toast_recording_finish" + " " + mFilePath, Toast.LENGTH_LONG).show();

        //remove notification
//        if (mIncrementTimerTask != null) {
//            mIncrementTimerTask.cancel();
//            mIncrementTimerTask = null;
//        }


        mRecorder = null;

        try {
            if (mSListener != null) {
                mSListener.sListener(mFilePath,mElapsedMillis);
                mSListener = null;
            }
//            mDatabase.addRecording(mFileName, mFilePath, mElapsedMillis);
            dismiss();
        } catch (Exception e){
            Log.e(LOG_TAG, "exception", e);
        }
    }

 public   interface SListener {
        void sListener(String soundurl,long time);
    }
    SListener mSListener;
    public void setSListener(SListener mSListener){
        this.mSListener=mSListener;
    }
}