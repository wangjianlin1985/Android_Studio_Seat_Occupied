package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

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

public class RoomSimpleAdapter extends SimpleAdapter { 
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

    public RoomSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.room_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_roomId = (TextView)convertView.findViewById(R.id.tv_roomId);
	  holder.tv_roomName = (TextView)convertView.findViewById(R.id.tv_roomName);
	  holder.iv_roomPhoto = (ImageView)convertView.findViewById(R.id.iv_roomPhoto);
	  holder.tv_roomPlace = (TextView)convertView.findViewById(R.id.tv_roomPlace);
	  holder.tv_seatNum = (TextView)convertView.findViewById(R.id.tv_seatNum);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_roomId.setText("������id��" + mData.get(position).get("roomId").toString());
	  holder.tv_roomName.setText("���������ƣ�" + mData.get(position).get("roomName").toString());
	  holder.iv_roomPhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener roomPhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_roomPhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("roomPhoto"),roomPhotoLoadListener);  
	  holder.tv_roomPlace.setText("������λ�ã�" + mData.get(position).get("roomPlace").toString());
	  holder.tv_seatNum.setText("����λ����" + mData.get(position).get("seatNum").toString());
	  /*�����޸ĺõ�view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_roomId;
    	TextView tv_roomName;
    	ImageView iv_roomPhoto;
    	TextView tv_roomPlace;
    	TextView tv_seatNum;
    }
} 
