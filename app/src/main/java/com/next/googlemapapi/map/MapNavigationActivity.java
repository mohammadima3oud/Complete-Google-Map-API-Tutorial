package com.next.googlemapapi.map;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.next.googlemapapi.R;

public class MapNavigationActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener
{
	private static final float SCROLL_BY_PX = 100;
	private GoogleMap googleMap;
	public static final CameraPosition BONDI = new CameraPosition.Builder()
			.target(new LatLng(-33.891614, 151.276417)).zoom(15.5f).bearing(300).tilt(25).build();

	public static final CameraPosition SYDNEY = new CameraPosition.Builder()
			.target(new LatLng(-33.87365, 151.20689)).zoom(15.5f).bearing(0).tilt(25).build();

	private static final CameraPosition ADELAIDE = new CameraPosition.Builder()
			.target(new LatLng(-34.92873, 138.59995)).zoom(12.0f).bearing(0).tilt(0).build();

	private static final CameraPosition KAWAKAMI = new CameraPosition.Builder()
			.target(new LatLng(35.92873, 138.59995)).zoom(12.0f).bearing(0).tilt(0).build();

	private static final LatLngBounds ADELAIDE_BOUNDS =
			new LatLngBounds(new LatLng(-35.0, 138.58), new LatLng(-34.9, 138.61));

	private static final LatLngBounds KAWAKAMI_BOUNDS =
			new LatLngBounds(new LatLng(35.9, 138.58), new LatLng(36.0, 138.61));

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_navigation);

		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);

		initialize();
	}

	private void initialize()
	{
		Button bTiltMore = findViewById(R.id.bTiltMore);
		Button bTiltLess = findViewById(R.id.bTiltLess);
		Button bZoomIn = findViewById(R.id.bZoomIn);
		Button bZoomOut = findViewById(R.id.bZoomOut);
		Button bPlayAnimation = findViewById(R.id.bPlayAnimation);
		Button bStopAnimation = findViewById(R.id.bStopAnimation);
		Button bScrollUp = findViewById(R.id.bScrollUp);
		Button bScrollLeft = findViewById(R.id.bScrollLeft);
		Button bScrollDown = findViewById(R.id.bScrollDown);
		Button bScrollRight = findViewById(R.id.bScrollRight);
		Button bGoToSydney = findViewById(R.id.bGoToSydney);
		Button bGoToBondi = findViewById(R.id.bGoToBondi);
		Button bGoToAdelaide = findViewById(R.id.bGoToAdelaide);
		Button bClampToKawakami = findViewById(R.id.bClampToKawakami);
		Button bClampToAdelaide = findViewById(R.id.bClampToAdelaide);

		bTiltMore.setOnClickListener(this);
		bTiltLess.setOnClickListener(this);
		bZoomIn.setOnClickListener(this);
		bZoomOut.setOnClickListener(this);
		bPlayAnimation.setOnClickListener(this);
		bStopAnimation.setOnClickListener(this);
		bScrollUp.setOnClickListener(this);
		bScrollLeft.setOnClickListener(this);
		bScrollDown.setOnClickListener(this);
		bScrollRight.setOnClickListener(this);
		bGoToSydney.setOnClickListener(this);
		bGoToBondi.setOnClickListener(this);
		bGoToAdelaide.setOnClickListener(this);
		bClampToKawakami.setOnClickListener(this);
		bClampToAdelaide.setOnClickListener(this);
	}

	@Override
	public void onMapReady(GoogleMap map)
	{
		googleMap = map;
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.bTiltMore:
				tiltMore();
				break;
			case R.id.bTiltLess:
				tiltLess();
				break;
			case R.id.bZoomIn:
				googleMap.moveCamera(CameraUpdateFactory.zoomIn());
				break;
			case R.id.bZoomOut:
				googleMap.moveCamera(CameraUpdateFactory.zoomOut());
				break;
			case R.id.bScrollUp:
				googleMap.moveCamera(CameraUpdateFactory.scrollBy(0, -SCROLL_BY_PX));
				break;
			case R.id.bScrollLeft:
				googleMap.moveCamera(CameraUpdateFactory.scrollBy(-SCROLL_BY_PX, 0));
				break;
			case R.id.bScrollDown:
				googleMap.moveCamera(CameraUpdateFactory.scrollBy(0, SCROLL_BY_PX));
				break;
			case R.id.bScrollRight:
				googleMap.moveCamera(CameraUpdateFactory.scrollBy(SCROLL_BY_PX, 0));
				break;
			case R.id.bPlayAnimation:
				googleMap.setLatLngBoundsForCameraTarget(null);
				googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(SYDNEY));
				break;
			case R.id.bStopAnimation:
				googleMap.stopAnimation();
				break;
			case R.id.bGoToSydney:
				googleMap.setLatLngBoundsForCameraTarget(null);
				googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(SYDNEY));
				break;
			case R.id.bGoToBondi:
				googleMap.setLatLngBoundsForCameraTarget(null);
				googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(BONDI));
				break;
			case R.id.bGoToAdelaide:
				googleMap.setLatLngBoundsForCameraTarget(null);
				googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(ADELAIDE));
				break;
			case R.id.bClampToKawakami:
				googleMap.setLatLngBoundsForCameraTarget(KAWAKAMI_BOUNDS);
				googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(KAWAKAMI));
				break;
			case R.id.bClampToAdelaide:
				googleMap.setLatLngBoundsForCameraTarget(ADELAIDE_BOUNDS);
				googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(ADELAIDE));
				break;
		}
	}

	private void tiltMore()
	{
		CameraPosition currentCameraPosition = googleMap.getCameraPosition();
		float currentTilt = currentCameraPosition.tilt;
		float newTilt = currentTilt + 10;
		newTilt = (newTilt > 90) ? 90 : newTilt;
		CameraPosition cameraPosition = new CameraPosition.Builder(currentCameraPosition).tilt(newTilt).build();
		googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	}

	private void tiltLess()
	{
		CameraPosition currentCameraPosition = googleMap.getCameraPosition();
		float currentTilt = currentCameraPosition.tilt;
		float newTilt = currentTilt - 10;
		newTilt = (newTilt > 0) ? newTilt : 0;
		CameraPosition cameraPosition = new CameraPosition.Builder(currentCameraPosition).tilt(newTilt).build();
		googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	}
}