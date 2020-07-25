package com.example.bghub.di;

import com.example.bghub.Views.Login.LoginActivity;
import com.example.bghub.Views.Login.LoginContract;
import com.example.bghub.Views.Login.LoginPresenter;
import com.example.bghub.Views.Main.MainActivity;
import com.example.bghub.Views.Main.MainContract;
import com.example.bghub.Views.Main.MainPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to auto create the LoginSubComponent and bind
 * the {@link LoginPresenter} to the graph
 */
@Module
public abstract class MainModule {

    @ActivityScoped
    @Binds
    abstract MainContract.Presenter mainPresenter(MainPresenter presenter);

    @Provides
    static MainContract.View provideView(MainActivity activity) {
        return activity;
    }

}
