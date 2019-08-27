package com.next.googlemapapi.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.next.googlemapapi.MainActivity;
import com.next.googlemapapi.PermissionUtils;
import com.next.googlemapapi.R;

public class MyLocationActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMyLocationClickListener, GoogleMap.OnMyLocationButtonClickListener
{
	private GoogleMap googleMap;
	private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_location);

		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
		Button bGoToMyLocation = findViewById(R.id.bGoToMyLocation);
		bGoToMyLocation.setOnClickListener(this);
	}

	@Override
	public void onMapReady(GoogleMap map)
	{
		googleMap = map;
		enableMyLocation();
		googleMap.setOnMyLocationClickListener(this);
		googleMap.setOnMyLocationButtonClickListener(this);
	}

	@Override
	public void onClick(View view)
	{
		goToMyLocation();
	}

	private void goToMyLocation()
	{
		if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
				ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
		{
			enableMyLocation();
		} else
		{
			googleMap.setMyLocationEnabled(true);
			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
			if (location != null)
			{
				googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
			}
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
	public void onMyLocationClick(@NonNull Location location)
	{
		Log.d(MainActivity.TAG, "onMyLocationClick() called with: location = [" + location + "]");
	}

	@Override
	public boolean onMyLocationButtonClick()
	{
		Log.d(MainActivity.TAG, "onMyLocationButtonClick() called");
		goToMyLocation();
		return true;
	}
}