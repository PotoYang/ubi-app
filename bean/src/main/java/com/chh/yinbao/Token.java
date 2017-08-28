package com.chh.yinbao;

import java.io.Serializable;

/**
 * Created by potoyang on 2017/8/7.
 */

public class Token implements Serializable {

    private int expire;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }
}
