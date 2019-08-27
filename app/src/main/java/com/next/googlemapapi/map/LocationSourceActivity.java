package com.next.googlemapapi.map;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.next.googlemapapi.PermissionUtils;
import com.next.googlemapapi.R;

public class LocationSourceActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, LocationSource

{
	private GoogleMap googleMap;
	private Location location;
	private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_source);

		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}

	@Override
	public void onMapReady(GoogleMap map)
	{
		googleMap = map;

		googleMap.setOnMapLongClickListener(this);
		if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
				ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
		{
			enableMyLocation();
		} else
		{
			map.setMyLocationEnabled(true);
		}
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

	@Override
	public void onMapLongClick(LatLng latLng)
	{
		location=new Location("LongPressLocationProvider");

		location.setLatitude(latLng.latitude);
		location.setLongitude(latLng.longitude);
		location.setAccuracy(100);
		googleMap.setLocationSource(this);
	}

	@Override
	public void activate(OnLocationChangedListener onLocationChangedListener)
	{
		onLocationChangedListener.onLocationChanged(location);
	}

	@Override
	public void deactivate()
	{

	}
}