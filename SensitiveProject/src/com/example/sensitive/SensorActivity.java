package com.example.sensitive;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
//센서 부분작업중
public class SensorActivity extends Activity {
	
	SensorManager sensorManager;
	Sensor sensorAccelerometer;
	Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
	SensorEventListener sensorAcceleromeerListener = new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			vibrator.vibrate(1000);
			sensorManager.unregisterListener(sensorAcceleromeerListener);
			
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_sensor);
	

	}

}
