package com.example.locationfinder;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterClass extends BaseAdapter{
	
	ArrayList<ConstructorClass> mylist = new ArrayList<ConstructorClass>();
	
	LayoutInflater inflater;
	Context context;
	
	
	
	public AdapterClass(Context context,ArrayList<ConstructorClass> mylist){
		
		this.mylist=mylist;
		this.context=context;
		inflater = LayoutInflater.from(this.context);
		
	}

	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mylist.size();
	}

	@Override
	public ConstructorClass getItem(int arg0) {
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
			
			convertView=inflater.inflate(R.layout.location_list, null);

			convertView.setTag(vh);			
		}
		else
			vh = (MyViewHolder) convertView.getTag();
		
	//	 mViewHolder.tvTitle = detail(convertView, R.id.tvTitle, myList.get(position).getTitle());
	//	Data_List i = new Data_List();
		ConstructorClass i = mylist.get(position);
	
		TextView lat = (TextView) convertView.findViewById(R.id.textView1);
		TextView lon=(TextView) convertView.findViewById(R.id.textView2);
		TextView add=(TextView) convertView.findViewById(R.id.textView3);
		
	//	lat.setText("Name:");
		lat.setText("Latitude: "+i.getLatiude());
		//namedata.setText(mylist.get(position));
		
		lon.setText("Longitude: "+ i.getLongitude());
		
		add.setText("Location: "+i.getAddress());
	//	pricedata.setText(i.getprice());
		
		
		return convertView;
	}



	private class MyViewHolder 
	{ 
		TextView textView1,textView2; 
		ImageView ivIcon; 
	}
	

}
