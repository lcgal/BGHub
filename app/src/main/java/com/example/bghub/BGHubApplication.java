package com.example.bghub;

import android.app.Application;
import android.content.Context;

import dagger.hilt.android.HiltAndroidApp;


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
@HiltAndroidApp
public class BGHubApplication extends Application  {
//implements Configuration.Provider
    private static Application sApplication;

//    @Inject
//    DbContract mDataRepository;
//    @Inject
//    HttpService mHttpService;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
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
//    @NonNull
//    @Override
//    public Configuration getWorkManagerConfiguration() {
//        DelegatingWorkerFactory delegatingWorkerFactory = new WorkFactoryDelegator(mHttpService,mDataRepository);
//
//
//        return new Configuration.Builder().setWorkerFactory(delegatingWorkerFactory)
//                .build();
//    }

}
