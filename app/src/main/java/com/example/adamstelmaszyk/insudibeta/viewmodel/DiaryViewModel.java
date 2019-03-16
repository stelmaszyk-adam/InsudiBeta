package com.example.adamstelmaszyk.insudibeta.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.adamstelmaszyk.insudibeta.DiaryRepository;
import com.example.adamstelmaszyk.insudibeta.db.entity.DiaryEntry;

import java.util.List;

public class DiaryViewModel extends AndroidViewModel {

    private DiaryRepository mRepository;

    private LiveData<List<DiaryEntry>> mAllEntries;
    private LiveData<List<DiaryEntry>> m5hEntries;

    public DiaryViewModel(Application application){
        super(application);
        mRepository = new DiaryRepository(application);
        mAllEntries = mRepository.getAllEntries();
        m5hEntries = mRepository.getEntries5hBefore();
    }

    public LiveData<List<DiaryEntry>> getAllEntries(){ return  mAllEntries; }
    LiveData<List<DiaryEntry>> getEntries5hBefore(){ return  m5hEntries; }


    public void insert(DiaryEntry entry) { mRepository.insert(entry);}

}
