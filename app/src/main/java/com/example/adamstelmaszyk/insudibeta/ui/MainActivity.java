package com.example.adamstelmaszyk.insudibeta.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.adamstelmaszyk.insudibeta.R;
import com.example.adamstelmaszyk.insudibeta.staticData;
import com.example.adamstelmaszyk.insudibeta.ui.Fragments.AuthorFragment;
import com.example.adamstelmaszyk.insudibeta.ui.Fragments.CalculatorFragment;
import com.example.adamstelmaszyk.insudibeta.ui.Fragments.DiaryFragment;
import com.example.adamstelmaszyk.insudibeta.ui.Fragments.SettingsFragment;
import com.example.adamstelmaszyk.insudibeta.ui.Fragments.UserFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigattion_drawer_open, R.string.navigattion_drawer_close);

        //moze sie tu krzaczyc bo w jezykach grzebalem
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        staticData.statyczneDaneInit();
        staticData.loadData(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final int lan =preferences.getInt("lan",-1);

        if(savedInstanceState == null){

            if(lan!=-1) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                        , new CalculatorFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_calculator);
            }else{
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                        , new UserFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_user);
            }

        }
    }



    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {


            case R.id.nav_calculator:
                final int lan1 =preferences.getInt("lan",-1);
                if(lan1!=-1) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                            , new CalculatorFragment()).commit();
                }else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                            , new UserFragment()).commit();
                }
                break;
            case R.id.nav_user:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                        , new UserFragment()).commit();
                break;
            case R.id.nav_authors:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                        , new AuthorFragment()).commit();
                break;
            case R.id.nav_diary:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                        , new DiaryFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
