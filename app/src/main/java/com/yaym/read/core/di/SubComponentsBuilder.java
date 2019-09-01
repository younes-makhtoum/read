package com.yaym.read.core.di;

import com.yaym.read.ui.BookSummaryFragment;
import com.yaym.read.ui.DetailActivity;
import com.yaym.read.ui.QueryActivity;
import com.yaym.read.ui.BookIdCardFragment;
import com.yaym.read.ui.SettingsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Binds all sub-component within the app.
 */
@Module
public abstract class SubComponentsBuilder {

    @ContributesAndroidInjector
    abstract QueryActivity bindQueryActivity();

    @ContributesAndroidInjector
    abstract DetailActivity bindDetailActivity();

    @ContributesAndroidInjector
    abstract BookIdCardFragment bindSummaryFragment();

    @ContributesAndroidInjector
    abstract BookSummaryFragment bindWebReaderFragment();

    @ContributesAndroidInjector
    abstract SettingsActivity.SettingsFragment bindSettingsFragment();
}
