package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Seat;
import com.mobileclient.domain.Room;
import com.mobileclient.service.RoomService;
import com.mobileclient.domain.SeatState;
import com.mobileclient.service.SeatStateService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class SeatQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
	// ��������������������
	private Spinner spinner_roomObj;
	private ArrayAdapter<String> roomObj_adapter;
	private static  String[] roomObj_ShowText  = null;
	private List<Room> roomList = null; 
	/*�����ҹ���ҵ���߼���*/
	private RoomService roomService = new RoomService();
	// ������λ��������
	private EditText ET_seatCode;
	// ������ǰ״̬������
	private Spinner spinner_seatStateObj;
	private ArrayAdapter<String> seatStateObj_adapter;
	private static  String[] seatStateObj_ShowText  = null;
	private List<SeatState> seatStateList = null; 
	/*��λ״̬����ҵ���߼���*/
	private SeatStateService seatStateService = new SeatStateService();
	/*��ѯ�����������浽���������*/
	private Seat queryConditionSeat = new Seat();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.seat_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("������λ��ѯ����");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_roomObj = (Spinner) findViewById(R.id.Spinner_roomObj);
		// ��ȡ���е�������
		try {
			roomList = roomService.QueryRoom(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int roomCount = roomList.size();
		roomObj_ShowText = new String[roomCount+1];
		roomObj_ShowText[0] = "������";
		for(int i=1;i<=roomCount;i++) { 
			roomObj_ShowText[i] = roomList.get(i-1).getRoomName();
		} 
		// ����ѡ������ArrayAdapter��������
		roomObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, roomObj_ShowText);
		// �������������������б�ķ��
		roomObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_roomObj.setAdapter(roomObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_roomObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionSeat.setRoomObj(roomList.get(arg2-1).getRoomId()); 
				else
					queryConditionSeat.setRoomObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_roomObj.setVisibility(View.VISIBLE);
		ET_seatCode = (EditText) findViewById(R.id.ET_seatCode);
		spinner_seatStateObj = (Spinner) findViewById(R.id.Spinner_seatStateObj);
		// ��ȡ���е���λ״̬
		try {
			seatStateList = seatStateService.QuerySeatState(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int seatStateCount = seatStateList.size();
		seatStateObj_ShowText = new String[seatStateCount+1];
		seatStateObj_ShowText[0] = "������";
		for(int i=1;i<=seatStateCount;i++) { 
			seatStateObj_ShowText[i] = seatStateList.get(i-1).getStateName();
		} 
		// ����ѡ������ArrayAdapter��������
		seatStateObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, seatStateObj_ShowText);
		// ���õ�ǰ״̬�����б�ķ��
		seatStateObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_seatStateObj.setAdapter(seatStateObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_seatStateObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionSeat.setSeatStateObj(seatStateList.get(arg2-1).getStateId()); 
				else
					queryConditionSeat.setSeatStateObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_seatStateObj.setVisibility(View.VISIBLE);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					queryConditionSeat.setSeatCode(ET_seatCode.getText().toString());
					Intent intent = getIntent();
					//����ʹ��bundle��������������
					Bundle bundle =new Bundle();
					//�����������Ȼ�Ǽ�ֵ�Ե���ʽ
					bundle.putSerializable("queryConditionSeat", queryConditionSeat);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
