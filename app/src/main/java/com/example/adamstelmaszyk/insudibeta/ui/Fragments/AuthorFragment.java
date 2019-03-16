package com.example.adamstelmaszyk.insudibeta.ui.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.adamstelmaszyk.insudibeta.R;

public class AuthorFragment extends Fragment {

    private View view;
    private ImageView buttonMail, buttonFacebook;

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_author, container, false);
        //preferences = PreferenceManager.getDefaultSaredPreferences(getContext());

        buttonMail = (ImageView) view.findViewById(R.id.aGoToMail);
        buttonFacebook = (ImageView) view.findViewById(R.id.aGoToFacebook);

        buttonFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent face = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Insudi-kalkulator-insuliny-255510678474323/?epa=SEARCH_BOX"));
                startActivity(face);
            }
        });

        buttonMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailInten = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","adamstelm98@gmail.com",null));
                //emailInten.putExtra(Intent.EXTRA_SUBJECT, "Welcome");
                //emailInten.putExtra(Intent.EXTRA_TEXT, "Here body of email goes...");
                startActivity(Intent.createChooser(emailInten, "Send email..."));
            }
        });


        return view;
    }
}
