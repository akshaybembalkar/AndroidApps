package com.akshay.employeedatafragment;


import android.content.Context;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Empdata_Fragment extends Fragment implements OnClickListener {
	
	Button b;
	EditText t1,t2,t3,t4,t5;
	Database_Class emp_db;
//	Context context = Empdata_Fragment.this;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		if(container == null)
			return null;
		
		View v = inflater.inflate(R.layout.empdata_fragment, container, false);		
		
		return v;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	//	setContentView(R.layout.activity_employee__data);
		
		emp_db=new Database_Class(getActivity());
	
	}
	
	@Override
	public void onStart(){
		super.onStart();
		
		TextView name = (TextView) getActivity().findViewById(R.id.textView2);
		name.setVisibility(View.GONE);
		
		
		t1 = (EditText) getActivity().findViewById(R.id.editText1);
		t2 = (EditText) getActivity().findViewById(R.id.editText2);
		t3 = (EditText) getActivity().findViewById(R.id.editText3);
		t4 = (EditText) getActivity().findViewById(R.id.editText4);
		t5 = (EditText) getActivity().findViewById(R.id.editText5);
		
		b = (Button) getActivity().findViewById(R.id.button1);
		
		b.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String name = t1.getText().toString();
		String age = t2.getText().toString();
		String designation = t3.getText().toString();
		String experience = t4.getText().toString();
		String date = t5.getText().toString();
		
		if(name.matches("") || age.matches(""))
			Toast.makeText(getActivity(), "Enter Required Fields", Toast.LENGTH_SHORT).show();
		
		else if(!t1.getText().toString().matches("[a-zA-Z]+")){
			Toast.makeText(getActivity(), "Enter Only Alphabets", Toast.LENGTH_SHORT).show();
			
		}
		else if(age.length()>3){
			Toast.makeText(getActivity(), "Enter Valid Age", Toast.LENGTH_SHORT).show();
		}
		else{
			emp_db.insertemp(name, age, designation,experience,date);
		   Toast.makeText(getActivity(), "Employee Added", Toast.LENGTH_LONG).show();
		}
		
		t1.setText(null);
		t2.setText(null);
		t3.setText(null);
		t4.setText(null);
		t5.setText(null);
		
		
	}
	
	@Override
	public void onPause(){
		super.onPause();
		getActivity().getFragmentManager().beginTransaction().remove(this).commit();
	}
}