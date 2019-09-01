package com.yaym.read.data;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
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

    // Tag for log messages
    private static final String LOG_TAG = BookRepository.class.getName();

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
                        Log.v(LOG_TAG, "LOG// fetched books are : " + books);
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

    public LiveData<Book> checkBook(String id) {
        return bookDao.loadBookById(id);
    }

    public void saveBookAsFavorite(Book book) {
        new insertBookAsyncTask(bookDao).execute(book);
    }

    public void removeBookFromFavorites(Book book) {
        new removeBookAsyncTask(bookDao).execute(book);
    }

    public void eraseAllFavoriteBooks () {
        new nukeDatabaseAsyncTask(bookDao).execute();
    }

    // Static inner classes below here to run database interactions in the background.

    /**
     * Inserts a book into the database.
     */
    private static class insertBookAsyncTask extends AsyncTask<Book, Void, Void> {
        private final BookDao mAsyncTaskDao;

        insertBookAsyncTask(BookDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Book... params) {
            mAsyncTaskDao.favoriteBook(params[0]);
            return null;

        }
    }

    /**
     *  Deletes a single book from the database.
     */
    private static class removeBookAsyncTask extends AsyncTask<Book, Void, Void> {
        private final BookDao mAsyncTaskDao;

        removeBookAsyncTask(BookDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Book... params) {
            mAsyncTaskDao.removeBook(params[0]);
            return null;
        }
    }

    /**
     * Deletes all books from the database (does not delete the table).
     */
    private static class nukeDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {
        private final BookDao mAsyncTaskDao;

        nukeDatabaseAsyncTask(BookDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.nukeTable();
            return null;
        }
    }
}