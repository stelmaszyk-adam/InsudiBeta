package com.example.adamstelmaszyk.insudibeta.ui.Fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adamstelmaszyk.insudibeta.BackCalculator;
import com.example.adamstelmaszyk.insudibeta.db.entity.DiaryEntry;
import com.example.adamstelmaszyk.insudibeta.viewmodel.DiaryViewModel;
import com.example.adamstelmaszyk.insudibeta.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalculatorFragment extends Fragment {

    private View view;
    private SharedPreferences preferences;
    private EditText TextUser[];
    private TextView lastTakenView;
    private String[] procentyDlaAktywnosci;
    private String formattedDate;
    private LinearLayout MoreOptions, TakeInsu;
    private DiaryViewModel mDiaryViewModel;
    private List<DiaryEntry> EntryList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calculator, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        //pobieram dane z bazy
        mDiaryViewModel = ViewModelProviders.of(this).get(DiaryViewModel.class);
        mDiaryViewModel.getAllEntries().observe(this, new Observer<List<DiaryEntry>>() {
            @Override
            public void onChanged(@Nullable final List<DiaryEntry> entries) {
                EntryList  = entries;
            }
        });

        layoutElementsRecognise();
        showlastInsluinTaken();

        MoreOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpcjeRozszerzone();
            }
        });

        TakeInsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickCount();
            }
        });

        return view;
    }

    void layoutElementsRecognise(){
        TextUser = new EditText[2];
        TextUser[0] = (EditText) view.findViewById(R.id.TextIloscWeglowodowanow);
        TextUser[1] = (EditText) view.findViewById(R.id.TextAktualnaGlikemia);

        MoreOptions =  (LinearLayout) view.findViewById(R.id.CalculatorMoreOptions);
        TakeInsu = (LinearLayout) view.findViewById(R.id.CalculatorTake);
    }

    public void showlastInsluinTaken()
    {
        lastTakenView = (TextView) view.findViewById(R.id.TextViewPrzyjecieInsuliny);

        final Date timeOfStart = Calendar.getInstance().getTime();
        long secoundsOfLastTake = preferences.getLong("hourOfLastTake", 0);
        formattedDate = preferences.getString("lastTaken", "");
        String lastInsulin = getResources().getString( R.string.calculatorLastTakenInsulin);

        if (timeOfStart.getTime() - secoundsOfLastTake < 18000) {

            lastTakenView.setText( lastInsulin+" " + formattedDate);
            lastTakenView.setVisibility(View.VISIBLE);
        } else {
            lastTakenView.setText(lastInsulin+" " +"--");
        }

    }

    public void OpcjeRozszerzone() {

        ExtenedCalculatorFragment extenedCalculator = new ExtenedCalculatorFragment();
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, extenedCalculator, extenedCalculator.getTag()).commit();

    }

    public void OnClickCount() {

        if(checkIfIsEmpty()){
            return;
        }
        // po sporcie rzeczy mozna dodac do kalulatora


        final double result = BackCalculator.countInsulin(TextUser[0].getText().toString(), TextUser[1].getText().toString(),preferences,EntryList);
        final SharedPreferences.Editor editor = preferences.edit();

        java.lang.String out = "";
        java.lang.String agree = "", disagree = "", title = "";

        String [] table = {"","","","",""};
        table = getResources().getStringArray(R.array.alert_dialog_calculator);
        out = table[0] + String.format(" %.2f ", result) + table[1];
        agree =table[2];
        disagree = table[3];
        title = table[4];

        AlertDialog.Builder altDial = new AlertDialog.Builder(getContext());
        altDial.setMessage(out).setCancelable(true)
                .setPositiveButton(agree, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final Date currentTime = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                        final String formattedDate = df.format(currentTime.getTime());

                        editor.putString("lastTaken", Integer.toString((int)result));
                        editor.putLong("hourOfLastTake", currentTime.getTime());

                        //zapis danych do dzinnika - w bazie danych To tez twoje Janek
                        DiaryEntry entry = new DiaryEntry( result , new Date(currentTime.getTime()) );
                        mDiaryViewModel.insert(entry);

                        editor.apply();
                        TextUser[0].setText("");
                        TextUser[1].setText("");

                        dialog.cancel();
                        showlastInsluinTaken();
                    }
                })
                .setNegativeButton(disagree, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.apply();
                        TextUser[0].setText("");
                        TextUser[1].setText("");
                        dialog.cancel();
                        showlastInsluinTaken();
                    }
                });
        AlertDialog alert = altDial.create();
        alert.setTitle(title);
        alert.show();

    }

    private boolean checkIfIsEmpty()
    {
        for(int i=0; i<2; i++)
        {
            if (TextUser[i].getText().toString().matches("")) {
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getContext());
                String [] table = {"",""};
                table = getResources().getStringArray(R.array.not_all_data_calculator);
                dlgAlert.setMessage(table[1]);
                dlgAlert.setTitle(table[0]);
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ProcentWBT", "0");
        editor.putString("ProcentAF", "0");
        editor.putString("ProcentIG", "0");
        editor.apply();
    }
}
