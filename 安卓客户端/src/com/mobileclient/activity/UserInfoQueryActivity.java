package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.domain.UserType;
import com.mobileclient.service.UserTypeService;
import com.mobileclient.domain.ClassInfo;
import com.mobileclient.service.ClassInfoService;

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
public class UserInfoQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
	// �����û��������
	private EditText ET_user_name;
	// �����û�����������
	private Spinner spinner_userTypeObj;
	private ArrayAdapter<String> userTypeObj_adapter;
	private static  String[] userTypeObj_ShowText  = null;
	private List<UserType> userTypeList = null; 
	/*�û����͹���ҵ���߼���*/
	private UserTypeService userTypeService = new UserTypeService();
	// �������ڰ༶������
	private Spinner spinner_classObj;
	private ArrayAdapter<String> classObj_adapter;
	private static  String[] classObj_ShowText  = null;
	private List<ClassInfo> classInfoList = null; 
	/*�༶����ҵ���߼���*/
	private ClassInfoService classInfoService = new ClassInfoService();
	// �������������
	private EditText ET_name;
	// �������ڿؼ�
	private DatePicker dp_birthDate;
	private CheckBox cb_birthDate;
	// ������ϵ�绰�����
	private EditText ET_telephone;
	// �����Ƿ�����������
	private EditText ET_blackFlag;
	/*��ѯ�����������浽���������*/
	private UserInfo queryConditionUserInfo = new UserInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.userinfo_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�����û���ѯ����");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_user_name = (EditText) findViewById(R.id.ET_user_name);
		spinner_userTypeObj = (Spinner) findViewById(R.id.Spinner_userTypeObj);
		// ��ȡ���е��û�����
		try {
			userTypeList = userTypeService.QueryUserType(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int userTypeCount = userTypeList.size();
		userTypeObj_ShowText = new String[userTypeCount+1];
		userTypeObj_ShowText[0] = "������";
		for(int i=1;i<=userTypeCount;i++) { 
			userTypeObj_ShowText[i] = userTypeList.get(i-1).getUserTypeName();
		} 
		// ����ѡ������ArrayAdapter��������
		userTypeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userTypeObj_ShowText);
		// �����û����������б�ķ��
		userTypeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_userTypeObj.setAdapter(userTypeObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_userTypeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionUserInfo.setUserTypeObj(userTypeList.get(arg2-1).getUserTypeId()); 
				else
					queryConditionUserInfo.setUserTypeObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_userTypeObj.setVisibility(View.VISIBLE);
		spinner_classObj = (Spinner) findViewById(R.id.Spinner_classObj);
		// ��ȡ���еİ༶
		try {
			classInfoList = classInfoService.QueryClassInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int classInfoCount = classInfoList.size();
		classObj_ShowText = new String[classInfoCount+1];
		classObj_ShowText[0] = "������";
		for(int i=1;i<=classInfoCount;i++) { 
			classObj_ShowText[i] = classInfoList.get(i-1).getClassName();
		} 
		// ����ѡ������ArrayAdapter��������
		classObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, classObj_ShowText);
		// �������ڰ༶�����б�ķ��
		classObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_classObj.setAdapter(classObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_classObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionUserInfo.setClassObj(classInfoList.get(arg2-1).getClassNo()); 
				else
					queryConditionUserInfo.setClassObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_classObj.setVisibility(View.VISIBLE);
		ET_name = (EditText) findViewById(R.id.ET_name);
		dp_birthDate = (DatePicker) findViewById(R.id.dp_birthDate);
		cb_birthDate = (CheckBox) findViewById(R.id.cb_birthDate);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_blackFlag = (EditText) findViewById(R.id.ET_blackFlag);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					queryConditionUserInfo.setUser_name(ET_user_name.getText().toString());
					queryConditionUserInfo.setName(ET_name.getText().toString());
					if(cb_birthDate.isChecked()) {
						/*��ȡ��������*/
						Date birthDate = new Date(dp_birthDate.getYear()-1900,dp_birthDate.getMonth(),dp_birthDate.getDayOfMonth());
						queryConditionUserInfo.setBirthDate(new Timestamp(birthDate.getTime()));
					} else {
						queryConditionUserInfo.setBirthDate(null);
					} 
					queryConditionUserInfo.setTelephone(ET_telephone.getText().toString());
					queryConditionUserInfo.setBlackFlag(ET_blackFlag.getText().toString());
					Intent intent = getIntent();
					//����ʹ��bundle��������������
					Bundle bundle =new Bundle();
					//�����������Ȼ�Ǽ�ֵ�Ե���ʽ
					bundle.putSerializable("queryConditionUserInfo", queryConditionUserInfo);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
