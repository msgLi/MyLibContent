package com.mygame.android.net.handle;

import com.alibaba.fastjson.JSON;
import com.mygame.android.json.JsonFormatException;
import com.mygame.android.net.NetRequest;
import com.mygame.android.net.handle.util.DataParseUtil;
import com.mygame.android.net.lobby.BasePhpModel;
import com.mygame.android.net.lobby.HttpPhpGetRequest;

public class PhpModelNetResponseParse implements INetResponseDataParse {

	private static final String DEFAULT_DATA_KEY = "data";
	
	@Override
	public <T> BasePhpModel<T> responseDataParse(NetRequest request, String result, Class responseClass) throws Exception {
		if(!(request instanceof HttpPhpGetRequest)){
			BasePhpModel ret = new BasePhpModel();
			ret.setStatus(0);
			ret.setData(null);
			ret.setErrorCode(-1);
			ret.setSuccess(false);
			ret.setErrorMessage("请求处理失败");
			return ret;
		}
		if(responseClass == null){
			BasePhpModel ret = new BasePhpModel();
			ret.setStatus(0);
			ret.setData(result);
			ret.setErrorCode(0);
			ret.setSuccess(true);
			ret.setErrorMessage(null);
			return ret;
		}
		try {
			BasePhpModel<Object> model = JSON.parseObject(result, BasePhpModel.class);
			//JSONObject json = new JSONObject(result);

			DataParseUtil.complieDataParse(DEFAULT_DATA_KEY, result, model, responseClass);
			return (BasePhpModel<T>) model;
		} catch (Exception e) {
			throw new JsonFormatException(e);
		}
	}

}
