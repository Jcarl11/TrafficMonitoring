package com.example.trafficmonitoring;

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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TaskExecute extends AsyncTask<String, Void, ArrayList<String>>
{
    ArrayList<String> reportList = new ArrayList<>();
    boolean finished = false;
    ProgressBar progressBar;
    Context context;

    public TaskExecute(ProgressBar progressBar, Context context) {
        this.progressBar = progressBar;
        this.context = context;
    }

    @Override
    protected ArrayList<String> doInBackground(String... strings)
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
                        reportList.add(entity.toJSON());
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
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(ArrayList<String> report1Entities) {
        progressBar.setVisibility(View.INVISIBLE);
        if(report1Entities.size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("data", report1Entities);
            context.startActivity(new Intent(context, AverageReport.class).putExtras(bundle));
        }
        else
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
    }
}
