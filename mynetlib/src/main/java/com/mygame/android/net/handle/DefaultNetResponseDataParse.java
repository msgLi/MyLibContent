package com.mygame.android.net.handle;

import com.mygame.android.json.JsonFormatException;
import com.mygame.android.model.DefaultModel;
import com.mygame.android.net.NetRequest;

public class DefaultNetResponseDataParse implements INetResponseDataParse {

	@Override
	public Object responseDataParse(NetRequest request, String result, Class responseClass) throws Exception {
		// TODO Auto-generated method stub
		try {
			DefaultModel response = new DefaultModel();
			response.setData(result);
			return response;
		} catch (Exception e) {
			throw new JsonFormatException(e);
		}
	}

}
