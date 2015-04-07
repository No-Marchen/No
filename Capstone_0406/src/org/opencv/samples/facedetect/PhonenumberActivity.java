package org.opencv.samples.facedetect;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PhonenumberActivity extends Activity implements OnClickListener {

	EditText edit1, edit2;
	Button back, del, del1, check;
	TextView editr1, editr2, check1, check2;
	String mFilePath, mFilePath2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phonenumber);

		Button btn1 = (Button) findViewById(R.id.numberenroll1);
		Button btn2 = (Button) findViewById(R.id.numberenroll2);
		edit1 = (EditText) findViewById(R.id.number1);
		edit2 = (EditText) findViewById(R.id.number2);
		back = (Button) findViewById(R.id.backset);
		editr1 = (TextView) findViewById(R.id.nresult1);
		editr2 = (TextView) findViewById(R.id.nresult2);
		check = (Button) findViewById(R.id.numconf);
		check1 = (TextView) findViewById(R.id.numconf1);
		check2 = (TextView) findViewById(R.id.numconf2);

		String sdRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		mFilePath = sdRootPath +"/facedetect/number2.txt";
		//mFilePath = Environment.getExternalStorageDirectory()+"/number2.txt";
		mFilePath2 = sdRootPath +"/facedetect/number3.txt";
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		btn1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try{	
					FileOutputStream fos = new FileOutputStream(mFilePath);
					//FileOutputStream fos = openFileOutput(mFilePath, Context.MODE_PRIVATE);
					String str = edit1.getText().toString();
					String str1 = edit1.getText().toString();
					
					editr1.setText(str1);
					fos.write(str.getBytes());
					fos.close();
				}catch (Exception e){
					Log.e("File", "에러 = 여기" +e);
				}
			}
		});
		
		btn2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try{
					FileOutputStream fos = new FileOutputStream(mFilePath2);
					//FileOutputStream fos = openFileOutput(mFilePath2, Context.MODE_PRIVATE);
					String str2 = edit2.getText().toString();	
					String str3 = edit2.getText().toString();
					editr2.setText(str3);
					fos.write(str2.getBytes());
					fos.close();
				}catch (Exception e){
					Log.e("File", "에러 =" +e);
				}
			}
		});
		
		
		check.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
                    FileInputStream fis = new FileInputStream(mFilePath);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    String str = new String(buffer);
                    check1.setText(str);
                    fis.close();                   
                } 
				catch (Exception e) {
                    Log.e("File", "에러=" + e);
                }
				try {					 
                    FileInputStream fis1 = new FileInputStream(mFilePath2);
                    byte[] buffer1 = new byte[fis1.available()];
                    fis1.read(buffer1);
                    String str1 = new String(buffer1);
                    check2.setText(str1);
                    fis1.close();                   
                }
				catch (Exception e) {
                    Log.e("File", "에러=" + e);
                } 
			}			
		});	
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){

		}
	}

	}
