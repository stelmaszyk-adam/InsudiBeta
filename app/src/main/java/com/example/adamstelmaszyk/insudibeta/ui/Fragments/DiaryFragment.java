package com.example.adamstelmaszyk.insudibeta.ui.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import com.example.adamstelmaszyk.insudibeta.R;
import com.example.adamstelmaszyk.insudibeta.db.entity.DiaryEntry;
import com.example.adamstelmaszyk.insudibeta.ui.DiaryAdapter;
import com.example.adamstelmaszyk.insudibeta.viewmodel.DiaryViewModel;

import java.util.Date;
import java.util.List;

public class DiaryFragment extends Fragment {


    private View view;
    private SharedPreferences preferences;
    private DiaryViewModel mDiaryViewModel;
    private Button button;
    private EditText editText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_diary, container, false);

        super.onCreate(savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final DiaryAdapter adapter = new DiaryAdapter(getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        mDiaryViewModel = ViewModelProviders.of(this).get(DiaryViewModel.class);

        mDiaryViewModel.getAllEntries().observe(this, new Observer<List<DiaryEntry>>() {
            @Override
            public void onChanged(@Nullable final List<DiaryEntry> entries) {
                // Update the cached copy of the words in the adapter.
                adapter.setEntries(entries);
            }
        });

        button = view.findViewById(R.id.fab);
        editText = view.findViewById(R.id.edit_entry);

        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          editText.getText();
                                          if (TextUtils.isEmpty(editText.getText())) {
                                              //setResult(RESULT_CANCELED, replyIntent);
                                              Toast.makeText(
                                                      getContext(),
                                                      R.string.empty_not_saved,
                                                      Toast.LENGTH_LONG).show();
                                              editText.setText("");
                                          } else {
                                              String sInsulineDosage = editText.getText().toString();
                                              double insulineDosage = Double.parseDouble(sInsulineDosage);
                                              DiaryEntry entry = new DiaryEntry( insulineDosage , new Date(System.currentTimeMillis()) );
                                              Toast.makeText(
                                                      getContext(),
                                                      R.string.hint_entry_insulinedosage,
                                                      Toast.LENGTH_LONG).show();
                                              mDiaryViewModel.insert(entry);
                                              editText.setText("");

                                          }
                                      }
        });


        return view;
    }


}
