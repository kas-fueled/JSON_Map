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
public class Adapter extends BaseAdapter{
	
	public static class ViewHolder {
		TextView name;
		TextView latitude;
		TextView longitude;
	}
	private List<Details> data =  new ArrayList<Details>();
	private LayoutInflater inflater;
	RequestQueue requestQueue;
	
	public Adapter(Context context, RequestQueue requestQueue) {
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.requestQueue = requestQueue;
	}
	
	public void setData(List<Details> data) {
		this.data = data;
	}
	
	private List<Details> getData() {
		if(null == data) {
			data = new ArrayList<Details>();			
		}
		return data;
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
		
		if(null == convertView) {
			convertView = inflater.inflate(R.layout.row, parent,false);
			viewHolder = new ViewHolder();
			
			viewHolder.name = (TextView) convertView.findViewById(R.id.name);
			//viewHolder.latitude = (TextView) convertView.findViewById(R.id.latitude);
			//viewHolder.longitude = (TextView) convertView.findViewById(R.id.longitude);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Details details = (Details) getItem(position);
		viewHolder.name.setText(details.getName() + "");
		//viewHolder.latitude.setText(details.getLatitude() + "");
		//viewHolder.longitude.setText(details.getLongitude() + "");
		
		return convertView;
		
		
	}

}
