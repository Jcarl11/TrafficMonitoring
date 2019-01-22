package com.example.joey11.trafficmonitoringsystem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jjoe64.graphview.GraphView;
import com.parse.Parse;

public class MainActivity extends AppCompatActivity
{
    private RadioGroup radioGroup;
    private RadioButton los, average;
    GraphView graph;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                // if defined
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());
        setContentView(R.layout.activity_main);
        radioGroup = (RadioGroup)findViewById(R.id.RADIOGRP);
        los = (RadioButton)findViewById(R.id.RADIOBTN_LOS);
        average = (RadioButton)findViewById(R.id.RADIOBTN_AVERAGE);
        graph = (GraphView)findViewById(R.id.graph);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
    }
    public void showOnClick(View view)
    {
        new TaskExecute(this, progressBar).execute("Data2");
        //startActivity(new Intent(MainActivity.this,ReportsActivity.class).putExtras());
        /*StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"Monday", "Tuesday", "Wednesday","Thursday","Friday","Saturday","Sunday"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        int id = radioGroup.getCheckedRadioButtonId();
        if(id == -1)
            Toast.makeText(this, "Please choose",Toast.LENGTH_SHORT).show();
        else if(los.getId() == id)
            Toast.makeText(this, "You choose LOS",Toast.LENGTH_SHORT).show();
        else if(average.getId() == id)
        {
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                    new DataPoint(1, 1),
                    new DataPoint(2, 2),
                    new DataPoint(3, 3),
                    new DataPoint(4, 4),
                    new DataPoint(5, 5),
                    new DataPoint(6, 6),
                    new DataPoint(7, 7)
            });
            graph.addSeries(series);
        }
        else
            Toast.makeText(this, "Error",Toast.LENGTH_SHORT).show();*/
    }
}
