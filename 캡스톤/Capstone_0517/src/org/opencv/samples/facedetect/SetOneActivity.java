package org.opencv.samples.facedetect;

import java.util.ArrayList;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class SetOneActivity extends Activity implements OnItemClickListener{

	  // Intent request code
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    
private static final String TAG = null;
ListView mList;
ArrayList<String> mArGeneral;
	
Button backbtn;


	public void initListView(){
		String[] setList = {"번호저장", "블루투스 연결", "녹음기"};
		mArGeneral = new ArrayList<String>();
		for(int i=0; i<3; i++){
			mArGeneral.add(setList[i]);
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mArGeneral);
		
		mList = (ListView)findViewById(R.id.listMember);
		mList.setAdapter(adapter);
		mList.setOnItemClickListener(this);
	}
	private BluetoothService btService = null;
    
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_one);
		
		Handler mHandler = new  Handler();
		 if(btService == null) {
	            btService = new BluetoothService(this, mHandler);
	        }
	       
		backbtn = (Button)findViewById(R.id.backbtn);
		
		backbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		initListView();
	}


	   public void onActivityResult(int requestCode, int resultCode, Intent data) {
    Log.d(TAG, "onActivityResult " + resultCode);
     
    switch (requestCode) {
    
    case REQUEST_CONNECT_DEVICE:
        // When DeviceListActivity returns with a device to connect
        if (resultCode == Activity.RESULT_OK) {
            btService.getDeviceInfo(data);
        }
        break;	
        
    case REQUEST_ENABLE_BT:	
        // When the request to enable Bluetooth returns
        if (resultCode == Activity.RESULT_OK) {
        	btService.scanDevice();
        } else {

            Log.d(TAG, "Bluetooth is not enabled");
        }
        break;
    }
} 


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if(position == 0){
			Intent intent = new Intent(this, PhonenumberActivity.class);
			startActivity(intent);
		
	}
		
		if(position == 1){
			if(btService.getDeviceState()) {
	            // 블루투스가 지원 가능한 기기일 때
	            btService.enableBluetooth();
	        } else {
	            finish();
	        }

		}
		if(position == 2){
			Intent intent = new Intent (this, RecorderActivity.class);
			startActivity(intent);
		}
		
		
	
	}
}