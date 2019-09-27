package com.yaym.read.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.yaym.read.data.BookRepository;
import com.yaym.read.ui.detail.BookDetailsViewModel;
import com.yaym.read.ui.explore.ExploreViewModel;
import com.yaym.read.ui.home.HomeViewModel;
import com.yaym.read.ui.settings.SettingsViewModel;

import javax.inject.Inject;

public class CustomViewModelFactory implements ViewModelProvider.Factory {

    private final BookRepository bookRepository;

    @Inject
    public CustomViewModelFactory(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class))
            return (T) new HomeViewModel(bookRepository);
        else if (modelClass.isAssignableFrom(ExploreViewModel.class))
            return (T) new ExploreViewModel(bookRepository);
        else if (modelClass.isAssignableFrom(BookDetailsViewModel.class))
            return (T) new BookDetailsViewModel(bookRepository);
        else if (modelClass.isAssignableFrom(SettingsViewModel.class))
            return (T) new SettingsViewModel(bookRepository);
        else {
            throw new IllegalArgumentException("ViewModel not Found " + modelClass);
        }
    }
}