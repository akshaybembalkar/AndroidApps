package com.example.locationfinder;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

public class GetLocation_Activity extends ListActivity {
	
	 ListView lv; 
	 Context context = GetLocation_Activity.this;
	 
	 ArrayList<ConstructorClass> myList = new ArrayList<ConstructorClass>();
	 DatabaseClass loc_db;
	 Cursor c;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_location_);
		
		lv= (ListView) findViewById(android.R.id.list);
		getData();
		
		lv.setAdapter(new AdapterClass(context, myList));
	}

	
	private void getData() {
		// TODO Auto-generated method stub
		loc_db=new DatabaseClass(this);
		
		c= loc_db.getDataFromSqlite();
		
		while(!c.isAfterLast()){
			String t1 = c.getString(1);
			String t2 = c.getString(2);
			String t3 = c.getString(3);
			Log.i("Value is: ", t1+t2+t3);
			c.moveToNext();
		
		
		 myList.add(0,new ConstructorClass(t1, t2,t3));
		 
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.get_location_, menu);
		return true;
	}

}
