package com.example.json_map;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {

	public static final String TAG = "MainActivity";

	private RequestQueue requestQueue;
	private Adapter adapter;
	private ListView dataList;

	List<EarthquakeLocation> listItems = new ArrayList<EarthquakeLocation>();
	double lati;
	double longi;
	String name;
	String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// call to volleyRequest() method
		volleyRequest();

		// call to fillAdpater() method
		fillAdapter();

		// call to loadParsedEarthquakeData()
		loadParsedEarthquakeData();

		// call to setOnItemClickListener() method
		setOnItemClickListener();

	}

	// volleyRequest() method definition

	public void volleyRequest() {
		requestQueue = Volley.newRequestQueue(getApplicationContext());
	}

	// fillAdapter() method definition

	public void fillAdapter() {
		adapter = new Adapter(this, requestQueue);
		dataList = (ListView) findViewById(R.id.listView1);
		dataList.setAdapter(adapter);

	}

	// loadParsedEarthquakeData() method for loading the parsed data from JSON
	public void loadParsedEarthquakeData() {
		try {
			executeRequest();
		} catch (Exception e) {
			Log.d(TAG, "exceptio: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// setOnItemClickListener() method definition for handling the
	// setOnItemClickListener

	public void setOnItemClickListener() {
		dataList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long arg) {

				if (listItems == null) {
					return;
				}

				// call to getEarthquakeLocation() method
				getEarthquakeLocation(position);

				// New Intent for map activity
				Intent intent = new Intent(MainActivity.this, MapActivity.class);

				// call to passEarthquakeLocationDetial for passing extras to
				// the map activity
				passEarthquakeLocationDetial(intent);

				// starting the map activity
				startActivity(intent);

			}

		});
	}

	// getEarthquakeLocation() method definition for getting the details of the
	// location which is clicked
	public void getEarthquakeLocation(int position) {
		EarthquakeLocation location = listItems.get(position);
		lati = location.getLatitude();
		longi = location.getLongitude();
		name = location.getName();
		url = location.getUrl();

	}

	// passEarthquakeLocationDetial() method definition for passing extras to
	// the map activity
	public void passEarthquakeLocationDetial(Intent intent) {
		intent.putExtra(MapActivity.EXTRA_LAT, lati);
		intent.putExtra(MapActivity.EXTRA_LON, longi);
		intent.putExtra(MapActivity.EXTRA_NAME, name);
		intent.putExtra(MapActivity.EXTRA_URL, url);

	}

	// executeRequest() method from volley library

	private void executeRequest() throws UnsupportedEncodingException {

		String earhquakeJsonUrl = "http://earthquake.usgs.gov/earthquakes/feed/v0.1/summary/1.0_hour.geojson";

		JsonObjectRequest myReq = new JsonObjectRequest(Method.GET,
				earhquakeJsonUrl, null, createMyReqSuccessListener(),
				(ErrorListener) createMyReqErrorListener());
		requestQueue.add(myReq);
	}

	// Response Listener
	private Response.Listener<JSONObject> createMyReqSuccessListener() {
		return new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {

				listItems.clear();
				Log.d(TAG, "response:\n " + response.toString());

				// parsing the data from JSON
				parsingJson(response);

			}

		};
	}

	// Error Listener
	private Response.ErrorListener createMyReqErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {

				error.getMessage();

			}
		};
	}

	/*
	 * parsingJson() method definition which takes JSON array response object
	 * and parse it to crate list of the objects(EarthquakeLocation Class)
	 */

	public void parsingJson(JSONObject response) {
		try {

			JSONArray dataArray = response.getJSONArray("features");
			for (int t = 0; t < dataArray.length(); t++) {
				EarthquakeLocation data = new EarthquakeLocation();

				JSONObject feature = dataArray.getJSONObject(t);
				String name = feature.getJSONObject("properties").getString(
						"place");

				String locationUrl = feature.getJSONObject("properties")
						.getString("url");

				JSONArray jsonArrayCoordinates = feature.getJSONObject(
						"geometry").getJSONArray("coordinates");

				double lati = (Double) jsonArrayCoordinates.get(1);
				double longi = (Double) jsonArrayCoordinates.get(0);

				data.setName(name);
				data.setLatitude(lati);
				data.setLongitude(longi);
				data.setUrl(locationUrl);

				listItems.add(data);

			}

			adapter.setData(listItems);
			adapter.notifyDataSetChanged();

		} catch (JSONException e) {

			e.printStackTrace();
		}

	}

}
