package com.yaym.read.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.yaym.read.data.models.Book;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface BookDao {
    @Insert(onConflict = REPLACE)
    void favoriteBook(Book book);

    @Delete
    void removeBook(Book book);

    @Query("DELETE FROM book_table")
    void nukeTable();

    @Transaction
    @Query("SELECT * FROM book_table WHERE id = :id")
    LiveData<Book> loadBookById(String id);

    @Query("SELECT * FROM book_table")
    LiveData<List<Book>> loadBooks();
}