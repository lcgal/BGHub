package com.example.bghub.di

import com.example.bghub.BuildConfig
import com.example.bghub.data.services.http.HttpContract
import com.example.bghub.data.services.http.HttpService
import com.example.bghub.data.services.data.DbContract
import com.example.bghub.data.services.data.DbService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class, SingletonComponent::class)
abstract class ServicesModule {

    @Binds
    abstract fun bindHttpService(service: HttpService): HttpContract

    @Binds
    abstract fun bindDataService(service: DbService): DbContract
}

//Separating this component so that we could inject a mocker interceptor in tests
@Module
@InstallIn(SingletonComponent::class)
class RetrofitInterceptor {
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() : Interceptor = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
}
