package com.chh.yinbao.service.account;

import com.chh.yinbao.Token;
import com.chh.yinbao.TokenList;
import com.chh.yinbao.User;
import com.chh.yinbao.config.MyURL;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by potoyang on 2017/8/7.
 */

public interface AccountApi {

    @POST(MyURL.login)
    Observable<Token> userLogin(@QueryMap Map<String, String> map);

    @POST(MyURL.register)
    Observable<Object> register(@QueryMap Map<String, String> map);

    //刷新token
    @POST(MyURL.refreshToken)
    Observable<TokenList> refreshToken(@QueryMap Map<String, String> map);

    @GET(MyURL.getUserInfo)
    Observable<User> getUserInfo(@QueryMap Map<String, String> map);

    @GET(MyURL.sendSmsCode)
    Observable<Object> sendSmsCode(@Query("mobile") String mobile, @Query("type") int type);

    @GET(MyURL.verifySmsCode)
    Observable<Object> verifySmsCode(@QueryMap Map<String, String> map);

    @POST(MyURL.resetPwd)
    Observable<Object> resetPwd(@QueryMap Map<String, String> map);

    @POST(MyURL.updatePwd)
    Observable<Object> updatePwd(@QueryMap Map<String, String> map);

    @POST(MyURL.bindInfo)
    Observable<Object> bindInfo(@Body User user, @Query("token") String token);
}
