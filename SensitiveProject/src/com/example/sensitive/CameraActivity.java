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

	// 분할 시간 변수 (ms단위)
	private static long RECORDE_TIME = 10000;

	// 해상도 설정 변수
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
		// 상단 타이틀바 없애기
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_camera);
		// 가로화면 고정
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
				Toast.makeText(CameraActivity.this, "촬영을 시작합니다.", 1).show();
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
				Toast.makeText(CameraActivity.this, "저장되었습니다.", 1).show();
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
		mTimer = new CountDownTimer(RECORDE_TIME, 1000) { // 10초동안 1초간격으로 줄어든다.
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

		// 오디오와 영상 입력 형식 설정
		mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
		mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

		// 저장될 파일 지정
		Path = getFilePath();
		mMediaRecorder.setOutputFile(Path);

		// 오디오와 영상 인코더 설정
		mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
		mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

		// 영상 옵션 설정 (화질 관련)
		mMediaRecorder.setVideoFrameRate(30);
		mMediaRecorder.setVideoSize(640, 480);


		//녹화도중에 녹화화면을 뷰에다가 출력하게 해주는 설정
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
			// 프리뷰 사이즈 값 재조정
			parameters.setPreviewSize(width, height);
			camera.setParameters(parameters);
			// 프리뷰 다시 시작
			camera.startPreview();
		}
	}

	public void surfaceCreated(SurfaceHolder holder) {
		try {
			// 카메라 객체를 만든다
			camera = Camera.open();

			Camera.Parameters parameters = camera.getParameters();
			parameters.setRotation(90);
			camera.setParameters(parameters);
			// 프리뷰 디스플레이를 담당한 서피스 홀더를 설정한다
			camera.setPreviewDisplay(holder);
			// 프리뷰 콜백을 설정한다 - 프레임 설정이 가능하다.
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