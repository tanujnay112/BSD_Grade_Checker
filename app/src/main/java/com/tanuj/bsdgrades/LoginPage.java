package com.tanuj.bsdgrades;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class LoginPage extends Activity implements OnClickListener {
	Context c;
	static String cookie = "";
	EditText user;
	EditText pass;
	Button login;
	ProgressBar circle;
	LinearLayout layout;
	CheckBox cb;

	String userN;
	String passwd;

	SharedPreferences data;
	final String userKey = "user";
	final String pwdKey = "password";
	final static String filename = "junat";

	boolean loggedIn = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginpage);
		c = this;
		circle = (ProgressBar) findViewById(R.id.pbLogin);
		layout = (LinearLayout) findViewById(R.id.layout);
		data = getSharedPreferences(filename, 0);
		userN = data.getString(userKey, "");
		loggedIn = !userN.equals("");
		passwd = data.getString(pwdKey, "");
		if (loggedIn) {
			onClick(login);
		}
		user = (EditText) findViewById(R.id.etUser);
		pass = (EditText) findViewById(R.id.etPass);
		login = (Button) findViewById(R.id.bLogin);
		cb = (CheckBox) findViewById(R.id.cbStay);
		login.setOnClickListener(this);
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (isNetworkAvailable()) {
			if (!loggedIn) {
				userN = user.getText().toString();
				passwd = pass.getText().toString();
			}
			(new Connect()).execute(userN, passwd);
		} else {
			AlertDialog.Builder alert = new AlertDialog.Builder(c);
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

	class Connect extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			layout.setVisibility(View.GONE);
			circle.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected String doInBackground(String... a) {
			// TODO Auto-generated method stub
			try {
				String cookie = Connection.main(a[0], a[1]);
				LoginPage.cookie = cookie.substring(21, cookie.length());
				System.out.print(cookie);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (cookie == "") {
				circle.setVisibility(View.GONE);
				layout.setVisibility(View.VISIBLE);
				AlertDialog.Builder alert = new AlertDialog.Builder(c);
				alert.setTitle("Access Denied");
				alert.setMessage("Incorrect Credentials");

				alert.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
							}
						});
				alert.show();
			} else {
				if (cb.isChecked()) {
					data.edit().putString(userKey, userN)
							.putString(pwdKey, passwd).commit();
				}
				Intent i = new Intent(c, MainActivity.class);
				startActivity(i);
			}
		}
	}
}
