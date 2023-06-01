package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.UserType;
import com.mobileclient.util.HttpUtil;

/*�û����͹���ҵ���߼���*/
public class UserTypeService {
	/* ����û����� */
	public String AddUserType(UserType userType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userTypeId", userType.getUserTypeId() + "");
		params.put("userTypeName", userType.getUserTypeName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "UserTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ�û����� */
	public List<UserType> QueryUserType(UserType queryConditionUserType) throws Exception {
		String urlString = HttpUtil.BASE_URL + "UserTypeServlet?action=query";
		if(queryConditionUserType != null) {
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		UserTypeListHandler userTypeListHander = new UserTypeListHandler();
		xr.setContentHandler(userTypeListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<UserType> userTypeList = userTypeListHander.getUserTypeList();
		return userTypeList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<UserType> userTypeList = new ArrayList<UserType>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				UserType userType = new UserType();
				userType.setUserTypeId(object.getInt("userTypeId"));
				userType.setUserTypeName(object.getString("userTypeName"));
				userTypeList.add(userType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userTypeList;
	}

	/* �����û����� */
	public String UpdateUserType(UserType userType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userTypeId", userType.getUserTypeId() + "");
		params.put("userTypeName", userType.getUserTypeName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "UserTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ���û����� */
	public String DeleteUserType(int userTypeId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userTypeId", userTypeId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "UserTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "�û�������Ϣɾ��ʧ��!";
		}
	}

	/* �����û�����id��ȡ�û����Ͷ��� */
	public UserType GetUserType(int userTypeId)  {
		List<UserType> userTypeList = new ArrayList<UserType>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userTypeId", userTypeId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "UserTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				UserType userType = new UserType();
				userType.setUserTypeId(object.getInt("userTypeId"));
				userType.setUserTypeName(object.getString("userTypeName"));
				userTypeList.add(userType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = userTypeList.size();
		if(size>0) return userTypeList.get(0); 
		else return null; 
	}
}
