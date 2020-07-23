package com.example.bghub;

import com.example.bghub.di.DaggerAppComponent;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Main Application class, Extends DaggerApplication.
 *
 * This app is designed to facilitate the connection with people interested in playing boardgames in your area.
 *
 * @author Lucas Collares Favaron Galvão (Henceforward, lcgal)
 * @version 1.0
 * @since 1.0
 * @see "https://github.com/lcgal"
 */
public class BGHubApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        FlowManager.init(new FlowConfig.Builder(this).build());
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

}
