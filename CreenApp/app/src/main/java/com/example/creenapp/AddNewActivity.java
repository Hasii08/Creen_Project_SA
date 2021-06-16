package com.example.creenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.Array;

public class AddNewActivity extends AppCompatActivity {

    private String complaintType;
    private EditText complaintAddress;
    private EditText complaintDescription;
    private Button submitComplaint;
    private String compaintEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        complaintAddress = (EditText) findViewById(R.id.editText2);
        complaintDescription = (EditText) findViewById(R.id.editText3);
        submitComplaint = (Button) findViewById(R.id.submitactivity);
        compaintEmail=MainActivity.emailAddress.getText().toString();

//        Reference of spinner from activity_add_new
        final Spinner typeOfcomplaint = (Spinner) findViewById(R.id.spinner);

//        items array
        ArrayAdapter<String> types = new ArrayAdapter<String>(AddNewActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.itemsList));
        types.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeOfcomplaint.setAdapter(types);

        submitComplaint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                complaintType=typeOfcomplaint.getSelectedItem().toString();
                postRequest();
                Toast.makeText(AddNewActivity.this, "Complaint received", Toast.LENGTH_SHORT).show();
                complaintAddress.setText(null);
                complaintDescription.setText(null);
            }
        });
    }

    private void postRequest() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL="https://creen.herokuapp.com/addcomplaint/"+compaintEmail;
            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("Type", complaintType);
            jsonBody.put("Address", complaintAddress.getText().toString());
            jsonBody.put("Description", complaintDescription.getText().toString());
            final String requestBody = jsonBody.toString();


            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                    System.out.println("Response from server:  " + response);
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
