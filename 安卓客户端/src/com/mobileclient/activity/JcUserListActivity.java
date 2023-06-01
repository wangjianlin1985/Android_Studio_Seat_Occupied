package com.mobileclient.activity;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Jc;
import com.mobileclient.service.JcService;
import com.mobileclient.util.ActivityUtils;import com.mobileclient.util.JcSimpleAdapter;
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

public class JcUserListActivity extends Activity {
	JcSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int jcId;
	/* ���Ͳ���ҵ���߼������ */
	JcService jcService = new JcService();
	/*�����ѯ���������Ľ��Ͷ���*/
	private Jc queryConditionJc;

	private MyProgressDialog dialog; //������	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.jc_list);
		dialog = MyProgressDialog.getInstance(this);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		//�������ؼ�
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setViews();
				//Intent intent = new Intent();
				//intent.setClass(JcUserListActivity.this, JcQueryActivity.class);
				//startActivityForResult(intent,ActivityUtils.QUERY_CODE);//�˴���requestCodeӦ�������������е��õ�requestCodeһ��
			}
		});
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("���Ͳ�ѯ�б�");
		ImageView add_btn = (ImageView) this.findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new android.view.View.OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(JcUserListActivity.this, JcAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		});
		add_btn.setVisibility(View.GONE);
		
		queryConditionJc = new Jc();
		queryConditionJc.setJcType("");
		queryConditionJc.setTitle("");
		queryConditionJc.setUserObj(username);
		queryConditionJc.setJcTime("");
		 
		setViews();
	}

	//���������������secondActivity�з���ʱ���ô˺���
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ActivityUtils.QUERY_CODE && resultCode==RESULT_OK){
        	Bundle extras = data.getExtras();
        	if(extras != null)
        		queryConditionJc = (Jc)extras.getSerializable("queryConditionJc");
        	setViews();
        }
        if(requestCode==ActivityUtils.EDIT_CODE && resultCode==RESULT_OK){
        	setViews();
        }
        if(requestCode == ActivityUtils.ADD_CODE && resultCode == RESULT_OK) {
        	queryConditionJc = null;
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
						adapter = new JcSimpleAdapter(JcUserListActivity.this, list,
	        					R.layout.jc_list_item,
	        					new String[] { "jcId","jcType","title","userObj","creditScore","jcTime" },
	        					new int[] { R.id.tv_jcId,R.id.tv_jcType,R.id.tv_title,R.id.tv_userObj,R.id.tv_creditScore,R.id.tv_jcTime,},lv);
	        			lv.setAdapter(adapter);
					}
				});
			}
		}.start(); 

		// ��ӳ������
		lv.setOnCreateContextMenuListener(jcListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int jcId = Integer.parseInt(list.get(arg2).get("jcId").toString());
            	Intent intent = new Intent();
            	intent.setClass(JcUserListActivity.this, JcDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("jcId", jcId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener jcListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			//menu.add(0, 0, 0, "�༭������Ϣ"); 
			//menu.add(0, 1, 0, "ɾ��������Ϣ");
		}
	};

	// �����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //�༭������Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ����id
			jcId = Integer.parseInt(list.get(position).get("jcId").toString());
			Intent intent = new Intent();
			intent.setClass(JcUserListActivity.this, JcEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("jcId", jcId);
			intent.putExtras(bundle);
			startActivityForResult(intent,ActivityUtils.EDIT_CODE);
		} else if (item.getItemId() == 1) {// ɾ��������Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ����id
			jcId = Integer.parseInt(list.get(position).get("jcId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(JcUserListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = jcService.DeleteJc(jcId);
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
			/* ��ѯ������Ϣ */
			List<Jc> jcList = jcService.QueryJc(queryConditionJc);
			for (int i = 0; i < jcList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("jcId", jcList.get(i).getJcId());
				map.put("jcType", jcList.get(i).getJcType());
				map.put("title", jcList.get(i).getTitle());
				map.put("userObj", jcList.get(i).getUserObj());
				map.put("creditScore", jcList.get(i).getCreditScore());
				map.put("jcTime", jcList.get(i).getJcTime());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

}
