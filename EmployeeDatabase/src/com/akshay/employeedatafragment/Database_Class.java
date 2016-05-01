package com.akshay.employeedatafragment;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;


public class Database_Class {
	
	final String TAG="Database of Employee";
	public static final String DATABASE_NAME = "empDB";
	public static final String DATABASE_NAME2 = "updatedempDB";
	private static final int DATABASE_VERSION = 1;
	private static final int DATABASE_VERSION2 = 3;
	private static final String TABLE_NAME = "employeeTable";
	private static final String TABLE_NAME2 = "updateemployeeTable";
	private static final String KEY_ID = "Empid";
	private static final String KEY_ID2 = "upEmpid";
	public static final String NAME = "name";
	
	private Context context;
	private SQLiteDatabase empdb,updateddb;
	private SQLiteStatement insertStmt,insertStmt2,deletestmt,updatestmt;
    String n;
	private static final String INSERT = "insert into " + TABLE_NAME + " (name,age,designation,Experience,DateofJoining,updated) values (?,?,?,?,?,?)";
	private static final String INSERT2 = "insert into " + TABLE_NAME  + " (updateid,updatename) values (?,?)"+ "where Empid=?";
	private static final String DELETE = "delete from " + TABLE_NAME + " where Empid=?";

	
//	Updated_list ul;
	public Database_Class(Context context){
		
		
		this.context=context;
		OpenHelper openhelper = new OpenHelper(this.context);
		this.empdb = openhelper.getWritableDatabase();
	
	
	    this.insertStmt = this.empdb.compileStatement(INSERT);
//	    this.insertStmt2 = this.empdb.compileStatement(INSERT2);
	    	    
	    this.deletestmt = this.empdb.compileStatement(DELETE);
	
	   
	    this.updateddb = openhelper.getWritableDatabase();
	
	}   
	
	
	
	public void insertemp(String name, String age,String designation,String Experience,String DateofJoining){
		
		this.insertStmt.bindString(1, name);
		this.insertStmt.bindString(2, age);                         //bindString is used to insert data instead of '?'
		this.insertStmt.bindString(3, designation);
		this.insertStmt.bindString(4, Experience);
		this.insertStmt.bindString(5, DateofJoining);
		this.insertStmt.bindString(6, "N");
			
//		 Log.i(TAG, "Employee id=" +latitude);
		 
		  this.insertStmt.executeInsert();               //Executes statement and returns ID of data inserted
		  empdb.close();
		    
	}
	
	
	public Cursor getDataFromSqlite()
	{
		
		Cursor c= empdb.rawQuery("SELECT * FROM employeeTable", null);
		
		Log.e("databse ", ": "+c);
		
				if(c!=null)
				{					
					c.moveToFirst();
					return c;
				}
				empdb.close();
		
		return null;
	}
	
	
	public Cursor getupdatedDataFromSqlite()
	{
		String where="updated= '"+"Y"+"'";
		Cursor c=empdb.query(TABLE_NAME, new String[] {"Empid","name","age","designation","Experience","DateofJoining"}, where, null, null, null, null);
	//	Cursor c= empdb.rawQuery("SELECT * FROM employeeTable", null);
//		String t = c.getString(0);
//		Toast.makeText(context, t, Toast.LENGTH_SHORT).show();
		Log.e("databse ", ": "+c);
		
				if(c!=null)
				{					
					c.moveToFirst();
					return c;
				}
		empdb.close();
		return null;
	}
	
	public void deleteSelected(String id){
		this.deletestmt.bindString(1, id);
		this.deletestmt.execute();
	
		return;
		
	}
	
	public void updateStatement(String name, String age,String designation,String Experience,String DateofJoining,String id){

		 try{
		String qry = "UPDATE "+TABLE_NAME+" SET name= '"+ name +"',age='"+age+"',designation='"+designation+"',Experience='"+Experience+"',DateofJoining='"+DateofJoining+"' WHERE Empid = "+"'"+id+"'";
		this.empdb.execSQL(qry);
		
	    n = name;

	    
	    String up = "UPDATE "+TABLE_NAME+" SET updated= '"+ "Y" +"' WHERE Empid = "+"'"+id+"'";
	    this.empdb.execSQL(up);
	    
	    empdb.close();
//	    this.insertStmt2.bindString(1, id);
//	    this.insertStmt2.bindString(2, name);
//	    this.insertStmt2.bindString(3, id);
	    
//	    this.insertStmt2.executeInsert();
		
		 }catch(SQLException e){
			 empdb.close();
			 e.printStackTrace();
		 }
		 
	//	Toast.makeText(context, name+age+id, Toast.LENGTH_SHORT).show();
		
		
	}
	
	public static class OpenHelper extends SQLiteOpenHelper{

		public OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		//	super(context, DATABASE_NAME2, null, DATABASE_VERSION2);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + TABLE_NAME + " (Empid INTEGER PRIMARY KEY , name TEXT,age INTEGER,designation TEXT,Experience VARCHAR,DateofJoining DATE,updated VARCHAR)");
		
			db.close();
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
			Log.w("Example", "Upgrading database, this will drop table and recreate");
			
			db.execSQL("drop table if exists " + TABLE_NAME);	
			
			onCreate(db);
			
		}
		
		
		
	}
	
	public Cursor getDatawithid(String id)
	{
		String where="Empid= '"+id+"'"; 
		
//		Cursor c= empdb.rawQuery("SELECT * FROM employeeTable WHERE Empid="+id, null);
		Cursor c=empdb.query(TABLE_NAME, new String[] {"name","age","designation","Experience","DateofJoining"}, where, null, null, null, null);
	
		
		return c;
	}
	
	
}
