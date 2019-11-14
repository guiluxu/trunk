package com.wavenet.ding.qpps.interf;

import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;

/**
 * Created by zoubeiwen on 2018/9/5.
 */

public interface AddLayerListen {
    //    void getSublayerLis(List<ArcGISMapImageSublayer> mImageSublayerList);
    void getImageLayer(ArcGISMapImageLayer mImageLayer);//falg0管网，1监测
    void getImageLayerJc(ArcGISMapImageLayer mImageLayer);//falg0管网，1监测
}
