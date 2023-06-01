package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

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

public class JcSimpleAdapter extends SimpleAdapter { 
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

    public JcSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.jc_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_jcId = (TextView)convertView.findViewById(R.id.tv_jcId);
	  holder.tv_jcType = (TextView)convertView.findViewById(R.id.tv_jcType);
	  holder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
	  holder.tv_userObj = (TextView)convertView.findViewById(R.id.tv_userObj);
	  holder.tv_creditScore = (TextView)convertView.findViewById(R.id.tv_creditScore);
	  holder.tv_jcTime = (TextView)convertView.findViewById(R.id.tv_jcTime);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_jcId.setText("����id��" + mData.get(position).get("jcId").toString());
	  holder.tv_jcType.setText("�������ͣ�" + mData.get(position).get("jcType").toString());
	  holder.tv_title.setText("���ͱ��⣺" + mData.get(position).get("title").toString());
	  holder.tv_userObj.setText("�����û���" + (new UserInfoService()).GetUserInfo(mData.get(position).get("userObj").toString()).getName());
	  holder.tv_creditScore.setText("���÷�����" + mData.get(position).get("creditScore").toString());
	  holder.tv_jcTime.setText("����ʱ�䣺" + mData.get(position).get("jcTime").toString());
	  /*�����޸ĺõ�view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_jcId;
    	TextView tv_jcType;
    	TextView tv_title;
    	TextView tv_userObj;
    	TextView tv_creditScore;
    	TextView tv_jcTime;
    }
} 
