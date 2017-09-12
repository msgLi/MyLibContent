package com.mygame.android.net;

/**
 * Created by msg on 2017/7/24.
 */

public class NetPostTask {
    public String url;
    public String responseKeyName;
    public NetRequest request;
    public Class<? extends NetResponse> responseClass;
    public Object response;
}
