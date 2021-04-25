package com.example.bghub.di;

import com.example.bghub.Repositories.Data.DataContract;
import com.example.bghub.Repositories.Data.DataRepository;
import com.example.bghub.Repositories.Http.HttpContract;
import com.example.bghub.Repositories.Http.HttpRepository;

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
