package com.example.sensitive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class LoginActivity extends Activity {
	
	private EditText userId;
	private EditText userPwd;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 상단 타이틀바 없애기
		setContentView(R.layout.activity_login);
		
		userId = (EditText)findViewById(R.id.edit_id);
		userPwd = (EditText)findViewById(R.id.edit_pwd);
	
	}
	
	public void onClick(View v) {
		if ("test".equals(userId.getText().toString()) && "1234".equals(userPwd.getText().toString())) {
			Intent intent = new Intent(this, MainActivity.class);
			intent.putExtra("userId", userId.getText().toString());
			startActivity(intent);
		}
	}

}
