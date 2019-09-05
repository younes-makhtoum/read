package com.yaym.read.core.di;

import com.yaym.read.ui.MainActivity;
import com.yaym.read.ui.explore.ExploreFragment;
import com.yaym.read.ui.detail.BookSummaryFragment;
import com.yaym.read.ui.detail.DetailActivity;
import com.yaym.read.ui.detail.BookIdCardFragment;
import com.yaym.read.ui.home.HomeFragment;
import com.yaym.read.ui.settings.SettingsActivity;

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
    abstract DetailActivity bindDetailActivity();

    @ContributesAndroidInjector
    abstract HomeFragment bindHomeFragment();

    @ContributesAndroidInjector
    abstract ExploreFragment bindExploreFragment();

    @ContributesAndroidInjector
    abstract BookIdCardFragment bindIdCardFragment();

    @ContributesAndroidInjector
    abstract BookSummaryFragment bindSummaryFragment();

    @ContributesAndroidInjector
    abstract SettingsActivity.SettingsFragment bindSettingsFragment();
}
