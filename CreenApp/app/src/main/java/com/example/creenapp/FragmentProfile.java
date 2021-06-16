package com.example.creenapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends Fragment {


    //Reference variables from fragment_profile
    private Button logOutBtn;
    private Button changePassword;
    private TextView userName;
    private TextView userEmailAddress;
    private TextView userPhoneNumber;
    private EditText oldPassword;
    private EditText newPassword;
    public static boolean logoutStatus = false;


    public FragmentProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        logOutBtn = (Button) view.findViewById(R.id.logout_button);
        changePassword = (Button) view.findViewById(R.id.changepass);
        userName = (TextView) view.findViewById(R.id.profile_username);
        userEmailAddress = (TextView) view.findViewById(R.id.user_email);
        userPhoneNumber = (TextView) view.findViewById(R.id.user_number);
        oldPassword = (EditText) view.findViewById(R.id.old_password);
        newPassword = (EditText) view.findViewById(R.id.new_password);


        userName.setText(MainActivity.userName);
        userEmailAddress.setText(MainActivity.emailAddress.getText().toString());
        userPhoneNumber.setText(MainActivity.phoneNumber);

        changePassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (oldPassword.getText().toString().matches(MainActivity.password.getText().toString())) {
                    changePasswordMethod();
                    oldPassword.setText(null);
                    newPassword.setText(null);
                    Toast.makeText(getActivity(), "Password Changed", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "Invalid Old Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    logOutEvent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    public void logOutEvent() throws IOException {
        startActivity(new Intent(getActivity(), SplashActivity.class));
        getActivity().finish();
    }

    public void changePasswordMethod() {
        String url = "https://creen.herokuapp.com/changepassword/" + MainActivity.emailAddress.getText().toString() + "/" + MainActivity.password.getText().toString() + "/" + newPassword.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Rest response", response.toString());
                try {
                    System.out.println(response.get("changepassword").toString());
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
