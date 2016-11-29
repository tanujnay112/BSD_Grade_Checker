package com.tanuj.bsdgrades;

import org.jsoup.nodes.Document;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;

public class Class extends ActionBarActivity {
	
	protected static Document doc = null;
	protected static String url = null;
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.assignments_main);
		url = getIntent().getStringExtra("link");
		ViewPager vp = (ViewPager) findViewById(R.id.tabhost2);
		vp.setAdapter(new MyAdapter(getSupportFragmentManager()));
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	}
 
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		doc = null;
		Categories.assigns = null;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.classbar, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.predict:
	            predict();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void predict(){
		if(doc!=null){
			
		}
	}*/
	
	class MyAdapter extends FragmentPagerAdapter{

		public MyAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public Fragment getItem(int i) {
			// TODO Auto-generated method stub
			Fragment fragment = null;
			if(i==0){
				fragment = new Assignments();
			}
			if(i==1){
				fragment = new Categories();
			}
				
			return fragment;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 2;
		}
		
		@Override
	    public CharSequence getPageTitle(int position) {
	        if(position==0)
	        {
	            return "Assignments";
	        }
	        if(position==1)
	        {
	            return "Categories";
	        }
	        return null;
	    }
	}
}
