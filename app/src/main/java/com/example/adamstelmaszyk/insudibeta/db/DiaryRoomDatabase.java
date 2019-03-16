package com.example.adamstelmaszyk.insudibeta.db;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.adamstelmaszyk.insudibeta.db.dao.DiaryDao;
import com.example.adamstelmaszyk.insudibeta.db.entity.DiaryEntry;

import java.util.Date;
import java.util.concurrent.TimeUnit;

//@Database(entities = {DiaryEntry.class}, version = 1, exportSchema = false)
@Database(entities = {DiaryEntry.class}, version = 1)
public abstract class DiaryRoomDatabase extends RoomDatabase {
    public abstract DiaryDao diaryDao();

    private static volatile DiaryRoomDatabase INSTANCE;

    public static DiaryRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (DiaryRoomDatabase.class){
                if (INSTANCE == null){
                    //create database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DiaryRoomDatabase.class, "diary_database")
                            //.addCallback(sRoomDatabaseCallback)
                            .build();

                }

            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final DiaryDao mDao;

        PopulateDbAsync(DiaryRoomDatabase db){
            mDao = db.diaryDao();
        }

        @Override
        protected Void doInBackground(final Void... params){
            mDao.deleteAll();
            DiaryEntry entry = new DiaryEntry(6 ,
                    new Date(System.currentTimeMillis()
                            - TimeUnit.DAYS.toMillis(1) - TimeUnit.HOURS.toMillis(12))
            );
            mDao.insert(entry);

            entry = new DiaryEntry(6.5 ,
                    new Date(System.currentTimeMillis()
                            - TimeUnit.DAYS.toMillis(1) - TimeUnit.HOURS.toMillis(8))
            );
            mDao.insert(entry);

            entry = new DiaryEntry(7.3 ,
                    new Date(System.currentTimeMillis()
                            - TimeUnit.DAYS.toMillis(1) - TimeUnit.HOURS.toMillis(4))
            );
            mDao.insert(entry);

            entry = new DiaryEntry(5.7 ,
                    new Date(System.currentTimeMillis()
                            - TimeUnit.DAYS.toMillis(1) - TimeUnit.HOURS.toMillis(1))
            );
            mDao.insert(entry);

            entry = new DiaryEntry(6 ,
                    new Date(System.currentTimeMillis()
                            - TimeUnit.HOURS.toMillis(22))
            );
            mDao.insert(entry);

            entry = new DiaryEntry(4.5 ,
                    new Date(System.currentTimeMillis()
                            - TimeUnit.HOURS.toMillis(12))
            );
            mDao.insert(entry);

            entry = new DiaryEntry(7 ,
                    new Date(System.currentTimeMillis()
                            - TimeUnit.HOURS.toMillis(8))
            );
            mDao.insert(entry);

            entry = new DiaryEntry(3.5 ,
                    new Date(System.currentTimeMillis()
                            - TimeUnit.HOURS.toMillis(4))
            );
            mDao.insert(entry);

            entry = new DiaryEntry(5 ,
                    new Date(System.currentTimeMillis()
                            - TimeUnit.HOURS.toMillis(1))
            );
            mDao.insert(entry);
            return null;
        }

    }


}
