package com.yaym.read.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.yaym.read.data.models.Book;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface BookDao {
    @Insert(onConflict = REPLACE)
    void saveBook(Book book);

    @Query("SELECT * FROM book_table")
    LiveData<List<Book>> loadBooks();
}