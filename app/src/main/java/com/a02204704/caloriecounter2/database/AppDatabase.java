package com.a02204704.caloriecounter2.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.a02204704.caloriecounter2.models.DailyCalorieEntry;

@Database(entities = {DailyCalorieEntry.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DailyCalorieEntriesDao getDailyCalorieEntriesDao();
}
