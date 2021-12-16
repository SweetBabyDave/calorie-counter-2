package com.a02204704.caloriecounter2.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.a02204704.caloriecounter2.database.AppDatabase;
import com.a02204704.caloriecounter2.models.DailyCalorieEntry;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DailyCalorieViewModel extends AndroidViewModel {
    private AppDatabase database;
    private MutableLiveData<Boolean> saving = new MutableLiveData<>();
    private ObservableArrayList<DailyCalorieEntry> entries = new ObservableArrayList<>();
    private MutableLiveData<DailyCalorieEntry> currentEntry = new MutableLiveData<>();

    public DailyCalorieViewModel(@NonNull Application application) {
        super(application);
        saving.setValue(false);
        database = Room.databaseBuilder(application, AppDatabase.class, "dailycaloriedb").build();

        new Thread(() -> {
            ArrayList<DailyCalorieEntry> dailyCalorieEntries = (ArrayList<DailyCalorieEntry>) database.getDailyCalorieEntriesDao().getAll();
            entries.addAll(dailyCalorieEntries);
        }).start();
    }

    public MutableLiveData<DailyCalorieEntry> getCurrentEntry() {
        return currentEntry;
    }

    public void setCurrentEntry(DailyCalorieEntry entry) {
        this.currentEntry.setValue(entry);
    }

    public MutableLiveData<Boolean> getSaving() {
        return saving;
    }

    public ObservableArrayList<DailyCalorieEntry> getEntries() {
        return entries;
    }

    public void deleteCurrentEntry() {
        new Thread(() -> {
            database.getDailyCalorieEntriesDao().delete(currentEntry.getValue());
            entries.remove(currentEntry.getValue());
            currentEntry.postValue(null);
        }).start();
    }

    // Could be a problem with the type later when api calling
    public void saveDailyCalorie(String food, String amount) {
        saving.setValue(true);
        new Thread(() -> {
            if (currentEntry.getValue() != null) {
                DailyCalorieEntry current = currentEntry.getValue();
                current.food = food;
                current.amount = amount;
                database.getDailyCalorieEntriesDao().update(current);
                currentEntry.postValue(current);
                int index = entries.indexOf(current);
                entries.set(index, current);
            } else {
                DailyCalorieEntry newEntry = new DailyCalorieEntry();
                newEntry.food = food;
                newEntry.amount = amount;
                newEntry.createdAt = System.currentTimeMillis();
                newEntry.id = database.getDailyCalorieEntriesDao().insert(newEntry);
                entries.add(newEntry);
            }


            saving.postValue(false);
        }).start();

    }
}
