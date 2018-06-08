package com.example.ass_boss.locationlist.component;

import com.example.ass_boss.locationlist.LocationHelper;
import com.example.ass_boss.locationlist.module.LocationModule;

import dagger.Component;

@Component(modules = {LocationModule.class})
public interface AppComponent {

    LocationHelper getLocationHelper();
}
