package com.chh.yinbao;

/**
 * Created by potoyang on 2017/8/7.
 */

public class BaseResult {

    private int code;    //
    private String msg;      // 返回信息
    private String timestamp;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


}
