package com.example.sensitive;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

public class ListActivity extends Activity {

	//Activity act = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_list);
	    
		Button bnt1; //���� ��ư
		Button bnt2; //��ü ���� ��ư
		
		CheckBox check1 = (CheckBox) findViewById(R.id.check1); 
		CheckBox check2 = (CheckBox) findViewById(R.id.check2); 

		check1.setOnCheckedChangeListener(checkChangeListener);
		check2.setOnCheckedChangeListener(checkChangeListener);	
		
		bnt1 = (Button)findViewById(R.id.delete);
		bnt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Builder dlg= new AlertDialog.Builder(ListActivity.this);
                dlg.setTitle("����")
                .setMessage("üũ�� ������ ����")
                .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ListActivity.this,
                        "������ ��ҵǾ����ϴ�.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ListActivity.this,
                        "���� �Ǿ����ϴ�.", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
				
			}
		});
		
		bnt2 = (Button)findViewById(R.id.deleteAll);
		bnt2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Builder dlg= new AlertDialog.Builder(ListActivity.this);
                dlg.setTitle("��ü ����")
                .setMessage("������ ��ü �����մϴ�")
                .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ListActivity.this,
                        "������ ��ҵǾ����ϴ�.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ListActivity.this,
                        "��ü ���� �Ǿ����ϴ�.", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
				
			}
		});
		
	}
	private CompoundButton.OnCheckedChangeListener checkChangeListener = new CompoundButton.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// TODO Auto-generated method stub
			switch (buttonView.getId()){
			case R.id.check1:
				if(isChecked){
					Toast.makeText(ListActivity.this, "���� �Ǿ���", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(ListActivity.this, "�����Ǿ���", Toast.LENGTH_SHORT).show();	
				}
				break;
			case R.id.check2:
				if(isChecked){
					Toast.makeText(ListActivity.this, "���� �Ǿ���", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(ListActivity.this, "�����Ǿ���", Toast.LENGTH_SHORT).show();	
				}
				break;

			}
		}
	};
}
