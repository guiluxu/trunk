package com.wavenet.ding.qpps.bean;

import java.io.Serializable;

/**
 * Created by zoubeiwen on 2018/8/17.
 */

public class Legendbean implements Serializable{

public boolean isShowTitle=false;
public boolean isSelectLl=false;
public boolean isSelectIv=false;
public int Ivid;
public String title="";
public String name="";
public int mLayer=0;//0单个图层，1多个图层
public int[] mLayerIndexArry;//指定图层打开的所有index；
 public int mLayerId ;
 public String[] key ;//id
 public int  mLayerIndex ;
 public String mImageLayer;//哪个ImageLayer图层跟title对应
 public int requestIddata;//哪个ImageLayer图层跟title对应
 public int requestIdhis;//哪个ImageLayer图层跟title对应
 public String requesturldata;//哪个ImageLayer图层跟title对应
 public String requesturlhis;//哪个ImageLayer图层跟title对应

    public Legendbean(boolean isShowTitle,String title, String name,int Ivid, int mLayer, int mLayerId, int mLayerIndex, String mImageLayer ,String[] key,int[] mLayerIndexArry,int requestIddata,String requesturldata,int requestIdhis,String requesturlhis) {
        this.title = title;
        this.name = name;
        this.mLayer = mLayer;
        this.mLayerId = mLayerId;
        this.mLayerIndex = mLayerIndex;
        this.mImageLayer = mImageLayer;
        this.key = key;
        this.mLayerIndexArry = mLayerIndexArry;
        this.requestIddata = requestIddata;
        this.requesturldata = requesturldata;
        this.requestIdhis = requestIdhis;
        this.requesturlhis = requesturlhis;
        this.isShowTitle = isShowTitle;
        this.Ivid = Ivid;
    }
}
