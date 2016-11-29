package com.tanuj.bsdgrades;

import java.util.ArrayList;


import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
public class CustomAdapter extends BaseAdapter implements OnClickListener {

	/*********** Declare Used Variables *********/
	private ArrayList<Thing> data;
	private static LayoutInflater inflater = null;
	public Resources res;
	int i = 0;
	Thing tempValue = null;

	/************* CustomAdapter Constructor *****************/
	public CustomAdapter(Context c, ArrayList<Thing> d, Resources resLocal) {

		/********** Take passed values **********/
		data = d;
		res = resLocal;

		/*********** Layout inflator to call external xml layout () ***********/
		inflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	/******** What is the size of Passed Arraylist Size ************/
	public int getCount() {

		if (data.size() <= 0)
			return 1;
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView text;
		public TextView text1;
	}

	/****** Depends upon data size called for each row , Create each ListView row *****/
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;

		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = inflater.inflate(R.layout.item, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.text = (TextView) vi.findViewById(R.id.tvClass);
			holder.text1 = (TextView) vi.findViewById(R.id.tvGrade);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();

		if (data.size() <= 0) {
			holder.text.setText("-");
			holder.text1.setText("unavailable currently");

		} else {
			tempValue = (Thing) data.get(position);
			holder.text.setText(tempValue.gettClass());
			holder.text1.setText(tempValue.gettGrade());

			/******** Set Item Click Listner for LayoutInflater for each row *******/

		}
		return vi;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
}