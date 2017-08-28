package com.chh.yinbao.config;

/**
 * Created by potoyang on 2017/8/7.
 */

public class Config {

    /**
     * 是否在调试状态，正式发布APP时改为false
     */
    public final static boolean isDebug = true;

    //是否加密
    public final static boolean isEncrypt = true;
    //应用通知ID
    public final static int NOTIFY_ID = Config.SHARED_APP_DIR_NAME.hashCode();

    public final static String CHARSET = "UTF-8";

    /**
     * 全局应用的SharedPreferences名称
     */
    public static final String SHARED_APP_PREFERENCE_NAME = "com_chh_potoyang_first_sp";

    /**
     * 全局应用的文件夹名称
     */
    public static final String SHARED_APP_DIR_NAME = "com_chh_potoyang_first_dir";


    /**
     * 数据库名称
     */
    public static final String APP_DB_NAME = "com_chh_potoyang_first.db";

    /**
     * 数据库版本
     */
    public static final int APP_DB_VERSION = 1;

    public static String APP_APK_BASE_NAME = "first";

    public static String baidu_map_ak = "HAARfj7QQlIdHmSsL8X509A5HR2Fs5oZ";
    public static String baidu_map_mcode = "F0:24:A7:73:75:46:3A:C5:0B:1B:E0:F2:8F:C7:B4:C3:21:DF:04:57;com.ubichina.obd";//开发板
    //    public static String baidu_map_mcode="46:10:EE:08:41:7B:D1:DE:28:B9:56:94:4D:37:97:C8:DF:1E:8B:30;com.ubichina.obd";//发布版
    public static String company_tel = "400-690-5901";
    /**
     * 默认为北京 来广营的坐标
     */
    public static final double default_longitude = 116.468084;
    public static final double default_latitude = 40.028289;

    /**
     * 微信的AppID和AppSecret
     */
    public static final String WX_APP_ID = "wx276b5bb127ed8869";
    public static final String WX_APP_SECRET= "7ecf26201c244f7336e0b1c1fd9cb00b";
}
