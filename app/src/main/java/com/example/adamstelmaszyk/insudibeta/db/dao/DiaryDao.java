package com.example.adamstelmaszyk.insudibeta.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.adamstelmaszyk.insudibeta.db.entity.DiaryEntry;

import java.util.List;

@Dao
public interface DiaryDao {

    @Insert
    void insert(DiaryEntry entry);

    @Query("SELECT * FROM diary_entries ORDER BY dateTime ASC")
    LiveData<List<DiaryEntry>> getAllEntries();

    @Query("SELECT * FROM diary_entries WHERE datetime(dateTime) > date('now', '-5 hours') ORDER BY dateTime ASC")
    LiveData<List<DiaryEntry>> getEntries5hBefore();

//    @Query("SELECT id, insulinDose , dateTime FROM diary_entries ORDER BY dateTime ASC  ")
//    List<DiaryEntry> getInsulineDosegeAndDateTime();
//
    @Query("DELETE FROM diary_entries")
    void deleteAll();

    @Query("SELECT id, insulinDose , dateTime FROM diary_entries ")
    LiveData<List<DiaryEntry>> getInsulineDosegeInLast24h();


}

