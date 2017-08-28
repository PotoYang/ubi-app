package com.chh.yinbao.service.http.gson;

import com.chh.yinbao.BaseResult;
import com.chh.yinbao.Result;
import com.chh.yinbao.service.http.HttpStatusCode;
import com.chh.yinbao.service.http.exception.ApiException;
import com.chh.yinbao.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by potoyang on 2017/8/7.
 */

public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, Object> {

    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public Object convert(ResponseBody value) throws IOException {
        try {
            String tempString = value.string();

            //显示数据
            LogUtils.i("AA", tempString);

            //没有token过期
            BaseResult result = new Gson().fromJson(tempString, BaseResult.class);
            int code = result.getCode();
            if (code == HttpStatusCode.SYSTEM_ERROR) {
                throw new ApiException(code, result.getMsg());
            } else if (code == HttpStatusCode.TOKEN_ERROR) {
                throw new ApiException(code, result.getMsg());
            } else if (code == HttpStatusCode.SUCCESS || code == HttpStatusCode.DATA_NULL) {
                try {
                    Result object = (Result) adapter.fromJson(tempString);

                    if (object.getData() == null) {
                        return new Object();
                    }
                    return object.getData();
                } catch (Exception e) {
                    e.printStackTrace();
                    return new Object();
                }
            }
        } finally {
            value.close();
        }
        return null;
    }

}
