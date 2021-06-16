package com.example.creenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    //Reference variable from xml activity_main
    public static EditText emailAddress;
    public static EditText password;
    public static String userName;
    public static String phoneNumber;
    private Button login;
    private Button signup;
    private String loginStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting email and password from the text field
        emailAddress = (EditText) findViewById(R.id.emailAddress);
        password = (EditText) findViewById(R.id.password);
        login = findViewById(R.id.loginbtn);
        signup = findViewById(R.id.Signupbtn);

        // Sign up button event Handler
        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });


        //Login button event handler
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                connection(emailAddress.getText(), password.getText());
                getData(emailAddress.getText(), password.getText());
                if (loginStatus == "true") {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(intent);
                } else {
                    emailAddress.setError("Invalid");
                    password.setError("Invalid");
                }
            }
        });
    }

    //Method to check if the email and password is correct
//    private void validate(String userEmail, String userPassword) {
//        if ((userEmail.equals("Admin")) && (userPassword.equals("1234"))) {
//            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
//            startActivity(intent);
//        } else {
//            emailAddress.setError("Invalid");
//            password.setError("Invalid");
//        }
//    }

    //Method to build connection and check user email and password.
    public void connection(Editable email, Editable pass) {
        String url = "https://creen.herokuapp.com/LoginCheck/" + email + "/" + pass;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Rest response", response.toString());
                try {
                    loginStatus = response.get("Login").toString();
                    System.out.println(loginStatus);
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

    //Method to get All User Data
    public void getData(Editable email, Editable pass) {
        String url = "https://creen.herokuapp.com/userprofile/" + email;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Rest response get data", response.toString());
                try {
                    phoneNumber = response.get("Phone").toString();
                    userName = response.get("username").toString();
                    System.out.println("The username " + userName);
                    System.out.println("The phone " + phoneNumber);
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
