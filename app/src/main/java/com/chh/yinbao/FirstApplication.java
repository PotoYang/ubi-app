package com.chh.yinbao;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chh.yinbao.config.Config;
import com.chh.yinbao.utils.FileUtils;
import com.chh.yinbao.utils.LogUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by potoyang on 2017/8/7.
 */

public class FirstApplication extends Application {

    public static IWXAPI iwxapi;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
//        ARouter.init(this);
    }

    private void init() {
        if (Config.isDebug) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        LogUtils.setFileLogPath(FileUtils.createAppDir(getApplicationContext()));
        ARouter.init(this);

        //App向微信注册
        iwxapi = WXAPIFactory.createWXAPI(this, Config.WX_APP_ID, false);
        iwxapi.registerApp(Config.WX_APP_ID);
    }
}
