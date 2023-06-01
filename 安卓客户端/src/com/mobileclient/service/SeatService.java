package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Seat;
import com.mobileclient.util.HttpUtil;

/*��λ����ҵ���߼���*/
public class SeatService {
	/* �����λ */
	public String AddSeat(Seat seat) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("seatId", seat.getSeatId() + "");
		params.put("roomObj", seat.getRoomObj() + "");
		params.put("seatCode", seat.getSeatCode());
		params.put("seatStateObj", seat.getSeatStateObj() + "");
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SeatServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ��λ */
	public List<Seat> QuerySeat(Seat queryConditionSeat) throws Exception {
		String urlString = HttpUtil.BASE_URL + "SeatServlet?action=query";
		if(queryConditionSeat != null) {
			urlString += "&roomObj=" + queryConditionSeat.getRoomObj();
			urlString += "&seatCode=" + URLEncoder.encode(queryConditionSeat.getSeatCode(), "UTF-8") + "";
			urlString += "&seatStateObj=" + queryConditionSeat.getSeatStateObj();
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		SeatListHandler seatListHander = new SeatListHandler();
		xr.setContentHandler(seatListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Seat> seatList = seatListHander.getSeatList();
		return seatList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<Seat> seatList = new ArrayList<Seat>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Seat seat = new Seat();
				seat.setSeatId(object.getInt("seatId"));
				seat.setRoomObj(object.getInt("roomObj"));
				seat.setSeatCode(object.getString("seatCode"));
				seat.setSeatStateObj(object.getInt("seatStateObj"));
				seatList.add(seat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return seatList;
	}

	/* ������λ */
	public String UpdateSeat(Seat seat) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("seatId", seat.getSeatId() + "");
		params.put("roomObj", seat.getRoomObj() + "");
		params.put("seatCode", seat.getSeatCode());
		params.put("seatStateObj", seat.getSeatStateObj() + "");
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SeatServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ����λ */
	public String DeleteSeat(int seatId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("seatId", seatId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SeatServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "��λ��Ϣɾ��ʧ��!";
		}
	}

	/* ������λid��ȡ��λ���� */
	public Seat GetSeat(int seatId)  {
		List<Seat> seatList = new ArrayList<Seat>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("seatId", seatId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SeatServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Seat seat = new Seat();
				seat.setSeatId(object.getInt("seatId"));
				seat.setRoomObj(object.getInt("roomObj"));
				seat.setSeatCode(object.getString("seatCode"));
				seat.setSeatStateObj(object.getInt("seatStateObj"));
				seatList.add(seat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = seatList.size();
		if(size>0) return seatList.get(0); 
		else return null; 
	}
}
