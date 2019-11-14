package com.wavenet.ding.qpps.bean;

import android.content.Context;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.wavenet.ding.qpps.api.QPSWApplication;
import com.wavenet.ding.qpps.utils.SPUtil;

import java.util.ArrayList;

/**
 * Created by zoubeiwen on 2018/7/27.
 */

public class ClasBean {
//value="t_stime",alternate = "d_time"
    @SerializedName(value = "Code",alternate = "code")
    public String Code;
    @SerializedName(value = "Msg",alternate = "msg")
    public String Msg;
    @SerializedName(value = "Data",alternate = "data")
    public ArrayList<ValueBean> value;
    public int options;
    public int ui;

    public void setDictionaries(Context mContext) {
        int size = value.size();

        ClasBean.ValueBean b;
        for (int j = 0; j < size; j++) {
            b = value.get(j);
            QPSWApplication.getInstance().m.put(b.SCORRESPOND, b.SVALUE);
        }
        SPUtil.getInstance(mContext).setStringValue(SPUtil.Dict, new Gson().toJson(QPSWApplication.getInstance().m));
    }

    public static class ValueBean implements IPickerViewData {
        @SerializedName("@odata.etag")
        public String _$OdataEtag293; // FIXME check this code
        @SerializedName(value = "S_CORRESPOND",alternate = "scorrespond")
        public String SCORRESPOND;
        @SerializedName(value = "S_VALUE",alternate = "svalue")
        public String SVALUE;

        @Override
        public String getPickerViewText() {
            return SVALUE;
        }

        public ValueBean(String SVALUE) {
            this.SVALUE = SVALUE;
        }
    }

}
