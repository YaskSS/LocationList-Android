package com.example.ass_boss.locationlist;

import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;

public interface onLoadPlaceListCallBack {
    void onCompleteLoad(PlaceLikelihoodBufferResponse result);
    void onFailLoad();
}
