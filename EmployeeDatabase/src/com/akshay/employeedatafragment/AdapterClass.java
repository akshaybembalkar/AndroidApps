package com.akshay.employeedatafragment;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterClass extends BaseAdapter {
	
ArrayList<GetterSetter_Class> mylist = new ArrayList<GetterSetter_Class>();

	
	LayoutInflater inflater;
	Context context;
	
	
	
	public AdapterClass(Context context,ArrayList<GetterSetter_Class> mylist){
		
		this.mylist=mylist;
		this.context=context;
		inflater = LayoutInflater.from(this.context);
		
	}

//	public AdapterClass(String str){
//		
//		this.mylist = 
//	}
	
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mylist.size();
	}

	@Override
	public GetterSetter_Class getItem(int arg0) {
		// TODO Auto-generated method stub
		return mylist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		MyViewHolder vh = new MyViewHolder();
		
		if(convertView==null){
			
			convertView=inflater.inflate(R.layout.employee_list, null);

			convertView.setTag(vh);			
		}
		else
			vh = (MyViewHolder) convertView.getTag();
		
	//	 mViewHolder.tvTitle = detail(convertView, R.id.tvTitle, myList.get(position).getTitle());
	//	Data_List i = new Data_List();
		GetterSetter_Class i = mylist.get(position);
	
		TextView name = (TextView) convertView.findViewById(R.id.textView1);
		TextView id=(TextView) convertView.findViewById(R.id.textView2);
		
		id.setVisibility(View.GONE);
	//	TextView add=(TextView) convertView.findViewById(R.id.textView3);
		
	//	lat.setText("Name:");
		name.setText(String.valueOf(i.getname()));
		//namedata.setText(mylist.get(position));
		
	//	age.setText("Age: "+ i.getage());
		id.setText(String.valueOf(i.getage()));
		
		
		
		return convertView;
	}



	private class MyViewHolder 
	{ 
		TextView textView1,textView2; 
	//	ImageView ivIcon; 
	}

}