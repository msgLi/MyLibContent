package com.mygame.android.net.handle;

import com.mygame.android.json.IJson;
import com.mygame.android.json.JsonFormatException;
import com.mygame.android.json.JsonModuleBean;
import com.mygame.android.json.util.JsonFormatFactory;
import com.mygame.android.net.NetRequest;

import org.json.JSONObject;

public class JsonModelNetResponseDataParse implements INetResponseDataParse {

	@Override
	public <T> Object responseDataParse(NetRequest request, String result, Class responseClass) throws Exception {
		try {
			JsonModuleBean<T> response = (JsonModuleBean<T>) JsonFormatFactory.getJsonModuleBeanParse(new JSONObject(result), "data", (Class<? extends IJson>) responseClass);
			return response;
		} catch (Exception e) {
			throw new JsonFormatException(e);
		}

	}

}
