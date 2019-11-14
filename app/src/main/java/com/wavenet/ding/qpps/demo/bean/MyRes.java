package com.wavenet.ding.qpps.demo.bean;

/**
 * Created by ding on 2018/6/4.
 */

public class MyRes {
    //生成URL
    public static String shengUrl = "http://118.25.116.247/";

    //登录接口
    public static String loginUrl = "mdos/mdos-service-auth/service/auth/appLogin";
    //登出接口
    public static String logoutUrl = "mdos/mdos-service-auth/service/auth/logout";
    //微信会话
    public static String wxnUrl = "mdos/mdos-service-weixin/resource/wx/message/message";
    //微信会话详情
    public static String wxnContentUrl = "mdos/mdos-service-weixin/resource/wx/message/message/detail";
    //测试
    public static String testUrl = "mdos-service-auth/service/auth/test";
    //请求头
    public static String Head = "Head";
    //微信会话文本信息发送
    public static String wxSendMessage = "mdos/mdos-service-weixin/resource/wx/message/receiveMessage";
    //培训的BaseUrl
    public static String BaseUrl = "http://118.126.71.122:8081/";
    //培训的课程
    public static String TrainingUrl = "Handlers/LearningCenter/SourceHandler.ashx?mod=";
    //培训的必修课程
    public static String TrainingNeedUrl = "Handlers/LearningCenter/SourceHandler.ashx?mod=";
    //培训的详情
    public static String TainingContentUrl = "Handlers/LearningCenter/SourceHandler.ashx?mod=sourcedetail";
    //培训的表明已学的接口
    public static String TrainingLearningrecord = "Handlers/LearningCenter/SourceHandler.ashx?mod=learningrecord";
    //试驾预约baseurl
    public static String BaseUrl1 = "http://192.168.1.244:18211/";
    //查询视频的浏览记录
    public static String TainingLearninghistory = "Handlers/LearningCenter/SourceHandler.ashx?mod=learninghistory";
    //试驾预约列表
    public static String testDriveUrl = "mdos/hmdos-service-dealer/service/testdriver/query/info";
    //试驾预约详情
    public static String testDriveContentUrl = "mdos/hmdos-service-dealer/service/testdriver/query/detail";
    //取消、确认、设置无效试驾预约
    public static String testDriveContentChooseUrl = "mdos/hmdos-service-dealer/service/testdriver/modify";
    //搜索通过全局搜索接口，
    public static String SearchTaining = "Handlers/LearningCenter/SourceHandler.ashx?mod=search";
    //banner接口，
    public static String banner = "Handlers/Common/CommonHandler.ashx?mod=getDisplayInfo";
    //评论列表接口
    public static String TrainingList = "/Handlers/LearningCenter/SourceHandler.ashx?mod=commentdetail";
    //发表评论
    public static String comment = "/Handlers/Common/CommonHandler.ashx?mod=comment";
    //发表点赞
    public static String praise = "/Handlers/Common/CommonHandler.ashx?mod=praise";

    //取消点赞
    public static String cancelpraise = "Handlers/Common/CommonHandler.ashx?mod=cancelpraise";

}
