package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Jc;
import com.mobileclient.service.JcService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class JcEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// ��������idTextView
	private TextView TV_jcId;
	// �����������������
	private EditText ET_jcType;
	// �������ͱ��������
	private EditText ET_title;
	// �����������������
	private EditText ET_content;
	// ���������û�������
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*�����û�����ҵ���߼���*/
	private UserInfoService userInfoService = new UserInfoService();
	// �������÷��������
	private EditText ET_creditScore;
	// ��������ʱ�������
	private EditText ET_jcTime;
	protected String carmera_path;
	/*Ҫ����Ľ�����Ϣ*/
	Jc jc = new Jc();
	/*���͹���ҵ���߼���*/
	private JcService jcService = new JcService();

	private int jcId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.jc_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�༭������Ϣ");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_jcId = (TextView) findViewById(R.id.TV_jcId);
		ET_jcType = (EditText) findViewById(R.id.ET_jcType);
		ET_title = (EditText) findViewById(R.id.ET_title);
		ET_content = (EditText) findViewById(R.id.ET_content);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// ��ȡ���еĽ����û�
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount];
		for(int i=0;i<userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i).getName();
		}
		// ����ѡ������ArrayAdapter��������
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// ����ͼ����������б�ķ��
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_userObj.setAdapter(userObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				jc.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_creditScore = (EditText) findViewById(R.id.ET_creditScore);
		ET_jcTime = (EditText) findViewById(R.id.ET_jcTime);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		jcId = extras.getInt("jcId");
		/*�����޸Ľ��Ͱ�ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��������*/ 
					if(ET_jcType.getText().toString().equals("")) {
						Toast.makeText(JcEditActivity.this, "�����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_jcType.setFocusable(true);
						ET_jcType.requestFocus();
						return;	
					}
					jc.setJcType(ET_jcType.getText().toString());
					/*��֤��ȡ���ͱ���*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(JcEditActivity.this, "���ͱ������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					jc.setTitle(ET_title.getText().toString());
					/*��֤��ȡ��������*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(JcEditActivity.this, "�����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					jc.setContent(ET_content.getText().toString());
					/*��֤��ȡ���÷���*/ 
					if(ET_creditScore.getText().toString().equals("")) {
						Toast.makeText(JcEditActivity.this, "���÷������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_creditScore.setFocusable(true);
						ET_creditScore.requestFocus();
						return;	
					}
					jc.setCreditScore(Float.parseFloat(ET_creditScore.getText().toString()));
					/*��֤��ȡ����ʱ��*/ 
					if(ET_jcTime.getText().toString().equals("")) {
						Toast.makeText(JcEditActivity.this, "����ʱ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_jcTime.setFocusable(true);
						ET_jcTime.requestFocus();
						return;	
					}
					jc.setJcTime(ET_jcTime.getText().toString());
					/*����ҵ���߼����ϴ�������Ϣ*/
					JcEditActivity.this.setTitle("���ڸ��½�����Ϣ���Ե�...");
					String result = jcService.UpdateJc(jc);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* ��ʼ����ʾ�༭��������� */
	private void initViewData() {
	    jc = jcService.GetJc(jcId);
		this.TV_jcId.setText(jcId+"");
		this.ET_jcType.setText(jc.getJcType());
		this.ET_title.setText(jc.getTitle());
		this.ET_content.setText(jc.getContent());
		for (int i = 0; i < userInfoList.size(); i++) {
			if (jc.getUserObj().equals(userInfoList.get(i).getUser_name())) {
				this.spinner_userObj.setSelection(i);
				break;
			}
		}
		this.ET_creditScore.setText(jc.getCreditScore() + "");
		this.ET_jcTime.setText(jc.getJcTime());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
