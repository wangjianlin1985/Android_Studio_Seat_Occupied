package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Jubao;
import com.mobileclient.service.JubaoService;
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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class JubaoUserAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明举报标题输入框
	private EditText ET_title;
	// 声明举报内容输入框
	private EditText ET_content;
	 
	 
	protected String carmera_path;
	/*要保存的举报信息*/
	Jubao jubao = new Jubao();
	/*举报管理业务逻辑层*/
	private JubaoService jubaoService = new JubaoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.jubao_user_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加举报");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_title = (EditText) findViewById(R.id.ET_title);
		ET_content = (EditText) findViewById(R.id.ET_content);
		
		Declare declare = (Declare) this.getApplication();
		jubao.setUserObj(declare.getUserName());
		
		 
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加举报按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取举报标题*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(JubaoUserAddActivity.this, "举报标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					jubao.setTitle(ET_title.getText().toString());
					/*验证获取举报内容*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(JubaoUserAddActivity.this, "举报内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					jubao.setContent(ET_content.getText().toString());
					/*举报时间*/
					jubao.setJubaoTime("");
					/*管理回复*/
					jubao.setReplyContent("--");
					/*调用业务逻辑层上传举报信息*/
					JubaoUserAddActivity.this.setTitle("正在上传举报信息，稍等...");
					String result = jubaoService.AddJubao(jubao);
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
