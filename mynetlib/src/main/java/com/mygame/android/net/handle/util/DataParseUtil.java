package com.mygame.android.net.handle.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mygame.android.json.templete.annotation.JsonClass;
import com.mygame.android.json.templete.annotation.JsonKey;
import com.mygame.android.json.templete.annotation.JsonType;
import com.mygame.android.model.Model;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class DataParseUtil {

	public static String getJsonKey(Class responseClass,String defaultKey){
		String dataKey = defaultKey;
		if(responseClass.getAnnotation(JsonKey.class) != null){
			JsonKey jsonKey = (JsonKey) responseClass.getAnnotation(JsonKey.class);
			dataKey = jsonKey.key();
			if(dataKey == null){
				dataKey = defaultKey;
			}
		}
		return dataKey;
	}

	public static void complieDataParse(String defaultDataKey, String jsonString, Model<Object> model, Class responseClass){
		if(responseClass.getAnnotation(JsonClass.class) != null){
			JsonClass jsonClass = (JsonClass) responseClass.getAnnotation(JsonClass.class);
			String fmt = jsonClass.fmt();
			String dataKey = DataParseUtil.getJsonKey(responseClass, defaultDataKey);

			if("json".equalsIgnoreCase(fmt)){
				JSONObject json = JSONObject.parseObject(jsonString);
				if(!json.containsKey(dataKey)){
					return;
				}
				if(jsonClass.type() == JsonType.JSONOBJECT){
					model.setData(JSON.parseObject(String.valueOf(json.getJSONObject(dataKey)), responseClass));
				}else if(jsonClass.type() == JsonType.JSONLIST){
					model.setData(JSON.parseArray(String.valueOf(json.getJSONArray(dataKey)), responseClass));
				}
			}else if("xml".equalsIgnoreCase(fmt)){
				XStream xstream = new XStream(new XppDriver(new NoNameCoder()));
				xstream.autodetectAnnotations(true);
				xstream.processAnnotations(responseClass);
				Object bean=xstream.fromXML(jsonString);
				model.setData(bean);
			}

		}else{
			JSONObject json = JSONObject.parseObject(jsonString);
			model.setData(JSON.parseObject(String.valueOf(json.getJSONObject(defaultDataKey)), responseClass));
		}
	}

}
