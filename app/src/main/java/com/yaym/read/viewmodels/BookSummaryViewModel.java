package com.yaym.read.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.yaym.read.data.BookRepository;
import com.yaym.read.data.models.Book;

public class BookSummaryViewModel extends ViewModel {

    private final BookRepository bookRepository;

    BookSummaryViewModel(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public LiveData<Book> checkBookInFavorites(String id) {
        return bookRepository.checkBook(id);
    }

    public void saveBookAsFavorite(Book book) {
        bookRepository.saveBookAsFavorite(book);
    }

    public void removeBookFromFavorites(Book book) {
        bookRepository.removeBookFromFavorites(book);
    }
}