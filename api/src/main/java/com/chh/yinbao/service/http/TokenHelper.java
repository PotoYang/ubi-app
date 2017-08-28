package com.chh.yinbao.service.http;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by potoyang on 2017/8/7.
 */

public class TokenHelper {

    public static final String ACCESS_TOKEN_KEY = "token";

    /**
     * 更换body请求方式的token参数。
     *
     * @param newToken
     * @param method
     * @param args
     */
    public static void updateTokenParamasByBodyRequest(String newToken, final Method method, final Object[] args) {
        if (args != null && args.length > 0) {
            for (Object o : args) {
                if (o instanceof Map && ((Map) o).get(ACCESS_TOKEN_KEY) != null) {
                    ((Map) o).put(ACCESS_TOKEN_KEY, newToken);
                    return;
                }
            }
        }
    }
}
