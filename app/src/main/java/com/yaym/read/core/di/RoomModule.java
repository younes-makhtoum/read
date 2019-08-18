package com.yaym.read.core.di;

import androidx.room.Room;

import com.yaym.read.ReadApplication;
import com.yaym.read.data.daos.BookDao;
import com.yaym.read.data.ReadDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.yaym.read.core.tools.Constants.DATABASE_NAME;

/**
 * Modules are responsible for creating/satisfying dependencies
 * and signal to Dagger to search within the available methods for
 * possible instance providers
 */
@Module
public class RoomModule {

    @Provides
    @Singleton
    ReadDatabase provideReachDb(ReadApplication readApplication) {
        return Room.databaseBuilder(readApplication, ReadDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    BookDao provideBookDao(ReadDatabase readDatabase) {
        return readDatabase.bookDao();
    }
}