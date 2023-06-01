package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.SeatState;
import com.mobileclient.util.HttpUtil;

/*��λ״̬����ҵ���߼���*/
public class SeatStateService {
	/* �����λ״̬ */
	public String AddSeatState(SeatState seatState) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", seatState.getStateId() + "");
		params.put("stateName", seatState.getStateName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SeatStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ��λ״̬ */
	public List<SeatState> QuerySeatState(SeatState queryConditionSeatState) throws Exception {
		String urlString = HttpUtil.BASE_URL + "SeatStateServlet?action=query";
		if(queryConditionSeatState != null) {
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		SeatStateListHandler seatStateListHander = new SeatStateListHandler();
		xr.setContentHandler(seatStateListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<SeatState> seatStateList = seatStateListHander.getSeatStateList();
		return seatStateList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<SeatState> seatStateList = new ArrayList<SeatState>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SeatState seatState = new SeatState();
				seatState.setStateId(object.getInt("stateId"));
				seatState.setStateName(object.getString("stateName"));
				seatStateList.add(seatState);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return seatStateList;
	}

	/* ������λ״̬ */
	public String UpdateSeatState(SeatState seatState) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", seatState.getStateId() + "");
		params.put("stateName", seatState.getStateName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SeatStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ����λ״̬ */
	public String DeleteSeatState(int stateId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", stateId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SeatStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "��λ״̬��Ϣɾ��ʧ��!";
		}
	}

	/* ����״̬id��ȡ��λ״̬���� */
	public SeatState GetSeatState(int stateId)  {
		List<SeatState> seatStateList = new ArrayList<SeatState>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", stateId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SeatStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SeatState seatState = new SeatState();
				seatState.setStateId(object.getInt("stateId"));
				seatState.setStateName(object.getString("stateName"));
				seatStateList.add(seatState);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = seatStateList.size();
		if(size>0) return seatStateList.get(0); 
		else return null; 
	}
}
