package com.example.joey11.quizpractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    Button submit,clear;
    EditText name,email,phone,postal;
    String selectedGender,name1,email1,phoneNum,postalNum;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText)findViewById(R.id.TEXTFIELD_NAME);
        email = (EditText)findViewById(R.id.TEXTFIELD_EMAIL);
        phone = (EditText)findViewById(R.id.TEXTFIELD_PHONE);
        postal = (EditText)findViewById(R.id.TEXTFIELD_POSTAL);
        submit = (Button)findViewById(R.id.button);
    }


    public void submit(View view)
    {
        getStringRepresentation();
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String URL = "http://192.168.1.6:8080/SAMPLE_ANDROID_WEBSERVICE/webresources/generic/login";
        try {
            HashMap<String,String>params = new HashMap<String,String>();
            params.put("uname","Carlo");
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL, new JSONObject(params), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try
                    {
                        Toast.makeText(MainActivity.this,(String) response.get("output"),Toast.LENGTH_SHORT).show();
                    }catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void clearFields(View v)
    {
        name.setText("");
        email.setText("");
        phone.setText("");
        postal.setText("");
        name.requestFocus();
    }

    public void getStringRepresentation()
    {
        RadioGroup gr = (RadioGroup)findViewById(R.id.radioGroup);
        RadioButton selected = (RadioButton) findViewById(gr.getCheckedRadioButtonId());
        selectedGender = selected.getText().toString();
        name1 = name.getText().toString().trim();
        email1 = email.getText().toString().trim();
        phoneNum = phone.getText().toString().trim();
        postalNum = postal.getText().toString().trim();
    }
}
