package com.mygame.android.net.handle;

import com.alibaba.fastjson.JSONObject;
import com.mygame.android.model.Model;
import com.mygame.android.net.NetRequest;
import com.mygame.android.net.handle.util.DataParseUtil;
import com.mygame.android.net.lobby.HttpCommonPostRequest;

/**
 * Created by msg on 2017/7/25.
 */

public class CommonPostResponseDataParse implements INetResponseDataParse {
    @Override
    public <T> Object responseDataParse(NetRequest request, String result, Class responseClass) throws Exception {

        Model model = new Model();

        if(request instanceof HttpCommonPostRequest){

            if(responseClass == null){
                model.setErrorMessage("");
                model.setErrorCode(0);
                model.setSuccess(true);
                model.setData(result);
            }else{
                if(null != request.getResponseCharacterSet()){
                    result = new String(result.getBytes(request.getResponseCharacterSet()),"UTF-8");
                }
                DataParseUtil.complieDataParse("data",result,model,responseClass);
                JSONObject jsonpObject = JSONObject.parseObject(result);
                model.setErrorCode("000000".equals(jsonpObject.getString("returnCode")) ? 0 : Integer.parseInt(jsonpObject.getString("returnCode")));
                model.setErrorMessage(jsonpObject.getString("message"));
            }

        }else{
            model.setData(null);
            model.setErrorCode(0);
            model.setErrorMessage("请求类型与相应转换匹配不当");
        }

        return model;
    }
}
