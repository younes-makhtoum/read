package com.yaym.read.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.yaym.read.data.converters.DateConverter;
import com.yaym.read.data.converters.StringsListConverter;
import com.yaym.read.data.daos.BookDao;
import com.yaym.read.data.models.Book;

@Database(entities = {Book.class}, version = 2, exportSchema = false)
@TypeConverters({DateConverter.class, StringsListConverter.class})
public abstract class ReadDatabase extends RoomDatabase {
    // --- SINGLETON ---
    private static volatile ReadDatabase INSTANCE;
    // --- DAO ---
    public abstract BookDao bookDao();
}