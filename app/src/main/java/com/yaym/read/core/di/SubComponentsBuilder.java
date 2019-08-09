package com.yaym.read.core.di;

import com.yaym.read.ui.QueryActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Binds all sub-component within the app.
 */
@Module
public abstract class SubComponentsBuilder {

    @ContributesAndroidInjector
    abstract QueryActivity bindQueryActivity();
}
