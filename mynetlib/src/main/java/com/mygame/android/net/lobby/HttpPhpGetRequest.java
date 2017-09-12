package com.mygame.android.net.lobby;

import android.app.Activity;

import com.mygame.android.model.NameValuePair;
import com.mygame.android.net.HttpPostHeader;
import com.mygame.android.net.NetRequest;
import com.mygame.android.net.handle.PhpModelNetResponseParse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * php接口请求类
 * */
public class HttpPhpGetRequest extends NetRequest implements HttpPostHeader{
	
	private static final String SECRET = "2e3aa31da2886b5be5cbf7c104a0da37";

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

	public HttpPhpGetRequest(Activity hostActivity, CallBackAsync callBackAsync) {
		super(hostActivity, callBackAsync);
		dataParseHandle = PhpModelNetResponseParse.class;
	}

	@Override
	public List<NameValuePair> complieParams() {
		// TODO Auto-generated method stub
		long time = System.currentTimeMillis() / 1000;
		addParameter("format", "json");
		addParameter("op", String.valueOf(time));
		String origin = "format=jsonop=" + time
				+ "secret="+SECRET;
		addParameter("sign",encodeByMD5(origin));
		return super.complieParams();
	}

	@Override
	public Map<String, String> getHeaders() {
		return this.headers;
	}
}
