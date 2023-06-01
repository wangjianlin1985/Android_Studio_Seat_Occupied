package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.RoomService;
import com.mobileclient.service.SeatStateService;
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

public class SeatSimpleAdapter extends SimpleAdapter { 
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

    public SeatSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.seat_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_seatId = (TextView)convertView.findViewById(R.id.tv_seatId);
	  holder.tv_roomObj = (TextView)convertView.findViewById(R.id.tv_roomObj);
	  holder.tv_seatCode = (TextView)convertView.findViewById(R.id.tv_seatCode);
	  holder.tv_seatStateObj = (TextView)convertView.findViewById(R.id.tv_seatStateObj);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_seatId.setText("��λid��" + mData.get(position).get("seatId").toString());
	  holder.tv_seatId.setVisibility(View.GONE);
	  holder.tv_roomObj.setText("���������ң�" + (new RoomService()).GetRoom(Integer.parseInt(mData.get(position).get("roomObj").toString())).getRoomName());
	  holder.tv_seatCode.setText("��λ��ţ�" + mData.get(position).get("seatCode").toString());
	  holder.tv_seatStateObj.setText("��ǰ״̬��" + (new SeatStateService()).GetSeatState(Integer.parseInt(mData.get(position).get("seatStateObj").toString())).getStateName());
	  /*�����޸ĺõ�view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_seatId;
    	TextView tv_roomObj;
    	TextView tv_seatCode;
    	TextView tv_seatStateObj;
    }
} 
