package com.chh.yinbao.config;

/**
 * Created by potoyang on 2017/8/7.
 */

public class MyURL {

    /**
     * 主机地址
     */
//    public final static String BASE_HOST = "http://test.ubi-china.com:8081/";
//    public final static String ISVR_HOST = "hujiaApi/";
//    public final static String BASE_HOST = "http://mo.ubi-china.com/";
//    public final static String ISVR_HOST = "";
//    public final static String BASE_HOST = "http://42.51.32.181/";
    public final static String BASE_HOST = "http://yinbao.senit.xyz/";
    public final static String ISVR_HOST = "api/";
//    http://192.168.0.106:8088/login/view


    //注册的服务条款链接
    public final static String registerTerms = BASE_HOST + "/share/licenseAgreement/hj.html";
    //    http://www.ubi-china.com/share/licenseAgreement/hj.html
    //登录
    public final static String login = "member/login";
    //注册
    public final static String updatePwd = "memberInfo/updatePassword";
    //获取用户信息
    public final static String getUserInfo = "member/info";
    //绑定用户信息
    public final static String bindInfo = "member/bindinfo";
    //刷新token
    public final static String refreshToken = "user/refreshToken";
    //发送短信验证码
    public final static String sendSmsCode = "sms/getCode";
    //校验短信验证码
    public final static String verifySmsCode = "sms/checkCode";
    //重置密码
    public final static String resetPwd = "memberInfo/findPassword";
    //用户注册
    public final static String register = "member/mobile/regist";


    //2.8.	获取车辆详细信息
    public final static String getVehicleInfo = "vehicle/getVehicleInfo";
    //2.9.	获取车辆实时GPS列表
    public final static String getCurrentGpsList = "vehicle/getCurrentGpsList";
    //2.10.	获取车辆最近一次信息
    public final static String getLastTrip = "vehicle/getLastTrip";
    //2.12.	根据行程ID获取行程信息
    public final static String getTripInfoById = "vehicle/getTripInfoById";
    //2.12.	获取车辆行程记录轨迹
    public final static String getTripDetailList = "vehicle/getTripDetailList";
    //2.14.	获取历史轨迹列表
    public final static String getHistoricalTrackList = "vehicle/getHistoricalTrackList";
    //2.15.	获取首页数据
    public final static String getHomeData = "vehicle/getHomeData";
    //2.16.	报警监控
    public final static String getVehicleWarning = "vehicle/getVehicleWarning";
    //2.17.	获取报警列表
    public final static String getWarningList = "vehicle/getWarningList";
    //2.18.	获取报警详细
    public final static String getWarningInfo = "vehicle/getWarningInfo";
    //2.19.	报警状态更改
    public final static String updateWarningStatus = "vehicle/updateWarningStatus";
    //2.20.	车务提醒
    public final static String getCarReminder = "vehicle/getCarReminder";
    //2.21.	车务状态更改
    public final static String updateCarReminder = "vehicle/updateCarReminder";
    //2.22.	根据车务提醒类型获取列表
    public final static String getReminderListByType = "vehicle/getReminderListByType";
    //2.23.	获取提醒详情
    public final static String getReminderInfo = "vehicle/getReminderInfo";
    //2.26.	统计报表
    public final static String getStatisticalReport = "vehicle/getStatisticalReport";
    //2.27.	升级
    public final static String upgrade = "app/upgrade";

    //2.27.	请求上传行程轨迹到地图
    public final static String uploadTripTrackToMap = "vehicle/uploadTripTrackToMap";

    //2.24.	获取报警设置列表
    public final static String getWarningSettingList = "vehicle/getWarningSettingList";
    //2.25.	报警设置状态更改
    public final static String updateWarningSettings = "vehicle/updateWarningSettings";
    //百度获取静态图的URL
    public final static String baiduMapImageURL = "http://api.map.baidu.com/staticimage/v2?";
    //百度获取地址位置信息的URL
    public final static String baiduMapURL = "http://api.map.baidu.com/";
    public final static String baiduMapGeocoderURL = "geocoder/v2/";
    //2.9.	获取用户绑定车辆信息
    public final static String getUserVehicleInfo = "vehicle/getUserVehicleInfo";
    //2.25.	激活盒子
    public final static String bindDevice = "vehicle/bindDevice";
    //2.28.	获取车辆常量数据
    public final static String getVehicleConst = "vehicle/getVehicleConst";


    //2.12.	根据指定日期获取统计数据（周数据/月数据）
    public final static String getStatisticsByData = "vehicle/getStatisticsByData";
    //2.22.	获取我的活动
    public final static String getActivityList = "user/getActivityList";
    //2.24.	我的钱包
    public final static String getMyWallet = "user/getMyWallet";
    //2.26.	获取我的消息
    public final static String getMessageList = "user/getMessageList";
    //保险和违章查询 1 车险服务，2 违章查询
    public final static String serviceUrl = BASE_HOST + ISVR_HOST + "app/service?type=";

    //2.26.	获取保险公司信息
    public final static String getCompanyInfo = "user/reportUserEvent";

    //2.23.	问题反馈
    public final static String feedback = "user/feedback";
    //历史体检
    public final static String historyCheck = BASE_HOST + "app/appShare!car_appPhysicalShareHistory.do?vehicleId=";
    //2.34.	获取天气
    public final static String getWeather = "app/getWeather";
    //2.33.	记录行程轨迹
    public final static String recordTrack = "vehicle/recordTrack";


}
