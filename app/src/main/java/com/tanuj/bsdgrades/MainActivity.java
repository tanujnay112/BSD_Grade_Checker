package com.tanuj.bsdgrades;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {

	MyAdapter adapter;
	int item = 0;
	String currentFrag;
	ViewPager vp;
	SharedPreferences data;
	final String state = "STATE";
	int sNum = 0;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
		vp = (ViewPager) findViewById(R.id.tabhost);
		adapter = new MyAdapter(getSupportFragmentManager());
		vp.setAdapter(adapter);
		data = getSharedPreferences("TDATA", MODE_PRIVATE);
		sNum = data.getInt(state, 0);
		vp.setCurrentItem(sNum);
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		data.edit().putInt(state, sNum).commit();
		super.onStop();
	}

	class MyAdapter extends FragmentPagerAdapter {

		public MyAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int i) {
			// TODO Auto-generated method stub
			Fragment fragment = null;
			if (i == 0) {
				fragment = new Fragment1();
			}
			if (i == 1) {
				fragment = new Fragment2();
			}
			sNum = i;
			return fragment;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			if (position == 0) {
				return "Semester 1";
			}
			if (position == 1) {
				return "Semester 2";
			}
			return null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_logout:
			logout();
			break;
		case R.id.action_refresh:
			refresh();
		}
		return true;
	}

	void refresh() {
		// TODO Auto-generated method stub
		if (isNetworkAvailable()) {
			MyFragment frg = null;
			frg = (MyFragment) getSupportFragmentManager().findFragmentByTag(
					getFragmentTag(vp.getId(), 0));
			frg.refresh();
			frg = (MyFragment) getSupportFragmentManager().findFragmentByTag(
					getFragmentTag(vp.getId(), 1));
			frg.refresh();
		} else {
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("No Network");
			alert.setMessage("No internet connection available.");

			alert.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
						}
					});
			alert.show();
		}
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	private void logout() {
		// TODO Auto-generated method stub
		LoginPage.cookie = "";
		getSharedPreferences(LoginPage.filename, 0).edit().clear().commit();
		Class.doc = null;
		Intent i = new Intent(this, LoginPage.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(i);
	}

	private String getFragmentTag(int viewPagerId, int fragmentPosition) {
		return "android:switcher:" + viewPagerId + ":" + fragmentPosition;
	}
}
