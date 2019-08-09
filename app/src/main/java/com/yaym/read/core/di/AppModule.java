package com.yaym.read.core.di;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;

import com.yaym.read.ReadApplication;
import com.yaym.read.core.tools.SpacesItemDecoration;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module in charge of injecting the application-wide dependencies
 */
@Module (includes = {ServicesModule.class})
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(ReadApplication reachApplication) {
        return reachApplication.getApplicationContext();
    }

    /**
     * Helper provider to calculate the optimal number of columns to be displayed
     * Source: https://stackoverflow.com/questions/29579811/changing-number-of-columns-with-gridlayoutmanager-and-recyclerview
     */
    @Provides
    @Singleton
    int provideNumberOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 150;
        // return the optimal number of columns
        return (int) (dpWidth / scalingFactor);
    }

    @Provides
    GridLayoutManager provideGridLayoutManager(Context context, int numberOfColumns) {
        return new GridLayoutManager(context, numberOfColumns);
    }

    @Provides
    @Singleton
    SpacesItemDecoration provideSpaceItemDecoration(int space) {
        return new SpacesItemDecoration(space);
    }
}
