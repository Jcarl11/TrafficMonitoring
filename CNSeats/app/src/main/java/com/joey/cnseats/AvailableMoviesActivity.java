package com.joey.cnseats;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class AvailableMoviesActivity extends AppCompatActivity
{
    ArrayList<DataItemAvailableMovies>dataItemAvailableMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_movies);
        dataItemAvailableMovies = new ArrayList<>();
        loadClick();
    }

    public void loadClick()
    {

        try
        {
            final ListView listView = (ListView)findViewById(R.id.listView_myTickets);
            final ArrayList<String> movies = new ArrayList<>();
            RequestQueue requestQueue = Volley.newRequestQueue(AvailableMoviesActivity.this);
            String myURL = "http://192.168.137.1:8080/SampleWebService/webresources/generic/getNowShowing";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myURL, new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response)
                {
                    try
                    {
                        Iterator<String> iterator = response.keys();
                        while (iterator.hasNext())
                        {
                            String key = iterator.next();
                            JSONObject child = response.getJSONObject(key);
                            final String title = (String) child.get("title");
                            String price = (String) child.get("price");
                            String rating = (String) child.get("rating");
                            String time = (String) child.get("time");
                            dataItemAvailableMovies.add(new DataItemAvailableMovies(title,price,rating,time));
                        }
                        AdapterAvailableMovies adapter = new AdapterAvailableMovies(AvailableMoviesActivity.this,R.layout.itemrow2,dataItemAvailableMovies);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                        {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView,  View view, int i, long l)
                            {
                                final String movietitle = ((TextView)view.findViewById(R.id.TEXTVIEW_TITLE3)).getText().toString();
                                final String moviePrice = ((TextView)view.findViewById(R.id.TEXTVIEW_PRICE3)).getText().toString();
                                final String movieRating = ((TextView)view.findViewById(R.id.TEXTVIEW_RATING3)).getText().toString();
                                final String movieTime = ((TextView)view.findViewById(R.id.TEXTVIEW_TIME3)).getText().toString();
                                AlertDialog.Builder builder = new AlertDialog.Builder(AvailableMoviesActivity.this);
                                builder.setTitle("Confirm selected");
                                builder.setMessage(movietitle + "\n" + movieTime);
                                builder.setCancelable(true);
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        Intent intent = new Intent(AvailableMoviesActivity.this, HomeScreen.class);
                                        intent.putExtra("selectedmovie", movietitle);
                                        intent.putExtra("playtime", movieTime);
                                        startActivity(intent);
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        });
                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(AvailableMoviesActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            int socketTimeout = 30000;
            RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjectRequest.setRetryPolicy(retryPolicy);
            requestQueue.add(jsonObjectRequest);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
