package com.example.json_map;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.RequestQueue;

public class Adapter extends BaseAdapter {

	public static class ViewHolder {
		TextView name;
	}

	private List<EarthquakeLocation> earthquakeLocatiosList = new ArrayList<EarthquakeLocation>();
	private LayoutInflater inflater;
	RequestQueue requestQueue;

	public Adapter(Context context, RequestQueue requestQueue) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.requestQueue = requestQueue;
	}

	public void setData(List<EarthquakeLocation> earthquakeLocatiosList) {
		this.earthquakeLocatiosList = earthquakeLocatiosList;
	}

	private List<EarthquakeLocation> getData() {
		if (null == earthquakeLocatiosList) {
			earthquakeLocatiosList = new ArrayList<EarthquakeLocation>();
		}
		return earthquakeLocatiosList;
	}

	@Override
	public int getCount() {
		return getData().size();
	}

	@Override
	public Object getItem(int position) {
		return getData().get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (null == convertView) {
			convertView = inflater.inflate(R.layout.row, parent, false);
			viewHolder = new ViewHolder();

			viewHolder.name = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		EarthquakeLocation details = (EarthquakeLocation) getItem(position);
		viewHolder.name.setText(details.getName() + "");

		return convertView;

	}

}
