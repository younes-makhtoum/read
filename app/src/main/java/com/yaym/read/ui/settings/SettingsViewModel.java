package com.yaym.read.ui.settings;

import androidx.lifecycle.ViewModel;

import com.yaym.read.data.BookRepository;

public class SettingsViewModel extends ViewModel {

    private final BookRepository bookRepository;

    public SettingsViewModel(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void eraseAllFavoriteBooks() {
        bookRepository.eraseAllFavoriteBooks();
    }
}