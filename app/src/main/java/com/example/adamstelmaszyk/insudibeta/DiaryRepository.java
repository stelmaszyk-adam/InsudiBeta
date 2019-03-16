package com.example.adamstelmaszyk.insudibeta;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.adamstelmaszyk.insudibeta.db.DiaryRoomDatabase;
import com.example.adamstelmaszyk.insudibeta.db.dao.DiaryDao;
import com.example.adamstelmaszyk.insudibeta.db.entity.DiaryEntry;

import java.util.List;

public class DiaryRepository {
    private DiaryDao mDiaryDao;
    private LiveData<List<DiaryEntry>> mAllEntries;
    private LiveData<List<DiaryEntry>> m5hEntries;
    public DiaryRepository(Application application) {
        DiaryRoomDatabase db = DiaryRoomDatabase.getDatabase(application);
        mDiaryDao = db.diaryDao();
        mAllEntries = mDiaryDao.getAllEntries();
        m5hEntries = mDiaryDao.getEntries5hBefore();
    }

   public LiveData<List<DiaryEntry>> getAllEntries() {
        return mAllEntries;
    }
   public LiveData<List<DiaryEntry>> getEntries5hBefore() {
        return m5hEntries;
    }

    public void insert (DiaryEntry entry) {
        new insertAsyncTask(mDiaryDao).execute(entry);
    }

    private static class insertAsyncTask extends AsyncTask<DiaryEntry, Void, Void> {

        private DiaryDao mAsyncTaskDao;

        insertAsyncTask(DiaryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final DiaryEntry... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
