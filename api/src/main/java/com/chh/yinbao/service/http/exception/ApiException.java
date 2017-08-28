package com.chh.yinbao.service.http.exception;

import java.io.IOException;

/**
 * Created by potoyang on 2017/8/7.
 */

public class ApiException extends IOException {
    private int state;

    public ApiException(String message) {
        super(message);
    }

    public ApiException(int state, String message) {
        super(message);
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
