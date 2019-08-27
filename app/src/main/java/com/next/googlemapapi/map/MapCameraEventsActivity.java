package com.next.googlemapapi.map;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.next.googlemapapi.R;

public class MapCameraEventsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback,
		GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnCameraIdleListener,
		GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraMoveCanceledListener
{
	public static String TAG = "MapActivity";
	private GoogleMap googleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_camera_events);

		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}

	@Override
	public void onMapReady(GoogleMap map)
	{
		googleMap = map;
		googleMap.setOnMapLoadedCallback(this);
		googleMap.setOnMapClickListener(this);
		googleMap.setOnMapLongClickListener(this);

		googleMap.setOnCameraIdleListener(this);
		googleMap.setOnCameraMoveStartedListener(this);
		googleMap.setOnCameraMoveListener(this);
		googleMap.setOnCameraMoveCanceledListener(this);
	}

	@Override
	public void onMapLoaded()
	{
		Log.d(TAG, "onMapLoaded() called");
	}

	@Override
	public void onMapClick(LatLng latLng)
	{
		googleMap.stopAnimation();
		Log.d(TAG, "onMapClick() called with: latLng = [" + latLng.latitude + "," + latLng.longitude + "]");
	}

	@Override
	public void onMapLongClick(LatLng latLng)
	{
		LatLng sydney = new LatLng(-33.87365, 151.20689);
		googleMap.animateCamera(CameraUpdateFactory.newLatLng(sydney));
		Log.d(TAG, "onMapLongClick() called with: latLng = [" + latLng.latitude + "," + latLng.longitude + "]");
	}

	@Override
	public void onCameraIdle()
	{
		Log.d(TAG, "onCameraIdle() called");
	}

	@Override
	public void onCameraMoveStarted(int i)
	{
		Log.d(TAG, "onCameraMoveStarted() called with: i = [" + i + "]");
	}

	@Override
	public void onCameraMove()
	{
		Log.d(TAG, "onCameraMove() called");
	}

	@Override
	public void onCameraMoveCanceled()
	{
		Log.d(TAG, "onCameraMoveCanceled() called");
	}
}