package com.yaym.read.core.di;

import android.app.SearchManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Modules are responsible for creating/satisfying dependencies
 * and signal to Dagger to search within the available methods for
 * possible instance providers
 */
@Module
public class ServicesModule {

    // Provide a reference to the ConnectivityManager to check state of network connectivity
    @Provides
    @Singleton
    ConnectivityManager provideConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    // Provide details on the currently active default data network
    @Provides
    @Singleton
    NetworkInfo provideNetworkInfo(ConnectivityManager connectivityManager) {
        return connectivityManager.getActiveNetworkInfo();
    }


    // Provide SearchManager
    @Provides
    @Singleton
    SearchManager provideSearchManager(Context context) {
        return (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
    }
}