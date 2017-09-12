package com.mygame.android.net.handle;

import com.mygame.android.net.NetRequest;

public interface INetResponseDataParse {

	<T> Object responseDataParse(NetRequest request, String result, Class responseClass) throws Exception;
	
}
