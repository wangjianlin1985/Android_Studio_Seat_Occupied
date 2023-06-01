package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Jubao;
import com.mobileclient.util.HttpUtil;

/*�ٱ�����ҵ���߼���*/
public class JubaoService {
	/* ��Ӿٱ� */
	public String AddJubao(Jubao jubao) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jubaoId", jubao.getJubaoId() + "");
		params.put("title", jubao.getTitle());
		params.put("content", jubao.getContent());
		params.put("userObj", jubao.getUserObj());
		params.put("jubaoTime", jubao.getJubaoTime());
		params.put("replyContent", jubao.getReplyContent());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JubaoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ�ٱ� */
	public List<Jubao> QueryJubao(Jubao queryConditionJubao) throws Exception {
		String urlString = HttpUtil.BASE_URL + "JubaoServlet?action=query";
		if(queryConditionJubao != null) {
			urlString += "&title=" + URLEncoder.encode(queryConditionJubao.getTitle(), "UTF-8") + "";
			urlString += "&userObj=" + URLEncoder.encode(queryConditionJubao.getUserObj(), "UTF-8") + "";
			urlString += "&jubaoTime=" + URLEncoder.encode(queryConditionJubao.getJubaoTime(), "UTF-8") + "";
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		JubaoListHandler jubaoListHander = new JubaoListHandler();
		xr.setContentHandler(jubaoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Jubao> jubaoList = jubaoListHander.getJubaoList();
		return jubaoList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<Jubao> jubaoList = new ArrayList<Jubao>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Jubao jubao = new Jubao();
				jubao.setJubaoId(object.getInt("jubaoId"));
				jubao.setTitle(object.getString("title"));
				jubao.setContent(object.getString("content"));
				jubao.setUserObj(object.getString("userObj"));
				jubao.setJubaoTime(object.getString("jubaoTime"));
				jubao.setReplyContent(object.getString("replyContent"));
				jubaoList.add(jubao);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jubaoList;
	}

	/* ���¾ٱ� */
	public String UpdateJubao(Jubao jubao) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jubaoId", jubao.getJubaoId() + "");
		params.put("title", jubao.getTitle());
		params.put("content", jubao.getContent());
		params.put("userObj", jubao.getUserObj());
		params.put("jubaoTime", jubao.getJubaoTime());
		params.put("replyContent", jubao.getReplyContent());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JubaoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ���ٱ� */
	public String DeleteJubao(int jubaoId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jubaoId", jubaoId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JubaoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "�ٱ���Ϣɾ��ʧ��!";
		}
	}

	/* ���ݾٱ�id��ȡ�ٱ����� */
	public Jubao GetJubao(int jubaoId)  {
		List<Jubao> jubaoList = new ArrayList<Jubao>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jubaoId", jubaoId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JubaoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Jubao jubao = new Jubao();
				jubao.setJubaoId(object.getInt("jubaoId"));
				jubao.setTitle(object.getString("title"));
				jubao.setContent(object.getString("content"));
				jubao.setUserObj(object.getString("userObj"));
				jubao.setJubaoTime(object.getString("jubaoTime"));
				jubao.setReplyContent(object.getString("replyContent"));
				jubaoList.add(jubao);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = jubaoList.size();
		if(size>0) return jubaoList.get(0); 
		else return null; 
	}
}
