package com.example.bghub;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Configuration;
import androidx.work.DelegatingWorkerFactory;


import com.example.bghub.Background.Factory.WorkFactoryDelegator;
import com.example.bghub.Repositories.Data.DataContract;
import com.example.bghub.Repositories.Http.HttpRepository;
import com.example.bghub.di.DaggerAppComponent;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import javax.inject.Inject;

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
public class BGHubApplication extends DaggerApplication implements Configuration.Provider {

    private static Application sApplication;

    @Inject
    DataContract.Repository mDataRepository;
    @Inject
    HttpRepository mHttpRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        FlowManager.init(new FlowConfig.Builder(this).build());
    }

    @Override
    public AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    public static Application getApplication() {
        return sApplication;
    }

    public static Context getAppContext() {
        return sApplication.getApplicationContext();
    }

    /**
     * Creating a custom WorkManager configuration
     *
     * We can add several different WorkerFactories to DelegationWorkerFactory, and let it sort it out through reflection* which one to use
     * *not sure if that's how it does it.
     */
    @NonNull
    @Override
    public Configuration getWorkManagerConfiguration() {
        DelegatingWorkerFactory delegatingWorkerFactory = new WorkFactoryDelegator(mHttpRepository,mDataRepository);


        return new Configuration.Builder().setWorkerFactory(delegatingWorkerFactory)
                .build();
    }

}
