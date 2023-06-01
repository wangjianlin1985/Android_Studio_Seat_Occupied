package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Room;
import com.mobileclient.service.RoomService;
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
public class RoomAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// �������������������
	private EditText ET_roomName;
	// ������������ƬͼƬ��ؼ�
	private ImageView iv_roomPhoto;
	private Button btn_roomPhoto;
	protected int REQ_CODE_SELECT_IMAGE_roomPhoto = 1;
	private int REQ_CODE_CAMERA_roomPhoto = 2;
	// ����������λ�������
	private EditText ET_roomPlace;
	// ��������λ�������
	private EditText ET_seatNum;
	protected String carmera_path;
	/*Ҫ�������������Ϣ*/
	Room room = new Room();
	/*�����ҹ���ҵ���߼���*/
	private RoomService roomService = new RoomService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.room_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("���������");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_roomName = (EditText) findViewById(R.id.ET_roomName);
		iv_roomPhoto = (ImageView) findViewById(R.id.iv_roomPhoto);
		/*����ͼƬ��ʾ�ؼ�ʱ����ͼƬ��ѡ��*/
		iv_roomPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(RoomAddActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_roomPhoto);
			}
		});
		btn_roomPhoto = (Button) findViewById(R.id.btn_roomPhoto);
		btn_roomPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_roomPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_roomPhoto);  
			}
		});
		ET_roomPlace = (EditText) findViewById(R.id.ET_roomPlace);
		ET_seatNum = (EditText) findViewById(R.id.ET_seatNum);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*������������Ұ�ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ����������*/ 
					if(ET_roomName.getText().toString().equals("")) {
						Toast.makeText(RoomAddActivity.this, "�������������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_roomName.setFocusable(true);
						ET_roomName.requestFocus();
						return;	
					}
					room.setRoomName(ET_roomName.getText().toString());
					if(room.getRoomPhoto() != null) {
						//���ͼƬ��ַ��Ϊ�գ�˵���û�ѡ����ͼƬ����ʱ��Ҫ���ӷ������ϴ�ͼƬ
						RoomAddActivity.this.setTitle("�����ϴ�ͼƬ���Ե�...");
						String roomPhoto = HttpUtil.uploadFile(room.getRoomPhoto());
						RoomAddActivity.this.setTitle("ͼƬ�ϴ���ϣ�");
						room.setRoomPhoto(roomPhoto);
					} else {
						room.setRoomPhoto("upload/noimage.jpg");
					}
					/*��֤��ȡ������λ��*/ 
					if(ET_roomPlace.getText().toString().equals("")) {
						Toast.makeText(RoomAddActivity.this, "������λ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_roomPlace.setFocusable(true);
						ET_roomPlace.requestFocus();
						return;	
					}
					room.setRoomPlace(ET_roomPlace.getText().toString());
					/*��֤��ȡ����λ��*/ 
					if(ET_seatNum.getText().toString().equals("")) {
						Toast.makeText(RoomAddActivity.this, "����λ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_seatNum.setFocusable(true);
						ET_seatNum.requestFocus();
						return;	
					}
					room.setSeatNum(Integer.parseInt(ET_seatNum.getText().toString()));
					/*����ҵ���߼����ϴ���������Ϣ*/
					RoomAddActivity.this.setTitle("�����ϴ���������Ϣ���Ե�...");
					String result = roomService.AddRoom(room);
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
		if (requestCode == REQ_CODE_CAMERA_roomPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_roomPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_roomPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// ������д���ļ� 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_roomPhoto.setImageBitmap(booImageBm);
				this.iv_roomPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.room.setRoomPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_roomPhoto && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_roomPhoto.setImageBitmap(bm); 
				this.iv_roomPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			room.setRoomPhoto(filename); 
		}
	}
}
