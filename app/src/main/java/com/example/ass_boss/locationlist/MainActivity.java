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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.ass_boss.locationlist.component.AppComponent;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    private LocationHelper locationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationHelper = App.getMainComponent().getLocationHelper();

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            locationHelper.getCurrentPlaceData(this, new onLoadPlaceListCallBack() {
                @Override
                public void onCompleteLoad(PlaceLikelihoodBufferResponse result) {

                    for (PlaceLikelihood place : result) {
                        Log.i("RESPONSE PLACE", String.valueOf(place.getPlace().getName()));
                    }
                }

                @Override
                public void onFailLoad() {
                    Log.i("TEST", " onFailLoad");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
