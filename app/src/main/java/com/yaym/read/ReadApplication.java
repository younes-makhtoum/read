package com.yaym.read;

import com.yaym.read.core.di.AppComponent;
import com.yaym.read.core.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class ReadApplication extends DaggerApplication {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }

    public static  AppComponent getComponent(){
        return appComponent;
    }
}