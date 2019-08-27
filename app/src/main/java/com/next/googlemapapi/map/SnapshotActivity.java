package com.next.googlemapapi.map;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.next.googlemapapi.R;

public class SnapshotActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener
{
	private GoogleMap googleMap;
	private ImageView ivImageHolder;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_snapshot);

		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);

		initialize();
	}

	private void initialize()
	{
		Button bTakeSnapshot = findViewById(R.id.bTakeSnapshot);
		Button bClear = findViewById(R.id.bClear);
		ivImageHolder = findViewById(R.id.ivImageHolder);
		bTakeSnapshot.setOnClickListener(this);
		bClear.setOnClickListener(this);
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
			case R.id.bTakeSnapshot:
				googleMap.snapshot(new GoogleMap.SnapshotReadyCallback()
				{
					@Override
					public void onSnapshotReady(Bitmap bitmap)
					{
						ivImageHolder.setImageBitmap(bitmap);
					}
				});
				break;
			case R.id.bClear:
				ivImageHolder.setImageDrawable(null);
				break;
		}
	}
}