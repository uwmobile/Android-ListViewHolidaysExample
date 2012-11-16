package com.example.holidaylist;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uwapi.UWAPIWrapper;
import uwapi.UWAPIWrapper.UWAPIWrapperListener;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements UWAPIWrapperListener {
	private static final String API_KEY = "YOUR API KEY HERE";

	EditText editText1;
	Button button1;
	ListView listView1;
	TextView textView1;
	
	UWAPIWrapper uwapi;
	
	HolidayListAdapter adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        editText1 = (EditText) findViewById(R.id.editText1);
        button1 = (Button) findViewById(R.id.button1);
        listView1 = (ListView) findViewById(R.id.listView1);
        textView1 = (TextView) findViewById(R.id.textView1);

        uwapi = new UWAPIWrapper(API_KEY, this);

		adapter = new HolidayListAdapter(this);
        listView1.setAdapter(adapter);
        
        listView1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					textView1.setText(adapter.getItem(position).getString(
							"Year"));
				} catch (JSONException e) {
					textView1.setText("No Description");
				}
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void buttonClicked(View view) {
    	uwapi.callService("Holidays");
    }

	@Override
	public void onUWAPIRequestComplete(JSONObject object, boolean successful) {
		if (successful) {
			try {
				JSONObject response = object.getJSONObject("response");
				JSONObject data = response.getJSONObject("data");
				JSONArray result = data.getJSONArray("result");
				
				ArrayList<JSONObject> yearList = new ArrayList<JSONObject>();
				for (int i = 0; i < result.length(); i++) {
					JSONObject yearObject = result.getJSONObject(i);
					yearList.add(yearObject);
				}
				
				adapter.setYears(yearList);
				adapter.notifyDataSetChanged();
			} catch (JSONException e) {
				textView1.setText("JSON Parsing Failed!");
			}
			
		} else {
			textView1.setText("Request Failed!!");
		}
	}
}
