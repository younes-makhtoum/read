package com.yaym.read.core.di;

import androidx.lifecycle.ViewModelProvider;

import com.yaym.read.data.BookRepository;
import com.yaym.read.viewmodels.CustomViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelModule {

    @Provides
    ViewModelProvider.Factory provideViewModelFactory(BookRepository bookRepository) {
        return new CustomViewModelFactory(bookRepository);
    }
}