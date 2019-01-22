package com.joey.cnseats;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText uname,pword;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uname = (EditText)findViewById(R.id.TEXTFIELD_USERNAME);
        pword = (EditText)findViewById(R.id.TEXTFIELD_PASSWORD);
    }

    public void registerClicked(View view)
    {
        Intent registerPage = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(registerPage);
    }

    public void loginClicked(View view)
    {

       try
       {
           JSONObject params = new JSONObject();
           params.put("USERNAME", uname.getText().toString().trim());
           params.put("PASSWORD", pword.getText().toString().trim());
           RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
           String myURL = "http://192.168.137.1:8080/SampleWebService/webresources/generic/login";
           JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(myURL,params, new Response.Listener<JSONObject>() {
               @Override
               public void onResponse(JSONObject response)
               {
                   try {
                       if(response.get("output").equals(true))
                       {
                           SharedPreferences sharedPref = getSharedPreferences("userid", Context.MODE_PRIVATE);
                           SharedPreferences.Editor editor = sharedPref.edit();
                           editor.putString("id",(String) response.get("userid"));
                           editor.putString("username", uname.getText().toString().trim());
                           editor.apply();

                           Intent moviePage = new Intent(LoginActivity.this,AvailableMoviesActivity.class);
                           startActivity(moviePage);
                       }
                       else if(response.get("output").equals(false))
                       {
                           Toast.makeText(LoginActivity.this, "Account does not exist",Toast.LENGTH_SHORT).show();
                       }
                   } catch (JSONException e)
                   {
                       e.printStackTrace();
                   }
               }
           }, new Response.ErrorListener()
           {
               @Override
               public void onErrorResponse(VolleyError error)
               {
                   Toast.makeText(LoginActivity.this, error.toString(),Toast.LENGTH_SHORT).show();
               }
           });
           int socketTimeout = 30000;
           RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
           jsonObjectRequest.setRetryPolicy(retryPolicy);
           requestQueue.add(jsonObjectRequest);
       }catch(Exception ex)
       {
           ex.printStackTrace();
       }
    }
}
