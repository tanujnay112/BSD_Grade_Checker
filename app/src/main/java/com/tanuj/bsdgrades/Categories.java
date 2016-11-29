package com.tanuj.bsdgrades;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Element;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

public class Categories extends Fragment{
	ListView grades;
	AssignmentAdapter c;
	static ArrayList<Assignment> assigns;
	ProgressBar circle;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_1, container, false);
		grades = (ListView) v.findViewById(R.id.listview1);
		assigns = new ArrayList<Assignment>();
		circle = (ProgressBar) v.findViewById(R.id.progressBar);
		c = new AssignmentAdapter(v.getContext(), assigns, getResources());
		grades.setAdapter(c);
		(new Connect()).execute();
		return v;
	}
	
	class Connect extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			circle.setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
				if (Class.doc == null) {
					Response resp = Jsoup
							.connect(com.tanuj.bsdgrades.Class.url)
							.cookie("ASPXAUTH", LoginPage.cookie)
							.method(Method.POST).execute();
					Class.doc = resp.parse();
				}
				Assignment a = null;
				for (Element e : Class.doc
						.select("table.reportTable > tbody > tr")) {
					// this is a little hackish, but check to make sure this tr
					// has
					// at least 5 children (tds)
					// if (e.children().size() >= 5) {
					// if so, print out the 1st child (country name)
					// and the 5th child (exchange rate)

					if (e.children().size() == 5) {
						a = new Assignment();
						a.setDescription(e.child(0).text());
						a.setCategory(e.child(1).text());
						a.setGrade(e.child(2).text());
						a.setMax(e.child(3).text() + "%");
						a.setLetter(e.child(4).text());
						assigns.add(a);
						a = null;
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			circle.setVisibility(View.GONE);
			c.notifyDataSetChanged();
			grades.setVisibility(View.VISIBLE);
		}
	}
}
