package com.wavenet.ding.qpps.demo.model;


import com.dereck.library.observer.CommonObserver;

import java.util.HashMap;
import java.util.Map;

public class RecyclerViewActivityRequestModel {

    public void request(String pageNumber, CommonObserver<String> callback) {


        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("empId", "2017001");
        reqParams.put("pageNumber", pageNumber);
        reqParams.put("readType", "1");
        reqParams.put("pageSize", "10");


    }


}
