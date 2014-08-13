package com.example.sensitive;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
		bnt1 = (Button)findViewById(R.id.delete);
		bnt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Builder dlg= new AlertDialog.Builder(ListActivity.this);
                dlg.setTitle("����")
                .setMessage("�����Ͻðڽ��ϱ�?")
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
                .setMessage("��ü �����Ͻðڽ��ϱ�?")
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

}
