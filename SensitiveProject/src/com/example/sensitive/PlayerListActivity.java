package com.example.sensitive;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class PlayerListActivity extends Activity {
	private VideoView videoview;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_list_player);
	    videoview = (VideoView)findViewById(R.id.videoView);
	    
	    MediaController mc = new MediaController(this); //�̵�� ��Ʈ�ѷ� ���� �� videoview ��ü�� ����
	    videoview.setMediaController(mc);
	    //uri ����
		Uri uri = Uri.parse("android.resource://"+getPackageName()+"/" + R.raw.video20140918_181502);
		videoview.setVideoURI(uri);
		videoview.requestFocus(); //videoview�� ��Ŀ�� �ϵ��� ����
		videoview.start();
	}

}
