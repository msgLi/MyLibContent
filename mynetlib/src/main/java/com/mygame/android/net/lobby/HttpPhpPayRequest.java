package com.mygame.android.net.lobby;

import android.app.Activity;

import com.mygame.android.model.BasicNameValuePair;
import com.mygame.android.model.NameValuePair;
import com.mygame.android.net.MD5;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * php接口请求类
 * */
public class HttpPhpPayRequest extends HttpPhpGetRequest{

	private static final String SECRET = "Uw7FXzhyoXzzUq8F0fMZCIcMg1iAyPxE";

	public HttpPhpPayRequest(Activity hostActivity, CallBackAsync callBackAsync) {
		super(hostActivity, callBackAsync);
	}

	@Override
	public List<NameValuePair> complieParams() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Set<String> keySet = getRequestParams().keySet();
		List<String> keys = new ArrayList<String>();
		for (String key : keySet) {
			keys.add(key);
		}
		Collections.sort(keys);
		if (null != keySet && 0 != keySet.size()) {
			for (String key : keys) {
				String val = getParameter(key);
				if(null == val){
					val = "";
				}
				params.add(new BasicNameValuePair(key,val));
			}
		}
		try {
			if(params != null && params.size() > 0){
                StringBuilder sb = new StringBuilder();

                for (NameValuePair param : params) {
                    sb.append(param.getName());
                    sb.append("=");
                    sb.append(URLEncoder.encode(param.getValue(),"UTF-8"));
                }

                sb.append("secret="+SECRET);
                MD5 md5 = new MD5();
                params.add(new BasicNameValuePair("sig", md5.getMD5ofStr(sb.toString()).toLowerCase()));
            }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return params;
	}

}
