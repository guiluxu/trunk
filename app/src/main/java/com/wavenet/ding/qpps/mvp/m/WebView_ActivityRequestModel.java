package com.wavenet.ding.qpps.mvp.m;

import com.dereck.library.observer.CommonObserver;

import java.util.HashMap;
import java.util.Map;

public class WebView_ActivityRequestModel {
    public void request(String userName, String passWord, CommonObserver<Object> callback) {
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("userName", userName);
        reqParams.put("passWord", passWord);

    }
}
