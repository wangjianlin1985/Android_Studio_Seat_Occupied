package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Seat;
import com.mobileclient.service.SeatService;
import com.mobileclient.domain.Room;
import com.mobileclient.service.RoomService;
import com.mobileclient.domain.SeatState;
import com.mobileclient.service.SeatStateService;
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
public class SeatAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// ��������������������
	private Spinner spinner_roomObj;
	private ArrayAdapter<String> roomObj_adapter;
	private static  String[] roomObj_ShowText  = null;
	private List<Room> roomList = null;
	/*���������ҹ���ҵ���߼���*/
	private RoomService roomService = new RoomService();
	// ������λ��������
	private EditText ET_seatCode;
	// ������ǰ״̬������
	private Spinner spinner_seatStateObj;
	private ArrayAdapter<String> seatStateObj_adapter;
	private static  String[] seatStateObj_ShowText  = null;
	private List<SeatState> seatStateList = null;
	/*��ǰ״̬����ҵ���߼���*/
	private SeatStateService seatStateService = new SeatStateService();
	protected String carmera_path;
	/*Ҫ�������λ��Ϣ*/
	Seat seat = new Seat();
	/*��λ����ҵ���߼���*/
	private SeatService seatService = new SeatService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.seat_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�����λ");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		spinner_roomObj = (Spinner) findViewById(R.id.Spinner_roomObj);
		// ��ȡ���е�����������
		try {
			roomList = roomService.QueryRoom(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int roomCount = roomList.size();
		roomObj_ShowText = new String[roomCount];
		for(int i=0;i<roomCount;i++) { 
			roomObj_ShowText[i] = roomList.get(i).getRoomName();
		}
		// ����ѡ������ArrayAdapter��������
		roomObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, roomObj_ShowText);
		// ���������б�ķ��
		roomObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_roomObj.setAdapter(roomObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_roomObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				seat.setRoomObj(roomList.get(arg2).getRoomId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_roomObj.setVisibility(View.VISIBLE);
		ET_seatCode = (EditText) findViewById(R.id.ET_seatCode);
		spinner_seatStateObj = (Spinner) findViewById(R.id.Spinner_seatStateObj);
		// ��ȡ���еĵ�ǰ״̬
		try {
			seatStateList = seatStateService.QuerySeatState(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int seatStateCount = seatStateList.size();
		seatStateObj_ShowText = new String[seatStateCount];
		for(int i=0;i<seatStateCount;i++) { 
			seatStateObj_ShowText[i] = seatStateList.get(i).getStateName();
		}
		// ����ѡ������ArrayAdapter��������
		seatStateObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, seatStateObj_ShowText);
		// ���������б�ķ��
		seatStateObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_seatStateObj.setAdapter(seatStateObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_seatStateObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				seat.setSeatStateObj(seatStateList.get(arg2).getStateId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_seatStateObj.setVisibility(View.VISIBLE);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*���������λ��ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��λ���*/ 
					if(ET_seatCode.getText().toString().equals("")) {
						Toast.makeText(SeatAddActivity.this, "��λ������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_seatCode.setFocusable(true);
						ET_seatCode.requestFocus();
						return;	
					}
					seat.setSeatCode(ET_seatCode.getText().toString());
					/*����ҵ���߼����ϴ���λ��Ϣ*/
					SeatAddActivity.this.setTitle("�����ϴ���λ��Ϣ���Ե�...");
					String result = seatService.AddSeat(seat);
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
