package com.example.ass_boss.locationlist;

import android.app.Activity;
import android.app.Application;

import com.example.ass_boss.locationlist.component.AppComponent;
import com.example.ass_boss.locationlist.component.DaggerAppComponent;
import com.example.ass_boss.locationlist.module.LocationModule;

public class App extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.create();
    }

    public static AppComponent getMainComponent(){
        return component;
    }
}
