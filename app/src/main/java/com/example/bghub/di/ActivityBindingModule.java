package com.example.bghub.di;

import com.example.bghub.Views.Login.LoginActivity;
import com.example.bghub.Views.Main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * We want Dagger.Android to create a Subcomponent which has a parent Component of whichever module ActivityBindingModule is on,
 * in our case that will be AppComponent. The beautiful part about this setup is that you never need to tell AppComponent that it is going to have all these subcomponents
 * nor do you need to tell these subcomponents that AppComponent exists.
 * We are also telling Dagger.Android that this generated SubComponent needs to include the specified modules and be aware of a scope annotation @ActivityScoped
 */

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = {MainModule.class})
    abstract MainActivity mainActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {LoginModule.class})
    abstract LoginActivity loginActivity();
}
