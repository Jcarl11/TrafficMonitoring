package com.example.joey11.trafficmonitoringsystem;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import bolts.Task;

public class TaskExecute extends AsyncTask<String, Void, ArrayList<Report1Entity>>
{
    ArrayList<Report1Entity> reportList = new ArrayList<>();
    boolean finished = false;
    ProgressBar progressBar;
    Context context;
    public TaskExecute(Context context, ProgressBar progressBar)
    {
        this.progressBar = progressBar;
        this.context = context;
    }

    @Override
    protected ArrayList<Report1Entity> doInBackground(String... strings)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Data2");
        query.findInBackground(new FindCallback<ParseObject>()
        {
            @Override
            public void done(List<ParseObject> objects, ParseException e)
            {
                if(e == null && objects != null)
                {
                    for(ParseObject data : objects)
                    {
                        Report1Entity entity = new Report1Entity();
                        entity.setTimeStamp(data.getDate("TIMESTAMP"));
                        entity.setAverage(data.getString("AVERAGE"));
                        entity.setDay(data.getString("DAY"));
                        reportList.add(entity);
                    }
                    finished = true;
                }
            }
        });
        while(finished == false)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {e.printStackTrace();}
        }
        return reportList;
    }

    @Override
    protected void onPreExecute()
    {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(ArrayList<Report1Entity> report1Entities)
    {
        progressBar.setVisibility(View.INVISIBLE);
        GlobalVariables.report1Entities = report1Entities;
        Intent intent = new Intent(context, ReportsActivity.class);
        context.startActivity(intent);
    }


}
