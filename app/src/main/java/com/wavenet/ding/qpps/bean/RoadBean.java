package com.wavenet.ding.qpps.bean;

import com.esri.arcgisruntime.geometry.Geometry;

import java.io.Serializable;

/**
 * Created by ding on 2018/8/15.
 */

public class RoadBean implements Serializable{
    public String NAME;
    public String QD_ROAD;
    public String ZD_ROAD;
    public String RANK;
    public String S_ROAD_ID;
    public boolean isSelect = false;
    public Geometry geometry;
}
