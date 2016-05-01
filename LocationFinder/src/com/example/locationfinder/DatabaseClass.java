package com.example.locationfinder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class DatabaseClass {

	final String TAG="Database of Locations";
	public static final String DATABASE_NAME = "locDB";
	private static final int DATABASE_VERSION = 4;
	private static final String TABLE_NAME = "locationTable";
	
	private Context context;
	private SQLiteDatabase locdb;
	private SQLiteStatement insertStmt;

	private static final String INSERT = "insert into " + TABLE_NAME + " (latitude,longitude,address) values (?,?,?)";
	
	public DatabaseClass(Context context){
		
		this.context=context;
		OpenHelper openhelper = new OpenHelper(this.context);
		this.locdb = openhelper.getWritableDatabase();
	
	    this.insertStmt = this.locdb.compileStatement(INSERT); 
	   
	    
	}   
	
	
	
	public void insertemp(String latitude, String longitude,String address){
		
		this.insertStmt.bindString(1, latitude);
		this.insertStmt.bindString(2, longitude);
		this.insertStmt.bindString(3, address);
		//this.insertStmt.bindString(3, department);
		
//		 Log.i(TAG, "Employee id=" +latitude);
		 
		  this.insertStmt.executeInsert();
//		  Log.i(TAG,"execute");
		    
	}
	
	
	public Cursor getDataFromSqlite()
	{
		
		Cursor c= locdb.rawQuery("SELECT * FROM locationTable", null);
		
		Log.e("databse ", ": "+c);
		
				if(c!=null)
				{					
					c.moveToFirst();
					return c;
				}
		
		return null;
	}
	
	
	
	
	public static class OpenHelper extends SQLiteOpenHelper{

		public OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + TABLE_NAME + " (Locid INTEGER PRIMARY KEY, latitude DOUBLE,longitude DOUBLE,address STRING)");
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
			Log.w("Example", "Upgrading database, this will drop table and recreate");
			
			db.execSQL("drop table if exists " + TABLE_NAME);
			
			onCreate(db);
			
		}
		
	}
	
}
