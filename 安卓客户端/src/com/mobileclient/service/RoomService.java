package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Room;
import com.mobileclient.util.HttpUtil;

/*�����ҹ���ҵ���߼���*/
public class RoomService {
	/* ��������� */
	public String AddRoom(Room room) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roomId", room.getRoomId() + "");
		params.put("roomName", room.getRoomName());
		params.put("roomPhoto", room.getRoomPhoto());
		params.put("roomPlace", room.getRoomPlace());
		params.put("seatNum", room.getSeatNum() + "");
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RoomServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ������ */
	public List<Room> QueryRoom(Room queryConditionRoom) throws Exception {
		String urlString = HttpUtil.BASE_URL + "RoomServlet?action=query";
		if(queryConditionRoom != null) {
			urlString += "&roomPlace=" + URLEncoder.encode(queryConditionRoom.getRoomPlace(), "UTF-8") + "";
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		RoomListHandler roomListHander = new RoomListHandler();
		xr.setContentHandler(roomListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Room> roomList = roomListHander.getRoomList();
		return roomList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<Room> roomList = new ArrayList<Room>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Room room = new Room();
				room.setRoomId(object.getInt("roomId"));
				room.setRoomName(object.getString("roomName"));
				room.setRoomPhoto(object.getString("roomPhoto"));
				room.setRoomPlace(object.getString("roomPlace"));
				room.setSeatNum(object.getInt("seatNum"));
				roomList.add(room);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roomList;
	}

	/* ���������� */
	public String UpdateRoom(Room room) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roomId", room.getRoomId() + "");
		params.put("roomName", room.getRoomName());
		params.put("roomPhoto", room.getRoomPhoto());
		params.put("roomPlace", room.getRoomPlace());
		params.put("seatNum", room.getSeatNum() + "");
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RoomServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ�������� */
	public String DeleteRoom(int roomId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roomId", roomId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RoomServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "��������Ϣɾ��ʧ��!";
		}
	}

	/* ����������id��ȡ�����Ҷ��� */
	public Room GetRoom(int roomId)  {
		List<Room> roomList = new ArrayList<Room>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roomId", roomId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RoomServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Room room = new Room();
				room.setRoomId(object.getInt("roomId"));
				room.setRoomName(object.getString("roomName"));
				room.setRoomPhoto(object.getString("roomPhoto"));
				room.setRoomPlace(object.getString("roomPlace"));
				room.setSeatNum(object.getInt("seatNum"));
				roomList.add(room);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = roomList.size();
		if(size>0) return roomList.get(0); 
		else return null; 
	}
}
