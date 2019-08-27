package com.next.googlemapapi;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter
{
	private final View window;
	private final View content;

	public static Marker brisbane;
	public static Marker adelaide;
	public static boolean useDefaultInfoWindow;

	public CustomInfoWindowAdapter(Activity activity)
	{
		window = activity.getLayoutInflater().inflate(R.layout.custom_info_window, null);
		content = activity.getLayoutInflater().inflate(R.layout.custom_info_contents, null);
	}

	@Override
	public View getInfoWindow(Marker marker)
	{
		if (useDefaultInfoWindow)
		{
			// This means that getInfoContents will be called.
			return null;
		}
		render(marker, window);
		return window;
	}

	@Override
	public View getInfoContents(Marker marker)
	{
		if (useDefaultInfoWindow)
		{
			// This means that the default info contents will be used.
			return null;
		}
		render(marker, content);
		return content;
	}

	private void render(Marker marker, View view)
	{
		int badge;
		if (marker.equals(brisbane))
		{
			badge = R.drawable.brisbane;
		} else if (marker.equals(adelaide))
		{
			badge = R.drawable.adelaide;
		} else
		{
			// Passing 0 to setImageResource will clear the image view.
			badge = 0;
		}
		((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);

		String title = marker.getTitle();
		TextView tvTitle = view.findViewById(R.id.tvTitle);
		tvTitle.setText(title);

		String snippet = marker.getSnippet();
		TextView tvSnippet = view.findViewById(R.id.tvSnippet);
		tvSnippet.setText(snippet);
	}
}