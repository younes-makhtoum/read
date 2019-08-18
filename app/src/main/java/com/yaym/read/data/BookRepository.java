package com.yaym.read.data;

import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;

import com.yaym.read.data.daos.BookDao;
import com.yaym.read.data.models.Book;
import com.yaym.read.data.models.GoogleBooksResponse;
import com.yaym.read.services.BooksWebServices;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

/**
 * Provide a clean API to the rest of the app.
 * Handles book-related data operations.
 */
@Singleton
public class BookRepository {

    private final BooksWebServices booksWebServices;
    private MutableLiveData<List<Book>> mutableLiveData = new MutableLiveData<>();
    private final BookDao bookDao;
    private final Executor executor;

    @Inject
    public BookRepository(BooksWebServices booksWebServices, BookDao bookDao, Executor executor) {
        this.booksWebServices = booksWebServices;
        this.bookDao = bookDao;
        this.executor = executor;
    }

    @EverythingIsNonNull
    public MutableLiveData<List<Book>> fetchBooksRemotely(String inputQuery) {
        booksWebServices.getBooks(inputQuery, "10").enqueue(new Callback<GoogleBooksResponse>() {
            @Override
            public void onResponse(Call<GoogleBooksResponse> call, Response<GoogleBooksResponse> response) {
                executor.execute(() -> {
                    if (response.body() != null) {
                        List<Book> books = response.body().getBooks();
                        mutableLiveData.postValue(books);
                    }
                });
            }
            @Override
            public void onFailure(Call<GoogleBooksResponse> call, Throwable t) {
            }
        });
        return mutableLiveData;
    }
}
