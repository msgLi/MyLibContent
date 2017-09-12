package com.mygame.android.net.lobby;

import android.app.Activity;
import android.text.TextUtils;

import com.mygame.android.model.BasicNameValuePair;
import com.mygame.android.model.NameValuePair;
import com.mygame.android.net.HttpPostHeader;
import com.mygame.android.net.NetRequest;
import com.mygame.android.net.handle.GateModelNetResponseParse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * C++ http网关服务器请求类
 * */
public class HttpGateRequest extends NetRequest implements HttpPostHeader{

	private Map<String,String> headers = new HashMap<>();
	
	public HttpGateRequest(Activity hostActivity, CallBackAsync callBackAsync) {
		super(hostActivity, callBackAsync);
		dataParseHandle = GateModelNetResponseParse.class;
		responseCharacterSet = "ISO-8859-1";
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
	public List<NameValuePair> complieParams() {
		// TODO Auto-generated method stub
		if(TextUtils.isEmpty(getParameter("fmt"))){
			super.addParameter("fmt","json");
		}

		List<NameValuePair> params =  super.complieParams();
		StringBuilder sb = new StringBuilder();

		for (NameValuePair param : params) {
			sb.append(param.getName());
			sb.append("=");
			sb.append(param.getValue());
			sb.append(",");
		}

		StringBuilder origin = sb.deleteCharAt(sb.length() - 1);
		params.add(new BasicNameValuePair("sign", encodeByMD5(origin.toString())));
		return params;
	}

	@Override
	public Map<String, String> getHeaders() {
		return this.headers;
	}
}
