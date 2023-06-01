package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.UserType;
import com.mobileclient.service.UserTypeService;
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

public class UserTypeEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// �����û�����idTextView
	private TextView TV_userTypeId;
	// �����û��������������
	private EditText ET_userTypeName;
	protected String carmera_path;
	/*Ҫ������û�������Ϣ*/
	UserType userType = new UserType();
	/*�û����͹���ҵ���߼���*/
	private UserTypeService userTypeService = new UserTypeService();

	private int userTypeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.usertype_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�༭�û�������Ϣ");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_userTypeId = (TextView) findViewById(R.id.TV_userTypeId);
		ET_userTypeName = (EditText) findViewById(R.id.ET_userTypeName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		userTypeId = extras.getInt("userTypeId");
		/*�����޸��û����Ͱ�ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ�û���������*/ 
					if(ET_userTypeName.getText().toString().equals("")) {
						Toast.makeText(UserTypeEditActivity.this, "�û������������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_userTypeName.setFocusable(true);
						ET_userTypeName.requestFocus();
						return;	
					}
					userType.setUserTypeName(ET_userTypeName.getText().toString());
					/*����ҵ���߼����ϴ��û�������Ϣ*/
					UserTypeEditActivity.this.setTitle("���ڸ����û�������Ϣ���Ե�...");
					String result = userTypeService.UpdateUserType(userType);
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
	    userType = userTypeService.GetUserType(userTypeId);
		this.TV_userTypeId.setText(userTypeId+"");
		this.ET_userTypeName.setText(userType.getUserTypeName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
