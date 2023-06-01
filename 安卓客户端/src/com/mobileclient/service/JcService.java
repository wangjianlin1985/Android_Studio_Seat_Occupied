package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Jc;
import com.mobileclient.util.HttpUtil;

/*���͹���ҵ���߼���*/
public class JcService {
	/* ��ӽ��� */
	public String AddJc(Jc jc) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jcId", jc.getJcId() + "");
		params.put("jcType", jc.getJcType());
		params.put("title", jc.getTitle());
		params.put("content", jc.getContent());
		params.put("userObj", jc.getUserObj());
		params.put("creditScore", jc.getCreditScore() + "");
		params.put("jcTime", jc.getJcTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JcServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ���� */
	public List<Jc> QueryJc(Jc queryConditionJc) throws Exception {
		String urlString = HttpUtil.BASE_URL + "JcServlet?action=query";
		if(queryConditionJc != null) {
			urlString += "&jcType=" + URLEncoder.encode(queryConditionJc.getJcType(), "UTF-8") + "";
			urlString += "&title=" + URLEncoder.encode(queryConditionJc.getTitle(), "UTF-8") + "";
			urlString += "&userObj=" + URLEncoder.encode(queryConditionJc.getUserObj(), "UTF-8") + "";
			urlString += "&jcTime=" + URLEncoder.encode(queryConditionJc.getJcTime(), "UTF-8") + "";
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		JcListHandler jcListHander = new JcListHandler();
		xr.setContentHandler(jcListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Jc> jcList = jcListHander.getJcList();
		return jcList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<Jc> jcList = new ArrayList<Jc>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Jc jc = new Jc();
				jc.setJcId(object.getInt("jcId"));
				jc.setJcType(object.getString("jcType"));
				jc.setTitle(object.getString("title"));
				jc.setContent(object.getString("content"));
				jc.setUserObj(object.getString("userObj"));
				jc.setCreditScore((float) object.getDouble("creditScore"));
				jc.setJcTime(object.getString("jcTime"));
				jcList.add(jc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jcList;
	}

	/* ���½��� */
	public String UpdateJc(Jc jc) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jcId", jc.getJcId() + "");
		params.put("jcType", jc.getJcType());
		params.put("title", jc.getTitle());
		params.put("content", jc.getContent());
		params.put("userObj", jc.getUserObj());
		params.put("creditScore", jc.getCreditScore() + "");
		params.put("jcTime", jc.getJcTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JcServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ������ */
	public String DeleteJc(int jcId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jcId", jcId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JcServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "������Ϣɾ��ʧ��!";
		}
	}

	/* ���ݽ���id��ȡ���Ͷ��� */
	public Jc GetJc(int jcId)  {
		List<Jc> jcList = new ArrayList<Jc>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jcId", jcId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JcServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Jc jc = new Jc();
				jc.setJcId(object.getInt("jcId"));
				jc.setJcType(object.getString("jcType"));
				jc.setTitle(object.getString("title"));
				jc.setContent(object.getString("content"));
				jc.setUserObj(object.getString("userObj"));
				jc.setCreditScore((float) object.getDouble("creditScore"));
				jc.setJcTime(object.getString("jcTime"));
				jcList.add(jc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = jcList.size();
		if(size>0) return jcList.get(0); 
		else return null; 
	}
}
