package com.next.googlemapapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
	public static final String TAG = "GoogleMapAPI";
	private String[] activityNames = {
			"MapBasic", "MapNavigation", "MapCameraEvents", "Marker", "Shape",
			"MapTypeAndStyle", "MapSettings", "Snapshot", "MyLocation", "LocationSource",
			"Basic", "Navigation", "Events", "Settings", "AndMap"
	};
	private Spinner sActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sActivity = findViewById(R.id.sActivity);
		Button bStart = findViewById(R.id.bStart);
		bStart.setOnClickListener(this);
	}

	@Override
	public void onClick(View view)
	{
		int activityId = (int) sActivity.getSelectedItemId();

		String activityToStart;
		if (activityId < 10)
			activityToStart = "com.next.googlemapapi.map." + activityNames[activityId] + "Activity";
		else
			activityToStart = "com.next.googlemapapi.streetview.StreetView" + activityNames[activityId] + "Activity";
		try
		{
			Class<?> c = Class.forName(activityToStart);
			Intent intent = new Intent(this, c);
			startActivity(intent);
		} catch (ClassNotFoundException ignored)
		{
			Toast.makeText(MainActivity.this, "Class Not Found, Wrong class or package name.", Toast.LENGTH_SHORT).show();
		}
	}
}