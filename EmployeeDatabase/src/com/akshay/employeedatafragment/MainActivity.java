package com.akshay.employeedatafragment;

import java.util.ArrayList;
import java.util.Currency;

import android.app.Fragment;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	public static MainActivity group;
	public ArrayList<View> history;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.history = new ArrayList<View>();
	      group = this;
        

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
  //      actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        ActionBar.Tab addTab = actionBar.newTab().setText("ADD");
        ActionBar.Tab listTab = actionBar.newTab().setText("Employee List");
        ActionBar.Tab uplistTab = actionBar.newTab().setText("Updated List");
        
        Fragment add = new Empdata_Fragment();
        Fragment list = new EmployeeListFragment();
        Fragment uplist = new Upldatelist_Fragment();
        
        addTab.setTabListener(new MyTabsListener(add));
        listTab.setTabListener(new MyTabsListener(list));
        uplistTab.setTabListener(new MyTabsListener(uplist));
        
        actionBar.addTab(addTab);
        actionBar.addTab(listTab);
        actionBar.addTab(uplistTab);
    }
    
    class MyTabsListener implements ActionBar.TabListener {

    	public Fragment fragment;

        public MyTabsListener(Fragment fragment) {
     //   this.fragment = fragment;
        	this.fragment = fragment;
        }
        
		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
			if(!fragment.isAdded())
				ft.replace(R.id.fragment_container, fragment);
			
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
			if(fragment!=null)
			ft.remove(fragment);
			FragmentManager fm = getFragmentManager();
			fm.popBackStack();
			
			
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
		
			
		}
    	
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
        switch (item.getItemId()) {
        case android.R.id.home:

           	try{
//           		MainActivity.group.back();
           		FragmentManager fm = getFragmentManager();
           		if(fm.getBackStackEntryCount()>0){
           			fm.popBackStackImmediate();
           	//		fm.popBackStack();
           			
           			return true;
           		}
           		else
           			finish();
           		
        	}catch(Exception e){
        		e.printStackTrace();
        	}
           	break;
//            return true;
            
        
        	
        	}  
           
        
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onBackPressed(){
    	super.onBackPressed();
    	try{
    	MainActivity.group.back();
    	}catch(Exception e){
    		e.printStackTrace();
    	}	
				
		}
    
    
    public void back() {
		 try {

	            if (history.size() > 1) {
	            	history.remove(history.size()-1);
//	            	history.remove(history.size()-1);
	      
			        setContentView(history.get(history.size()-1));
			      
	               //  setContentView(history.get(history.size()-1));
	            	  
	            } else  {
	              //  finish();
	            }
	      } catch (Exception e) {
	            // TODO: handle exceptione
	            e.printStackTrace();
	        }   
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
