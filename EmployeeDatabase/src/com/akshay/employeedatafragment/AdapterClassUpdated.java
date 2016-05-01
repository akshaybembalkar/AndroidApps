package com.akshay.employeedatafragment;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterClassUpdated extends BaseAdapter{

ArrayList<GetterSetter_Class> mylist = new ArrayList<GetterSetter_Class>();

	
	LayoutInflater inflater;
	Context context;
	
public AdapterClassUpdated(Context context,ArrayList<GetterSetter_Class> mylist){
		
		this.mylist=mylist;
		this.context=context;
		inflater = LayoutInflater.from(context);

		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mylist.size();
	}

	@Override
	public GetterSetter_Class getItem(int position) {
		// TODO Auto-generated method stub
		return mylist.get(position);

	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MyViewHolder vh = new MyViewHolder();
		
		if(convertView==null){
			
			convertView=inflater.inflate(R.layout.updated_list, null);

			convertView.setTag(vh);			
		}
		else
			vh = (MyViewHolder) convertView.getTag();
		
	//	 mViewHolder.tvTitle = detail(convertView, R.id.tvTitle, myList.get(position).getTitle());
	//	Data_List i = new Data_List();
		GetterSetter_Class i = mylist.get(position);
	
	//	TextView name = (TextView) convertView.findViewById(R.id.textView4);
		
		TextView id = (TextView) convertView.findViewById(R.id.textView1);
		TextView name = (TextView) convertView.findViewById(R.id.textView2);
	
		
	    id.setVisibility(View.GONE);	
	//	lat.setText("Name:");
		id.setText(String.valueOf(i.getupdatedid()));
		name.setText(String.valueOf(i.getupdatedname()));
		//namedata.setText(mylist.get(position));
		
	//	age.setText("Age: "+ i.getage());
	//	age.setText(String.valueOf(i.getage()));
		
		return convertView;
	}

	
	private class MyViewHolder 
	{ 
		TextView textView1,textView2; 
	//	ImageView ivIcon; 
	}
	
}
