package com.akshay.employeedatafragment;

import java.util.ArrayList;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Upldatelist_Fragment extends Fragment{
	
	View v;
	Context context;
	ListView lv;
	 ArrayList<GetterSetter_Class> myList = new ArrayList<GetterSetter_Class>();
	 Database_Class emp_db;
	 AdapterClassUpdated adapter;
	 Cursor c;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		if(container == null)
			return null;	
	    v = inflater.inflate(R.layout.updatelist_fragment, container, false);
		return v;
	
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			
		context = getActivity();
		
//		lv.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// TODO Auto-generated method stub
//				
//				String t1 = ((TextView) view.findViewById(R.id.textView1)).getText().toString();
//	   			
//				Toast.makeText(context, t1, Toast.LENGTH_SHORT).show();
				
//				c = emp_db.getDatawithid(t1);
//		
//			
//				String name = null,age = null,designation = null,experience = null,date = null;
//				Log.i("db count", "db count= "+c.getCount());
//				while(c.moveToNext()){
//					Log.i("db count", "db name value= "+c.getString(1));
//					
//					name = c.getString(0);
//					age = c.getString(1);
//					designation = c.getString(2);
//				experience = c.getString(3);
//					date = c.getString(4);
//					
//				}
////				
//				
//		//		Intent in = new Intent(Employee_List.this,Employee_Details.class);
//				Intent in = new Intent(Updated_list.this,Edit_Employee.class);
//				in.putExtra("KEY_ID", t1);
//				in.putExtra("KEY_NAME", name);
//				in.putExtra("KEY_AGE", age);
//				in.putExtra("KEY_des", designation);
//				in.putExtra("KEY_exp", experience);
//				in.putExtra("KEY_date", date);
//			//	startActivity(in);
//				
//				StringBuffer str = new StringBuffer();
//				
//				View view1 = ActivityGroupUpdate.group.getLocalActivityManager()
//				        .startActivity("show_city", in
//				        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//				        .getDecorView();
//			 
//				        // Again, replace the view
//				ActivityGroupUpdate.group.replaceView(view1);
//				
//				
//			}
//		});
		
	}
	
	public void onViewCreated (View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	
		
		 lv= (ListView) v.findViewById(android.R.id.list);
		 getupdateData();
		 lv.setAdapter(new AdapterClassUpdated(context, myList));
		 
		 
		 lv.setOnItemClickListener(new OnItemClickListener() {
			 
			 			@Override
			 			public void onItemClick(AdapterView<?> parent, View view,
			 					int position, long id) {
			 				// TODO Auto-generated method stub
			 				
			 				String t1 = ((TextView) view.findViewById(R.id.textView1)).getText().toString();
			 	   			
			 	//			Toast.makeText(context, t1, Toast.LENGTH_SHORT).show();

			 				c = emp_db.getDatawithid(t1);
							
							
							String name = null,age = null,designation = null,experience = null,date = null;
							Log.i("db count", "db count= "+c.getCount());
							while(c.moveToNext()){
								Log.i("db count", "db name value= "+c.getString(1));
								
								name = c.getString(0);
								age = c.getString(1);
								designation = c.getString(2);
							    experience = c.getString(3);
								date = c.getString(4);
								
							}		
						
							Fragment f = new Editemp_Fragment();
							
						Bundle bundle = new Bundle();
						bundle.putString("ID", t1);
						bundle.putString("NAME", name);
						bundle.putString("AGE", age);
						bundle.putString("DESIGNATION", designation);
						bundle.putString("EXPERIENCE", experience);
						bundle.putString("DATE", date);
						f.setArguments(bundle);
							
							
						
							FragmentManager fm = getFragmentManager();
					         FragmentTransaction fragmentTransaction = fm.beginTransaction();
					         fragmentTransaction.replace(R.id.fragment_container, f);
					         fragmentTransaction.addToBackStack(null);
					         fragmentTransaction.commit();
						
 			}
		});
	}
	
	
	public  void getupdateData() {
		// TODO Auto-generated method stub
		emp_db=new Database_Class(getActivity());
		
		c= emp_db.getupdatedDataFromSqlite();
	//	c = Database_Class.getDataFromSqlite();
		
		while(!c.isAfterLast()){
			String t1 = c.getString(0);       //Returns Value of particular column as string
			String t2 = c.getString(1);
			
			int id = Integer.parseInt(t1);
			
			Log.i("Value is: ", t1);
			Log.i("Value is: ", t2);
			c.moveToNext();
			
		//			tasks.add(t2);
//			
//			  ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
//		              android.R.layout.simple_list_item_1, android.R.id.text1, tasks);
//			  
//			  lv.setAdapter(adapter);
		 myList.add(0,new GetterSetter_Class(id,t2));
		 
		
		}		 
		 
		}
	
	public void onPause(){
		super.onPause();
		myList.clear();
		getActivity().getFragmentManager().beginTransaction().remove(this).commit();
	}
	
	

}
