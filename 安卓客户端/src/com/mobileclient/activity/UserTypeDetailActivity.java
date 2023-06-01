package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.UserType;
import com.mobileclient.service.UserTypeService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class UserTypeDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// �����û�����id�ؼ�
	private TextView TV_userTypeId;
	// �����û��������ƿؼ�
	private TextView TV_userTypeName;
	/* Ҫ������û�������Ϣ */
	UserType userType = new UserType(); 
	/* �û����͹���ҵ���߼��� */
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
		setContentView(R.layout.usertype_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴�û���������");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_userTypeId = (TextView) findViewById(R.id.TV_userTypeId);
		TV_userTypeName = (TextView) findViewById(R.id.TV_userTypeName);
		Bundle extras = this.getIntent().getExtras();
		userTypeId = extras.getInt("userTypeId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				UserTypeDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    userType = userTypeService.GetUserType(userTypeId); 
		this.TV_userTypeId.setText(userType.getUserTypeId() + "");
		this.TV_userTypeName.setText(userType.getUserTypeName());
	} 
}
