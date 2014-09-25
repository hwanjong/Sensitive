package com.example.sensitive;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

public class WaitActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_wating);
	    
	    final ProgressDialog progressD;
	    progressD = new ProgressDialog(WaitActivity.this);
	    progressD.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	    progressD.setMessage("충격 감지로 녹화중");
	    progressD.setCancelable(false);
	    progressD.setMax(90);
	    progressD.show();
	    
	    Thread t = new Thread(){
	    	public void run() {
				int tick = 0;
				while(tick < 90){
					try {
						Thread.sleep(90);
					} catch(InterruptedException e){
					}
					tick++;
					progressD.incrementProgressBy(1);
				}
			}
	    };
	    t.start();
	}
}
