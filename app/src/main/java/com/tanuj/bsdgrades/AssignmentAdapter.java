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

public class AssignmentAdapter extends BaseAdapter implements OnClickListener {

	Context context;
	ArrayList<Assignment> data;
	Resources res;
	LayoutInflater inflater;
	Assignment tempValue = null;

	public AssignmentAdapter(Context c, ArrayList<Assignment> d,
			Resources resLocal) {

		/********** Take passed values **********/
		context = c;
		data = d;
		res = resLocal;

		/*********** Layout inflator to call external xml layout () ***********/
		inflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
		ViewHolder holder;
		if (vi == null) {
			vi = inflater.inflate(R.layout.assignment, null);

			holder = new ViewHolder();
			holder.number = (TextView) vi.findViewById(R.id.tvNum);
			holder.description = (TextView) vi.findViewById(R.id.tvDesc);
			holder.category = (TextView) vi.findViewById(R.id.tvCat);
			holder.grade = (TextView) vi.findViewById(R.id.tvPoints);
			holder.letter = (TextView) vi.findViewById(R.id.tvLetter);

			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}
		if (data.size() > 0) {
			tempValue = (Assignment) data.get(position);
			if (tempValue.number != null){
				holder.number.setText(tempValue.number);
				holder.grade.setText(tempValue.grade + "/" + tempValue.max);
			}else{
				holder.grade.setText(tempValue.grade + "\n" + tempValue.max);
			}
			holder.description.setText(tempValue.description);
			holder.category.setText(tempValue.category);
			holder.letter.setText(tempValue.letter);
			holder.number.setText(tempValue.number);
		}
		return vi;
	}

	public static class ViewHolder {
		TextView number;
		TextView description;
		TextView category;
		TextView grade;
		TextView max;
		TextView letter;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
