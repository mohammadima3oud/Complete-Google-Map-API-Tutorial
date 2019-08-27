package com.next.googlemapapi.map;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.next.googlemapapi.R;

public class MapBasicActivity extends FragmentActivity implements OnMapReadyCallback
{
	private GoogleMap googleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_basic);

		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}

	@Override
	public void onMapReady(GoogleMap map)
	{
		googleMap = map;

		// Add a marker in Sydney and move the camera
		LatLng sydney = new LatLng(-34, 151);
		googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
	}
}