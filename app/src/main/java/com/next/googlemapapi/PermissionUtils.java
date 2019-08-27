package com.next.googlemapapi;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

public abstract class PermissionUtils
{
	public static void requestPermission(FragmentActivity activity, int requestId, String permission, boolean finishActivity)
	{
		if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission))
		{
			// Display a dialog with rationale.
			PermissionUtils.RationaleDialog.newInstance(requestId, finishActivity).show(activity.getSupportFragmentManager(), "dialog");
		} else
		{
			// Location permission has not been granted yet, request it.
			ActivityCompat.requestPermissions(activity, new String[]{permission}, requestId);
		}
	}

	public static boolean isPermissionGranted(String[] grantPermissions, int[] grantResults, String permission)
	{
		for (int i = 0; i < grantPermissions.length; i++)
		{
			if (permission.equals(grantPermissions[i]))
			{
				return grantResults[i] == PackageManager.PERMISSION_GRANTED;
			}
		}
		return false;
	}

	public static class PermissionDeniedDialog extends DialogFragment
	{
		private static final String ARGUMENT_FINISH_ACTIVITY = "finish";

		private boolean mFinishActivity = false;

		public static PermissionDeniedDialog newInstance(boolean finishActivity)
		{
			Bundle arguments = new Bundle();
			arguments.putBoolean(ARGUMENT_FINISH_ACTIVITY, finishActivity);

			PermissionDeniedDialog dialog = new PermissionDeniedDialog();
			dialog.setArguments(arguments);
			return dialog;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			mFinishActivity = getArguments().getBoolean(ARGUMENT_FINISH_ACTIVITY);

			return new AlertDialog.Builder(getActivity())
					.setMessage("This sample requires location permission to enable the \\'my location\\' layer. Please try again and grant access to use the location.\\nIf the permission has been permanently denied, it can be enabled from the System Settings &gt; Apps &gt; \\'Google Maps API Demos\\'.")
					.setPositiveButton(android.R.string.ok, null)
					.create();
		}

		@Override
		public void onDismiss(DialogInterface dialog)
		{
			super.onDismiss(dialog);
			if (mFinishActivity)
			{
				Toast.makeText(getActivity(), "Location permission is required for this demo.", Toast.LENGTH_SHORT).show();
				getActivity().finish();
			}
		}
	}

	public static class RationaleDialog extends DialogFragment
	{
		private static final String ARGUMENT_PERMISSION_REQUEST_CODE = "requestCode";
		private static final String ARGUMENT_FINISH_ACTIVITY = "finish";
		private boolean mFinishActivity = false;

		public static RationaleDialog newInstance(int requestCode, boolean finishActivity)
		{
			Bundle arguments = new Bundle();
			arguments.putInt(ARGUMENT_PERMISSION_REQUEST_CODE, requestCode);
			arguments.putBoolean(ARGUMENT_FINISH_ACTIVITY, finishActivity);
			RationaleDialog dialog = new RationaleDialog();
			dialog.setArguments(arguments);
			return dialog;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			Bundle arguments = getArguments();
			final int requestCode = arguments.getInt(ARGUMENT_PERMISSION_REQUEST_CODE);
			mFinishActivity = arguments.getBoolean(ARGUMENT_FINISH_ACTIVITY);

			return new AlertDialog.Builder(getActivity())
					.setMessage("Access to the location service is required to demonstrate the \\'my location\\' feature, which shows your current location on the map.")
					.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							// After click on Ok, request the permission.
							ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestCode);
							// Do not finish the Activity while requesting permission.
							mFinishActivity = false;
						}
					})
					.setNegativeButton(android.R.string.cancel, null)
					.create();
		}

		@Override
		public void onDismiss(DialogInterface dialog)
		{
			super.onDismiss(dialog);
			if (mFinishActivity)
			{
				Toast.makeText(getActivity(), "Location permission is required for this demo.", Toast.LENGTH_SHORT).show();
				getActivity().finish();
			}
		}
	}
}