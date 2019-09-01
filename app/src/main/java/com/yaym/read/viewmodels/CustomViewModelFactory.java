package com.yaym.read.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.yaym.read.data.BookRepository;

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
        if (modelClass.isAssignableFrom(BooksListViewModel.class))
            return (T) new BooksListViewModel(bookRepository);
        else if (modelClass.isAssignableFrom(BookSummaryViewModel.class))
            return (T) new BookSummaryViewModel(bookRepository);
        else if (modelClass.isAssignableFrom(SettingsViewModel.class))
            return (T) new SettingsViewModel(bookRepository);
        else {
            throw new IllegalArgumentException("ViewModel not Found " + modelClass);
        }
    }
}