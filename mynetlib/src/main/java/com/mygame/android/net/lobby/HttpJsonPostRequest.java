package com.mygame.android.net.lobby;

import android.app.Activity;

import com.alibaba.fastjson.JSON;

/**
 * Created by msg on 2017/7/25.
 */

public class HttpJsonPostRequest extends HttpCommonPostRequest {

    private Object data;

    public HttpJsonPostRequest(Activity hostActivity, CallBackAsync callBackAsync) {
        super(hostActivity, callBackAsync);
        this.getHeaders().put("Content-Type","application/json; charset=utf-8");
    }

    @Override
    public String complieGet() {
        if(data != null){
            return JSON.toJSONString(data);
        }
        return null;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
