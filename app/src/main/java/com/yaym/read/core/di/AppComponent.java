package com.yaym.read.core.di;

import com.yaym.read.ReadApplication;
import javax.inject.Singleton;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        SubComponentsBuilder.class})
public interface AppComponent extends AndroidInjector<DaggerApplication> {

    void inject(ReadApplication ReadApp);

    @Override
    void inject(DaggerApplication instance);

    @Component.Builder
    interface Builder {
        AppComponent build();
        @BindsInstance
        Builder application(ReadApplication ReadApp);
    }
}
