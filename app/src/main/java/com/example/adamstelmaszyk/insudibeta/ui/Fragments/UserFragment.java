package com.example.adamstelmaszyk.insudibeta.ui.Fragments;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adamstelmaszyk.insudibeta.R;
import com.example.adamstelmaszyk.insudibeta.staticData;

public class UserFragment extends Fragment {

    private View view;
    private SharedPreferences preferences;
    private LinearLayout buttonCancel, buttonAdd;
    private EditText InputText[];
    private String [] table_text;
    private TextView TitleOfpage;
    private TextView TextViewSetChange, TextViewMenu;
    private String forToast="";
    private Spinner fakeSpinner;
    private TextView spinnerText;
    private FragmentManager manager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());


        manager = getFragmentManager();

        layoutElementsRecognise();
        setLanguage();



        loadData();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickGoTo();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickComeBack();
            }
        });


        int lan=preferences.getInt("lan",-1);

        if(lan==-1){
            startAcceptTerms();
        }


        return view;
    }

    private void startAcceptTerms() {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getContext());
        dlgAlert.setMessage(R.string.textTerms);
        dlgAlert.setTitle(R.string.titleTrems);
        dlgAlert.setPositiveButton(R.string.agree, null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    private void loadData()
    {
        // @todo: Load old data as default when user changes his profile
        //Adam zrob zeby domyslnie te wartosci byly wpisane w pola. Wszytsko masz juz w stringach. Jak to bedzie pierwszy raz to string bedzie pusty
        for(int i=0; i<8; i++)
        {
            String textID = "TextNumber"+i;
            int resID = getResources().getIdentifier(textID, "id", getActivity().getPackageName());
            InputText[i] = view.findViewById(resID);
        }

        int i=0;
        for(String data : staticData.statyczneDaneUser){
            InputText[i].setText(data);
            i++;
        }

    }

    void layoutElementsRecognise() {

        buttonCancel = (LinearLayout) view.findViewById(R.id.buttonCancelUser);
        buttonAdd = (LinearLayout) view.findViewById(R.id.buttonAddUser);

        InputText = new EditText[8];
        table_text = new String[8];

        TitleOfpage = (TextView) view.findViewById(R.id.TitleOfPage);
        TextViewSetChange = (TextView) view.findViewById(R.id.TextViewSetChanges);
        TextViewMenu = (TextView) view.findViewById(R.id.comebackToMenu);
        spinnerText = (TextView) view.findViewById(R.id.text_Fake);
        spinnerText.setVisibility(View.VISIBLE);

        fakeSpinner = (Spinner) view.findViewById(R.id.spinner_Fake);
        fakeSpinner.setVisibility(View.VISIBLE);
        fakeSpinner.setAdapter(arraySpinner(R.array.fake_spinner_text));
    }

    public void setLanguage()
    {
        String [] table = {"","","","",""};
        table = getResources().getStringArray(R.array.text_user_first);
        spinnerText.setText(table[1]);
        //TitleOfpage.setText(table[1]);
        TextViewSetChange.setText(table[2]);
        TextViewMenu.setText(table[3]);
        forToast = table[4];
    }

    public ArrayAdapter<String> arraySpinner(int name_array)
    {
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(name_array));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return myAdapter;
    }

    public void OnClickGoTo()
    {
        if(checkIfIsEmpty())
        {
            return;
        }
        User2Fragment userSecond = new User2Fragment();
        manager.beginTransaction().replace(R.id.fragment_container, userSecond, userSecond.getTag()).commit();

        Toast.makeText(getContext(), forToast, Toast.LENGTH_LONG).show();
    }


    public void OnClickComeBack()
    {
        int lan=preferences.getInt("lan",-1);
        if(lan!=-1) {
            CalculatorFragment calculator = new CalculatorFragment();
            //FragmentManager manager = getFragmentManager();
            staticData.loadData(getContext());
            manager.beginTransaction().replace(R.id.fragment_container, calculator, calculator.getTag()).commit();
        }else{
            Toast.makeText(getContext(), R.string.userFill,Toast.LENGTH_LONG).show();
        }

    }

    private boolean checkIfIsEmpty()
    {
        for(int i=0; i<8; i++)
        {
            if (InputText[i].getText().toString().matches("")) {
                niepelnedane();
                return true;
            }
            table_text[i] = InputText[i].getText().toString();
        }
        for(int i =0; i<staticData.statyczneDaneUser.size(); i++){
            staticData.statyczneDaneUser.set(i, table_text[i]);
        }
        return false;
    }

    public void niepelnedane() {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getContext());
        java.lang.String blad = "", message = "";
        String [] table = {"",""};
        table = getResources().getStringArray(R.array.not_all_data_calculator);
        blad = table[0];
        message = table[1];
        dlgAlert.setMessage(message);
        dlgAlert.setTitle(blad);
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
}
