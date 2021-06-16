package com.example.creenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SubmittedActivities extends AppCompatActivity {

    //Array for complaints
    String[] mobileArray = {"Android", "IPhone", "WindowsMobile", "Blackberry",
            "WebOS", "Ubuntu", "Windows7", "Max OS X", "wduhwdhwudhw",
            "wdjwdijawdiaw", "wdiawjiawjdiaw", "wdjwidjwi", "wdiwjdiwj"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitted_activities);

        //List view reference from activity_submitted_activities
//        ListView listView = (ListView) findViewById(R.id.complainlist);
//
//        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mobileArray);
//        listView.setAdapter(myAdapter);
    }
}
