package com.example.sensitive;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class ListActivity extends Activity {

	ListView listview; //����Ʈ�� ����
	ListAdapter adapter; //������ ����
	ArrayList<LVItem> alist; //������ ���� �ڷᱸ��

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_list);
	 
		Button bnt1 = (Button)findViewById(R.id.delete); //���� ��ư;
		Button bnt2 = (Button)findViewById(R.id.deleteAll); //��ü ���� ��ư

		// For Custom ListView
		//����Ʈ �信 ����� ����Ʈ�� ����
		this.listview = (ListView) findViewById(R.id.listview);
		
		alist = new ArrayList<LVItem>();
		
		alist.add(new LVItem(getApplicationContext(), R.drawable.picture, "2014-08-11-������", false));
		alist.add(new LVItem(getApplicationContext(), R.drawable.picture, "2014-08-11-������", false));
		alist.add(new LVItem(getApplicationContext(), R.drawable.picture, "2014-08-21-�����", false));
		alist.add(new LVItem(getApplicationContext(), R.drawable.picture, "2014-08-21-�����", false));
		//�����͸� �ޱ� ���� ������ ��� ��ü ����
		adapter = new ListAdapter(this, alist);
		//����Ʈ�ο� ����� ����
		
		//list ��ü ����
		this.listview.setAdapter(adapter);
		listview.setItemsCanFocus(false);
		listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);	

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
//				SparseBooleanArray checkArr = listview.getCheckedItemPositions();
//				if(checkArr.size() != 0){
//					for(int i = listview.getCount() -1; i > -1; i--){
//						if(checkArr.get(i)){
//							alist.remove(i);
//						}
//					}
//				}
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
		private ArrayList<Integer> listItems; //üũ�� �������� ������ list
		private ArrayList<LVItem> listItem;
		
		
		public ListAdapter(Context context,	ArrayList<LVItem> objects) {
			super(context, 0, objects);
			// TODO Auto-generated constructor stub
			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//listItems = new ArrayList<Integer>();
			listItem = objects;
		}

		public class ViewHolder{
			TextView date;
//			VideoView mVideoView;
			CheckBox check;
			ImageView image;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listItem.size();
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
//			final int checkBoxPosition = position;
			ViewHolder holder = null;
			
			if(convertView == null){
				convertView = mInflater.inflate(R.layout.activity_list_view, null);
				holder = new ViewHolder();
				holder.check = (CheckBox)convertView.findViewById(R.id.check);
//				holder.mVideoView = (VideoView)convertView.findViewById(R.id.videoView);
				holder.image = (ImageView)convertView.findViewById(R.id.imageBnt);
				holder.date = (TextView)convertView.findViewById(R.id.data);

				convertView.setTag(holder);

			} else {
				holder = (ViewHolder)convertView.getTag();
			}
			item = this.getItem(position);
			
			if(item != null){
//				//üũ�ڽ��� null�� �ƴ϶�� 
//				if(holder.check != null){
//					holder.check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//						
//						@Override
//						public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//							// TODO Auto-generated method stub
//							if(isChecked){//üũ�ڽ��� üũ�� �� 
//								for(int i = 0; i < listItems.size(); i++){
//									if(listItems.get(i) == checkBoxPosition){
//										return;
//									}
//								}
//								listItems.add(checkBoxPosition);
//							} else{ //üũ�ڽ� ������ ��
//								for(int i = 0; i < listItems.size(); i++){
//									if(listItems.get(i) == checkBoxPosition){
//										listItems.remove(i);
//										break;
//									}
//								}
//							}
//						}
//					});	
//					//üũ�� ���������� �Ǵ� �� boolean ����
//					boolean isChecked = false;
//					//���� üũ�� ������ �̶��
//					for(int i = 0; i < listItems.size(); i++){
//						if(listItems.get(i) == checkBoxPosition){
//							holder.check.setChecked(true);
//							isChecked = false;
//							break;
//						}
//					}
//					//�ƴ϶�� üũ ����
//					if(!isChecked){
//						holder.check.setChecked(false);
//					}
//				}
				holder.check.setChecked(false);
				holder.check.setTag(item.isSelected());
				holder.image.setImageResource(item.getImage());
				holder.image.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent it = new Intent(ListActivity.this, PlayerListActivity.class);
						startActivity(it);
					}
				});
				holder.date.setText(item.getData());
			}
			
			return convertView;
		}		
	}
	
	class LVItem{
		private int image;
		private String data;
		boolean selected;
		
		public LVItem(Context context, int _image, String _data, boolean selected) {
			super();
			this.image = _image;
			this.data = _data;
			this.selected = selected;
		}

		public int getImage() {
			return image;
		}

		public String getData() {
			return data;
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}		
	}
}
