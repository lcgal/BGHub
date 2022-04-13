package com.example.bghub.di;

import com.example.bghub.views.activities.main.MainActivity;
import com.example.bghub.views.activities.main.MainContract;
import com.example.bghub.views.activities.main.MainPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to auto create the MainSubComponent and bind
 * the {@link MainPresenter} to the graph
 */
@Module
public abstract class LoginModule {
    @Provides
    static MainContract.View provideView(MainActivity activity) {
        return activity;
    }

}
