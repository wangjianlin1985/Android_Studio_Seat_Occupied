package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Jc;
import com.mobileclient.service.JcService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
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
public class JcDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ��������id�ؼ�
	private TextView TV_jcId;
	// �����������Ϳؼ�
	private TextView TV_jcType;
	// �������ͱ���ؼ�
	private TextView TV_title;
	// �����������ݿؼ�
	private TextView TV_content;
	// ���������û��ؼ�
	private TextView TV_userObj;
	// �������÷����ؼ�
	private TextView TV_creditScore;
	// ��������ʱ��ؼ�
	private TextView TV_jcTime;
	/* Ҫ����Ľ�����Ϣ */
	Jc jc = new Jc(); 
	/* ���͹���ҵ���߼��� */
	private JcService jcService = new JcService();
	private UserInfoService userInfoService = new UserInfoService();
	private int jcId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.jc_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴��������");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_jcId = (TextView) findViewById(R.id.TV_jcId);
		TV_jcType = (TextView) findViewById(R.id.TV_jcType);
		TV_title = (TextView) findViewById(R.id.TV_title);
		TV_content = (TextView) findViewById(R.id.TV_content);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_creditScore = (TextView) findViewById(R.id.TV_creditScore);
		TV_jcTime = (TextView) findViewById(R.id.TV_jcTime);
		Bundle extras = this.getIntent().getExtras();
		jcId = extras.getInt("jcId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				JcDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    jc = jcService.GetJc(jcId); 
		this.TV_jcId.setText(jc.getJcId() + "");
		this.TV_jcType.setText(jc.getJcType());
		this.TV_title.setText(jc.getTitle());
		this.TV_content.setText(jc.getContent());
		UserInfo userObj = userInfoService.GetUserInfo(jc.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_creditScore.setText(jc.getCreditScore() + "");
		this.TV_jcTime.setText(jc.getJcTime());
	} 
}
