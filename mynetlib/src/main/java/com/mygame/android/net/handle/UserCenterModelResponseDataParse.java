package com.mygame.android.net.handle;

import com.alibaba.fastjson.JSON;
import com.mygame.android.json.JsonFormatException;
import com.mygame.android.net.NetRequest;
import com.mygame.android.net.handle.util.DataParseUtil;
import com.mygame.android.net.lobby.BaseUserCenterModel;
import com.mygame.android.net.lobby.HttpUserCenterRequest;

public class UserCenterModelResponseDataParse implements INetResponseDataParse {

	private static final String DEFAULT_DATA_KEY = "data";
	
	@Override
	public <T> BaseUserCenterModel<T> responseDataParse(NetRequest request, String result, Class responseClass) throws Exception {
		if(!(request instanceof HttpUserCenterRequest)){
			BaseUserCenterModel ret = new BaseUserCenterModel();
			ret.setResultCode(0);
			ret.setData(null);
			ret.setErrorCode(-1);
			ret.setSuccess(false);
			ret.setErrorMessage("请求处理失败");
			return ret;
		}

		try {
			BaseUserCenterModel<Object> model = JSON.parseObject(result, BaseUserCenterModel.class);
			//JSONObject json = new JSONObject(result);

			DataParseUtil.complieDataParse(DEFAULT_DATA_KEY, result, model, responseClass);
			return (BaseUserCenterModel<T>) model;
		} catch (Exception e) {
			throw new JsonFormatException(e);
		}
	}

}
