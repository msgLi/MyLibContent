package com.mygame.android.net.handle;

import com.mygame.android.json.JsonFormatException;
import com.mygame.android.net.NetRequest;
import com.mygame.android.net.handle.util.DataParseUtil;
import com.mygame.android.net.lobby.BaseGateModel;
import com.mygame.android.net.lobby.HttpGateRequest;

public class GateModelNetResponseParse implements INetResponseDataParse {

	private static final String DEFAULT_DATA_KEY = "data";
	
	@Override
	public <T> BaseGateModel<T> responseDataParse(NetRequest request, String result, Class responseClass) throws Exception {
		if(!(request instanceof HttpGateRequest)){
			BaseGateModel ret = new BaseGateModel();
			ret.setCode(0);
			ret.setData(null);
			ret.setErrorCode(-1);
			ret.setSuccess(false);
			ret.setErrorMessage("请求处理失败");
			return ret;
		}
		if(responseClass == null){
			BaseGateModel ret = new BaseGateModel();
			ret.setCode(0);
			ret.setData(result);
			ret.setErrorCode(0);
			ret.setSuccess(true);
			ret.setMsg(null);
			ret.setErrorMessage(null);
			return ret;
		}

		try {
			if(null != request.getResponseCharacterSet()){
                result = new String(result.getBytes(request.getResponseCharacterSet()),"UTF-8");
            }

			BaseGateModel<Object> model = new BaseGateModel<>();
			//JSONObject json = new JSONObject(result);

			DataParseUtil.complieDataParse(DEFAULT_DATA_KEY, result, model, responseClass);
			return (BaseGateModel<T>) model;
		} catch (Exception e) {
			throw new JsonFormatException(e);
		}
	}

}
