package com.example.locationfinder;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {
	Button b;
	Context mContext;
	GetCoordinates gps;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		b = (Button) findViewById(R.id.button3);
		
		
		
		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this,GetLocation_Activity.class);
				startActivity(i);
				
			}
			
		});
		
	}
	
	
	public void start(View view){
		
		gps = new GetCoordinates(MainActivity.this);
		
		if(gps.canGetLocation()){
		startService(new Intent(this, ServiceClass.class));
		}else{
			gps.showSettingsAlert();
		}
		
	}

	public void destroy(View view){
		
		stopService(new Intent(this, ServiceClass.class));
		
	}
	
	

	
}
