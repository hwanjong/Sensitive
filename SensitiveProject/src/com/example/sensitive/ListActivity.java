package com.example.sensitive;

import java.util.ArrayList;
import com.example.sensitive.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListActivity extends Activity {

	ListView listview; //����Ʈ�� ����
	ListAdapter adapter; //������ ����
	ArrayList<LVItem> list; //������ ���� �ڷᱸ��
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_list);
	 
		Button bnt1 = (Button)findViewById(R.id.delete); //���� ��ư;
		Button bnt2 = (Button)findViewById(R.id.deleteAll); //��ü ���� ��ư

		// For Custom ListView
		//����Ʈ �信 ����� ����Ʈ�� ����
		this.listview = (ListView) findViewById(R.id.listview);
		
		list = new ArrayList<LVItem>();
		
		list.add(new LVItem(getApplicationContext(), R.drawable.picture, "2014-08-11-������"));
		list.add(new LVItem(getApplicationContext(), R.drawable.picture, "2014-08-11-������"));
		list.add(new LVItem(getApplicationContext(), R.drawable.picture, "2014-08-21-�����"));
		list.add(new LVItem(getApplicationContext(), R.drawable.picture, "2014-08-21-�����"));
		//�����͸� �ޱ� ���� ������ ��� ��ü ����
		adapter = new ListAdapter(this, list);
		//����Ʈ�ο� ����� ����
		
		//list ��ü ����
		this.listview.setAdapter(adapter);

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
		
		bnt2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Builder dlg= new AlertDialog.Builder(ListActivity.this);
	            
				dlg.setTitle("��ü����")
	            .setMessage("üũ�� ������ ��ü����")
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
		
	}
	
	private class ListAdapter extends ArrayAdapter<LVItem>{

		private LayoutInflater mInflater;
		private LVItem item;
		
		public ListAdapter(Context context,	ArrayList<LVItem> objects) {
			super(context, 0, objects);
			// TODO Auto-generated constructor stub
			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView == null){
				convertView = mInflater.inflate(R.layout.activity_list_view, null);
			} 
			item = this.getItem(position);
			
			if(item != null){
				ImageView imv = (ImageView)convertView.findViewById(R.id.image);
				TextView texv = (TextView)convertView.findViewById(R.id.data);
				
				imv.setImageResource(item.getImage());
				texv.setText(item.getData());
			}
			
			return convertView;
		}		
	}
	
	class LVItem{
		private int image;
		private String data;
		
		public LVItem(Context context, int _image, String _data) {
			super();
			this.image = _image;
			this.data = _data;
		}

		public int getImage() {
			return image;
		}

		public String getData() {
			return data;
		}
	}
//	private CompoundButton.OnCheckedChangeListener checkChangeListener = new CompoundButton.OnCheckedChangeListener() {
//		
//		@Override
//		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//			// TODO Auto-generated method stub
//			switch (buttonView.getId()){
//			case R.id.check:
//				if(isChecked){
//					Toast.makeText(ListActivity.this, "���� �Ǿ���", Toast.LENGTH_SHORT).show();
//					
//				} else {
//					Toast.makeText(ListActivity.this, "�����Ǿ���", Toast.LENGTH_SHORT).show();	
//				}
//				break;
//			case R.id.check2:
//				if(isChecked){
//					Toast.makeText(ListActivity.this, "���� �Ǿ���", Toast.LENGTH_SHORT).show();
//				} else {
//					Toast.makeText(ListActivity.this, "�����Ǿ���", Toast.LENGTH_SHORT).show();	
//				}
//				break;
//
//				}
//			}
//		};

}
