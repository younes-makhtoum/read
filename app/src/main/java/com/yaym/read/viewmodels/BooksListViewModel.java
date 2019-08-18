package com.yaym.read.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.yaym.read.data.BookRepository;
import com.yaym.read.data.models.Book;

import java.util.List;

public class BooksListViewModel extends ViewModel {

    private LiveData<List<Book>> books;
    private final BookRepository bookRepository;

    BooksListViewModel(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void fetchBooks(String inputQuery) {
        if (this.books != null) {
            return;
        }
        books = bookRepository.fetchBooksRemotely(inputQuery);
    }
    public LiveData<List<Book>> getBooks() {
        return this.books;
    }
}