package com.chh.yinbao;

/**
 * Created by potoyang on 2017/8/7.
 */

public class Result<T> extends BaseResult {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
