package com.yaym.read.viewmodels;

import androidx.lifecycle.ViewModel;

import com.yaym.read.data.BookRepository;

public class SettingsViewModel extends ViewModel {

    private final BookRepository bookRepository;

    SettingsViewModel(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void eraseAllFavoriteBooks() {
        bookRepository.eraseAllFavoriteBooks();
    }
}