package com.example.ass_boss.locationlist;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import static android.app.Activity.RESULT_OK;

public class LocationHelper {

    private PlaceDetectionClient placeDetectionClient;

    private static final int REQUEST_CODE = 465;

    @SuppressLint("MissingPermission")
    public void getCurrentPlaceData(Activity activity, final onLoadPlaceListCallBack callBack) throws Exception {
        if (isCanGetLocation(activity)) {
            placeDetectionClient = Places.getPlaceDetectionClient(activity);
            Task<PlaceLikelihoodBufferResponse> placeResponse =
                    placeDetectionClient.getCurrentPlace(null);

            placeResponse.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
                @Override
                public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        callBack.onCompleteLoad(task.getResult());
                    } else {
                        callBack.onFailLoad();
                    }
                }
            });
        } else {

            if (!isLocationAccessPermitted(activity)) {
                requestAccessPermission(activity);
            }

            if (isLocationProviderEnable(activity)) {
                requestLocationProvider(activity);
            }
        }
    }

    private void requestLocationProvider(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(activity.getResources().getString(R.string.gps_provider_disabled));
        builder.setPositiveButton(R.string.open_settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setCancelable(false);

        builder.show();
    }

    private void requestAccessPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    private boolean isCanGetLocation(Activity activity) throws Exception {
        return isLocationProviderEnable(activity) && isLocationAccessPermitted(activity);
    }

    private boolean isLocationAccessPermitted(Activity activity) {
        return ContextCompat.checkSelfPermission(activity.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isLocationProviderEnable(Activity activity) throws Exception {
        boolean gpsEnable = false;
        boolean networkEnable = false;

        LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        try {
            gpsEnable = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ignored) {
            throw new Exception(ignored);
        }

        try {
            networkEnable = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ignored) {
            throw new Exception(ignored);
        }


        return gpsEnable && networkEnable;
    }

    public static void activityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //getCurrentPlaceData();
            }
        }
    }
}
