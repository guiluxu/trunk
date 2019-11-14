package com.wavenet.ding.qpps.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ding on 2018/10/31.
 */

public class CheckBean {


    @SerializedName("@odata.context")
    public String _$OdataContext284; // FIXME check this code
    public List<ValueBean> value;

    public static class ValueBean {
        @SerializedName("@odata.etag")
        public String _$OdataEtag136; // FIXME check this code
        public int S_ISTEST;

    }
}
