package com.wavenet.ding.qpps.utils.iflytek.voicedemo;//
//package com.fxriver.wavenet.fxriver.utils.iflytek.voicedemo;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.os.Environment;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.Window;
//import android.widget.EditText;
//import android.widget.RadioGroup;
//import android.widget.RadioGroup.OnCheckedChangeListener;
//import android.widget.Toast;
//
//import com.fxriver.wavenet.fxriver.R;
//import com.fxriver.wavenet.fxriver.utils.iflytek.speech.setting.IatSettings;
//import com.iflytek.cloud.ErrorCode;
//import com.iflytek.cloud.InitListener;
//import com.iflytek.cloud.RecognizerListener;
//import com.iflytek.cloud.RecognizerResult;
//import com.iflytek.cloud.SpeechConstant;
//import com.iflytek.cloud.SpeechError;
//import com.iflytek.cloud.SpeechRecognizer;
//import com.iflytek.cloud.ui.RecognizerDialog;
//import com.iflytek.cloud.ui.RecognizerDialogListener;
//import com.iflytek.sunflower.FlowerCollector;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//
//
//public class IatDemo extends Activity implements OnClickListener {
//	private static String TAG = IatDemo.class.getSimpleName();
//	// 语音听写对象
//	private SpeechRecognizer mIat;
//	// 语音听写UI
//	private RecognizerDialog mIatDialog;
//	// 用HashMap存储听写结果
//	private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
//
//	private EditText mResultText;
//	private Toast mToast;
//	private SharedPreferences mSharedPreferences;
//	// 引擎类型
//	private String mEngineType = SpeechConstant.TYPE_CLOUD;
//
//	private boolean mTranslateEnable = false;
//
//
//	@SuppressLint("ShowToast")
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.iatdemo);
//
//		initLayout();
//		// 初始化识别无UI识别对象
//		// 使用SpeechRecognizer对象，可根据回调消息自定义界面；
//		mIat = SpeechRecognizer.createRecognizer(IatDemo.this, mInitListener);
//
//		// 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
//		// 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
//		mIatDialog = new RecognizerDialog(IatDemo.this, mInitListener);
//
//		mSharedPreferences = getSharedPreferences(IatSettings.PREFER_NAME,
//				Activity.MODE_PRIVATE);
//		mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
//		mResultText = ((EditText) findViewById(R.id.iat_text));
//	}
//
//	/**
//	 * 初始化Layout。
//	 */
//	private void initLayout() {
//		findViewById(R.id.iat_recognize).setOnClickListener(IatDemo.this);
//		// 目前仅支持在线听写
//		RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup);
//		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//			@Override
//			public void onCheckedChanged(RadioGroup group, int checkedId) {
//				if( null == mIat ){
//					// 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
//					showTip( "创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化" );
//					return;
//				}
//
//				switch (checkedId) {
//					case R.id.iatRadioCloud:
//						mEngineType = SpeechConstant.TYPE_CLOUD;
////						findViewById(R.id.iat_upload_contacts).setEnabled(true);
////						findViewById(R.id.iat_upload_userwords).setEnabled(true);
//						break;
//					default:
//						break;
//				}
//			}
//		});
//	}
//
//	int ret = 0; // 函数调用返回值
//
//	@Override
//	public void onClick(View view) {
//		if( null == mIat ){
//			// 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
//			this.showTip( "创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化" );
//			return;
//		}
//
//		switch (view.getId()) {
//
//		// 开始听写
//		// 如何判断一次听写结束：OnResult isLast=true 或者 onError
//		case R.id.iat_recognize:
//			// 移动数据分析，收集开始听写事件
//			FlowerCollector.onEvent(IatDemo.this, "iat_recognize");
//
//			mResultText.setText(null);// 清空显示内容
//			mIatResults.clear();
//			// 设置参数
//			setParam();
//			boolean isShowDialog = mSharedPreferences.getBoolean(
//					getString(R.string.pref_key_iat_show), true);
//			if (isShowDialog) {
//				// 显示听写对话框
//				mIatDialog.setListener(mRecognizerDialogListener);
//				mIatDialog.show();
//				showTip(getString(R.string.text_begin));
//			} else {
//				// 不显示听写对话框
//				ret = mIat.startListening(mRecognizerListener);
//				if (ret != ErrorCode.SUCCESS) {
//					showTip("听写失败,错误码：" + ret);
//				} else {
//					showTip(getString(R.string.text_begin));
//				}
//			}
//			break;
//
//		default:
//			break;
//		}
//	}
//
//	/**
//	 * 初始化监听器。
//	 */
//	private InitListener mInitListener = new InitListener() {
//
//		@Override
//		public void onInit(int code) {
//			Log.d(TAG, "SpeechRecognizer init() code = " + code);
//			if (code != ErrorCode.SUCCESS) {
//				showTip("初始化失败，错误码：" + code);
//			}
//		}
//	};
//
//	/**
//	 * 听写监听器。
//	 */
//	private RecognizerListener mRecognizerListener = new RecognizerListener() {
//
//		@Override
//		public void onBeginOfSpeech() {
//			// 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
//			showTip("开始说话");
//		}
//
//		@Override
//		public void onError(SpeechError error) {
//			// Tips：
//			// 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
//			// 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
//			if(mTranslateEnable && error.getErrorCode() == 14002) {
//				showTip( error.getPlainDescription(true)+"\n请确认是否已开通翻译功能" );
//			} else {
//				showTip(error.getPlainDescription(true));
//			}
//		}
//
//		@Override
//		public void onEndOfSpeech() {
//			// 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
//			showTip("结束说话");
//		}
//
//		@Override
//		public void onResult(RecognizerResult results, boolean isLast) {
//			Log.d(TAG, results.getResultString());
//			if( mTranslateEnable ){
//				printTransResult( results );
//			}else{
//				printResult(results);
//			}
//
//			if (isLast) {
//				// TODO 最后的结果
//			}
//		}
//
//		@Override
//		public void onVolumeChanged(int volume, byte[] data) {
//			showTip("当前正在说话，音量大小：" + volume);
//			Log.d(TAG, "返回音频数据："+data.length);
//		}
//
//		@Override
//		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
//			// 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
//			// 若使用本地能力，会话id为null
//			//	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
//			//		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
//			//		Log.d(TAG, "session id =" + sid);
//			//	}
//		}
//	};
//
//	private void printResult(RecognizerResult results) {
//		String text = JsonParser.parseIatResult(results.getResultString());
//
//		String sn = null;
//		// 读取json结果中的sn字段
//		try {
//			JSONObject resultJson = new JSONObject(results.getResultString());
//			sn = resultJson.optString("sn");
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//
//		mIatResults.put(sn, text);
//
//		StringBuffer resultBuffer = new StringBuffer();
//		for (String key : mIatResults.keySet()) {
//			resultBuffer.append(mIatResults.get(key));
//		}
//
//		mResultText.setText(resultBuffer.toString());
//		mResultText.setSelection(mResultText.length());
//	}
//
//	/**
//	 * 听写UI监听器
//	 */
//	private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
//		public void onResult(RecognizerResult results, boolean isLast) {
//			if( mTranslateEnable ){
//				printTransResult( results );
//			}else{
//				printResult(results);
//			}
//
//		}
//
//		/**
//		 * 识别回调错误.
//		 */
//		public void onError(SpeechError error) {
//			if(mTranslateEnable && error.getErrorCode() == 14002) {
//				showTip( error.getPlainDescription(true)+"\n请确认是否已开通翻译功能" );
//			} else {
//				showTip(error.getPlainDescription(true));
//			}
//		}
//
//	};
//	private void showTip(final String str) {
//		mToast.setText(str);
//		mToast.show();
//	}
//
//	/**
//	 * 参数设置
//	 *
//	 * @return
//	 */
//	public void setParam() {
//		// 清空参数
//		mIat.setParameter(SpeechConstant.PARAMS, null);
//
//		// 设置听写引擎
//		mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
//		// 设置返回结果格式
//		mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
//
//		this.mTranslateEnable = mSharedPreferences.getBoolean( this.getString(R.string.pref_key_translate), false );
//		if( mTranslateEnable ){
//			Log.i( TAG, "translate enable" );
//			mIat.setParameter( SpeechConstant.ASR_SCH, "1" );
//			mIat.setParameter( SpeechConstant.ADD_CAP, "translate" );
//			mIat.setParameter( SpeechConstant.TRS_SRC, "its" );
//		}
//
//		String lag = mSharedPreferences.getString("iat_language_preference",
//				"mandarin");
//		if (lag.equals("en_us")) {
//			// 设置语言
//			mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
//			mIat.setParameter(SpeechConstant.ACCENT, null);
//
//			if( mTranslateEnable ){
//				mIat.setParameter( SpeechConstant.ORI_LANG, "en" );
//				mIat.setParameter( SpeechConstant.TRANS_LANG, "cn" );
//			}
//		} else {
//			// 设置语言
//			mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
//			// 设置语言区域
//			mIat.setParameter(SpeechConstant.ACCENT, lag);
//
//			if( mTranslateEnable ){
//				mIat.setParameter( SpeechConstant.ORI_LANG, "cn" );
//				mIat.setParameter( SpeechConstant.TRANS_LANG, "en" );
//			}
//		}
//
//		// 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
//		mIat.setParameter(SpeechConstant.VAD_BOS, "4000");
//
//		// 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
//		mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
//
//		// 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
//		mIat.setParameter(SpeechConstant.ASR_PTT, "1");
//
//		// 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
//		// 注：AUDIO_FORMAT参数语记需要更新版本才能生效
//		mIat.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
//		mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/iat.wav");
//	}
//
//	private void printTransResult (RecognizerResult results) {
//		String trans  = JsonParser.parseTransResult(results.getResultString(),"dst");
//		String oris = JsonParser.parseTransResult(results.getResultString(),"src");
//
//		if( TextUtils.isEmpty(trans)|| TextUtils.isEmpty(oris) ){
//			showTip( "解析结果失败，请确认是否已开通翻译功能。" );
//		}else{
//			mResultText.setText( "原始语言:\n"+oris+"\n目标语言:\n"+trans );
//		}
//
//	}
//
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//
//		if( null != mIat ){
//			// 退出时释放连接
//			mIat.cancel();
//			mIat.destroy();
//		}
//	}
//
//	@Override
//	protected void onResume() {
//		// 开放统计 移动数据统计分析
//		FlowerCollector.onResume(IatDemo.this);
//		FlowerCollector.onPageStart(TAG);
//		super.onResume();
//	}
//
//	@Override
//	protected void onPause() {
//		// 开放统计 移动数据统计分析
//		FlowerCollector.onPageEnd(TAG);
//		FlowerCollector.onPause(IatDemo.this);
//		super.onPause();
//	}
//}
