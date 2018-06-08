package com.example.ass_boss.locationlist.module;

import android.app.Activity;

import com.example.ass_boss.locationlist.LocationHelper;

import dagger.Module;
import dagger.Provides;

@Module
public class LocationModule {

    @Provides
    LocationHelper provideLocationHelper(){
        return new LocationHelper();
    }
}
