package com.wavenet.ding.qpps.db;



import android.util.Log;

import com.dereck.library.observer.CommonObserver;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.Call;


/**
 * Created by jsrgc on 2017/10/11.
 */

public abstract class WavenetCallBack extends StringCallback {

    @Override
    public void onError(Call call, Exception ex, int id) {
        if (ex instanceof InterruptedException) {
            onError(id, "", "InterruptedException");
        } else if (ex instanceof IOException) {
            onError(id, "", "请检查网络是否连接");
        } else if (ex instanceof XmlPullParserException) {
            onError(id, "", "XmlPullParserException");
        } else {
            onError(id, "", "未知错误");
        }
    }

    @Override
    public void onResponse(String response, int id) {
        Log.v("tag", "OkHttpPost_Rspose=" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            onSuccess(id, jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public abstract void onError(int id, String errorCode, String error);

    public abstract void onSuccess(int id, JSONObject result);
}
