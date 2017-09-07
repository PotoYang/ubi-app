package com.chh.yinbao.service.account;

import com.chh.yinbao.Token;
import com.chh.yinbao.User;
import com.chh.yinbao.service.http.HttpCallBack;
import com.chh.yinbao.weixin.WXBaseInfo;

import java.util.Map;

/**
 * Created by potoyang on 2017/8/7.
 */

public interface AccountService {
    //登录
    void login(String mobile, String password, boolean isRememberPwd, HttpCallBack<Token> callBack);

    void loginOut(HttpCallBack<String> callBack);

    //发送注册短信验证码
    void sendRegisterSmsCode(String mobile, HttpCallBack<Object> callBack);

    //注册
    void register(String mobile, String password1, String password2, HttpCallBack<Object> callBack);

    //发送找回密码验证码
    void sendFindPwdSmsCode(String mobile, HttpCallBack<Object> callBack);

    //校验短信验证码
    void verifySmsCode(String mobile, String checkCode, HttpCallBack<Object> callBack);

    //通过短信重置密码
    void resetPwd(String mobile, String password1, String password2, HttpCallBack<Object> callBack);

    //登录成功后重置密码
    void updatePwd(String oldPwd, String newPwd, String newPwdAgain, HttpCallBack<Object> callBack);

    //获取用户信息
    void getUserInfo(String token, HttpCallBack<User> callBack);

    //绑定用户信息
    void bindInfo(User user, String name, String idCard, String carNo, String token, HttpCallBack<Object> callBack);

    //绑定用户微信信息
    void wxInfoBind(String unionId, String weixinNickName, String headImgUrl, HttpCallBack<Token> callBack);

    //获取用户微信基本信息
    void getWXBaseInfo(Map<String, String> map, HttpCallBack<WXBaseInfo> callBack);
}
