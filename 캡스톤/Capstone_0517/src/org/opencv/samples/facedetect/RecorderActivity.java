package org.opencv.samples.facedetect;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class RecorderActivity extends Activity implements MediaRecorder.OnInfoListener{
	MediaPlayer mplayer = null;
	MediaRecorder mRecorder = null;
	String mFilePath;
	Button back, btnr, btns, btnp, btnps, saveBtn;
	LinearLayout linearLayout;
	Bitmap bm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recorder);
	
	
		btnr = (Button) findViewById(R.id.btnRecord);
		btns = (Button) findViewById(R.id.btnstop);
		btnp = (Button) findViewById(R.id.btnPlay);
		back = (Button) findViewById(R.id.backset);
		btnps =(Button) findViewById(R.id.btnplaystop);
		String sdRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		File cfile=new File(sdRootPath + "/facedetect");  
        cfile.mkdirs(); //폴더가 없을 경우 facedetect 폴더생성
        
		mFilePath = sdRootPath +"/facedetect/"+ "record.mp3";
		

		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	
	}
	
	public void onBtnRecord(){
		if(mRecorder != null){
			mRecorder.release();
			mRecorder = null;
		}
		
		mRecorder = new MediaRecorder();
		mRecorder.setOutputFile(mFilePath);
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		mRecorder.setMaxDuration(60*1000);
		mRecorder.setOnInfoListener(this);
		
		try{
			mRecorder.prepare();
		}catch(IOException e){
			Log.d("tag", "Record Prepare error");
		}
		
		mRecorder.start();
		
				Toast.makeText(RecorderActivity.this, "녹음이 시작되었습니다", Toast.LENGTH_SHORT).show();
		
		
	}

	 public void onBtnStop(){
		 mRecorder.stop();
		 mRecorder.release();
			Toast.makeText(RecorderActivity.this, "녹음이 중지되었습니다", Toast.LENGTH_SHORT).show();
			
	 }
	 
	 public void onBtnPlay(){
		 if(mplayer != null){
			 mplayer.stop();
			 mplayer.release();
			 mplayer = null;
		 }
		 mplayer = new MediaPlayer();
		 
		 try{
			 mplayer.setDataSource(mFilePath);
			 mplayer.prepare();
		 }catch(IOException e){
			 Log.d("tag", "Audio Play error");
			 return;
		 }
		 mplayer.start();
	 }
	 
	 
	 public void onClick(View v){
		 switch(v.getId()){
		 case R.id.btnRecord:
			 onBtnRecord();
			 break;
		 case R.id.btnstop:
			 onBtnStop();
			 break;
		 case R.id.btnPlay:
			 onBtnPlay();
			 break;
		 case R.id.btnplaystop:
			 mplayer.stop();
			 break;
		 }
	 }

	@Override
	public void onInfo(MediaRecorder mr, int what, int extra) {
		// TODO Auto-generated method stub
		switch(what){
		case MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED:
			onBtnStop();
			break;
		}
	}
	 
	
}
