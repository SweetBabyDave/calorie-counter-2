package com.a02204704.caloriecounter2.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.a02204704.caloriecounter2.models.DailyCalorieEntry;

import java.util.List;

@Dao
public interface DailyCalorieEntriesDao {
    @Insert
    public long insert(DailyCalorieEntry entry);

    @Query("SELECT * FROM dailycalorieentry")
    public List<DailyCalorieEntry> getAll();

    @Query("SELECT * FROM dailycalorieentry WHERE id = :id LIMIT 1")
    public DailyCalorieEntry findById(long id);

    @Update
    public void update(DailyCalorieEntry entry);

    @Delete
    public void delete(DailyCalorieEntry entry);
}
