package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.ClassInfo;
import com.mobileclient.service.ClassInfoService;
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
public class ClassInfoDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// �����༶��ſؼ�
	private TextView TV_classNo;
	// �����༶���ƿؼ�
	private TextView TV_className;
	// �����������ڿؼ�
	private TextView TV_bornDate;
	// ���������οؼ�
	private TextView TV_mainTeacher;
	// �����༶��ע�ؼ�
	private TextView TV_classMemo;
	/* Ҫ����İ༶��Ϣ */
	ClassInfo classInfo = new ClassInfo(); 
	/* �༶����ҵ���߼��� */
	private ClassInfoService classInfoService = new ClassInfoService();
	private String classNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.classinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴�༶����");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_classNo = (TextView) findViewById(R.id.TV_classNo);
		TV_className = (TextView) findViewById(R.id.TV_className);
		TV_bornDate = (TextView) findViewById(R.id.TV_bornDate);
		TV_mainTeacher = (TextView) findViewById(R.id.TV_mainTeacher);
		TV_classMemo = (TextView) findViewById(R.id.TV_classMemo);
		Bundle extras = this.getIntent().getExtras();
		classNo = extras.getString("classNo");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ClassInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    classInfo = classInfoService.GetClassInfo(classNo); 
		this.TV_classNo.setText(classInfo.getClassNo());
		this.TV_className.setText(classInfo.getClassName());
		Date bornDate = new Date(classInfo.getBornDate().getTime());
		String bornDateStr = (bornDate.getYear() + 1900) + "-" + (bornDate.getMonth()+1) + "-" + bornDate.getDate();
		this.TV_bornDate.setText(bornDateStr);
		this.TV_mainTeacher.setText(classInfo.getMainTeacher());
		this.TV_classMemo.setText(classInfo.getClassMemo());
	} 
}
