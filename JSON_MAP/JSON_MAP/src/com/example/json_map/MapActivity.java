package com.example.json_map;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;

public class MapActivity extends Activity {
	public final static String EXTRA_LAT = "lat";
	public static final String EXTRA_LON = "lon";
	public static final String EXTRA_NAME = "name";
	public static final String EXTRA_URL = "url";

	public final static String INSTANCE_LAT = "instance_lat";
	public final static String INSTANCE_LON = "instance_lon";

	private GoogleMap earthquakesMap;

	private double lat;
	private double lon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_map);

		if (savedInstanceState == null) {
			lat = getIntent().getDoubleExtra(EXTRA_LAT, 0.0);
			lon = getIntent().getDoubleExtra(EXTRA_LON, 0.0);
		} else {
			lat = savedInstanceState.getDouble(INSTANCE_LAT);
			lon = savedInstanceState.getDouble(INSTANCE_LON);
		}

		LatLng city = new LatLng(lat, lon);

		// creatingMap call
		creatingMap();

		// movingMapToCity
		movingMapToCity(city);

		// Add marker to the location of earthquake
		addMarker(city);

		// redirectionToEarthQuakeLocation() call
		redirectionToEarthQuakeLocation();

	}

	// Creating the map
	public void creatingMap() {
		MapFragment mapFragment = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.map));
		earthquakesMap = mapFragment.getMap();

	}

	// moving map directly to the location
	public void movingMapToCity(LatLng city) {
		earthquakesMap.moveCamera(CameraUpdateFactory.newLatLngZoom(city, 2));

	}

	// Adding marker on the map with the custom icon, title and description of
	// earthquake
	public void addMarker(LatLng city) {

		String name = getIntent().getStringExtra(EXTRA_NAME);

		earthquakesMap.addMarker(new MarkerOptions()
				.position(city)
				.title(name)
				.snippet("EarthQuake sensitive Zone")
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_earthquake1)));

	}

	/*
	 * open the earthquake location in browser and Handling the
	 * onInfoWindowClick
	 */
	public void redirectionToEarthQuakeLocation() {
		earthquakesMap
				.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
					public void onInfoWindowClick(Marker marker) {

						String url = getIntent().getStringExtra(EXTRA_URL);
						Intent intent = new Intent(
								android.content.Intent.ACTION_VIEW, Uri
										.parse(url));

						startActivity(intent);

					}
				});

	}

	// saving the instance latitude and longitude even after activity is
	// recreated
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putDouble(INSTANCE_LAT, lat);
		outState.putDouble(INSTANCE_LON, lon);
	}

}
