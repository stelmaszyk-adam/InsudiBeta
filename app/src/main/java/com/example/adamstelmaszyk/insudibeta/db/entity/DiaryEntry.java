package com.example.adamstelmaszyk.insudibeta.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.adamstelmaszyk.insudibeta.db.converter.DateConverter;

import java.util.Date;

@Entity(tableName = "diary_entries")
public class DiaryEntry {



    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    @NonNull
    public double insulinDose; //dawka insuliny
    @NonNull
    @TypeConverters({DateConverter.class})
    public Date dateTime; // data i czas wpisu

    // dane pomocnicze
    /*
     * zapisujemy wszystkie dane potrzebne do obliczenia dawki insuliny
     * ( (aktualna glikemia - docelowa glikemia )  / wspolczynnik wrazliwosci ) - aktywana insulina + ilosc wymiennikow weglowodanowych - przelicznik weglowodanow ) mnoznik
     *
     * */
//    public double activeInsulin;  //aktywana insulina
//    public double currentGlycemia; //aktialna glikemia
//    public double targetGlycemia; //docelowa glikemia
//    public double sensitivityFactor; // wspolczynnik wrazliwosci
//    public double amountOfCarbohydrateExchangers; // ilosc wymiennikow weglowodanowych
//    public double carbohydrateConversion; // przelicznik weglowodanow
//    public double superFactor; // mnoznik

    public DiaryEntry(){}

    @Ignore
    public DiaryEntry( double insulinDose, Date dateTime){
        this.id = 0;
        this.insulinDose = insulinDose;
        this.dateTime = dateTime;
//
//        this.activeInsulin = calcArg[0];
//        this.currentGlycemia = calcArg[1];
//        this.targetGlycemia = calcArg[2];
//        this.sensitivityFactor = calcArg[3];
//        this.amountOfCarbohydrateExchangers = calcArg[4];
//        this.carbohydrateConversion = calcArg[5];
//        this.superFactor = calcArg[6];



    }

}
