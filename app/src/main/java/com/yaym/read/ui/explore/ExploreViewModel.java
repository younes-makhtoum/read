package com.yaym.read.ui.explore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.yaym.read.data.BookRepository;
import com.yaym.read.data.models.Book;

import java.util.List;

public class ExploreViewModel extends ViewModel {

    private static final String LOG_TAG = ExploreViewModel.class.getName();

    private LiveData<List<Book>> books;
    private final BookRepository bookRepository;

    public ExploreViewModel(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void fetchBooks(String inputQuery) {
        books = bookRepository.fetchBooksRemotely(inputQuery);
    }

    public LiveData<List<Book>> getBooks() {
        return this.books;
    }
}