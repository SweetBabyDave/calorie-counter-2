package com.a02204704.caloriecounter2.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class DailyCalorieEntry {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    public String food;

    @ColumnInfo
    public String amount;

    @ColumnInfo(name = "created_at")
    public long createdAt;
}
