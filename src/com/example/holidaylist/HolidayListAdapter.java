package com.example.holidaylist;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HolidayListAdapter extends BaseAdapter {
	ArrayList<JSONObject> years;
	Context context;
	
	public HolidayListAdapter(Context context) {
		this.context = context;
		years = new ArrayList<JSONObject>();
	}
	
	public void setYears(ArrayList<JSONObject> years) {
		this.years = years;
	}
	
	@Override
	public int getCount() {
		return years.size();
	}

	@Override
	public JSONObject getItem(int arg0) {
		return years.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int index, View oldView, ViewGroup parent) {
		TextView textView = new TextView(context);
		try {
			textView.setText(years.get(index).getString("Year"));
		} catch (Exception e) {
			textView.setText("JSON error");
		}
		return textView;
	}

}
