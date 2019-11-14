package com.wavenet.ding.qpps.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zoubeiwen on 2018/11/21.
 */

public class ChangePasBean {

    /**
     * Code : 200
     * Msg : 修改成功!
     * Data : null
     */

    @SerializedName("Code")
    public String Code;
    @SerializedName("Msg")
    public String Msg;
    @SerializedName("Data")
    public Object Data;
}
