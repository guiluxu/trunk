package com.wavenet.ding.qpps.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zoubeiwen on 2018/8/27.
 */

public class DetailsBean {

    @SerializedName("@odata.context")
    public String _$OdataContext277; // FIXME check this code
    @SerializedName("value")
    public List<ValueBean> value;

    public static class ValueBean {
        @SerializedName("@odata.etag")
        public String _$OdataEtag19; // FIXME check this code
        @SerializedName("@odata.id")
        public String _$OdataId316; // FIXME check this code
        @SerializedName("T_IN_DATE")
        public String TINDATE;
        @SerializedName("S_SJCZ_ID")
        public String SSJCZID;
        @SerializedName("S_MANGE_MAN")
        public String SMANGEMAN;
        @SerializedName("T_MANGE_TIME")
        public String TMANGETIME;
        @SerializedName("S_SJSB_ID")
        public String SSJSBID;
        @SerializedName("S_IN_MAN")
        public String SINMAN;
    }
}
