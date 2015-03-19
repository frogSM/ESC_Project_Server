package com.esc_project.Parser;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.esc_project.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonHelper {

	/** JSON ���Ÿ�� **/
	private String mType;
	/** JSON �����ͺκ� **/
	private Object mObject ;
	
	public JsonHelper() {
		mObject = new Object();
	}
	
	/** �Ű������� ���� ���type�� ������ ��ü�� �̿��Ͽ� �޼����� �����. **/
	public String makeJsonMessage(String mType, Object mObjList) {
		JSONObject object = new JSONObject();
		
		object.put("type", mType);
		object.put("Object", mObjList);
		
		String sendMessage = object.toString();
		
		return sendMessage;
	}
	
	
	/** ������ ���type�� ������ ��ü�� �Ľ��Ѵ�. **/
	public void parserJsonMessage(String json_msg) {
		
		String mMessage = json_msg;
		
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonObject object = parser.parse(mMessage).getAsJsonObject();
		
		mType = gson.fromJson(object.get("type"), String.class);
		
		switch(mType) {
		
		case Constants.Uid_Info :
			// uid �� �̿��� ��ǰ ���� ��������
			List<String> uid = gson.fromJson(object.get("uid"), ArrayList.class);
			mObject = uid;
			break;
			
		case Constants.All_Product_Info :
			mObject = null;
			break;
		}
	}
	
	/** ��� Ÿ���� ��ȯ�Ѵ�. **/
	public String getType() {
		return mType;
	}
	
	/** ������ ��ü�� ��ȯ�Ѵ�. **/
	public Object getObject() {
		return mObject;
	}
}
