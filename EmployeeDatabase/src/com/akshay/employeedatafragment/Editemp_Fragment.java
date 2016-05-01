package com.akshay.employeedatafragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Editemp_Fragment extends Fragment{
	
	View v;
	Button b;
	Database_Class db;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		if(container == null)
			return null;
		
	    v = inflater.inflate(R.layout.editemp_fragment, container, false);
		return v;
	
	}

	@Override 
	public void onViewCreated (View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	
		b = (Button) v.findViewById(R.id.button1);
		db = new Database_Class(getActivity());
		
		final String eid,ename,eage,edesignation,eexperience,edate;
		
		
		Bundle bundle = this.getArguments();
		
		eid = bundle.getString("ID");
		ename = bundle.getString("NAME");
		eage = bundle.getString("AGE");
		edesignation = bundle.getString("DESIGNATION");
		eexperience = bundle.getString("EXPERIENCE");
		edate = bundle.getString("DATE");
		
		TextView id = (TextView) v.findViewById(R.id.textView8);
		id.setText("id: "+eid);
		
		
		final EditText t1 = (EditText)v.findViewById(R.id.editText1);
		final EditText t2 = (EditText)v.findViewById(R.id.editText2);
		final EditText t3 = (EditText)v.findViewById(R.id.editText3);
		final EditText t4 = (EditText)v.findViewById(R.id.editText4);
		final EditText t5 = (EditText)v.findViewById(R.id.editText5);
		
		t1.setText(ename);
		t2.setText(eage);
		t3.setText(edesignation);
		t4.setText(eexperience);
		t5.setText(edate);
		
		
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				    String name = t1.getText().toString();
					String age = t2.getText().toString();
					String desig = t3.getText().toString();
					String exp = t4.getText().toString();
					String date = t5.getText().toString();
			
					db.updateStatement(name,age,desig,exp,date,eid);
				
			//	    ul.getupdatedData(name);
					Toast.makeText(getActivity(), "Employee Updated", Toast.LENGTH_SHORT).show();
					
					
					Fragment f = new EmployeeListFragment();
					FragmentManager fm = getFragmentManager();
					FragmentTransaction ft = fm.beginTransaction();
					ft.replace(R.id.fragment_container, f);
					
					ft.commit();
				
			}
		});
	}
	
	@Override
	public void onPause(){
		super.onPause();
		getActivity().getFragmentManager().beginTransaction().remove(this).commit();
	}
	
	}
