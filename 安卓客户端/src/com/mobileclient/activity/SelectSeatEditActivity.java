package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.SelectSeat;
import com.mobileclient.service.SelectSeatService;
import com.mobileclient.domain.Seat;
import com.mobileclient.service.SeatService;
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

public class SelectSeatEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// ����ѡ��idTextView
	private TextView TV_selectId;
	// ������λ���������
	private Spinner spinner_seatObj;
	private ArrayAdapter<String> seatObj_adapter;
	private static  String[] seatObj_ShowText  = null;
	private List<Seat> seatList = null;
	/*��λ��Ź���ҵ���߼���*/
	private SeatService seatService = new SeatService();
	// ����ѡ���û�������
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*ѡ���û�����ҵ���߼���*/
	private UserInfoService userInfoService = new UserInfoService();
	// ����ѡ����ʼʱ�������
	private EditText ET_startTime;
	// ����ѡ������ʱ�������
	private EditText ET_endTime;
	// ����ѡ��״̬�����
	private EditText ET_seatState;
	protected String carmera_path;
	/*Ҫ�����ѡ����Ϣ*/
	SelectSeat selectSeat = new SelectSeat();
	/*ѡ������ҵ���߼���*/
	private SelectSeatService selectSeatService = new SelectSeatService();

	private int selectId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.selectseat_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�༭ѡ����Ϣ");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_selectId = (TextView) findViewById(R.id.TV_selectId);
		spinner_seatObj = (Spinner) findViewById(R.id.Spinner_seatObj);
		// ��ȡ���е���λ���
		try {
			seatList = seatService.QuerySeat(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int seatCount = seatList.size();
		seatObj_ShowText = new String[seatCount];
		for(int i=0;i<seatCount;i++) { 
			seatObj_ShowText[i] = seatList.get(i).getSeatCode();
		}
		// ����ѡ������ArrayAdapter��������
		seatObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, seatObj_ShowText);
		// ����ͼ����������б�ķ��
		seatObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_seatObj.setAdapter(seatObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_seatObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				selectSeat.setSeatObj(seatList.get(arg2).getSeatId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_seatObj.setVisibility(View.VISIBLE);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// ��ȡ���е�ѡ���û�
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
				selectSeat.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_startTime = (EditText) findViewById(R.id.ET_startTime);
		ET_endTime = (EditText) findViewById(R.id.ET_endTime);
		ET_seatState = (EditText) findViewById(R.id.ET_seatState);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		selectId = extras.getInt("selectId");
		/*�����޸�ѡ����ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡѡ����ʼʱ��*/ 
					if(ET_startTime.getText().toString().equals("")) {
						Toast.makeText(SelectSeatEditActivity.this, "ѡ����ʼʱ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_startTime.setFocusable(true);
						ET_startTime.requestFocus();
						return;	
					}
					selectSeat.setStartTime(ET_startTime.getText().toString());
					/*��֤��ȡѡ������ʱ��*/ 
					if(ET_endTime.getText().toString().equals("")) {
						Toast.makeText(SelectSeatEditActivity.this, "ѡ������ʱ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_endTime.setFocusable(true);
						ET_endTime.requestFocus();
						return;	
					}
					selectSeat.setEndTime(ET_endTime.getText().toString());
					/*��֤��ȡѡ��״̬*/ 
					if(ET_seatState.getText().toString().equals("")) {
						Toast.makeText(SelectSeatEditActivity.this, "ѡ��״̬���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_seatState.setFocusable(true);
						ET_seatState.requestFocus();
						return;	
					}
					selectSeat.setSeatState(ET_seatState.getText().toString());
					/*����ҵ���߼����ϴ�ѡ����Ϣ*/
					SelectSeatEditActivity.this.setTitle("���ڸ���ѡ����Ϣ���Ե�...");
					String result = selectSeatService.UpdateSelectSeat(selectSeat);
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
	    selectSeat = selectSeatService.GetSelectSeat(selectId);
		this.TV_selectId.setText(selectId+"");
		for (int i = 0; i < seatList.size(); i++) {
			if (selectSeat.getSeatObj() == seatList.get(i).getSeatId()) {
				this.spinner_seatObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < userInfoList.size(); i++) {
			if (selectSeat.getUserObj().equals(userInfoList.get(i).getUser_name())) {
				this.spinner_userObj.setSelection(i);
				break;
			}
		}
		this.ET_startTime.setText(selectSeat.getStartTime());
		this.ET_endTime.setText(selectSeat.getEndTime());
		this.ET_seatState.setText(selectSeat.getSeatState());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
