package com.example.sensitive;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
	    
	    MediaController mc = new MediaController(this); //미디어 컨트롤러 생성 후 videoview 객체에 설정
	    videoview.setMediaController(mc);
	
	    String sd = Environment.getExternalStorageDirectory().getAbsolutePath() + "/sensitiveRecorder/video20140918_181502.mp4";
	    
//	    String[] proj = {MediaStore.Video.Media._ID, MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.DATA};
//	    Cursor cursor = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, proj, null, null, null);
//	    int indexData = cursor.getColumnIndex(MediaStore.Video.Media.DATA);
//	    cursor.moveToFirst();
//	    String path = sd + indexData + ".mp4";
	    videoview.setVideoPath(sd);
		videoview.requestFocus(); //videoview를 포커스 하도록 지정
		videoview.start();
	}

}
