package com.chh.yinbao.config;

import android.content.Context;

import com.chh.yinbao.utils.ACache;
import com.chh.yinbao.utils.SharedPreferencesUtils;

import java.io.Serializable;

/**
 * Created by potoyang on 2017/8/7.
 */

public class UserData implements Serializable {

    // 是否是第一次启动
    public final static String IS_FIRST_START = "is_first_start";

    //token
    public final static String TOKEN = "token";

    //----------------下载相关----------//
    public final static String VERSION_JSON = "version_json";

    // 最后一次获取下载的时间
    public final static String LAST_TIME_GET_VERSION_INFO = "last_time_get_version_info";

    //-----------------通知相关----------------//
    public static final int NOTIFY_ID = Config.SHARED_APP_DIR_NAME.hashCode();

    //-----------------登陆后的参数----------------//
    public final static String ACCOUNT_ID = "accountId"; //用户ID
    public final static String LOGIN_SEQ = "loginSeq"; //用户登录序列号

    //鹰眼entity_name前缀
    public final static String entity_name = "baiduEntityPrefix";

    //----------------------------------------------//
    public final static String USER_PWD = "user_pwd";//本地保存的密码
    public final static String USER_NAME = "user_name";//本地保存的用户名

    public final static String GET_CHECKCODE_TIME = "get_checkcode_time";//获取短信验证码的时间

    public final static String DEFAULT_VEHICLE_ID = "default_vehicle_id";//默认的车辆ID
    public final static String VEHICLE_LIST = "vehicle_list";//默认的车辆ID

    public final static String VEHICLE_TRACE_SWITCH = "vehicle_trace_switch";//行驶轨迹开关

    /**
     * 初始化登陆数据
     *
     * @param context
     * @throws Exception
     */
    public static void initAppData(Context context) {
        cleanAppData(context);
    }

    public static void cleanAppData(Context context) {
        ACache.get(context).clear();
    }

    /**
     * 检测用户是否已经登录
     *
     * @param context
     * @return
     */
    public static boolean isLogin(Context context) {
        return false;
    }

    /**
     * 获取登录的account_id
     *
     * @param context
     * @return
     */
    public static String getAccountId(Context context) {
        return SharedPreferencesUtils.getValue(context, UserData.ACCOUNT_ID);
    }

    public static String getToken(Context context) {
        return SharedPreferencesUtils.getValue(context, UserData.TOKEN);
    }

    public static String getDefaultVehicleId(Context context) {
        return SharedPreferencesUtils.getValue(context, UserData.DEFAULT_VEHICLE_ID);
    }

    public static String getLoginSeq(Context context) {
        return SharedPreferencesUtils.getValue(context, LOGIN_SEQ);
    }

    public static String getUserName(Context context) {
        return SharedPreferencesUtils.getDecryptValue(context, UserData.USER_NAME);
    }

    /**
     * 清除登录的用户数据
     *
     * @param context
     */
    public static void cleanUserData(Context context) {
        //SharedPreferencesUtils.removeValue(context, UserData.USER_NAME);
        SharedPreferencesUtils.removeValue(context, UserData.USER_PWD);
        SharedPreferencesUtils.removeValue(context, UserData.TOKEN);
    }

    /**
     * 清除搜索的数据
     *
     * @param context
     */
    public static void cleanAllData(Context context) {
        cleanUserData(context);
    }

}
