package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.domain.UserType;
import com.mobileclient.service.UserTypeService;
import com.mobileclient.domain.ClassInfo;
import com.mobileclient.service.ClassInfoService;
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
public class UserInfoAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明用户名输入框
	private EditText ET_user_name;
	// 声明登录密码输入框
	private EditText ET_password;
	// 声明用户类型下拉框
	private Spinner spinner_userTypeObj;
	private ArrayAdapter<String> userTypeObj_adapter;
	private static  String[] userTypeObj_ShowText  = null;
	private List<UserType> userTypeList = null;
	/*用户类型管理业务逻辑层*/
	private UserTypeService userTypeService = new UserTypeService();
	// 声明所在班级下拉框
	private Spinner spinner_classObj;
	private ArrayAdapter<String> classObj_adapter;
	private static  String[] classObj_ShowText  = null;
	private List<ClassInfo> classInfoList = null;
	/*所在班级管理业务逻辑层*/
	private ClassInfoService classInfoService = new ClassInfoService();
	// 声明姓名输入框
	private EditText ET_name;
	// 声明性别输入框
	private EditText ET_gender;
	// 出版出生日期控件
	private DatePicker dp_birthDate;
	// 声明用户照片图片框控件
	private ImageView iv_userPhoto;
	private Button btn_userPhoto;
	protected int REQ_CODE_SELECT_IMAGE_userPhoto = 1;
	private int REQ_CODE_CAMERA_userPhoto = 2;
	// 声明联系电话输入框
	private EditText ET_telephone;
	// 声明邮箱输入框
	private EditText ET_email;
	// 声明家庭地址输入框
	private EditText ET_address;
	// 声明是否黑名单输入框
	private EditText ET_blackFlag;
	// 声明信用分输入框
	private EditText ET_creditScore;
	// 声明注册时间输入框
	private EditText ET_regTime;
	protected String carmera_path;
	/*要保存的用户信息*/
	UserInfo userInfo = new UserInfo();
	/*用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.userinfo_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加用户");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_user_name = (EditText) findViewById(R.id.ET_user_name);
		ET_password = (EditText) findViewById(R.id.ET_password);
		spinner_userTypeObj = (Spinner) findViewById(R.id.Spinner_userTypeObj);
		// 获取所有的用户类型
		try {
			userTypeList = userTypeService.QueryUserType(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int userTypeCount = userTypeList.size();
		userTypeObj_ShowText = new String[userTypeCount];
		for(int i=0;i<userTypeCount;i++) { 
			userTypeObj_ShowText[i] = userTypeList.get(i).getUserTypeName();
		}
		// 将可选内容与ArrayAdapter连接起来
		userTypeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userTypeObj_ShowText);
		// 设置下拉列表的风格
		userTypeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userTypeObj.setAdapter(userTypeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userTypeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				userInfo.setUserTypeObj(userTypeList.get(arg2).getUserTypeId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userTypeObj.setVisibility(View.VISIBLE);
		spinner_classObj = (Spinner) findViewById(R.id.Spinner_classObj);
		// 获取所有的所在班级
		try {
			classInfoList = classInfoService.QueryClassInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int classInfoCount = classInfoList.size();
		classObj_ShowText = new String[classInfoCount];
		for(int i=0;i<classInfoCount;i++) { 
			classObj_ShowText[i] = classInfoList.get(i).getClassName();
		}
		// 将可选内容与ArrayAdapter连接起来
		classObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, classObj_ShowText);
		// 设置下拉列表的风格
		classObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_classObj.setAdapter(classObj_adapter);
		// 添加事件Spinner事件监听
		spinner_classObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				userInfo.setClassObj(classInfoList.get(arg2).getClassNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_classObj.setVisibility(View.VISIBLE);
		ET_name = (EditText) findViewById(R.id.ET_name);
		ET_gender = (EditText) findViewById(R.id.ET_gender);
		dp_birthDate = (DatePicker)this.findViewById(R.id.dp_birthDate);
		iv_userPhoto = (ImageView) findViewById(R.id.iv_userPhoto);
		/*单击图片显示控件时进行图片的选择*/
		iv_userPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(UserInfoAddActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_userPhoto);
			}
		});
		btn_userPhoto = (Button) findViewById(R.id.btn_userPhoto);
		btn_userPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_userPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_userPhoto);  
			}
		});
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_email = (EditText) findViewById(R.id.ET_email);
		ET_address = (EditText) findViewById(R.id.ET_address);
		ET_blackFlag = (EditText) findViewById(R.id.ET_blackFlag);
		ET_creditScore = (EditText) findViewById(R.id.ET_creditScore);
		ET_regTime = (EditText) findViewById(R.id.ET_regTime);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加用户按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取用户名*/
					if(ET_user_name.getText().toString().equals("")) {
						Toast.makeText(UserInfoAddActivity.this, "用户名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_user_name.setFocusable(true);
						ET_user_name.requestFocus();
						return;
					}
					userInfo.setUser_name(ET_user_name.getText().toString());
					/*验证获取登录密码*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(UserInfoAddActivity.this, "登录密码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					userInfo.setPassword(ET_password.getText().toString());
					/*验证获取姓名*/ 
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(UserInfoAddActivity.this, "姓名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;	
					}
					userInfo.setName(ET_name.getText().toString());
					/*验证获取性别*/ 
					if(ET_gender.getText().toString().equals("")) {
						Toast.makeText(UserInfoAddActivity.this, "性别输入不能为空!", Toast.LENGTH_LONG).show();
						ET_gender.setFocusable(true);
						ET_gender.requestFocus();
						return;	
					}
					userInfo.setGender(ET_gender.getText().toString());
					/*获取出生日期*/
					Date birthDate = new Date(dp_birthDate.getYear()-1900,dp_birthDate.getMonth(),dp_birthDate.getDayOfMonth());
					userInfo.setBirthDate(new Timestamp(birthDate.getTime()));
					if(userInfo.getUserPhoto() != null) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						UserInfoAddActivity.this.setTitle("正在上传图片，稍等...");
						String userPhoto = HttpUtil.uploadFile(userInfo.getUserPhoto());
						UserInfoAddActivity.this.setTitle("图片上传完毕！");
						userInfo.setUserPhoto(userPhoto);
					} else {
						userInfo.setUserPhoto("upload/noimage.jpg");
					}
					/*验证获取联系电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(UserInfoAddActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					userInfo.setTelephone(ET_telephone.getText().toString());
					/*验证获取邮箱*/ 
					if(ET_email.getText().toString().equals("")) {
						Toast.makeText(UserInfoAddActivity.this, "邮箱输入不能为空!", Toast.LENGTH_LONG).show();
						ET_email.setFocusable(true);
						ET_email.requestFocus();
						return;	
					}
					userInfo.setEmail(ET_email.getText().toString());
					/*验证获取家庭地址*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(UserInfoAddActivity.this, "家庭地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					userInfo.setAddress(ET_address.getText().toString());
					/*验证获取是否黑名单*/ 
					if(ET_blackFlag.getText().toString().equals("")) {
						Toast.makeText(UserInfoAddActivity.this, "是否黑名单输入不能为空!", Toast.LENGTH_LONG).show();
						ET_blackFlag.setFocusable(true);
						ET_blackFlag.requestFocus();
						return;	
					}
					userInfo.setBlackFlag(ET_blackFlag.getText().toString());
					/*验证获取信用分*/ 
					if(ET_creditScore.getText().toString().equals("")) {
						Toast.makeText(UserInfoAddActivity.this, "信用分输入不能为空!", Toast.LENGTH_LONG).show();
						ET_creditScore.setFocusable(true);
						ET_creditScore.requestFocus();
						return;	
					}
					userInfo.setCreditScore(Float.parseFloat(ET_creditScore.getText().toString()));
					/*验证获取注册时间*/ 
					if(ET_regTime.getText().toString().equals("")) {
						Toast.makeText(UserInfoAddActivity.this, "注册时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_regTime.setFocusable(true);
						ET_regTime.requestFocus();
						return;	
					}
					userInfo.setRegTime(ET_regTime.getText().toString());
					/*调用业务逻辑层上传用户信息*/
					UserInfoAddActivity.this.setTitle("正在上传用户信息，稍等...");
					String result = userInfoService.AddUserInfo(userInfo);
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
		if (requestCode == REQ_CODE_CAMERA_userPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_userPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_userPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_userPhoto.setImageBitmap(booImageBm);
				this.iv_userPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.userInfo.setUserPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_userPhoto && resultCode == Activity.RESULT_OK) {
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
				this.iv_userPhoto.setImageBitmap(bm); 
				this.iv_userPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			userInfo.setUserPhoto(filename); 
		}
	}
}
