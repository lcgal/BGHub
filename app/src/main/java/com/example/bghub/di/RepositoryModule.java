package com.example.bghub.di;

import android.content.Context;

import com.example.bghub.Repositories.Data.DataContract;
import com.example.bghub.Repositories.Data.DataRepository;
import com.example.bghub.Repositories.Http.HttpContract;
import com.example.bghub.Repositories.Http.HttpRepository;
import com.example.bghub.Views.Login.LoginActivity;
import com.example.bghub.Views.Login.LoginContract;
import com.example.bghub.Views.Login.LoginPresenter;
import com.facebook.login.LoginFragment;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;


@Module
public class RepositoryModule {
    @Provides
    @Singleton
    HttpContract providesHttpRepository(HttpRepository repository){
        return repository;
    }


    @Provides
    @Singleton
    DataContract.Repository providesDataRepository(DataRepository repository) { return repository; }
}
