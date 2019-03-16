package com.example.adamstelmaszyk.insudibeta;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;

public class staticData {

    static public ArrayList<String> statyczneDaneUser;
    static public  ArrayList<String> statyczneDaneUserSecond;
    static public  ArrayList<String> statyczneDaneUserThird;

    static private SharedPreferences preferences;
    static public Context context;


    static public  void statyczneDaneInit(){

        statyczneDaneUser = new ArrayList<>();
        statyczneDaneUserSecond = new ArrayList<>();
        statyczneDaneUserThird = new ArrayList<>();
    }

    static public  void statyczneDaneUpload(Context context){

        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = preferences.edit(); //Wiem ze bardzo elegancko. Odwalcie sie xd

        for(int i=0; i<8; i++){
            editor.putString("CarbConv"+Integer.toString(i*3)+"_"+Integer.toString(i*3+2)+"", statyczneDaneUser.get(i));
            editor.putString("Sensitivity"+Integer.toString(i*3)+"_"+Integer.toString(i*3+2)+"", statyczneDaneUserSecond.get(i));
            editor.putString("GotoGlicemy"+Integer.toString(i*3)+"_"+Integer.toString(i*3+2)+"", statyczneDaneUserThird.get(i));
        }
        editor.apply();
    }

    static public  void loadData(Context context){

        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        statyczneDaneUser.clear();
        statyczneDaneUserSecond.clear();
        statyczneDaneUserThird.clear();

        for(int i=0; i<8; i++){
            statyczneDaneUser.add(preferences.getString("CarbConv"+Integer.toString(i*3)+"_"+Integer.toString(i*3+2), ""));
            statyczneDaneUserSecond.add(preferences.getString("Sensitivity"+Integer.toString(i*3)+"_"+Integer.toString(i*3+2), ""));
            statyczneDaneUserThird.add(preferences.getString("GotoGlicemy"+Integer.toString(i*3)+"_"+Integer.toString(i*3+2), ""));

        }

    }
}
