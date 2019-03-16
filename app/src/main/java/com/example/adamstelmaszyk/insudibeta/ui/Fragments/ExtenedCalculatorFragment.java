package com.example.adamstelmaszyk.insudibeta.ui.Fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adamstelmaszyk.insudibeta.R;

public class ExtenedCalculatorFragment extends Fragment {

    private View view;
    private SharedPreferences preferences;
    private String forToast = "";
    private CalculatorFragment calculator;
    private FragmentManager manager;
    private LinearLayout cancelButton, addButton;
    private Spinner spinners[];
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_extened_calculator, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        initSpinner();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickComeBack();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickGoTo();
            }
        });

        return view;
    }

    private void initSpinner()
    {
        calculator = new CalculatorFragment();
        manager = getFragmentManager();
        cancelButton = (LinearLayout) view.findViewById(R.id.cancelExtendCalculator);
        addButton = (LinearLayout) view.findViewById(R.id.addExtendCalculator);

        spinners = new Spinner[3];
        spinners[0] = (Spinner) view.findViewById(R.id.Spinner0);
        spinners[1] = (Spinner) view.findViewById(R.id.Spinner1);
        spinners[2] = (Spinner) view.findViewById(R.id.Spinner2);
        for(Spinner spi : spinners)
        {
            spi.setAdapter(arraySpinner(R.array.activity_spinner_array));
        }

    }

    public void OnClickGoTo()
    {

        int i=-1;
        String text [] = new String[3];
        String [] zamiana_na_proc = {"0","0.1","0.2","0.3","0.4","0.5","0.6","0.7","0.8","0.9","1"};
        String [] procenty = {"10%","20%","30%","40%","50%","60%","70%","80%","90%","100%"};
        for(Spinner spi : spinners) {

            i++;
            text[i] = spi.getSelectedItem().toString();
            if(text[i].equals( "0%")) {
                text[i] = zamiana_na_proc[0];
                continue;
            }

            for(int k=0; k<9; k++) {
                if(text[i].equals(procenty[k])) {
                    text[i]=zamiana_na_proc[k+1];
                    break;
                }
            }
        }
        //Tutaj pobierasz wszystko z Itemow i chcialem zapisac w preferencjach ale nie umiem

        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor editor = preferences.edit();
        Toast.makeText(getContext(),  R.string.eCalculatorFinishText, Toast.LENGTH_LONG).show();
        //inicjacja Toasta nie ma nazwy

        editor.putString("ProcentWBT", text[0]);
        editor.putString("ProcentAF", text[1]);
        editor.putString("ProcentIG", text[2]);
        editor.apply();
        // i powino dzalac

        manager.beginTransaction().replace(R.id.fragment_container, calculator, calculator.getTag()).commit();
    }


    public void OnClickComeBack()
    {
        Toast.makeText(getContext(), R.string.eCalculatorCancel, Toast.LENGTH_LONG).show();
        manager.beginTransaction().replace(R.id.fragment_container, calculator, calculator.getTag()).commit();
    }

    public ArrayAdapter<String> arraySpinner(int name_array)
    {
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(name_array));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return myAdapter;
    }


}
