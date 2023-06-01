package com.mobileclient.activity;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.SelectSeat;
import com.mobileclient.service.SelectSeatService;
import com.mobileclient.util.ActivityUtils;import com.mobileclient.util.SelectSeatSimpleAdapter;
import com.mobileclient.util.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class SelectSeatUserListActivity extends Activity {
	SelectSeatSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int selectId;
	/* ѡ������ҵ���߼������ */
	SelectSeatService selectSeatService = new SelectSeatService();
	/*�����ѯ����������ѡ������*/
	private SelectSeat queryConditionSelectSeat;

	private MyProgressDialog dialog; //������	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.selectseat_list);
		dialog = MyProgressDialog.getInstance(this);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		//�������ؼ�
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//Intent intent = new Intent();
				//intent.setClass(SelectSeatUserListActivity.this, SelectSeatQueryActivity.class);
				//startActivityForResult(intent,ActivityUtils.QUERY_CODE);//�˴���requestCodeӦ�������������е��õ�requestCodeһ��
				setViews();
			}
		});
		 
		
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("ѡ����ѯ�б�");
		ImageView add_btn = (ImageView) this.findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new android.view.View.OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(SelectSeatUserListActivity.this, SelectSeatAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		});
		add_btn.setVisibility(View.GONE);
		
		queryConditionSelectSeat = new SelectSeat();
		queryConditionSelectSeat.setUserObj(username);
		queryConditionSelectSeat.setSeatObj(0);
		queryConditionSelectSeat.setStartTime("");
		queryConditionSelectSeat.setEndTime("");
		queryConditionSelectSeat.setSeatState("");
		 
		setViews();
	}

	//���������������secondActivity�з���ʱ���ô˺���
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ActivityUtils.QUERY_CODE && resultCode==RESULT_OK){
        	Bundle extras = data.getExtras();
        	if(extras != null)
        		queryConditionSelectSeat = (SelectSeat)extras.getSerializable("queryConditionSelectSeat");
        	setViews();
        }
        if(requestCode==ActivityUtils.EDIT_CODE && resultCode==RESULT_OK){
        	setViews();
        }
        if(requestCode == ActivityUtils.ADD_CODE && resultCode == RESULT_OK) {
        	queryConditionSelectSeat = null;
        	setViews();
        }
    }

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		dialog.show();
		final Handler handler = new Handler();
		new Thread(){
			@Override
			public void run() {
				//�����߳��н����������ݲ���
				list = getDatas();
				//������ʧ��handler��֪ͨ���߳��������
				handler.post(new Runnable() {
					@Override
					public void run() {
						dialog.cancel();
						adapter = new SelectSeatSimpleAdapter(SelectSeatUserListActivity.this, list,
	        					R.layout.selectseat_list_item,
	        					new String[] { "selectId","seatObj","userObj","startTime","endTime","seatState" },
	        					new int[] { R.id.tv_selectId,R.id.tv_seatObj,R.id.tv_userObj,R.id.tv_startTime,R.id.tv_endTime,R.id.tv_seatState,},lv);
	        			lv.setAdapter(adapter);
					}
				});
			}
		}.start(); 

		// ��ӳ������
		lv.setOnCreateContextMenuListener(selectSeatListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int selectId = Integer.parseInt(list.get(arg2).get("selectId").toString());
            	Intent intent = new Intent();
            	intent.setClass(SelectSeatUserListActivity.this, SelectSeatDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("selectId", selectId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener selectSeatListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			//menu.add(0, 0, 0, "�༭ѡ����Ϣ"); 
			//menu.add(0, 1, 0, "ɾ��ѡ����λ");
		}
	};

	// �����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //�༭ѡ����Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡѡ��id
			selectId = Integer.parseInt(list.get(position).get("selectId").toString());
			Intent intent = new Intent();
			intent.setClass(SelectSeatUserListActivity.this, SelectSeatEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("selectId", selectId);
			intent.putExtras(bundle);
			startActivityForResult(intent,ActivityUtils.EDIT_CODE);
		} else if (item.getItemId() == 1) {// ɾ��ѡ����Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡѡ��id
			selectId = Integer.parseInt(list.get(position).get("selectId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(SelectSeatUserListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = selectSeatService.DeleteSelectSeat(selectId);
				Toast.makeText(getApplicationContext(), result, 1).show();
				setViews();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			/* ��ѯѡ����Ϣ */
			List<SelectSeat> selectSeatList = selectSeatService.QuerySelectSeat(queryConditionSelectSeat);
			for (int i = 0; i < selectSeatList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("selectId", selectSeatList.get(i).getSelectId());
				map.put("seatObj", selectSeatList.get(i).getSeatObj());
				map.put("userObj", selectSeatList.get(i).getUserObj());
				map.put("startTime", selectSeatList.get(i).getStartTime());
				map.put("endTime", selectSeatList.get(i).getEndTime());
				map.put("seatState", selectSeatList.get(i).getSeatState());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

}
