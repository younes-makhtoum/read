package com.yaym.read.core.di;

import com.yaym.read.ui.DetailActivity;
import com.yaym.read.ui.QueryActivity;
import com.yaym.read.ui.SummaryFragment;
import com.yaym.read.ui.WebReaderFragment;

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
    abstract SummaryFragment bindSummaryFragment();

    @ContributesAndroidInjector
    abstract WebReaderFragment bindWebReaderFragment();
}
