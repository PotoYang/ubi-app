package com.chh.yinbao.service.account;

import com.chh.yinbao.weixin.WXBaseInfo;
import com.chh.yinbao.weixin.WXUserInfo;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by potoyang on 2017/8/23.
 */

public interface WeixinApi {
    @GET("sns/oauth2/access_token")
    Observable<WXBaseInfo> getWXBaseInfo(@QueryMap Map<String, String> map);

    @GET("sns/userinfo")
    Observable<WXUserInfo> getWXUserInfo(@QueryMap Map<String, String> map);
}
