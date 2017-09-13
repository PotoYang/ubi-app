package com.chh.yinbao.service.account;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chh.yinbao.Token;
import com.chh.yinbao.User;
import com.chh.yinbao.config.UserData;
import com.chh.yinbao.service.BaseImp;
import com.chh.yinbao.service.http.HttpCallBack;
import com.chh.yinbao.service.http.RetrofitEngine;
import com.chh.yinbao.utils.InputVerifyUtils;
import com.chh.yinbao.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Function;

/**
 * Created by potoyang on 2017/8/7.
 */

public class AccountServiceImpl extends BaseImp implements AccountService {
    private AccountApi accountApi;
    private Context context;

    public AccountServiceImpl(Context context) {
        this.context = context;
//        accountApi = new RetrofitServiceProxy(null, new RetrofitProxyHandler(this.context)).getProxy(AccountApi.class);
        accountApi = RetrofitEngine.getInstance().create(AccountApi.class);
//        如果不需要经过自定义的过滤器
    }

    @Override
    public void login(final String mobile, final String password, final boolean isRememberPwd, HttpCallBack<Token> callBack) {
        if (TextUtils.isEmpty(mobile)) {
            callBack.onError(-1, "用户名不允许为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            callBack.onError(-1, "密码不允许为空");
            return;
        }
        doRequest(accountApi.userLogin(mobile, password)
                .map(new Function<Token, Token>() {
                    @Override
                    public Token apply(@Nullable Token token) throws Exception {
                        SharedPreferencesUtils.addEncryptValue(context, UserData.USER_NAME, mobile);
                        SharedPreferencesUtils.addValue(context, UserData.TOKEN, token.getToken());
                        if (isRememberPwd) {
                            SharedPreferencesUtils.addEncryptValue(context, UserData.USER_PWD, password);
                        }
                        return token;
                    }
                }), callBack);
    }

    @Override
    public void loginOut(HttpCallBack<String> callBack) {
        SharedPreferencesUtils.removeValue(context, UserData.USER_PWD);
        SharedPreferencesUtils.removeValue(context, UserData.TOKEN);
        SharedPreferencesUtils.removeValue(context, UserData.ACCOUNT_ID);
        UserData.cleanUserData(context);
        UserData.cleanAppData(context);
        if (callBack != null) {
            callBack.onSuccess(null);
        }
    }

    @Override
    public void sendRegisterSmsCode(String mobile, HttpCallBack<Object> callBack) {
        if (TextUtils.isEmpty(mobile) || !InputVerifyUtils.isMobileNO(mobile)) {
            callBack.onError(-1, "手机号不正确");
            return;
        }
        doRequest(accountApi.sendSmsCode(mobile, 1), callBack);
    }

    @Override
    public void register(String mobile, String password1, String password2, HttpCallBack<Object> callBack) {
        if (TextUtils.isEmpty(mobile) || !InputVerifyUtils.isMobileNO(mobile)) {
            callBack.onError(-1, "手机号不正确");
            return;
        }
        if (!InputVerifyUtils.checkPwd(password1) && !InputVerifyUtils.checkPwd(password2)) {
            callBack.onError(-1, "密码只能是大写字母、小写字母和数字构成");
            return;
        }
        if (!password1.equals(password2)) {
            callBack.onError(-1, "两次密码不一致，请重新输入");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("password", password1);
        doRequest(accountApi.register(map), callBack);
    }

    @Override
    public void sendFindPwdSmsCode(String mobile, HttpCallBack<Object> callBack) {
        if (TextUtils.isEmpty(mobile) || !InputVerifyUtils.isMobileNO(mobile)) {
            callBack.onError(-1, "手机号不正确");
            return;
        }
        doRequest(accountApi.sendSmsCode(mobile, 2), callBack);
    }

    @Override
    public void verifySmsCode(String mobile, String checkCode, HttpCallBack<Object> callBack) {
        if (TextUtils.isEmpty(mobile) || !InputVerifyUtils.isMobileNO(mobile)) {
            callBack.onError(-1, "手机号不正确");
            return;
        }
        if (TextUtils.isEmpty(checkCode) || (checkCode.length() < 4 || checkCode.length() > 8)) {
            callBack.onError(-1, "验证码输入不正确");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("code", checkCode);
        doRequest(accountApi.verifySmsCode(map), callBack);
    }

    @Override
    public void resetPwd(String mobile, String password1, String password2, HttpCallBack<Object> callBack) {
        if (TextUtils.isEmpty(password1) || TextUtils.isEmpty(password2) || password1.length() < 6 || password1.length() > 20) {
            callBack.onError(-1, "请输入6-20位字符的新密码");
            return;
        }
        if (!InputVerifyUtils.checkPwd(password1)) {
            callBack.onError(-1, "密码只能是大写字母、小写字母和数字构成");
            return;
        }
        if (!password1.equals(password2)) {
            callBack.onError(-1, "两次输入密码不一致");
        }
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("newPassword", password1);
        doRequest(accountApi.resetPwd(map), callBack);
    }

    @Override
    public void updatePwd(String oldPwd, String newPwd, String newPwdAgain, HttpCallBack<Object> callBack) {
        if (TextUtils.isEmpty(oldPwd)) {
            callBack.onError(-1, "请输入原密码");
            return;
        }
        if (TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(newPwdAgain) || newPwd.length() < 6 || newPwd.length() > 20) {
            callBack.onError(-1, "请输入6-20位字符的新密码");
            return;
        }
        if (!InputVerifyUtils.checkPwd(newPwd)) {
            callBack.onError(-1, "密码只能是大写字母、小写字母和数字构成");
            return;
        }
        if (!newPwd.equals(newPwdAgain)) {
            callBack.onError(-1, "两次输入的密码不一致");
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("oldPassword", oldPwd);
        map.put("newPassword", newPwd);
        map.put("token", UserData.getToken(context));
        doRequest(accountApi.updatePwd(map), callBack);
    }

    @Override
    public void getUserInfo(String token, HttpCallBack<User> callBack) {
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        doRequest(accountApi.getUserInfo(map), callBack);
    }

    @Override
    public void bindInfo(String name, String idCard, String carNo, String token, HttpCallBack<Object> callBack) {
//        if (TextUtils.isEmpty(name) || !InputVerifyUtils.isChinese(name)) {
//            callBack.onError(-1, "姓名不正确");
//            return;
//        }
        if (TextUtils.isEmpty(idCard) || !InputVerifyUtils.isId(idCard)) {
            callBack.onError(-1, "身份证号不正确");
            return;
        }
        if (TextUtils.isEmpty(carNo) || InputVerifyUtils.isSymbol(carNo)) {
            callBack.onError(-1, "车牌号不正确");
            return;
        }

//        Map<String, String> map = new HashMap<>();
//        map.put("name", name);
//        map.put("idCard", idCard);
//        map.put("carNo", carNo);

        User user = new User();
        user.setName(name);
        user.setIdCard(idCard);
        user.setCarNo(carNo);

        doRequest(accountApi.bindInfo(token, user), callBack);
    }

    @Override
    public void wxInfoBind(String unionId, String weixinNickName, String headImgUrl, HttpCallBack<Token> callBack) {
        System.out.println(unionId + " " + weixinNickName + " " + headImgUrl);
        Map<String, String> map = new HashMap<>();
        map.put("unionId", unionId);
        map.put("nickName", weixinNickName);
        map.put("headImgUrl", headImgUrl);
        doRequest(accountApi.wxInfoBind(map), callBack);
    }
}
