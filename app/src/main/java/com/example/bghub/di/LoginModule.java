package com.example.bghub.di;

import com.example.bghub.Views.Login.LoginActivity;
import com.example.bghub.Views.Login.LoginContract;
import com.example.bghub.Views.Login.LoginPresenter;
import com.facebook.login.LoginFragment;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * This is a Dagger module. We use this to auto create the LoginSubComponent and bind
 * the {@link LoginPresenter} to the graph
 */
@Module
public abstract class LoginModule {

    @ActivityScoped
    @Binds
    abstract LoginContract.Presenter loginPresenter(LoginPresenter presenter);

    @Provides
    static LoginContract.View provideView(LoginActivity activity) {
        return activity;
    }

}
