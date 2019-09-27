package com.yaym.read.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.yaym.read.data.BookRepository;
import com.yaym.read.data.models.Book;

public class BookDetailsViewModel extends ViewModel {

    private final BookRepository bookRepository;

    public BookDetailsViewModel(BookRepository bookRepository) {
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