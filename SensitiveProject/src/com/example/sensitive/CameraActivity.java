package com.example.sensitive;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class CameraActivity extends Activity implements SurfaceHolder.Callback {

	private MediaRecorder mRecorder = null;
	private Button mPlayBtn;
	private TextView userId;
	private boolean mIsStart = false;
	private String Path = "";
	private SurfaceView mPreview;
	private SurfaceHolder mHolder;
	VideoView mVideo;
	private static int fileIndex = 0;
	private Camera mCamera;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 상단 타이틀바 없애기
		setContentView(R.layout.activity_camera);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 

		mPreview = (SurfaceView) findViewById(R.id.surface);
		mHolder = mPreview.getHolder();
		mHolder.addCallback(this);
		mVideo = (VideoView) findViewById(R.id.videoview);
		mPlayBtn = (Button) findViewById(R.id.playBtn);
		userId = (TextView) findViewById(R.id.userId);

		Intent intent = getIntent();
		String test = intent.getStringExtra("userId");
		if (test != null) {
			userId.setText(test);
		}

		mPreview.setVisibility(View.VISIBLE);
		mVideo.setVisibility(View.INVISIBLE);

		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		mPlayBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (mIsStart == false) {
					String sd = Environment.getExternalStorageDirectory()
							.getAbsolutePath();
					fileIndex++;
					Path = sd + "/video" + fileIndex + ".mp4";
					if (mCamera != null) {
						mCamera.stopPreview();
						mCamera.release();
						mCamera = null;
						Log.e("CAM TEST", "#3 Release Camera  _---> OK!!!");
					}
					if (mRecorder == null) {
						mRecorder = new MediaRecorder();
					} else {
						mRecorder.reset();
					}
					mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
					mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					mRecorder
							.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
					mRecorder
							.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
					mRecorder
							.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					mRecorder.setOutputFile(Path);
					mRecorder.setPreviewDisplay(mHolder.getSurface());

					try {
						mRecorder.prepare();
						mRecorder.start();
					} catch (IllegalStateException e) {
						Toast.makeText(CameraActivity.this, e.toString(), 1)
								.show();
						return;
					} catch (IOException e) {
						Toast.makeText(CameraActivity.this, e.toString(), 1)
								.show();
						return;
					} catch (Exception e) {
						Toast.makeText(CameraActivity.this, e.toString(), 1)
								.show();
						return;
					}
					mIsStart = true;
					Toast.makeText(CameraActivity.this, "촬영을 시작합니다.", 1).show();
					mPlayBtn.setText("Stop");
				} else {
					mRecorder.stop();
					mRecorder.release();
					mRecorder = null;
					mIsStart = false;
					Toast.makeText(CameraActivity.this, "저장되었습니다.", 1).show();
					mPlayBtn.setText("Start");

				}
			}

		});
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			// 카메라 객체를 만든다
			mCamera = Camera.open();

			Camera.Parameters parameters = mCamera.getParameters();
			parameters.setRotation(90);
			mCamera.setParameters(parameters);
			// 프리뷰 디스플레이를 담당한 서피스 홀더를 설정한다
			mCamera.setPreviewDisplay(holder);
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

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		// 서피스 변경되었을 때의 대응 루틴
		if (mCamera != null) {
			Camera.Parameters parameters = mCamera.getParameters();
			// 프리뷰 사이즈 값 재조정
			parameters.setPreviewSize(width, height);
			mCamera.setParameters(parameters);
			// 프리뷰 다시 시작
			mCamera.startPreview();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}

	public void onDestroy() {
		super.onDestroy();

		if (mCamera != null) {
			mCamera.stopPreview();
			// 카메라 객체 초기화
			mCamera = null;
		}
		if (mRecorder != null) {
			mRecorder.release();
			mRecorder = null;
		}
	}
}
