package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Jubao;
import com.mobileclient.service.JubaoService;
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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class JubaoAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// �����ٱ����������
	private EditText ET_title;
	// �����ٱ����������
	private EditText ET_content;
	// �����ٱ��û�������
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*�ٱ��û�����ҵ���߼���*/
	private UserInfoService userInfoService = new UserInfoService();
	// �����ٱ�ʱ�������
	private EditText ET_jubaoTime;
	// ��������ظ������
	private EditText ET_replyContent;
	protected String carmera_path;
	/*Ҫ����ľٱ���Ϣ*/
	Jubao jubao = new Jubao();
	/*�ٱ�����ҵ���߼���*/
	private JubaoService jubaoService = new JubaoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.jubao_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("��Ӿٱ�");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_title = (EditText) findViewById(R.id.ET_title);
		ET_content = (EditText) findViewById(R.id.ET_content);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// ��ȡ���еľٱ��û�
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
		// ���������б�ķ��
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_userObj.setAdapter(userObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				jubao.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_jubaoTime = (EditText) findViewById(R.id.ET_jubaoTime);
		ET_replyContent = (EditText) findViewById(R.id.ET_replyContent);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*������Ӿٱ���ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ�ٱ�����*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(JubaoAddActivity.this, "�ٱ��������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					jubao.setTitle(ET_title.getText().toString());
					/*��֤��ȡ�ٱ�����*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(JubaoAddActivity.this, "�ٱ��������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					jubao.setContent(ET_content.getText().toString());
					/*��֤��ȡ�ٱ�ʱ��*/ 
					if(ET_jubaoTime.getText().toString().equals("")) {
						Toast.makeText(JubaoAddActivity.this, "�ٱ�ʱ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_jubaoTime.setFocusable(true);
						ET_jubaoTime.requestFocus();
						return;	
					}
					jubao.setJubaoTime(ET_jubaoTime.getText().toString());
					/*��֤��ȡ����ظ�*/ 
					if(ET_replyContent.getText().toString().equals("")) {
						Toast.makeText(JubaoAddActivity.this, "����ظ����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_replyContent.setFocusable(true);
						ET_replyContent.requestFocus();
						return;	
					}
					jubao.setReplyContent(ET_replyContent.getText().toString());
					/*����ҵ���߼����ϴ��ٱ���Ϣ*/
					JubaoAddActivity.this.setTitle("�����ϴ��ٱ���Ϣ���Ե�...");
					String result = jubaoService.AddJubao(jubao);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
