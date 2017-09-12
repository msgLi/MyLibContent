package com.mygame.android.net.lobby;

import android.app.Activity;

import com.mygame.android.model.BasicNameValuePair;
import com.mygame.android.model.NameValuePair;
import com.mygame.android.net.HttpPostHeader;
import com.mygame.android.net.NetRequest;
import com.mygame.android.net.handle.UserCenterModelResponseDataParse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 用户中心相关接口请求类
 * */
public class HttpUserCenterRequest extends NetRequest implements HttpPostHeader {

	public static final String secretKey = "6a1494cf07452af16b70a5ca39664619";

	private Map<String,String> headers = new HashMap<>();

	public void addHeader(String name,String value){
		this.headers.put(name,value);
	}

	public void removeHeader(String name){
		this.headers.remove(name);
	}

	public void cleanHeader(){
		this.headers.clear();
	}
	public HttpUserCenterRequest(Activity hostActivity,
			CallBackAsync callBackAsync) {
		super(hostActivity, callBackAsync);
		// TODO Auto-generated constructor stub
		dataParseHandle = UserCenterModelResponseDataParse.class;
	}

	@Override
	public List<NameValuePair> complieParams() {
		// TODO Auto-generated method stub
		List<NameValuePair> params =  super.complieParams();
		StringBuilder sb = new StringBuilder();

		for (NameValuePair param : params) {
			sb.append(param.getName());
			sb.append("=");
			sb.append(param.getValue());
			sb.append("&");
		}

		StringBuilder origin = sb.deleteCharAt(sb.length() - 1);
		params.add(new BasicNameValuePair("sign", encodeByMD5(origin.toString()+secretKey)));
		return params;
	}

	@Override
	public Map<String, String> getHeaders() {
		return this.headers;
	}
}
