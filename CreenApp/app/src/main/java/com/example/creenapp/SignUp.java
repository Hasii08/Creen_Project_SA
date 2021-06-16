package com.example.creenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;
import com.mongodb.lang.NonNull;
import com.mongodb.stitch.android.core.auth.providers.userpassword.UserPasswordAuthProviderClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private EditText nameView;
    private EditText emailView;
    private EditText passwordView;
    private EditText phoneView;
    private Button registerBtn;
    private String statusCode;

    private Response.Listener<String> mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameView = (EditText) findViewById(R.id.editText5);
        emailView = (EditText) findViewById(R.id.editText7);
        passwordView = (EditText) findViewById(R.id.editText10);
        phoneView = (EditText) findViewById(R.id.editText9);
        registerBtn = (Button) findViewById(R.id.submit);


        registerBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (nameView.getText().toString().isEmpty()) {
                    nameView.setError("Please Enter Name");
                }
                if (emailView.getText().toString().isEmpty() || !MiscFunc.isEmailValid(emailView.getText().toString())) {
                    emailView.setError("Email not valid");
                }
                if (phoneView.getText().toString().isEmpty()) {
                    phoneView.setError("Phone is Empty");
                }
                if (passwordView.getText().toString().isEmpty() || !MiscFunc.isPasswordValid(passwordView.getText().toString())) {
                    passwordView.setError("Password atleast 6 characters");
                } else {
                    postRequest();
                        nameView.setText(null);
                        emailView.setText(null);
                        phoneView.setText(null);
                        passwordView.setText(null);
                    Toast.makeText(SignUp.this, "Request received", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void postRequest() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "https://creen.herokuapp.com/adduser";
            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("Email", emailView.getText().toString());
            jsonBody.put("username", nameView.getText().toString());
            jsonBody.put("Password", passwordView.getText().toString());
            jsonBody.put("Phone", phoneView.getText().toString());
            final String requestBody = jsonBody.toString();


            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                    System.out.println("Response from server:  " + response);
                    statusCode = response;
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
