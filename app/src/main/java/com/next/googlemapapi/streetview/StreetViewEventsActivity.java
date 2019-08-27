package com.next.googlemapapi.streetview;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;
import com.next.googlemapapi.MainActivity;
import com.next.googlemapapi.R;

public class StreetViewEventsActivity extends FragmentActivity implements OnStreetViewPanoramaReadyCallback, StreetViewPanorama.OnStreetViewPanoramaChangeListener, StreetViewPanorama.OnStreetViewPanoramaCameraChangeListener, StreetViewPanorama.OnStreetViewPanoramaClickListener, StreetViewPanorama.OnStreetViewPanoramaLongClickListener
{
	private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
	private StreetViewPanorama streetViewPanorama;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_street_view_events);

		SupportStreetViewPanoramaFragment fragment = (SupportStreetViewPanoramaFragment) getSupportFragmentManager().findFragmentById(R.id.street_view);
		fragment.getStreetViewPanoramaAsync(this);
	}

	@Override
	public void onStreetViewPanoramaReady(StreetViewPanorama streetView)
	{
		streetViewPanorama = streetView;
		streetViewPanorama.setPosition(SYDNEY);

		streetViewPanorama.setOnStreetViewPanoramaChangeListener(this);
		streetViewPanorama.setOnStreetViewPanoramaCameraChangeListener(this);
		streetViewPanorama.setOnStreetViewPanoramaClickListener(this);
		streetViewPanorama.setOnStreetViewPanoramaLongClickListener(this);
	}

	@Override
	public void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation)
	{
		Log.d(MainActivity.TAG, "onStreetViewPanoramaChange() called with: streetViewPanoramaLocation = [" + streetViewPanoramaLocation + "]");
	}

	@Override
	public void onStreetViewPanoramaCameraChange(StreetViewPanoramaCamera streetViewPanoramaCamera)
	{
		Log.d(MainActivity.TAG, "onStreetViewPanoramaCameraChange() called with: streetViewPanoramaCamera = [" + streetViewPanoramaCamera + "]");
	}

	@Override
	public void onStreetViewPanoramaClick(StreetViewPanoramaOrientation streetViewPanoramaOrientation)
	{
		Log.d(MainActivity.TAG, "onStreetViewPanoramaClick() called with: streetViewPanoramaOrientation = [" + streetViewPanoramaOrientation + "]");
	}

	@Override
	public void onStreetViewPanoramaLongClick(StreetViewPanoramaOrientation streetViewPanoramaOrientation)
	{
		Log.d(MainActivity.TAG, "onStreetViewPanoramaLongClick() called with: streetViewPanoramaOrientation = [" + streetViewPanoramaOrientation + "]");
	}
}