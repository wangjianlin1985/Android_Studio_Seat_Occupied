package com.mobileclient.activity;

import java.util.Date;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Seat;
import com.mobileclient.domain.SelectSeat;
import com.mobileclient.service.SeatService;
import com.mobileclient.service.SelectSeatService;
import com.mobileclient.domain.Room;
import com.mobileclient.service.RoomService;
import com.mobileclient.domain.SeatState;
import com.mobileclient.service.SeatStateService;
import com.mobileclient.util.ActivityUtils;
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
public class SeatDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn,btnSelect,btnOrder;
	
	// ������λid�ؼ�
	private TextView TV_seatId;
	// �������������ҿؼ�
	private TextView TV_roomObj;
	// ������λ��ſؼ�
	private TextView TV_seatCode;
	// ������ǰ״̬�ؼ�
	private TextView TV_seatStateObj;
	/* Ҫ�������λ��Ϣ */
	Seat seat = new Seat(); 
	/* ��λ����ҵ���߼��� */
	private SeatService seatService = new SeatService();
	private RoomService roomService = new RoomService();
	private SeatStateService seatStateService = new SeatStateService();
	private SelectSeatService selectSeatService = new SelectSeatService();
	private int seatId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.seat_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴��λ����");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_seatId = (TextView) findViewById(R.id.TV_seatId);
		TV_roomObj = (TextView) findViewById(R.id.TV_roomObj);
		TV_seatCode = (TextView) findViewById(R.id.TV_seatCode);
		TV_seatStateObj = (TextView) findViewById(R.id.TV_seatStateObj);
		Bundle extras = this.getIntent().getExtras();
		seatId = extras.getInt("seatId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SeatDetailActivity.this.finish();
			}
		}); 
	
		
		initViewData();
		
		
		btnSelect = (Button) findViewById(R.id.btnSelect);
		btnSelect.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SelectSeat selectSeat = new SelectSeat();
				Declare declare = (Declare)SeatDetailActivity.this.getApplication();
				selectSeat.setUserObj(declare.getUserName());
				selectSeat.setSeatObj(seatId);
				selectSeat.setEndTime("--");
				selectSeat.setSeatState("ռ����");
				selectSeat.setStartTime("");
				/*����ҵ���߼����ϴ���λ��Ϣ*/
				SeatDetailActivity.this.setTitle("��������λ���Ե�...");
				String result = selectSeatService.AddSelectSeat(selectSeat);
				Toast.makeText(getApplicationContext(), result, 1).show(); 
				Intent intent = getIntent();
				setResult(RESULT_OK,intent);
				finish();
			}
		}); 
		
		
		
		btnOrder = (Button) findViewById(R.id.btnOrder);
		btnOrder.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
            	bundle.putInt("seatId", seat.getSeatId());
            	intent.putExtras(bundle);
				intent.setClass(SeatDetailActivity.this, SeatOrderUserAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		}); 
		
		Declare declare = (Declare)this.getApplication();
		if(declare.getIdentify().equals("admin")) btnOrder.setVisibility(View.GONE);
		if(!(declare.getIdentify().equals("user") && seat.getSeatStateObj() == 1)) btnSelect.setVisibility(View.GONE);
		
		
		
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    seat = seatService.GetSeat(seatId); 
		this.TV_seatId.setText(seat.getSeatId() + "");
		Room roomObj = roomService.GetRoom(seat.getRoomObj());
		this.TV_roomObj.setText(roomObj.getRoomName());
		this.TV_seatCode.setText(seat.getSeatCode());
		SeatState seatStateObj = seatStateService.GetSeatState(seat.getSeatStateObj());
		this.TV_seatStateObj.setText(seatStateObj.getStateName());
	} 
}
