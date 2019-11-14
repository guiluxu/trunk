package com.wavenet.ding.qpps.utils;

public class CoordinateUtil {

    //经纬度转墨卡托
    public static double[] lonLat2Mercator(double lon, double lat) {
        double[] result = new double[2];
        double x = lon * 20037508.34 / 180;
        double y = Math.log(Math.tan((90 + lat) * Math.PI / 360)) / (Math.PI / 180);
        y = y * 20037508.34 / 180;
        result[0] = x;
        result[1] = y;
        return result;
    }

    //经度转墨卡托
    public static double lon2Mercator(double lon) {
        return lon * 20037508.34 / 180;
    }

    //纬度转墨卡托
    public static double lat2Mercator(double lat) {
        double y = Math.log(Math.tan((90 + lat) * Math.PI / 360)) / (Math.PI / 180);
        return y * 20037508.34 / 180;
    }

    //墨卡托转经纬度
    public static double[] Mercator2lonLat(double lon, double lat) {
        double[] result = new double[2];
        double x = lon / 20037508.34 * 180;
        double y = lat / 20037508.34 * 180;
        y = 180 / Math.PI * (2 * Math.atan(Math.exp(y * Math.PI / 180)) - Math.PI / 2);
        result[0] = x;
        result[1] = y;
        return result;
    }

    //墨卡托经度转换
    public static double Mercator2lon(double lon) {
        return lon / 20037508.34 * 180;
    }

    //墨卡托纬度转换
    public static double Mercator2lat(double lat) {
        double y = lat / 20037508.34 * 180;
        return 180 / Math.PI * (2 * Math.atan(Math.exp(y * Math.PI / 180)) - Math.PI / 2);
    }
}