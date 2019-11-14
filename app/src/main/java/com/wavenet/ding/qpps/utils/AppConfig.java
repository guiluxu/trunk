package com.wavenet.ding.qpps.utils;

/**
 * Created by Administrator on 2017/5/10.
 */

    public class AppConfig {
//    public static    String  BeasUrl="http://222.66.154.70:";
    public static    String  BeasUrl="http://116.247.123.54:";
    public static    String  BeasUrl1="http://172.18.0.148:1995";
    //管网图层
    public static final String GuanWangUrl = BeasUrl+"2084/arcgis/rest/services/qpmobile/MapServer";
    public static final String JCUrl = BeasUrl+"2084/arcgis/rest/services/QPSYMapDataLayer/MapServer";
    //监测图层
//    public static final String JCUrl = "http://172.18.1.215:6080/arcgis/rest/services/QPSYMapDataLayer/MapServer";
    public final static int PSJ = 42;   //下标 3  0  1
    public final static int PSGD = 14;//下标2 0 2//
    public final static int PSGDJ1 = 42;//下标3 0 1//
    public final static int PSGDJ2 = 43;//下标3 0 2//
    public final static int PSGDJ3 = 44;//下标3 0 3//
    public final static int PSGDWS = 24;//下标2 1 2
    public final static int PSGDWSJ1 = 47;//下标3 1  1
    public final static int PSGDWSJ2 = 48;//下标3  1  2
    public final static int PFK = 63;//下标10
    public final static int PSBZ = 7;//下标1
    public final static int WSCLC = 53;//下标4
    public final static int ZDPFK = 54;//下标5
    public final static int PSH = 67;//下标12
    public final static int JCsw = 14;//下标12
    public final static String JCsws = "Wellcode";//id
    public final static String JCswsname = "水位监测";//id
    public final static int JCclc = 3;//下标12
    public final static String JCclcs = "S_FACT_ID";//id
    public final static String JCclcsname = "污水处理厂";//id
    public final static int JCbz = 10;//下标12
    public final static String JCbzs = "S_XTBM_SYS";//id
    public final static String JCbzsname = "污水泵站";//id
//    public final static int IndexPSJ = 39;   //下标 3  0  1
//    public final static int IndexPSGD = 8;//下标2 0 2//
//    public final static int IndexPSGDWS = 8001;//下标2 1 2
//    public final static int IndexPFK = 63;//下标10
//    public final static int IndexPSBZ = 7;//下标1
//    public final static int IndexWSCLC = 53;//下标4
//    public final static int IndexZDPFK = 54;//下标5
//    public final static int IndexPSH = 55;//下标12
    public final static int IndexPSJ = 3;   //下标 3  0  1
    public final static int IndexPSGD = 2001;//下标2 0 2//
    public final static int IndexPSGDWS = 2002;//下标2 1 2
    public final static int IndexPFK = 10;//下标10
    public final static int IndexPSBZ = 1;//下标1
    public final static int IndexWSCLC = 4;//下标4
    public final static int IndexZDPFK = 5;//下标5
    public final static int IndexPSH = 12;//下标12
    public final static int IndexJCsw = 7;//
    public final static int IndexJCclc = 1;//
    public final static int IndexJCbz = 3;//
    // public static  String GuanWangUrl=  SPUtils.get("url5", "http://222.66.154.70:2084") + "/arcgis/rest/services/PSSSServices0824/MapServer";
    public static String mainArcGISMapImageLayerURL = BeasUrl+   "2084/arcgis/rest/services/QPRoadPipe0818/MapServer";
    //官网井详情的地址html
    private static String Detailsurl=BeasUrl+"2088/QP_H5/module/home/index.html#/";

    public static String DetailsurlPSJ = Detailsurl+"Tab_DRAINMANHOLE?s_xtbm=%s";
    public static String DetailsurlPSGD = Detailsurl+"Tab_drainpipe?s_xtbm=%s";
    public static String DetailsurlPFK = Detailsurl+"Tab_pfk?s_xtbm=%s";
    public static String DetailsurlPSBZ = Detailsurl+"Tab_bz?s_xtbm=%s";
    public static String DetailsurlWSCLC = Detailsurl+"Tab_wsc?S_FACT_ID=%s";
    public static String DetailsurlZDPFK = Detailsurl+"Tab_pfkzd?s_xtbm=%s";
    public static String DetailsurlPSH = Detailsurl+"Tab_pfh?wb_id=%s";
//官网井纠错html
public static String JCurlPSJ = BeasUrl+"2088/QP_H5/module/home/index.html#/JC_PSJ?S_MANHOLE_ID=%s";
    //搜索要展示的字段
    public static String DetailsSeachPSJ = "OBJECTID%2CS_MANHOLE_NAME_ROAD%2CN_MANHOLE_STATE%2CN_WellForm%2CN_MANHOLE_DEPTH%2CN_MANHOLE_MATERIAL%2CN_MANHOLE_LENGTH%2CN_MANHOLE_ALT_GRD%2CS_ManageUnit%2CN_MANHOLE_GRADE%2CN_MANHOLE_TYPE%2CS_XTBM%2CS_MANHOLE_ID";
    public static String DetailsSeachPSGD = "OBJECTID%2CS_DRAI_PIPE_NAME_ROAD%2CN_DRAI_PIPE_GRADE%2CN_DRAI_PIPE_TYPE%2CS_DRAI_PIPE_BROAD_NAME%2CS_DRAI_PIPE_EROAD_NAME%2CN_DRAI_PIPE_STYLE%2CN_DRAI_PIPE_D1%2CN_DRAI_PIPE_LENGTH%2CN_DRAI_PIPE_PALT_BEG%2CN_DRAI_PIPE_PALT_END%2CS_ManageUnit%2CS_XTBM";
    public static String DetailsSeachPFK = "OBJECTID%2CS_MANHOLE_NAME_ROAD%2CN_MANHOLE_GRADE%2CN_MANHOLE_TYPE%2CN_MANHOLE_STATE%2CN_WellForm%2CN_MANHOLE_LENGTH%2CN_MANHOLE_ALT_GRD%2CN_MANHOLE_DEPTH%2CS_XTBM";
    public static String DetailsSeachPSBZ = "OBJECTID%2CS_DRAI_PUMP_NAME%2CN_DRAI_PUMP_TYPE%2CN_DRAI_PUMP_TYPE_FEAT%2CS_DRAI_PUMP_ADD%2CS_DRAI_PUMP_PHONE%2CS_DRAI_PUMP_COM_MNG%2CS_XTBM";
    public static String DetailsSeachWSCLC = "OBJECTID%2CS_FACT_NAME%2CS_FACT_ADD%2CS_FACT_PHONE%2CS_FACT_POST_CODE%2CN_FACT_CAP_DSN%2CS_FACT_METHOD_TREAT%2CS_FACT_LET_GRADE%2CS_FACT_ID";
    public static String DetailsSeachZDPFK = "OBJECTID%2CS_ManageUnit%2CS_pairufangxiang%2CS_SYS_NAME%2CS_OutletNAME%2CS_OUTLETID";
    public static String DetailsSeachPSH = "OBJECTID%2CS_NAME%2CS_ADDRESS%2CS_TYPE%2CS_AREA%2CS_TOWN%2CQPPSH_TABLE_ID";





    public static String Filterurlstr="%s/%s/query?where=%s%s";
    //巡检，养护版本的地图设施查询用登陆返回的用townid
    public static String Filterurlstrstart1  ="upper(S_town)='%s'and(%s)LIKE'%s%s%s'";
    //管理员版本的地图设施查询用登陆返回的des
    public static String Filterurlstrstart1Admin  ="upper(S_town)in (%s) and(%s)LIKE'%s%s%s'";
    public static String Filterurlstrstart2  ="upper(%s)LIKE'%s%s%s'";
    public static String Filterurlstrend="&returnGeometry=false&returnIdsOnly=true&returnCountOnly=false&returnZ=false&returnM=false&returnDistinctValues=false&returnTrueCurves=false&f=pjson";



    public static String Updatehistor = BeasUrl+"2088/QP_H5/module/home/index.html#/VersionLog";
    //技术支持H5
public static String Jssupport = BeasUrl+"2088/QP_H5/module/home/index.html#/TechSupport";

//养护记录H5地址
   public static String YHHistory=BeasUrl+"2088/QP_H5/module/home/index.html#/YHJL_List_VYH?personname=%s";
   //养护道路地图服务地址
   public static String YHImageRoadurl=BeasUrl+"2084/arcgis/rest/services/DLZXXService/MapServer";
   public static String YHRoadurl=YHImageRoadurl+"/0";
    //巡查轨迹地图服务
   public static String XJRoadImageLayerurl=BeasUrl+"2084/arcgis/rest/services/QP/S_TRAJECTORYLINE_XC/MapServer";//公司QP
//   public static String XJRoadImageLayerurl=BeasUrl+"2084/arcgis/rest/services/qp/S_TRAJECTORYLINE_XC/MapServer";//客户qp
    //养护轨迹地图服务
   public static String YHRoadImageLayerurl=BeasUrl+"2084/arcgis/rest/services/QP/S_TRAJECTORYLINE_YH/MapServer";//公司QP
//   public static String YHRoadImageLayerurl=BeasUrl+"2084/arcgis/rest/services/qp/S_TRAJECTORYLINE_YH/MapServer";//客户qp
    //街镇地图服务
   public static String TownLayerurl=BeasUrl+"2084/arcgis/rest/services/QPTownService/MapServer/0";
    //继续养护轨迹获取接口
    public static  String ContinueYHurl= BeasUrl+"2056/api/CuringRecode/GetTrajectoryByReCodeId?s_recode_id=%s";
    //调度员版本带派单H5
    public static  String DDYdpdurl= BeasUrl+"2088/QP_H5/module/home/index.html#/PaiDan_List?personname=%s&truename=%s&townname=%s";
    //巡检记录H5地址
    public static String XJHistory=BeasUrl+"2088/QP_H5/module/home/index.html#/XCSJTab?personname=%s";
    //轨迹播放接口
    public static String TrackUrl=BeasUrl+"2056/api/T_TRAJECTORY/GetTrajectoryByMan?S_Record_Id=%s";



    //管理员版本人员详情H5事件详情
    public static String Adminshijian=BeasUrl+"2088/QP_H5/module/home/index.html#/SJ_List?company=";
    //管理员版本人员详情H5巡查详情
    public static String Adminxuncha=BeasUrl+"2088/QP_H5/module/home/index.html#/XC_List?company=";
    //管理员版本人员详情H5巡查详情
    public static String Adminchuzhi=BeasUrl+"2088/QP_H5/module/home/index.html#/CZ_List?company=";
    //管理员版本人员详情H5巡查详情
    public static String Adminyanghu=BeasUrl+"2088/QP_H5/module/home/index.html#/YHJL_List_VYH?flag=yhadmin&personname=";
    //管理员版本巡查模块
    public static String AdminXCFrame=BeasUrl+"2088/QP_H5/module/home/index.html#/XCFrame";
    //管理员版本养护模块
    public static String AdminYHFrame=BeasUrl+"2088/QP_H5/module/home/index.html#/YHFrame";
    //管理员版本统计模块
    public static String AdminTJFrame=BeasUrl+"2088/QP_H5/module/home/index.html#/SY";



//    轨迹上传接口
public static String Guiijurl=BeasUrl+"2056/api/T_TRAJECTORY/AddTrajectory";

    public static String  ceshiurl="http://218.1.102.109:6080/arcgis/rest/services/OneMap/TH_BXZ/MapServer";
    //监测数据查询泵站实时数据
    public static String  jCurlbz=BeasUrl+"2056/api/Pump/GetPump?PumpId=";
    //监测数据查询污水厂实时数据
    public static String  jCurlclc=BeasUrl+"2056/api/Pump/GetWSC?Fact_Id=";
    //监测数据获取管网水位
    public static String  jCurlsw=BeasUrl+"2056/api/Pump/GetGW?WellCode=";
    //监测数据查询泵站历史图表数据
    public static String  jCurlbzhis=BeasUrl+"2056/api/Pump/GetPumpHis?PumpId=%s&T_Stime=%s";
    //监测数据查询污水厂历史图表数据
    public static String  jCurlclchis=BeasUrl+"2056/api/Pump/GetWSCHis?Fact_Id=%s&DTime=%s";
    //监测数据获取管网水位历史图表
    public static String  jCurlswhis=BeasUrl+"2056/api/Pump/GetGWHis?WellCode=%s&DTime=%s";
}


