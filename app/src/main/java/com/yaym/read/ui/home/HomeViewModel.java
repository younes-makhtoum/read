package com.yaym.read.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.yaym.read.data.BookRepository;
import com.yaym.read.data.models.Book;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private static final String LOG_TAG = HomeViewModel.class.getName();

    private LiveData<List<Book>> books;
    private final BookRepository bookRepository;

    public HomeViewModel(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public LiveData<List<Book>> getAllBooks() {
        return bookRepository.getAllBooks();
    }
}