package com.next.googlemapapi.streetview;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.next.googlemapapi.R;

public class StreetViewBasicActivity extends FragmentActivity implements OnStreetViewPanoramaReadyCallback
{
	private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
	private StreetViewPanorama streetViewPanorama;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_street_view_basic);

		SupportStreetViewPanoramaFragment streetViewPanoramaFragment = (SupportStreetViewPanoramaFragment) getSupportFragmentManager().findFragmentById(R.id.street_view);
		streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
	}

	@Override
	public void onStreetViewPanoramaReady(StreetViewPanorama streetView)
	{
		streetViewPanorama = streetView;
		streetView.setPosition(SYDNEY);
	}
}