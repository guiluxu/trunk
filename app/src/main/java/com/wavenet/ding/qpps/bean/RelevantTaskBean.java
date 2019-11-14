package com.wavenet.ding.qpps.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zoubeiwen on 2018/8/28.
 */

public class RelevantTaskBean {

    @SerializedName("@odata.context")
    public String _$OdataContext187; // FIXME check this code
    @SerializedName("value")
    public List<ValueBean> value;

    public static class ValueBean {
        @SerializedName("@odata.etag")
        public String _$OdataEtag67; // FIXME check this code
        @SerializedName("S_TYPE")
        public String STYPE;
        @SerializedName("N_X")
        public String NX;
        @SerializedName("S_IS_MANGE")
        public String SISMANGE;
        @SerializedName("S_MANGE_ID")
        public String SMANGEID;
        @SerializedName("N_Y")
        public String NY;
        @SerializedName("S_IN_MAN")
        public String SINMAN;
        @SerializedName("S_CATEGORY")
        public String SCATEGORY;
        @SerializedName("T_CREATE")
        public String TCREATE;
        @SerializedName("T_IN_DATE")
        public String TINDATE;
    }
}
