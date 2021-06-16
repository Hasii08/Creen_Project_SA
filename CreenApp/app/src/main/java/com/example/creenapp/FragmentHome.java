package com.example.creenapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {

    //Reference variables from fragment_home xml
    private Button addNew;
    private Button mySub;
    private Button viewWeb;
    private TextView pendingComplaint;
    private TextView approvedComplaint;
    public static String pending;
    public static String approved;

    public FragmentHome() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getComplaintStatus(MainActivity.emailAddress.getText());
        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        Initializing the buttons with xml ID
        addNew = (Button) view.findViewById(R.id.addNewActivity);
        mySub = (Button) view.findViewById(R.id.mysubmittedActivities);
        viewWeb = (Button) view.findViewById(R.id.visitwebsite);
        pendingComplaint=(TextView) view.findViewById(R.id.pendingText);
        approvedComplaint=(TextView) view.findViewById(R.id.approvedText);

        pendingComplaint.setText(pending);
        approvedComplaint.setText(approved);


        //Add new activity button event
        addNew.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddNewActivity.class);
                startActivity(intent);
            }
        });

        //My submitted activities button event
        mySub.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewSubmittedActivities(v);
//                Intent intent = new Intent(getActivity(), SubmittedActivities.class);
//                startActivity(intent);
            }
        });



        //View web button event
        viewWeb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSite(v);
            }
        });

        return view;
    }


    //Method to view website
    public void openSite(View view) {
        Intent toView = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
        startActivity(toView);
    }

    //Method to view user submitted activities
    public void viewSubmittedActivities(View view){
        Intent toView = new Intent(Intent.ACTION_VIEW, Uri.parse("https://creen.herokuapp.com/viewcomplains/"+MainActivity.emailAddress.getText()));
        startActivity(toView);
    }

    //Method to get complaint status

    public void getComplaintStatus(Editable email) {
        String url = "https://creen.herokuapp.com/homepagedata/"+email;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Rest response", response.toString());
                try {
                    pending=response.get("pending").toString();
                    approved=response.get("solved").toString();
                    System.out.println("Pending complaints :"+pending);
                    System.out.println("Approved complaints :"+approved);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Rest response", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}
