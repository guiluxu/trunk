package com.wavenet.ding.qpps.api;


import com.wavenet.ding.qpps.bean.LoginBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by allen on 2016/12/26.
 */

public interface ApiService {

    //    @GET("v2/book/1220562")
//    Observable<BookBean> getBook();
//
//    @GET("v2/movie/top250")
//    Observable<Top250Bean> getTop250(@Query("count") int count);

    @POST()
    Observable<String> upXY(@Url String urlstr ,@Body Map<String, Object> map);

    ////登录
    @POST()
    Observable<LoginBean> userLogin(@Url String urlstr ,@QueryMap Map<String, String> map);

    //派单列表
    @GET()
    Observable<String> userXJTasklist(@Url String urlstr);

    //日常巡检开始
    @POST()
    Observable<String> userXJcTaskStart(@Url String url, @Body Map<Object, Object> map);

    //执行专项巡查任务
    @POST()
    Observable<String> userXJReportDataPai(@Url String url, @Body Map<Object, Object> map);   //执行专项巡查任务改变状态

    @PATCH()
    Observable<String> userXJReportDataPaistate(@Url String url, @Body Map<Object, Object> map);

    //拒绝退单专项巡查任务改变状态
    @PATCH()
    Observable<String> userXJReportDataPaireason1(@Url String url, @Body Map<Object, Object> map);

    //拒绝退单专项巡查任务改变状态
    @POST()
    Observable<String> userXJReportDataPaireason2(@Url String url, @Body Map<Object, Object> map);

    //拒绝派单
    @POST()
    Observable<String> refuse(@Url String url, @Body Map<String, Object> map);


    //巡检以及养护坐标传送接口
    @POST()
    Observable<String> userXJCoordinate(@Url String url, @Body Map<String, Object> map);

    //获得养护图片URL
    @GET()
    Observable<String> getPicUrl(@Url String url, @QueryMap Map<String, Object> map);

    //得到上报大类
    @GET()
    Observable<String> userXJgetClasbig(@Url String url);

    //得到上报小类
    @GET()
    Observable<String> userXJgetClassmall(@Url String url);

    //获取来源
    @GET()
    Observable<String> userXJgetSource(@Url String url);

    //上报数据
    @POST()
    Observable<String> userXJReportData(@Url String url, @Body Map<String, Object> map);


    @Headers({"Content-Type:application/json;charset=utf-8"})
    @POST()
    Observable<String> userXJReportData2(@Url String url, @Body Map<String, Object> map);

    //是否处置
    @POST()
    Observable<String> userXJIsDeal(@Url String url, @Body Map<String, Object> map);

    //事件详情
    @GET()
    Observable<String> userXJIsDealDetail(@Url String url);

    //派单任务开始执行
    @POST()
    Observable<String>  userXJpaidanstart(@Url String url, @Body Map<String, Object> map);
    //上报数据1
    @POST()
    Observable<String>  cancleTask(@Url String url, @Body Map<String, Object> map);

    //日常事件处置
    @POST
    Observable<String> userXJTaskDeal(@Url String url, @Body Map<String, String> map);

    //派单处置
    @POST
    Observable<String> userXJTaskDeal3(@Url String url, @Body Map<String, Object> map);


    //字典接口
    @GET()
    Observable<String> userXJgetDictionaries(@Url String url);

    //是否处置
    @GET()
    Observable<String> RequestXJISDeal(@Url String url);

    //处置详情
    @GET()
    Observable<String> RequestXJDealDetails(@Url String url);

    //处置详情图片
    @GET()
    Observable<String> RequestXJDealDetailsPhoto(@Url String url);

    //相关任务
    @GET()
    Observable<String> RequestXJRelevantTask(@Url String url);

    //日常巡检取消
    @POST()
    Observable<String> RequestCancleTask(@Url String url, @Body Map<Object, Object> map);

    //结束巡检
    @POST()
    Observable<String> RequestXJEndTask(@Url String url, @Body Map<Object, Object> map);

    @POST()
    Observable<String> RequestXJEndTask1(@Url String url, @Body Map<Object, Object> map);

    //巡检获得relyid
    @POST()
    Observable<String> userXJRelyid(@Url String url, @Body Map<String, Object> map);

    //地图服务id
    @GET()
    Observable<String> AdminGetObjectIds(@Url String urlstr);

    //地图服务details
    @GET()
    Observable<String> AdminGetObjectDetails(@Url String urlstr);

    // 版本更新
    @GET()
    Observable<String> getAPPverSionCode(@Url String url);

    //测试
    @POST
    Observable<String> ceshi(@Url String url, @Body  Map<String, Object> map);
 //结束养护
    @POST
    Observable<String> userFinishTask(@Url String url, @Body Map<String, Object> map);

    @GET()
    Observable<String> getCheck(@Url String urlstr);

    //管理员人版本员管理
    @GET
    Observable<String> requestPeople(@Url String urlstr);

    //管理员人版本员管理增加筛选字段
    @GET
    Observable<String> requestPeople2(@Url String urlstr);
    //监测实时数据
    @GET
    Observable<String> getJCData_id(@Url String urlstr);
   //监测历史图表数据
    @GET
    Observable<String> getJCData_his(@Url String urlstr);

    //修改密码
    @POST()
    Observable<String> changePassword(@Url String url,@Body Map<String, Object> map);

    @GET()
    Observable<String> YhContinue(@Url String url);


    @POST()
    Observable<String> mPostUrlMap(@Url String url, @Body Map<String, Object> map);

    @GET()
    Observable<String> mGetUrlMap(@Url String url, @Body Map<String, Object> map);
  @GET()
    Observable<String> requestTrack(@Url String url);

    @GET()
    Observable<String> mGetUrl(@Url String url);

    @GET()
    Observable<String> mMapbase(@Url String urlstr);

    //获取巡查人员详细信息，头像
    @Headers({"Accept:application/json"})
    @GET
    Observable<String> getAdminHeader(@Url String urlstr);

    @POST()
    Observable<String> subMitAdminMessage(@Url String url, @Body Map<String, Object> map);

    @POST()
    Observable<String> pushMessage(@Url String url);

    @POST()
    Observable<String> addSearchHis(@Url String url, @Body Map<String, Object> map);

    @GET()
    Observable<String> getSearchList(@Url String url);

    @POST
    Observable<String> clearHis(@Url String url, @Body Map<String, Object> params);
}
