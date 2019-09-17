package com.yaym.read.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.yaym.read.R;
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
    private final SharedPreferences sharedPrefs;
    private final Context context;

    @Inject
    public BookRepository(BooksWebServices booksWebServices, BookDao bookDao, Executor executor, SharedPreferences sharedPrefs, Context context) {
        this.booksWebServices = booksWebServices;
        this.bookDao = bookDao;
        this.executor = executor;
        this.sharedPrefs = sharedPrefs;
        this.context = context;
    }

    @EverythingIsNonNull
    public MutableLiveData<List<Book>> fetchBooksRemotely(String inputQuery) {

        booksWebServices.getBooks(inputQuery, String.valueOf(sharedPrefs.getInt(context.getResources().getString(R.string.key_max_results), 1)), sharedPrefs.getString(context.getResources().getString(R.string.key_set_lang), "")).enqueue(new Callback<GoogleBooksResponse>() {
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

    public LiveData<List<Book>> getAllBooks() {
        return bookDao.loadBooks();
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