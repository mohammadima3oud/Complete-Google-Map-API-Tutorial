package com.next.googlemapapi.map;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.next.googlemapapi.PermissionUtils;
import com.next.googlemapapi.R;

public class MapSettingsActivity extends FragmentActivity implements OnMapReadyCallback
{
	private GoogleMap googleMap;
	private LatLng EIFFEL_TOWER = new LatLng(48.8582667, 2.2944489);
	private LatLng MADISON_SQUARE_GARDEN = new LatLng(40.7503388, -73.9934577);
	private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_settings);

		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}

	@Override
	public void onMapReady(GoogleMap map)
	{
		googleMap = map;

		UiSettings uiSettings = googleMap.getUiSettings();

		enableMyLocation();
		googleMap.setIndoorEnabled(true);
		googleMap.setBuildingsEnabled(true);
		googleMap.setTrafficEnabled(true);

		uiSettings.setCompassEnabled(true);
		uiSettings.setMyLocationButtonEnabled(true);
		uiSettings.setZoomControlsEnabled(true);
		uiSettings.setMapToolbarEnabled(true);
		uiSettings.setIndoorLevelPickerEnabled(true);

		uiSettings.setRotateGesturesEnabled(true);
		uiSettings.setScrollGesturesEnabled(true);
		uiSettings.setTiltGesturesEnabled(true);
		uiSettings.setZoomGesturesEnabled(true);
		uiSettings.setAllGesturesEnabled(true);

		googleMap.addMarker(new MarkerOptions().position(MADISON_SQUARE_GARDEN));
		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MADISON_SQUARE_GARDEN, 17));
	}

	// need for android 6 and above
	private void enableMyLocation()
	{
		if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
		{
			PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE, android.Manifest.permission.ACCESS_FINE_LOCATION, true);
		} else if (googleMap != null)
		{
			googleMap.setMyLocationEnabled(true);
		}
	}

	// need for android 6 and above
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		if (requestCode != LOCATION_PERMISSION_REQUEST_CODE)
		{
			return;
		}
		if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION))
		{
			enableMyLocation();
		}
	}
}