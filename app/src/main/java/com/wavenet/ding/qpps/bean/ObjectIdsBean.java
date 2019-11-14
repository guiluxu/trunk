package com.wavenet.ding.qpps.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zoubeiwen on 2018/9/14.
 */

public class ObjectIdsBean {

    /**
     * objectIdFieldName : OBJECTID
     * objectIds : [2708,2709,2710,2711,2712,2713,2715,2716,2717,2718,2719,2720,2721,2722,2723,2773,2951,2952,2953,2954,2955,2956,2958,2959,2960,2961,2962,2963,2964,2965,2966,2967,2968,2969,2970,2971,2972,2973,2974,2975,2976,2977,2978,2979,2980,2981,3530,3531,3532,3533,3534,3548,3549,3588,3589,3590,3591,3592,3608,3624,3625,3633]
     */

    @SerializedName("objectIdFieldName")
    public String objectIdFieldName;
    @SerializedName("objectIds")
    public List<Integer> objectIds;
}
