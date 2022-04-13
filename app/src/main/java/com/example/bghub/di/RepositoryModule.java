package com.example.bghub.di;

import com.example.bghub.data.services.data.DbContract;
import com.example.bghub.data.services.data.DbService;
import com.example.bghub.data.services.Http.HttpContract;
import com.example.bghub.data.services.Http.HttpService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class RepositoryModule {
    @Provides
    @Singleton
    HttpContract providesHttpRepository(HttpService repository){
        return repository;
    }


    @Provides
    @Singleton
    DbContract providesDataRepository(DbService repository) { return repository; }
}
