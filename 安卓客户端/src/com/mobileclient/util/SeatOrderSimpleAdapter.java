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

public class SeatOrderSimpleAdapter extends SimpleAdapter { 
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

    public SeatOrderSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.seatorder_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_orderId = (TextView)convertView.findViewById(R.id.tv_orderId);
	  holder.tv_seatObj = (TextView)convertView.findViewById(R.id.tv_seatObj);
	  holder.tv_orderDate = (TextView)convertView.findViewById(R.id.tv_orderDate);
	  holder.tv_startTime = (TextView)convertView.findViewById(R.id.tv_startTime);
	  holder.tv_endTime = (TextView)convertView.findViewById(R.id.tv_endTime);
	  holder.tv_addTime = (TextView)convertView.findViewById(R.id.tv_addTime);
	  holder.tv_userObj = (TextView)convertView.findViewById(R.id.tv_userObj);
	  holder.tv_orderState = (TextView)convertView.findViewById(R.id.tv_orderState);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_orderId.setText("ԤԼid��" + mData.get(position).get("orderId").toString());
	  holder.tv_seatObj.setText("ԤԼ��λ��" + (new SeatService()).GetSeat(Integer.parseInt(mData.get(position).get("seatObj").toString())).getSeatCode());
	  try {holder.tv_orderDate.setText("ԤԼ���ڣ�" + mData.get(position).get("orderDate").toString().substring(0, 10));} catch(Exception ex){}
	  holder.tv_startTime.setText("��ʼʱ�䣺" + mData.get(position).get("startTime").toString());
	  holder.tv_endTime.setText("����ʱ�䣺" + mData.get(position).get("endTime").toString());
	  holder.tv_addTime.setText("�ύԤԼʱ�䣺" + mData.get(position).get("addTime").toString());
	  holder.tv_userObj.setText("ԤԼ�û���" + (new UserInfoService()).GetUserInfo(mData.get(position).get("userObj").toString()).getName());
	  holder.tv_orderState.setText("ԤԼ״̬��" + mData.get(position).get("orderState").toString());
	  /*�����޸ĺõ�view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_orderId;
    	TextView tv_seatObj;
    	TextView tv_orderDate;
    	TextView tv_startTime;
    	TextView tv_endTime;
    	TextView tv_addTime;
    	TextView tv_userObj;
    	TextView tv_orderState;
    }
} 
