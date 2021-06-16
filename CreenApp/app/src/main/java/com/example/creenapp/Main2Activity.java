package com.example.creenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main2Activity extends AppCompatActivity {

//    //Reference Variables from activity_main2
//    private BottomNavigationView mainNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        //Reference of bottom nav from activity_main2 xml
        BottomNavigationView mainNav = (BottomNavigationView) findViewById(R.id.bottomNavigationView);


        //Setting fragment_home as default
        setFragment(new FragmentHome());

        //Bottom Navigation events and fragment switching
        mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        fragment = new FragmentHome();
                        setFragment(fragment);
//                        mainNav.setItemBackgroundResource(R.color.grey);
                        break;
                    case R.id.nav_aboutus:
                        fragment = new FragmentAboutUs();
                        setFragment(fragment);
                        break;
                    case R.id.nav_profile:
                        fragment = new FragmentProfile();
                        setFragment(fragment);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

    }

    //Method to view website
    public void openSite(View view) {
        Intent toView = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
        startActivity(toView);
    }

    // Bottom navigation view method
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}

