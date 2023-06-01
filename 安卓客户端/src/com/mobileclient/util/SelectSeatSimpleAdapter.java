package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.SeatService;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class SelectSeatSimpleAdapter extends SimpleAdapter { 
	/*��Ҫ�󶨵Ŀؼ���Դid*/
    private int[] mTo;
    /*map���Ϲؼ�������*/
    private String[] mFrom;
/*��Ҫ�󶨵�����*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //ͼƬ�첽���������,���ڴ滺����ļ�����
    private SyncImageLoader syncImageLoader;

    public SelectSeatSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*��һ��װ�����viewʱ=null,���½�һ������inflate��Ⱦһ��view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.selectseat_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_selectId = (TextView)convertView.findViewById(R.id.tv_selectId);
	  holder.tv_seatObj = (TextView)convertView.findViewById(R.id.tv_seatObj);
	  holder.tv_userObj = (TextView)convertView.findViewById(R.id.tv_userObj);
	  holder.tv_startTime = (TextView)convertView.findViewById(R.id.tv_startTime);
	  holder.tv_endTime = (TextView)convertView.findViewById(R.id.tv_endTime);
	  holder.tv_seatState = (TextView)convertView.findViewById(R.id.tv_seatState);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_selectId.setText("ѡ��id��" + mData.get(position).get("selectId").toString());
	  holder.tv_seatObj.setText("��λ��ţ�" + (new SeatService()).GetSeat(Integer.parseInt(mData.get(position).get("seatObj").toString())).getSeatCode());
	  holder.tv_userObj.setText("ѡ���û���" + (new UserInfoService()).GetUserInfo(mData.get(position).get("userObj").toString()).getName());
	  holder.tv_startTime.setText("ѡ����ʼʱ�䣺" + mData.get(position).get("startTime").toString());
	  holder.tv_endTime.setText("ѡ������ʱ�䣺" + mData.get(position).get("endTime").toString());
	  holder.tv_seatState.setText("ѡ��״̬��" + mData.get(position).get("seatState").toString());
	  /*�����޸ĺõ�view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_selectId;
    	TextView tv_seatObj;
    	TextView tv_userObj;
    	TextView tv_startTime;
    	TextView tv_endTime;
    	TextView tv_seatState;
    }
} 
