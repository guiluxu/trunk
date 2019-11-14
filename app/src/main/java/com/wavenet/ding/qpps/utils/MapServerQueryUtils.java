package com.wavenet.ding.qpps.utils;

import android.content.Context;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.wavenet.ding.qpps.activity.AdminSubmitActivity;

import java.util.Iterator;
import java.util.Map;

public class MapServerQueryUtils {
    private Context mContext;
    private static MapServerQueryUtils mapServerQueryUtils;
    private MapView mapView;
    private QueryParameters queryParameters;
    private Point point;
//    private static String serverUrl = "http://222.66.154.70:2084/arcgis/rest/services/QP1014/MapServer/139";
    public static String serverUrl =AppConfig.TownLayerurl;
    private static String jiezhen_Name = "";
    private static String jiezhen_Code = "";
    public MapServerQueryUtils(Context mContext) {
        this.mContext = mContext;
    }

    public static MapServerQueryUtils getInstance(Context mContext) {
        if (mapServerQueryUtils == null ) {
            mapServerQueryUtils = new MapServerQueryUtils(mContext);
        }
        return mapServerQueryUtils;
    }

    public void queryPointFromJieZhen(QueryParameters queryParameters){
        this.queryParameters = queryParameters;
        final ServiceFeatureTable mTableZDPFK = new ServiceFeatureTable(serverUrl);
        final ListenableFuture<FeatureQueryResult> future = mTableZDPFK
                .queryFeaturesAsync(queryParameters, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FeatureQueryResult result = future.get();
                            boolean queryResult = false;
                            for (Feature feature : result) {
                                queryResult = true;
                                Map<String, Object> mQuerryString = feature.getAttributes();
                                Geometry geometry = feature.getGeometry();
                                AdminSubmitActivity.S_JIEZHEN_NAME  = mQuerryString.get("NAME").toString();
                                AdminSubmitActivity.S_JIEZHEN_CODE  = mQuerryString.get("CODE").toString();
//                                tv_jiezhen.setText(AdminSubmitActivity.S_JIEZHEN_NAME);
                                queryCallBackInterface.querySucess(AdminSubmitActivity.S_JIEZHEN_NAME, AdminSubmitActivity.S_JIEZHEN_CODE);
                            }
                            if(!queryResult){
                                AdminSubmitActivity.S_JIEZHEN_CODE = "";
                                queryCallBackInterface.queryFail("当前位置不在青浦区");
                            }
                        } catch (Exception ep) {
                            ep.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    public void queryAllJieZhenData(){
        ServiceFeatureTable mServiceFeatureTable = new ServiceFeatureTable(serverUrl);
        FeatureLayer mFeatureLayer = new FeatureLayer(mServiceFeatureTable);
        mFeatureLayer.clearSelection();
        QueryParameters query = new QueryParameters();
        query.setReturnGeometry(true);
        query.setWhereClause("1=1");
        // call select features
        final ListenableFuture<FeatureQueryResult> future = mServiceFeatureTable.queryFeaturesAsync(query);
        // add done loading listener to fire when the selection returns
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                try {
                    // call get on the future to get the result
                    FeatureQueryResult result = future.get();
                    // check there are some results
                    Iterator<Feature> resultIterator = result.iterator();
                    for (Feature feature : result) {
                        Map<String, Object> mQuerryString = feature.getAttributes();
                        Geometry geometry = feature.getGeometry();
                        Point point = geometry.getExtent().getCenter();
                        double point_x = point.getX();
                        double point_y = point.getY();
                        //这里使用服务图层里面的街镇数据
                    }
                } catch (Exception e) {
                }
            }
        });
    }

    MapServerQueryUtils.QueryCallBackInterface queryCallBackInterface;
    public void setQueryCallBackInterface(MapServerQueryUtils.QueryCallBackInterface queryCallBackInterface){
        this.queryCallBackInterface = queryCallBackInterface;
    }

    public interface QueryCallBackInterface{
        public void queryFail(String content);
        public void querySucess(String Name, String Code);
    }


}
