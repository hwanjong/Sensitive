package com.example.sensitive;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.media.*;
import android.view.*;
import android.widget.Button;
import android.widget.Toast;
import android.util.*;
import android.os.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends Activity implements SurfaceHolder.Callback, Handler.Callback {
	final private String TAG = "CamTest";
	private static String Path = "";

	// ���� �ð� ���� (ms����)
	private static long RECORDE_TIME = 10000;

	// �ػ� ���� ����
	private static int WIDTH;
	private static int HEIGHT;

	// handler command
	final private int START_RECORDING = 1;
	final private int STOP_RECORDING = 2;
	final private int INIT_RECORDER = 3;
	final private int RELEASE_RECORDER = 4;
	final private int START_INTERVAL_RECORD = 5;

	private SurfaceHolder mSurfaceHolder = null;
	private MediaRecorder mMediaRecorder = null;

	private Handler mHandler;
	private CountDownTimer mTimer = null;

	private Camera camera;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ��� Ÿ��Ʋ�� ���ֱ�
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_camera);
		// ����ȭ�� ����
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 

		// preview surface
		SurfaceView surView = (SurfaceView) findViewById(R.id.surface);

		Button startBtn = (Button) findViewById(R.id.startBtn);
		Button stopBtn = (Button) findViewById(R.id.stopBtn);

		SurfaceHolder holder = surView.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		// handler
		mHandler = new Handler(this);

		startBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				startIntervalRecording();
				Toast.makeText(CameraActivity.this, "�Կ��� �����մϴ�.", 1).show();
			}
		});

		stopBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (mTimer != null) {
					mTimer.cancel();
					mTimer = null;
				}
				stopMediaRecorder();
				releaseMediaRecorder();
				Toast.makeText(CameraActivity.this, "����Ǿ����ϴ�.", 1).show();
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onStop() {
		super.onStop();
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
		stopMediaRecorder();
		releaseMediaRecorder();
	}

	protected void startIntervalRecording() {
		mTimer = new CountDownTimer(RECORDE_TIME, 1000) { // 10�ʵ��� 1�ʰ������� �پ���.
			boolean recordStart = false;

			public void onTick(long millisUntilFinished) {
				if (!recordStart) {
					recordStart = true;
					mHandler.sendEmptyMessage(INIT_RECORDER);
					mHandler.sendEmptyMessage(START_RECORDING);
				}
			}

			public void onFinish() {
				mHandler.sendEmptyMessage(STOP_RECORDING);
				mHandler.sendEmptyMessage(RELEASE_RECORDER);
				mHandler.sendEmptyMessage(START_INTERVAL_RECORD);
			}
		};

		mTimer.start();
	}

	protected void initMediaRecorder() {

		if (mSurfaceHolder == null) {
			Log.e(TAG, "No Surface Holder");
			return;
		}
		if (camera != null) {
			camera.stopPreview();
			camera.release();
			camera = null;
		}
		if (mMediaRecorder == null) {
			mMediaRecorder = new MediaRecorder();
		}

		// ������� ���� �Է� ���� ����
		mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
		mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

		// ����� ���� ����
		Path = getFilePath();
		mMediaRecorder.setOutputFile(Path);

		// ������� ���� ���ڴ� ����
		mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
		mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

		// ���� �ɼ� ���� (ȭ�� ����)
		mMediaRecorder.setVideoFrameRate(30);
		mMediaRecorder.setVideoSize(640, 480);


		//��ȭ���߿� ��ȭȭ���� �信�ٰ� ����ϰ� ���ִ� ����
		mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

		try {
			mMediaRecorder.prepare();
		} catch (Exception ex) {
			ex.printStackTrace();
			mMediaRecorder.release();
			mMediaRecorder = null;
		}
	}

	protected void releaseMediaRecorder() {
		if (mMediaRecorder == null)
			return;
		mMediaRecorder.reset();
		mMediaRecorder.release();
		mMediaRecorder = null;
	}

	protected void startMediaRecorder() {
		try {
			if (mMediaRecorder != null) {
				mMediaRecorder.start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			mMediaRecorder.release();
			mMediaRecorder = null;
		}
	}

	protected void stopMediaRecorder() {
		if (mMediaRecorder != null) {
			mMediaRecorder.stop();
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// do something
		if (camera != null) {
			Camera.Parameters parameters = camera.getParameters();
			// ������ ������ �� ������
			parameters.setPreviewSize(width, height);
			camera.setParameters(parameters);
			// ������ �ٽ� ����
			camera.startPreview();
		}
	}

	public void surfaceCreated(SurfaceHolder holder) {
		try {
			// ī�޶� ��ü�� �����
			camera = Camera.open();

			Camera.Parameters parameters = camera.getParameters();
			parameters.setRotation(90);
			camera.setParameters(parameters);
			// ������ ���÷��̸� ����� ���ǽ� Ȧ���� �����Ѵ�
			camera.setPreviewDisplay(holder);
			// ������ �ݹ��� �����Ѵ� - ������ ������ �����ϴ�.
			/*
			 * mCamera.setPreviewCallback(new PreviewCallback() {
			 * 
			 * @Override public void onPreviewFrame(byte[] data, Camera camera)
			 * { // TODO Auto-generated method stub } });
			 */
		} catch (Exception e) {
			// TODO: handle exception
		}

		mSurfaceHolder = holder;
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		mSurfaceHolder = null;
	}

	public String getFilePath() {

		String sd = Environment.getExternalStorageDirectory().getAbsolutePath();

		sd += "/sensitiveRecorder";

		File file = new File(sd);
		if (!file.exists()) {
			file.mkdirs();
		}

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

		String path = sd + "/video" + timeStamp + ".mp4";

		return path;
	}

	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case START_RECORDING:
			startMediaRecorder();
			return true;
		case STOP_RECORDING:
			stopMediaRecorder();
			return true;
		case INIT_RECORDER:
			initMediaRecorder();
			return true;
		case RELEASE_RECORDER:
			releaseMediaRecorder();
			return true;
		case START_INTERVAL_RECORD:
			startIntervalRecording();
			return true;
		}
		return false;
	}
}