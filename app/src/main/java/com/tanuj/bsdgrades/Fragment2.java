package com.tanuj.bsdgrades;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

public class Fragment2 extends MyFragment implements OnItemClickListener{

	ListView grades;
	CustomAdapter c;
	ArrayList<Thing> things;
	ProgressBar circle;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_2, container, false);
		grades = (ListView) v.findViewById(R.id.listview2);
		things = new ArrayList<Thing>();
		circle = (ProgressBar) v.findViewById(R.id.progressBar);
		c = new CustomAdapter(v.getContext(), things, getResources());
		grades.setAdapter(c);
		grades.setOnItemClickListener(this);
		refresh();
		return v;
	}
	
	public void refresh(){
		(new Connect()).execute();
	}
	
	class Connect extends AsyncTask<String, Integer, String>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			circle.setVisibility(View.VISIBLE);
			things.clear();
			grades.setVisibility(View.GONE);
		}
		
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
				Response resp = Jsoup.connect("https://grades.bsd405.org/Pinnacle/Gradebook/InternetViewer/GradeSummary.aspx")
						.data("ctl00$ContentPlaceHolder$LogonButton","Sign in")
						.cookie("ASPXAUTH", LoginPage.cookie).method(Method.POST).execute();
				Document doc = resp.parse();
				Thing thing = null;
				String grade = "";
				 for (Element e : doc.select("table.reportTable > tbody > tr")) {
				        //this is a little hackish, but check to make sure this tr has
				        //at least 5 children (tds)
				        //if (e.children().size() >= 5) {
				            //if so, print out the 1st child (country name)
				            //and the 5th child (exchange rate)
					 
				            thing = new Thing();
				            grade = e.child(3).text();
				            if(grade.equals("")){
				            	thing = null;
				            	continue;
				            }
				            thing.settClass(e.child(0).text());
				            thing.settGrade(e.child(3).text());
				            thing.setLink(e.child(3).childNode(0).attr("abs:href"));
				            things.add(thing);
				            thing = null;
				        //}
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int i, long arg3) {
		// TODO Auto-generated method stub
		String link;
		try{
		link = things.get(i).getLink();
		}catch(IndexOutOfBoundsException e){
			return;
		}
		if (link != null) {
			Intent intent = new Intent(this.getActivity(), Class.class);
			intent.putExtra("link", things.get(i).getLink());
			startActivity(intent);
		}
	}
}
