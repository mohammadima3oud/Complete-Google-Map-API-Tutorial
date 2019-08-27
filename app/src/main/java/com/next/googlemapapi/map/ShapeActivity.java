package com.next.googlemapapi.map;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.next.googlemapapi.R;

import java.util.Arrays;
import java.util.List;

public class ShapeActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnCircleClickListener, GoogleMap.OnPolygonClickListener, GoogleMap.OnPolylineClickListener, GoogleMap.OnGroundOverlayClickListener
{
	private GoogleMap googleMap;
	private static final int PATTERN_DASH_LENGTH_PX = 100;
	private static final int PATTERN_GAP_LENGTH_PX = 200;
	private static final Dot DOT = new Dot();
	private static final Dash DASH = new Dash(PATTERN_DASH_LENGTH_PX);
	private static final Gap GAP = new Gap(PATTERN_GAP_LENGTH_PX);
	private static final List<PatternItem> PATTERN_DOTTED = Arrays.asList(DOT, GAP);
	private static final List<PatternItem> PATTERN_DASHED = Arrays.asList(DASH, GAP);
	private static final List<PatternItem> PATTERN_MIXED = Arrays.asList(DOT, GAP, DOT, DASH, GAP);

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shape);

		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);

		initialize();
	}

	private void initialize()
	{
		Button bCircle = findViewById(R.id.bCircle);
		Button bPolygone = findViewById(R.id.bPolygone);
		Button bPolyline = findViewById(R.id.bPolyline);
		Button bGroundOverlays = findViewById(R.id.bGroundOverlays);
		bCircle.setOnClickListener(this);
		bPolygone.setOnClickListener(this);
		bPolyline.setOnClickListener(this);
		bGroundOverlays.setOnClickListener(this);
	}

	@Override
	public void onMapReady(GoogleMap map)
	{
		googleMap = map;
		googleMap.setOnCircleClickListener(this);
		googleMap.setOnPolygonClickListener(this);
		googleMap.setOnPolylineClickListener(this);
		googleMap.setOnGroundOverlayClickListener(this);
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.bCircle:
				addCircle();
				break;
			case R.id.bPolygone:
				addPolygon();
				break;
			case R.id.bPolyline:
				addPolyline();
				break;
			case R.id.bGroundOverlays:
				addGroundOverlays();
				break;
		}
	}

	private void addCircle()
	{
		googleMap.clear();
		CircleOptions circleOptions = new CircleOptions();
		circleOptions.center(new LatLng(37, 67));
		circleOptions.radius(100000);
		circleOptions.strokeColor(Color.RED);
		circleOptions.strokeWidth(10f);
		circleOptions.strokePattern(PATTERN_MIXED);
		circleOptions.clickable(true);
		circleOptions.fillColor(Color.GREEN);

		googleMap.addCircle(circleOptions).setTag(new CustomTag("circle"));
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37, 67)));
	}

	private void addPolygon()
	{
		googleMap.clear();
		PolygonOptions polygonOptions = new PolygonOptions();
		polygonOptions.add(new LatLng(0, 0));
		polygonOptions.add(new LatLng(-3, 2.5));
		polygonOptions.add(new LatLng(0, 5));
		polygonOptions.add(new LatLng(3, 5));
		polygonOptions.add(new LatLng(3, 0));
		polygonOptions.add(new LatLng(0, 0));
		polygonOptions.clickable(true);
		polygonOptions.fillColor(Color.BLUE);

		googleMap.addPolygon(polygonOptions).setTag(new CustomTag("polygon"));
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(0, 0)));
	}

	private void addPolyline()
	{
		googleMap.clear();
		PolylineOptions polylineOptions = new PolylineOptions();
		polylineOptions.add(new LatLng(37.35, -122.0));
		polylineOptions.add(new LatLng(37.45, -122.0));
		polylineOptions.add(new LatLng(37.45, -122.2));
		polylineOptions.add(new LatLng(37.35, -122.2));
		polylineOptions.clickable(true);
		polylineOptions.color(Color.CYAN);

		googleMap.addPolyline(polylineOptions).setTag(new CustomTag("polyline"));
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.35, -122.0)));
	}

	private void addGroundOverlays()
	{
		googleMap.clear();
		GroundOverlayOptions groundOverlayOptions = new GroundOverlayOptions();
		groundOverlayOptions.image(BitmapDescriptorFactory.fromResource(R.drawable.city)).anchor(0, 1);
		groundOverlayOptions.position(new LatLng(40, -74), 8600f, 6500f);
		groundOverlayOptions.bearing(0);
		groundOverlayOptions.clickable(true);

		googleMap.addGroundOverlay(groundOverlayOptions).setTag(new CustomTag("ground overlay"));
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(40, -74)));
	}

	@Override
	public void onCircleClick(Circle circle)
	{
		onClick((CustomTag) circle.getTag());
	}

	@Override
	public void onPolygonClick(Polygon polygon)
	{
		onClick((CustomTag) polygon.getTag());
	}

	@Override
	public void onPolylineClick(Polyline polyline)
	{
		onClick((CustomTag) polyline.getTag());
	}

	@Override
	public void onGroundOverlayClick(GroundOverlay groundOverlay)
	{
		onClick((CustomTag) groundOverlay.getTag());
	}

	private void onClick(CustomTag tag)
	{
		tag.incrementClickCount();
		Toast.makeText(ShapeActivity.this, tag.toString(), Toast.LENGTH_SHORT).show();
	}

	private static class CustomTag
	{
		private final String description;
		private int clickCount;

		public CustomTag(String description)
		{
			this.description = description;
			this.clickCount = 0;
		}

		void incrementClickCount()
		{
			clickCount++;
		}

		@Override
		public String toString()
		{
			return "The " + description + " has been clicked " + clickCount + " Times.";
		}
	}
}