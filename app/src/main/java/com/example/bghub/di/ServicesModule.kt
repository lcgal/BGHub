package com.example.bghub.di

import com.example.bghub.data.services.Http.HttpContract
import com.example.bghub.data.services.Http.HttpService
import com.example.bghub.data.services.data.DbContract
import com.example.bghub.data.services.data.DbService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ActivityComponent::class, SingletonComponent::class)
abstract class ServicesModule {
    @Binds
    abstract fun bindHttpService(service: HttpService): HttpContract

    @Binds
    abstract fun bindDataService(service: DbService): DbContract
}