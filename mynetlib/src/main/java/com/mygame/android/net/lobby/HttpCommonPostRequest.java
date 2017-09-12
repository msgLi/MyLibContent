package com.mygame.android.net.lobby;

import android.app.Activity;

import com.mygame.android.net.HttpPostHeader;
import com.mygame.android.net.NetRequest;
import com.mygame.android.net.handle.CommonPostResponseDataParse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by msg on 2017/7/25.
 */

public class HttpCommonPostRequest extends NetRequest implements HttpPostHeader{

    private Map<String,String> headers = new HashMap<>();

    public HttpCommonPostRequest(Activity hostActivity, CallBackAsync callBackAsync) {
        super(hostActivity, callBackAsync);
        this.setDataParseHandle(CommonPostResponseDataParse.class);
    }

    public void addHeader(String name,String value){
        this.headers.put(name,value);
    }

    public void removeHeader(String name){
        this.headers.remove(name);
    }

    public void cleanHeader(){
        this.headers.clear();
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }
}
