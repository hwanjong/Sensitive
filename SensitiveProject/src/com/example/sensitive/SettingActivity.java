package com.example.sensitive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SettingActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		makeSpinner();
		
		Button btn1 = (Button)findViewById(R.id.selectButton);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SettingActivity.this, SelectSetActivity.class);
				startActivity(intent);
				
			}
		});
		


	}
	
	void makeSpinner(){
		Spinner spinner1,spinner2;
		ArrayAdapter adapter1,adapter2;
		
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		adapter1 = ArrayAdapter.createFromResource(this, R.array.selected, android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter1);
		
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		adapter2 = ArrayAdapter.createFromResource(this, R.array.save_quality, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(adapter2);

		
	}

}
