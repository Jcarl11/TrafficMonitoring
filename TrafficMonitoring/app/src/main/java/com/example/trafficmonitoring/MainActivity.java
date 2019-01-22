package com.example.trafficmonitoring;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.Parse;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radiobtn_los;
    RadioButton radiobtn_average;
    Button btn_show;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                // if defined
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());
        radioGroup = (RadioGroup)findViewById(R.id.RADIOGRP);
        radiobtn_los = (RadioButton)findViewById(R.id.RADIOBTN_LOS);
        radiobtn_average = (RadioButton)findViewById(R.id.RADIOBTN_AVERAGE);
        btn_show = (Button)findViewById(R.id.btn_show);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
    }
    public void showClick(View view)
    {
        int checkedId = radioGroup.getCheckedRadioButtonId();
        if(checkedId == R.id.RADIOBTN_AVERAGE)
            Toast.makeText(this, "Average", Toast.LENGTH_SHORT).show();
        else if(checkedId == R.id.RADIOBTN_LOS)
            new TaskExecute(progressBar, this).execute("");
        else
            Toast.makeText(this, "Please choose", Toast.LENGTH_SHORT).show();
    }
}
