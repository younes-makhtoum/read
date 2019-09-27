package com.yaym.read.core.di;

import com.yaym.read.ui.MainActivity;
import com.yaym.read.ui.detail.BookDetailsFragment;
import com.yaym.read.ui.explore.ExploreFragment;
import com.yaym.read.ui.home.HomeFragment;
import com.yaym.read.ui.settings.SettingsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Binds all sub-component within the app.
 */
@Module
public abstract class SubComponentsBuilder {

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract HomeFragment bindHomeFragment();

    @ContributesAndroidInjector
    abstract ExploreFragment bindExploreFragment();

    @ContributesAndroidInjector
    abstract BookDetailsFragment bindBookDetailsFragment();

    @ContributesAndroidInjector
    abstract SettingsFragment bindSettingsFragment();
}
