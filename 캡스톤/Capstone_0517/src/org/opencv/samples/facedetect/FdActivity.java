package org.opencv.samples.facedetect;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.objdetect.CascadeClassifier;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.SnapshotReadyCallback;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class FdActivity extends FragmentActivity implements CvCameraViewListener2, OnClickListener {

    private static final String    TAG                 = "OCVSample::Activity";
    private static final Scalar    FACE_RECT_COLOR     = new Scalar(0, 255, 0, 255);
    public static final int        JAVA_DETECTOR       = 0;
    public static final int        NATIVE_DETECTOR     = 1;

    String RootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    String log_Path = RootPath+"/facedetect/datalog.txt";
   // File f1 = new File(log_Path) ;
    
    // Layout
    private Button btn_Connect;
    private TextView txt_Result;
    
    private MenuItem               mItemFace50;
    private MenuItem               mItemFace40;
    private MenuItem               mItemFace30;
    private MenuItem               mItemFace20;
    private MenuItem               mItemType;

    private Mat                    mRgba;
    private Mat                    mGray;
    private File                   mCascadeFile;
    private File                   mCascadeFile2;
    private CascadeClassifier      mJavaDetector;
    private CascadeClassifier      mJavaDetector2;
    private DetectionBasedTracker  mNativeDetector;
    
    public int 				   faceNum=0; //페이스 변수;
    public long curTime, stdTime=0,captureTime=0;
    public long check_Time, cur_Check_Time;
    public int state =0;
    //public int next_sleepLevel = 0;
    public int cur_sleepLevel = 1;

    private int                    mDetectorType       = JAVA_DETECTOR;
    private String[]               mDetectorName;

    private float                  mRelativeFaceSize1   = 0.15f;	//eye
    private float                  mRelativeFaceSize2   = 0.15f;	//face
    private int                    mAbsoluteFaceSize   = 0;

    private CameraBridgeViewBase   mOpenCvCameraView;

    TextView textView;
    Button btnTime , set1, te;
    Handler myHandler;	
    SimpleDateFormat DF = new SimpleDateFormat("yyyy년 MM월 dd일\n   HH시 mm분 ss초");
    SimpleDateFormat DF1 = new SimpleDateFormat("yyyy년 MM월 dd일   HH시 mm분 ss초");
    TextView edit, getnum, getnum1, tlog;
    
    MediaPlayer mPlayer = null;
    TextView battery;
    GoogleMap mGoogleMap, myMap;
    boolean mFirstLoc = true;
    LocationManager mLocMgr;
    Marker mMarkerStart, mMarkerMan;
    ImageView signpic;
    String fileName;
    String fileName1;
    String filePath;
 //   DbHelper mDbHelper;
    SQLiteDatabase mDb;
    Cursor mCursor;
    int mSelIndex = -1;
    ScrollView sv;
    
    Button btn_sms;
    TextView receiver1,receiver2;
    Location myLocation;
    double lati,longi,alti;
    String loca_t;
    
    private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");

                    // Load native library after(!) OpenCV initialization
                    System.loadLibrary("detection_based_tracker");

                    try {
                        // load cascade file from application resources
                        InputStream is = getResources().openRawResource(R.raw.haarcascade_frontalface_alt);
                        File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
                        mCascadeFile = new File(cascadeDir, "haarcascade_frontalface_alt.xml");
                        FileOutputStream os = new FileOutputStream(mCascadeFile);

                        InputStream is2 = getResources().openRawResource(R.raw.haarcascade_eye);
                        File cascadeDir2 = getDir("cascade", Context.MODE_PRIVATE);
                        mCascadeFile2 = new File(cascadeDir2, "haarcascade_eye.xml");
                        FileOutputStream os2 = new FileOutputStream(mCascadeFile2);

                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                        
                        byte[] buffer2 = new byte[4096];
                        int bytesRead2;
                        while ((bytesRead2 = is2.read(buffer2)) != -1) {
                            os2.write(buffer2, 0, bytesRead2);
                        }
                        
                        is.close();
                        os.close();
                        
                        is2.close();
                        os2.close();

                        mJavaDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
                        if (mJavaDetector.empty()) {
                            Log.e(TAG, "Failed to load cascade classifier");
                            mJavaDetector = null;
                        } else
                            Log.i(TAG, "Loaded cascade classifier from " + mCascadeFile.getAbsolutePath());
                        

                        mJavaDetector2 = new CascadeClassifier(mCascadeFile2.getAbsolutePath());
                        if (mJavaDetector2.empty()) {
                            Log.e(TAG, "Failed to load cascade classifier");
                            mJavaDetector2 = null;
                        } else
                            Log.i(TAG, "Loaded cascade classifier from " + mCascadeFile2.getAbsolutePath());

                        mNativeDetector = new DetectionBasedTracker(mCascadeFile.getAbsolutePath(), 0);

                        cascadeDir.delete();
                        cascadeDir2.delete();

                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, "Failed to load cascade. Exception thrown: " + e);
                    }

                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public FdActivity() {
        mDetectorName = new String[2];
        mDetectorName[JAVA_DETECTOR] = "Java";
        mDetectorName[NATIVE_DETECTOR] = "Native (tracking)";

        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    /* PART Bluetooth */
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.face_detect_surface_view);
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.fd_activity_surface_view);
        mOpenCvCameraView.setCvCameraViewListener(this);
        
        /** Main Layout **/

        textView = (TextView) findViewById(R.id.time); //텍스트뷰생성 
        getnum = (TextView) findViewById(R.id.getnumber1);
        getnum1 = (TextView) findViewById(R.id.getnumber2);
        mHandler.sendEmptyMessage(0);
	//	ihandler.sendEmptyMessage(0);
        sv = (ScrollView) findViewById(R.id.sc);
        myMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap(); 
		battery = (TextView) findViewById(R.id.battery);
				
		IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(mBatteryRecv, filter);
    	
		mLocMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);  
		initMap();
		
        tlog = (TextView) findViewById(R.id.tlog);
      //  mDbHelper = new DbHelper(this);
        String sdRootPath = Environment.getExternalStorageDirectory().getAbsolutePath(); //녹음파일 경로설정
        
        String init_path = sdRootPath +"/facedetect/";
        
        File file = new File(init_path);
        if(file.exists()){
        	Log.i(init_path, "Exists");
        }
        else
        	file.mkdir();
        
		filePath = Environment.getExternalStorageDirectory().getPath() +"/facedetect"+"/erorr.wav";
        fileName = sdRootPath + "/facedetect/"+ "number2.txt";
        fileName1 = sdRootPath + "/facedetect/"+ "number3.txt";
/*
        mDb = mDbHelper.getWritableDatabase();
				mDbHelper.onUpgrade(mDb	, 1, 2);
				mDb.close();
			 
        mDb = mDbHelper.getWritableDatabase(); //데이터베이스
     
        readAllRecords(); //처음에 데이터베이스에 저장된거 올리기
        
       */
    }
    
    

    
    public void CaptureMapScreen()  //구글맵 사진찍어서 
    {
    	
    	SnapshotReadyCallback callback = new SnapshotReadyCallback() {
                Bitmap bitmap;

                @Override
                public void onSnapshotReady(Bitmap snapshot) {
                    // TODO Auto-generated method stub
                	
                	SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.");
                	Date d = new Date();
                	String date = format.format(d);
                	String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/facedetect/"+date+"_gps.png";
                	
                    bitmap = snapshot;
                    /*String sdcard=Environment.getExternalStorageDirectory().getAbsolutePath();
    		        File cfile=new File(sdcard + "/facedetect");  
    		        cfile.mkdirs(); //폴더가 없을 경우 facedetect 폴더생성*/
    		        
    		       // String path=sdcard + "/facedetect/" + "zzz.jpg";  //facedetect폴더에 저장
                    try {
                		FileOutputStream fos=new FileOutputStream(path);
    		 			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
    		 		
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            myMap.snapshot(callback);
    }
	public void scroldown(){ //스크롤뷰 스크롤 항상 밑에
    	sv.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				sv.fullScroll(ScrollView.FOCUS_DOWN);
			}
		});
		
    }

	

	/*
	Handler ihandler = new Handler(){
		public void handleMessage(Message msg){
			readAllRecords(); //데이터베이스에 저장
			scroldown(); //스크롤 밑으로
			ihandler.sendEmptyMessageDelayed(0, 10000); //20초마다 1번씩 텍스트뷰에 업데이트
		}
	};
    class DbHelper extends SQLiteOpenHelper { //데이터 베이스 테이블 생성
        public DbHelper(Context context) {
            super(context, "ReportCard", null, 1);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table Student (" +
                    "_id integer PRIMARY KEY autoincrement, " +
                    "name TEXT);");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists Student");
            onCreate(db);
        }
    }


    public void onBtnAdd() { //얼굴 인식안됬을때 데이터베이스에 정보저장
    	String strName = DF1.format(new java.util.Date())+ " 얼굴이 인식되지 않았습니다.";
        String strQuery = "insert into Student(name) values (' " 
                + strName + " ');";
        mDb.execSQL(strQuery);
    }
    
    public void onBtnAdd1() { //얼굴 인식 됬을때 데이터베이스에 정보저장
    	String strName = DF1.format(new java.util.Date()) + " 얼굴이 인식되었습니다.";
        String strQuery = "insert into Student(name) values (' " 
                + strName + " ');";
        mDb.execSQL(strQuery);
    }


    public void readAllRecords() { //데이터베이스 텍스트저장
    	String t ="";
    	
        String strQuery = "select _id, name from Student";
        mCursor = mDb.rawQuery(strQuery, null);
        
        for(int i=0; i < mCursor.getCount(); i++) {
            mCursor.moveToNext();
            int nId = mCursor.getInt(0);
            String strName = mCursor.getString(1);
            String strRecord = strName;
            t += strRecord + "\n";
            tlog.setText(t);
        }

    }
*/

    
    
    private void initMap() { //GPS 출발위치 찍어주고 현재자기위치 화살표로 찍어주는 
		// TODO Auto-generated method stub
    	GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
    	mGoogleMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        LatLng pos = new LatLng(37.4980, 127.027);
        mGoogleMap.moveCamera( 
                CameraUpdateFactory.newLatLngZoom(pos, 15));
        
        MarkerOptions moStart = new MarkerOptions();
        moStart.position(pos);
        mMarkerStart = mGoogleMap.addMarker(moStart);
        mMarkerStart.showInfoWindow();

        mGoogleMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getApplication(), "출발 위치", 
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });

		MarkerOptions moMan = new MarkerOptions();
		moMan.position(pos);
		moMan.icon(BitmapDescriptorFactory.fromResource(R.drawable.abc));
		mMarkerMan = mGoogleMap.addMarker(moMan);
    }


	LocationListener mLocListener = new LocationListener() { //gps위치 변할때마다 
		
	public void onStatusChanged(String provider, int status, Bundle extras) {}
	public void onProviderEnabled(String provider) {}
	public void onProviderDisabled(String provider) {}
	public void onLocationChanged(Location location) {
	      
				LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
		   		if(mFirstLoc){
		   			mFirstLoc = false;
		   			mMarkerStart.setPosition(position);
		   		}
		   		
		   		mMarkerMan.setPosition(position);
		   		
		   		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
		   		
		   		myLocation=location;
				lati=location.getLatitude();
				longi=location.getLongitude();
				alti=location.getAltitude();
				String Lati=String.format("%.6f",lati);
				String Longi=String.format("%.6f",longi);
				String Alti=String.format("%.0f",alti);
				loca_t="위도 : "+Lati+"\n경도 :"+Longi+"\n고도 :"+Alti;
		   		
		
		}
	};
	
    BroadcastReceiver mBatteryRecv = new BroadcastReceiver() { //배터리 브로드캐스트 
		
		@Override
		public void onReceive(Context context, Intent intent) { //배터리 실행
			// TODO Auto-generated method stub
			showBattery(intent);
					}
	};
	
    public void showBattery(Intent intent) { //배터리 잔량 구하기 
		// TODO Auto-generated method stub
		int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
		int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 100);
		int percent = (int)((float)level / (float)scale *100.);
		battery.setText(percent + "%");
		
		if(percent >= 0 && percent <= 5){
			battery.setBackgroundResource(R.drawable.battery0);
		}
		else if(percent >75 && percent <=100){
			battery.setBackgroundResource(R.drawable.battery100);
		}
		else if(percent >50 && percent <=75){
			battery.setBackgroundResource(R.drawable.battery75);
		}
		else if(percent >25 && percent <=50){
			battery.setBackgroundResource(R.drawable.battery50);
		}
		else if(percent >5 && percent <= 25){
			battery.setBackgroundResource(R.drawable.battery25);
		}
    }



    
    public void onBtnPlay(int lev){ //음악재생
    	mPlayer = MediaPlayer.create(this, R.raw.error);

    	if(mPlayer.isPlaying()){
			if(lev != 1){}
			else
				mPlayer.stop();
			}
    	else{
    		if(lev != 1)
    			mPlayer.start();
    		else{}
    	}
			 
	 }
    
	Handler mHandler =new Handler(){ //시간 나타내는 핸들러
    	 public void handleMessage(Message msg){ 
             textView.setText(DF.format(new java.util.Date())); 
             num();

             mHandler.sendEmptyMessageDelayed(0, 1000);
         }
     }; 
     
    public void num(){ //메인화면에 번호출력
    			try {
					FileInputStream fis = new FileInputStream(fileName);
					InputStreamReader isr = new InputStreamReader(fis);
					 
		            // 임의의 길이로 문자열을 읽는다.
		            StringBuilder sb = new StringBuilder();
		            char[] inputBuffer = new char[2048];
		            int l, j;
		            // 버퍼에 데이터를 채운다.
		            while ((l = isr.read(inputBuffer)) != -1) {
		                sb.append(inputBuffer, 0, l);
		            }
		 
		            // 바이트를 문자열로 변환한다.
		            String readString = sb.toString();
		            getnum.setText(readString);
		            fis.close();
				} catch (Exception e) {
					// TODO: handle exception
					Log.e("File", "에러");
				}
				
				try {
					FileInputStream fis1 = new FileInputStream(fileName1);
					InputStreamReader isr1 = new InputStreamReader(fis1);
					 
		            // 임의의 길이로 문자열을 읽는다.
		            StringBuilder sb1 = new StringBuilder();
		            char[] inputBuffer1 = new char[2048];
		            int l, j;
		            // 버퍼에 데이터를 채운다.
		            while ((j = isr1.read(inputBuffer1)) != -1) {
		                sb1.append(inputBuffer1, 0, j);
		            }
		 
		            // 바이트를 문자열로 변환한다.
		            String readString1 = sb1.toString();
		            getnum1.setText(readString1);
		            fis1.close();
				} catch (Exception e) {
					// TODO: handle exception
				}
		 }
     
   
	
	
	
	public void onResume(){
		super.onResume();
		String locProv = LocationManager.GPS_PROVIDER; //gps
		mLocMgr.requestLocationUpdates(locProv, (long)3000, 3.f, mLocListener);//gps
	        
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
	
	}
	
	public void onPause(){
	
	    super.onPause();
	    mLocMgr.removeUpdates(mLocListener); //gps
	    
	    if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
	}
    
	
	public void onClick(View v){ //진동 음악 버튼 이벤트
	
		int soundId = 0;
			switch(v.getId()){
			case R.id.set1:
				Intent intent = new Intent(this, SetOneActivity.class);
				startActivity(intent);
				break;
			}
		}

    


    public void onDestroy() {
        super.onDestroy();

    	unregisterReceiver(mBatteryRecv);  //배터리 레지스터 삭제
        mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
        mGray = new Mat();
        mRgba = new Mat();
    }

    public void onCameraViewStopped() {
        mGray.release();
        mRgba.release();
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {

        mRgba = inputFrame.rgba();
        mGray = inputFrame.gray();

        Bitmap img = Bitmap.createBitmap(mRgba.cols(), mRgba.rows(),Bitmap.Config.ARGB_8888);

        MatOfRect faces = new MatOfRect();

        if (((cur_sleepLevel==3)||(cur_sleepLevel==2))&&(mDetectorType == JAVA_DETECTOR)) {
        	if (mAbsoluteFaceSize == 0) {
                int height = mGray.rows();
                if (Math.round(height * mRelativeFaceSize2) > 0) {
                    mAbsoluteFaceSize = Math.round(height * mRelativeFaceSize2);
                }
                mNativeDetector.setMinFaceSize(mAbsoluteFaceSize);
            }
            if (mJavaDetector != null)
                mJavaDetector.detectMultiScale(mGray, faces, 1.1, 2, 2, // TODO: objdetect.CV_HAAR_SCALE_IMAGE
                        new Size(mAbsoluteFaceSize, mAbsoluteFaceSize), new Size());
            Log.i(TAG, "Detector");
        }

        else if ((cur_sleepLevel==1)&&(mDetectorType == JAVA_DETECTOR)) {
        	if (mAbsoluteFaceSize == 0) {
                int height = mGray.rows();
                if (Math.round(height * mRelativeFaceSize1) > 0) {
                    mAbsoluteFaceSize = Math.round(height * mRelativeFaceSize1);
                }
                mNativeDetector.setMinFaceSize(mAbsoluteFaceSize);
            }
            if (mJavaDetector2 != null)
                mJavaDetector2.detectMultiScale(mGray, faces, 1.1, 2, 2, // TODO: objdetect.CV_HAAR_SCALE_IMAGE
                        new Size(mAbsoluteFaceSize, mAbsoluteFaceSize), new Size());
            Log.i(TAG, "Detector2");
        }
        
        Rect[] facesArray = faces.toArray();
        
        faceNum=faces.toArray().length;			// Number of faces
        curTime = System.currentTimeMillis();	//current time set
        
        if(stdTime==0){
     	   stdTime = curTime;
     	   captureTime = 0;
     	   check_Time = 0;
     	   
        }
        
        if(faceNum==0){
        	if((curTime-stdTime)/1000>2 && state<40 && state>5){
        		try{check_log(curTime, check_Time);}
                catch (IOException e1){}
        		cur_sleepLevel = 2;
        		onBtnPlay(cur_sleepLevel);
        		state++;
        		Log.i(TAG, "Level 2");
        	}
        	else if((curTime-stdTime)/1000>2 && state>=40){
        		if(captureTime ==0 || (curTime-captureTime)/1000>90 ){
        			try{check_log(curTime, check_Time);}
        	        catch (IOException e1){}
        			CaptureMapScreen(); //구글맵 저장
        			//onBtnAdd(); //얼굴인식 안됬을때 메세지저장
        			cur_sleepLevel = 3;
        			onBtnPlay(cur_sleepLevel);  //음악 재생
            		captureTime = curTime;
            		
            		Utils.matToBitmap(mRgba, img);
            		resizeBitmapImg(img,250);
                    saveImage(img);
                    
                    Log.i(TAG, "Level 3 capture");
                    try {
						sendSMS();
					} catch (IOException e) {}
                    
        		}
        		else{
        			try{check_log(curTime, check_Time);}
        	        catch (IOException e1){}
        			cur_sleepLevel = 3;
        			//onBtnAdd(); //얼굴인식 안됬을때 메세지저장
        			onBtnPlay(cur_sleepLevel);  //음악 재생
        			Log.i(TAG, "Level 3");
        			}
        		
        	}
        	else if((curTime-stdTime)/1000>1)
        		state++;
        }
        
        else{        	
        	stdTime = curTime;	//standard time set
        	if(state>5){
        		cur_sleepLevel = 1;
        		state--;
        		Log.i(TAG, "Level 1 but warnnig!");
        		//onBtnAdd1(); //얼굴인식 됬을떄 데이터베이스 저장
        	}
        	else{        
        		cur_sleepLevel = 1;
        		Log.i(TAG, "Level 1");
        		//onBtnAdd1(); //얼굴인식 됬을떄 데이터베이스 저장
        		}
        	onBtnPlay(cur_sleepLevel);  //음악 재생	
        }   
        
      
        for (int i = 0; i < facesArray.length; i++){
            Core.rectangle(mRgba, facesArray[i].tl(), facesArray[i].br(), FACE_RECT_COLOR, 3);
            
        }
        
        changeColor(cur_sleepLevel);
       
        return mRgba;
    }
    
    public void changeColor(final int level){
    	final ImageView color1 = (ImageView) findViewById(R.id.c1);	//basic color is black
        final ImageView color2 = (ImageView) findViewById(R.id.c2);
        final ImageView color3 = (ImageView) findViewById(R.id.c3);
    	switch(level){
    	case 1:
    		runOnUiThread(new Runnable(){
    			@Override
    			public void run() {
    				// TODO Auto-generated method stub
    				color1.setBackgroundResource(R.drawable.black1);
    				color2.setBackgroundResource(R.drawable.black2);
    				color3.setBackgroundResource(R.drawable.black3);
    				color1.setBackgroundResource(R.drawable.color1);
    			}
            });    		
    		cur_sleepLevel=1;
    		break;
    	case 2:
    		runOnUiThread(new Runnable(){
    			@Override
    			public void run() {
    				color1.setBackgroundResource(R.drawable.black1);
    				color2.setBackgroundResource(R.drawable.black2);
    				color3.setBackgroundResource(R.drawable.black3);
    				color2.setBackgroundResource(R.drawable.color2);
    			}
            });    		
    		cur_sleepLevel=2;
    		break;
    	case 3:
    		runOnUiThread(new Runnable(){
    			@Override
    			public void run() {
    				color1.setBackgroundResource(R.drawable.black1);
    				color2.setBackgroundResource(R.drawable.black2);
    				color3.setBackgroundResource(R.drawable.black3);
    				color3.setBackgroundResource(R.drawable.color3);
    			}
            });    		
    		cur_sleepLevel=3;
    		
    		break;
    		}
    		
    }
    
	public void saveImage(Bitmap bm) {
  	  FileOutputStream stream;
  	  SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.");
  	  Date d = new Date();
  	  String date = format.format(d);
  	  String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/facedetect/"+date+".png";
  	  try {
	    	   stream = new FileOutputStream(path);
	    	   bm.compress(Bitmap.CompressFormat.PNG, 90, stream);
	    	   
  	  }
  	  catch (FileNotFoundException e) {
  	  
  		  e.printStackTrace();
  	  }
  }
 
  public Bitmap resizeBitmapImg(Bitmap source, int maxResolution){ 
      int width = source.getWidth(); 
      int height = source.getHeight(); 
      int newWidth = width; 
      int newHeight = height; 
      float rate = 0.0f;
       
      if(width > height){
              if(maxResolution < width){ 
                      rate = maxResolution / (float) width; 
                      newHeight = (int) (height * rate); 
                      newWidth = maxResolution; 
              }
      }else{ 
              if(maxResolution < height){ 
                      rate = maxResolution / (float) height; 
                      newWidth = (int) (width * rate); 
                      newHeight = maxResolution; 
              }
      }

      return Bitmap.createScaledBitmap(source, newWidth, newHeight, true); 
}
  	public void check_log(long cur_Time, long check_Time) throws IOException{
  		
  		if((cur_Time-check_Time)/1000>5){
  			check_Time = cur_Time;
  			SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.");
  	    	Date d = new Date();
  	    	String date = format.format(d);
  	  		try {
  	  			 RandomAccessFile raf = new RandomAccessFile(log_Path, "rw");
  	  		     raf.seek(raf.length());
  	  		     raf.writeBytes(date+" : Un detected\n");
  						
  			} catch (FileNotFoundException e) {
  				Log.i(TAG, "No such File");
  			}
  	  		
  		}
  		else{}
  		
  		
  	}
  
	public void sendSMS() throws IOException{
		String number1=null,number2 = null;
		// TODO Auto-generated method stub
		TelephonyManager mgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE); 
		String myNumber = mgr.getLine1Number();
		SmsManager smsMgr = SmsManager.getDefault();
		String txt = myNumber+"님 께서\n"+loca_t+"\n의 위치에서 \n졸음운전중입니다.";
		
		String sdRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		String mFilePath1 = sdRootPath+"/facedetect/number2.txt";
		String mFilePath2 = sdRootPath+"/facedetect/number3.txt";
		try {
			File f1 = new File(mFilePath1) ;
			File f2 = new File(mFilePath2) ;
			
			if(f1.exists()){
				BufferedReader br1 = new BufferedReader(new FileReader(f1));
				number1=br1.readLine();
			}
			else{
				f1.createNewFile();
				FileWriter fw = new FileWriter(f1);
				fw.write("default");
				fw.close();
				BufferedReader br1 = new BufferedReader(new FileReader(f1));
				number1=br1.readLine();
				}
			
			if(f2.exists()){
				BufferedReader br2 = new BufferedReader(new FileReader(f2));
				number2=br2.readLine();
			}
			else{
				f2.createNewFile();
				FileWriter fw = new FileWriter(f2);
				fw.write("default");
				fw.close();
				BufferedReader br2 = new BufferedReader(new FileReader(f2));
				number2=br2.readLine();
				}
			
		} catch (FileNotFoundException e) {
			Log.i(TAG, "No such File");
		}

		if(!number1.isEmpty() && number1.length()>10)
			smsMgr.sendTextMessage(number1, null, txt, null, null);
		else
			Log.i(TAG, "No input number1");
		
		if(!number1.isEmpty() && number2.length()>10)
			smsMgr.sendTextMessage(number2, null, txt, null, null);
		else
			Log.i(TAG, "No input number2");
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "called onCreateOptionsMenu");
        mItemFace50 = menu.add("Face size 50%");
        mItemFace40 = menu.add("Face size 40%");
        mItemFace30 = menu.add("Face size 30%");
        mItemFace20 = menu.add("Face size 20%");
        mItemType   = menu.add(mDetectorName[mDetectorType]);
        
    	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "called onOptionsItemSelected; selected item: " + item);
        if (item == mItemFace50)
            setMinFaceSize(0.5f);
        else if (item == mItemFace40)
            setMinFaceSize(0.4f);
        else if (item == mItemFace30)
            setMinFaceSize(0.3f);
        else if (item == mItemFace20)
            
        	setMinFaceSize(0.2f);
        	
        else if (item == mItemType) {
            int tmpDetectorType = (mDetectorType + 1) % mDetectorName.length;
            item.setTitle(mDetectorName[tmpDetectorType]);
            setDetectorType(tmpDetectorType);
            
        }
        return true;
    }

    private void setMinFaceSize(float faceSize) {
        mRelativeFaceSize1 = faceSize;
        mRelativeFaceSize2 = faceSize;
        mAbsoluteFaceSize = 0;
    }

    private void setDetectorType(int type) {
        if (mDetectorType != type) {
            mDetectorType = type;

            if (type == NATIVE_DETECTOR) {
                Log.i(TAG, "Detection Based Tracker enabled");
                mNativeDetector.start();
            } else {
                Log.i(TAG, "Cascade detector enabled");
                mNativeDetector.stop();
            }
        }
    }
}
