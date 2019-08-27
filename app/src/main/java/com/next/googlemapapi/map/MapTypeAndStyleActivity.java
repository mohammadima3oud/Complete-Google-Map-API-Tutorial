package com.next.googlemapapi.map;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.next.googlemapapi.R;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NONE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_TERRAIN;

public class MapTypeAndStyleActivity extends FragmentActivity implements OnMapReadyCallback,
		AdapterView.OnItemSelectedListener
{
	private GoogleMap googleMap;
	private static final int[] mapTypes = {MAP_TYPE_NORMAL, MAP_TYPE_SATELLITE, MAP_TYPE_HYBRID, MAP_TYPE_TERRAIN, MAP_TYPE_NONE};
	private static final Integer[] mapStyles = {R.raw.mapstyle_grayscale, R.raw.mapstyle_night, R.raw.mapstyle_retro, R.raw.mapstyle_transit, null};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_type_and_style);

		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);

		Spinner sMapType = findViewById(R.id.sMapType);
		Spinner sMapStyle = findViewById(R.id.sMapStyle);

		sMapType.setOnItemSelectedListener(this);
		sMapStyle.setOnItemSelectedListener(this);
	}

	@Override
	public void onMapReady(GoogleMap map)
	{
		googleMap = map;
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
	{
		if (adapterView.getId() == R.id.sMapType)
		{
			googleMap.setMapType(mapTypes[i]);
		} else if (adapterView.getId() == R.id.sMapStyle)
		{
			try
			{
				googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, mapStyles[i]));
			} catch (Exception exc)
			{
				googleMap.setMapStyle(null);
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView)
	{

	}
}