package com.mobileclient.activity;

import java.util.Date;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.SelectSeat;
import com.mobileclient.service.SelectSeatService;
import com.mobileclient.domain.Seat;
import com.mobileclient.service.SeatService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.content.Intent;
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
public class SelectSeatDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn,btnLeave;
	// ����ѡ��id�ؼ�
	private TextView TV_selectId;
	// ������λ��ſؼ�
	private TextView TV_seatObj;
	// ����ѡ���û��ؼ�
	private TextView TV_userObj;
	// ����ѡ����ʼʱ��ؼ�
	private TextView TV_startTime;
	// ����ѡ������ʱ��ؼ�
	private TextView TV_endTime;
	// ����ѡ��״̬�ؼ�
	private TextView TV_seatState;
	/* Ҫ�����ѡ����Ϣ */
	SelectSeat selectSeat = new SelectSeat(); 
	/* ѡ������ҵ���߼��� */
	private SelectSeatService selectSeatService = new SelectSeatService();
	private SeatService seatService = new SeatService();
	private UserInfoService userInfoService = new UserInfoService();
	private int selectId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.selectseat_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴ѡ������");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_selectId = (TextView) findViewById(R.id.TV_selectId);
		TV_seatObj = (TextView) findViewById(R.id.TV_seatObj);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_startTime = (TextView) findViewById(R.id.TV_startTime);
		TV_endTime = (TextView) findViewById(R.id.TV_endTime);
		TV_seatState = (TextView) findViewById(R.id.TV_seatState);
		Bundle extras = this.getIntent().getExtras();
		selectId = extras.getInt("selectId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SelectSeatDetailActivity.this.finish();
			}
		}); 
		
		btnLeave = (Button) findViewById(R.id.btnLeave);
		btnLeave.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub 
				String result = selectSeatService.leaveSeat(selectSeat.getSelectId());
				Toast.makeText(getApplicationContext(), result, 1).show(); 
				Intent intent = getIntent();
				setResult(RESULT_OK,intent);
				finish();
			}
		});
		
		
		initViewData();
		
		Declare declare = (Declare) this.getApplication();
		if(declare.getIdentify().equals("user") && selectSeat.getSeatState().equals("ռ����")) {
			btnLeave.setVisibility(View.VISIBLE);
		}
		
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    selectSeat = selectSeatService.GetSelectSeat(selectId); 
		this.TV_selectId.setText(selectSeat.getSelectId() + "");
		Seat seatObj = seatService.GetSeat(selectSeat.getSeatObj());
		this.TV_seatObj.setText(seatObj.getSeatCode());
		UserInfo userObj = userInfoService.GetUserInfo(selectSeat.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_startTime.setText(selectSeat.getStartTime());
		this.TV_endTime.setText(selectSeat.getEndTime());
		this.TV_seatState.setText(selectSeat.getSeatState());
	} 
}
