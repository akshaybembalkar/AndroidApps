package com.akshay.employeedatafragment;


import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EmployeeListFragment extends Fragment{

	Context context;
	View v;
    ListView lv;
	 ArrayList<GetterSetter_Class> myList = new ArrayList<GetterSetter_Class>();
	 Database_Class emp_db;
	 AdapterClass adapter;
	 Cursor c;
	 
//	 List<String> tasks = new ArrayList<String>();
	 
	 
	 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		if(container == null)
			return null;
		
	    v = inflater.inflate(R.layout.emplist_fragment, container, false);
		return v;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			
		context = getActivity();
		
	}
	
		
	
	@Override
	public void onPause(){
		super.onPause();
		
		myList.clear();
		getActivity().getFragmentManager().beginTransaction().remove(this).commit();
	}
	
	@Override 
	public void onViewCreated (View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	
		
		 lv= (ListView) v.findViewById(android.R.id.list);
		 getData();
		 lv.setAdapter(new AdapterClass(context, myList));
		 
		 lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
				
					String t1 = ((TextView) view.findViewById(R.id.textView2)).getText().toString();
			//		Toast.makeText(getActivity(), t1, Toast.LENGTH_LONG).show();
				
					
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
		 
		 
		 lv.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent, final View view,
						final int position, final long id) {
					// TODO Auto-generated method stub
					
	            	
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());      //getParent() to call parent activity context
																									//instead of current activity context
					
					alertDialog.setMessage("Do you want to Delete?");
					
					alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog,int which) {
//			           
			            	
			            	
			            	String t1 = ((TextView) view.findViewById(R.id.textView2)).getText().toString();
			            	emp_db.deleteSelected(t1);
			            	
			            	Fragment f = new EmployeeListFragment();
							FragmentManager fm = getFragmentManager();
							FragmentTransaction ft = fm.beginTransaction().addToBackStack("key3");
							ft.replace(R.id.fragment_container, f);
							ft.commit();
			     
			            	
			            }
			        });
					
	            			
					
					alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int which) {
			            dialog.cancel();
			            }
			        });
					
					
					if(!((Activity) context).isFinishing()){
						 alertDialog.show();		
					}		 
	            	
					return false;
				}
			});

	}
	
	
	public  void getData() {
		// TODO Auto-generated method stub
		emp_db=new Database_Class(getActivity());
		
		c= emp_db.getDataFromSqlite();
	//	c = Database_Class.getDataFromSqlite();
		
		while(!c.isAfterLast()){
			String t1 = c.getString(0);       //Returns Value of particular column as string
			String t2 = c.getString(1);
			
			int id = Integer.parseInt(t1);
			
			Log.i("Value is: ", t1+t2);
			c.moveToNext();
		
//			tasks.add(t2);
//			
//			  ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
//		              android.R.layout.simple_list_item_1, android.R.id.text1, tasks);
//			  
//			  lv.setAdapter(adapter);
			
		 myList.add(0,new GetterSetter_Class(t2, id));
		 
		 
		}		
		
	}
	
		
	
}
