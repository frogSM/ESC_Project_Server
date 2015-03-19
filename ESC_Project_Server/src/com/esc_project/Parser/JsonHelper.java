package com.esc_project.Parser;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.esc_project.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonHelper {

	/** JSON 명령타입 **/
	private String mType;
	/** JSON 데이터부분 **/
	private Object mObject ;
	
	public JsonHelper() {
		mObject = new Object();
	}
	
	/** 매개변수로 들어온 명령type과 데이터 객체를 이용하여 메세지를 만든다. **/
	public String makeJsonMessage(String mType, Object mObjList) {
		JSONObject object = new JSONObject();
		
		object.put("type", mType);
		object.put("Object", mObjList);
		
		String sendMessage = object.toString();
		
		return sendMessage;
	}
	
	
	/** 문장을 명령type과 데이터 객체로 파싱한다. **/
	public void parserJsonMessage(String json_msg) {
		
		String mMessage = json_msg;
		
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonObject object = parser.parse(mMessage).getAsJsonObject();
		
		mType = gson.fromJson(object.get("type"), String.class);
		
		switch(mType) {
		
		case Constants.Uid_Info :
			// uid 를 이용한 상품 정보 가져오기
			List<String> uid = gson.fromJson(object.get("uid"), ArrayList.class);
			mObject = uid;
			break;
			
		case Constants.All_Product_Info :
			mObject = null;
			break;
		}
	}
	
	/** 명령 타입을 반환한다. **/
	public String getType() {
		return mType;
	}
	
	/** 데이터 객체를 반환한다. **/
	public Object getObject() {
		return mObject;
	}
}
