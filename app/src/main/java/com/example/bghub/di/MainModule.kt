package com.example.bghub.di

import com.example.bghub.views.activities.main.MainActivity
import com.example.bghub.views.activities.main.MainContract
import com.example.bghub.views.activities.main.MainContract.Presenter
import com.example.bghub.views.activities.main.MainPresenter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ActivityComponent::class, SingletonComponent::class)
abstract class MainModule {
    @Binds
    abstract fun bindMainPresenter(
        mainPresenter: MainPresenter
    ) : Presenter
}