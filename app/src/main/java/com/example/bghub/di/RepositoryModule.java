package com.example.bghub.di;

import com.example.bghub.repositories.data.DataContract;
import com.example.bghub.repositories.data.DataRepository;
import com.example.bghub.repositories.Http.HttpContract;
import com.example.bghub.repositories.Http.HttpRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


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
