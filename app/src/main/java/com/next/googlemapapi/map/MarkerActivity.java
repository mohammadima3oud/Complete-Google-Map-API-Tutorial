package com.next.googlemapapi.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.next.googlemapapi.CustomInfoWindowAdapter;
import com.next.googlemapapi.MainActivity;
import com.next.googlemapapi.R;

public class MarkerActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener
		, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerDragListener,
		GoogleMap.OnInfoWindowCloseListener, GoogleMap.OnInfoWindowLongClickListener
{
	private GoogleMap googleMap;
	private static final LatLng SYDNEY = new LatLng(-34, 151);
	private static final LatLng BRISBANE = new LatLng(-27.47093, 153.0235);
	private static final LatLng ADELAIDE = new LatLng(-34.92873, 138.59995);

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_marker);

		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);

		initialize();
	}

	private void initialize()
	{
		Button bSimpleMarker = findViewById(R.id.bSimpleMarker);
		Button bMarkerOption = findViewById(R.id.bMarkerOption);
		Button bCustomMarker = findViewById(R.id.bCustomMarker);
		bSimpleMarker.setOnClickListener(this);
		bMarkerOption.setOnClickListener(this);
		bCustomMarker.setOnClickListener(this);
	}

	@Override
	public void onMapReady(GoogleMap map)
	{
		googleMap = map;
		googleMap.setOnMarkerClickListener(this);
		googleMap.setOnInfoWindowClickListener(this);
		googleMap.setOnMarkerDragListener(this);
		googleMap.setOnInfoWindowCloseListener(this);
		googleMap.setOnInfoWindowLongClickListener(this);
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.bSimpleMarker:
				googleMap.clear();
				googleMap.addMarker(new MarkerOptions().position(SYDNEY).title("Sydney"));
				break;
			case R.id.bMarkerOption:
				googleMap.clear();
				CustomInfoWindowAdapter.useDefaultInfoWindow=true;
				addMarkersToMap();
				googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MarkerActivity.this));
				break;
			case R.id.bCustomMarker:
				googleMap.clear();
				CustomInfoWindowAdapter.useDefaultInfoWindow=false;
				addMarkersToMap();
				googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MarkerActivity.this));
				break;
		}
	}

	private void addMarkersToMap()
	{
		MarkerOptions options =new MarkerOptions();
		options.position(BRISBANE);
		options.title("brisbane");
		options.snippet("Population: 2,544,634");
		options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
		options.draggable(true);
		CustomInfoWindowAdapter.brisbane=googleMap.addMarker(options);


		MarkerOptions options2 =new MarkerOptions();
		options2.position(ADELAIDE);
		options2.title("adelaide");
		options2.snippet("Population: 3,543,222");
		Drawable drawable=ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_person_pin_circle_black_24dp);
		options2.icon(convertDrawableToBitmap(drawable));
		options2.draggable(true);
		CustomInfoWindowAdapter.adelaide=googleMap.addMarker(options2);
	}

	private BitmapDescriptor convertDrawableToBitmap(Drawable drawable)
	{
		Canvas canvas = new Canvas();
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
		canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return BitmapDescriptorFactory.fromBitmap(bitmap);
	}

	@Override
	public boolean onMarkerClick(Marker marker)
	{
		Log.d(MainActivity.TAG, "onMarkerClick() called with: marker = [" + marker.getTitle() + "]");
		return false;
	}

	@Override
	public void onInfoWindowClick(Marker marker)
	{
		Log.d(MainActivity.TAG, "onInfoWindowClick() called with: marker = [" + marker.getTitle() + "]");
	}

	@Override
	public void onMarkerDragStart(Marker marker)
	{
		Log.d(MainActivity.TAG, "onMarkerDragStart() called with: marker = [" + marker.getTitle() + "]");
	}

	@Override
	public void onMarkerDrag(Marker marker)
	{
		Log.d(MainActivity.TAG, "onMarkerDrag() called with: marker = [" + marker.getTitle() + "]");
	}

	@Override
	public void onMarkerDragEnd(Marker marker)
	{
		Log.d(MainActivity.TAG, "onMarkerDragEnd() called with: marker = [" + marker.getTitle() + "]");
	}

	@Override
	public void onInfoWindowClose(Marker marker)
	{
		Log.d(MainActivity.TAG, "onInfoWindowClose() called with: marker = [" + marker.getTitle() + "]");
	}

	@Override
	public void onInfoWindowLongClick(Marker marker)
	{
		Log.d(MainActivity.TAG, "onInfoWindowLongClick() called with: marker = [" + marker.getTitle() + "]");
	}
}