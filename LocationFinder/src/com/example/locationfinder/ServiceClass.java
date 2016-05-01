package com.example.locationfinder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ServiceClass extends Service{
	
	public static final long NOTIFY_INTERVAL = 10 * 1000; // 180 seconds(sec*millsec)
	GetCoordinates gps;
	List<Address> address;
			
	DatabaseClass loc_db;
	
	 
	// run on another Thread to avoid crash
	    private Handler mHandler = new Handler();
	    // timer handling
	    private Timer mTimer= new Timer();	   

			@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
	//	throw new UnsupportedOperationException("Not yet implemented");
		return null;
	}

	@Override
	 public void onCreate() {
		
		Toast.makeText(getApplicationContext(), "Started", Toast.LENGTH_LONG).show();
		loc_db = new DatabaseClass(this);
	        // cancel if already existed
//		Toast.makeText(this, "Service was Created", Toast.LENGTH_LONG).show();
		// Log.i("In","oncreate");
//	        if(mTimer != null) {
//	            mTimer.cancel();
//	        }
//	        } else {
//	            // recreate new
//	    //        mTimer = new Timer();
//	        }
	      //   schedule task
//	        mTimer.scheduleAtFixedRate(new auto(), 0, NOTIFY_INTERVAL);
	    }
	
	public void onStart(Intent intent, int startId) {
		 
		 Log.i("In","onStart");
	//	Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
	//	getcoordinates();
		 			
		 mTimer.scheduleAtFixedRate(new auto(), 0, NOTIFY_INTERVAL);
	 }
	
	@Override
	 public void onDestroy() {
//	  Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
	  mTimer.cancel();
	 }
	
	
		
	
	class auto extends TimerTask{
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			mHandler.post(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					getcoordinates();	
				}
					
			});
			
		}
	}
	
	
	

	 @SuppressLint("NewApi")
	public void getcoordinates() {
		 		 
		 
		 gps = new GetCoordinates(ServiceClass.this);   //Create class object
		 
		 if(gps.canGetLocation()) {
				
			    double lat = gps.getLatitude();
				double log = gps.getLongitude();
				
				String t1 = String.valueOf(lat);  //Convert value of Double into String
				String t2 = String.valueOf(log);
		 
		 
				try{
					Geocoder go = new Geocoder(ServiceClass.this,Locale.ENGLISH);
					address = go.getFromLocation(lat, log, 1);
					
					
					StringBuilder str = new StringBuilder();
					if(Geocoder.isPresent()){
						Toast.makeText(getApplicationContext(),
								"geocoder present", Toast.LENGTH_SHORT).show();
						 if(address != null && address.size()>0) {
						Address add = address.get(0);
						
					//	String locality = add.getLocality();
						String locality = add.getCountryName();
						String country = add.getCountryCode();
						String region = add.getSubLocality();
						
						str.append(country+"/ ");
						str.append(locality+"/ ");
						str.append(region+" ");
						
											
						String addr = str.toString();      //convert StringBuilder into String.
						loc_db.insertemp(t1, t2,addr);
						Toast.makeText(getApplicationContext(), "Current Latitude: "+lat+"\n"+"Current Longitude: "+log+"\nLocation: "+addr, Toast.LENGTH_LONG).show();
						 }
					}
					else{
						
						Toast.makeText(getApplicationContext(),
								"Can not find geocoder", Toast.LENGTH_SHORT).show();
					}
						
					
				}catch(IOException e){
					e.printStackTrace();
				}
		 }
		 
		
  }


}
